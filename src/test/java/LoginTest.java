import org.junit.Assert;
import org.junit.Test;

public class LoginTest extends BaseTest {

    @Test
    public void testLogin() {
        this.driver.get("https://www.chess.com/home");
        LoginPage loginPage = new LoginPage(this.driver);
        loginPage.acceptCookies();
        MainPage main = loginPage.login("fisevo9721", "Aa123456789");
        main.closePopUpIfThereIsOne();

        Assert.assertTrue(main.getBodyText().contains("fisevo9721"));
    }

    @Test
    public void testLogout() {
        this.driver.get("https://www.chess.com/home");
        LoginPage loginPage = new LoginPage(this.driver);
        loginPage.acceptCookies();
        MainPage main = loginPage.login("fisevo9721", "Aa123456789");
        main.closePopUpIfThereIsOne();
        main.logout();

        Assert.assertTrue(main.isLoginButtonDisplayed());
    }
}
