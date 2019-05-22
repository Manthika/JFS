package sample;

import Connectivity.Connectivity;
import Interface.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;


public class Newsletter {
    public TextField subject;
    public TextArea body;
    public Button backButton;
    public Button sendButton;

    SceneSwitcher sceneSwitcher = new SceneSwitcher();
    Connectivity connection = new Connectivity();
    Connection connec = connection.getConnection();

    public void sendButtonClicked(ActionEvent actionEvent) {

        InternetAddress[] nwemailIDs = getAddresses("nwemails");
        InternetAddress[] users = getAddresses("users WHERE access=\"Customer\"");

        sendMail(subject.getText(),body.getText(),nwemailIDs);
        sendMail(subject.getText(),body.getText(),users);
    }

    public void backButtonClicked(ActionEvent actionEvent) {
        sceneSwitcher.switchScene(backButton,"staffView.fxml","Staff View");
    }

    public void sendMail(String subject, String messageText, InternetAddress[] address){

        String host = "smtp.gmail.com";
        String user = "tharumudu@gmail.com";
        String password = "manthikagoogle1234";
        String from = "tharumudu@gmail.com";
        boolean sessionBug = false;

        Properties properties = System.getProperties();

        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.required","true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getDefaultInstance(properties, null);
        session.setDebug(sessionBug);

        Message msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(from));
            msg.addRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setText(messageText);

            Transport transport = session.getTransport("smtp");
            transport.connect(host, user, password);
            transport.sendMessage(msg,address);
            transport.close();
            System.out.println("email sent successfully");

        } catch (MessagingException e) {
            System.err.println(e);
        }


    }


    public InternetAddress[] getAddresses(String table){
        PreparedStatement pstmt;
        ResultSet result;
        String sql = "SELECT * FROM "+table;
        ArrayList email = new ArrayList();

        try {
            pstmt = connec.prepareStatement(sql);
            result = pstmt.executeQuery();

            while(result.next()) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(result.getString("email"));
                email.add(stringBuffer.toString());
            }

        } catch (SQLException e) {
            System.err.println(e);
        }

        InternetAddress[] address = new InternetAddress[email.size()];

        for (int i = 0; i < email.size(); i++) {
            try {
                address[i] = new InternetAddress(email.get(i).toString());
            } catch (AddressException e) {
                System.err.println(e);
            }
        }

        return address;
    }


}
