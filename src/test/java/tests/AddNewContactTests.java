package tests;

import config.AppuimConfig;
import models.Auth;
import models.Contact;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.AuthenticationScreen;
import screens.ContactListScreen;

import java.util.Random;


public class AddNewContactTests extends AppuimConfig {

    int i = new Random().nextInt(1000) + 1000;

    @BeforeMethod
    public void precondition() {
        new AuthenticationScreen(driver)
                .login(Auth.builder()
                        .email("abc@def.com")
                        .password("$Abcdef12345")
                        .build());
    }

    @Test
    public void addNewContactPositive() {

        Contact contact = Contact.builder()
                .name("Add_" + i)
                .lastName("Positive")
                .email("add_" + i + "@mail.com")
                .phone("1234567" + i)
                .address("Haifa")
                .description("Add_" + i + "_NewPositive")
                .build();


        Assert.assertTrue(
                new ContactListScreen(driver)
                        .openContactForm()
                        .fillContactForm(contact)
                        .submitContact()
                        .isFindAddedContactListPresent("QA38 Automation", "123456781944")
        );
    }

}
