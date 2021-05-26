package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

public class ChangeAppConditionTests extends CoreTestCase {

    @Test
    public void testChangeScreenOrientationOnSearchResults() {

        if (Platform.getInstance().isMW()) {
            return;
        }

        SearchPageObject searchPage = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePage = ArticlePageObjectFactory.get(driver);

        final String searchLine = "Java";
        searchPage.searchByValue(searchLine);

        final String substring = "Object-oriented programming language";
        searchPage.clickByArticleWithSubstring(substring);

        articlePage.waitForTitleElement();
        String titleBeforeRotation = articlePage.getArticleTitle();

        this.rotateScreenLandscape();

        articlePage.waitForTitleElement();
        String titleAfterRotation = articlePage.getArticleTitle();

        Assert.assertEquals(
                "\n  Ошибка! Название статьи изменилось после изменения ориентации экрана.\n",
                titleBeforeRotation,
                titleAfterRotation
        );

        this.rotateScreenPortrait();

        articlePage.waitForTitleElement();
        String titleAfterSecondRotation = articlePage.getArticleTitle();

        Assert.assertEquals(
                "\n  Ошибка! Название статьи изменилось после изменения ориентации экрана.\n",
                titleBeforeRotation,
                titleAfterSecondRotation
        );
    }

    @Test
    public void testCheckSearchArticleInBackground() {

        if (Platform.getInstance().isMW()) {
            return;
        }

        SearchPageObject searchPage = SearchPageObjectFactory.get(driver);

        final String searchLine = "Java";
        searchPage.searchByValue(searchLine);

        final String substring = "Object-oriented programming language";
        searchPage.waitForSearchResult(substring);

        this.backgroundApp(2);

        searchPage.waitForSearchResult(substring);
    }
}