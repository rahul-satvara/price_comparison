import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;


public class TestB {
	
	WebDriver driver;

	@Test
	public void login() throws IOException
	{
		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		driver.get("http://www.bigbasket.com/");
		Select sel = new Select(driver.findElement(By.id("ftv-city-selectboxdiv")));
        sel.selectByIndex(0);
        driver.findElement(By.id("skip_explore")).click();
		
		String csv_path = "D:\\Export_bigbasket_url.csv";
		String line;
		String categories = null;
		int linenumber;
		BufferedReader br = new BufferedReader(new FileReader(csv_path));		
		
		String fileName = "d:\\records_bigbasketdata.csv";
		FileWriter writer = new FileWriter(fileName);
		String FILE_HEADER = "pid;name;description;short_desc;url;categories;tags;brand;price;shipping;shipping_comment;image";
		writer.append(FILE_HEADER);
		writer.append("\n");
        int count=0; 		
		linenumber=0;			
		while((line = br.readLine())!=null) {
			if(linenumber>0)
			{
			    count++;
			    System.out.println(count);
				String[] cols = line.split(";");
				String a = cols[0];
                driver.get(a);
                String title = driver.findElement(By.className("uiv2-product-name")).getText();
                String description = driver.findElement(By.cssSelector("#uiv2-tab1 p")).getText();
                String short_description = "";
                String url = a;
                String shipping = "";
                if(driver.findElements(By.className("pd-left-nav-active")).size()!=0)
                {	
                  String categories1 = driver.findElement(By.className("pd-left-nav-active")).getText();
                  categories = categories1.replace(">", "");  
                }
                else
                {	
                  categories = "";	
                }
                String tags = "";
                String brand_name = driver.findElement(By.className("uiv2-brand-name")).getText();
                String price = driver.findElement(By.className("uiv2-price")).getText();
                String shipping_comment = driver.findElement(By.className("delivery-slot-label")).getText();
                String image = driver.findElement(By.cssSelector(".zoomPad img")).getAttribute("src");
                String total_record = count+';'+title+';'+description+';'+short_description+';'+url+';'+categories+';'+tags+';'+brand_name+';'+price+';'+shipping+';'+shipping_comment+';'+image;
                String final_record = total_record.concat(";");
                
                try {    
				    writer.append(final_record);
			     }	
			     catch (IOException ex) {
				    ex.printStackTrace();    
				 } 
			}
			writer.append("\n");
			linenumber++;
		}
		
		writer.flush();
        writer.close(); 
		
	}
}
