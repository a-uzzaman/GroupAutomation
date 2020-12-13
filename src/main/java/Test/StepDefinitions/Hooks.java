package Test.StepDefinitions;

import Test.Base.BaseUtil;
import com.google.common.base.Strings;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Hooks extends BaseUtil {
    public static WebDriver driver;

    String defaultBrowser = "ch";
    String defaultEnv = "prod";
    String url;
    String baseURL =  System.getProperty ("env");
    String browserType = System.getProperty("browser");


    @Before
    public void openBrowser(){
        if (Strings.isNullOrEmpty(browserType)){
            browserType = defaultBrowser;
        }
        if (Strings.isNullOrEmpty(baseURL)){
            baseURL = defaultEnv;
        }
        driver = initDriver (browserType);
        switch(baseURL) {
            case "qa":
                url = "http://qa.taltektc.com/";
                break;
            case "dev":
                url = "";
                break;
            case "prod" :
                url = "http://stage.taltektc.com/";
                break;
        }
        driver.get(url);
    }

    @After
    public void tearDown(Scenario scenario){
        try {
            if (scenario.isFailed()) {
                final byte[] screenshot = ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", scenario.getName());
            }
        } finally {
            System.out.println("closing the browser...");
            System.out.println("=============================");
            driver.quit();
        }
    }
}
