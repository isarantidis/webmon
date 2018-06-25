package com.isarantidis.webmon.service.http;

import java.time.Duration;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isarantidis.webmon.service.common.CheckResult;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

@RestController
class HttpStreamingService {

	@Autowired
	private ConnectableFlux<CheckResult> checkStream;
	private Flux<List<CheckResult>> windowStream;
	private Flux<CheckResult> singleStream;
	
	@PostConstruct
	private void init() {
		windowStream = checkStream.autoConnect().window(Duration.ofSeconds(20)).flatMap(result -> result.collectList());
		singleStream = checkStream.autoConnect();
	}
	
	@GetMapping(path="/single", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<CheckResult> singleItem() {
		return singleStream;
	}
	
	@GetMapping(path="/window", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<List<CheckResult>> windowed() {
		return windowStream;
	}
}
