package dev.flamenbaum.automated;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.flamenbaum.automated.account.repository.AccountRepository;
import dev.flamenbaum.automated.transaction.repository.TransactionRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;

import java.util.Map;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources")
@CucumberContextConfiguration
@SpringBootTest(classes = AutomatedApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class AutomatedApplicationTests<T> {

	@Autowired
	protected RestTemplate restTemplate;

	@Autowired
	protected AccountRepository accountRepository;

	@Autowired
	protected TransactionRepository transactionRepository;

	protected int status;

	protected T respBody;

	protected void executePost(String url, Object body, Class<T> clazz) {

		try{
			ResponseEntity<T> resp = restTemplate
					.exchange(url, HttpMethod.POST, new HttpEntity<>(body), clazz);

			respBody = resp.getBody();
			status = resp.getStatusCode().value();
		} catch (HttpClientErrorException ex) {
			status = ex.getStatusCode().value();
		}
	}

	protected void executeGet(String url, Long id) {

		try{
			ResponseEntity<Object> resp = restTemplate
					.exchange(url, HttpMethod.GET,
							new HttpEntity<>(new HttpHeaders()), Object.class, Map.of("id", id));

			status = resp.getStatusCode().value();
		} catch (HttpClientErrorException ex) {
			status = ex.getStatusCode().value();
		}
	}


}
