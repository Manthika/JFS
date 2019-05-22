package ImplementedLinkedList;

import Database.Product;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.PrintWriter;

public class MyLinkedList {

    private ProductNode newP;


    public void insertAtProduct(int number,String description,String code,String size,int cost,int quantity,int amount){
        ProductNode productNode = new ProductNode(number, description, code, size, cost, quantity, amount);
        productNode.setNextProductNode(this.newP);
        this.newP = productNode;
    }


    public int length(){
        int length = 0;
        ProductNode current = this.newP;

        while(current != null){
            length++;
            current.getNextProductNode();
        }
        return  length;
    }


    @Override
    public String toString(){
        String result = "{ ";
        ProductNode current = this.newP;

        while(current!=null){
            result += current.toString() + ",  \n";
            current = current.getNextProductNode();
        }

        result += " }";
        return result;
    }

    public PdfPTable addTable(){

        PdfPTable table = new PdfPTable(7);
        try {
            table.setTotalWidth(new float[]{60f, 80f, 50f, 80f, 10f, 20f, 20f});

            ProductNode current = this.newP;

            while(current!=null){
                Paragraph no = new Paragraph(current.getNumber());
                Paragraph desc = new Paragraph(current.getDescription());
                Paragraph code = new Paragraph(current.getCode());
                Paragraph size = new Paragraph(current.getSize());
                Paragraph cost = new Paragraph(current.getCost());
                Paragraph quantity = new Paragraph(current.getQuantity());
                Paragraph amount = new Paragraph(current.getAmount());

                table.addCell(no);
                table.addCell(desc);
                table.addCell(code);
                table.addCell(size);
                table.addCell(cost);
                table.addCell(quantity);
                table.addCell(amount);

                current = current.getNextProductNode();
            }

        } catch (DocumentException e) {
            System.err.println(e);
        }

        return table;
    }


}
