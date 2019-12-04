package uy.com.demente.ideas.javamail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author diego.gonzalezdurand
 */
public class SendEmailSMTP {
	
	Session mailSession;

	/**
	 * @param args
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public static void main(String args[]) throws AddressException, MessagingException {

		SendEmailSMTP javaEmail = new SendEmailSMTP();
		
		javaEmail.setMailServerProperties();
		javaEmail.draftEmailMessage();
		javaEmail.sendEmail();
	}

	/**
	 */
	private void setMailServerProperties() {
		
		Properties emailProperties = System.getProperties();
		
		emailProperties.put("mail.smtp.port", "586");
		emailProperties.put("mail.smtp.auth", "true");
		emailProperties.put("mail.smtp.starttls.enable", "true");
		
		mailSession = Session.getDefaultInstance(emailProperties, null);
	}

	/**
	 * @return
	 * @throws AddressException
	 * @throws MessagingException
	 */
	private MimeMessage draftEmailMessage() throws AddressException, MessagingException {
		
		String[] toEmails = { "1987diegog@gmail.com" };
		String emailSubject = "Test email subject";
		String emailBody = "This is an email sent by <b>//howtodoinjava.com</b>.";
		MimeMessage emailMessage = new MimeMessage(mailSession);
		
		/**
		 * Set the mail recipients
		 */
		for (int i = 0; i < toEmails.length; i++) {
			emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails[i]));
		}
		
		emailMessage.setSubject(emailSubject);
		
		/**
		 * If sending HTML mail
		 */
		emailMessage.setContent(emailBody, "text/html");
		
		/**
		 * If sending only text mail
		 */
		// emailMessage.setText(emailBody);// for a text email
		return emailMessage;
	}

	/**
	 * @throws AddressException
	 * @throws MessagingException
	 */
	private void sendEmail() throws AddressException, MessagingException {
		
		/**
		 * Sender's credentials
		 */
		String fromUser = "1987diegog@gmail.com";
		String fromUserEmailPassword = "05425003DysGmail";

		String emailHost = "smtp.gmail.com";
		Transport transport = mailSession.getTransport("smtp");
		transport.connect(emailHost, fromUser, fromUserEmailPassword);
		/**
		 * Draft the message
		 */
		MimeMessage emailMessage = draftEmailMessage();
		/**
		 * Send the mail
		 */
		transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
		transport.close();
		System.out.println("Email sent successfully.");
	}
}