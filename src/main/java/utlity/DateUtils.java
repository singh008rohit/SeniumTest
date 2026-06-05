// DateUtils.java
package utlity;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtils {

    private DateUtils() {}

    // Legacy — kept so nothing else breaks
    public static String getCurrentDate() {
        return new Date().toString()
            .replace(":", "_")
            .replace(" ", "_");
    }

    // NEW — clean format for filenames: Jun_04_18-01-58_IST_2026
    // Matches your desired format: SearchFlightWithFlightNoTest_Jun_04_18_01_58_IST_2026
    public static String getReportTimestamp() {
        return new SimpleDateFormat("MMM_dd_HH_mm_ss_zzz_yyyy")
            .format(new Date());
    }
}