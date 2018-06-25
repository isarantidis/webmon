package com.isarantidis.webmon.service.resultStore;

import java.time.Duration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isarantidis.webmon.service.common.CheckResult;

import reactor.core.Disposable;
import reactor.core.publisher.ConnectableFlux;

@Component
class CheckResultService {

	@Autowired
	private ConnectableFlux<CheckResult> checkStream;

	@Autowired
	private CheckResultRepository checkResultRepository;
	
	@Autowired
	private CheckStoreConfigurationProperties properties;

	private Disposable streamSubscription;

	@PostConstruct
	private void init() {
		streamSubscription = checkStream.autoConnect().window(Duration.ofMillis(properties.getWindow()))
				.flatMap(result -> result.collectList()).doOnNext(data -> checkResultRepository.saveAll(data))
				.subscribe();
	}
	
	@PreDestroy
	private void shutdown() {
		streamSubscription.dispose();
	}
}
