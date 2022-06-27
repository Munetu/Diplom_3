package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.UserCredentials;
import pojo.UserPOJO;

public class UserAPI extends MainAPI {

    @Step("Послать POST запрос на ручку /auth/register")
    public Response sendPostRequestRegisterUser(UserPOJO userPOJO) {
        return reqSpec.body(userPOJO)
                .when()
                .post("/auth/register");
    }

    @Step("Послать POST запрос на ручку /auth/login")
    public Response sendPostLoginUser(UserCredentials credentials) {
        return reqSpec.body(credentials)
                .when()
                .post("/auth/login");
    }

    @Step("Послать DELETE запрос на ручку /auth/user c accessToken")
    public Response sendDeleteUser(String token) {
        String pureToken = token.substring(7);
        return reqSpec.auth().oauth2(pureToken)
                .when()
                .delete("/auth/user");
    }

}
