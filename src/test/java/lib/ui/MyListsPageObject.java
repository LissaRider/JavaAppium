package lib.ui;

import lib.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.ArrayList;
import java.util.List;

abstract public class MyListsPageObject extends MainPageObject {

    protected static String
            MY_LISTS_PAGE_TITLE,
            FOLDER_BY_NAME_TPL,
            ARTICLE_LIST,
            ARTICLE_LIST_ITEM,
            ARTICLE_BY_TITLE,
            ARTICLE_BY_TITLE_TPL,
            SYNC_YOUR_SAVED_ARTICLES_POPUP,
            CLOSE_SYNC_POPUP_BUTTON,
            REMOVE_FROM_SAVED_LIST_BUTTON_TPL;

    public MyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    //region TEMPLATES METHODS
    private static String getFolderXpathByName(String folderName) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", folderName);
    }

    private static String getArticleXpathByTitle(String articleTitle) {
        return ARTICLE_BY_TITLE_TPL.replace("{ARTICLE_TITLE}", articleTitle);
    }

    private static String getRemoveButtonByTitle(String articleTitle) {
        return REMOVE_FROM_SAVED_LIST_BUTTON_TPL.replace("{ARTICLE_TITLE}", articleTitle);
    }
    //endregion

    public void openFolderByName(String folderName) {
        this.waitForNumberOfElementsToBeMoreThan(ARTICLE_LIST, 0, "В списке 'My lists' ни одной папки не найдено.", 15);
        String folderNameXpath = getFolderXpathByName(folderName);
        this.waitForElementAndClick(folderNameXpath, String.format("Папка '%s' не найдена или недоступна для действий.", folderName), 5);
        waitForElementVisible(folderNameXpath, String.format("Заголовок папки '%s' не найден.", folderName), 15);
    }

    public List getArticleTitleList(){
        List<WebElement> webElements = this.waitForPresenceOfAllElements(ARTICLE_BY_TITLE, "Не найдено ни одного заголовка статьи.", 15);
        List<String> titles = new ArrayList<>();
        for (WebElement element: webElements) {
            titles.add(element.getText());
        }
        return titles;
    }

    public void waitForArticleToAppearByTitle(String articleTitle) {
        String articleTitleXpath = getArticleXpathByTitle(articleTitle);
        this.waitForElementPresent(articleTitleXpath, String.format("В списке статья '%s' не найдена.", articleTitle), 15);
    }

    public void waitForArticleToDisappearByTitle(String articleTitle) {
        String articleTitleXpath = getArticleXpathByTitle(articleTitle);
        if (Platform.getInstance().isMW()) {
            long finish = System.currentTimeMillis() + 5000; // end time
            while (isAnyElementPresent(articleTitleXpath) && (System.currentTimeMillis() < finish)) {
                driver.navigate().refresh();
            }
        } else {
            this.waitForElementNotPresent(articleTitleXpath, String.format("Статья '%s' не удалилась из списка.", articleTitle), 15);
        }
    }

    public void swipeByArticleToDelete(String articleTitle) {
        this.waitForArticleToAppearByTitle(articleTitle);
        String articleTitleXpath = getArticleXpathByTitle(articleTitle);
        if (Platform.getInstance().isAndroid() || Platform.getInstance().isIOS()) {
            this.swipeElementToLeft(articleTitleXpath, String.format("В списке статья '%s' не найдена.", articleTitle));
        } else {
            String removeLocator = getRemoveButtonByTitle(articleTitle);
            this.waitForElementClickableAndClick(removeLocator, "Кнопка удаления статьи в списке сохраненных не найдена или недоступна для действий.", 10);
        }
        if (Platform.getInstance().isIOS())
            this.clickElementToTheRightUpperCorner(articleTitleXpath, "В списке статья не найдена.");
        this.waitForArticleToDisappearByTitle(articleTitle);
    }

    public int getAmountOfAddedArticles() {
        this.waitForElementPresent(ARTICLE_LIST_ITEM, "Ничего не найдено по заданному запросу.", 15);
        return getAmountOfElements(ARTICLE_LIST_ITEM);
    }

    public void clickByArticleWithTitle(String articleTitle) {
        String articleTitleXpath = getArticleXpathByTitle(articleTitle);
        this.waitForElementClickableAndClick(articleTitleXpath, String.format("Статья с заголовком '%s' не найдена или недоступна для действий.", articleTitle), 5);
    }

    /**
     * Метод для закрытия всплывающего окна при входе в список сохраненных статей с главной страницы на iOS
     */
    public void closeSyncSavedArticlesPopUp() {
        if (isElementPresent(SYNC_YOUR_SAVED_ARTICLES_POPUP))
            this.waitForElementClickableAndClick(CLOSE_SYNC_POPUP_BUTTON, "Кнопка 'Close' для закрытия окна 'Sync your saved articles popup?' не найдена или недоступна для действий.", 10);
    }
}