package lib.ui.mobile_web;

import lib.ui.NavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWNavigationUI extends NavigationUI {

    static {
        OPEN_NAVIGATION = "id:mw-mf-main-menu-button";
        READING_LISTS_LINK = "css:[data-event-name='menu.unStar']";
    }

    public MWNavigationUI(RemoteWebDriver driver) {
        super(driver);
    }
}