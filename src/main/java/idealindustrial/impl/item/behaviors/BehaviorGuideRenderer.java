package idealindustrial.impl.item.behaviors;

import cpw.mods.fml.common.FMLCommonHandler;
import idealindustrial.api.items.IItemBehavior;
import idealindustrial.impl.autogen.material.II_Materials;
import idealindustrial.impl.autogen.material.Prefixes;
import idealindustrial.impl.tile.impl.multi.MultiMachineBase;
import idealindustrial.api.tile.meta.Tile;
import idealindustrial.impl.tile.impl.multi.struct.IGuideRenderer;
import idealindustrial.impl.tile.impl.multi.struct.MultiMachineShape;
import idealindustrial.impl.blocks.ores.TileOres;
import idealindustrial.util.misc.II_TileUtil;
import idealindustrial.util.misc.II_Util;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BehaviorGuideRenderer implements IItemBehavior, IGuideRenderer {

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        II_Util.sendChatToPlayer(player, "Meta: " + world.getBlockMetadata(x,  y + 1, z));
        Tile<?> tile = II_TileUtil.getMetaTile(world, x, y, z);
        if (tile instanceof MultiMachineBase) {
            MultiMachineBase<?> multiMachine = (MultiMachineBase<?>) tile;
            MultiMachineShape shape = multiMachine.getShape();
            if (shape != null) {
                if (player.isSneaking() && player.capabilities.isCreativeMode) {
                    if (world.isRemote) {
                        return false;
                    }
                    shape.build(multiMachine);
                    return true;
                } else if (!player.isSneaking() && world.isRemote) {
                    shape.render(multiMachine, this);
                    return true;
                }
            }
        }
        else {
            if (FMLCommonHandler.instance().getEffectiveSide().isServer()) {
                int meta = TileOres.getMeta(II_Materials.iron, Prefixes.ore);
                TileOres.replaceBlock(world, x, y, z, meta);
            }
            return false;
        }
        return true;
    }

    @Override
    public void registerNewParticle(EntityFX particle) {

    }

    @Override
    public int getMaxAge() {
        return 100;
    }
}
