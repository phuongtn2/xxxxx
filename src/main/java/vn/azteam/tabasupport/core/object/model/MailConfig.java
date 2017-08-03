package vn.azteam.tabasupport.core.object.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.Errors;

import java.util.Properties;

/**
 * package vn.azteam.tabasupport.core.object.model
 * created 12/30/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseModel
 * @since 1.0.0
 */
public class MailConfig extends BaseModel {
	private long id;
	private String domain;
	private String incomingProtocol;
	private boolean incomingSslTls;
	private String incomingAddress;
	private int incomingPort;

	private String outgoingProtocol;
	private boolean outgoingSslTls;
	private String outgoingAddress;
	private int outgoingPort;

	public long getId() {
		return id;
	}

	public MailConfig setId(long id) {
		this.id = id;
		return this;
	}

	public String getDomain() {
		return domain;
	}

	public MailConfig setDomain(String domain) {
		this.domain = domain;
		return this;
	}

	public String getIncomingProtocol() {
		return incomingProtocol;
	}

	public MailConfig setIncomingProtocol(String incomingProtocol) {
		this.incomingProtocol = incomingProtocol;
		return this;
	}

	public boolean isIncomingSslTls() {
		return incomingSslTls;
	}

	public MailConfig setIncomingSslTls(boolean incomingSslTls) {
		this.incomingSslTls = incomingSslTls;
		return this;
	}

	public String getIncomingAddress() {
		return incomingAddress;
	}

	public MailConfig setIncomingAddress(String incomingAddress) {
		this.incomingAddress = incomingAddress;
		return this;
	}

	public int getIncomingPort() {
		return incomingPort;
	}

	public MailConfig setIncomingPort(int incomingPort) {
		this.incomingPort = incomingPort;
		return this;
	}

	public String getOutgoingProtocol() {
		return outgoingProtocol;
	}

	public MailConfig setOutgoingProtocol(String outgoingProtocol) {
		this.outgoingProtocol = outgoingProtocol;
		return this;
	}

	public boolean isOutgoingSslTls() {
		return outgoingSslTls;
	}

	public MailConfig setOutgoingSslTls(boolean outgoingSslTls) {
		this.outgoingSslTls = outgoingSslTls;
		return this;
	}

	public String getOutgoingAddress() {
		return outgoingAddress;
	}

	public MailConfig setOutgoingAddress(String outgoingAddress) {
		this.outgoingAddress = outgoingAddress;
		return this;
	}

	public int getOutgoingPort() {
		return outgoingPort;
	}

	public MailConfig setOutgoingPort(int outgoingPort) {
		this.outgoingPort = outgoingPort;
		return this;
	}

	@JsonIgnore
	public Properties getOutgoingJavaMailProperties() {
		final Properties properties = new Properties();
		properties.put("mail.smtp.auth", isOutgoingSslTls());
		properties.put("mail.smtp.starttls.enable", true);
		return properties;
	}

	/**
	 * Get mail sender by current mail config
	 *
	 * @param username a {@link String}
	 * @param password a {@link String}
	 * @return a {@link JavaMailSenderImpl} object
	 */
	@JsonIgnore
	public JavaMailSenderImpl getMailSender(String username, String password) {
		final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setDefaultEncoding("UTF-8");
		mailSender.setHost(getOutgoingAddress());
		mailSender.setPort(getOutgoingPort());
		mailSender.setProtocol(getOutgoingProtocol());
		mailSender.setUsername(username);
		mailSender.setPassword(password);

		mailSender.setJavaMailProperties(getOutgoingJavaMailProperties());

		return mailSender;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return this.getClass().equals(clazz);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(Object target, Errors errors) {

	}

	@Override
	public String toString() {
		return "MailConfig{" +
				"domain='" + domain + '\'' +
				", incomingProtocol='" + incomingProtocol + '\'' +
				", incomingAddress='" + incomingAddress + '\'' +
				", incomingPort='" + incomingPort + '\'' +
				", outgoingProtocol='" + outgoingProtocol + '\'' +
				", outgoingAddress='" + outgoingAddress + '\'' +
				", outgoingPort='" + outgoingPort + '\'' +
				'}';
	}
}
