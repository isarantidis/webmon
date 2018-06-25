package com.isarantidis.webmon.service.check;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("webchecks")
public class WebsiteCheckConfigurationProperties {
private List<CheckConfig> checks;
	
	private Long period;
	
	private Integer requestTimeout;
	
	private Integer concurrentRequests;
	
	public Integer getConcurrentRequests() {
		return concurrentRequests;
	}

	public void setConcurrentRequests(Integer concurrentRequests) {
		this.concurrentRequests = concurrentRequests;
	}

	public List<CheckConfig> getChecks() {
		return Collections.unmodifiableList(checks);
	}
	
	public void setChecks(List<CheckConfig> checks) {
		this.checks = checks;
	}

	public Long getPeriod() {
		return period;
	}

	public void setPeriod(Long period) {
		this.period = period;
	}

	public Integer getRequestTimeout() {
		return requestTimeout;
	}
	
	public void setRequestTimeout(Integer requestTimeout) {
		this.requestTimeout = requestTimeout;
	}
}
