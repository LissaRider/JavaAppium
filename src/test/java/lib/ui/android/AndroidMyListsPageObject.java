package lib.ui.android;

import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidMyListsPageObject extends MyListsPageObject {

    static {
        MY_LISTS_PAGE_TITLE = "xpath://*[@resource-id='org.wikipedia:id/single_fragment_toolbar']/*[@text='My lists']";
        ARTICLE_LIST = "id:org.wikipedia:id/item_container";
        FOLDER_BY_NAME_TPL = "xpath://*[@resource-id='org.wikipedia:id/item_title'][@text='{FOLDER_NAME}']";
        ARTICLE_LIST_ITEM = "id:org.wikipedia:id/page_list_item_container";
        ARTICLE_BY_TITLE_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='{ARTICLE_TITLE}']";
    }

    public AndroidMyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}