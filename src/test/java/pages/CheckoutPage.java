package pages;

import org.junit.Assert;

import com.microsoft.playwright.Page;

public class CheckoutPage extends BasePage{
	
	Page page;
	
	  public CheckoutPage(Page page) { 
		  this.page = page;
      }
	 
	 public void fillCheckoutDetails(String firstname, String lastname, String postcode) {	
	      page.fill("[data-test=\"firstName\"]", firstname);
	      page.fill("[data-test=\"lastName\"]", lastname);
	      page.fill("[data-test=\"postalCode\"]", postcode);
	}
	 public void completeCheckout() {
	      page.click("[data-test=\"continue\"]");
	      page.click("[data-test=\"finish\"]");
	    }
	 
	 public void checkoutSuccessful() {
	      Assert.assertTrue(page.isVisible("text=THANK YOU FOR YOUR ORDER"));
	}
}
