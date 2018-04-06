package org.prathmesh.recipe.converters;

import java.util.Optional;

import org.prathmesh.recipe.commands.IngredientCommand;
import org.prathmesh.recipe.domain.Ingredient;
import org.prathmesh.recipe.domain.Recipe;
import org.prathmesh.recipe.repositories.RecipeRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 6/21/17.
 */
@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;
    //private final RecipeRepository recipeRepository;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
        this.uomConverter = uomConverter;
        //this.recipeRepository=recipeRepository;
    }

    @Nullable
    @Override
    public Ingredient convert(IngredientCommand source) {
        if (source == null) {
            return null;
        }

        final Ingredient ingredient = new Ingredient();
        ingredient.setId(source.getId());
        //Optional<Recipe> recipeOptional = recipeRepository.findById(source.getRecipeId());
        //ingredient.setRecipe(recipeOptional.get());
        ingredient.setAmount(source.getAmount());
        ingredient.setDescription(source.getDescription());
        ingredient.setUom(uomConverter.convert(source.getUom()));
        return ingredient;
    }
}
