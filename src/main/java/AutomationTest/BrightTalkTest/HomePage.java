package AutomationTest.BrightTalkTest;

import static AutomationTest.BrightTalkTest.LoadProp.getproperty;

public class HomePage extends BasePage {

    public static void homePage() {
        driver.get(getproperty("url"));
    }

    public static String apiHomePage() {
        return getproperty("apiUrl");
    }
}
