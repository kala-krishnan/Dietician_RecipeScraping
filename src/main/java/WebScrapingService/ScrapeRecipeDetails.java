package WebScrapingService;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import CommonUtils.ExcelUtility;
import enumConstants.FoodCategory;
import enumConstants.RecipeCategory;
import enumConstants.TargettedMorbidConditions;
import io.github.bonigarcia.wdm.WebDriverManager;



public class ScrapeRecipeDetails {
	
	public void setColumnsforAllRecipeList() throws IOException
	{
	ChromeOptions ops = new ChromeOptions();
	ops.addArguments("--remote-allow-origins=*");
    ops.addArguments("--headless");
	ops.setExperimentalOption("detach", true);
    
	WebDriverManager.chromedriver().driverVersion("115.0.5790.170").setup();
	WebDriver driver=new ChromeDriver(ops);
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	driver.manage().window().maximize();
	
	String path=".\\dataFiles\\tarladalal.xlsx";
	ExcelUtility xlutil=new ExcelUtility(path);
	ScrapeRecipe rec = new ScrapeRecipe();
	rec.getAllRecipeList();
	
	xlutil.setCellData("Sheet1", 0,0, "Recipe ID");
	xlutil.setCellData("Sheet1", 0,1, "Recipe Name");
	xlutil.setCellData("Sheet1", 0,2, "Recipe Category(Breakfast/lunch/snack/dinner)");
	xlutil.setCellData("Sheet1", 0,3, "Food Category(Veg/non-veg/vegan/Jain)");
	xlutil.setCellData("Sheet1", 0,4, "Ingredients");
	xlutil.setCellData("Sheet1", 0,5, "Preparation Time");
	xlutil.setCellData("Sheet1", 0,6, "Cooking Time");
	xlutil.setCellData("Sheet1", 0,7, "Preparation method");
	xlutil.setCellData("Sheet1", 0,8, "Nutrient values");
	xlutil.setCellData("Sheet1", 0,9, "Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism)");
	xlutil.setCellData("Sheet1", 0,10, "Recipe URL");
	
	
	
//	List<String> URLList = xlutil.getData("AllRecipeURLs.xlsx", "Sheet1");
	
	
	
	}
}
