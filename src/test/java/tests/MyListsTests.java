package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

@Epic("Tests for my lists")
public class MyListsTests extends CoreTestCase {

    private final String
            login = "Lissa Rider",
            password = "uX9HJvEv";

    @Test
    @Features(value = {@Feature(value = "Search"), @Feature(value = "Article"),  @Feature(value = "MyLists")})
    @DisplayName("Save the first found article to my new list")
    @Description("Add the first found article to the new reading list and then remove it from the list")
    @Step("Starting test 'testSaveFirstArticleToMyList'")
    @Severity(value = SeverityLevel.BLOCKER)
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

            Assert.assertEquals("\n    Ошибка! После авторизации открылась другая страница.", articleTitle, articlePage.getArticleTitle());

            navigation.openNavigation();
        }

        navigation.clickMyLists();

        if (Platform.getInstance().isAndroid()) myListsPage.openFolderByName(folderName);

        if (Platform.getInstance().isIOS()) myListsPage.closeSyncSavedArticlesPopUp();

        myListsPage.swipeByArticleToDelete(articleTitle);
    }

    /**
     * Тестовый метод, где проверка осуществляется по заголовку статьи
     */
    @Test
    @Features(value = {@Feature(value = "Search"), @Feature(value = "Article"),  @Feature(value = "MyLists")})
    @DisplayName("Check actions with articles in the reading list")
    @Description("Add two articles to the reading list and then check remove one article from two saved ones (by title text)")
    @Step("Starting test 'testActionsWithArticlesInMyList'")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testActionsWithArticlesInMyList() {

        SearchPageObject searchPage = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePage = ArticlePageObjectFactory.get(driver);
        NavigationUI navigation = NavigationUIFactory.get(driver);
        MyListsPageObject myListsPage = MyListsPageObjectFactory.get(driver);
        AuthorizationPageObject auth = new AuthorizationPageObject(driver);

        String searchLine;
        searchLine = "World of Tanks";
        searchPage.searchByValue(searchLine);

        searchPage.waitForNotEmptySearchResults();

        searchPage.clickByArticleWithSubstring(searchLine);

        articlePage.waitForTitleElement();
        String articleAboutWotTitle = articlePage.getArticleTitle();

        final String folderName = "Games";
        if (Platform.getInstance().isAndroid()) {
            articlePage.addArticleToNewList(folderName);
        } else if (Platform.getInstance().isIOS()) {
            articlePage.addArticleToSavedList();
        } else {
            articlePage.addArticleToSavedList();
            auth.clickAuthButton();
            auth.enterLoginData(login, password);
            auth.submitForm();

            articlePage.waitForTitleElement();

            Assert.assertEquals("\n    Ошибка! После авторизации открылась другая страница.", articleAboutWotTitle, articlePage.getArticleTitle());
        }

        searchLine = "World of Warcraft";
        searchPage.searchByValue(searchLine);

        searchPage.waitForNotEmptySearchResults();

        searchPage.clickByArticleWithSubstring(searchLine);

        articlePage.waitForTitleElement();
        String articleAboutWowTitle = articlePage.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            articlePage.addArticleToExistingList(folderName);
            articlePage.closeArticle();
        } else if (Platform.getInstance().isIOS()) {
            articlePage.addArticleToSavedList();
            articlePage.closeArticleAndReturnToMainPage();
        } else {
            articlePage.addArticleToSavedList();
            navigation.openNavigation();
        }

        navigation.clickMyLists();

        if (Platform.getInstance().isAndroid()) myListsPage.openFolderByName(folderName);

        if (Platform.getInstance().isIOS()) myListsPage.closeSyncSavedArticlesPopUp();

        int amountOfArticlesBefore = myListsPage.getAmountOfAddedArticles();

        final String message = Platform.getInstance().isAndroid()
                ? String.format("\n  Ошибка! В папке '%s' отображается некорректное количество статей.\n", folderName)
                : "\n  Ошибка! В списке сохраненных статей отображается некорректное количество статей.\n";

        if (Platform.getInstance().isMW()) {
            List articleTitleListBefore = myListsPage.getArticleTitleList();
            Assert.assertTrue(
                    String.format("\n  Ошибка! В списке сохраненных статей отсутствует статья с заголовком '%s'.", articleAboutWotTitle),
                    articleTitleListBefore.contains(articleAboutWotTitle));
            Assert.assertTrue(
                    String.format("\n  Ошибка! В списке сохраненных статей отсутствует статья с заголовком '%s'.", articleAboutWowTitle),
                    articleTitleListBefore.contains(articleAboutWowTitle));
        } else {
            Assert.assertEquals(message, 2, amountOfArticlesBefore);
        }

        myListsPage.swipeByArticleToDelete(articleAboutWowTitle);

        int amountOfArticlesAfter = myListsPage.getAmountOfAddedArticles();

        Assert.assertEquals(message, amountOfArticlesBefore - 1, amountOfArticlesAfter);

        if (Platform.getInstance().isMW()) {
            List articleTitleListAfter = myListsPage.getArticleTitleList();
            Assert.assertTrue(
                    String.format("\n  Ошибка! В списке сохраненных статей отсутствует статья с заголовком '%s'.", articleAboutWotTitle),
                    articleTitleListAfter.contains(articleAboutWotTitle));
            Assert.assertFalse(
                    String.format("\n  Ошибка! В списке сохраненных статей присутствует статья с заголовком '%s'.", articleAboutWowTitle),
                    articleTitleListAfter.contains(articleAboutWowTitle));
        }

        myListsPage.clickByArticleWithTitle(articleAboutWotTitle);

        articlePage.waitForTitleElement();
        String actualTitle = articlePage.getArticleTitle();

        Assert.assertEquals(
                "\n  Ошибка! Отображается некорректное название статьи.\n",
                articleAboutWotTitle,
                actualTitle);
    }

    /**
     * Тестовый метод, где проверка осуществляется по тексту баннера на странице статьи
     */
    @Test
    @Features(value = {@Feature(value = "Search"), @Feature(value = "Article"),  @Feature(value = "MyLists")})
    @DisplayName("Delete one of two articles from the reading list")
    @Description("Add two articles to the reading list and then check remove one article from two saved ones (by banner text)")
    @Step("Starting test 'testArticleDeletionFromReadingList'")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testArticleDeletionFromReadingList() {

        SearchPageObject searchPage = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePage = ArticlePageObjectFactory.get(driver);
        NavigationUI navigation = NavigationUIFactory.get(driver);
        MyListsPageObject myListsPage = MyListsPageObjectFactory.get(driver);
        AuthorizationPageObject auth = new AuthorizationPageObject(driver);

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
        } else if (Platform.getInstance().isIOS()) {
            articlePage.waitForBannerElement(substringForTeamCity);
            articleAboutTeamCityTitle = substringForTeamCity;
            articlePage.addArticleToSavedList();
        } else {
            articlePage.addArticleToSavedList();
            auth.clickAuthButton();
            auth.enterLoginData(login, password);
            auth.submitForm();

            articlePage.waitForBannerElement(substringForTeamCity);
            articleAboutTeamCityTitle = substringForTeamCity;
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
        } else if (Platform.getInstance().isIOS()) {
            articlePage.waitForBannerElement(substringForJenkins);
            articleAboutJenkinsTitle = substringForJenkins;
            articlePage.addArticleToSavedList();
            articlePage.closeArticleAndReturnToMainPage();
        } else {
            articlePage.waitForBannerElement(substringForJenkins);
            articleAboutJenkinsTitle = substringForJenkins;
            articlePage.addArticleToSavedList();
            navigation.openNavigation();
        }

        navigation.clickMyLists();

        if (Platform.getInstance().isAndroid()) myListsPage.openFolderByName(folderName);

        if (Platform.getInstance().isIOS()) myListsPage.closeSyncSavedArticlesPopUp();

        int amountOfArticlesBefore = myListsPage.getAmountOfAddedArticles();

        final String message = Platform.getInstance().isAndroid()
                ? String.format("\n  Ошибка! В папке '%s' отображается некорректное количество статей.\n", folderName)
                : "\n  Ошибка! В списке сохраненных статей отображается некорректное количество статей.\n";

        if (Platform.getInstance().isMW()) {
            List articleTitleListBefore = myListsPage.getArticleTitleList();
            Assert.assertTrue(
                    String.format("\n  Ошибка! В списке сохраненных статей отсутствует статья с заголовком '%s'.", articleAboutJenkinsTitle),
                    articleTitleListBefore.contains(articleAboutJenkinsTitle));
            Assert.assertTrue(
                    String.format("\n  Ошибка! В списке сохраненных статей отсутствует статья с заголовком '%s'.", articleAboutTeamCityTitle),
                    articleTitleListBefore.contains(articleAboutTeamCityTitle));
        } else {
            Assert.assertEquals(message, 2, amountOfArticlesBefore);
        }

        myListsPage.swipeByArticleToDelete(articleAboutJenkinsTitle);

        int amountOfArticlesAfter = myListsPage.getAmountOfAddedArticles();

        Assert.assertEquals(message, amountOfArticlesBefore - 1, amountOfArticlesAfter);

        if (Platform.getInstance().isMW()) {
            List articleTitleListAfter = myListsPage.getArticleTitleList();
            Assert.assertFalse(
                    String.format("\n  Ошибка! В списке сохраненных статей присутствует статья с заголовком '%s'.", articleAboutJenkinsTitle),
                    articleTitleListAfter.contains(articleAboutJenkinsTitle));
            Assert.assertTrue(
                    String.format("\n  Ошибка! В списке сохраненных статей отсутствует статья с заголовком '%s'.", articleAboutTeamCityTitle),
                    articleTitleListAfter.contains(articleAboutTeamCityTitle));
        }

        myListsPage.clickByArticleWithTitle(articleAboutTeamCityTitle);

        if (Platform.getInstance().isAndroid()) {
            articlePage.waitForTitleElement();
            String actualTitle = articlePage.getArticleTitle();
            Assert.assertEquals(
                    "\n  Ошибка! Отображается некорректное название статьи.\n",
                    articleAboutTeamCityTitle,
                    actualTitle);
        } else {
            articlePage.waitForBannerElement(substringForTeamCity);
        }
    }
}