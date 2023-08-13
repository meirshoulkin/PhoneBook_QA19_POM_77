package screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import models.Contact;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

public class ContactListScreen extends BaseScreen{


    public ContactListScreen(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    String phoneNumber;

    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/action_bar']/android.widget.TextView")
    MobileElement activityViewText;

    @FindBy(xpath = "//*[@content-desc='More options']")
    MobileElement moreOptions;

    @FindBy(id="com.sheygam.contactapp:id/add_contact_btn")
    MobileElement addButton;

    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/title']")
    MobileElement logoutButton;
    @FindBy(xpath = "//*[@resource-id='android:id/button1']")
    MobileElement yesButton;
    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/emptyTxt']")
    MobileElement emptyTxt;

    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/rowPhone']")
    MobileElement contactPhone;
    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/rowName']")
    List<MobileElement> names;
    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/rowPhone']")
    List<MobileElement> phones;
    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/rowContainer']")
    List<MobileElement> contacts;


    public boolean isContactListActivityPresent(){
        return shouldHave(activityViewText, "Contact list", 5);
    }

    public AuthenticationScreen logout(){
        moreOptions.click();
        logoutButton.click();
        return new AuthenticationScreen(driver);
    }

    public AddNewContactScreen openContactForm(){
//        waitElement(addButton, 10);
        if(isDisplayedWithException(addButton)){
            addButton.click();
        }
        return new AddNewContactScreen(driver);
    }

    public ContactListScreen isContactAdded(Contact contact){

        boolean checkName = checkContainsText(names, contact.getName() + " " + contact.getLastName());
        boolean checkPhone = checkContainsText(phones, contact.getPhone());
        Assert.assertTrue(checkName && checkPhone);
        return this;
    }

    private boolean checkContainsText(List<MobileElement> list, String text) {

        for (MobileElement element : list){
            if(element.getText().contains(text)) return true;
        }
        return false;
    }


    public ContactListScreen removeOneContact(){
        waitElement(addButton, 5);
        MobileElement contact = contacts.get(0);
        Rectangle rect = contact.getRect();
        phoneNumber = contactPhone.getText();

        int xStart = rect.getX() + rect.getWidth() / 8;
        int xEnd = xStart + (rect.getWidth() * 6) / 8;
        int y = rect.getY() + rect.getHeight() / 2;

        TouchAction<?> touchAction = new TouchAction<>(driver);
        touchAction.longPress(PointOption.point(xStart, y))
                .moveTo(PointOption.point(xEnd, y))
                .release()
                .perform();

        waitElement(yesButton, 5);
        yesButton.click();
        pause(3000);
        return this;


    }

    public ContactListScreen isOneContactRemoved(){
        Assert.assertFalse(phones.contains(phoneNumber));
        return this;
    }

    public ContactListScreen removeAllContacts(){
        waitElement(addButton, 5);
        while (contacts.size() > 0){
            removeOneContact();
        }
        return this;
    }

    public ContactListScreen isNoContactsMessage(){
        Assert.assertTrue(shouldHave(emptyTxt, "No Contacts. Add One more!", 5));
        return this;
    }

    public ContactListScreen provideContacts(){
        while (contacts.size() < 3){
            addContact();
        }
        return this;
    }

    public void addContact(){

        int i = new Random().nextInt(1000) + 1000;

        Contact contact = Contact.builder()
                .name("Add_" + i)
                .lastName("Positive")
                .email("add_" + i + "@mail.com")
                .phone("1234567" + i)
                .address("Haifa")
                .description("Add_" + i + "_NewPositive")
                .build();

        new ContactListScreen(driver)
                .openContactForm()
                .fillContactForm(contact)
                .submitContact();
    }

    public EditContactScreen updateOneContact(){
        waitElement(addButton, 5);
        MobileElement contact = contacts.get(0);
        Rectangle rect = contact.getRect();

        int xEnd = rect.getX() + rect.getWidth() / 8;
        int xStart = xEnd + (rect.getWidth() * 6) / 8;
        int y = rect.getY() + rect.getHeight() / 2;

        TouchAction<?> touchAction = new TouchAction<>(driver);
        touchAction.longPress(PointOption.point(xStart, y))
                .moveTo(PointOption.point(xEnd, y))
                .release()
                .perform();

        return new EditContactScreen(driver);
    }

    public boolean isContactContains(String text){
        contacts.get(0).click();
        Contact contact = new ViewContactScreen(driver).viewContactObject();
        driver.navigate().back();
        return contact.toString().contains(text);
    }

    public ContactListScreen scrollList(){
        waitElement(addButton, 5);
        MobileElement contact = contacts.get(contacts.size() - 1);
        Rectangle rect = contact.getRect();

        int xRow = rect.getX() + rect.getWidth() / 2;
        int yRow = rect.getY() + rect.getHeight() / 2;

        TouchAction<?> touchAction = new TouchAction<>(driver);
        touchAction.longPress(PointOption.point(xRow, yRow))
                .moveTo(PointOption.point(xRow, 0))
                .release()
                .perform();

        return this;
    }

    public boolean isEndOfList(){
        // your brilliant code is here
        return false;
    }

    public ContactListScreen isContactAddedScroll(Contact contact){

        return this;
    }
}
