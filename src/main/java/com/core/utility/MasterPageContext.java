package com.core.utility;

import com.microsoft.playwright.Page;
import org.assertj.core.api.SoftAssertions;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.MouseButton;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.junit.Assert;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;

import static com.core.hooks.Hooks.createPlaywrightPageInstance;
import static com.core.hooks.Hooks.*;

public interface MasterPageContext {
    SoftAssertions softAssertions = new SoftAssertions();

    default Page auto_newPage(){
        try {
            page = createPlaywrightPageInstance(System.getProperty("browser"));
            page.navigate(System.getProperty("applicationUrl"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return page;
    }

    default String auto_getCurrentUrl(Page page){
        return page.url();
    }

    default String auto_getPageTitle(Page page){
        return page.title();
    }

    default void auto_setClickElement(String locator, Page page){
        page.click(locator);

    }

    default void auto_setDoubleClickElement(String locator, Page page){
        page.dblclick(locator);
    }

    default void auto_setClickElementAndHold(String locator, Page page){
        page.locator(locator).click((new Locator.ClickOptions()
                .setButton(MouseButton.RIGHT)
                .setDelay(5000)));
    }

    default void auto_setTextToInput(String locator, String value, Page page){
        page.locator(locator).clear();
        page.fill(locator, value);
    }

    default void auto_setTextToInputWithoutClear(String locator, String value, Page page){
        page.fill(locator, value);
    }

    default void auto_clearInput(String locator, Page page){
        page.locator(locator).clear();
    }

    default String auto_getElementText(String locator, Page page){
        return page.locator(locator).textContent();
    }

    default String auto_getInputValue(String locator, Page page){
        return page.locator(locator).inputValue();
    }

    default void auto_selectCheckbox(String locator, Page page){
        page.locator(locator).isVisible();
        if (!page.locator(locator).isChecked()){
            page.locator(locator).check();
        }
    }

    default void auto_deselectCheckbox(String locator, Page page){
        page.locator(locator).isVisible();
        if (page.locator(locator).isChecked()){
            page.locator(locator).uncheck();
        }
    }

    default boolean auto_isElementVisible(String locator, Page page){
        return page.locator(locator).isVisible();
    }

    default boolean auto_isInputEmpty(String locator, Page page){
        return page.locator(locator).getAttribute("value").isEmpty();
    }

    default void auto_waitForElementPresent(String locator, Page page){
        page.locator(locator).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));
    }

    default void auto_waitForElementVisibility(String locator, Page page){
        page.locator(locator).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
    }

    default void auto_waitForElementInvisibility(String locator, Page page){
        page.locator(locator).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
    }

    default void auto_setTextToClipboard(String value, Page page){
        StringSelection stringSelection = new StringSelection(value);
        Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
        clpbrd.setContents(stringSelection, (ClipboardOwner)null);
    }

    default boolean auto_isElementEnabled(String locator, Page page){
        return page.locator(locator).isEnabled();
    }

    default boolean auto_isElementEditable(String locator, Page page){
        return page.locator(locator).isEditable();
    }

    default String auto_getAttribute(String locator, String attribute, Page page){
        return page.locator(locator).getAttribute(attribute);
    }

    default void auto_HoverElement(String locator, Page page){
        page.locator(locator).hover();
    }

    default void auto_ScrollToElement(String locator, Page page){
        page.locator(locator).scrollIntoViewIfNeeded();
    }

    default void auto_scrollToElementJS(int x, int y, Page page) {
        page.evaluate("window.scrollBy(" + x + ", " + y + ");");
    }

    default void auto_switchToIframe(String iFrame, Page page){
        page.frameLocator(iFrame);
    }

    default void auto_goBack(Page page){
        page.goBack();
    }

    default void auto_sendKeys(String locator, String key, Page page){
        page.locator(locator).press(key);
    }

    default void auto_selectOptionFromLabel(String locator,String value, Page page){
        ElementHandle select = page.querySelector(locator);
        select.selectOption(new SelectOption().setLabel(value));
    }

    default void auto_selectOptionFromIndex(String locator,int value, Page page){
        ElementHandle select = page.querySelector(locator);
        select.selectOption(new SelectOption().setIndex(value));
    }

    default void auto_selectOptionFromValue(String locator,String value, Page page){
        ElementHandle select = page.querySelector(locator);
        select.selectOption(new SelectOption().setValue(value));
    }

    default void auto_verifyVisibility(String locator, Page page){
        MasterPage.auto_waitForElementVisibility(locator);
        Assert.assertTrue("El elemento no es visible", MasterPage.auto_isElementVisible(locator));
    }

    default void auto_verifyVisibilities(Page page, String ... locators){
        for (String locator : locators) {
            MasterPage.auto_waitForElementVisibility(locator);
            softAssertions.assertThat(locator).as("El elemento no se visualiza correctamente").isVisible();
        }
        softAssertions.assertAll();
    }

    default void auto_verifyInvisibilities(Page page, String ... locators){
        for (String locator : locators) {
            MasterPage.auto_waitForElementInvisibility(locator);
            softAssertions.assertThat(MasterPage.auto_isElementVisible(locator)).as("El elemento no es invisible").isFalse();
        }
        softAssertions.assertAll();
    }
}
