package stepDefinitions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.List;

public class TransactionFilter {

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

            // Step 3: Parse the transactions
            Thread.sleep(2000); // Wait for the transactions to load
            List<WebElement> transactions = driver.findElements(By.xpath("//div[@class='transaction-box']"));

            System.out.println("Number of transactions found: " + transactions.size());
            int matchCount = 0;

            for (WebElement transaction : transactions) {
                // Locate the inputs and outputs
                List<WebElement> inputs = transaction.findElements(By.xpath(".//div[@class='ins-and-outs']//div[@class='vins']"));
                List<WebElement> outputs = transaction.findElements(By.xpath(".//div[@class='vouts']"));

                // Debugging: Log the number of inputs and outputs
                System.out.println("Transaction Inputs: " + inputs.size());
                System.out.println("Transaction Outputs: " + outputs.size());

                // Filter transactions with 1 input and 2 outputs
                if (inputs.size() == 1 && outputs.size() == 2) {
                    // Get and print the transaction hash
                    String transactionHash = transaction.findElement(By.xpath(".//a[contains(@href, 'tx')]")).getText();
                    System.out.println("Matching Transaction Hash: " + transactionHash);
                    matchCount++;
                }
            }

            System.out.println("Total matching transactions: " + matchCount);

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
