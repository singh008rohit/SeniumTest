package driver;

import enums.DriverType;
import loggerUtil.LoggerUtils;

public class DriverManagerFactory {

    private DriverManagerFactory() {}

    public static DriverManager_OC getManager(DriverType driverType) {
        LoggerUtils.info("Creating driver manager for: " + driverType);

        switch (driverType) {
            case CHROME:  return new DriverManagerChrome();
            case FIREFOX: return new DriverManagerFirefox();
            case EDGE:    return new DriverManagerEdge();
            default:      throw new IllegalArgumentException(
                              "No driver manager found for: " + driverType
                              + " — supported types: CHROME, FIREFOX, EDGE"
                          );
        }
    }
}