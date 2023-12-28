package com.example.springboot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FeatureControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate template;

	@Autowired
	private FeatureController featureController;

	@BeforeEach
	void setUp() {
		featureController.clearFeatureSwitches();  // Clear the feature switches before each test
	}

	@Test
	void getFeatureRequestOnEmptyDatabaseReturnsFeatureStatusFalse() {
		String url = "http://localhost:" + port + "/feature?email=user@example.com&featureName=feature1";
		ResponseEntity<String> responseEntity = template.getForEntity(url, String.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("{\"canAccess\":false}", responseEntity.getBody());
	}

	@Test
	void getFeatureRequestWithPopulatedDatabaseReturnsFeatureStatusTrue() {
		FeatureRequest postRequest = new FeatureRequest("feature1", "user@example.com", true);
		String postUrl = "http://localhost:" + port + "/feature";
		template.postForEntity(postUrl, postRequest, String.class);

		String getUrl = "http://localhost:" + port + "/feature?email=user@example.com&featureName=feature1";
		ResponseEntity<String> getResponseEntity = template.getForEntity(getUrl, String.class);

		assertEquals(HttpStatus.OK, getResponseEntity.getStatusCode());
		assertEquals("{\"canAccess\":true}", getResponseEntity.getBody());
	}



}
