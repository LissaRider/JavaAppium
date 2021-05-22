package lib.ui.mobile_web;

import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWMyListsPageObject extends MyListsPageObject {

    static {
        MY_LISTS_PAGE_TITLE = "xpath://*[@class='page-heading']//h1[normalize-space(text())='Watchlist']";
        ARTICLE_BY_TITLE_TPL = "xpath://*[starts-with(@class,'page-summary')]//h3[contains(text(),'{ARTICLE_TITLE}')]";
        REMOVE_FROM_SAVED_LIST_BUTTON_TPL = "xpath://*[starts-with(@class,'page-summary')][.//h3[contains(text(),'{ARTICLE_TITLE}')]]/*[contains(@class,'watched')]";
    }

    public MWMyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}