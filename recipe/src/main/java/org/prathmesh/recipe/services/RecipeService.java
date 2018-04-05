package org.prathmesh.recipe.services;

import java.util.Set;

import org.prathmesh.recipe.commands.RecipeCommand;
import org.prathmesh.recipe.domain.Recipe;


public interface RecipeService {
	
	Set<Recipe> getRecipes();
	Recipe findById(Long l);
	RecipeCommand saveRecipeCommand(RecipeCommand command);
	RecipeCommand findCommandById(Long l);
	void deleteById(Long idToDelete);
}
