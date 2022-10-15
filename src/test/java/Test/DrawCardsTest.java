package Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import TestBase.BaseClass;
import endPoints.EndPoints;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import page.Draw;

 class DrawCardsTest extends BaseClass {

	private static final Logger logger = LogManager.getLogger(DrawCardsTest.class);

	/**
	 * Testing for draw a card from new deck without shuffle
	 */
	@Test
	@DisplayName("Draw a card from new deck without shuffle")
	void testDrawOneCardFromDeckWithoutSuffle() {
		logger.info(" ------> Adding jokers to a new deck without shuffle");
		Response response = given().log().all().accept(ContentType.JSON).basePath(EndPoints.NEW.getEndPoint()).when()
				.get();
		Assertions.assertEquals(RESPONSE_STATUS_CODE_200, response.getStatusCode());
		String deckId = response.jsonPath().getString("deck_id");
		Assertions.assertNotNull(deckId);

		// Drawing a new deck card
		given().log().all().accept(ContentType.JSON)
				.basePath(EndPoints.DECKID.getEndPoint() + EndPoints.DRAW.getEndPoint()).pathParam("deck_id", deckId)
				.when().get().then().statusCode(RESPONSE_STATUS_CODE_200).body("success", equalTo(true))
				.body("deck_id", equalTo(deckId)).body("cards[0].code", equalTo("AS"))
				.body("cards[0].value", equalTo("ACE")).body("cards[0].suit", equalTo("SPADES"))
				.body("remaining", equalTo(51));
	}

	/**
	 * Testing for draw multiple cards from a new deck of cards without shuffle
	 */
	@Test
	@DisplayName("Draw multiple cards from a new deck of cards without shuffle")
	void testDrawMultipleCardFromDeckWithoutSuffle() {
		logger.info(" ------> drawing multiple cards from a new deck of cards without shuffle");
		Response response = given().log().all().accept(ContentType.JSON).basePath(EndPoints.NEW.getEndPoint()).when()
				.get();
		Assertions.assertEquals(RESPONSE_STATUS_CODE_200, response.getStatusCode());
		String deckId = response.jsonPath().getString("deck_id");
		Assertions.assertNotNull(deckId);

		// Drawing a new deck card
		given().log().all().accept(ContentType.JSON)
				.basePath(EndPoints.DECKID.getEndPoint() + EndPoints.DRAW.getEndPoint()).pathParam("deck_id", deckId)
				.param("count", 2).when().get().then().statusCode(200).body("success", equalTo(true))
				.body("deck_id", equalTo(deckId)).body("cards[0].code", equalTo("AS"))
				.body("cards[0].value", equalTo("ACE")).body("cards[0].suit", equalTo("SPADES"))
				.body("cards[1].code", equalTo("2S")).body("cards[1].value", equalTo("2"))
				.body("cards[1].suit", equalTo("SPADES")).body("remaining", equalTo(50));
	}

	/**
	 * Testing for draw a card from new deck with shuffle
	 */
	@Test
	@DisplayName("Draw a card from new deck with shuffle")
	void testDrawOneCardFromDeckWithSuffle() {
		logger.info(" ------> drawing a cards from a new deck of cards with shuffle");
		Response response = given().log().all().accept(ContentType.JSON)
				.basePath(EndPoints.NEW.getEndPoint() + EndPoints.SHUFFLE.getEndPoint()).when().get();
		Assertions.assertEquals(RESPONSE_STATUS_CODE_200, response.getStatusCode());
		String deckId = response.jsonPath().getString("deck_id");
		Assertions.assertNotNull(deckId);

		// Drawing a new deck card
		given().log().all().accept(ContentType.JSON)
				.basePath(EndPoints.DECKID.getEndPoint() + EndPoints.DRAW.getEndPoint()).pathParam("deck_id", deckId)
				.when().get().then().statusCode(RESPONSE_STATUS_CODE_200).body("success", equalTo(true))
				.body("deck_id", equalTo(deckId)).body("cards", hasSize(1)).body("cards[0].code", notNullValue())
				.body("cards[0].value", notNullValue()).body("cards[0].suit", notNullValue())
				.body("remaining", equalTo(51));
	}

	/**
	 * Drawing multiple cards from a new deck of cards with shuffle
	 */
	@Test
	@DisplayName("Draw multiple cards from a new deck of cards with shuffle")
	void testDrawMultipleCardFromDeckWithSuffle() {
		logger.info(" ------> drawing a cards from a new deck of cards without shuffle");
		Response response = given().log().all().accept(ContentType.JSON)
				.basePath(EndPoints.NEW.getEndPoint() + EndPoints.SHUFFLE.getEndPoint()).when().get();
		Assertions.assertEquals(RESPONSE_STATUS_CODE_200, response.getStatusCode());
		String deckId = response.jsonPath().getString("deck_id");
		Assertions.assertNotNull(deckId);

		// Drawing a new deck card

		given().log().all().accept(ContentType.JSON)
				.basePath(EndPoints.DECKID.getEndPoint() + EndPoints.DRAW.getEndPoint()).pathParam("deck_id", deckId)
				.param("count", 2).when().get().then().statusCode(RESPONSE_STATUS_CODE_200)
				.body("success", equalTo(true)).body("deck_id", equalTo(deckId)).body("cards", hasSize(2))
				.body("cards[0].code", notNullValue()).body("cards[0].value", notNullValue())
				.body("cards[0].suit", notNullValue()).body("cards[1].code", notNullValue())
				.body("cards[1].value", notNullValue()).body("cards[1].suit", notNullValue())
				.body("remaining", equalTo(50));
	}

	/**
	 * Drawing 5 cards verify that Success is True verify remaining cards are 47
	 */

	@Test
	@DisplayName("Draw 5 cards , Validate remaining are 47 ")
	void drawFiveCards() {

		logger.info(" ------> Drawing 5 cards");
		Response response = given().log().all().accept(ContentType.JSON)
				.basePath(EndPoints.NEW.getEndPoint() + EndPoints.DRAW.getEndPoint()).queryParam("count", 5).when()
				.get();

		logger.info(" ------> Deserialize JSON into Page");

		Draw draw = response.jsonPath().getObject("", Draw.class);

		Assertions.assertEquals(response.statusCode(), RESPONSE_STATUS_CODE_200);
		Assertions.assertTrue(draw.isSuccess());
		Assertions.assertEquals(draw.getRemaining(), 47);
		Assertions.assertEquals(response.statusCode(), RESPONSE_STATUS_CODE_200);
		Assertions.assertTrue(draw.isSuccess());
		Assertions.assertEquals(draw.getRemaining(), 47);

	}

	/**
	 * Drawing more than 52 cards without jokers verify that Success is False verify
	 */
	@Test
	@DisplayName("Draw more than 52 without jokers, validate error message")
	void drawFiftyThreeCards() {
		logger.info(" ------> Drawing 53 cards");
		Response response = given().log().all().accept(ContentType.JSON)
				.basePath(EndPoints.NEW.getEndPoint() + EndPoints.DRAW.getEndPoint()).queryParam("count", 53).when()
				.get();

		Draw draw = response.jsonPath().getObject("", Draw.class);
		logger.info(" ------> Validating response");
		Assertions.assertEquals(response.statusCode(), RESPONSE_STATUS_CODE_200);
		Assertions.assertFalse(draw.isSuccess());
		Assertions.assertEquals(draw.getRemaining(), 0);
		Assertions.assertEquals(draw.getError(), "Not enough cards remaining to draw 53 additional");
	}

	/**
	 * passing decimal value in query Parameters 5.5 test Will Fail
	 */
	@Test
	@DisplayName("Drawing 5,5 cards from the deck")
	void DoubleValueQueyParam() {

		logger.info(" ------> Drawing 5.5 cards ");
		try {
			given().log().all().accept(ContentType.JSON)
					.basePath(EndPoints.NEW.getEndPoint() + EndPoints.DRAW.getEndPoint()).queryParam("count", 5.5)
					.when().get();
		} catch (Exception e) {
			logger.info(" ------> An Exception has occurred " + e);
			Assertions.fail("Incorrect query parameters");
		}
	}

}
