package com.valan.service;

import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	private JavaMailSender javaMailSender;

	public void sendVerificationOtpEmail(String email, String otp) throws MessagingException {

		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, "utf-8");

		String subject = "Verification OTP";
		String text = "Verfication OTP code is" + otp;

		messageHelper.setSubject(subject);
		messageHelper.setText(text);
		messageHelper.setTo(email);

		try {
          javaMailSender.send(message);
		} catch (Exception e) {
          throw new MailSendException(e.getMessage());
		}
	}
}
