package lib.ui.mobile_web;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {

    static {
        ARTICLE_TITLE = "css:#content h1";
        ARTICLE_FOOTER_ELEMENT = "css:footer";
        ADD_TO_WATCHLIST_BUTTON = "css:#page-actions #ca-watch[class*='mw-ui-icon-wikimedia-star'][role='button']";
        ARTICLE_SEARCH_INIT_ELEMENT = "id:searchIcon";
        REMOVE_FROM_WATCHLIST_BUTTON = "css:#page-actions #ca-watch.watched[class*='mw-ui-icon-wikimedia-unStar'][role='button']";
        ARTICLE_BANNER_BY_SUBSTRING_TPL ="xpath://*[contains(@class,'pre-content') and contains(normalize-space(.),'{SUBSTRING}')]";
    }

    public MWArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}