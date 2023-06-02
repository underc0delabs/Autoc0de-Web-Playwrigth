package com.core.hooks;

import com.microsoft.playwright.*;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Scenario;

import java.awt.*;
import java.util.Arrays;

public abstract class Hooks {

    /**
     * Page
     */

    public static Browser browser;
    public static Page page;

    public static Page createPlaywrightPageInstance(String browserTypeAsString) {
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
        if (System.getProperty("executionMode").equals("Local")){
            browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(true));
            BrowserContext context = browser.newContext(new Browser.NewContextOptions().setViewportSize(1500,800));
            page= context.newPage();

        } else {
            browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext context = browser.newContext(new Browser.NewContextOptions().setViewportSize(1920,1080));
            page= context.newPage();
        }
        return page;
    }
}