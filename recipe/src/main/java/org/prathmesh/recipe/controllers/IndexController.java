package org.prathmesh.recipe.controllers;

import java.util.Optional;

import org.prathmesh.recipe.domain.Category;
import org.prathmesh.recipe.domain.UnitOfMeasure;
import org.prathmesh.recipe.repositories.CategoryRepository;
import org.prathmesh.recipe.repositories.UnitOfMeasureRepository;
import org.prathmesh.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	private final RecipeService recipeService;

	public IndexController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}



	@RequestMapping({"","/","/index"})
	public String getIndexPage(Model model){
		
		model.addAttribute("recipes", recipeService.getRecipes());
		return "index";
	}

}
