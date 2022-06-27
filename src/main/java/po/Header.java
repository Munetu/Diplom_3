package po;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
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

    public ProfilePage goToProfilePage(){
        clickNavToProfilePage();
        return Selenide.page(ProfilePage.class);
    }

    public LoginPage clickProfilePageButtonWithoutLogin(){
        clickNavToProfilePage();
        return Selenide.page(LoginPage.class);
    }

    public FeedPage goToFeedPage(){
        clickNavFeedPage();
        return Selenide.page(FeedPage.class);
    }

    public BurgerСonstructorPage goToBurgerConstructorPageByNav(){
        clickNavBurgerConstructorPage();
        return Selenide.page(BurgerСonstructorPage.class);
    }

    public BurgerСonstructorPage goToBurgerConstructorPageByLogo(){
        logoStellarBurgers.click();
        return Selenide.page(BurgerСonstructorPage.class);
    }

}
