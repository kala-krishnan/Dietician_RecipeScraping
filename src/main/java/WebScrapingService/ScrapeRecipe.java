package WebScrapingService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ScrapeRecipe {

	public void getAllRecipeList() throws IOException {
		
		ChromeOptions ops = new ChromeOptions();
		ops.addArguments("--remote-allow-origins=*");
		ops.addArguments("--headless");
		ops.setExperimentalOption("detach", true);

		WebDriverManager.chromedriver().driverVersion("115.0.5790.170").setup();
		WebDriver driver=new ChromeDriver(ops);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();
		String URL = "https://www.tarladalal.com/RecipeAtoZ.aspx?beginswith=A&pageindex=1";
		driver.get(URL);
		List<String> alphabet = Arrays.asList("A","B","C","D","E","F","G","H",
				"I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z");
	
		List<String> urlTableList = alphabet.stream()
				.map(e->"https://www.tarladalal.com/RecipeAtoZ.aspx?beginswith="+e+"&pageindex=1")
				.collect(Collectors.toList());
		
		List<ArrayList<String>> finalList = new ArrayList<ArrayList<String>>();
		for(String url: urlTableList) {
			System.out.println("URL is "+url);
			driver.navigate().to(url);
			ArrayList<String> tempList = (ArrayList<String>)getURLListAtoZURL(driver, url);
			finalList.add(tempList);
		}
		List<String> finalUrls = finalList.stream().flatMap(e->e.stream()).collect(Collectors.toList());
		writeColumnByColumn(finalUrls);

	}
	public static void writeColumnByColumn(List<String> finalUrls) throws IOException {
		   XSSFWorkbook workbook = new XSSFWorkbook();
		   XSSFSheet sheet = workbook.createSheet();
		   Row row = sheet.createRow(0);
		   
		   IntStream.range(0, finalUrls.size())
		   	.forEach(e -> row.createCell(e).setCellValue(finalUrls.get(e)));
		 
		   FileOutputStream outputStream = new FileOutputStream("//src//test//resources//RecipeFiles//AllRecipeURLs.xlsx");
		   try (outputStream) {
		       workbook.write(outputStream);
		       System.out.println("Finished Writing");
		   }
		  }
	public static List<String> getURLListAtoZURL(WebDriver driver, String URL){  
		driver.get(URL);
		driver.manage().window().maximize();
		
		List<String> urls = new ArrayList<String>();
		try {
			int lastPage = Integer.parseInt(driver.findElement(By.cssSelector("a.respglink:last-of-type")).getText());
			System.out.println("LastPage: "+URL +" " +lastPage);
			WebElement nextButtonClass = driver.findElement(By.xpath("//a[@class='rescurrpg']/following-sibling::a[1]"));
			for(int i=1; i<=lastPage; i++) {
//				Thread.sleep(500);
				List<WebElement> aElements = driver.findElements(By.cssSelector("span.rcc_recipename a"));
				System.out.println("size of urls elements : "+i+" " +URL+" "+ aElements.size());
//				driver.getPageSource().contains("lunch");
				aElements.stream().parallel()
					.map(e->e.getAttribute("href"))
					.forEach(s->urls.add(s));

				if(i==lastPage) {
					break;
				}
				// locating next button
				nextButtonClass = driver.findElement(By.xpath("//a[@class='rescurrpg']/following-sibling::a[1]"));
				//traversing through the table until the last button and adding names to the list defined about
				nextButtonClass.click();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(urls);
		return urls;

	}
	
	public static void main(String args[]) throws IOException
{
		ScrapeRecipe s = new ScrapeRecipe();
		s.getAllRecipeList();
}
	}

