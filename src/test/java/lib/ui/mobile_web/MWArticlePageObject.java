package lib.ui.mobile_web;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {
    static {
        ARTICLE_TITLE = "css:#content h1";
        ARTICLE_FOOTER_ELEMENT = "css:footer";
        ADD_TO_WATCH_LIST_BUTTON = "css:#page-actions #ca-watch[role='button']";
        ARTICLE_SEARCH_INIT_ELEMENT = "id:page-actions-menu";
    }

    public MWArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}