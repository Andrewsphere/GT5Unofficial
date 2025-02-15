package idealindustrial.api.recipe;

import idealindustrial.impl.recipe.MachineEnergyParams;
import idealindustrial.impl.recipe.RecipeMapStorage;
import idealindustrial.api.tile.fluid.FluidHandler;
import idealindustrial.api.tile.inventory.RecipedInventory;
import idealindustrial.impl.item.stack.II_StackSignature;

import java.util.List;
import java.util.Set;

public interface RecipeMap<R extends IMachineRecipe> {

    R findRecipe(RecipedInventory inventory, FluidHandler fluidInputs, MachineEnergyParams params);

    boolean addRecipe(R recipe);

    List<R> getAllRecipes();

    void optimize();

    String getName();

    IRecipeGuiParams getGuiParams();

    Set<R> getCraftingRecipes(II_StackSignature signature);

    Set<R> getUsageRecipes(II_StackSignature signature);

    RecipeMapStorage<R> getJsonReflection();

    RecipeMap<R> newEmpty();

//    boolean isInputEqualStacks(R recipe, II_RecipedInventory inventory, II_FluidHandler fluidInputs, boolean doConsume);

    //todo reinit call on IDs reFuck
}
