package stepDefinitions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.List;

public class BitcoinTransactionValidation {

    public static void main(String[] args) {
        // Setup WebDriver
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            // Open the Bitcoin block page
            driver.get("https://blockstream.info/block/000000000000000000076c036ff5119e5a5a74df77abf64203473364509f7732");

            // Wait for the page to load
            Thread.sleep(3000);

            // Step 1: Click on the first link
            WebElement firstLink = driver.findElement(By.xpath("//a[text()='6587550e410fa1815cf180decc03ef84dcddd19478a081097bd2090c5e85b4b6']"));
            firstLink.click();

            // Step 2: Click on the second link
            Thread.sleep(2000); // Wait for navigation
            WebElement secondLink = driver.findElement(By.xpath("//a[text()='1PQwtwajfHWyAkedss5utwBvULqbGocRpu']"));
            secondLink.click();

            // Step 3: Retrieve and validate the transactions
            Thread.sleep(2000); // Wait for the transactions to load
            List<WebElement> transactions = driver.findElements(By.xpath("//div[@class='transaction-box']"));

            // Validate the number of transactions
            if (transactions.size() == 25) {
                System.out.println("Validation passed: 25 transactions are displayed.");
            } else {
                System.out.println("Validation failed: Expected 25 transactions, but found " + transactions.size());
                throw new AssertionError("Incorrect number of transactions displayed.");
            }

            // Print transaction hashes (Optional)
            for (WebElement transaction : transactions) {
                String transactionHash = transaction.findElement(By.xpath(".//a[contains(@href, 'tx')]")).getText();
                System.out.println("Transaction Hash: " + transactionHash);
            }

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
