package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Epic("Tests for searching")
public class SearchTests extends CoreTestCase {

    @Test
    @Feature(value = "Search")
    @DisplayName("Simple search for the article")
    @Description("Search for the article and check its presence in search results by substring")
    @Step("Starting test 'testSearch'")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testSearch() {

        SearchPageObject searchPage = SearchPageObjectFactory.get(driver);

        final String searchLine = "Java";
        searchPage.searchByValue(searchLine);

        final String substring = "bject-oriented programming language";
        searchPage.waitForSearchResult(substring);
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Cancel search for the article")
    @Description("Search for the article and then cancel the search")
    @Step("Starting test 'testCancelSearch'")
    @Severity(value = SeverityLevel.MINOR)
    public void testCancelSearch() {

        SearchPageObject searchPage = SearchPageObjectFactory.get(driver);

        searchPage.initSearchInput();

        searchPage.waitForCancelButtonToAppear();

        searchPage.clickCancelButton();

        searchPage.waitForCancelButtonToDisappear();
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Search for not empty results")
    @Description("Search for the article and then check that search results are not empty")
    @Step("Starting test 'testAmountOfNotEmptySearch'")
    @Severity(value = SeverityLevel.NORMAL)
    public void testAmountOfNotEmptySearch() {

        SearchPageObject searchPage = SearchPageObjectFactory.get(driver);

        final String searchLine = "Linkin Park Discography";
        searchPage.searchByValue(searchLine);

        int amountOfSearchResults = searchPage.getAmountOfFoundArticles();

        Assert.assertTrue(
                String.format("\n  Ошибка! Найдено меньше результатов, чем ожидалось: %d.\n", amountOfSearchResults),
                amountOfSearchResults > 0);
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Search for empty results")
    @Description("Search for the article and then check that search results are empty")
    @Step("Starting test 'testAmountOfEmptySearch'")
    @Severity(value = SeverityLevel.NORMAL)
    public void testAmountOfEmptySearch() {

        SearchPageObject searchPage = SearchPageObjectFactory.get(driver);

        final String searchLine = "TIJIIOLLIKA";
        searchPage.searchByValue(searchLine);

        searchPage.waitForEmptyResultsLabel();

        searchPage.assertThereIsNoResultOfSearch();
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Check search placeholder")
    @Description("We init search and check the placeholder in the search input field")
    @Step("Starting test 'testSearchPlaceholder'")
    @Severity(value = SeverityLevel.TRIVIAL)
    public void testSearchPlaceholder() {

        SearchPageObject searchPage = SearchPageObjectFactory.get(driver);

        searchPage.initSearchInput();

        searchPage.assertSearchPlaceholderHasText(Platform.getInstance().isAndroid()
                ? "Search…"
                : "Search Wikipedia");
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Clear the search input field")
    @Description("Search for the article and then clear the search input field")
    @Step("Starting test 'testSearchAndClear'")
    @Severity(value = SeverityLevel.NORMAL)
    public void testSearchAndClear() {

        SearchPageObject searchPage = SearchPageObjectFactory.get(driver);

        final String searchLine = "Java";
        searchPage.searchByValue(searchLine);

        searchPage.waitForNumberOfResultsMoreThan(1);

        searchPage.clearSearchInput();
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Check search results")
    @Description("Search for the article and then check that each displayed search result contains the search value")
    @Step("Starting test 'testSearchResults'")
    @Severity(value = SeverityLevel.NORMAL)
    public void testSearchResults() {
        SearchPageObject searchPage = SearchPageObjectFactory.get(driver);

        final String searchValue = "JAVA";
        searchPage.searchByValue(searchValue);

        List<WebElement> articleTitles = searchPage.getSearchResultsList();

        for (int i = 0; i < articleTitles.size(); i++) {
            final WebElement titleElement = articleTitles.get(i);
            String articleTitle = "";
            if (Platform.getInstance().isAndroid()) {
                articleTitle = titleElement.getAttribute("text").toLowerCase();
            } else if (Platform.getInstance().isIOS()) {
                articleTitle = titleElement.getAttribute("name").toLowerCase();
            } else if (Platform.getInstance().isMW()) {
                articleTitle = titleElement.getAttribute("textContent").toLowerCase();
            }

            Assert.assertTrue(
                    String.format("\n  Ошибка! В заголовке найденной статьи с индексом [%d] отсутствует заданное для поиска значение '%s'.\n", i, searchValue),
                    articleTitle.contains(searchValue.toLowerCase()));
        }
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Search for the article by title and description")
    @Description("Search for the article and then check that first three search results contain the article with the specified title and description")
    @Step("Starting test 'testSearchArticleWithTitleAndDescription'")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testSearchArticleWithTitleAndDescription() {

        SearchPageObject searchPage = SearchPageObjectFactory.get(driver);

        Map<String, String> searchResults = new HashMap<>();

        searchResults.put("Java", "Island of Indonesia|Indonesian island");
        searchResults.put("JavaScript", "Programming language|High-level programming language");
        searchResults.put("Java (programming language)", "Object-oriented programming language");

        searchPage.searchByValue("Java");

        int amountOfSearchResults = searchPage.getAmountOfFoundArticles();

        Assert.assertTrue(
                String.format("\n  Ошибка! Найдено меньше статей, чем ожидалось: %d.\n", amountOfSearchResults),
                amountOfSearchResults >= 3);

        searchResults.forEach(searchPage::waitForElementByTitleAndDescription);
    }
}