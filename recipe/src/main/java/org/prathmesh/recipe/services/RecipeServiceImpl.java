package org.prathmesh.recipe.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.prathmesh.recipe.domain.Recipe;
import org.prathmesh.recipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService {

	private final RecipeRepository recipeRepository;

	public RecipeServiceImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
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
	
	
	
}
