package driver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import loggerUtils.LogUtils;

import org.openqa.selenium.remote.AbstractDriverOptions;

import utlity.ConfigLoader;

public interface DriverManager_OC {

    WebDriver createDriver();

    // ─── shared configuration applied to every browser ─────────
    // default method — all three managers inherit this automatically
    // no duplication, guaranteed consistency across Chrome/Firefox/Edge
    default WebDriver configureDriver(WebDriver driver) {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().deleteAllCookies();
        LogUtils.info("Driver configured — implicit wait: 30s, "
            + "page load timeout: 30s, cookies cleared");
        return driver;
    }

    // ─── shared grid support — all three managers inherit this ──
    default boolean isGridEnabled() {
        return Boolean.parseBoolean(ConfigLoader.getInstance().getuseGrid());
    }

    default String getGridUrl() {
        return ConfigLoader.getInstance().getGridUrl();
    }

    // builds a RemoteWebDriver for any browser options type
    default WebDriver createRemoteDriver(AbstractDriverOptions<?> options) {
        try {
            WebDriver driver = new RemoteWebDriver(new URL(getGridUrl()), options);
            LogUtils.info("RemoteWebDriver created at: " + getGridUrl());
            return driver;
        } catch (MalformedURLException e) {
            throw new RuntimeException(
                "Invalid grid URL: " + getGridUrl()
                + " — check gridUrl in application.properties", e
            );
        }
    }
}
