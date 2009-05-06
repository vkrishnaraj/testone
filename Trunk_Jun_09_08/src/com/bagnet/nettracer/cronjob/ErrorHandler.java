package com.bagnet.nettracer.cronjob;

public interface ErrorHandler {

	public abstract void sendEmail(String message, Exception e, boolean fatal, boolean hibernateError);

	public abstract void sendEmail(String message, boolean fatal, boolean hibernateError);

}