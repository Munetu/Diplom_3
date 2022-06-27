package po;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.codeborne.selenide.Condition.visible;

public class RegisterPage extends Header{
    public static final String URL = "https://stellarburgers.nomoreparties.site/register";

    @FindBy(how = How.XPATH, using = ".//h2[text()='Регистрация']")
    private SelenideElement titleRegister;

    @FindBy(how = How.XPATH, using = ".//fieldset[1]/div/div/input")
    private SelenideElement inputName;

    @FindBy(how = How.XPATH, using = ".//fieldset[2]/div/div/input")
    private SelenideElement inputEmail;

    @FindBy(how = How.XPATH, using = ".//fieldset[3]/div/div/input")
    private SelenideElement inputPassword;

    @FindBy(how = How.CLASS_NAME, using = "input__error")
    private SelenideElement inputPasswordError;

    @FindBy(how = How.XPATH, using = ".//button[text()='Зарегистрироваться']")
    private SelenideElement buttonRegister;

    @FindBy(how = How.XPATH, using = ".//a[text()='Войти']")
    private SelenideElement buttonLogin;

    public void waitForLoadRegisterPage(){
        titleRegister.shouldBe(visible);
    }

    public void setName(String name) {
        inputName.setValue(name);
    }

    public void setEmail(String email) {
        inputEmail.setValue(email);
    }

    public void setPassword(String password) {
        inputPassword.setValue(password);
    }

    public void clickRegisterButton() {
        buttonRegister.click();
    }

    public LoginPage register(String name, String email, String password) {
        waitForLoadRegisterPage();
        setName(name);
        setEmail(email);
        setPassword(password);
        clickRegisterButton();
        return Selenide.page(LoginPage.class);
    }

    public String registerWithIncorrectPassword(String name, String email, String password) {
        waitForLoadRegisterPage();
        setName(name);
        setEmail(email);
        setPassword(password);
        clickRegisterButton();
        return inputPasswordError.shouldBe(Condition.appear).getText();
    }

    public LoginPage clickLoginButton() {
        buttonLogin.click();
        return Selenide.page(LoginPage.class);
    }

}
