package tests;

import config.AppuimConfig;
import org.testng.Assert;
import org.testng.annotations.Test;
import screens.SplashScreen;

public class LoginTests extends AppuimConfig {

    @Test
    public void loginPositive(){

        Assert.assertTrue(
                new SplashScreen(driver)
                        .gotoAuthenticationScreen()
                        .fillEmail("abc@def.com")
                        .fillPassword("$Abcdef12345")
                        .submitLogin()
                        .isContactListActivityPresent()
        );
    }

}
