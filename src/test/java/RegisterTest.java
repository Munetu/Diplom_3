import api.UserAPI;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import po.RegisterPage;
import model.UserCredentials;
import model.UserPOJO;

public class RegisterTest {
    private UserPOJO user;
    private UserAPI userAPI;
    private boolean loginSuccess;
    private Response loginResponse;
    private String accessToken;

    @Before
    public void setUp() {
        Configuration.browserSize = "1600x900";
        Configuration.browserPosition = "0x0";
        userAPI = new UserAPI();
    }

    @After
    public void teardown() {
        Selenide.closeWindow();
        if (loginSuccess) {
            Response deleteResponse = userAPI.sendDeleteUser(accessToken);
            boolean deleted = userAPI.userDeletedSuccess(deleteResponse);
        }
    }

    @Test
    @DisplayName("Проверка успешной регистрации пользователя")
    public void checkRegisterSuccessTest() {
        //Тест
        String expectedResult = "Оформить заказ";
        String name = "Kabanchik";
        String email = "Kabanchik@ymail.ru";
        String password = "12345678";
        String actualResult = Selenide.open(RegisterPage.URL, RegisterPage.class)
                .register(name, email, password)
                .login(email, password)
                .getButtonMakeOrderText();
        //Для удаления пользователя с помощью API
        UserCredentials credentials = new UserCredentials(email, password);
        loginResponse = userAPI.sendPostLoginUser(credentials);
        accessToken = userAPI.userAccessToken(loginResponse);
        loginSuccess = userAPI.userLoginSuccess(loginResponse);
        //Проверка результатов
        Assert.assertEquals("Ожидается, что после регистрации и логина откроется страница Конструктор и появится кнопка Оформить заказ", expectedResult, actualResult);
    }

    @Test
    @DisplayName("Проверка валидации пароля")
    public void checkRegisterIncorrectPasswordTest() {
        //Тест
        String expectedResult = "Некорректный пароль";
        String name = "Kabanchik";
        String email = "Kabanchik@ymail.ru";
        String password = "12345";
        String actualResult = Selenide.open(RegisterPage.URL, RegisterPage.class)
                .registerWithIncorrectPassword(name, email, password);
        //Проверка результатов
        Assert.assertEquals("Текст ошибки о некорректном пароле не совпадает с ожидаемым", expectedResult, actualResult);
    }
}
