package AutomationTest.BrightTalkTest;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReqresPage extends  BasePage {

    ECUtils ecUtils=new ECUtils();
    @FindBy(xpath = "//button[text()=\"Upgrade\"]")
    WebElement upgradeButton;


    @FindBy(xpath = "//input[@id=\"mce-EMAIL\"]")
    WebElement emailButton;


    @FindBy(xpath = "//input[@name=\"subscribe\"]")
    WebElement subscribeButton;

    ReqresPage(){

        PageFactory.initElements(driver,this);
    }

    public String getPageTitle(){

        String text=driver.findElement(By.cssSelector("h2[class=\"tagline\"]")).getText();
        System.out.println(text);
        return text;
    }

    public void clickOnUpgradeButton() {

        new WebDriverWait(driver, 1000).until(ExpectedConditions.elementToBeClickable(this.upgradeButton));// Expectedcondition for the element to be clickable
        this.upgradeButton.click();
    }
    public boolean elementVisibleEmail(){
        Boolean b=ecUtils.elementVisible(driver,emailButton);
        return b;
    }
    public boolean elementVisibleSubscribe(){
        Boolean b=ecUtils.elementVisible(driver,subscribeButton);
        return b;
    }

    public boolean elementVisibleUpgradeButton() throws InterruptedException {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", this.upgradeButton);
        Thread.sleep(500);

        Boolean b=ecUtils.elementVisible(driver,upgradeButton);
        return b;
    }



}
