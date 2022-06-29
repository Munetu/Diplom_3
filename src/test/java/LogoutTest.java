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

public class LogoutTest {
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
    @DisplayName("Проверка логина пользователя со страницы Регистрация, через кнопку Войти")
    public void loginUserSuccessFromRegisterPageLoginButton() {
        String expectedResult = "Вход";
        String actualResult = Selenide.open(RegisterPage.URL, RegisterPage.class)
                .clickLoginButton()
                .login(credentials.getEmail(), credentials.getPassword())
                .goToProfilePage()
                .logout()
                .getPageTitle();
        Assert.assertEquals("Ожидается, что после выхода из УЗ откроется страница Логин(Вход) и появится загаовок Вход", expectedResult, actualResult);
    }
}
