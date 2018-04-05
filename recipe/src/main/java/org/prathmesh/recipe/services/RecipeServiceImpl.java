package org.prathmesh.recipe.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.prathmesh.recipe.commands.RecipeCommand;
import org.prathmesh.recipe.converters.RecipeCommandToRecipe;
import org.prathmesh.recipe.converters.RecipeToRecipeCommand;
import org.prathmesh.recipe.domain.Recipe;
import org.prathmesh.recipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService {

	private final RecipeRepository recipeRepository;
	private final RecipeCommandToRecipe recipeCommandToRecipe;
	private final RecipeToRecipeCommand recipeToRecipeCommand;

	public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
		this.recipeRepository = recipeRepository;
		this.recipeCommandToRecipe=recipeCommandToRecipe;
		this.recipeToRecipeCommand=recipeToRecipeCommand;
	}

	@Override
	public Set<Recipe> getRecipes() {
		Set<Recipe> recipeSet = new HashSet<>();
		recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
		return recipeSet;
	}

	@Override
	public Recipe findById(Long l) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(l);
		
		if(!recipeOptional.isPresent()){
			throw new RuntimeException("Recipe Not found");
		}
		return recipeOptional.get();
	}
	
	@Override
	@Transactional
	public RecipeCommand findCommandById(Long l){
		return recipeToRecipeCommand.convert(findById(l));
	}
	
	@Override
	@Transactional
	public RecipeCommand saveRecipeCommand(RecipeCommand command) {
		Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
		
		Recipe savedRecipe = recipeRepository.save(detachedRecipe);
		return recipeToRecipeCommand.convert(savedRecipe);
	}
	
	@Override
	public void deleteById(Long idToDelete){
		recipeRepository.deleteById(idToDelete);
	}
	
	
}
