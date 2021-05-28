package lib.ui;

import io.qameta.allure.Step;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WelcomePageObject extends MainPageObject {

    public static final String
            LEARN_MORE_ABOUT_WIKIPEDIA_LINK = "id:Learn more about Wikipedia",
            NEW_WAYS_TO_EXPLORE_TEXT = "id:New ways to explore",
            ADD_OR_EDIT_PREFERRED_LANG_LINK = "id:Add or edit preferred languages",
            LEARN_MORE_ABOUT_DATA_COLLECTED_LINK = "id:Learn more about data collected",
            NEXT_BUTTON = "id:Next",
            GET_STARTED_BUTTON = "id:Get started",
            SKIP_BUTTON = "id:Skip";

    public WelcomePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    @Step("Waiting for the link 'Learn more about Wikipedia'")
    public void waitForLearnMoreLink() {
        this.waitForElementPresent(LEARN_MORE_ABOUT_WIKIPEDIA_LINK, "Ссылка 'Learn more about Wikipedia' не найдена.", 10);
    }

    @Step("Waiting for the link 'New ways to explore'")
    public void waitForNewWayToExploreText() {
        this.waitForElementPresent(NEW_WAYS_TO_EXPLORE_TEXT, "Заголовок страницы 'New ways to explore' не найден.", 10);
    }

    @Step("Waiting for the link 'Add or edit preferred languages'")
    public void waitForAddOrEditPreferredLangText() {
        this.waitForElementPresent(ADD_OR_EDIT_PREFERRED_LANG_LINK, "Ссылка 'Add or edit preferred languages' не найдена.", 10);
    }

    @Step("Waiting for the link 'Learn more about data collected'")
    public void waitForLearnMoreAboutDataCollectedText() {
        this.waitForElementPresent(LEARN_MORE_ABOUT_DATA_COLLECTED_LINK, "Ссылка 'Learn more about data collected' не найдена.", 10);
    }

    @Step("Clicking the button 'Next'")
    public void clickNextButton() {
        this.waitForElementClickableAndClick(NEXT_BUTTON, "Кнопка 'Next' не найдена или недоступна для действий.", 10);
    }

    @Step("Clicking the button 'Get started'")
    public void clickGetStartedButton() {
        this.waitForElementClickableAndClick(GET_STARTED_BUTTON, "Кнопка 'Get started' не найдена или недоступна для действий.", 10);
    }

    @Step("Clicking the button 'Skip'")
    public void clickSkip() {
        this.waitForElementClickableAndClick(SKIP_BUTTON, "Кнопка 'Skip' не найдена или недоступна для действий.", 5);
    }
}