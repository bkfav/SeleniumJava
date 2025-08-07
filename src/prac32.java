import java.util.*; 
import java.net.*; 
import java.time.Duration;
import org.openqa.selenium.WebDriver; 
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By; 
import org.openqa.selenium.Alert; 
import org.openqa.selenium.support.ui.WebDriverWait; 
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;

public class prac32 {
    public static void main(String[] args){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\KN00832612\\Documents\\Drivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver(); 
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        try{
            //alert
            driver.get("https://the-internet.herokuapp.com/javascript_alerts");
            driver.findElement(By.xpath("//button[text()=\"Click for JS Prompt\"]")).click();
            Alert alert = driver.switchTo().alert(); 
            System.out.println("Alert Message: "+ alert.getText()); 
            alert.sendKeys("Hello");
            alert.accept();
            //frame
            driver.get("https://ui.vision/demo/webtest/frames/");
            driver.switchTo().frame(0);
            driver.findElement(By.name("mytext1")).sendKeys("Hello");
            driver.switchTo().defaultContent();
            WebElement secondframe = driver.findElement(By.xpath("//frame[@src=\"frame_2.html\"]"));
            driver.switchTo().frame(secondframe);
            driver.findElement(By.name("mytext2")).sendKeys("Hello2");
            //Select
            driver.get("https://demo.guru99.com/test/newtours/register.php");
            JavascriptExecutor js = (JavascriptExecutor) driver; 
            js.executeScript("scrollBy(0,500)");
            Select select = new Select(driver.findElement(By.name("country")));
            select.selectByIndex(10);
            Thread.sleep(1000);
            select.selectByContainsVisibleText("INDIA");
            System.out.println(select.getFirstSelectedOption().getText());
            //Broken link
            String homeurl = "https://www.zlti.com/";
            driver.get(homeurl);
            List<WebElement> urList = driver.findElements(By.tagName("a"));
            for(int i=0; i<urList.size(); i++){
                String urlString = urList.get(i).getAttribute("href");
                if(urlString == null || urlString.isEmpty())
                    System.out.println("Empty URL"); 
                else if (urlString.startsWith(homeurl)==false)
                    System.out.println(urlString+" does not start with homeurl");
                else if(urlString.startsWith("http")){
                    URL url = new URI(urlString).toURL();
                    //System.setProperty("http.protocol", "TLVs1.0");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("HEAD");
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.connect();
                    if(httpURLConnection.getResponseCode() >= 400)
                        System.out.println("Broken link: "+url);
                    else
                        System.out.println("Working link: "+url);  
                }
            }
            //Dynamic controls
            driver.navigate().to("https://the-internet.herokuapp.com/dynamic_controls");
            driver.findElement(By.xpath("//form[@id=\"input-example\"]/button")).click();
            WebElement inputText = driver.findElement(By.xpath("//form[@id=\"input-example\"]/input"));
            wait.until(ExpectedConditions.elementToBeClickable(inputText)).sendKeys("enabled");
            if(wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("message")))).getText().endsWith("enabled!"))
                System.out.println("PASS");
            else
                System.out.println("FAIL");
            //Action class
            driver.get("https://the-internet.herokuapp.com/drag_and_drop");
            WebElement first = driver.findElement(By.id("column-a")); 
            WebElement second = driver.findElement(By.id("column-b"));
            Actions action = new Actions(driver);
            action.dragAndDrop(first, second).perform();
            if(first.getText().equalsIgnoreCase("B"))
                System.out.println("PASS");
            else
                System.out.println("FAIL");
            driver.navigate().to("https://the-internet.herokuapp.com/context_menu");
            WebElement control = driver.findElement(By.id("hot-spot"));
            action.contextClick(control).perform();
            Alert alert2 = driver.switchTo().alert();
            System.out.println("Alert Message: "+alert2.getText());
            alert2.accept();
            Thread.sleep(2000);

        }catch(Exception e){
            System.out.println("Exception: "+e.getMessage());
        } finally{
            driver.quit();
        }
    }

}
