package sayanacademy.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import sayanacademy.pageobjects.LandingPage;

public class BaseTest {

    public WebDriver driver;
    public LandingPage landingPage;

    public WebDriver initializeDriver() throws IOException
        {
            Properties prop= new Properties();
            FileInputStream fis= new FileInputStream (System.getProperty("user.dir")+"\\src\\main\\java\\sayanacademy\\Resources\\GlobalData.properties");
            prop.load(fis);
            
            String browserName= System.getProperty("browser")!=null? System.getProperty("browser"): prop.getProperty("browser");
            
            ChromeOptions op1 = new ChromeOptions();
            FirefoxOptions op2 = new FirefoxOptions();
            EdgeOptions op3=new EdgeOptions();
            
    
            if (browserName.contains("chrome")){
            	
                WebDriverManager.chromedriver().setup();
                
                if(prop.getProperty("headless").equalsIgnoreCase("true") || browserName.contains("headless"))
                {
                	op1.addArguments("headless");
                }
                
                driver = new ChromeDriver(op1);
            }
            
            else if (browserName.contains("firefox")){
            	
                WebDriverManager.firefoxdriver().setup();
                
                if(prop.getProperty("headless").equalsIgnoreCase("true") || browserName.contains("headless"))
                {
                	op2.addArguments("headless");
                }
                
                driver = new FirefoxDriver(op2);
            }
    
            else if (browserName.contains("edge")){
            	
                WebDriverManager.edgedriver().setup();
                
                if(prop.getProperty("headless").equalsIgnoreCase("true") || browserName.contains("headless"))
                {
                	op3.addArguments("--headless=new");
                }
                
                driver = new EdgeDriver(op3);
            }

            int implicitWaitTime = Integer.parseInt(prop.getProperty("implicitWait"));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitTime));
            driver.manage().window().maximize();
            return driver;
            
        }
    
    @BeforeMethod(alwaysRun=true)
    public LandingPage launchApplication() throws IOException{
        driver= initializeDriver();
        landingPage = new LandingPage(driver);
        landingPage.goTo();
        return landingPage;
    }

    public String getProductName() throws IOException{
        Properties prop= new Properties();
        FileInputStream fis= new FileInputStream (System.getProperty("user.dir")+"\\src\\main\\java\\sayanacademy\\Resources\\GlobalData.properties");
        prop.load(fis);
        return prop.getProperty("productName");
    }

    @AfterMethod(alwaysRun=true)
    public void tearDown(){
        driver.close();
    }
    
    //To take screenshot utility
    
    public String getScreenshot(String testCaseName,WebDriver driver) throws IOException {
    	TakesScreenshot ts = (TakesScreenshot) driver;
    	File source = ts.getScreenshotAs(OutputType.FILE);
    	File file = new File(System.getProperty("user.dir")+"\\reports\\"+ testCaseName + ".png");
    	FileUtils.copyFile(source, file);
    	return System.getProperty("user.dir")+"\\reports\\"+ testCaseName + ".png";
    }
    
    // JSON to HashMap Converter
    
    public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException
	{
		String jsonContent= FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
		
		ObjectMapper mapper = new ObjectMapper();
			
			List <HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>(){		
			});
			
			return data;
	}
    
    
}
