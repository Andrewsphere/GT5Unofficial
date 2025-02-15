package idealindustrial.impl.render;

import cpw.mods.fml.relauncher.SideOnly;
import idealindustrial.II_Values;
import idealindustrial.api.tile.render.IFastRenderedTileEntity;
import idealindustrial.impl.blocks.II_Blocks;
import idealindustrial.impl.blocks.base.BlockTile32kItem;
import idealindustrial.impl.blocks.base.Tile32kBlock;
import idealindustrial.impl.tile.Item_Machines;
import idealindustrial.api.tile.meta.Tile;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.opengl.GL11;

import static cpw.mods.fml.relauncher.Side.CLIENT;
import static idealindustrial.impl.render.GT_Renderer_Block.renderInventory;

@SideOnly(CLIENT)
public final class MachineItemRenderer implements IItemRenderer {
    public MachineItemRenderer() {
        MinecraftForgeClient.registerItemRenderer(Item_Machines.INSTANCE, this);
        BlockTile32kItem.instances.forEach(i -> MinecraftForgeClient.registerItemRenderer(i, this));
    }

    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type != ItemRenderType.FIRST_PERSON_MAP;
    }


    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return type == ItemRenderType.EQUIPPED || type == ItemRenderType.INVENTORY || type == ItemRenderType.ENTITY || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
    }

    public void renderItem(ItemRenderType type, ItemStack is, Object... data) {

        if (type == ItemRenderType.INVENTORY) {
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        } else if (type == ItemRenderType.ENTITY) {
            GL11.glTranslatef(-.5f, -.5f, -.5f);
            //  GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
        }

        IFastRenderedTileEntity tileEntity;
        Block block;
        if (is.getItem() instanceof Item_Machines) {
            int meta = Items.feather.getDamage(is);
            Tile<?> tile = II_Values.TILES[meta];
            tileEntity = tile == null ? null : tile.getHost();
            block = II_Blocks.INSTANCE.blockMachines;

        } else if (is.getItem() instanceof BlockTile32kItem) {
            Tile32kBlock<?> block32k = (Tile32kBlock<?>) ((BlockTile32kItem) is.getItem()).field_150939_a;
            tileEntity = block32k.getCachedTile();
            block = block32k;
        } else {
            return;
        }
        if (block == null || tileEntity == null) {
            return;
        }
        block.setBlockBoundsForItemRender();
        RenderBlocks renderer = RenderBlocks.getInstance();
        renderer.setRenderBoundsFromBlock(block);
        if (tileEntity.getCustomRenderer() != null) {
            tileEntity.getCustomRenderer().renderItem(type, is, II_Blocks.INSTANCE.blockMachines, RenderBlocks.getInstance(), Items.feather.getDamage(is));
        } else {
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            renderInventory(block, renderer, tileEntity.getTextures(is, (byte) 4, true, false, true));
            GL11.glEnable(GL11.GL_LIGHTING);
        }

    }
}