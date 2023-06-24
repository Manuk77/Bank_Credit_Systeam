package com.example.bank.mailmessage;

import com.example.bank.customer.creating_requests.requests.CustomerRequest;
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

@Service
@EnableAutoConfiguration
public class EmailService {

    private final PdfGenerator pdfGenerator;
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(PdfGenerator pdfGenerator, JavaMailSender javaMailSender) {
        this.pdfGenerator = pdfGenerator;
        this.javaMailSender = javaMailSender;
    }

    public void sendEmailWithAttachment(CustomerModel customerModel)
            throws MessagingException, DocumentException, IOException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(customerModel.getCustomerInfoModel().getEmail());
        helper.setSubject("");
        helper.setText("  ");
        String filePath = pdfGenerator.generatePdf("this is content", customerModel);

        File file = new File(filePath);
        helper.addAttachment(file.getName(), file);

        javaMailSender.send(message);
        System.out.println("sending");
    }
}
