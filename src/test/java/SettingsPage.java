import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SettingsPage extends BasePage {
    private By profileMenu = By.xpath("//a[@href='/settings/profile']");
    private By bioTextarea = By.xpath("//textarea[contains(@class, 'short-bio-short-bio')]");
    private By bioSaveButton = By.xpath("//div[@class='profile-header-buttons']//button[2]");

    public SettingsPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToProfilePage() {
        this.waitAndReturnElement(profileMenu).click();
    }

    public String fillAndSaveBioWithInputValue(String bioText) {
        this.waitAndReturnElement(bioTextarea).clear();
        this.waitAndReturnElement(bioTextarea).sendKeys(bioText);
        String inputValue = this.waitAndReturnElement(bioTextarea).getAttribute("value");
        this.waitAndReturnElement(bioSaveButton).click();

        return inputValue;
    }

    public String isBioClearAfterSave() {
        return this.waitAndReturnElement(bioTextarea).getAttribute("value");
    }
}
