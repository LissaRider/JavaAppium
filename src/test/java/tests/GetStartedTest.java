package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.WelcomePageObject;
import org.junit.Test;

@Epic("Tests for welcome page")
public class GetStartedTest extends CoreTestCase {

    /**
     * Для успешного прохождения теста необходимо в классе {@link lib.CoreTestCase}
     * отключить метод {@link CoreTestCase#setUp()#skipWelcomePageForIOSApp()}
     *
     * @see CoreTestCase
     */
    @Test
    @Feature(value = "WelcomePage")
    @DisplayName("Check the welcome page")
    @Description("We check links displayed on the welcome page")
    @Step("Starting test 'testPassThroughWelcome'")
    @Severity(value = SeverityLevel.MINOR)
    public void testPassThroughWelcome() {

        if (Platform.getInstance().isAndroid() ||
                Platform.getInstance().isMW() ||
                Platform.getInstance().isIOS()) {
            return;
        }

        WelcomePageObject welcomePage = new WelcomePageObject(driver);

        welcomePage.waitForLearnMoreLink();
        welcomePage.clickNextButton();

        welcomePage.waitForNewWayToExploreText();
        welcomePage.clickNextButton();

        welcomePage.waitForAddOrEditPreferredLangText();
        welcomePage.clickNextButton();

        welcomePage.waitForLearnMoreAboutDataCollectedText();
        welcomePage.clickGetStartedButton();
    }
}