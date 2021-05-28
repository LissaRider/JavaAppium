package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

@Epic("Tests for articles")
public class ArticleTests extends CoreTestCase {

    @Test
    @Features(value = {@Feature(value = "Search"), @Feature(value = "Article")})
    @DisplayName("Compare article title with expected one")
    @Description("We open 'Java Object-oriented programming language' article and make sure the title is expected")
    @Step("Starting test testCompareArticleTitle")
    @Severity(value =SeverityLevel.BLOCKER)
    public void testCompareArticleTitle() {
        SearchPageObject searchPage = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePage = ArticlePageObjectFactory.get(driver);

        final String searchLine = "Java";
        searchPage.searchByValue(searchLine);

        searchPage.waitForNotEmptySearchResults();

        final String substring = "bject-oriented programming language";
        searchPage.clickByArticleWithSubstring(substring);

        String articleTitle = articlePage.getArticleTitle();

//        articlePage.takeScreenshot("article_page");

        Assert.assertEquals(
                "\n  Ошибка! Отображается некорректный заголовок статьи.\n",
                "Java (programming language)",
                articleTitle
        );
    }

    @Test
    @Features(value = {@Feature(value = "Search"), @Feature(value = "Article")})
    @DisplayName("Swipe article to the footer")
    @Description("We open an article and swipe it to the footer")
    @Step("Starting test testSwipeArticle")
    @Severity(value = SeverityLevel.MINOR)
    public void testSwipeArticle() {

        SearchPageObject searchPage = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePage = ArticlePageObjectFactory.get(driver);

        final String searchLine = "Java";
        searchPage.searchByValue(searchLine);

        searchPage.waitForNotEmptySearchResults();

        final String substring = "bject-oriented programming language";
        searchPage.clickByArticleWithSubstring(substring);

        articlePage.waitForTitleElement();

        articlePage.swipeToFooter();
    }

    @Test
    public void testArticleTitlePresence() {

        SearchPageObject searchPage = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePage = ArticlePageObjectFactory.get(driver);

        final String searchLine = "Lords Mobile";
        searchPage.searchByValue(searchLine);

        searchPage.waitForNotEmptySearchResults();

        searchPage.clickByArticleWithTitle(searchLine);

        articlePage.assertIsArticleTitlePresent();
    }
}