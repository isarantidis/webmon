package com.isarantidis.webmon.service.check;

import java.net.SocketTimeoutException;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.isarantidis.webmon.service.common.CheckResult;
import com.isarantidis.webmon.service.common.ResultCode;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;

@Service
public class WebsiteCheckService {

	@Autowired
	@Qualifier("parallelRequestWorkers")
	private Scheduler parallelRequestWorkers;

	@Autowired
	private RestTemplate httpRest;

	@Autowired
	private WebsiteCheckConfigurationProperties checkConfigStore;

	@Bean
	public ConnectableFlux<CheckResult> checkStream() {
		return ConnectableFlux.from(Flux.fromIterable(checkConfigStore.getChecks()).parallel()
				.runOn(parallelRequestWorkers).map(config -> {
					long timeChecked = System.currentTimeMillis();
					CheckResult checkResult = null;
					try {
						ResponseEntity<String> result = httpRest.getForEntity(config.getUrl().toURI(), String.class);
						long timeReceived = System.currentTimeMillis();
						if (result.getStatusCode() == HttpStatus.OK) {
							if (result.getBody().contains(config.getContentCheck())) {
								checkResult = CheckResult.create(config.getUrl(), timeReceived - timeChecked,
										timeChecked, ResultCode.SUCCESS);
							} else {
								checkResult = CheckResult.create(config.getUrl(), timeReceived - timeChecked,
										timeChecked, ResultCode.FAILURE_CONTENT_CHECK_FAILED);
							}
						} else {
							checkResult = CheckResult.create(config.getUrl(), null, timeChecked,
									ResultCode.FAILURE_STATUS_CODE);
						}
					} catch (Exception e) {
						if (e instanceof ResourceAccessException && e.getCause() instanceof SocketTimeoutException) {
							checkResult = CheckResult.create(config.getUrl(), null, timeChecked,
									ResultCode.FAILURE_SOCKET_TIMEOUT);
						} else {
							checkResult = CheckResult.create(config.getUrl(), null, timeChecked,
									ResultCode.FAILURE_GENERIC_ERROR);
						}
					}
					return checkResult;
				})).delaySequence(Duration.ofMillis(checkConfigStore.getPeriod())).repeat().publish();
	}

}
