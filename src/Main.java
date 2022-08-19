import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Main {
    public static void main(String[] args) {
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");
        final String username = "yourEmail@gmail.com";
        final String password = "16-character-password";
        try{
            Session session = Session.getDefaultInstance(props,
                    new Authenticator(){
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }});

            // -- Create a new message --
            Message msg = new MimeMessage(session);

            // -- Set the FROM and TO fields --
            msg.setFrom(new InternetAddress("yourEmail@gmail.com"));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("toEmail@gmail.com",false));
            msg.setSubject("Testing");
            msg.setText("Pls work");
            msg.setSentDate(new Date());
            Transport.send(msg);
            System.out.println("Message sent.");
        }catch (MessagingException e){
            System.out.println("Erreur d'envoi, cause: " + e);
        }
    }
}
/*
Google stopped supporting "Less Secure Apps" as of May 30th, 2022.

One of the alternatives to solve this problem is to use 2-Step Verification and generate app password:

Google Account -> Security -> 2-Step Verification -> Input password as asked -> Turn ON (you could use SMS to get Gmail code to activate 2-Step Verification)
Google Account -> Security -> App password -> Input password as asked -> Select the app and device... -> e.g. Other(Custom name) -> Input app name e.g. MyApp -> Generate
Copy a 16-character password
Use a 16-character password with Gmail username in your application
*/