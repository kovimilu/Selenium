import org.junit.Assert;
import org.junit.Test;

public class HoverTest extends BaseTest {

    @Test
    public void testHoverSettings() {
        this.driver.get("https://www.chess.com/home");
        LoginPage loginPage = new LoginPage(this.driver);
        loginPage.acceptCookies();
        MainPage main = loginPage.login("fisevo9721", "Aa123456789");
        main.closePopUpIfThereIsOne();
        main.moveCursorToSettingsMenu();

        Assert.assertTrue(main.isLogoutButtonDisplayed());
    }
}
