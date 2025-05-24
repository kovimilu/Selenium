import org.junit.Assert;
import org.junit.Test;

public class FormTest extends BaseTest {

    @Test
    public void testSendBioForm() {
        this.driver.get("https://www.chess.com/home");
        LoginPage loginPage = new LoginPage(this.driver);
        loginPage.acceptCookies();
        MainPage main = loginPage.login("fisevo9721", "Aa123456789");
        main.closePopUpIfThereIsOne();
        SettingsPage settingsPage = main.openSettings();
        settingsPage.navigateToProfilePage();
        String inputValue = settingsPage.fillAndSaveBioWithInputValue("Test");
        //String inputValueAfterSave = settingsPage.isBioClearAfterSave();

        Assert.assertEquals("Test", inputValue);
        //Assert.assertEquals("Test", inputValueAfterSave);
    }
}
