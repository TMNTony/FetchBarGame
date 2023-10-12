import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AlgorithmTest {
    private WebDriver driver;

    @Before
    public void setUp() {
        // Set up WebDriver
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://sdetchallenge.fetch.com/");
    }



    @Test
    public void resetTest() {
        // Fill in left side of sheet
        fillSheet(driver, "left");

        // Click on "Reset" button
        clickReset(driver);

    }

    @Test
    public void weighButtonTest() {
        //Fill left side of sheet
        fillSheet(driver, "left");
        //Click weigh button
        clickWeigh(driver);
        //Delay result
        delayResult();
        //Get result and assert the correct comparator sign
        String result = getResult(driver);
        assertTrue(result.contains(">"));
    }

    @Test
    public void fillTest() {
        //Fill Left Sheet
        fillSheet(driver, "left");
        //Assert that values match
        for (int i = 0; i < 9; i++) {
            WebElement bowlInput = driver.findElement(By.id("left_" + i));
            int actualValue = Integer.parseInt(bowlInput.getAttribute("value"));
            assertEquals(i, actualValue);
        }
        clickReset(driver);
        fillSheet(driver, "right");
        for (int i = 0; i < 9; i++) {
            WebElement bowlInput = driver.findElement(By.id("right_" + i));
            int actualValue = Integer.parseInt(bowlInput.getAttribute("value"));
            assertEquals(i, actualValue);
        }
    }

    @Test
    public void getResultsTest(){
        fillSheet(driver, "left");
        clickWeigh(driver);
        delayResult();
        String[] weightings = getWeighings();
        assertEquals(1, weightings.length);
    }
    @Test
    public void fetchAlgorithmTest() {
        //Compare sets
        int[] result = compareSets(driver);
        //Compare returned set
        int answer = compareSet(driver, result);
        //Click answer
        clickAnswer(answer);

        //Get alert message;
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();

        // Close the alert
        alert.accept();

        //Assert alert message indicates correct answer
        String expectedAlertText = "Yay! You find it!";
        assertEquals(expectedAlertText, alertText);

        //Assert that answer was found after 2 weightings
        assertEquals(2, getWeighings().length);
    }


    @After
    public void tearDown() {
        // Clean up and close the WebDriver
        if (driver != null) {
            driver.quit();
        }
    }

    private void clickWeigh(WebDriver driver) {
        //Get and click weigh button
        WebElement weighButton = driver.findElement(By.id("weigh"));
        weighButton.click();
    }

    public static void clickReset(WebDriver driver) {
        //Get and click reset button
        List<WebElement> resetButtons = driver.findElements(By.id("reset"));
        for (WebElement resetButton : resetButtons) {
            if (resetButton.isEnabled()) {
                resetButton.click();
            }
        }
    }

    public static String getResult(WebDriver driver) {
        String result = null;
        //Find result button that is not enabled
        List<WebElement> resetButtons = driver.findElements(By.id("reset"));
        for (WebElement resetButton : resetButtons) {
            if (!resetButton.isEnabled()) {
                result = resetButton.getText();
            }
        }
        return result;
    }

    public static void fillSheet(WebDriver driver, String side) {
        //Fill left side of sheet for testing0
        for (int i = 0; i <= 8; i++) {
            WebElement bowlInput = driver.findElement(By.id(side + "_" + i)); // Replace with actual IDs
            bowlInput.sendKeys(Integer.toString(i));
        }
    }

    public int[] compareSets(WebDriver driver) {
        int[] lightestSet;
        //Separate into 3 equal sets of three bars
        for (int i = 0; i < 3; i++) {
            WebElement bowlInput = driver.findElement(By.id("left_" + i));
            bowlInput.sendKeys(Integer.toString(i));
        }
        for (int i = 3; i < 6; i++) {
            WebElement bowlInput = driver.findElement(By.id("right_" + i));
            bowlInput.sendKeys(Integer.toString(i));
        }
        //Weigh bars
        clickWeigh(driver);
        // Add a delay
        delayResult();
        //Get result
        String result = getResult(driver);
        //Find the lightest of the 3 sets
        if (result.equals("<")) {
            lightestSet = new int[]{0, 1, 2};
        } else if (result.equals(">")) {
            lightestSet = new int[]{3, 4, 5};
        } else {
            lightestSet = new int[]{6, 7, 8};
        }

        clickReset(driver);
        return lightestSet;
    }
    public void delayResult(){
        try {
            // Wait for 5 seconds
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public int compareSet(WebDriver driver, int[] set){
        String result = null;
        int answer;
        //Input first 2 numbers in set to right and left fields
        WebElement bowlInputLeft = driver.findElement(By.id("left_0"));
        bowlInputLeft.sendKeys(Integer.toString(set[0]));

        WebElement bowlInputRight = driver.findElement(By.id("right_0"));
        bowlInputRight.sendKeys(Integer.toString(set[1]));

        clickWeigh(driver);
        delayResult();
        //Find the smallest bar based on comparison
        result = getResult(driver);
        if (result.equals("<")){
            answer = set[0];
        } else if (result.equals(">")) {
            answer = set[1];
        } else {
            answer = set[2];
        }
        return answer;
    }
    public void clickAnswer(int answer){
        //Find and click answer
        WebElement answerButton = driver.findElement(By.id("coin_" + answer));
        answerButton.click();
    }
    public String[] getWeighings(){
        WebElement gameBoard = driver.findElement(By.className("game-info"));
        List<WebElement> weighings = gameBoard.findElements(By.tagName("li"));

        String[] listWeighings = new String[weighings.size()];

        for (int i = 0; i < weighings.size(); i++) {
            listWeighings[i] = weighings.get(i).getText();
        }

        return listWeighings;
    }
}


