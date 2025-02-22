package sayanacademy.Tests;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import sayanacademy.TestComponents.BaseTest;
import sayanacademy.pageobjects.CartPage;
import sayanacademy.pageobjects.CheckoutPage;
import sayanacademy.pageobjects.ConfirmationPage;
import sayanacademy.pageobjects.OrderPage;
import sayanacademy.pageobjects.ProductCatalogue;

public class SubmitOrderTest extends BaseTest{

    String productName;

    public SubmitOrderTest() throws IOException {
        super();
        productName = getProductName();
    }
    
    
    @Test(dataProvider="getData", groups={"Purchase"})
    public void submitOrder(HashMap<String, String> input) throws IOException, InterruptedException {

        
        //Login Function
        ProductCatalogue productCatalogue = landingPage.loginApp(input.get("email"),input.get("password"));
        productCatalogue.addProductToCart(input.get("product"));

        //Go to Cart section
        CartPage cartPage = productCatalogue.goToCartPage();

        Boolean match =cartPage.VerifyProductDisplay(input.get("product"));
        
        Assert.assertTrue(match);

        CheckoutPage checkoutPage= cartPage.goToCheckout();

        //Checkout Page validation

        checkoutPage.selectCountry("india");

        ConfirmationPage confirmationPage = checkoutPage.submitOrder();

        String confirmationMessage = confirmationPage.getConfirmationMessage();
       
        Assert.assertTrue(confirmationMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
        
    }

    @Test(dependsOnMethods = {"submitOrder"})
    public void OrderHistoryTest() throws IOException, InterruptedException {
        ProductCatalogue productCatalogue = landingPage.loginApp("chhutkuritam@gmail.com","Iamsrk@1590");
        OrderPage orderspage= productCatalogue.goToOrdersPage();
        Assert.assertTrue(orderspage.VerifyOrderDisplay(getProductName()));
    }
    
    @DataProvider
    public Object[][] getData() throws IOException {
    	
//    	HashMap<String, String> map = new HashMap<String,String>();
//    	map.put("email", "chhutkuritam@gmail.com");
//    	map.put("password", "Iamsrk@1590");
//    	
//    	HashMap<String, String> map1= new HashMap<String,String>();
//    	map1.put("email", "iocl@gmail.com");
//    	map1.put("password", "Iocl@1590");
//    	
//    	return new Object [][] {{map},{map1}};
    	
    	List <HashMap<String, String>>data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\sayanacademy\\data\\PurchaseOrder.json");
    	 
    	return new Object[][] {{data.get(0)},{data.get(1)}};
    	
    }

        

}
