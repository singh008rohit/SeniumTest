  package test.data;

import commonConstant.CommonConstant;
import java.util.HashMap;

public class MapTestData {
   public HashMap<String, String> setUserData() {
      HashMap<String, String> setuserDetails = new HashMap();
      setuserDetails.put(CommonConstant.USER_NAME, "rohit");
      setuserDetails.put(CommonConstant.USER_EMAIL, "test@gmail.com");
      setuserDetails.put(CommonConstant.USER_PASSWORD, "Nov@1122!0091");
      setuserDetails.put(CommonConstant.USER_FIRST_NAME, "rohit");
      setuserDetails.put(CommonConstant.USER_LAST_NAME, "singh");
      setuserDetails.put(CommonConstant.USER_ADDRESS, "noida");
      setuserDetails.put(CommonConstant.USER_STATE, "UP");
      setuserDetails.put(CommonConstant.USER_CITY, "noida");
      setuserDetails.put(CommonConstant.USER_ZIP_CODE, "541234");
      setuserDetails.put(CommonConstant.USER_MOBILE, "7643551111");
      setuserDetails.put(CommonConstant.USER_COUNTRY, "India");
      setuserDetails.put(CommonConstant.USER_COMPANY, "Coforge");
      setuserDetails.put("day", "6");
      setuserDetails.put("month", "March");
      setuserDetails.put("year", "2020");
      return setuserDetails;
   }
}
    