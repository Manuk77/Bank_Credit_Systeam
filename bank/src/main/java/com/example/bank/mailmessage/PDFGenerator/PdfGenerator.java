package com.example.bank.mailmessage.PDFGenerator;

import com.example.bank.customer.dto.CustomerModel;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * The `PdfGenerator` class is a service class that generates a PDF document based on the provided content and customer model.
 * It provides a method to generate a PDF and return the file path of the generated PDF.
 */
@Service
public class PdfGenerator {

    /**
     * Generates a PDF document with the specified content and customer model.
     *
     * @param content       The content to be included in the PDF document.
     * @param customerModel The customer model containing customer information.
     * @return The file path of the generated PDF document.
     * @throws DocumentException If an error occurs during PDF document creation.
     * @throws IOException       If an I/O error occurs.
     */
    public String generatePdf(String content,CustomerModel customerModel) throws DocumentException, IOException {
        String fileName = customerModel.getCustomerInfoModel().getFirstName() +
                " " + customerModel.getCustomerInfoModel().getLastName() + ".pdf";
        String filePath = "bank/src/main/resources/" + fileName;
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();
        document.add(new Paragraph("Hargeli " + customerModel.getCustomerInfoModel().getFirstName() +
                " " + customerModel.getCustomerInfoModel().getLastName() + " dzer hayt@ " + content ));
        document.close();
        return filePath;
    }
}
