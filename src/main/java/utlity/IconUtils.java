/**
 * @author Rajat Verma
 * https://www.linkedin.com/in/rajat-v-3b0685128/
 * https://github.com/rajatt95
 * https://rajatt95.github.io/
 *
 * Course: Selenium Java Test Framework & Best Practices - Masterclass (https://www.udemy.com/course/selenium-java-test-framework/)
 * Tutor: Omprakash Chavan (https://www.udemy.com/user/omprakash-chavan/)
 */

/***************************************************/

package utlity;



import commonConstant.ExtentReportConstant;

public final class IconUtils {

	/**
	 * Private constructor to avoid external instantiation
	 */
	private IconUtils() {
	}

	public static String getBrowserIcon() {
		String browserInLowerCase = BrowserInfoUtils.getBrowserInfo().toLowerCase();
		if (browserInLowerCase.contains(ExtentReportConstant.ICON_BROWSER_EDGE)) {
			return ExtentReportConstant.ICON_BROWSER_PREFIX +ExtentReportConstant.ICON_BROWSER_EDGE +ExtentReportConstant. ICON_BROWSER_SUFFIX;
			//return "<i class='fa-brands fa-edge'></i>";
		} else {
			return ExtentReportConstant.ICON_BROWSER_PREFIX + browserInLowerCase + ExtentReportConstant.ICON_BROWSER_SUFFIX;
		}
	}

	public static String getOSIcon() {

		String operSys = OSInfoUtils.getOSInfo().toLowerCase();
		if (operSys.contains("win")) {
			return ExtentReportConstant.ICON_OS_WINDOWS;
		} else if (operSys.contains("nix") || operSys.contains("nux") || operSys.contains("aix")) {
			return ExtentReportConstant.ICON_OS_LINUX;
		} else if (operSys.contains("mac")) {
			return ExtentReportConstant.ICON_OS_MAC;
		}
		return operSys;
	}
}
