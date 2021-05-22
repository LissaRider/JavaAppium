package lib.ui.ios;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSSearchPageObject extends SearchPageObject {

    static {
        MAIN_PAGE_SEARCH_INIT_ELEMENT = "xpath://XCUIElementTypeSearchField[@name='Search Wikipedia']";
        SEARCH_INPUT_FIELD = "xpath://XCUIElementTypeSearchField[@label='Search Wikipedia']";
        SEARCH_CANCEL_BUTTON = "id:Cancel";
        SEARCH_RESULT_BY_LIST_ITEM_SUBSTRING_TPL = "xpath://XCUIElementTypeCell//XCUIElementTypeStaticText[contains(@value,'{SUBSTRING}')]";
        SEARCH_EMPTY_RESULT_ELEMENT = "xpath://XCUIElementTypeStaticText[@name='No results found']";
        SEARCH_RESULT_LIST = "xpath://XCUIElementTypeCollectionView[@visible='true']";
        SEARCH_RESULT_LIST_ITEM = "xpath://XCUIElementTypeCollectionView[@visible='true']//XCUIElementTypeCell";
        RECENT_SEARCHES_YET_ELEMENT = "id:No recent searches yet";
        SEARCH_RESULT_LIST_ITEM_TITLE =
                "xpath://XCUIElementTypeCollectionView[@visible='true']" +
                        "//XCUIElementTypeCell//XCUIElementTypeStaticText[@value][1]";
        SEARCH_RESULT_BY_LIST_ITEM_TITLE_TPL =
                "xpath://XCUIElementTypeCollectionView[@visible='true']" +
                        "//XCUIElementTypeCell//XCUIElementTypeStaticText[@value='{TITLE}'][1]";
        SEARCH_RESULT_BY_LIST_ITEM_TITLE_AND_DESCRIPTION_TPL =
                "xpath://XCUIElementTypeCollectionView[@visible='true']//XCUIElementTypeCell" +
                        "[.//XCUIElementTypeStaticText[@value='{ARTICLE_TITLE}']]" +
                        "[.//XCUIElementTypeStaticText[contains(@value,'{ARTICLE_DESCRIPTION}')]]";
    }

    public iOSSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}