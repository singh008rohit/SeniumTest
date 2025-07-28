package reportManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
    
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    private static ExtentReports extent;
    
    // 🔥 Method to Setup Extent Report
    public static ExtentReports getSetup(String testname) {
        if (extent == null) { // Prevent multiple initializations
            ExtentSparkReporter reporter = new ExtentSparkReporter(setReportname(testname));
            configureReporter(reporter);  // Move customization to a separate method
            extent = new ExtentReports();
            extent.attachReporter(reporter);
            extent.setSystemInfo("Tester", "Rohit Singh");
            extent.setSystemInfo("Environment", "QA");
        }
        return extent;
    }

    // 🔹 Configure Reporter Customizations
    private static void configureReporter(ExtentSparkReporter reporter) {
        reporter.config().setReportName("Automation Test Report");
        reporter.config().setDocumentTitle("Test Execution Report");
        reporter.config().setTheme(Theme.DARK);
        reporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");
        reporter.config().setEncoding("UTF-8");
        reporter.config().setProtocol(Protocol.HTTPS);

        // 🔹 Append Multiple JavaScript Customizations
        StringBuilder customJs = new StringBuilder();

        customJs.append("document.addEventListener('DOMContentLoaded', function() { ")
                .append("document.body.style.backgroundColor = '#20232a'; ")
                .append("document.querySelector('.report-name').style.color = 'yellow'; ")
                .append("document.title = 'Customized Selenium Report'; ")
                .append("alert('Welcome to Custom Extent Report!'); ")
                .append("});");

        customJs.append("document.addEventListener(\"DOMContentLoaded\", function() { ")
                .append("document.querySelectorAll(\".fail\").forEach(e => { ")
                .append("e.style.backgroundColor = \"#ff0000\"; ")
                .append("e.style.color = \"#ffffff\"; ")
                .append("}); ")
                .append("});");

        customJs.append("document.addEventListener(\"DOMContentLoaded\", function() { ")
                .append("let btn = document.createElement(\"button\"); ")
                .append("btn.innerText = \"🔄 Refresh Report\"; ")
                .append("btn.style = \"position: fixed; bottom: 50px; right: 70px; padding: 10px; background: cyan; border: none; cursor: pointer; font-weight: bold;\"; ")
                .append("btn.onclick = () => location.reload(); ")
                .append("document.body.appendChild(btn); ")
                .append("});");

        customJs.append("document.addEventListener(\"DOMContentLoaded\", function() { ")
                .append("document.querySelectorAll(\".node-name\").forEach(node => node.click()); ")
                .append("});");


        customJs.append("document.addEventListener(\"DOMContentLoaded\", function() { ")
                .append("let footer = document.createElement(\"div\"); ")
                .append("footer.innerHTML = \"<p style='text-align:center; color:white;'>Execution completed at: \" + new Date().toLocaleString() + \"</p>\"; ")
                .append("footer.style = \"position: fixed; bottom: 0; width: 100%; background: black; padding: 5px;\"; ")
                .append("document.body.appendChild(footer); ")
                .append("});");

        reporter.config().setJs(customJs.toString());
    }

    //  Create a Test Case in Extent Report
    public static synchronized void createTest(String testName) {
        ExtentTest extentTest = extent.createTest(testName);
        test.set(extentTest);  // Store in ThreadLocal
    }

    public static ExtentTest getTest() {
        return test.get(); // Retrieve ThreadLocal Test
    }

    //  Flush Extent Report After Execution
    public static void flush() {
        if (extent != null) {
            extent.flush();
        }
    }

    //  Generate Dynamic Report Name
    public static String setReportname(String testName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return "./reports/" + testName + "_Report_" + timestamp + ".html";
    }
}
