package lib.ui.ios;

import lib.ui.NavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSNavigationUI extends NavigationUI {

    static {
        READING_LISTS_LINK = "id:Saved";
        MAIN_NAV_TAB_ELEMENT = "id:Tab Bar";
    }

    public iOSNavigationUI(RemoteWebDriver driver) {
        super(driver);
    }
}