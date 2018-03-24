import com.sun.mail.imap.IdleManager;

import javax.mail.*;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GmailService {
	String host = "imap.gmail.com";
	String user = "iownit.tau@gmail.com";
	String pass = "yourpassword";

	private volatile boolean isMessageReceived = false;
	private volatile String messageString;

	public static void main(String[] args) {
		String messageSubject = "Fwd: Iownit email verification";
		String messageTo = "bfrow.tau@gmail.com";
		String messageFrom = "Mykola Gladchenko <mykola_gladchenko@iownit.us>";

		GmailService gmailService = new GmailService();
		String message = gmailService.waitForNewMessage(messageSubject, messageTo, messageFrom, 60);
		System.out.println("Content: " + message);
	}

	private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
		String result = "";
		int count = mimeMultipart.getCount();
		for (int i = 0; i < count; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.isMimeType("text/plain")) {
				result = result + "\n" + bodyPart.getContent();
				break; // without break same text appears twice in my tests
			} else if (bodyPart.isMimeType("text/html")) {
				String html = (String) bodyPart.getContent();
				result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
			} else if (bodyPart.getContent() instanceof MimeMultipart) {
				result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
			}
		}
		return result;
	}

	public synchronized String waitForNewMessage(String messageSubject, String messageTo, String messageFrom,
			long timeoutInSec) {
		Properties properties = new Properties();
		properties.put("mail.imap.usesocketchannels", "true");
		properties.put("mail.imap.ssl.enable", "true");
		properties.put("mail.store.protocol", "imap");

		try {
			Session session = Session.getInstance(properties);
			Store store = session.getStore();
			try {
				store.connect(host, user, pass);
			} catch (AuthenticationFailedException e) {
				throw new Exception("Make sure 'Allow less secure apps' is 'ON' on gmail account here "
						+ "https://myaccount.google.com/lesssecureapps" + e);
			}

			ExecutorService executorService = Executors.newCachedThreadPool();
			IdleManager idleManager = new IdleManager(session, executorService);

			Folder inbox = store.getFolder("inbox");
			inbox.open(Folder.READ_WRITE);

			inbox.addMessageCountListener(new MessageCountAdapter() {
				public synchronized void messagesAdded(MessageCountEvent ev) {

					try {
						//Folder folder = (Folder) ev.getSource();
						Message[] messages = ev.getMessages();
						for (Message message : messages) {
							String from = message.getFrom()[0].toString();
							String to = message.getAllRecipients()[0].toString();
							String subject = message.getSubject().toString();

							if (from.equals(messageFrom) && to.equals(messageTo) && subject.equals(messageSubject)) {
								isMessageReceived = true;
								if (message.isMimeType("text/plain")) {
									messageString = message.getContent().toString();
								} else if (message.isMimeType("multipart/*")) {
									MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
									messageString = getTextFromMimeMultipart(mimeMultipart);
								}
								idleManager.stop();
							}
							idleManager.watch(inbox); // keep watching for new messages
						}

					} catch (MessagingException e) {
						//e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			});

			idleManager.watch(inbox);

			long startTime = System.currentTimeMillis();
			while (true) {
				if (isMessageReceived && messageString != null) {
					break;
				} else if ((System.currentTimeMillis() - startTime) > timeoutInSec * 1000) {
					idleManager.stop();
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageString;
	}

}
