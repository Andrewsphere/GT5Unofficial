package idealindustrial.tile.module;

import idealindustrial.recipe.IMachineRecipe;
import idealindustrial.recipe.RecipeMap;
import idealindustrial.tile.impl.TileFacing2Main;
import idealindustrial.util.parameter.RecipedMachineStats;
import net.minecraft.nbt.NBTTagCompound;

public interface RecipeModule<R extends IMachineRecipe> extends MetaTileModule {


   void saveToNBT(String prefix, NBTTagCompound nbt);

   void loadFromNBT(String prefix, NBTTagCompound nbt);

   void onInInventoryModified(int id);

   short getProgress();

   RecipeMap<R> getRecipeMap();

   RecipeModule<R> reInit(TileFacing2Main<?> machine);

   RecipedMachineStats getMachineStats();
}
