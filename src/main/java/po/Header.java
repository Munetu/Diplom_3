package po;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class Header {

    @FindBy(how = How.XPATH, using = ".//a/p[text()='Личный Кабинет']")
    private SelenideElement navProfilePage;

    @FindBy(how = How.XPATH, using = ".//a[@href='/feed']")
    private SelenideElement navFeedPage;

    @FindBy(how = How.XPATH, using = ".//li/a[@href='/']")
    private SelenideElement navBurgerConstructorPage;

    @FindBy(how = How.XPATH, using = ".//a[@href='/']")
    private SelenideElement logoStellarBurgers;

    public void clickNavToProfilePage() {
        navProfilePage.click();
    }

    public void clickNavFeedPage() {
        navFeedPage.click();
    }

    public void clickNavBurgerConstructorPage() {
        navBurgerConstructorPage.click();
    }

    @Step("Нажимаем на Личный Кабинет в хедере, когда мы  авторизованы")
    public ProfilePage goToProfilePage() {
        clickNavToProfilePage();
        return Selenide.page(ProfilePage.class);
    }

    @Step("Нажимаем на Личный Кабинет в хедере в случае, когда мы не авторизованы")
    public LoginPage clickProfilePageButtonWithoutLogin() {
        clickNavToProfilePage();
        return Selenide.page(LoginPage.class);
    }

    @Step("Нажимаем на Список заказов в хедере")
    public FeedPage goToFeedPage() {
        clickNavFeedPage();
        return Selenide.page(FeedPage.class);
    }

    @Step("Нажимаем на Конструктор в хедере")
    public BurgerСonstructorPage goToBurgerConstructorPageByNav() {
        clickNavBurgerConstructorPage();
        return Selenide.page(BurgerСonstructorPage.class);
    }

    @Step("Нажимаем на Лого в хедере")
    public BurgerСonstructorPage goToBurgerConstructorPageByLogo() {
        logoStellarBurgers.click();
        return Selenide.page(BurgerСonstructorPage.class);
    }
}
