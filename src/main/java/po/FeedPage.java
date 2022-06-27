package po;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.codeborne.selenide.Condition.visible;

public class FeedPage extends Header{
    public static final String URL = "https://stellarburgers.nomoreparties.site/feed";

    @FindBy(how = How.XPATH, using = ".//h1[text()='Лента заказов']")
    private SelenideElement titleMainFeed;

    public FeedPage waitForLoadFeedPage(){
        titleMainFeed.shouldBe(visible);
        return Selenide.page(FeedPage.class);
    }
}
