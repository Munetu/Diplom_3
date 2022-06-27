import api.UserAPI;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import po.BurgerСonstructorPage;
import po.RegisterPage;

public class CheckBurgerConstructorPageSectionsTest {

    @Before
    public void setUp() {
        Configuration.browserSize = "1600x900";
        Configuration.browserPosition = "0x0";
    }

    @After
    public void teardown() {
        Selenide.closeWindow();
    }

    @Test
    @DisplayName("Проверка валидации пароля")
    public void checkSectionBuns() {
        //Тест
        String expectedResult = "Булки";
        String actualResult = Selenide.open(BurgerСonstructorPage.URL, BurgerСonstructorPage.class)
                .checkSectionBuns();
        //Проверка результатов
        Assert.assertEquals("Ожидается, что при нажатии на раздел Булки, произойдет скролл до заголовка Булки", expectedResult, actualResult);
    }

    @Test
    @DisplayName("Проверка валидации пароля")
    public void checkSectionSauce() {
        //Тест
        String expectedResult = "Соусы";
        String actualResult = Selenide.open(BurgerСonstructorPage.URL, BurgerСonstructorPage.class)
                .checkSectionSauce();
        //Проверка результатов
        Assert.assertEquals("Ожидается, что при нажатии на раздел Соусы, произойдет скролл до заголовка Соусы", expectedResult, actualResult);
    }

    @Test
    @DisplayName("Проверка валидации пароля")
    public void checkSectionFilling() {
        //Тест
        String expectedResult = "Начинки";
        String actualResult = Selenide.open(BurgerСonstructorPage.URL, BurgerСonstructorPage.class)
                .checkSectionFilling();
        Assert.assertEquals("Ожидается, что при нажатии на раздел Начинки, произойдет скролл до заголовка Начинки", expectedResult, actualResult);
    }
}
