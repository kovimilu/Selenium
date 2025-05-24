import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class MainPage extends BasePage {
    private By loginButton = By.xpath("//a[contains(@class, 'login')]");
    private By settingsMenu = By.xpath("//a[contains(@class, 'has-popover settings')]");
    private By logoutButton = By.xpath("//button[contains(@class, 'logout')]");
    private By modalCloseButton = By.xpath("//button[contains(@class, 'cc-modal-close-component')]");

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public MainPage logout() {
        Actions actions = new Actions(driver);
        WebElement settingNav = this.waitAndReturnElement(settingsMenu);
        actions.moveToElement(settingNav).perform();
        this.waitAndReturnElement(logoutButton).click();

        return this;
    }

    public boolean isLoginButtonDisplayed() {
        return this.waitAndReturnElement(loginButton).isDisplayed();
    }

    public void moveCursorToSettingsMenu() {
        Actions actions = new Actions(driver);
        WebElement settingNav = this.waitAndReturnElement(settingsMenu);
        actions.moveToElement(settingNav).perform();
    }

    public boolean isLogoutButtonDisplayed() {
        return this.waitAndReturnElement(logoutButton).isDisplayed();
    }

    public SettingsPage openSettings() {
        this.waitAndReturnElement(settingsMenu).click();

        return new SettingsPage(driver);
    }

    public void closePopUpIfThereIsOne() {
        try {
            WebElement closeBtn = this.waitAndReturnElement(modalCloseButton);
            closeBtn.click();
        } catch (TimeoutException e) {
            // Modal not present, continue without failing
        }
    }
}
