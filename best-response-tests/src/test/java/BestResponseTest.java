import com.tictactoebot.api.ProjectConfig;
import com.tictactoebot.api.services.TicTacToeService;
import com.tictactoebot.api.steps.TicTacToeSteps;
import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.aeonbits.owner.ConfigFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BestResponseTest {
    private TicTacToeService ticTacToeService = new TicTacToeService();
    private TicTacToeSteps ticTacToeSteps = new TicTacToeSteps();
    Logger logger = Logger.getLogger(BestResponseTest.class.getName());

    @BeforeClass
    public void setUp() {
        ProjectConfig config = ConfigFactory.create(ProjectConfig.class, System.getProperties());
        RestAssured.baseURI = config.baseUrl();
    }

    @DataProvider(name = "singleNumbers")
    public Object[][] createDataSingleNumbers() {
        return new Object[][]{{"1"}, {"3"}, {"5"}, {"7"}, {"9"}, {"2"}, {"4"}, {"6"}, {"8"}};
    }

    @DataProvider(name = "invalidNumbers")
    public Object[][] createDataInvalidNumbers() {
        return new Object[][]{{"0"}, {"test"}, {"%*"}, {"тест"}, {"%*"}, {"5555"}, {"2133"}, {"213546789"}
                , {"123456789"}};
    }

    @DataProvider(name = "edgeCase")
    public Object[][] createEdgeCasesUsage() {
        return new Object[][]{{"24"}, {"12"}, {"13"}, {"тест"}, {"%*"}, {"5555"}, {"2133"}, {"213546789"}
                , {"123456789"}};
    }

    @DataProvider(name = "winCase")
    public Object[][] createDataWinCasesUsage() {
        return new Object[][]{{"759"}};
    }

    @DataProvider(name = "cornerCase")
    public Object[][] createDataCornerCasesUsage() {
        return new Object[][]{{"51"}, {"45"}, {"15"}, {"24"}, {"78"}, {"14"}, {"864"}};
    }

    @Test(description = "Verify that best approach is applied for entered sequence for first move at {singleNumbers}",
            dataProvider = "singleNumbers")
    void verifyTicTacResponseForSingleEvent(String values) {
        int expectedResult = 5;
        String botResponse = ticTacToeService.insertValuesMove(values);
        logger.log(Level.INFO, botResponse + " response from bot after entering values " + values);
        if (values.contains("5")) {
            expectedResult = 1;
        }
        Assert.assertEquals(Integer.parseInt(botResponse), expectedResult,
                "Bot isn't chosen a valid strategy for single number, actual result is " + botResponse);

    }

    @Test(description = "Verify that bot returns zero if invalid numbers were entered {invalidNumbers}", dataProvider = "invalidNumbers")
    void verifyTicTacToeInvalid(String values) {
        ticTacToeService.insertValuesMove(values);
        String botResponse = ticTacToeService.insertValuesMove(values);
        logger.log(Level.INFO, botResponse + " response from bot after entering values " + values);
        Assert.assertEquals(Integer.parseInt(botResponse), 0,
                "Bot isn't chosen a valid strategy for single number, actual result is " + botResponse);
    }

    @Test(description = "Verify that bot returns corner value if one of them already exist in sequence {cornerCase}",
            dataProvider = "cornerCase")
    void verifyCornerValue(String values) {
        String botResponse = ticTacToeService.insertValuesMove(values);
        Assert.assertTrue(ticTacToeSteps.verifyThatEnteredSequenceContainCornerValue(Integer.parseInt(botResponse)),
                "Bot doesn't return corner value, actual value is" + botResponse);
    }


}
