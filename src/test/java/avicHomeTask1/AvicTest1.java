package avicHomeTask1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class AvicTest1 {
    private WebDriver driver;

    @BeforeTest
    public void profileSetUp() {
        System.setProperty("webdriver.chrome.driver", "D:\\workspace\\JavaEpam2021\\tests\\src\\main\\resources\\chromedriver.exe");
    }

    @BeforeMethod
    public void testsSetUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://avic.ua");

    }

    @Test(priority = 1)
    public void checkThatUrlContainsTitleOfParagraph() {
        driver.findElement(By.xpath("//ul[contains(@class, 'header')]//a[text()='Оплата и доставка']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        Assert.assertTrue(driver.getCurrentUrl().contains("pokupka-i-dostavka"));
    }

    @Test(priority = 1)
    public void checkThatCartCheckoutPageIsOpen() {
        driver.findElement(By.xpath("//span[@class='sidebar-item-title']//span[text()='Умный дом']")).click();

        driver.findElement(By.xpath("//a[@class='prod-cart__buy'][contains(@data-ecomm-cart,'235113')]")).click();
        WebDriverWait wait = new WebDriverWait(driver, 30);//ждем пока не отобразится попап с товаром добавленным в корзину
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("js_cart")));
        driver.findElement(By.xpath("//a[@class='main-btn  main-btn--green']")).click();
        wait = new WebDriverWait(driver, 30);
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout"));
    }

    @Test(priority = 1)
    public void checkThatSumToPayIsIncreaseByPlus() {
        driver.findElement(By.xpath("//span[@class='sidebar-item-title']//span[text()='Умный дом']")).click();

        driver.findElement(By.xpath("//a[@class='prod-cart__buy'][contains(@data-ecomm-cart,'235113')]")).click();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("js_cart")));
        driver.findElement(By.xpath("//a[@class='main-btn  main-btn--green']")).click();
        driver.findElement(By.xpath("//span[@class='js_plus btn-count btn-count--plus']")).click();
        new WebDriverWait(driver, 60);
        String actualProductsCountInCart = driver.findElement(By.xpath("//span[@id='total-price']")).getText();
        Assert.assertEquals(actualProductsCountInCart, "1098");//there is a real bug here
    }

    @Test(priority = 2)
    public void checkThatCartIsEmpty() {
        driver.findElement(By.xpath("//span[@class='sidebar-item-title']//span[text()='Умный дом']")).click();

        driver.findElement(By.xpath("//a[@class='prod-cart__buy'][contains(@data-ecomm-cart,'235113')]")).click();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("js_cart")));
        driver.findElement(By.xpath("//a[@class='main-btn  main-btn--green']")).click();
        driver.findElement(By.xpath("//i[@class='icon icon-close']")).click();
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("modalAlert")));
        String message = driver.findElement(By.xpath("//div[@class='ttl js_title']")).getText();
        Assert.assertEquals(message, "Корзина пустая!");
    }

    


    @AfterMethod
    public void tearDown() {
        driver.close();
    }


}
