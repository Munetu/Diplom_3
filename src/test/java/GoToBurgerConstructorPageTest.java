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
import po.LoginPage;
import pojo.UserCredentials;
import pojo.UserPOJO;

public class GoToBurgerConstructorPageTest {
    private UserPOJO user;
    private UserAPI userAPI;
    private Response response;
    private boolean created;
    private boolean loginSuccess;
    private Response loginResponse;
    private String actualMessage;
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
//        user = UserPOJO.getRandom();
        user = new UserPOJO(email, password, name);
        response = userAPI.sendPostRequestRegisterUser(user);
        created = userCreatedSuccess(response);
        accessToken = userAccessToken(response);
        credentials = UserCredentials.from(user);
    }

    @After
    public void teardown() {
        Selenide.closeWindow();
        if (created) {
            Response deleteResponse = userAPI.sendDeleteUser(accessToken);
            boolean deleted = userDeletedSuccess(deleteResponse);
        }
    }

    @Test
    @DisplayName("Проверка перехода на страницу Конструктор со страницы Личный Кабинет")
    public void goToBurgerConstructorPageFromProfilePageByNav() {
        String expectedResult = "Оформить заказ";
        String actualResult = Selenide.open(LoginPage.URL, LoginPage.class)
                .login(credentials.getEmail(), credentials.getPassword())
                .goToProfilePage()
                .waitForLoadProfilePage()
                .goToBurgerConstructorPageByNav()
                .getButtonMakeOrderText();
        Assert.assertEquals("Ожидается, что при переходе из страницы Личный Кабинет откроется страница Консруктор и появится кнопка Оформить заказ", expectedResult, actualResult);
    }

    @Test
    @DisplayName("Проверка перехода на страницу Конструктор со страницы Личный Кабинет")
    public void goToBurgerConstructorPageFromProfilePageByLogo() {
        String expectedResult = "Оформить заказ";
        String actualResult = Selenide.open(LoginPage.URL, LoginPage.class)
                .login(credentials.getEmail(), credentials.getPassword())
                .goToProfilePage()
                .waitForLoadProfilePage()
                .goToBurgerConstructorPageByLogo()
                .getButtonMakeOrderText();
        Assert.assertEquals("Ожидается, что при переходе из страницы Личный Кабинет откроется страница Консруктор и появится кнопка Оформить заказ", expectedResult, actualResult);
    }

    @Step("Получить accessToken")
    public String userAccessToken(Response response) {
        return response.then()
                .extract()
                .path("accessToken");
    }

    @Step("Получить статус об успешном создании пользователя - 200")
    public boolean userCreatedSuccess(Response response) {
        return response.then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("success");
    }

    @Step("Получить статус об успешном удалении пользователя - 202")
    public boolean userDeletedSuccess(Response response) {
        return response.then().log().all()
                .assertThat()
                .statusCode(202)
                .extract()
                .path("success");
    }
}
