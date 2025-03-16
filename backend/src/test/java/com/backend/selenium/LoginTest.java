// package selenium;

// import org.junit.jupiter.api.Test;
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.chrome.ChromeDriver;

// import static org.junit.jupiter.api.Assertions.assertEquals;

// public class LoginTest {

// @Test
// public void testLoginPage() {
// // Imposta il percorso del driver di Chrome
// System.setProperty("webdriver.chrome.driver",
// "src/test/resources/drivers/chromedriver");

// WebDriver driver = new ChromeDriver();
// try {
// driver.get("http://localhost:4200");
// String title = driver.getTitle();
// assertEquals("Login - Ticketing", title);
// } finally {
// driver.quit();
// }
// }
// }
