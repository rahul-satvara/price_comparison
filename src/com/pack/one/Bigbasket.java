package com.pack.one;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

public class Bigbasket {

	WebDriver driver;

	@Test
	public void login() throws IOException
	{
		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
		driver.get("http://www.bigbasket.com/");
		Select sel = new Select(driver.findElement(By.id("ftv-city-selectboxdiv")));
        sel.selectByIndex(0);
        driver.findElement(By.id("skip_explore")).click();
		
		String csv_path = "D:\\Export_bigbasket_url.csv";
		String line;
		String categories = null;
		BufferedReader br = new BufferedReader(new FileReader(csv_path));		
		
		String fileName = "d:\\JAVA_SCRIPT\\Workspace\\Demo_Ant_Test\\bigbasket.csv";
		FileWriter writer = new FileWriter(fileName);
		String FILE_HEADER = "pid;name;description;short_desc;url;categories;tags;brand;price;shipping;shipping_comment;image";
		writer.append(FILE_HEADER);
		writer.append("\n");
      		
		while((line = br.readLine())!=null) {
			
				String[] cols = line.split(";");
				String a = cols[0];
				String a1 = a.replaceAll("\\d","");
				String b = cols[1];
                driver.get(b);
                String title = driver.findElement(By.className("uiv2-product-name")).getText();
                String description = "";
                String short_description = "";
                String url = b;
                String shipping = "";
                
                if(a1.equals("V"))
                {
                	categories = "Fruits and Vegetables,Vegetables";
                }
                else if(a1.equals("F"))
                {
                	categories = "Fruits and Vegetables,Fruits";
                }
                else if(a1.equals("FL"))
                {
                	categories = "Grocery,Flour";
                }
                else if(a1.equals("RI"))
                {
                	categories = "Grocery,Rice";
                }
                else if(a1.equals("SU"))
                {
                	categories = "Grocery,Sugar";
                }
                else if(a1.equals("SA"))
                {
                	categories = "Grocery,Salt";
                }
                else if(a1.equals("OIL"))
                {
                	categories = "Grocery,Oil";
                }
                
                String tags = "";
                String brand_name = driver.findElement(By.className("uiv2-brand-name")).getText();
                String price = driver.findElement(By.className("uiv2-price")).getText();
                String shipping_comment = driver.findElement(By.className("delivery-slot-label")).getText();
                String image = driver.findElement(By.cssSelector(".zoomPad img")).getAttribute("src");
                String total_record = a+';'+title+';'+description+';'+short_description+';'+url+';'+categories+';'+tags+';'+brand_name+';'+price+';'+shipping+';'+shipping_comment+';'+image;
                String final_record = total_record.concat(";");
                
                try {    
				    writer.append(final_record);
			     }	
			     catch (IOException ex) {
				    ex.printStackTrace();    
				 } 
			
			writer.append("\n");
		}
		
		writer.flush();
        writer.close(); 
		
	}
	
}
