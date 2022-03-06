package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;
import data.CardInfo;
import data.RegistrationInfo;
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

    private static Faker faker;

    @BeforeAll
    static void SetUpAll(){
        faker = new Faker(new Locale("ru"));
    }

    String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouldGenerateInfo(){
        CardInfo cardInfo = RegistrationInfo.Registration.generateInfo("ru");
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(cardInfo.getCity());
        String planningDate = generateDate(11);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue(cardInfo.getName());
        $("[data-test-id=phone] input").setValue(cardInfo.getPhone());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__content").shouldHave(Condition.text("запланирована на " + planningDate), Duration.ofSeconds(20));
        $("[data-test-id=city] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=city] input").setValue(cardInfo.getCity());
        planningDate = generateDate(15);
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
