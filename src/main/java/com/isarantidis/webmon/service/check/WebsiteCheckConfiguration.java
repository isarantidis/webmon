package com.isarantidis.webmon.service.check;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Configuration
public class WebsiteCheckConfiguration {

	@Autowired
	private WebsiteCheckConfigurationProperties properties;
	
	@Bean("parallelRequestWorkers")
	public Scheduler parallelRequestWorkers() {
		return Schedulers.newParallel("requests", properties.getConcurrentRequests());
	}
	
	@Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) 
    {
        return restTemplateBuilder
           .setConnectTimeout(properties.getRequestTimeout())
           .setReadTimeout(properties.getRequestTimeout())
           .build();
    }

	@PreDestroy
	public void shutdown() {
		parallelRequestWorkers().dispose();
	}
}
