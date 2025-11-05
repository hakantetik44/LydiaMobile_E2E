package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import utils.OS;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Méthodes d'attente
    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Actions de base
    protected void click(By locator) {
        waitForClickable(locator).click();
    }

    protected void sendKeys(By locator, String text) {
        WebElement element = waitForVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    // Vérifications
    protected boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Gestion du clavier
    protected void hideKeyboard() {
        try {
            if (OS.isAndroid()) {
                try {
                    ((AndroidDriver) driver).hideKeyboard();
                } catch (Exception e1) {
                    driver.navigate().back();
                }
            } else {
                try {
                    ((IOSDriver) driver).hideKeyboard();
                } catch (Exception e2) {
                    try {
                        By doneButton = AppiumBy.accessibilityId("Done");
                        if (isElementPresent(doneButton)) {
                            click(doneButton);
                        } else {
                            By tapPoint = AppiumBy.xpath("//XCUIElementTypeApplication");
                            if (isElementPresent(tapPoint)) {
                                click(tapPoint);
                            }
                        }
                    } catch (Exception e3) {
                        System.out.println("Impossible de masquer le clavier iOS : " + e3.getMessage());
                    }
                }
            }
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("Erreur lors de la tentative de masquage du clavier : " + e.getMessage());
        }
    }

    // Actions avec logs
    protected void clickWithLog(By locator, String elementName) {
        try {
            System.out.println("🔍 Tentative de clic sur " + elementName);
            click(locator);
            System.out.println("✅ Clic réussi sur " + elementName);
        } catch (Exception e) {
            System.out.println("❌ Erreur lors du clic sur " + elementName + " : " + e.getMessage());
            throw e;
        }
    }

    protected void sendKeysWithLog(By locator, String text, String fieldName) {
        try {
            System.out.println("⌨️  Saisie de '" + text + "' dans " + fieldName);
            sendKeys(locator, text);
            hideKeyboard();
            System.out.println("✅ Saisie réussie dans " + fieldName);
        } catch (Exception e) {
            System.out.println("❌ Erreur lors de la saisie dans " + fieldName + " : " + e.getMessage());
            throw e;
        }
    }
}
