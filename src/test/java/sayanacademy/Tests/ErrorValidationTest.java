package sayanacademy.Tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import sayanacademy.TestComponents.BaseTest;
import sayanacademy.TestComponents.RetryFailedTest;

public class ErrorValidationTest extends BaseTest {
    
    @Test(groups={"ErrorHandling"}, retryAnalyzer= RetryFailedTest.class)
    public void errorValidation() throws IOException, InterruptedException {

        landingPage.loginApp("chhutkuritam@gmail.com","Iamsrk@1591");
        Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());   
    }

}
