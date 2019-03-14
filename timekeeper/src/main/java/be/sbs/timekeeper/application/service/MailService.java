package be.sbs.timekeeper.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import be.sbs.timekeeper.application.security.MailConfig;

@Service
public class MailService {
	private static final String baseUrl = "http://localhost:8383/Timekeeper-frontend/activate.html";
	
	@Autowired
	public MailConfig javaMailSender;
	
	public void sendMail(String email, String activationToken, String userName) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("Activation account timekeeper");
		String activationUrl = buildUrl(activationToken, userName);
		message.setText("Activate your account: " + activationUrl);
		message.setFrom("info@sexybatmansquad.com");
		javaMailSender.getMailSender().send(message);
	}
	
	private String buildUrl(String activationToken, String userName) {
		return baseUrl + "?name=" + userName + "&activationtoken=" + activationToken;
	}
}
