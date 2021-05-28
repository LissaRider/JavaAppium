package lib.ui;

import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

import static lib.ui.MyListsPageObject.MY_LISTS_PAGE_TITLE;

abstract public class NavigationUI extends MainPageObject {

    protected static String
            MAIN_NAV_TAB_ELEMENT,
            READING_LISTS_LINK,
            OPEN_NAVIGATION;

    public NavigationUI(RemoteWebDriver driver) {
        super(driver);
    }

    @Step("Opening the article main menu")
    public void openNavigation() {
        if (Platform.getInstance().isMW()) {
            this.waitForElementClickableAndClick(OPEN_NAVIGATION, "Кнопка открытия главного меню статьи не найдена или недоступна для действий.", 5);
        } else {
            System.out.printf("  Внимание! Метод openNavigation() не работает для платформы '%s'.%n", Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Opening the page with saved articles/reading lists")
    public void clickMyLists() {
        if (Platform.getInstance().isMW()) {
            this.tryClickElementWithFewAttempts(READING_LISTS_LINK, "Кнопка перехода к спискам не найдена или недоступна для действий.", 5);
        } else {
            this.waitForElementVisible(MAIN_NAV_TAB_ELEMENT, "На главной странице панель навигации не найдена.", 15);
            this.waitForElementClickableAndClick(READING_LISTS_LINK, "Кнопка перехода к спискам не найдена или недоступна для действий.", 5);
        }
        this.waitForElementPresent(MY_LISTS_PAGE_TITLE, "Переход к списку личных папок со статьями не был выполнен.", 15);
    }
}