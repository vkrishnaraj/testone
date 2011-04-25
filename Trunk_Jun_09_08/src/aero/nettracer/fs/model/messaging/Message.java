package aero.nettracer.fs.model.messaging;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
@Table(name="central_message")
public class Message implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static enum MessageContext {
		FraudScreening
	}

	public static enum MessageStatus {

	}

	@Id
	@GeneratedValue
	private long id;

	private long senderAgentId;
	private MessageContext messageContext;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Column(length=30)
	private String senderName;
	
	@Column(length=2500)
	private String message;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getSenderAgentId() {
		return senderAgentId;
	}

	public void setSenderAgentId(long senderAgentId) {
		this.senderAgentId = senderAgentId;
	}

	public MessageContext getMessageContext() {
		return messageContext;
	}

	public void setMessageContext(MessageContext messageContext) {
		this.messageContext = messageContext;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

}
