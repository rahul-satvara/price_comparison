package com.pack.one;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class Zopnow {

	WebDriver driver;

	@Test
	public void login() throws IOException
	{
		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		driver.get("http://www.zopnow.com/");
		
		String csv_path = "D:\\Export_zopnow_url.csv";
		String line;
		String categories = null;
		String description = null;
		BufferedReader br = new BufferedReader(new FileReader(csv_path));		
		
		String fileName = "d:\\JAVA_SCRIPT\\Workspace\\Demo_Ant_Test\\zopnow.csv";
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
                String title = driver.findElement(By.cssSelector(".detailview-option2 h1")).getText();   
                description = "";    
                String short_description = "";
                String url = b;
                
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
                
                String shipping = "";
                String tags = "";
                String brand_name = driver.findElement(By.xpath("//td[@itemprop='brand']/span")).getText();
                String price1 = driver.findElement(By.xpath("//td[@itemprop='price']")).getText();
                String price = price1.replace("₹ ", "");
                String shipping_comment = "";
                String image = driver.findElement(By.cssSelector(".itemImageLarge img")).getAttribute("src");
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
