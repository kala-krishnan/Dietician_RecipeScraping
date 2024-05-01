package WebScrapingService;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.stream.IntStream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import CommonUtils.ExcelUtility;
import DieticianPojos.RecipeDetails;
import io.github.bonigarcia.wdm.WebDriverManager;

public class getRecipeDetailsForDiabetics {

	public static void main(String[] args) throws IOException {
		ChromeOptions ops = new ChromeOptions();
		ops.addArguments("--remote-allow-origins=*");
	    ops.addArguments("--headless");
		ops.setExperimentalOption("detach", true);
	    String sheetName = "Diabetics(After Elimination)";
	    ExcelReaderWriter write = new ExcelReaderWriter();
		WebDriverManager.chromedriver().driverVersion("115.0.5790.170").setup();
		WebDriver driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		ExcelUtility Util = new ExcelUtility(System.getProperty("user.dir")+"//src//main//resources//InputData//DarlaTalalRecipes.xlsx");
		List<String> recipeList=Util.getData("Diabetes_EliminatedRecipes");
		System.out.println("No of recipes are " +recipeList.size());
		ExcelUtility xlUtil = new ExcelUtility(System.getProperty("user.dir")+"//src//main//resources//RecipeFiles//RecipeDetails.xlsx");
		
		xlUtil.setCellData(sheetName, 0,0, "Recipe ID");
		xlUtil.setCellData(sheetName, 0,1, "Recipe Name");
		xlUtil.setCellData(sheetName, 0,2, "Recipe Category(Tags)");
//		xlUtil.setCellData("Diabetics(After Elimination)", 0,3, "Food Category(Veg/non-veg/vegan/Jain)");
		xlUtil.setCellData(sheetName, 0,3, "Preparation Time");
		xlUtil.setCellData(sheetName, 0,4, "Cooking Time");
		xlUtil.setCellData(sheetName, 0,5, "Total Time Taken to cook");
		xlUtil.setCellData(sheetName, 0,6, "No on Servings");
		xlUtil.setCellData(sheetName, 0,7, "Ingredients");
		xlUtil.setCellData(sheetName, 0,8, "Recipe Description");
		xlUtil.setCellData(sheetName, 0,9, "Recipe URL");
		try
		{
		IntStream.range(1, recipeList.size()+1)  //.parallel()
		.forEach(r -> {
			try
			{
//		String url = "https://www.tarladalal.com/air-fried-bhindi-healthy-air-fryer-crispy-okra-42874r";
				System.out.println("URL is" +recipeList.get(r-1));
		RecipeDetails recipeDet = scrapeDetails(driver,recipeList.get(r-1));
		try
		{
			xlUtil.setCellData(sheetName, r, 0, recipeDet.getRecipeId());
			xlUtil.setCellData(sheetName, r, 1, recipeDet.getRecipeName());
			xlUtil.setCellData(sheetName, r, 2, recipeDet.getRecipeTags());
			xlUtil.setCellData(sheetName, r, 3, recipeDet.getRecipePrepTime());
			xlUtil.setCellData(sheetName, r, 4, recipeDet.getRecipeCookTime());
			xlUtil.setCellData(sheetName, r, 5, recipeDet.getTotalTime());
			xlUtil.setCellData(sheetName, r, 6, recipeDet.getNoOfServings());
			xlUtil.setCellData(sheetName, r, 7, recipeDet.getIngredientDetails());
			xlUtil.setCellData(sheetName, r, 8, recipeDet.getRecipeDescription());
			xlUtil.setCellData(sheetName, r, 9, recipeList.get(r-1) );
//			xlutil.setCellData("Sheet1", r, 10, RecipeURL );
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		write.writingintoExcel(sheetName,recipeDet,recipeList.get(r-1));
		}
			catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static RecipeDetails scrapeDetails(WebDriver driver,String url)
	{
		RecipeDetails recipe = new RecipeDetails();
		driver.get(url);
		String recipeID = url.replaceAll("[^0-9]", "");
		recipe.setRecipeId(recipeID);
		recipe.setRecipeName(driver.findElement(By.id("ctl00_cntrightpanel_lblRecipeName")).getText());
		recipe.setRecipeTags(driver.findElement(By.id("recipe_tags")).getText());
		recipe.setRecipePrepTime(driver.findElement(By.xpath("//p/time[@itemprop='prepTime']")).getText());
		recipe.setRecipeCookTime(driver.findElement(By.xpath("//p/time[@itemprop='cookTime']")).getText());
		recipe.setTotalTime(driver.findElement(By.xpath("//p/time[@itemprop='totalTime']")).getText());
		recipe.setNoOfServings(driver.findElement(By.id("ctl00_cntrightpanel_lblServes")).getText());
		recipe.setIngredientDetails(driver.findElement(By.id("rcpinglist")).getText());
		recipe.setRecipeDescription(driver.findElement(By.id("recipe_small_steps")).getText());
		
		System.out.println(driver.findElement(By.id("ctl00_cntrightpanel_lblRecipeName")).getText());
		System.out.println(driver.findElement(By.id("recipe_tags")).getText());
		System.out.println(driver.findElement(By.xpath("//p/time[@itemprop='prepTime']")).getText());
		System.out.println(driver.findElement(By.xpath("//p/time[@itemprop='cookTime']")).getText());
		System.out.println(driver.findElement(By.xpath("//p/time[@itemprop='totalTime']")).getText());
		
		System.out.println(driver.findElement(By.id("ctl00_cntrightpanel_lblServes")).getText());
		
		System.out.println(driver.findElement(By.id("rcpinglist")).getText());
		
		System.out.println(driver.findElement(By.id("recipe_small_steps")).getText());
		
		return recipe;
	}

}
