package com.example.bank.mailmessage;


import com.example.bank.customer.dto.CustomerModel;
import com.example.bank.mailmessage.PDFGenerator.PdfGenerator;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.File;
import java.io.IOException;

/**
 * The `EmailService` class is a service class responsible for sending emails to customers.
 * It utilizes the `PdfGenerator` and `JavaMailSender` components for generating PDF documents and sending emails, respectively.
 */
@Service
@EnableAutoConfiguration
public class EmailService {

    private final PdfGenerator pdfGenerator;
    private final JavaMailSender javaMailSender;

    /**
     * Constructs an `EmailService` with the specified `PdfGenerator` and `JavaMailSender`.
     *
     * @param pdfGenerator     The `PdfGenerator` component used for generating PDF documents.
     * @param javaMailSender   The `JavaMailSender` component used for sending emails.
     */
    @Autowired
    public EmailService(PdfGenerator pdfGenerator, JavaMailSender javaMailSender) {
        this.pdfGenerator = pdfGenerator;
        this.javaMailSender = javaMailSender;
    }

    /**
     * Sends an email to the specified customer with the provided content and customer model.
     *
     * @param content       The content of the email.
     * @param customerModel The customer model containing customer information.
     * @throws MessagingException If an error occurs during email composition or sending.
     * @throws DocumentException If an error occurs during PDF document generation.
     * @throws IOException       If an I/O error occurs.
     */
    public void sendEmailCustomers(String content,CustomerModel customerModel)
            throws MessagingException, DocumentException, IOException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(customerModel.getCustomerInfoModel().getEmail());
        helper.setSubject("Texekanq bankic");
        helper.setText("harcman ardyunq@ karox eq tesnel kcvac pdf-um");
        String filePath = pdfGenerator.generatePdf( content,customerModel);

        File file = new File(filePath);
        helper.addAttachment(file.getName(), file);

        javaMailSender.send(message);
        System.out.println("sending");
    }
}
