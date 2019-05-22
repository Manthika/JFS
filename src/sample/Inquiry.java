package sample;

import Interface.SceneSwitcher;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class Inquiry {
    public TextArea inquiry;
    public Button backButton;
    public Button sendButton;

    SceneSwitcher sceneSwitcher = new SceneSwitcher();
    String cusName = "Customer Name here";

    public void backButtonClicked(ActionEvent actionEvent) {
        sceneSwitcher.switchScene(backButton,"customerView.fxml","Customer View");
    }

    public void sendButtonClicked(ActionEvent actionEvent) {

        sendInquiry(cusName, inquiry.getText());
        AlertBox.displayAlertBox("Success","You have successfully sent the inquiry");
        sceneSwitcher.switchScene(backButton,"customerView.fxml","Customer View");
    }

    public void sendInquiry(String customerName, String messageText){

        String host = "smtp.gmail.com";
        String user = "tharumudu@gmail.com";
        String from = "tharumudu@gmail.com";
        String to = "tharumudu@gmail.com";
        String password = "manthikagoogle1234";
        String subject = "CUSTOMER INQUIRY from "+customerName.toUpperCase();
        boolean sessionBug = false;

        Properties properties = System.getProperties();

        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.required","true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator(){
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                "tharumudu@gmail.com", "manthikagoogle1234");// Specify the Username and the PassWord
                    }
                });
        session.setDebug(sessionBug);

        Message msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(from));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setText(messageText);

            Transport transport = session.getTransport("smtp");
            transport.connect(host, user, password);
            transport.send(msg);
            transport.close();
            System.out.println("inquiry sent successfully");

        } catch (MessagingException e) {
            System.err.println(e);
        }


    }



}


