package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.io.FileWriter;
import java.io.PrintWriter;

public class Task3 {

    public static void getWeatherForecast(WebDriver webDriver) throws Exception {
        String url = "https://api.open-meteo.com/v1/forecast" +
            "?latitude=56&longitude=44" +
            "&hourly=temperature_2m,rain" +
            "&current=cloud_cover" +
            "&timezone=Europe%2FMoscow" +
            "&forecast_days=1" +
            "&wind_speed_unit=ms";

        String jsonText = (String) ((JavascriptExecutor) webDriver).executeScript(
            "var xhr = new XMLHttpRequest();" +
            "xhr.open('GET', arguments[0], false);" +
            "xhr.send(null);" +
            "return xhr.responseText;",
            url
        );

        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(jsonText);
        JSONObject hourly = (JSONObject) obj.get("hourly");
        JSONArray times = (JSONArray) hourly.get("time");
        JSONArray temperatures = (JSONArray) hourly.get("temperature_2m");
        JSONArray rains = (JSONArray) hourly.get("rain");

        String rowFmt = "%-4s | %-20s | %-13s | %-12s";
        String sep = "-".repeat(58);
        StringBuilder sb = new StringBuilder();
        sb.append(sep).append("\n");
        sb.append(String.format(rowFmt, "№", "Дата/время", "Температура", "Осадки (мм)")).append("\n");
        sb.append(sep).append("\n");

        for (int i = 0; i < times.size(); i++) {
            String time = (String) times.get(i);
            double temp = ((Number) temperatures.get(i)).doubleValue();
            double rain = ((Number) rains.get(i)).doubleValue();
            sb.append(String.format("%-4d | %-20s | %-13.1f | %-12.2f%n",
                i + 1, time, temp, rain));
        }
        sb.append(sep);

        String table = sb.toString();
        System.out.println(table);

        try (PrintWriter pw = new PrintWriter(new FileWriter("result/forecast.txt"))) {
            pw.print(table);
        }
        System.out.println("Таблица сохранена в result/forecast.txt");
    }
}
