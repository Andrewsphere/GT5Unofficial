package idealindustrial.impl.tile.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import idealindustrial.api.textures.ITexture;
import idealindustrial.api.tile.render.CustomRenderer;
import idealindustrial.impl.tile.host.HostMachineTileImpl;
import idealindustrial.api.tile.host.HostMachineTile;
import idealindustrial.api.tile.host.HostTile;
import idealindustrial.api.tile.meta.Tile;
import idealindustrial.impl.tile.energy.electric.EnergyHandler;
import idealindustrial.api.tile.fluid.FluidHandler;
import idealindustrial.api.tile.inventory.InternalInventory;
import idealindustrial.util.misc.II_DirUtil;
import idealindustrial.util.misc.II_Util;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * base class for cuboid machines, has IO for Items, Fluids and Energies(tbi)
 * also has very simple textures
 * you should init IO handlers
 */
public abstract class TileMachineBase<H extends HostMachineTile> implements Tile<H> {

    public boolean hasInventory, hasTank, hasEnergy;
    public InternalInventory inventoryIn, inventoryOut, inventorySpecial;
    public FluidHandler tankIn, tankOut;
    public EnergyHandler energyHandler;
    public String name;
    public H hostTile;
    /**
     * texture arrays, 0 - down, 1 - top, 2 - side, 3 - down active, 5 - top active...
     */
    protected ITexture[] baseTextures, overlays;

    public TileMachineBase(H hostTile, String name, ITexture[] baseTextures, ITexture[] overlays) {
        assert baseTextures != null && overlays != null;
        this.name = name;
        this.hostTile = hostTile;
        this.baseTextures = baseTextures;
        this.overlays = overlays;
    }

    protected TileMachineBase(H hostTile, TileMachineBase<?> copyFrom) {
        this.hostTile = hostTile;

        this.name = copyFrom.name;
        this.baseTextures = copyFrom.baseTextures;
        this.overlays = copyFrom.overlays;
    }



    @Override
    public boolean hasInventory() {
        return hasInventory;
    }

    @Override
    public InternalInventory getInputsHandler() {
        return inventoryIn;
    }

    @Override
    public InternalInventory getOutputsHandler() {
        return inventoryOut;
    }

    @Override
    public InternalInventory getSpecialHandler() {
        return inventorySpecial;
    }

    @Override
    public boolean hasFluidTank() {
        return hasTank;
    }

    @Override
    public FluidHandler getInputTank() {
        return tankIn;
    }

    @Override
    public FluidHandler getOutputTank() {
        return tankOut;
    }

    @Override
    public void onOpenGui() {

    }

    @Override
    public void onCloseGui() {

    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isAccessAllowed(EntityPlayer aPlayer) {
        return true;
    }

    @Override
    public ITexture[] provideTexture(boolean active, int side) {
        int index = II_DirUtil.directionToSide(side) + (active ? 0 : 3);
        return Stream.of(baseTextures[index], overlays[index]).filter(Objects::nonNull).toArray(ITexture[]::new);
    }

    @Override
    public H getHost() {
        return hostTile;
    }

    @Override
    public void onFirstTick(long timer, boolean serverSide) {

    }

    @Override
    public void onPreTick(long timer, boolean serverSide) {

    }

    @Override
    public void onTick(long timer, boolean serverSide) {

    }

    @Override
    public void onPostTick(long timer, boolean serverSide) {

    }

    @Override
    public void writeTile(ByteArrayDataOutput stream) {

    }

    @Override
    public void readTile(ByteArrayDataInput stream) {

    }

    @Override
    public boolean onSoftHammerClick(EntityPlayer player, ItemStack item, int side) {
        if (hostTile.isServerSide()) {
            getHost().setAllowedToWork(!getHost().isAllowedToWork());
            II_Util.sendChatToPlayer(player, "Processing " + (hostTile.isAllowedToWork() ? "Enabled" : "Disabled"));
        }
        return true;
    }

    @Override
    public boolean onWrenchClick(EntityPlayer player, ItemStack item, int side, float hitX, float hitY, float hitZ) {
        return false;
    }

    @Override
    public boolean onScrewdriverClick(EntityPlayer player, ItemStack item, int side) {
        return false;
    }

    @Override
    public boolean onCrowbarClick(EntityPlayer player, ItemStack item, int side) {
        return false;
    }

    @Override
    public boolean onRightClick(EntityPlayer player, ItemStack item, int side, float hitX, float hitY, float hitZ) {
        return false;
    }

    @Override
    public boolean onLeftClick(EntityPlayer player, ItemStack item, int side, float hitX, float hitY, float hitZ) {
        return false;
    }

    @Override
    public boolean receiveClientEvent(int id, int value) {
        return false;
    }

    @Override
    public IInventory getFluidIORepresentation() {
        return null;
    }

    @Override
    public boolean hasRenderer() {
        return false;
    }

    @Override
    public CustomRenderer getRenderer() {
        return null;
    }

    @Override
    public boolean cacheCoverTexturesSeparately() {
        return false;
    }

    @Override
    public GuiContainer getClientGUI(EntityPlayer player, int internalID) {
        return null;
    }

    @Override
    public Container getServerGUI(EntityPlayer player, int internalID) {
        return null;
    }

    @Override
    public void onBlockChange() {

    }

    @Override
    public void onPlaced() {

    }

    @Override
    public void onRemoval() {

    }

    @Override
    public NBTTagCompound saveToNBT(NBTTagCompound nbt) {
        return nbt;
    }

    @Override
    public void loadFromNBT(NBTTagCompound nbt) {

    }

    @Override
    public NBTTagCompound saveNBTtoDrop(NBTTagCompound nbt) {
        return nbt;
    }

    @Override
    public Class<? extends HostTile> getBaseTileClass() {
        return HostMachineTileImpl.class;
    }

    @Override
    public boolean hasEnergy() {
        return hasEnergy;
    }

    @Override
    public EnergyHandler getEnergyHandler() {
        return energyHandler;
    }
}
