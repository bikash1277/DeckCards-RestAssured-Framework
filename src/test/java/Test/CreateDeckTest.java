package Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.params.ParameterizedTest;

import TestBase.BaseClass;
import endPoints.EndPoints;
import io.restassured.http.ContentType;

class CreateDeckTest extends BaseClass {

    private static final Logger logger = LogManager.getLogger(CreateDeckTest.class);

    /**
	 * Testing for creating a new Deck with shuffle
	 */
	@Test
	@DisplayName("Creating a new Deck with shuffle")
	void testCreateDeckWithShuffle() {
		logger.info(" ------> Drawing A new deck with shuffle");
		given().log().all().accept(ContentType.JSON)
				.basePath(EndPoints.NEW.getEndPoint() + EndPoints.SHUFFLE.getEndPoint()).when().get().then()
				.assertThat().contentType("application/json").statusCode(RESPONSE_STATUS_CODE_200).body("success", equalTo(true))
				.body("deck_id", notNullValue()).body("remaining", equalTo(52)).body("shuffled", equalTo(true));

	}
	 /**
		 * Testing for drawing A new deck without shuffle
		 */
		@Test 
		@DisplayName("Draw A new deck without shuffle")
		void testCreateDeckWithoutShuffle() {
			logger.info(" ------> Drawing A new deck without shuffle");
			given().log().all().accept(ContentType.JSON).basePath(EndPoints.NEW.getEndPoint()).when().get().then()
					.assertThat().contentType("application/json").statusCode(RESPONSE_STATUS_CODE_200).body("success", equalTo(true))
					.body("deck_id", notNullValue()).body("remaining", equalTo(52)).body("shuffled", equalTo(false));

		}

		

		/**
		 * Testing for adding jokers to a new deck without shuffle
		 */
		@Test
		@DisplayName("Adding jokers to a new deck without shuffle")
		 void testCreateWithJokersWithoutShuffle() {
			logger.info(" ------> Adding jokers to a new deck without shuffle");
			given().log().all().accept(ContentType.JSON).basePath(EndPoints.NEW.getEndPoint())
					.queryParam("jokers_enabled", true).when().get().then().assertThat().contentType("application/json")
					.statusCode(RESPONSE_STATUS_CODE_200).body("success", equalTo(true)).body("deck_id", notNullValue())
					.body("remaining", equalTo(54)).body("shuffled", equalTo(false));
		}
		/**
		 * Testing for adding Jokers to new Deck with shuffle
		 */
		@Test
		@DisplayName("Adding Jokers to new Deck with shuffle")
		void testCreateWithJokersWithShuffle() {
			logger.info(" ------> Adding jokers to a new deck with shuffle");
			given().log().all().accept(ContentType.JSON)
					.basePath(EndPoints.NEW.getEndPoint() + EndPoints.SHUFFLE.getEndPoint())
					.queryParam("jokers_enabled", true).when().get().then().assertThat().contentType("application/json")
					.statusCode(RESPONSE_STATUS_CODE_200).body("success", equalTo(true)).body("deck_id", notNullValue())
					.body("remaining", equalTo(54)).body("shuffled", equalTo(true));
		}





}