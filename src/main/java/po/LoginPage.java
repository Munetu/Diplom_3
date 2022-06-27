package po;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.codeborne.selenide.Condition.visible;

public class LoginPage extends Header{
    public static final String URL = "https://stellarburgers.nomoreparties.site/login";

    @FindBy(how = How.XPATH, using = ".//h2[text()='Вход']")
    private SelenideElement titleEnter;

    @FindBy(how = How.XPATH, using = ".//fieldset[1]/div/div/input")
    private SelenideElement inputEmail;

    @FindBy(how = How.XPATH, using = ".//fieldset[2]/div/div/input")
    private SelenideElement inputPassword;

    @FindBy(how = How.XPATH, using = ".//button[text()='Войти']")
    private SelenideElement buttonEnter;

    public void waitForLoadLoginPage(){
        titleEnter.shouldBe(visible);
    }

    public String getPageTitle(){
        return titleEnter.shouldBe(Condition.appear).getText();
    }

    public void setEmail(String email) {
        inputEmail.setValue(email);
    }

    public void setPassword(String password) {
        inputPassword.setValue(password);
    }

    public void clickRegisterButton() {
        buttonEnter.click();
    }

    public BurgerСonstructorPage login(String email, String password) {
        waitForLoadLoginPage();
        setEmail(email);
        setPassword(password);
        clickRegisterButton();
        return Selenide.page(BurgerСonstructorPage.class);
    }

}
