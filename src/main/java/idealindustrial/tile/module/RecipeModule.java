package idealindustrial.tile.module;

import idealindustrial.recipe.IMachineRecipe;
import idealindustrial.recipe.RecipeMap;
import idealindustrial.tile.meta.BaseMetaTile_Facing2Main;
import idealindustrial.util.parameter.RecipedMachineStats;
import net.minecraft.nbt.NBTTagCompound;

public interface RecipeModule<R extends IMachineRecipe> extends MetaTileModule {


   void saveToNBT(String prefix, NBTTagCompound nbt);

   void loadFromNBT(String prefix, NBTTagCompound nbt);

   void onInInventoryModified(int id);

   short getProgress();

   RecipeMap<R> getRecipeMap();

   RecipeModule<R> reInit(BaseMetaTile_Facing2Main<?> machine);

   RecipedMachineStats getMachineStats();
}
