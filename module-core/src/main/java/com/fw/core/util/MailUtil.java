package com.fw.core.util;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import com.fw.core.dto.MailDTO;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


import com.amazonaws.regions.Regions;

/**
 * AWS SES Send Email
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MailUtil {

	private static AmazonSimpleEmailService amazonSimpleEmailService;

	@Autowired
	public void setAmazonSimpleEmailService(AmazonSimpleEmailService amazonSimpleEmailService) {
		MailUtil.amazonSimpleEmailService = amazonSimpleEmailService;
		
	}

	public static void sendEmailRaw(MailDTO mailDTO) throws Exception{
		Session session = Session.getDefaultInstance(new Properties());
		MimeMessage message = new MimeMessage(session);

		for(String to : mailDTO.getReceiveEmail()){
		
			message.setSubject(mailDTO.getSubject(), "UTF-8");
			message.setFrom(new InternetAddress(mailDTO.getFromEmail()));
			message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(to));


			// Create a multipart/alternative child container.
			MimeMultipart msg_body = new MimeMultipart("alternative");

			// Create a wrapper for the HTML and text parts.        
			MimeBodyPart wrap = new MimeBodyPart();
					
			// Define the HTML part.
			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(mailDTO.getContent(),"text/html; charset=UTF-8");
			
			//msg_body.addBodyPart(textPart);
			msg_body.addBodyPart(htmlPart);
			
			// Add the child container to the wrapper object.
			wrap.setContent(msg_body);
			
			// Create a multipart/mixed parent container.
			MimeMultipart msg = new MimeMultipart("mixed");
			
			// Add the parent container to the message.
			message.setContent(msg);
			
			// Add the multipart/alternative part to the message.
			msg.addBodyPart(wrap);
			
			// Define the attachment
			MimeBodyPart att = new MimeBodyPart();
			DataSource fds = new FileDataSource(mailDTO.getFilepath());
			att.setDataHandler(new DataHandler(fds));
			att.setFileName(fds.getName());
			
			// Add the attachment to the message.
			msg.addBodyPart(att);
			
			// Print the raw email content on the console
			PrintStream out = System.out;
			message.writeTo(out);

			// Send the email.
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			message.writeTo(outputStream);
			RawMessage rawMessage = 
					new RawMessage(ByteBuffer.wrap(outputStream.toByteArray()));

			
			SendRawEmailRequest rawEmailRequest = 
			new SendRawEmailRequest().withSource(mailDTO.getFromEmail()).withDestinations(to).withRawMessage(rawMessage);
			
			SendRawEmailResult sendRawEmailResult = amazonSimpleEmailService.sendRawEmail(rawEmailRequest);

			log.info("Email Send Result - {}", sendRawEmailResult.getSdkResponseMetadata().toString());

		}
    	
	}

	public static void sendEmail(MailDTO mailDTO) {
		// 목적지
		Destination destination = new Destination().withToAddresses(mailDTO.getReceiveEmail());

		// 제목, 본문 설정
		Message message = new Message().withSubject(new Content().withCharset("UTF-8").withData(mailDTO.getSubject()))
				.withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(mailDTO.getContent())));

		SendEmailRequest sendEmailRequest = new SendEmailRequest().withSource(mailDTO.getFromEmail()).withDestination(destination)
				.withMessage(message);

		SendEmailResult sendEmailResult = amazonSimpleEmailService.sendEmail(sendEmailRequest);
		log.info("Email Send Result - {}", sendEmailResult.getSdkResponseMetadata().toString());
	}

	public static void sendTemplateEmail(MailDTO mailDTO) {
		// 목적지
		Destination destination = new Destination().withToAddresses(mailDTO.getReceiveEmail());

		String templateData = new Gson().toJson(mailDTO.getTemplateData());
		SendTemplatedEmailRequest sendTemplatedEmailRequest = new SendTemplatedEmailRequest().withSource(mailDTO.getFromEmail()).withDestination(destination)
				.withTemplate(mailDTO.getTemplate())
				.withTemplateData(templateData);

		SendTemplatedEmailResult sendTemplatedEmailResult = amazonSimpleEmailService.sendTemplatedEmail(sendTemplatedEmailRequest);
		log.info("Email Send Result - {}", sendTemplatedEmailResult.getSdkResponseMetadata().toString());
	}

	public static void createTemplate(String subjectPart, String templateName, String textPart, String htmlPart) {
		Template template = new Template();
		template.setSubjectPart(subjectPart);
		template.setTemplateName(templateName);
		template.setTextPart(textPart);
		template.setHtmlPart(htmlPart);
		CreateTemplateRequest createTemplateRequest = new CreateTemplateRequest().withTemplate(template);
		CreateTemplateResult createTemplateResult = amazonSimpleEmailService.createTemplate(createTemplateRequest);
		log.info("Email Send Result - {}", createTemplateResult.getSdkResponseMetadata().toString());
	}

}
