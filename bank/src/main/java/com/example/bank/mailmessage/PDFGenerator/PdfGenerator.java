package com.example.bank.mailmessage.PDFGenerator;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
@Service
public class PdfGenerator {

    public String generatePdf(String content) throws DocumentException, IOException {
        String fileName = "credit.pdf";
        String filePath = "bank/src/main/resources/" + fileName;
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();
        document.add(new Paragraph(content));
        document.close();
        return filePath;
    }
}
