package lib.ui.mobile_web;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject {

    static {
        MAIN_PAGE_SEARCH_INIT_ELEMENT = "css:button#searchIcon";
        SEARCH_INPUT_FIELD = "css:form>input[type='search']";
        SEARCH_CANCEL_BUTTON = "css:.header-action>button.cancel";
        SEARCH_INPUT_CLEAR_BUTTON = "css:button.clear";
        SEARCH_RESULT_BY_LIST_ITEM_SUBSTRING_TPL = "xpath://*[contains(@class,'wikidata-description')][contains(normalize-space(.),'{SUBSTRING}')]";
        SEARCH_RESULT_LIST_ITEM_TITLE = "css:a.title h3";
        SEARCH_RESULT_BY_LIST_ITEM_TITLE_TPL = "xpath://a[contains(@class,'title')]/h3[normalize-space(.)='{TITLE}']";
        SEARCH_RESULT_LIST_ITEM = "css:ul.page-list>li.page-summary";
        SEARCH_RESULT_LIST = "css:ul.page-list";
        SEARCH_EMPTY_RESULT_ELEMENT = "css:p.without-results";
    }

    public MWSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}