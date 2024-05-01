package WebScrapingService;

import java.io.IOException;

import CommonUtils.ExcelUtility;
import DieticianPojos.RecipeDetails;

public class ExcelReaderWriter {
	
	public void writingintoExcel(String sheetName,RecipeDetails recipeDet,String url) throws IOException
	{
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
			xlUtil.setCellData(sheetName, 1, 0, recipeDet.getRecipeId());
			xlUtil.setCellData(sheetName, 1, 1, recipeDet.getRecipeName());
			xlUtil.setCellData(sheetName, 1, 2, recipeDet.getRecipeTags());
			xlUtil.setCellData(sheetName, 1, 3, recipeDet.getRecipePrepTime());
			xlUtil.setCellData(sheetName, 1, 4, recipeDet.getRecipeCookTime());
			xlUtil.setCellData(sheetName, 1, 5, recipeDet.getTotalTime());
			xlUtil.setCellData(sheetName, 1, 6, recipeDet.getNoOfServings());
			xlUtil.setCellData(sheetName, 1, 7, recipeDet.getIngredientDetails());
			xlUtil.setCellData(sheetName, 1, 8, recipeDet.getRecipeDescription());
			xlUtil.setCellData(sheetName, 1, 9, url );
//			xlutil.setCellData("Sheet1", r, 10, RecipeURL );
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	

}
