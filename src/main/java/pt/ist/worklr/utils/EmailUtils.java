package pt.ist.worklr.utils;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import pt.ist.worklr.domain.User;

public class EmailUtils {

    private static final Properties properties = new Properties();

    static {
	try {
	    properties.load(PropertiesManager.class.getResourceAsStream("/configuration.properties"));
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public static void emailActivationInstructionsTo(User user) {

	final String username = "info@worklr.org";
	final String password = "";
	String from = "Worklr <" + username + ">";

	String to = user.getEmail();

	Properties props = System.getProperties();
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.host", "smtp.gmail.com");
	props.put("mail.smtp.port", "587");

	Session session = Session.getInstance(props, new javax.mail.Authenticator() {
	    @Override
	    protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password);
	    }
	});
	try {
	    // Create a default MimeMessage object.
	    MimeMessage message = new MimeMessage(session);

	    // Set From: header field of the header.
	    message.setFrom(new InternetAddress(from));

	    // Set To: header field of the header.
	    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	    // Set Subject: header field
	    message.setSubject("Worklr Activation");

	    // Now set the actual message
	    message.setText("Hi " + user.getName() + ",\n\nYou're receiving this email message"
		    + "in order to activate your Worklr account. To do so, please visit " + "the following URL. Thank you.\n\n"
		    + "http://" + properties.getProperty("app.host") + "/worklr/api/users/" + user.getExternalId()
		    + "/activate?activationKey=" + user.getActivationKey());

	    Transport.send(message);

	    // Send message
	    Transport.send(message);
	    System.out.println("Sent message successfully....");
	} catch (MessagingException mex) {
	    mex.printStackTrace();
	}

    }

}
