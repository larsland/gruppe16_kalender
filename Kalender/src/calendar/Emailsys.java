package calendar;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class Emailsys {
 
  public Emailsys(ArrayList<String> emails) {
 
    final String username = "prosjektvar2014@gmail.com";
    final String password = "Prosjekt til Det er Over";
 
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");
 
    Session session = Session.getInstance(props,
      new javax.mail.Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
      }
      });
 for (String mail : emails) {
    try {
 
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress("prosjektvar2014@gmail.com"));
      message.setRecipients(Message.RecipientType.TO,
        InternetAddress.parse(mail));
      message.setSubject("Testing Subject");
      message.setText("Dear Mail Crawler,"
        + "\n\n No spam to my email, please!");
 
      Transport.send(message);
 
      System.out.println("Done");
 
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }
  }
}
