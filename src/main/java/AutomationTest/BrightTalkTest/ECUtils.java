package AutomationTest.BrightTalkTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ECUtils {

    public boolean elementVisible(WebDriver driver,WebElement element){
        new WebDriverWait(driver,1000).until(ExpectedConditions.visibilityOf(element));
        boolean b=element.isDisplayed();
        return b;
    }
}
