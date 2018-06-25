package com.isarantidis.webmon.service.common;

import java.net.URL;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CheckResult {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private long id;
	
	private URL url;
	
	private Long timeElapsed;
	
	private long timeChecked;
	
	private ResultCode code;
	
	CheckResult() {
	}
	
	
	public static CheckResult create(URL url, Long timeElapsed, long timeChecked, ResultCode code) {
		CheckResult result = new CheckResult();
		result.url = url;
		result.timeElapsed = timeElapsed;
		result.timeChecked = timeChecked;
		result.code = code;
		return result;
	}

	public URL getUrl() {
		return url;
	}

	public long getTimeChecked() {
		return timeChecked;
	}

	public ResultCode getCode() {
		return code;
	}

	public Long getTimeElapsed() {
		return timeElapsed;
	}

}
