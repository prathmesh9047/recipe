package org.prathmesh.recipe.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.prathmesh.recipe.commands.IngredientCommand;
import org.prathmesh.recipe.commands.RecipeCommand;
import org.prathmesh.recipe.converters.IngredientCommandToIngredient;
import org.prathmesh.recipe.converters.IngredientToIngredientCommand;
import org.prathmesh.recipe.domain.Ingredient;
import org.prathmesh.recipe.domain.Recipe;
import org.prathmesh.recipe.repositories.RecipeRepository;
import org.prathmesh.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

@Service
public class IngredientServiceImpl implements IngredientService {
	
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	private final RecipeRepository recipeRepository;
	private final UnitOfMeasureRepository unitOfMeasureRepository;
	
	public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,IngredientCommandToIngredient ingredientCommandToIngredient, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository){
		this.ingredientToIngredientCommand=ingredientToIngredientCommand;
		this.ingredientCommandToIngredient=ingredientCommandToIngredient;
		this.recipeRepository=recipeRepository;
		this.unitOfMeasureRepository=unitOfMeasureRepository;
	}
	
	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId){
		Optional<Recipe> recipeOptional=recipeRepository.findById(recipeId);
		
		Recipe recipe = recipeOptional.get();
		
		Optional<IngredientCommand> ingredientCommandOptional =  recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId))
				.map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();
		
		return ingredientCommandOptional.get();
	}
	
	@Override
	@Transactional
	public IngredientCommand saveIngredientCommand(IngredientCommand command){
		Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());
		
		if(!recipeOptional.isPresent()){
			return new IngredientCommand();
		} else {
			Recipe recipe = recipeOptional.get();
			
			Optional<Ingredient> ingredientOptional = recipe
					.getIngredients()
					.stream()
					.filter(ingredient -> ingredient.getId().equals(command.getId()))
					.findFirst();
			
			if(ingredientOptional.isPresent()){
				Ingredient ingredientFound = ingredientOptional.get();
				ingredientFound.setDescription(command.getDescription());
				ingredientFound.setAmount(command.getAmount());
				ingredientFound.setUom(unitOfMeasureRepository
						.findById(command.getUom().getId())
						.orElseThrow(() -> new RuntimeException("UOM NOT FOUND")));
			} else {
				recipe.addIngredient(ingredientCommandToIngredient.convert(command));
			}
			
			Recipe savedRecipe = recipeRepository.save(recipe);
			
			return ingredientToIngredientCommand.convert(savedRecipe.getIngredients().stream()
					.filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
					.findFirst()
					.get());
		}
	}

}
