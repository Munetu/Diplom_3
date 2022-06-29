import api.UserAPI;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import po.LoginPage;
import model.UserCredentials;
import model.UserPOJO;

public class GoToProfilePageTest {
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
    @DisplayName("Проверка перехода на страницу Личный кабинет со страницы Конструктор")
    public void goToProfilePageFromBurgerConstructorPage() {
        String expectedResult = "Выход";
        String actualResult = Selenide.open(LoginPage.URL, LoginPage.class)
                .login(credentials.getEmail(), credentials.getPassword())
                .goToProfilePage()
                .getExitText();
        Assert.assertEquals("Ожидается, что откроется страница Profile и появится кнопка Выход", expectedResult, actualResult);
    }

    @Test
    @DisplayName("Проверка перехода на страницу Личный кабинет со страницы Лента Заказов")
    public void goToProfilePageFromFeedPage() {
        String expectedResult = "Выход";
        String actualResult = Selenide.open(LoginPage.URL, LoginPage.class)
                .login(credentials.getEmail(), credentials.getPassword())
                .goToFeedPage()
                .waitForLoadFeedPage()
                .goToProfilePage()
                .getExitText();
        Assert.assertEquals("Ожидается, что откроется страница Profile и появится кнопка Выход", expectedResult, actualResult);
    }
}
