package pages;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public abstract class BasePage {

	/**
	 * Page
	 */
	
	protected Browser browser;
	protected Page page;

	public Page createPlaywrightPageInstance(String browserTypeAsString) {
		BrowserType browserType = null;
		switch (browserTypeAsString) {
		case "Firefox":
			browserType = Playwright.create().firefox();
			break;
		case "Chromium":
			browserType = Playwright.create().chromium();
			break;
		case "Webkit":
			browserType = Playwright.create().webkit();
			break;

		}
		if (browserType == null) {
			throw new IllegalArgumentException("Could not launch a browser for type " + browserTypeAsString);
		}
		browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(false));
		page = browser.newPage();
		return page;

	}

}