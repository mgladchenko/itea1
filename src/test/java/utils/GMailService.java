package utils;

import com.sun.mail.imap.IdleManager;

import javax.mail.*;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GMailService {
	String host = "imap.gmail.com";
	String user = "Mykola.Gladchenko@gmail.com";
	String pass = "your_pass_here";

	private volatile boolean isMessageReceived = false;
	private volatile String messageString;

	public static void main(String[] args) {
		String messageSubjectPartial = "here's the link to reset your password";
		String messageToPartial = "mykola.gladchenko@gmail.com";
		String messageFromPartial = "security-noreply@linkedin.com";

		GMailService GMailService = new GMailService();
		String message = GMailService.waitForNewMessage(messageSubjectPartial, messageToPartial, messageFromPartial, 60);
		System.out.println("Content: " + message);
	}

	public synchronized String waitForNewMessage(String messageSubjectPartial, String messageToPartial, String messageFromPartial,
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
				throw new AuthenticationFailedException("Make sure 'Allow less secure apps' is 'ON' on gmail account here "
						+ "https://myaccount.google.com/lesssecureapps" + "\n" + e.getMessage());
			}

			ExecutorService executorService = Executors.newCachedThreadPool();
			IdleManager idleManager = new IdleManager(session, executorService);

			Folder inbox = store.getFolder("inbox");
			inbox.open(Folder.READ_WRITE);

			inbox.addMessageCountListener(new MessageCountAdapter() {
				public synchronized void messagesAdded(MessageCountEvent ev) {

					try {
						Message[] messages = ev.getMessages();
						for (Message message : messages) {
							String from = message.getFrom()[0].toString();
							String to = message.getAllRecipients()[0].toString();
							String subject = message.getSubject().toString();

							if (from.contains(messageFromPartial) && to.contains(messageToPartial) && subject.contains(messageSubjectPartial)) {
								isMessageReceived = true;
								if (message.isMimeType("text/plain")) {
									messageString = message.getContent().toString();
								} else if (message.isMimeType("multipart/*")) {
									MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
									messageString = getTextFromMimeMultipart(mimeMultipart);
								}
								idleManager.stop();
							}
						}

					} catch (MessagingException e) {
						e.printStackTrace();
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

	private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
		String result = "";
		int count = mimeMultipart.getCount();
		for (int i = 0; i < count; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.isMimeType("text/plain")) {
				result = result + "\n" + bodyPart.getContent();
				break;
			} else if (bodyPart.isMimeType("text/html")) {
				String html = (String) bodyPart.getContent();
				result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
			} else if (bodyPart.getContent() instanceof MimeMultipart) {
				result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
			}
		}
		return result;
	}

}
