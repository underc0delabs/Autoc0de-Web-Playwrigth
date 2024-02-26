package com.core.utility;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.*;
import com.core.hooks.Hooks;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;

public abstract class MasterPage extends Hooks {

    SoftAssertions softAssertions = new SoftAssertions();

    public void auto_openURLInBrowser() {
        try {
            page = createPlaywrightPageInstance(System.getProperty("browser"));
            page.navigate(System.getProperty("applicationUrl"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void auto_openNewURLInBrowser(String url) {
        try {
            page = createPlaywrightPageInstance(System.getProperty("browser"));
            page.navigate(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String auto_getCurrentUrl() {
        return page.url();
    }

    public String auto_getPageTitle() {
        return page.title();
    }

    public void auto_setClickElement(String locator) {
        page.click(locator);

    }

    public void auto_setDoubleClickElement(String locator) {
        page.dblclick(locator);
    }

    public void auto_setClickElementAndHold(String locator) {
        page.locator(locator).click((new Locator.ClickOptions()
                .setButton(MouseButton.RIGHT)
                .setDelay(5000)));
    }

    public void auto_setTextToInput(String locator, String value) {
        page.locator(locator).clear();
        page.fill(locator, value);
    }

    public void auto_setTextToInputWithoutClear(String locator, String value) {
        page.fill(locator, value);
    }

    public void auto_clearInput(String locator) {
        page.locator(locator).clear();
    }

    public String auto_getElementText(String locator) {
        return page.locator(locator).textContent();
    }

    public String auto_getInputValue(String locator) {
        return page.locator(locator).inputValue();
    }

    public void auto_selectCheckbox(String locator) {
        page.locator(locator).isVisible();
        if (!page.locator(locator).isChecked()) {
            page.locator(locator).check();
        }
    }

    public void auto_deselectCheckbox(String locator) {
        page.locator(locator).isVisible();
        if (page.locator(locator).isChecked()) {
            page.locator(locator).uncheck();
        }
    }

    public boolean auto_isElementVisible(String locator) {
        return page.locator(locator).isVisible();
    }

    public boolean auto_isInputEmpty(String locator) {
        return page.locator(locator).getAttribute("value").isEmpty();
    }

    public void auto_waitForElementPresent(String locator) {
        page.locator(locator).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));
    }

    public void auto_waitForElementVisibility(String locator) {
        page.locator(locator).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
    }

    public void auto_waitForElementsVisibilities(String locatorList) {
        page.waitForSelector(locatorList, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
    }

    public void auto_waitForElementInvisibility(String locator) {
        page.locator(locator).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
    }

    public void auto_waitForLoadComplete(){
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
    }

    public void auto_setTextToClipboard(String value) {
        StringSelection stringSelection = new StringSelection(value);
        Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
        clpbrd.setContents(stringSelection, (ClipboardOwner) null);
    }

    public boolean auto_isElementEnabled(String locator) {
        return page.locator(locator).isEnabled();
    }

    public boolean auto_isElementEditable(String locator) {
        return page.locator(locator).isEditable();
    }

    public String auto_getAttribute(String locator, String attribute) {
        return page.locator(locator).getAttribute(attribute);
    }

    public void auto_HoverElement(String locator) {
        page.locator(locator).hover();
    }

    public void auto_ScrollToElement(String locator) {
        page.locator(locator).scrollIntoViewIfNeeded();
    }

    public void scrollToElementJS(int x, int y) {
        page.evaluate("window.scrollBy(" + x + ", " + y + ");");
    }

    public void auto_switchToIframe(String iFrame) {
        page.frameLocator(iFrame);
    }

    public void auto_goBack() {
        page.goBack();
    }

    public void auto_sendKeys(String locator, String key) {
        page.locator(locator).press(key);
    }

    public void auto_selectOptionFromLabel(String locator, String value) {
        ElementHandle select = page.querySelector(locator);
        select.selectOption(new SelectOption().setLabel(value));
    }

    public void auto_selectOptionFromIndex(String locator, int value) {
        ElementHandle select = page.querySelector(locator);
        select.selectOption(new SelectOption().setIndex(value));
    }

    public void auto_selectOptionFromValue(String locator, String value) {
        ElementHandle select = page.querySelector(locator);
        select.selectOption(new SelectOption().setValue(value));
    }

    public void auto_verifyVisibility(String locator) {
        auto_waitForElementVisibility(locator);
        Assert.assertTrue("El elemento no es visible", auto_isElementVisible(locator));
    }

    public void auto_verifyVisibilities(String... locators) {
        for (String locator : locators) {
            auto_waitForElementVisibility(locator);
            softAssertions.assertThat(page.locator(locator).isVisible()).as("El elemento no se visualiza correctamente").isTrue();
        }
        softAssertions.assertAll();
    }

    public void auto_verifyVisibilities(String locatorList) {
        auto_waitForElementsVisibilities(locatorList);
        List<ElementHandle> elements = page.locator(locatorList).elementHandles();
        for (ElementHandle element : elements) {
            softAssertions.assertThat(element.isVisible()).as("El elemento no es visible");
        }
        softAssertions.assertAll();
    }

    public void auto_verifyInvisibilities(String... locators) {
        for (String locator : locators) {
            auto_waitForElementInvisibility(locator);
            softAssertions.assertThat(page.locator(locator).isHidden()).as("El elemento no es invisible").isTrue();
        }
        softAssertions.assertAll();
    }

    public void auto_verifyInvisibilities(String locatorList) {
        auto_waitForElementsVisibilities(locatorList);
        List<ElementHandle> elements = page.locator(locatorList).elementHandles();
        for (ElementHandle element : elements) {
            softAssertions.assertThat(element.isVisible()).as("El elemento no es invisible").isFalse();
        }
        softAssertions.assertAll();
    }

    public String auto_rempalceSpacesLocator(String value) {
        return value.replaceAll(" ","_");
    }
    public String auto_addSpacesLocator(String value) {
        return value.replaceAll(" ","");
    }
    public void auto_verifyText(String xpath, String expectedText) {
        ElementHandle element = (ElementHandle) page.locator(xpath).first();
        if (element == null) {
            throw new AssertionError("Elemento no encontrado con XPath: " + xpath);
        }
        String actualText = element.textContent();
        if (!actualText.equals(expectedText)) {
            throw new AssertionError("Texto no coincide. Esperado: " + expectedText + ", Actual: " + actualText);
        }
    }

    public List<ElementHandle> auto_returnElementHandleList(String locator) {
        auto_waitForElementsVisibilities(locator);
        return page.locator(locator).elementHandles();
    }

    public String auto_returnPropertyValue(String locator, String property) {
        auto_waitForElementsVisibilities(locator);
        return page.locator(locator).evaluate("element => window.getComputedStyle(element).getPropertyValue('"+property+"')").toString();

    }

    public void auto_verifyTextMatchInLists(String parentXPath, List<String> textList) {
        for (String text : textList) {
            String completeXPath = String.format(parentXPath, text);
            auto_waitForElementsVisibilities(completeXPath); // Espera la visibilidad para el XPath completo
            List<ElementHandle> elements = page.locator(completeXPath).elementHandles();
            softAssertions.assertThat(elements.stream().anyMatch(x -> {
                String innerText = x.textContent();
                System.out.println("Comparando: " + innerText + " con " + text);
                return innerText != null && innerText.contains(text);
            })).as("No se muestra el titulo de la sección: " + text).isTrue();
        }

        softAssertions.assertAll();
    }
}