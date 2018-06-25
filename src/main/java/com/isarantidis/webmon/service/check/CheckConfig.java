package com.isarantidis.webmon.service.check;

import java.net.URL;

public class CheckConfig {
	private URL url;
	
	private String contentCheck;
	
	public URL getUrl() {
		return url;
	}
	
	public String getContentCheck() {
		return contentCheck;
	}
	
	public void setUrl(URL url) {
		this.url = url;
	}

	public void setContentCheck(String contentCheck) {
		this.contentCheck = contentCheck;
	}

}
