import api.UserAPI;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import po.BurgerСonstructorPage;
import po.ForgotPasswordPage;
import po.RegisterPage;
import model.UserCredentials;
import model.UserPOJO;

public class LoginTest {
    private UserPOJO user;
    private UserAPI userAPI;
    private Response response;
    private boolean created;
    private String accessToken;
    private UserCredentials credentials;

    @Before
    public void setup() {
        Configuration.browserSize = "1600x900";
        Configuration.browserPosition = "0x0";
        String name = "Kabanchik";
        String email = "Kabanchik@ymail.ru";
        String password = "12345678";
        userAPI = new UserAPI();
        user = new UserPOJO(email, password, name);
        response = userAPI.sendPostRequestRegisterUser(user);
        created = userAPI.userCreatedSuccess(response);
        accessToken = userAPI.userAccessToken(response);
        credentials = UserCredentials.from(user);
    }

    @After
    public void teardown() {
        Selenide.closeWindow();
        if (created) {
            Response deleteResponse = userAPI.sendDeleteUser(accessToken);
            boolean deleted = userAPI.userDeletedSuccess(deleteResponse);
        }
    }

    @Test
    @DisplayName("Проверка логина пользователя со страницы Конструктор Бургеров, через кнопку Войти в аккаунт")
    public void loginUserSuccessFromBurgerConstructorPageLoginButton() {
        String expectedResult = "Оформить заказ";
        String actualResult = Selenide.open(BurgerСonstructorPage.URL, BurgerСonstructorPage.class)
                .clickButtonLogin()
                .login(credentials.getEmail(), credentials.getPassword())
                .getButtonMakeOrderText();
        Assert.assertEquals("Ожидается, что после логина откроется страница Консруктор и появится кнопка Оформить заказ", expectedResult, actualResult);
    }

    @Test
    @DisplayName("Проверка логина пользователя со страницы Конструктор Бургеров, через кнопку Личный Кабинет")
    public void loginUserSuccessFromBurgerConstructorPageProfileButton() {
        String expectedResult = "Оформить заказ";
        String actualResult = Selenide.open(BurgerСonstructorPage.URL, BurgerСonstructorPage.class)
                .clickProfilePageButtonWithoutLogin()
                .login(credentials.getEmail(), credentials.getPassword())
                .getButtonMakeOrderText();
        Assert.assertEquals("Ожидается, что после логина откроется страница Консруктор и появится кнопка Оформить заказ", expectedResult, actualResult);
    }

    @Test
    @DisplayName("Проверка логина пользователя со страницы Регистрация, через кнопку Войти")
    public void loginUserSuccessFromRegisterPageLoginButton() {
        String expectedResult = "Оформить заказ";
        String actualResult = Selenide.open(RegisterPage.URL, RegisterPage.class)
                .clickLoginButton()
                .login(credentials.getEmail(), credentials.getPassword())
                .getButtonMakeOrderText();
        Assert.assertEquals("Ожидается, что после логина откроется страница Консруктор и появится кнопка Оформить заказ", expectedResult, actualResult);
    }

    @Test
    @DisplayName("Проверка логина пользователя со страницы Забыли Пароль, через кнопку Войти")
    public void loginUserSuccessFromForgotPasswordPageLoginButton() {
        String expectedResult = "Оформить заказ";
        String actualResult = Selenide.open(ForgotPasswordPage.URL, ForgotPasswordPage.class)
                .clickLoginButton()
                .login(credentials.getEmail(), credentials.getPassword())
                .getButtonMakeOrderText();
        Assert.assertEquals("Ожидается, что после логина откроется страница Консруктор и появится кнопка Оформить заказ", expectedResult, actualResult);
    }
}
