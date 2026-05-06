package org.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class Task2 {

    public static void getIPAddress(WebDriver webDriver) throws Exception {
        String url = "https://api.ipify.org/?format=json";

        // Используем синхронный XHR из контекста браузера для получения JSON
        String jsonText = (String) ((JavascriptExecutor) webDriver).executeScript(
            "var xhr = new XMLHttpRequest();" +
            "xhr.open('GET', arguments[0], false);" +
            "xhr.send(null);" +
            "return xhr.responseText;",
            url
        );

        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(jsonText);
        String ip = (String) obj.get("ip");
        System.out.println("IP-адрес клиента: " + ip);
    }
}
