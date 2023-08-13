package tests;

import config.AppuimConfig;
import models.Auth;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.AuthenticationScreen;
import screens.ContactListScreen;

public class RemoveContactTests extends AppuimConfig {

    @BeforeClass
    public void precondition(){
        new AuthenticationScreen(driver)
                .login(Auth.builder()
                        .email("abc_1203@mail.com")
                        .password("$Abcdef12345")
                        .build());
    }

    @BeforeMethod
    public void providerContacts(){
        new ContactListScreen(driver)
                .provideContacts();
    }

    @Test
    public void removeOneContactPositive(){
        new ContactListScreen(driver).removeOneContact().isOneContactRemoved();
    }

    @Test
    public void removeAllContactsPositive(){
        new ContactListScreen(driver)
                .removeAllContacts()
                .isNoContactsMessage();
    }
}
