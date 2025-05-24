import org.junit.Assert;
import org.junit.Test;

public class PageTest extends BaseTest {

    @Test
    public void testReadTitle() {
        this.driver.get("https://www.chess.com");

        Assert.assertEquals("Chess.com - Play Chess Online - Free Games", driver.getTitle());
    }

    @Test
    public void testPageLoadSuccessfully() {
        this.driver.get("https://www.chess.com");
        MainPage main = new MainPage(this.driver);

        Assert.assertTrue(main.isLoginButtonDisplayed());
    }
}
