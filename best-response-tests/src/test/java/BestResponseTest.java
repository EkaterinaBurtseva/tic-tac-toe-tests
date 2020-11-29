import com.tictactoebot.api.ProjectConfig;
import com.tictactoebot.api.services.TicTacToeService;
import com.tictactoebot.api.steps.TicTacToeSteps;
import io.restassured.RestAssured;
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
        return new Object[][]{{"0"}, {"test"}, {"тест"}, {"%*"}, {"5555"}, {"2133"}, {"213546789"}, {"10"}
                , {"123456789"}};
    }

    @DataProvider(name = "edgeCase")
    public Object[][] createEdgeCasesUsage() {
        return new Object[][]{{"1379"}, {"159"}, {"1562"}, {"15937"}};
    }

    @DataProvider(name = "winBlockCase")
    public Object[][] createDataWinCasesUsage() {
        return new Object[][]{{"759"}, {"157"}};
    }

    @DataProvider(name = "centerCase")
    public Object[][] createDataCenterCasesUsage() {
        return new Object[][]{{"2486"}, {"4268"}, {"1486"}};
    }

    @DataProvider(name = "cornerCase")
    public Object[][] createDataCornerCasesUsage() {
        return new Object[][]{{"51"}, {"45"}, {"15"}, {"24"}, {"78"}, {"14"}, {"864"}, {"863"}, {"24863"}};
    }

    @Test(description = "Verify that best approach is applied for entered sequence for first move at {singleNumbers}",
            dataProvider = "singleNumbers")
    void verifyTicTacResponseForSingleEvent(String value) {
        int expectedResult = 5;
        String botResponse = ticTacToeService.insertValuesMove(value);
        logger.log(Level.INFO, botResponse + " response from bot after entering values " + value);
        if (value.contains("5")) {
            Assert.assertTrue(ticTacToeSteps.verifyThatEnteredSequenceContainCornerValue(Integer.parseInt(botResponse)),
                    "Bot doesn't return corner value, actual value is" + botResponse);
        } else {
            Assert.assertEquals(Integer.parseInt(botResponse), expectedResult,
                    "Bot isn't chosen a valid strategy for single number, actual result is " + botResponse);
        }

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

    @Test(description = "Verify if predefined move {winBlockCase} already contain two values from row bot returns not mentioned value from that row",
            dataProvider = "winBlockCase")
    void verifyWinCase(String move) {
        String botResponse = ticTacToeService.insertValuesMove(move);
        Assert.assertTrue(ticTacToeSteps.checkWhetherValuesExistInSomeRow(move).contains(botResponse),
                "Bot response doesn't match any value from mentioned row, actual result is " + botResponse);
    }

    @Test(description = "Verify if predefined move {centerCase} covered corner moves - bot response will be center",
            dataProvider = "centerCase")
    void verifyCenterCase(String move) {
        Assert.assertTrue(ticTacToeService.insertValuesMove(move).contains("5"),
                "Bot response doesn't equal to center position");
    }

    @Test(description = "Verify if predefined move {edgeCase} covered some edge case  - bot response will be edge as well",
            dataProvider = "edgeCase")
    void verifyEdgeCase(String move) {
        String botResponse = ticTacToeService.insertValuesMove(move);
        ticTacToeSteps.verifyThatEnteredSequenceContainEdgeValue(Integer.parseInt(botResponse));
        Assert.assertTrue(ticTacToeSteps.verifyThatEnteredSequenceContainEdgeValue(Integer.parseInt(botResponse)),
                "Bot response doesn't get edge case, actual result is " + botResponse);
    }

}
