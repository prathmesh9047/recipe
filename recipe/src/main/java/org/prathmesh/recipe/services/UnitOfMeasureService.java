package org.prathmesh.recipe.services;

import java.util.Set;

import org.prathmesh.recipe.commands.UnitOfMeasureCommand;

public interface UnitOfMeasureService {
	
	Set<UnitOfMeasureCommand> listAllUoms();

}
