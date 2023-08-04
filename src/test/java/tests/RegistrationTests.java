package tests;

import config.AppuimConfig;
import org.testng.Assert;
import org.testng.annotations.Test;
import screens.SplashScreen;

public class RegistrationTests extends AppuimConfig {

    @Test(invocationCount = 5)
    public void registrationPositive(){
        int i = (int)(System.currentTimeMillis()/1000)%3600;
        Assert.assertTrue(
                new SplashScreen(driver)
                        .gotoAuthenticationScreen()
                        .fillEmail("abc" + i + "@def.com")
                        .fillPassword("$Abcdef12345")
                        .submitRegistration()
                        .isContactListActivityPresent()
        );
    }

}
