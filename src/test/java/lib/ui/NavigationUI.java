package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class NavigationUI extends MainPageObject {

    protected static String
            MAIN_NAV_TAB_ELEMENT,
            READING_LISTS_LINK,
            MY_LISTS_PAGE_TITLE;

    public NavigationUI(RemoteWebDriver driver) {
        super(driver);
    }

    public void clickMyLists() {
        this.waitForElementVisible(MAIN_NAV_TAB_ELEMENT, "На главной странице панель навигации не найдена.", 15);
        this.waitForElementClickableAndClick(READING_LISTS_LINK, "Кнопка перехода к спискам не найдена или недоступна для действий.", 5);
        this.waitForElementPresent(MY_LISTS_PAGE_TITLE, "Переход к списку личных папок со статьями не был выполнен.", 15);
    }
}