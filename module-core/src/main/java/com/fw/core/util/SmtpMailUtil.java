package com.fw.core.util;

import com.fw.core.dto.FileDTO;
import com.fw.core.dto.MailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Component
public class SmtpMailUtil {

    private static JavaMailSender javaMailSender;
    private static String username;
    private static String password;

    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        SmtpMailUtil.javaMailSender = javaMailSender;
    }

    @Value("${spring.mail.username}")
    public void setUsername(String username) {
        SmtpMailUtil.username = username;
    }

    @Value("${spring.mail.password}")
    public void setPassword(String password) {
        SmtpMailUtil.password = password;
    }

    public static void sendMail(MailDTO mailDTO){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            InternetAddress[] toEmailArr = Arrays.stream(mailDTO.getToEmailList().toArray()).map(str -> {
                try {
                    return new InternetAddress(String.valueOf(str));
                } catch (AddressException e) {
                    throw new RuntimeException(e);
                }
            }).toArray(InternetAddress[]::new);

            InternetAddress[] ccEmailArr = new InternetAddress[]{};
            if(!Objects.isNull(mailDTO.getCcEmailList())) {
                ccEmailArr = Arrays.stream(mailDTO.getCcEmailList().toArray()).map(str -> {
                    try {
                        return new InternetAddress(String.valueOf(str));
                    } catch (AddressException e) {
                        throw new RuntimeException(e);
                    }
                }).toArray(InternetAddress[]::new);
            }

            InternetAddress[] hiddenCcEmailArr = new InternetAddress[]{};
            if(!Objects.isNull(mailDTO.getHiddenCcEmailList())) {
                hiddenCcEmailArr = Arrays.stream(mailDTO.getHiddenCcEmailList().toArray()).map(str -> {
                    try {
                        return new InternetAddress(String.valueOf(str));
                    } catch (AddressException e) {
                        throw new RuntimeException(e);
                    }
                }).toArray(InternetAddress[]::new);
            }

            mimeMessageHelper.setSubject(MimeUtility.encodeText(mailDTO.getSubject(), "UTF-8", "B")); // Base64 encoding
            mimeMessageHelper.setText(mailDTO.getContent(), mailDTO.isUseHtmlYn());
            mimeMessageHelper.setTo(toEmailArr);
            mimeMessageHelper.setCc(ccEmailArr);
            mimeMessageHelper.setBcc(hiddenCcEmailArr);
            mimeMessageHelper.setReplyTo(mailDTO.getReplyToEmail());

            if(!CollectionUtils.isEmpty(mailDTO.getAttachFileList())) {
                for(FileDTO f: mailDTO.getAttachFileList()) {
                    FileSystemResource fileSystemResource = new FileSystemResource(new File(f.getOriginName()));
                    mimeMessageHelper.addAttachment(MimeUtility.encodeText(f.getOriginName(), "UTF-8", "B"), fileSystemResource);
                }
            }

            for(String str : mailDTO.getReceiveEmail()){
                mimeMessageHelper.setFrom(new InternetAddress(str, str, "UTF-8"));
                javaMailSender.send(mimeMessage);
            }

        } catch(Exception e){
            log.error("error", e);
        }
    }

}
