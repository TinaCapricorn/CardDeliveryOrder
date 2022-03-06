package data;

import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;

import java.util.Locale;

@UtilityClass
public class RegistrationInfo {

    @UtilityClass
    public static class Registration{
        public static CardInfo generateInfo (String locale){
            Faker faker = new Faker(new Locale(locale));
            return new CardInfo(
                    faker.address().city(),
                    faker.name().firstName().concat(" ").concat(faker.name().lastName()),
                    faker.phoneNumber().phoneNumber()
            );
        }
    }
}
