package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.github.javafaker.Faker;
import data.CardInfo;
import data.DateGenerator;
import data.RegistrationInfo;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryOrderTest {

    @BeforeAll
    static void addListener(){
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void removeListener(){
        SelenideLogger.removeListener("allure");
    }

    DateGenerator dateGenerator = new DateGenerator();

    @Test
    void shouldGenerateInfo(){
        CardInfo cardInfo = RegistrationInfo.Registration.generateInfo("ru");
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(cardInfo.getCity());
        String planningDate = dateGenerator.generateDate(11);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue(cardInfo.getName());
        $("[data-test-id=phone] input").setValue(cardInfo.getPhone());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__content").shouldHave(Condition.text("запланирована на " + planningDate), Duration.ofSeconds(20));
        $("[data-test-id=city] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=city] input").setValue(cardInfo.getCity());
        planningDate = dateGenerator.generateDate(15);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id=name] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=name] input").setValue(cardInfo.getName());
        $("[data-test-id=phone] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=phone] input").setValue(cardInfo.getPhone());
        $(withText("Запланировать")).click();
        $("[data-test-id=replan-notification] button").click();
        $("[data-test-id=success-notification] .notification__content").shouldHave(Condition.text("запланирована на " + planningDate), Duration.ofSeconds(20));
    }

}
