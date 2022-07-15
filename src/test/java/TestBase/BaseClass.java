package TestBase;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import utilities.ConfigReader;

import static io.restassured.RestAssured.baseURI;

public class BaseClass {

    private static final Logger logger = LogManager.getLogger(BaseClass .class);
    public static int RESPONSE_STATUS_CODE_200 = 200;

    @BeforeAll
    public static void basURISetUp() {
        baseURI = ConfigReader.getProperty("deck.uri");
        logger.info("BaseURI :: " + ConfigReader.getProperty("deck.uri"));
    }
}
