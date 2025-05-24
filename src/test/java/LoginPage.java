import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private By acceptCookiesButton = By.xpath("//button[@id='onetrust-accept-btn-handler']");
    private By loginEmailInput = By.xpath("//input[@id='login-username']");
    private By loginPasswordInput = By.xpath("//input[@id='login-password']");
    private By loginButton = By.xpath("//button[@id='login']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void acceptCookies() {
        this.waitAndReturnElement(acceptCookiesButton).click();
    }

    public MainPage login(String username, String password) {
        this.waitAndReturnElement(loginEmailInput).sendKeys(username);
        this.waitAndReturnElement(loginPasswordInput).sendKeys(password);
        this.waitAndReturnElement(loginButton).click();

        return new MainPage(driver);
    }
}
