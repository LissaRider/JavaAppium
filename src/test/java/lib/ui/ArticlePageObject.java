package lib.ui;

import lib.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class ArticlePageObject extends MainPageObject {

    protected static String
            ARTICLE_TITLE,
            ARTICLE_BANNER_BY_SUBSTRING_TPL,
            ARTICLE_FOOTER_ELEMENT,
            ARTICLE_TOP_TOOLBAR,
            ARTICLE_SEARCH_INIT_ELEMENT, /* INIT ELEMENT: элемент на странице статьи для перехода на форму поиска */
            OPTIONS_BUTTON,
            OPTIONS_MENU,
            OPTION_BUTTON_BY_NAME_TPL,
            ADD_TO_LIST_OVERLAY,
            NEW_LIST_NAME_INPUT,
            NEW_LIST_CREATION_OK_BUTTON,
            CLOSE_ARTICLE_BUTTON,
            NEW_LIST_CREATION_FORM,
            CREATE_NEW_LIST_BUTTON,
            ADD_TO_LIST_INIT_FORM,
            READING_LIST_BY_NAME_TPL,
            SAVE_FOR_LATER_BUTTON,
            TAP_TO_GO_BACK_BUTTON,
            ADD_TO_WATCHLIST_BUTTON,
            REMOVE_FROM_WATCHLIST_BUTTON;

    public ArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    //region TEMPLATES METHODS
    private static String getOptionByName(String optionTitle) {
        return OPTION_BUTTON_BY_NAME_TPL.replace("{MENU_OPTION}", optionTitle);
    }

    private static String getFolderByName(String folderName) {
        return READING_LIST_BY_NAME_TPL.replace("{FOLDER_NAME}", folderName);
    }

    private static String getBannerBySubstring(String substring) {
        return ARTICLE_BANNER_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    //endregion

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(ARTICLE_TITLE, "Заголовок статьи не найден.", 15);
    }

    public WebElement waitForBannerElement(String substring) {
        String bannerBySubstringXpath = getBannerBySubstring(substring);
        return this.waitForElementVisible(bannerBySubstringXpath, String.format("На странице баннер с текстом '%s' не найден.", substring), 15);
    }

    public String getArticleTitle() {
        WebElement titleElement = waitForTitleElement();
        if (Platform.getInstance().isAndroid()) {
            return titleElement.getAttribute("text");
        } else if (Platform.getInstance().isAndroid()) {
            return titleElement.getAttribute("name");
        } else {
            return titleElement.getText();
        }
    }

    public void swipeToFooter() {
        String errorMessage = "Конец статьи не найден.";
        if (Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(ARTICLE_FOOTER_ELEMENT, errorMessage, 150);
        } else if (Platform.getInstance().isIOS()) {
            this.swipeUpTillElementAppear(ARTICLE_FOOTER_ELEMENT, errorMessage, 150);
        } else {
            this.scrollWebPageTillElementNotVisible(ARTICLE_FOOTER_ELEMENT, errorMessage, 40);
        }
    }

    /**
     * Метод для инициализации добавления статьи в список на Android
     */
    public void selectAddToReadingListOption() {
        this.waitForElementVisible(ARTICLE_TOP_TOOLBAR, "На странице верхняя панель инструментов не найдена.", 15);
        this.waitForElementClickableAndClick(OPTIONS_BUTTON, "Кнопка открытия меню действий со статьей не найдена или недоступна для действий.", 5);
        this.waitForElementVisible(OPTIONS_MENU, "Меню действий со статьей не найдена.", 15);
        String addToReadingListXpath = getOptionByName("Add to reading list");
        this.waitForElementClickableAndClick(addToReadingListXpath, "Опция добавления статьи в список не найдена или недоступна для действий.", 5);
    }

    /**
     * Метод для создани списка и добавления статьи в него на Android
     *
     * @param folderName -существующий список статей
     */
    public void addArticleToNewList(String folderName) {
        selectAddToReadingListOption();
        this.waitForElementVisible(ADD_TO_LIST_INIT_FORM, "Форма добавления статьи в папку не найдена.", 15);
        if (this.isElementPresent(ADD_TO_LIST_OVERLAY))
            waitForElementClickableAndClick(ADD_TO_LIST_OVERLAY, "Кнопка 'GOT IT' не найдена или недоступна для действий.", 5);
        else
            this.waitForElementClickableAndClick(CREATE_NEW_LIST_BUTTON, "Кнопка 'Create' не найдена или недоступна для действий.", 5);
        this.waitForElementVisible(NEW_LIST_CREATION_FORM, "Форма создания новой папки для добавления статьи не найдена.", 15);
        this.waitForElementAndClear(NEW_LIST_NAME_INPUT, "Поле ввода имени новой папки для добавления статьи не найдено.", 5);
        this.waitForElementAndSendKeys(NEW_LIST_NAME_INPUT, folderName, "Невозможно ввести текст в поле ввода имени папки для добавления статьи.", 5);
        this.waitForElementClickableAndClick(NEW_LIST_CREATION_OK_BUTTON, "Кнопка 'OK' не найдена или недоступна для действий.", 5);
    }

    /**
     * Метод для добавления статьи в уже существующий список на Android
     *
     * @param folderName -существующий список статей
     */
    public void addArticleToExistingList(String folderName) {
        selectAddToReadingListOption();
        this.waitForElementVisible(ADD_TO_LIST_INIT_FORM, "Форма добавления статьи в папку не найдена.", 15);
        String folderToAddArticleXpath = getFolderByName(folderName);
        this.waitForElementClickableAndClick(folderToAddArticleXpath, String.format("Папка с именем '%s' не найдена или недоступна для действий.", folderName), 5);
    }

    /**
     * Метод для добавления статьи в список сохраненных на iOS
     */
    public void addArticleToSavedList() {
        if (Platform.getInstance().isMW()) {
            this.removeArticleFromSavedListIfItAdded();
            this.waitForElementClickableAndClick(ADD_TO_WATCHLIST_BUTTON, "Кнопка добавления статьи в список сохраненных не найдена или недоступна для действий.", 10);
        } else {
            this.waitForElementClickableAndClick(SAVE_FOR_LATER_BUTTON, "Кнопка 'Save for later' не найдена или недоступна для действий.", 5);
        }
    }

    public void removeArticleFromSavedListIfItAdded() {
        if (this.isAnyElementPresent(REMOVE_FROM_WATCHLIST_BUTTON)) {
            this.waitForElementClickableAndClick(REMOVE_FROM_WATCHLIST_BUTTON, "Кнопка удаления статьи из списка сохраненных не найдена или недоступна для действий.", 1);
            this.waitForElementVisible(ADD_TO_WATCHLIST_BUTTON, "Кнопка добавления статьи в список сохраненных не найдена после удаления статьи из списка.");
        }
    }

    /**
     * Метод для закрытия страницы и автоматического перехода на главную страицу на Android
     */
    public void closeArticle() {
        this.waitForElementVisible(ARTICLE_TOP_TOOLBAR, "На странице верхняя панель инструментов не найдена.", 15);
        this.waitForElementAndClick(CLOSE_ARTICLE_BUTTON, "Кнопка закрытия статьи не найдена или недоступна для действий.", 10);
    }

    public void assertIsArticleTitlePresent() {
        this.assertElementPresent(ARTICLE_TITLE, "Заголовок статьи не найден.");
    }

    /**
     * Метод для закрытия страницы и возврата на главную страницу на iOS
     */
    public void closeArticleAndReturnToMainPage() {
        this.waitForElementClickableAndClick(TAP_TO_GO_BACK_BUTTON, "Кнопка возврата на главную страницу не найдена или недоступна для действий.", 5);
    }
}