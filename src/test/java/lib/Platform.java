package lib;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Platform {

    private static final String PLATFORM_ANDROID = "android";
    private static final String PLATFORM_IOS = "ios";
    private static final String PLATFORM_MOBILE_WEB = "mobile_web";
    private static final String APPIUM_URL = "http://127.0.0.1:4723/wd/hub";

    public static Platform instance;

    private Platform() {
    }

    public static Platform getInstance() {
        if (instance == null) {
            instance = new Platform();
        }
        return instance;
    }

    public RemoteWebDriver getDriver() throws Exception {
        URL url = new URL(APPIUM_URL);
        if (this.isAndroid()) {
            return new AndroidDriver(url, this.getAndroidDesiredCapabilities());
        } else if (this.isIOS()) {
            return new IOSDriver(url, this.getIOSDesiredCapabilities());
        } else if (this.isMW()) {
            return new ChromeDriver(this.getMWChromeOptions());
        } else {
            throw new Exception(String.format("Невозможно определить тип драйвера. Платформа: %s", this.getPlatformVar()));
        }
    }

    public boolean isAndroid() {
        return isPlatform(PLATFORM_ANDROID);
    }

    public boolean isIOS() {
        return isPlatform(PLATFORM_IOS);
    }

    public boolean isMW() {
        return isPlatform(PLATFORM_MOBILE_WEB);
    }

    private DesiredCapabilities getIOSDesiredCapabilities() throws IOException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", "iPhone SE");
        capabilities.setCapability("platformVersion", "14.5");
        capabilities.setCapability("udid", "0D67A646-F872-484A-A48D-F759CB59BD28");
        capabilities.setCapability("orientation", "PORTRAIT");
        capabilities.setCapability("app", new File("src/test/resources/apks/Wikipedia.app").getCanonicalPath());
        return capabilities;
    }

    private DesiredCapabilities getAndroidDesiredCapabilities() throws IOException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0");
//        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("avd","and80");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("orientation", "PORTRAIT");
        capabilities.setCapability("app", new File("src/test/resources/apks/org.wikipedia.apk").getCanonicalPath());
        return capabilities;
    }

    private ChromeOptions getMWChromeOptions() throws IOException {

        String chromeBinary = System.getProperty("webdriver.chrome.driver");
        if (chromeBinary == null || chromeBinary.equals("")) {
            String os = System.getProperty("os.name").toLowerCase().substring(0, 3);
            final String driverPath = new File("src/test/resources/drivers/chromedriver").getCanonicalPath();
            chromeBinary = driverPath + (os.equals("win") ? ".exe" : "");
            System.setProperty("webdriver.chrome.driver", chromeBinary);
        }

        Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", 360);
        deviceMetrics.put("height", 640);
        deviceMetrics.put("pixelRatio", 3.0);

        Map<String, Object> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        mobileEmulation.put("userAgent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Mobile Safari/537.36");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
        chromeOptions.addArguments("--window-size=340,780");
        return chromeOptions;
    }

    private boolean isPlatform(String myPlatform) {
        String platform = this.getPlatformVar();
        return myPlatform.equals(platform);
    }

    public String getPlatformVar() {
        return System.getenv("PLATFORM");
    }
}