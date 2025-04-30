   package utlity;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

public class JsonUtils {
   private static DocumentContext loadJson(String input) throws IOException {
      File file = new File(System.getProperty("user.dir") + input);
      String jsonContent;
      if (input.endsWith(".json") && file.exists()) {
         jsonContent = new String(Files.readAllBytes(file.toPath()));
      } else {
         jsonContent = input;
      }

      return JsonPath.parse(jsonContent);
   }

   public static String getStringValueFromJson(String jsonFilePath, String path) throws IOException {
      return (String)loadJson(jsonFilePath).read(path);
   }

   public static int getIntegerValueFromJson(String jsonFilePath, String path) throws IOException {
      return (Integer)loadJson(jsonFilePath).read(path);
   }

   public static boolean getBooleanValueFromJson(String jsonFilePath, String path) throws IOException {
      return (Boolean)loadJson(jsonFilePath).read(path);
   }

   public static <T> List<T> getListvalueFromJson(String jsonFilePath, String path) throws IOException {
      return (List)loadJson(jsonFilePath).read(path);
   }

   public static <K, V> Map<K, V> getMapFromJson(String jsonFilePath, String path) throws IOException {
      return (Map)loadJson(jsonFilePath).read(path);
   }

   public static String getJsonObjectAsString(String jsonFilePath, String path) throws IOException {
      Object obj = loadJson(jsonFilePath).read(path);
      return obj.toString();
   }

   public static String getRawJsonContent(String jsonFilePath) throws IOException {
      File file = new File(System.getProperty("user.dir") + jsonFilePath);
      return new String(Files.readAllBytes(file.toPath()));
   }
}
    