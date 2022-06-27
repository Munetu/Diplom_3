import api.UserAPI;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import po.BurgerСonstructorPage;
import po.LoginPage;
import po.ProfilePage;
import po.RegisterPage;
import pojo.UserCredentials;
import pojo.UserPOJO;

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
//        user = UserPOJO.getRandom();
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
        accessToken = userAccessToken(loginResponse);
        loginSuccess = userLoginSuccess(loginResponse);
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

    @After
    public void teardown() {
        Selenide.closeWindow();
        if (loginSuccess) {
            Response deleteResponse = userAPI.sendDeleteUser(accessToken);
            boolean deleted = userDeletedSuccess(deleteResponse);
        }
    }

    @Step("Получить accessToken")
    public String userAccessToken(Response response) {
        return response.then()
                .extract()
                .path("accessToken");
    }

    @Step("Получить статус об успешном логине пользователя - 200")
    public boolean userLoginSuccess(Response response) {
        return response.then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("success");
    }

    @Step("Получить статус об успешном удалении пользователя - 202")
    public boolean userDeletedSuccess(Response response) {
        return response.then()
                .assertThat()
                .statusCode(202)
                .extract()
                .path("success");
    }


}
