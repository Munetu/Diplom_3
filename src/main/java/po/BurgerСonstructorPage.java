package po;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.codeborne.selenide.Condition.visible;

public class BurgerСonstructorPage extends Header {
    public static final String URL = "https://stellarburgers.nomoreparties.site";

    @FindBy(how = How.XPATH, using = ".//h1[text()='Соберите бургер']")
    private SelenideElement titleMainBurgerConstructor;

    @FindBy(how = How.XPATH, using = ".//button[text()='Оформить заказ']")
    private SelenideElement buttonMakeOrder;

    @FindBy(how = How.XPATH, using = ".//button[text()='Войти в аккаунт']")
    private SelenideElement buttonLogin;

    //невозможно сократить путь до элемента в дереве, Т.К. фронтендом/верстальщиком не было установлена особенность данного элемента
    @FindBy(how = How.XPATH, using = ".//section[@class='BurgerIngredients_ingredients__1N8v2']/div[1]/div[1]")
    private SelenideElement sectionBuns;

    //невозможно сократить путь до элемента в дереве, Т.К. фронтендом/верстальщиком не было установлена особенность данного элемента
    @FindBy(how = How.XPATH, using = ".//section[@class='BurgerIngredients_ingredients__1N8v2']/div[1]/div[2]")
    private SelenideElement sectionSauce;

    //невозможно сократить путь до элемента в дереве, Т.К. фронтендом/верстальщиком не было установлена особенность данного элемента
    @FindBy(how = How.XPATH, using = ".//section[@class='BurgerIngredients_ingredients__1N8v2']/div[1]/div[3]")
    private SelenideElement sectionFilling;

    @FindBy(how = How.XPATH, using = ".//h2[text()='Булки']")
    private SelenideElement titleBuns;

    @FindBy(how = How.XPATH, using = ".//h2[text()='Соусы']")
    private SelenideElement titleSauce;

    @FindBy(how = How.XPATH, using = ".//h2[text()='Начинки']")
    private SelenideElement titleFilling;

    @Step("Ожидание загрузки страницы Конструктор (проверяем, что появился заголовок страницы)")
    public BurgerСonstructorPage waitForLoadBurgerConstructorPage() {
        titleMainBurgerConstructor.shouldBe(visible);
        return Selenide.page(BurgerСonstructorPage.class);
    }

    @Step("Нажимаем на кнопку Авторизоваться")
    public LoginPage clickButtonLogin() {
        buttonLogin.click();
        return Selenide.page(LoginPage.class);
    }

    @Step("Получаем название кнопки Сделать заказ (после авторизации)")
    public String getButtonMakeOrderText() {
        return buttonMakeOrder.shouldBe(Condition.appear).getText();
    }

    @Step("Нажимаем на секцию Булочки в области с ингредиентами")
    public void clickSectionBuns() {
        sectionBuns.click();
    }

    @Step("Нажимаем на секцию Соусы в области с ингредиентами")
    public void clickSectionSauce() {
        sectionSauce.click();
    }

    @Step("Нажимаем на секцию Filling в области с ингредиентами")
    public void clickSectionFilling() {
        sectionFilling.click();
    }

    public String checkSectionBuns() {
        clickSectionFilling();
        sectionFilling.shouldBe(Condition.appear);
        clickSectionBuns();
        return sectionBuns.shouldBe(Condition.appear).getText();
    }

    public String checkSectionSauce() {
        clickSectionSauce();
        return sectionSauce.shouldBe(Condition.appear).getText();
    }

    public String checkSectionFilling() {
        clickSectionFilling();
        return sectionFilling.shouldBe(Condition.appear).getText();
    }
}
