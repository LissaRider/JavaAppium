package lib.ui;

import org.openqa.selenium.remote.RemoteWebDriver;

public class AuthorizationPageObject extends MainPageObject {

    public static final String
            LOGIN_BUTTON = "xpath://*[contains(@class,'drawer-container__drawer')]//a[text()='Log in']",
            LOGIN_INPUT = "css:input[name='wpName']",
            PASSWORD_INPUT = "css:input[name='wpPassword']",
            SUBMIT_BUTTON = "id:wpLoginAttempt";

    public AuthorizationPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void clickAuthButton() {
        this.waitForElementVisible(LOGIN_BUTTON, "Кнопка перехода на форму авторизации не найдена.", 5);
        this.waitForElementClickableAndClick(LOGIN_BUTTON, "Кнопка перехода на форму авторизации не найдена.", 5);
    }

    public void enterLoginData(String login, String password) {
        this.waitForElementAndSendKeys(LOGIN_INPUT, login, "Поле ввода логина не найдено.", 5);
        this.waitForElementAndSendKeys(PASSWORD_INPUT, password, "Поле ввода пароля не найдено.", 5);
    }

    public void submitForm() {
        this.waitForElementClickableAndClick(SUBMIT_BUTTON, "Кнопка входа по логину и паролю не найдена.", 5);
    }
}