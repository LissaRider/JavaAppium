package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {

    private final String
            login = "Lissa Rider",
            password = "uX9HJvEv";

    @Test
    public void testSaveFirstArticleToMyList() {

        SearchPageObject searchPage = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePage = ArticlePageObjectFactory.get(driver);
        NavigationUI navigation = NavigationUIFactory.get(driver);
        MyListsPageObject myListsPage = MyListsPageObjectFactory.get(driver);
        AuthorizationPageObject auth = new AuthorizationPageObject(driver);

        final String searchLine = "Java";
        searchPage.searchByValue(searchLine);

        searchPage.waitForNotEmptySearchResults();

        final String substring = "bject-oriented programming language";
        searchPage.clickByArticleWithSubstring(substring);

        articlePage.waitForTitleElement();
        String articleTitle = articlePage.getArticleTitle();

        final String folderName = "Learning programming";
        if (Platform.getInstance().isAndroid()) {
            articlePage.addArticleToNewList(folderName);
            articlePage.closeArticle();
        } else if (Platform.getInstance().isIOS()) {
            articlePage.addArticleToSavedList();
            articlePage.closeArticleAndReturnToMainPage();
        } else {
            articlePage.addArticleToSavedList();
            auth.clickAuthButton();
            auth.enterLoginData(login, password);
            auth.submitForm();

            articlePage.waitForTitleElement();

            assertEquals("\n    Ошибка! После авторизации открылась другая страница.", articleTitle, articlePage.getArticleTitle());

            navigation.openNavigation();
        }

        navigation.clickMyLists();

        if (Platform.getInstance().isAndroid()) myListsPage.openFolderByName(folderName);

        if (Platform.getInstance().isIOS()) myListsPage.closeSyncSavedArticlesPopUp();

        myListsPage.swipeByArticleToDelete(articleTitle);
    }

    /**
     * Тестовый метод, где проверка осуществляется по заголовку статьи в iOS
     */
    @Test
    public void testActionsWithArticlesInMyList() {

        SearchPageObject searchPage = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePage = ArticlePageObjectFactory.get(driver);
        NavigationUI navigation = NavigationUIFactory.get(driver);
        MyListsPageObject myListsPage = MyListsPageObjectFactory.get(driver);

        String searchLine;
        searchLine = "World of Tanks";
        searchPage.searchByValue(searchLine);

        searchPage.waitForNotEmptySearchResults();

        searchPage.clickByArticleWithSubstring(searchLine);

        articlePage.waitForTitleElement();
        String articleAboutWotTitle = articlePage.getArticleTitle();

        final String folderName = "Games";
        if (Platform.getInstance().isAndroid()) articlePage.addArticleToNewList(folderName);
        else articlePage.addArticleToSavedList();

        searchLine = "World of Warcraft";
        searchPage.searchByValue(searchLine);

        searchPage.waitForNotEmptySearchResults();

        searchPage.clickByArticleWithSubstring(searchLine);

        articlePage.waitForTitleElement();
        String articleAboutWowTitle = articlePage.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            articlePage.addArticleToExistingList(folderName);
            articlePage.closeArticle();
        } else {
            articlePage.addArticleToSavedList();
            articlePage.closeArticleAndReturnToMainPage();
        }

        navigation.clickMyLists();

        if (Platform.getInstance().isAndroid()) myListsPage.openFolderByName(folderName);
        else myListsPage.closeSyncSavedArticlesPopUp();

        int amountOfArticlesBefore = myListsPage.getAmountOfAddedArticles();

        assertEquals(
                String.format("\n  Ошибка! В папке '%s' отображается некорректное количество статей.\n", folderName),
                amountOfArticlesBefore,
                2);

        myListsPage.swipeByArticleToDelete(articleAboutWowTitle);

        int amountOfArticlesAfter = myListsPage.getAmountOfAddedArticles();

        assertEquals(
                String.format("\n  Ошибка! В папке '%s' отображается некорректное количество статей.\n", folderName),
                amountOfArticlesBefore - 1,
                amountOfArticlesAfter
        );

        myListsPage.clickByArticleWithTitle(articleAboutWotTitle);

        articlePage.waitForTitleElement();
        String actualTitle = articlePage.getArticleTitle();

        assertEquals(
                "\n  Ошибка! Отображается некорректное название статьи.\n",
                articleAboutWotTitle,
                actualTitle);
    }

    /**
     * Тестовый метод, где проверка осуществляется по тексту баннера на странице статьи в iOS
     */
    @Test
    public void testArticleDeletionFromReadingList() {

        SearchPageObject searchPage = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePage = ArticlePageObjectFactory.get(driver);
        NavigationUI navigation = NavigationUIFactory.get(driver);
        MyListsPageObject myListsPage = MyListsPageObjectFactory.get(driver);

        String searchLine;
        searchLine = "TeamCity";
        searchPage.searchByValue(searchLine);

        searchPage.waitForNotEmptySearchResults();

        final String substringForTeamCity = searchLine;
        searchPage.clickByArticleWithSubstring(substringForTeamCity);

        final String folderName = "CI servers";
        String articleAboutTeamCityTitle = "";
        if (Platform.getInstance().isAndroid()) {
            articlePage.waitForTitleElement();
            articleAboutTeamCityTitle = articlePage.getArticleTitle();
            articlePage.addArticleToNewList(folderName);
        } else {
            articlePage.waitForBannerElement(substringForTeamCity);
            articleAboutTeamCityTitle = substringForTeamCity;
            articlePage.addArticleToSavedList();
        }

        searchLine = "Jenkins";
        searchPage.searchByValue(searchLine);

        searchPage.waitForNotEmptySearchResults();

        final String substringForJenkins = "Jenkins (software)";
        searchPage.clickByArticleWithSubstring(substringForJenkins);

        String articleAboutJenkinsTitle = "";
        if (Platform.getInstance().isAndroid()) {
            articlePage.waitForTitleElement();
            articleAboutJenkinsTitle = articlePage.getArticleTitle();
            articlePage.addArticleToExistingList(folderName);
            articlePage.closeArticle();
        } else {
            articlePage.waitForBannerElement(substringForJenkins);
            articleAboutJenkinsTitle = substringForJenkins;
            articlePage.addArticleToSavedList();
            articlePage.closeArticleAndReturnToMainPage();
        }

        navigation.clickMyLists();

        if (Platform.getInstance().isAndroid()) myListsPage.openFolderByName(folderName);
        else myListsPage.closeSyncSavedArticlesPopUp();

        int amountOfArticlesBefore = myListsPage.getAmountOfAddedArticles();

        assertEquals(
                String.format("\n  Ошибка! В папке '%s' отображается некорректное количество статей.\n", folderName),
                amountOfArticlesBefore,
                2);

        myListsPage.swipeByArticleToDelete(articleAboutJenkinsTitle);

        int amountOfArticlesAfter = myListsPage.getAmountOfAddedArticles();

        assertEquals(
                String.format("\n  Ошибка! В папке '%s' отображается некорректное количество статей.\n", folderName),
                amountOfArticlesBefore - 1,
                amountOfArticlesAfter
        );

        myListsPage.clickByArticleWithTitle(articleAboutTeamCityTitle);

        if (Platform.getInstance().isAndroid()) {
            articlePage.waitForTitleElement();
            String actualTitle = articlePage.getArticleTitle();
            assertEquals(
                    "\n  Ошибка! Отображается некорректное название статьи.\n",
                    articleAboutTeamCityTitle,
                    actualTitle);
        } else {
            articlePage.waitForBannerElement(substringForTeamCity);
        }
    }
}