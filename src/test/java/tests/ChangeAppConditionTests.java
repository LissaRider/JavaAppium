package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

@Epic("Tests for device interactions and activity")
public class ChangeAppConditionTests extends CoreTestCase {

    @Test
    @Features(value = {@Feature(value = "Search"), @Feature(value = "AppCondition")})
    @DisplayName("Check search results by setting different device orientations")
    @Description("Search for the article, rotate the device and check search results, then return device back to the portrait orientation and check them again")
    @Step("Starting test 'testChangeScreenOrientationOnSearchResults'")
    @Severity(value = SeverityLevel.BLOCKER)
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
    @Features(value = {@Feature(value = "Search"), @Feature(value = "AppCondition")})
    @DisplayName("Check search results after sending the app to the background")
    @Description("Search for the article, then send the currently active app to the background, return after 2 sec and check search results")
    @Step("Starting test 'testChangeScreenOrientationOnSearchResults'")
    @Severity(value = SeverityLevel.BLOCKER)
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