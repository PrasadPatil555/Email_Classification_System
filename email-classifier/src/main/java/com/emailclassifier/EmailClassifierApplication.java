//package com.emailclassifier;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class EmailClassifierApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(EmailClassifierApplication.class, args);
//	}
//}
package com.emailclassifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class EmailClassifierApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailClassifierApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		// small timeouts so ML service failures don't hang your app
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(2_000);
		factory.setReadTimeout(5_000);
		return new RestTemplate(factory);
	}
}



//


