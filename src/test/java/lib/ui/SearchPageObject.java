package lib.ui;

import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.List;
import java.util.regex.Pattern;

abstract public class SearchPageObject extends MainPageObject {

    protected static String
            MAIN_PAGE_SEARCH_INIT_ELEMENT, /* INIT ELEMENT: элемент на главной странице для перехода на форму поиска */
            SEARCH_INPUT_FIELD, /* INPUT FIELD: поле ввода значения для поиска */
            SEARCH_CANCEL_BUTTON, /* BUTTON: кнопка для очистки поля ввода или закрытия формы поиска */
            SEARCH_INPUT_CLEAR_BUTTON, /* BUTTON: кнопка для очистки поля ввода или закрытия формы поиска (только MW) */
            SEARCH_EMPTY_RESULT_ELEMENT,/* SEARCH RESULT: форма, отображающаяся, если по заданному запросу ничего не найдено */
            SEARCH_RESULT_LIST, /* SEARCH RESULT: список всех найденных результатов */
            SEARCH_RESULT_LIST_ITEM, /* SEARCH RESULT: элемент списка всех найденных результатов (результат поиска) */
            SEARCH_RESULT_BY_LIST_ITEM_SUBSTRING_TPL, /* SEARCH RESULT: элемент списка всех найденных результатов с указанием текста в нем */
            SEARCH_RESULT_LIST_ITEM_TITLE,  /* SEARCH RESULT: заголовок статьи в элементе списка */
            SEARCH_RESULT_BY_LIST_ITEM_TITLE_TPL, /* SEARCH RESULT: заголовок статьи в элементе списка всех найденных результатов с указанием текста в нем */
            SEARCH_RESULT_BY_LIST_ITEM_TITLE_AND_DESCRIPTION_TPL, /* SEARCH RESULT: элемент списка всех найденных результатов с указанием заголовка и описания в нем */
            RECENT_SEARCHES_YET_ELEMENT; /* SEARCH ELEMENT: элемент с текстом, который отображается при очистке поля ввода для поиска (только iOS) */

    public SearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    //region TEMPLATES METHODS
    @Step("Get the result search by substring '{substring}'")
    private static String getResultSearchElementWithSubstring(String substring) {
        return SEARCH_RESULT_BY_LIST_ITEM_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    @Step("Get the result search by title '{articleTitle}'")
    private static String getResultSearchElementWithTitle(String articleTitle) {
        return SEARCH_RESULT_BY_LIST_ITEM_TITLE_TPL.replace("{TITLE}", articleTitle.replace("\n", ""));
    }

    @Step("Get the found article by '{articleTitle}' and '{articleDescription}'")
    private static String getArticleWithTitleAndDescription(String articleTitle, String articleDescription) {
        return SEARCH_RESULT_BY_LIST_ITEM_TITLE_AND_DESCRIPTION_TPL
                .replace("{ARTICLE_TITLE}", articleTitle)
                .replace("{ARTICLE_DESCRIPTION}", articleDescription);
    }
    //endregion

    @Step("Waiting for the article with title '{title}' and '{description}' to appear")
    public void waitForElementByTitleAndDescription(String title, String description) {
        String articleWithTitleAndDescriptionXpath = "";
        if (description.contains("|")) {
            String[] values = description.split(Pattern.quote("|"), 2);
            String value1 = values[0];
            String value2 = values[1];
            articleWithTitleAndDescriptionXpath = !isElementPresent(getArticleWithTitleAndDescription(title, value1))
                    ? getArticleWithTitleAndDescription(title, value2)
                    : getArticleWithTitleAndDescription(title, value1);
        } else {
            articleWithTitleAndDescriptionXpath = getArticleWithTitleAndDescription(title, description);
        }
        this.waitForElementPresent(
                articleWithTitleAndDescriptionXpath,
                String.format("Не найдена статья с заголовком '%s' и описанием '%s'.", title, description),
                15
        );
    }

    @Step("Initializing the search field")
    public void initSearchInput() {
        String searchWikiMessageError = "элемент 'Search Wikipedia' не найден или недоступен для действий";
        if (this.isElementPresent(MAIN_PAGE_SEARCH_INIT_ELEMENT))
            this.waitForElementClickableAndClick(MAIN_PAGE_SEARCH_INIT_ELEMENT, String.format("На главной странице %s.", searchWikiMessageError), 5);
        else
            this.waitForElementClickableAndClick(ArticlePageObject.ARTICLE_SEARCH_INIT_ELEMENT, String.format("На странице статьи %s.", searchWikiMessageError), 5);
        this.waitForElementPresent(SEARCH_INPUT_FIELD, "Поле ввода текста для поиска не найдено.");
    }

    @Step("Waiting for button to cancel search result")
    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Кнопка отмены поиска не найдена.");
    }

    @Step("Waiting for search cancel button to disappear")
    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Кнопка отмены поиска все ещё отображается.", 5);
    }

    @Step("Clearing search input")
    public void clearSearchInput() {
        if (Platform.getInstance().isMW())
            this.waitForElementClickableAndClick(SEARCH_INPUT_CLEAR_BUTTON, "Кнопка очистки поля ввода текста для поиска не найдено.", 5);
        else
            this.waitForElementAndClear(SEARCH_INPUT_FIELD, "Поле ввода текста для поиска не найдено.", 5);
        String errorMessage = "Список результатов все ещё отображается.";
        if (Platform.getInstance().isIOS())
            this.waitForElementVisible(RECENT_SEARCHES_YET_ELEMENT, errorMessage, 15);
        else
            this.waitForElementNotPresent(SEARCH_RESULT_LIST, errorMessage, 15);
    }

    @Step("Get the search results list")
    public List<WebElement> getSearchResultsList() {
        return this.waitForPresenceOfAllElements(SEARCH_RESULT_LIST_ITEM_TITLE, "По заданному запросу ничего не найдено.", 15);
    }

    @Step("Clicking button to cancel search result")
    public void clickCancelButton() {
        this.waitForElementClickableAndClick(SEARCH_CANCEL_BUTTON, "Кнопка отмены поиска не найдена или недоступна для действий.", 5);
    }

    @Step("Typing '{substring}' to the search line")
    public void typeSearchLine(String substring) {
        this.waitForElementAndSendKeys(SEARCH_INPUT_FIELD, substring, "Поле ввода текста для поиска не найдено.", 5);
    }

    @Step("Waiting for search result with substring '{substring}'")
    public WebElement waitForSearchResult(String substring) {
        String searchResultXpath = getResultSearchElementWithSubstring(substring);
        return this.waitForElementPresent(searchResultXpath, String.format("Текст '%s' не найден.", substring), 15);
    }

    @Step("Waiting for amount of found results more than '{resultsCount}'")
    public void waitForNumberOfResultsMoreThan(int resultsCount) {
        this.waitForNumberOfElementsToBeMoreThan(SEARCH_RESULT_LIST_ITEM, resultsCount, String.format("Количество найденных результатов меньше ожидаемого: %d.", resultsCount), 15);
    }

    @Step("Waiting for search result and select an article by substring in article title")
    public void waitForNotEmptySearchResults() {
        this.waitForNumberOfResultsMoreThan(0);
    }

    @Step("Waiting for search result and select an article by substring in article title")
    public void clickByArticleWithSubstring(String substring) {
        String searchResultXpath = getResultSearchElementWithSubstring(substring);
        if (Platform.getInstance().isMW()) {
            if (isAnyElementPresent(searchResultXpath)) {
                this.waitForElementClickableAndClick(searchResultXpath, String.format("Текст '%s' не найден или элемент недоступен для действий.", substring), 10);
            } else {
                this.clickByArticleWithTitle(substring);
            }
        } else {
            this.waitForElementClickableAndClick(searchResultXpath, String.format("Текст '%s' не найден или элемент недоступен для действий.", substring), 10);
        }
    }

    @Step("Clicking on the found article with the title '{articleTitle}'")
    public void clickByArticleWithTitle(String articleTitle) {
        String articleTitleXpath = getResultSearchElementWithTitle(articleTitle);
        this.waitForElementClickableAndClick(articleTitleXpath, String.format("Статья с заголовком '%s' не найдена или недоступна для действий.", articleTitle), 5);
    }

    @Step("Get amount of found articles")
    public int getAmountOfFoundArticles() {
        this.waitForElementVisible(SEARCH_RESULT_LIST_ITEM, "Ничего не найдено по заданному запросу.", 15);
        return getAmountOfElements(SEARCH_RESULT_LIST_ITEM);
    }

    @Step("Waiting for empty result label")
    public void waitForEmptyResultsLabel() {
        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT, "Найдены результаты по запросу поиска.", 15);
    }

    @Step("Making sure there are no results fo the search")
    public void assertThereIsNoResultOfSearch() {
        this.assertElementNotPresent(SEARCH_RESULT_LIST_ITEM, "Найдены результаты по запросу поиска.");
    }

    @Step("Asserting that the search placeholder has text '{placeholder}'")
    public void assertSearchPlaceholderHasText(String placeholder) {
        String errorMessage = "Поле ввода для поиска статьи содержит некорректный текст.";
        if (Platform.getInstance().isMW())
            this.assertElementHasPlaceholder(SEARCH_INPUT_FIELD, placeholder, errorMessage);
        else
            this.assertElementHasText(SEARCH_INPUT_FIELD, placeholder, errorMessage);
    }

    @Step("Searching for '{searchLine}'")
    public void searchByValue(String searchLine) {
        initSearchInput();
        typeSearchLine(searchLine);
    }
}