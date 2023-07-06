package com.core.utility;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.MouseButton;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.core.hooks.Hooks;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;

public abstract class MasterPage extends Hooks {
    public void auto_openURLInBrowser(){
        try {
            page = createPlaywrightPageInstance(System.getProperty("browser"));
            page.navigate(System.getProperty("applicationUrl"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void auto_openNewURLInBrowser(String url){
        try {
            page = createPlaywrightPageInstance(System.getProperty("browser"));
            page.navigate(url);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String auto_getCurrentUrl(){
        return page.url();
    }

    public String auto_getPageTitle(){
        return page.title();
    }

    public void auto_setClickElement(String locator){
        page.click(locator);

    }

    public void auto_setDoubleClickElement(String locator){
        page.dblclick(locator);
    }

    public void auto_setClickElementAndHold(String locator){
        page.locator(locator).click((new Locator.ClickOptions()
                .setButton(MouseButton.RIGHT)
                .setDelay(5000)));
    }

    public void auto_setTextToInput(String locator, String value){
        page.locator(locator).clear();
        page.fill(locator, value);
    }

    public void auto_setTextToInputWithoutClear(String locator, String value){
        page.fill(locator, value);
    }

    public void auto_clearInput(String locator){
        page.locator(locator).clear();
    }

    public String auto_getElementText(String locator){
        return page.locator(locator).textContent();
    }

    public String auto_getInputValue(String locator){
        return page.locator(locator).inputValue();
    }

    public void auto_selectCheckbox(String locator){
        page.locator(locator).isVisible();
        if (!page.locator(locator).isChecked()){
            page.locator(locator).check();
        }
    }

    public void auto_deselectCheckbox(String locator){
        page.locator(locator).isVisible();
        if (page.locator(locator).isChecked()){
            page.locator(locator).uncheck();
        }
    }

    public boolean auto_isElementVisible(String locator){
        return page.locator(locator).isVisible();
    }

    public boolean auto_isInputEmpty(String locator){
        return page.locator(locator).getAttribute("value").isEmpty();
    }

    public void auto_waitForElementPresent(String locator){
        page.locator(locator).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));
    }

    public void auto_waitForElementVisibility(String locator){
        page.locator(locator).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
    }

    public void auto_waitForElementInvisibility(String locator){
        page.locator(locator).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
    }

    public void auto_setTextToClipboard(String value){
        StringSelection stringSelection = new StringSelection(value);
        Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
        clpbrd.setContents(stringSelection, (ClipboardOwner)null);
    }

    public boolean auto_isElementEnabled(String locator){
        return page.locator(locator).isEnabled();
    }

    public boolean auto_isElementEditable(String locator){
        return page.locator(locator).isEditable();
    }

    public String auto_getAttribute(String locator, String attribute){
        return page.locator(locator).getAttribute(attribute);
    }

    public void auto_HoverElement(String locator){
        page.locator(locator).hover();
    }

    public void auto_ScrollToElement(String locator){
        page.locator(locator).scrollIntoViewIfNeeded();
    }

    public void auto_scrollToElementJS(int x, int y) {
        page.evaluate("window.scrollBy(" + x + ", " + y + ");");
    }

    public void auto_switchToIframe(String iFrame){
        page.frameLocator(iFrame);
    }

    public void auto_goBack(){
        page.goBack();
    }
    
    public void auto_sendKeys(String locator, String key){
        page.locator(locator).press(key);
    }

    public void auto_selectOptionFromLabel(String locator,String value){
        ElementHandle select = page.querySelector(locator);
        select.selectOption(new SelectOption().setLabel(value));
    }

    public void auto_selectOptionFromIndex(String locator,int value){
        ElementHandle select = page.querySelector(locator);
        select.selectOption(new SelectOption().setIndex(value));
    }

    public void auto_selectOptionFromValue(String locator,String value){
        ElementHandle select = page.querySelector(locator);
        select.selectOption(new SelectOption().setValue(value));
    }

}
