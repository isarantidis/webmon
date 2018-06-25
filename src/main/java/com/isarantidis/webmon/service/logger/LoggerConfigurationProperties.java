package com.isarantidis.webmon.service.logger;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("logger")
class LoggerConfigurationProperties {

	private Long window;

	public Long getWindow() {
		return window;
	}

	public void setWindow(Long window) {
		this.window = window;
	}
}
