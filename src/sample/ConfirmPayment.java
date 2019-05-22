package sample;

import ImplementedLinkedList.MyLinkedList;
import Interface.SceneSwitcher;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.html.table.Table;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;



public class ConfirmPayment implements Initializable {

    SceneSwitcher sceneSwitcher = new SceneSwitcher();
    MyLinkedList linkedList = new MyLinkedList();

    public Button confirmOrderButton;
    public Button backButton;

    public void initialize(URL url, ResourceBundle rb) {





    }


    public void backButtonClicked(ActionEvent actionEvent) {
        sceneSwitcher.switchScene(backButton,"customerView.fxml","Customer View");
    }

    public void confirmOrderButtonClicked(ActionEvent actionEvent) {

        sendReceipt();
        //reduce from product stock
        //update the transaction database table
        AlertBox.displayAlertBox("Congratulations","You have successfully confirmed the order! ");
        sceneSwitcher.switchScene(confirmOrderButton,"customerView.fxml","Customer View");
    }




    public void sendReceipt(){


        String sender = "tharumudu@gmail.com";
        String recipient = "tharumudu@gmail.com"; //replace this with a valid recipient email address
        String content = "Here is your tax invoice of the bought stuff from JFS. Thank you! ";
        String subject = "JFS - Tax Invoice";

        String host = "smtp.gmail.com";
        String user = "tharumudu@gmail.com";
        String password = "manthikagoogle1234";
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
                                "tharumudu@gmail.com", "manthikagoogle1234");
                    }
                });
        session.setDebug(sessionBug);

        ByteArrayOutputStream outputStream = null;

        try {

            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(content);

            outputStream = new ByteArrayOutputStream();
            writePdf(outputStream);
            byte[] bytes = outputStream.toByteArray();

            DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
            MimeBodyPart pdfBodypart = new MimeBodyPart();
            pdfBodypart.setDataHandler(new DataHandler(dataSource));
            pdfBodypart.setFileName("invoice.pdf");

            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(textBodyPart);
            mimeMultipart.addBodyPart(pdfBodypart);

            InternetAddress iaSender = new InternetAddress(sender);
            InternetAddress iaRecipient = new InternetAddress(recipient);

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setSender(iaSender);
            mimeMessage.setSubject(subject);
            mimeMessage.setRecipient(Message.RecipientType.TO, iaRecipient);
            mimeMessage.setContent(mimeMultipart);

            Transport transport = session.getTransport("smtp");
            transport.connect(host, user, password);
            Transport.send(mimeMessage);

            System.out.println("sent from " + sender +
                    ", to " + recipient);



        } catch(Exception ex) {
            System.err.println(ex);
        } finally {
            //clean off
            if(null != outputStream) {
                try { outputStream.close(); outputStream = null; }
                catch(Exception ex) { }
            }
        }



    }


    public void writePdf(OutputStream outputStream){

        Font h1 = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        Font h2 = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.UNDERLINE);
        Font body = new Font(Font.FontFamily.TIMES_ROMAN, 10);
        Font jfsEmail = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.UNDERLINE);
        jfsEmail.setColor(BaseColor.BLUE);


        Document document = new Document();
        try {
            PdfWriter.getInstance(document, outputStream);

            document.open();
            document.addTitle("JFS");
            document.addSubject("Tax Invoice");
            document.addKeywords("iText, email");
            document.addAuthor("Manthika Tharumudu");
            document.addCreator("Manthika Tharumudu");



            Paragraph head = new Paragraph("Jeff's Fishing Shack", h1);
            head.setAlignment(Element.ALIGN_CENTER);
            head.setSpacingAfter(5f);

            Paragraph subHead = new Paragraph("Tax Invoice",h2);
            subHead.setAlignment(Element.ALIGN_CENTER);
            subHead.setSpacingAfter(50f);

            Paragraph jfsAddress1 = new Paragraph("Jeff's Fishing Shack", body);
            Paragraph jfsAddress2 = new Paragraph("Trading as: Octopus pty Ltd", body);
            Paragraph jfsAddress3 = new Paragraph("Shop 4, Hillary's Boat Harbour", body);
            Paragraph jfsAddress4 = new Paragraph("Hillary's, WA, 6025", body);
            Paragraph jfsAddress5 = new Paragraph("T: 08 9402 2222", body);
            Paragraph jfsAddress6 = new Paragraph("sales@jfs.com", jfsEmail);
            jfsAddress6.setSpacingAfter(20f);

            Paragraph rID = new Paragraph("Receipt#:", body);
            Paragraph date = new Paragraph("Date: ", body);
            date.setAlignment(Element.ALIGN_RIGHT);
            date.setSpacingAfter(20f);

            Paragraph cusName = new Paragraph("Customer: ", body);
            Paragraph cusAddress = new Paragraph("Address here ", body);
            cusAddress.setSpacingAfter(20f);

            Paragraph cusId = new Paragraph("Customer#: ", body);
            Paragraph cusEmail = new Paragraph("Customer email: ", body);
            cusEmail.setSpacingAfter(20f);


            Paragraph purchases = new Paragraph("Purchases ", body);
            PdfPTable table;
            table = linkedList.addTable();


            document.add(head);
            document.add(subHead);
            document.add(jfsAddress1);
            document.add(jfsAddress2);
            document.add(jfsAddress3);
            document.add(jfsAddress4);
            document.add(jfsAddress5);
            document.add(jfsAddress6);
            document.add(rID);
            document.add(date);
            document.add(cusName);
            document.add(cusAddress);
            document.add(cusId);
            document.add(cusEmail);
            document.add(purchases);
            document.add(table);


            document.close();

        } catch (DocumentException e) {
            System.err.println(e);
        }


    }


  /*  public PdfPTable addTable(){

        PdfPTable table = new PdfPTable(7);
        try {
            table.setTotalWidth(new float[]{60f, 80f, 50f, 80f, 10f, 20f, 20f});







        } catch (DocumentException e) {
            System.err.println(e);
        }
    }*/



}
