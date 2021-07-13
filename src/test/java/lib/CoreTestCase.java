package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.qameta.allure.Step;
import lib.ui.WelcomePageObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileOutputStream;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class CoreTestCase {

    protected RemoteWebDriver driver;
    private static AppiumDriverLocalService server;

    @Before
    @Step("Run driver and session")
    public void setUp() throws Exception {
        driver = Platform.getInstance().getDriver();
        this.createAllurePropertyFile();
        this.skipWelcomePageForIOSApp();
        this.openWikiWebPageForMobileWeb();
    }

    @BeforeClass
    @Step("Run appium server")
    public static void startAppiumServer() {
        if (!Platform.getInstance().isMW()) {
            System.out.println("Start appium server...");
            AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder();

            /*Use any port, in case the default 4723 is already taken (maybe by another Appium server)*/
            // serviceBuilder.usingAnyFreePort();
            /*Tell serviceBuilder where node is installed. Or set this path in an environment variable named NODE_PATH*/
            // serviceBuilder.usingDriverExecutable(new File("/Users/jonahss/.nvm/versions/node/v12.1.0/bin/node"));
            /*Tell serviceBuilder where Appium is installed. Or set this path in an environment variable named APPIUM_PATH*/
            // serviceBuilder.withAppiumJS(new File("/Users/jonahss/.nvm/versions/node/v12.1.0/bin/appium"));
            /*The XCUITest driver requires that a path to the Carthage binary is in the PATH variable. I have this set for my shell,
             but the Java process does not see it. It can be inserted here.*/
            if (Platform.getInstance().isIOS()) {
                HashMap<String, String> environment = new HashMap();
                environment.put("PATH", "/usr/local/bin:" + System.getenv("PATH"));
                serviceBuilder.withEnvironment(environment);
            }

            server = AppiumDriverLocalService.buildService(serviceBuilder);
            server.start();
        } else {
            System.out.printf("  Внимание! Метод startAppiumServer() не работает для платформы '%s'.%n", Platform.getInstance().getPlatformVar());
        }
    }

    @After
    @Step("Remove driver and session")
    public void tearDown() {
        driver.quit();
    }

    @AfterClass
    @Step("Stop appium server")
    public static void stopAppiumServer() {
        if (!Platform.getInstance().isMW()) {
            System.out.println("Stop appium server...");
            server.stop();
            closeEmulatorOrSimulator();
        } else {
            System.out.printf("  Внимание! Метод stopAppiumServer() не работает для платформы '%s'.%n", Platform.getInstance().getPlatformVar());
        }
    }

    /**
     * Kills all running emulators/simulators
     */
    public static void closeEmulatorOrSimulator() {
        String[] aCommand;
        System.out.println("Killing emulator/simulator...");
        if (Platform.getInstance().isAndroid()) {
            aCommand = new String[]{"adb", "emu", "kill"};
        } else {
            aCommand = new String[]{"killall", "Simulator"};
        }
        try {
            Process process = new ProcessBuilder(aCommand).start();
            process.waitFor(1, TimeUnit.SECONDS);
            System.out.println("Emulator/simulator closed successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Step("Rotate screen to portrait mode")
    protected void rotateScreenPortrait() {
        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.rotate(ScreenOrientation.PORTRAIT);
        } else {
            System.out.printf("  Внимание! Метод rotateScreenPortrait() не работает для платформы '%s'.%n", Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Rotate screen to landscape mode")
    protected void rotateScreenLandscape() {
        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.rotate(ScreenOrientation.LANDSCAPE);
        } else {
            System.out.printf("  Внимание! Метод rotateScreenPortrait() не работает для платформы '%s'.%n", Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Send mobile app to background (this method does nothing for Mobile Web)")
    protected void backgroundApp(int seconds) {
        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.runAppInBackground(Duration.ofSeconds(seconds));
        } else {
            System.out.printf("  Внимание! Метод rotateScreenPortrait() не работает для платформы '%s'.%n", Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Send Wikipedia URL for Mobile Web (this method does nothing for Mobile Web)")
    protected void openWikiWebPageForMobileWeb() {
        if (Platform.getInstance().isMW()) {
            driver.get("https://en.m.wikipedia.org");
        } else {
            System.out.printf("  Внимание! Метод openWikiWebPageForMobileWeb() не работает для платформы '%s'.%n", Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Skip welcome page screen for iOS")
    private void skipWelcomePageForIOSApp() {
        if (Platform.getInstance().isIOS()) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            WelcomePageObject WelcomePageObject = new WelcomePageObject(driver);
            WelcomePageObject.clickSkip();
        }
    }

    private void createAllurePropertyFile() {
        String path = System.getProperty("allure.results.directory");
        File directory = new File(path);
        if (!directory.exists()) {
            System.out.printf("Внимание! Директория '%s' не найдена.\n", path);
            directory.mkdir();
            System.out.printf("Создана директория '%s'.\n", path);
        }
        try {
            Properties props = new Properties();
            FileOutputStream fos = new FileOutputStream(path + "/environment.properties");
            props.setProperty("Environment", Platform.getInstance().getPlatformVar());
            props.store(fos, "See https://docs.qameta.io/allure/#_environment");
            fos.close();
        } catch (Exception e) {
            System.err.println("Внимание! Проблема ввода-вывода при записи файла свойств для allure отчета.");
            e.printStackTrace();
        }
    }
}