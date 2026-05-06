package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class App {

    public static void main(String[] args) throws Exception {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        // Укажите путь к chromedriver, если он не находится в PATH
        // System.setProperty("webdriver.chrome.driver", "/путь/к/chromedriver");
        WebDriver webDriver = new ChromeDriver(options);

        try {
            // Задание №1 — получить сгенерированный пароль
            webDriver.get("https://www.calculator.net/password-generator.html");

            // Пароль генерируется JS в div.verybigtext > b, ждём его появления
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
            WebElement passwordElem = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.verybigtext b"))
            );
            String password = passwordElem.getText();

            System.out.println("=== Задание №1 ===");
            if (password != null && !password.isEmpty()) {
                System.out.println("Сгенерированный пароль: " + password);
            } else {
                System.out.println("Пароль не найден на странице.");
            }

            // Задание №2 — получить IP-адрес через api.ipify.org
            System.out.println("\n=== Задание №2 ===");
            Task2.getIPAddress(webDriver);

            // Задание №3 — прогноз погоды для Нижнего Новгорода
            System.out.println("\n=== Задание №3 ===");
            Task3.getWeatherForecast(webDriver);

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        } finally {
            webDriver.quit();
        }
    }
}
