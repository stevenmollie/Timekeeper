package be.sbs.timekeeper.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import be.sbs.timekeeper.application.beans.User;
import be.sbs.timekeeper.application.security.MailConfig;

@Service
public class MailService {
	private static final String BASE_URL = "http://localhost:8383/Timekeeper-frontend/";
	private static final String SENDER = "info@sexybatmansquad.com";
	
	@Autowired
	public MailConfig javaMailSender;
	
	public void sendActivationMail(String email, String activationToken, String userName) {
		sendEmail(email, "Activation account timekeeper", "Activate your account: " + buildActivationUrl(activationToken, userName));
	}
	
	public void sendResetPasswordMail(User user) {
		sendEmail(user.getEmail(), "Reset timekeeper password", "Reset your password: " + buildResetPasswordUrl(user));
	}
	
	private void sendEmail(String email, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject(subject);
		message.setText(text);
		message.setFrom(SENDER);
		javaMailSender.getMailSender().send(message);
	}
	
	private String buildActivationUrl(String activationToken, String userName) {
		return BASE_URL + "login.html?name=" + userName + "&activationtoken=" + activationToken;
	}
	
	private String buildResetPasswordUrl(User user) {
		return BASE_URL + "resetpassword.html?name=" + user.getName() + "&forgotpasswordtoken=" + user.getResetPasswordToken();
	}
}
