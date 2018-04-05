package org.prathmesh.recipe.controllers;

import org.prathmesh.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IngredientController {
	
	private final RecipeService recipeService;
	
	public IngredientController(RecipeService recipeService){
		this.recipeService=recipeService;
	}
	
	@GetMapping
	@RequestMapping("recipe/{recipeId}/ingredients")
	public String listIngredient(@PathVariable String recipeId, Model model){
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));
		return "recipe/ingredient/list";
	}

}
