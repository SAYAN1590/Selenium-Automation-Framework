package sayanacademy.Tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import sayanacademy.TestComponents.BaseTest;
import sayanacademy.TestComponents.RetryFailedTest;

public class ErrorValidationTest extends BaseTest {
    
    @Test(groups={"ErrorHandling"}, retryAnalyzer= RetryFailedTest.class, dataProvider = "getErrorData")
    public void errorValidation(HashMap<String, String> input) throws IOException, InterruptedException {

        landingPage.loginApp(input.get("email"),input.get("password"));
        Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());   
    }
    
    @DataProvider
	public Object[][] getErrorData() throws IOException {
	    	
	    	
	    	List <HashMap<String, String>>data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\sayanacademy\\data\\ErrorLoginData.json");
	    	 
	    	return new Object[][] {{data.get(0)},{data.get(1)}};
    	
    }

}
