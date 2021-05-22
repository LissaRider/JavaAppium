package lib.ui.ios;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSArticlePageObject extends ArticlePageObject {

    static {
        ARTICLE_TITLE = "xpath://XCUIElementTypeOther[@name='banner']/parent::node()";
        ARTICLE_BANNER_BY_SUBSTRING_TPL = "xpath://XCUIElementTypeOther[@name='banner']//XCUIElementTypeStaticText[@name='{SUBSTRING}']";
        ARTICLE_SEARCH_INIT_ELEMENT = "xpath://XCUIElementTypeNavigationBar//XCUIElementTypeButton[@name='Search Wikipedia']";
        ARTICLE_FOOTER_ELEMENT = "id:View article in browser";
        SAVE_FOR_LATER_BUTTON = "id:Save for later";
        TAP_TO_GO_BACK_BUTTON = "xpath://XCUIElementTypeButton[@name='W']";
    }

    public iOSArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}