package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

public class ArticleTests extends CoreTestCase {

    @Test
    public void testCompareArticleTitle() {
        SearchPageObject searchPage = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePage = ArticlePageObjectFactory.get(driver);

        final String searchLine = "Java";
        searchPage.searchByValue(searchLine);

        searchPage.waitForNotEmptySearchResults();

        final String substring = "bject-oriented programming language";
        searchPage.clickByArticleWithSubstring(substring);

        String articleTitle = articlePage.getArticleTitle();

        Assert.assertEquals(
                "\n  Ошибка! Отображается некорректный заголовок статьи.\n",
                "Java (programming language)",
                articleTitle
        );
    }

    @Test
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