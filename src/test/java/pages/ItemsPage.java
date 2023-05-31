package pages;

import org.junit.Assert;

import com.microsoft.playwright.Page;

public class ItemsPage extends BasePage {

	Page page;

	public ItemsPage(Page page) {
		this.page = page;
	}

	public void orderProduct(String ProductName) {
		page.click("//div[text()='" + ProductName + "']/following::button[1]");
		page.click("#shopping_cart_container > a");
		Assert.assertTrue(page.isVisible("text="+ProductName));
		page.click("[data-test=\"checkout\"]");
	}

	public void loginSuccessful() {
		Assert.assertTrue(page.isVisible("text=Products"));
	}
}
