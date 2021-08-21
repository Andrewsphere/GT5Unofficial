package idealindustrial.tile.gui.base.component;

import net.minecraft.inventory.IInventory;

public interface II_SlotSupplier {
    /**
     * provides a slot with given arguments
     * @param inventory inventory to add slot
     * @param slotId slot id in this inventory
     * @param x x render coord
     * @param y y render coord
     * @param textureID local adding scope id, is used for adding groups of slots
     *                for example adding input slots, then output, usually used to determine texture
     * @return constructed slot
     */
    II_Slot provide(IInventory inventory, int slotId, int x, int y, int textureID);


}
