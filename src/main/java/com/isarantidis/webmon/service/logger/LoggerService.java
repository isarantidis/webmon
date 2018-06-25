package com.isarantidis.webmon.service.logger;

import java.time.Duration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isarantidis.webmon.service.common.CheckResult;

import reactor.core.Disposable;
import reactor.core.publisher.ConnectableFlux;

@Component
class LoggerService {

	private static final Logger LOG = LoggerFactory.getLogger(LoggerService.class);

	@Autowired
	private ConnectableFlux<CheckResult> checkStream;

	@Autowired
	private LoggerConfigurationProperties properties;

	private Disposable streamSubscription;

	@PostConstruct
	private void init() {
		streamSubscription = checkStream.autoConnect().window(Duration.ofMillis(properties.getWindow()))
				.flatMap(result -> result.collectList())
				.doOnNext(data -> data.stream().forEach(checkResult -> LOG.info(
						"Checked URL {} at time {}. The result was {}. It took {} milliseconds to retrieve full response",
						checkResult.getUrl(), checkResult.getTimeChecked(), checkResult.getCode(), checkResult.getTimeElapsed())))
				.subscribe();
	}

	@PreDestroy
	private void shutdown() {
		streamSubscription.dispose();
	}
}
