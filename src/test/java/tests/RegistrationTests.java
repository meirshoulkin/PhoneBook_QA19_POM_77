package tests;

import config.AppuimConfig;
import models.Auth;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import screens.AuthenticationScreen;
import screens.ContactListScreen;
import screens.SplashScreen;

import java.util.Random;



public class RegistrationTests extends AppuimConfig {

    int i = new Random().nextInt(1000) + 1000;

    @Test
    public void registrationPositive(){

        new AuthenticationScreen(driver)
                .fillEmail("abc_" + i + "@mail.com")
                .fillPassword("$Abcdef12345")
                .submitRegistration()
                .isContactListActivityPresent();

        new ContactListScreen(driver).logout();
    }
    @Test
    public void registrationPositiveModel(){

        new AuthenticationScreen(driver)
                .registration(Auth.builder()
                        .email("abc_" + i + "@mail.com")
                        .password("$Abcdef12345")
                        .build())
                .isContactListActivityPresent();

        new ContactListScreen(driver).logout();
    }
    @Test
    public void registrationNegativeWrongEmail(){

        new AuthenticationScreen(driver)
                .fillEmail("abc_" + i + "mail.com")
                .fillPassword("$Abcdef12345")
                .submitRegistrationNegative()
                .isErrorMessageHasText("{username=must be a well-formed email address}");
    }
    @Test
    public void registrationNegativeWrongPassword(){

        new AuthenticationScreen(driver)
                .fillEmail("abc_" + i + "@mail.com")
                .fillPassword("Abcdef12345")
                .submitRegistrationNegative()
                .isErrorMessageHasText("{password= At least 8 characters; Must contain at least 1 uppercase letter, 1 lowercase letter, and 1 number; Can contain special characters [@$#^&*!]}");
    }

    @AfterMethod
    public void postcondition(){

        new SplashScreen(driver);
    }
}
