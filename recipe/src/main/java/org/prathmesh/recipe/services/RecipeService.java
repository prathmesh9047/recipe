package org.prathmesh.recipe.services;

import java.util.Set;

import org.prathmesh.recipe.domain.Recipe;


public interface RecipeService {
	
	Set<Recipe> getRecipes();
	Recipe findById(Long l);
}
