// ReportPathUtils.java — simplify, BDD Cucumber adapter still uses this
package utlity;

import commonConstant.ExtentReportConstant;

public final class ReportPathUtils {

    private ReportPathUtils() {}

    // Used only by the Cucumber extent adapter (extent.properties)
    // The TestNG report path is now built in ExtentReportManager directly
    public static String createExtentReportPath() {
        if (ConfigLoader.getInstance().getOverrideReports()
                .equalsIgnoreCase(ExtentReportConstant.NO)) {
            return ExtentReportConstant.EXTENT_REPORT_FOLDER_PATH
                + ExtentReportConstant.EXTENT_REPORT_NAME
                + "_" + DateUtils.getReportTimestamp() + ".html";
        }
        return ExtentReportConstant.EXTENT_REPORT_FOLDER_PATH
            + ExtentReportConstant.EXTENT_REPORT_NAME + ".html";
    }
}