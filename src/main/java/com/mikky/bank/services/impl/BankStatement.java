package com.mikky.bank.services.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mikky.bank.entities.Transaction;
import com.mikky.bank.entities.User;
import com.mikky.bank.repositories.TransactionRepository;
import com.mikky.bank.repositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

@Service
public class BankStatement {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    private static final String FILE = "D:\\Personal\\MyBankStatement.pdf";

    public List<Transaction> generateAndSendStatement(String accountNumber, String startDate, String endDate)
            throws FileNotFoundException, DocumentException, MessagingException {
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);

        List<Transaction> transactionList = transactionRepository.findAll().stream()
                .filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
                .filter(transaction -> {
                    LocalDate transactionDate = transaction.getCreatedAt().toLocalDate();
                    return !transactionDate.isBefore(start) && !transactionDate.isAfter(end);
                })
                .toList();
        User user = userRepository.findByAccountNumber(accountNumber);
        String recipientEmail = user.getEmail();
        // Generate PDF
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        generatePDF(outputStream, user, transactionList);

        // Send email with the PDF attachment
        sendEmail(recipientEmail, outputStream.toByteArray(), "Bank Statement", "Your bank statement is attached.");
        return  transactionList;
    }

    private void generatePDF(OutputStream outputStream, User user, List<Transaction> transactions)
            throws DocumentException {
        Rectangle statementSize = new Rectangle(PageSize.A4);
        Document document = new Document(statementSize);
        PdfWriter.getInstance(document, outputStream);
        document.open();

        addBankInfo(document);
        addUserDetails(document, user);
        addStatementInfo(document, transactions);

        document.close();
    }

    private void addBankInfo(Document document) throws DocumentException {
        PdfPTable bankInfoTable = new PdfPTable(1);
        PdfPCell bankName = new PdfPCell(new Phrase("Mikky Bank"));
        bankName.setBorder(0);
        bankName.setBackgroundColor(BaseColor.BLUE);
        bankName.setPadding(20f);

        PdfPCell bankAddress = new PdfPCell(new Phrase("04 Dumri Giridih Jharkhand 825106"));
        bankAddress.setBorder(0);

        bankInfoTable.addCell(bankName);
        bankInfoTable.addCell(bankAddress);

        document.add(bankInfoTable);
        document.add(new Paragraph("\n")); // Add space between sections
    }

    private void addUserDetails(Document document, User user) throws DocumentException {
        PdfPTable userDetailsTable = new PdfPTable(2);
        userDetailsTable.setWidthPercentage(50);

        addRow(userDetailsTable, "Account Number", user.getAccountNumber());
        addRow(userDetailsTable, "Full Name", user.getFirstName()+" "+user.getLastName());
        addRow(userDetailsTable, "Address", user.getAddress());

        document.add(userDetailsTable);
        document.add(new Paragraph("\n")); // Add space between sections
    }

    private void addStatementInfo(Document document, List<Transaction> transactions) throws DocumentException {
        PdfPTable statementInfo = new PdfPTable(5);
        addTableHeader(statementInfo);

        for (Transaction transaction : transactions) {
            addTransactionRow(statementInfo, transaction);
        }

        document.add(statementInfo);
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Transaction ID", "Transaction Type", "Amount", "Status", "Date")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addTransactionRow(PdfPTable table, Transaction transaction) {
        table.addCell(transaction.getTransactionId());
        table.addCell(transaction.getTransactionType());
        table.addCell(transaction.getAmount().toString());
        table.addCell(transaction.getStatus());
        table.addCell(transaction.getCreatedAt().toLocalDate().toString());
    }

    private void addRow(PdfPTable table, String key, String value) {
        table.addCell(key);
        table.addCell(value);
    }

    private void sendEmail(String to, byte[] attachment, String subject, String text) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        helper.addAttachment("Bank_Statement.pdf", new ByteArrayDataSource(attachment, "application/pdf"));

        mailSender.send(message);
    }
}
