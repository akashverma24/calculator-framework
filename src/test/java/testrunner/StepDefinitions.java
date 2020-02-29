package testrunner;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testrunner.Hooks;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StepDefinitions {


    public WebDriver driver;
    public WebDriverWait wait;
    WebElement app;
    //Properties prop = new Properties();
    WebElement select_frame;
    String number1, number2;
    public StepDefinitions(){
        driver = Hooks.driver;
        wait = new WebDriverWait(driver, 20);

    }

    @Given("User is present on web url {string}")
    public void user_is_present_on_web_url(String string) {
        driver.get(string);
    }

    @Then("Verify the title of the page")
    public void verify_the_title_of_the_page() {

        Assert.assertEquals(driver.getTitle(),"Full Screen Calculator - Online Calculator");
    }

    @When("User input the {string}")
    public void user_input_the(String string) {

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("fullframe")));
        select_frame = driver.findElement(By.id("fullframe"));
        driver.switchTo().frame(select_frame);

        actionsClassWithoutShift(string);

        driver.switchTo().defaultContent();

    }

    @When("press {string} button")
    public void press_button(String string) throws InterruptedException {

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("fullframe")));
        select_frame = driver.findElement(By.id("fullframe"));
        driver.switchTo().frame(select_frame);

        if (string.equals("YES")){
            actionsClassWithShift("3");
        }

        else if(string.equals("Clear")){
            actionsClassWithoutShift("c");
            clickScreenshot();
            Thread.sleep(3000);
        }
        else if(string.equals("Division")){
            actionsClassWithoutShift("/");
        }
        else if (string.equals("Enter")){
            actionsClassWithoutShift("=");
            clickScreenshot();
            Thread.sleep(3000);
        }
        else if (string.equals("Addition")){
            actionsClassWithShift("=");
        }

        else if (string.equals("Subtract")){
            actionsClassWithoutShift("-");
        }

        driver.switchTo().defaultContent();
    }


    @Then("value should be reset to zero")
    public void value_should_be_reset_to_zero() throws IOException, InterruptedException {

        Assert.assertEquals(onlineOCR(), "");
        Thread.sleep(3000);

    }

    @When("User input the {string} and {string}")
    public void user_input_the_and(String string, String string2) {


        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("fullframe")));
        select_frame = driver.findElement(By.id("fullframe"));
        driver.switchTo().frame(select_frame);

        actionsClassWithoutShift(string);

        if (string2.equals("YES")){
            actionsClassWithShift("3");
        }
        else if (string2.equals("NO")){
            System.out.println("Positive Number= "+string);
        }

        driver.switchTo().defaultContent();
    }

    @Then("calculator value should be equal {string} value")
    public void calculator_value_should_be_equal_value(String string) throws IOException, InterruptedException {

        Assert.assertEquals(string, onlineOCR());
        Thread.sleep(3000);

    }



    public void actionsClassWithoutShift(String string){

        Actions builder = new Actions(driver);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("canvas")));
        app = driver.findElement(By.id("canvas"));
        Action operation = builder.sendKeys(app,string).build();
        operation.perform();



    }

    public void actionsClassWithShift(String string){

        Actions builder = new Actions(driver);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("canvas")));
        app = driver.findElement(By.id("canvas"));
        Action operation = builder.keyDown(app, Keys.SHIFT).sendKeys(app, string).keyUp(app, Keys.SHIFT).build();
        operation.perform();

    }

    public void clickScreenshot(){

        File  screenshot = app.getScreenshotAs(OutputType.FILE);
        BufferedImage bufferedImage = null;

        try {
            bufferedImage = ImageIO.read(screenshot);
            BufferedImage desiredSize;
            File pathToImg;
            assert bufferedImage != null;
            desiredSize = bufferedImage.getSubimage(10, 10, 200, 35);
            pathToImg = new File("evidence/calculator.png");
            ImageIO.write(desiredSize, "png", screenshot);
            FileHandler.copy(screenshot, pathToImg);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String onlineOCR() throws IOException {

        // Free 500 requests per day
        // apiKey: 0695ba6f6788957
        // TO-DO: dynamic function for OCR

        System.out.println("********** Image Base64 Value");
        System.out.println(encodeFileToBase64());
        System.out.println("********** END ***********");

        String base64Image = "data:image/jpeg;base64," + encodeFileToBase64();
        String response_value = "";

        HttpPost post = new HttpPost("https://api.ocr.space/parse/image");
        // add request parameter, form parameters
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("apikey", "0695ba6f6788957"));
        urlParameters.add(new BasicNameValuePair("language", "eng"));
        urlParameters.add(new BasicNameValuePair("isOverlayRequired", "false"));
        urlParameters.add(new BasicNameValuePair("base64Image", base64Image));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {


            String res = EntityUtils.toString(response.getEntity());
            System.out.println("******  API Response **** ");
            System.out.println(res);
            System.out.println("******** End *******");

            Pattern p = Pattern.compile("((?<=\"ParsedText\":\").*?(?=\\\\r\\\\n\"))");
            Matcher m = p.matcher(res);

            if (m.find()) {
                response_value = m.group(1);
            }

        }
        return response_value;
    }

    public String encodeFileToBase64() throws IOException {

        File f =  new File("evidence/calculator.png");
        FileInputStream fileInputStreamReader = new FileInputStream(f);
        byte[] bytes = new byte[(int)f.length()];
        fileInputStreamReader.read(bytes);

        return new String(Base64.encodeBase64(bytes), StandardCharsets.UTF_8);

    }






}
