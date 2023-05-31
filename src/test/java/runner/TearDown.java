package runner;

import com.core.hooks.Hooks;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;


public class TearDown extends Hooks {
    @After
    public static void tearDown(Scenario scenario) {
        if (scenario.isFailed()){
            byte[] screenshot = page.screenshot();
            scenario.attach(screenshot,"image/png","screenshot");
        }
        if (browser != null) {
            browser.close();
        }
        if (page != null) {
            page.close();
        }
    }
}
