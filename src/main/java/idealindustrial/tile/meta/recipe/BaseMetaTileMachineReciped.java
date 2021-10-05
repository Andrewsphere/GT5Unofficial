package idealindustrial.tile.meta.recipe;

import gregtech.api.interfaces.ITexture;
import idealindustrial.recipe.IMachineRecipe;
import idealindustrial.recipe.RecipeMap;
import idealindustrial.tile.gui.RecipedContainer;
import idealindustrial.tile.gui.RecipedGuiContainer;
import idealindustrial.tile.interfaces.base.BaseMachineTile;
import idealindustrial.tile.meta.BaseMetaTile_Facing2Main;
import idealindustrial.tile.module.BasicRecipeModule;
import idealindustrial.tile.module.RecipeModule;
import idealindustrial.util.misc.II_Paths;
import idealindustrial.util.parameter.RecipedMachineStats;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class BaseMetaTileMachineReciped<BaseTileType extends BaseMachineTile, RecipeType extends IMachineRecipe> extends BaseMetaTile_Facing2Main<BaseTileType> {
    RecipeModule<RecipeType> module;

    public BaseMetaTileMachineReciped(BaseTileType baseTile, String name, ITexture[] baseTextures, ITexture[] overlays, RecipeMap<RecipeType> recipeMap, RecipedMachineStats stats) {
        super(baseTile, name, baseTextures, overlays);
        module = new BasicRecipeModule<RecipeType>(this, stats, recipeMap);
    }

    public BaseMetaTileMachineReciped(BaseTileType baseTile, BaseMetaTileMachineReciped<BaseTileType, RecipeType> copyFrom) {
        super(baseTile, copyFrom);
        module = copyFrom.module.reInit(this);
    }

    @Override
    public void onPostTick(long timer, boolean serverSide) {
        module.onPostTick(timer, serverSide);
    }

    @Override
    public RecipedContainer getServerGUI(EntityPlayer player, int internalID) {
        return new RecipedContainer(baseTile, player, module.getRecipeMap().getGuiParams());
    }

    @Override
    public GuiContainer getClientGUI(EntityPlayer player, int internalID) {
        return new RecipedGuiContainer(getServerGUI(player, internalID), II_Paths.PATH_GUI + "BasicGui.png");
    }

    @Override
    public void onInInventoryModified(int id) {
        module.onInInventoryModified(id);
    }

    @Override
    public boolean onSoftHammerClick(EntityPlayer player, ItemStack item, int side) {
        super.onSoftHammerClick(player, item, side);
        if (baseTile.isAllowedToWork()) {
            module.onInInventoryModified(0);
        }
        return true;
    }

    /**
     * value that represents the machine progress clamped between 0 and 20
     * used to render progress arrow on client
     *
     * @return progress representation
     */
    public short getProgress() {
        return module.getProgress();
    }

    @Override
    public NBTTagCompound saveToNBT(NBTTagCompound nbt) {
        module.saveToNBT("R", nbt);
        return super.saveToNBT(nbt);
    }

    @Override
    public void loadFromNBT(NBTTagCompound nbt) {
        super.loadFromNBT(nbt);
        module.loadFromNBT("R", nbt);
    }

}
