import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class MailSender {

    public static void sendMail(String emailAddress, String verificationEndpoint) {

        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

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
        final String username = "javatest5663@gmail.com";
        final String password = "JavaTest5663+";
        try{
            Session session = Session.getDefaultInstance(props,
                    new Authenticator(){
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }});

            // -- Create a new message --
            Message msg = new MimeMessage(session);

            // -- Set the FROM and TO fields --
            msg.setFrom(new InternetAddress("javatest5663@gmail.com"));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailAddress,false));
            msg.setSubject("Hello");
            msg.setText("To verify your registration please click on the following link:\n http://localhost:22222" + verificationEndpoint);
            msg.setSentDate(new Date());
            Transport.send(msg);
            System.out.println("Message sent.");
        } catch (MessagingException e){
            System.out.println("Error: " + e);
        }
    }
}

