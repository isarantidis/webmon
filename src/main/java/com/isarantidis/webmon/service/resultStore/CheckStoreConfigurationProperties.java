package com.isarantidis.webmon.service.resultStore;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("check-store")
class CheckStoreConfigurationProperties {
	private Long window;

	public Long getWindow() {
		return window;
	}

	public void setWindow(Long window) {
		this.window = window;
	}
}
