package pages;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.OS;
import utils.Driver;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.remote.RemoteWebElement;
import java.util.Collections;
import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.Point;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class WiglLoginPage extends BasePage {

    public WiglLoginPage() {
        super(Driver.getDriver());
    }

    private By getEmailInput() {
        return OS.isAndroid() ?
                AppiumBy.xpath("//android.widget.EditText[@resource-id='email-input']") :
                AppiumBy.xpath("//XCUIElementTypeTextField[@name='email-input']");
    }

    private By getPasswordInput() {
        return OS.isAndroid() ?
                AppiumBy.xpath("//android.widget.EditText[@resource-id='password-input']") :
                AppiumBy.xpath("//XCUIElementTypeSecureTextField[@name='password-input']");
    }

    private By getLoginButton() {
        return OS.isAndroid() ?
                AppiumBy.xpath("//android.widget.Button[@text='Login']") :
                AppiumBy.xpath("//XCUIElementTypeButton[@name='Login']");
    }

    private By getCashbackAmount() {
        return OS.isAndroid() ?
                AppiumBy.xpath("//android.widget.TextView[@resource-id='cashback-amount']") :
                AppiumBy.xpath("//XCUIElementTypeStaticText[@name='cashback-amount']");
    }

    private By getCryptoBalance() {
        return OS.isAndroid() ?
                AppiumBy.xpath("//android.widget.TextView[@resource-id='crypto-balance']") :
                AppiumBy.xpath("//XCUIElementTypeStaticText[@name='crypto-balance']");
    }

    public By getCreateAccountText() {
        return OS.isAndroid() ?
                AppiumBy.xpath("//android.widget.TextView[contains(@text, 'Create your account')]") :
                AppiumBy.xpath("//XCUIElementTypeStaticText[contains(@name, 'Create your account')]");
    }

    public By getLanguageOptionsContainer() {
        return OS.isAndroid() ?
                AppiumBy.xpath("//android.view.ViewGroup[contains(@resource-id, 'language-container')]") :
                AppiumBy.xpath("//XCUIElementTypeOther[contains(@name, 'language-container')]");
    }

    public By getLanguageOption(String language) {
        String text = language.equals("Français") ? "FR" : "EN";
        return OS.isAndroid() ?
                AppiumBy.xpath(String.format("//android.widget.TextView[@text='%s']", text)) :
                AppiumBy.xpath(String.format("//XCUIElementTypeStaticText[@name='%s']", text));
    }

    public By getFrenchLanguageIndicator() {
        return OS.isAndroid() ?
                AppiumBy.xpath("//android.widget.TextView[@text='FR']") :
                AppiumBy.xpath("//XCUIElementTypeStaticText[@name='FR']");
    }

    private WebElement getLanguageToggleButton() {
        if (OS.isAndroid()) {
            // Utilisation d'un localisateur plus flexible pour le bouton de sélection de langue
            return driver.findElement(By.xpath("//android.widget.TextView[contains(@text, '󰅀')]"));
        } else {
            // Pour iOS, nous gardons la logique existante
            return driver.findElement(By.xpath("//XCUIElementTypeButton[@name='language-toggle-button']"));
        }
    }

    public void login(String email, String password) {
        System.out.println("Saisie de l'email: " + email);
        WebElement emailField = driver.findElement(getEmailInput());
        emailField.click();
        emailField.clear();
        emailField.sendKeys(email);
        hideKeyboard();

        System.out.println("Saisie du mot de passe: " + password);
        WebElement passwordField = driver.findElement(getPasswordInput());
        passwordField.click();
        passwordField.clear();
        passwordField.sendKeys(password);
        hideKeyboard();

        System.out.println("Clic sur le bouton de connexion");
        driver.findElement(getLoginButton()).click();
    }

    public String getCashbackValue() {
        return driver.findElement(getCashbackAmount()).getText();
    }

    public String getCryptoBalanceValue() {
        return driver.findElement(getCryptoBalance()).getText();
    }

    private void hideKeyboard() {
        if (OS.isAndroid()) {
            try {
                Thread.sleep(1000);
                try {
                    ((io.appium.java_client.android.AndroidDriver) driver).hideKeyboard();
                } catch (Exception e1) {
                    try {
                        driver.navigate().back();
                    } catch (Exception e2) {
                        System.out.println("Impossible de masquer le clavier via le bouton retour: " + e2.getMessage());
                    }
                }
                Thread.sleep(500);
            } catch (Exception e) {
                System.out.println("Impossible de masquer le clavier: " + e.getMessage());
            }
        } else {
            try {
                Thread.sleep(1000);
                try {
                    By doneButton = AppiumBy.xpath("//XCUIElementTypeButton[@name='Done']");
                    driver.findElement(doneButton).click();
                } catch (Exception e1) {
                    try {
                        By emptyArea = AppiumBy.xpath("//XCUIElementTypeApplication");
                        driver.findElement(emptyArea).click();
                    } catch (Exception e2) {
                        System.out.println("Impossible de masquer le clavier iOS: " + e2.getMessage());
                    }
                }
                Thread.sleep(500);
            } catch (Exception e) {
                System.out.println("Erreur lors du masquage du clavier iOS: " + e.getMessage());
            }
        }
    }

    public boolean isHomePageDisplayed() {
        try {
            return driver.findElement(getCreateAccountText()).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean areLanguageOptionsDisplayed() {
        try {
            WebElement languageToggle = getLanguageToggleButton();
            System.out.println("Bouton de sélection de langue trouvé");
            return languageToggle.isDisplayed();
        } catch (Exception e) {
            System.out.println("Erreur lors de la vérification de la visibilité des options de langue: " + e.getMessage());
            return false;
        }
    }

    public void selectLanguage() {
        System.out.println("Tentative de sélection de la langue");
        try {
            WebElement languageToggle = getLanguageToggleButton();
            System.out.println("Bouton de sélection de langue trouvé, clic en cours...");
            languageToggle.click();
            
            // Bouton de fermeture
            By closeButton = OS.isAndroid() ?
                AppiumBy.xpath("(//android.widget.TextView)[12]") :
                AppiumBy.xpath("//XCUIElementTypeButton[@name='Close']");
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement closeButtonElement = wait.until(ExpectedConditions.elementToBeClickable(closeButton));
            System.out.println("Bouton de fermeture trouvé, clic en cours...");
            closeButtonElement.click();
        } catch (Exception e) {
            System.out.println("Erreur lors de la sélection de la langue: " + e.getMessage());
            throw new RuntimeException("Échec de la sélection de la langue", e);
        }
    }

    public boolean isLanguageFrench() {
        try {
            return driver.findElement(getFrenchLanguageIndicator()).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void dragCreateAccountTextUp() {
        int maxAttempts = 3;
        int attempt = 0;
        boolean success = false;

        while (attempt < maxAttempts && !success) {
            try {
                // Utiliser la nouvelle méthode de défilement
                scrollWiglTextUp();
                success = true;
                System.out.println("Défilement du texte Wigl vers le haut réussi");
            } catch (Exception e) {
                System.out.println("Tentative " + (attempt + 1) + " échouée: " + e.getMessage());
            }
            attempt++;
        }

        if (!success) {
            throw new RuntimeException("Échec du défilement du texte Wigl vers le haut après " + maxAttempts + " tentatives");
        }
    }

    public void scrollViewGroupUp() {
        try {
            By viewGroupLocator = AppiumBy.xpath("//android.view.ViewGroup[@bounds='[0,63][1080,1316]']");
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(viewGroupLocator));
            
            // Défilement vers le haut de 30% de la hauteur de l'écran
            scrollUp(element, 30);
            
            // Attendre que l'animation soit terminée
            Thread.sleep(1500);
            
        } catch (Exception e) {
            System.out.println("Erreur lors du défilement du ViewGroup: " + e.getMessage());
            throw new RuntimeException("Échec du défilement du ViewGroup vers le haut", e);
        }
    }

    public void scrollWelcomeTextUp() {
        try {
            By welcomeTextLocator = AppiumBy.xpath("//android.widget.TextView[@text='Welcome|']");
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(welcomeTextLocator));
            
            // Obtenir la position initiale pour vérification
            Point initialPosition = element.getLocation();
            
            // Défilement vers le haut de 40% de la hauteur de l'écran
            scrollUp(element, 40);
            
            // Attendre que l'animation soit terminée
            Thread.sleep(1500);
            
            // Vérifier que le défilement a fonctionné
            Point newPosition = element.getLocation();
            if (newPosition.getY() >= initialPosition.getY()) {
                throw new RuntimeException("Le défilement n'a pas déplacé l'élément vers le haut");
            }
            
            System.out.println("Défilement du texte de bienvenue réussi");
            
        } catch (Exception e) {
            System.out.println("Erreur lors du défilement du texte de bienvenue: " + e.getMessage());
            throw new RuntimeException("Échec du défilement du texte de bienvenue vers le haut", e);
        }
    }
} 