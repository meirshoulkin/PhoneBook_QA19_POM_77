package tests;

import config.AppuimConfig;
import models.Auth;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import screens.AuthenticationScreen;
import screens.ContactListScreen;

import java.util.Random;

public class EditContactTests extends AppuimConfig {

    int i = new Random().nextInt(1000) + 1000;

    @BeforeClass
    public void precondition(){
        new AuthenticationScreen(driver)
                .login(Auth.builder()
                        .email("abc@def.com")
                        .password("$Abcdef12345")
                        .build());
    }

    @Test
    public void editOneContact(){
        String text = "Update_" + i;
        new ContactListScreen(driver)
                .updateOneContact()
                .updateName(text)
                .submitEditContactForm()
                .isContactContains(text);
    }

}
