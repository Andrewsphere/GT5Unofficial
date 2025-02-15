package gregtech.api.metatileentity;

import com.google.common.io.ByteArrayDataInput;
import gregtech.api.interfaces.tileentity.IHasWorldObjectAndCoords;
import gregtech.api.net.GT_Packet_Block_Event;
import idealindustrial.impl.net.Network;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.Arrays;


/**
 * The Functions my old TileEntities and my BaseMetaTileEntities have in common.
 * <p/>
 * Basically everything a TileEntity should have.
 */
public abstract class BaseTileEntity extends TileEntity implements IHasWorldObjectAndCoords {
    /**
     * Buffers adjacent TileEntities for faster access
     * <p/>
     * "this" means that there is no TileEntity, while "null" means that it doesn't know if there is even a TileEntity and still needs to check that if needed.
     */
    private final TileEntity[] mBufferedTileEntities = new TileEntity[6];
    /**
     * If this TileEntity checks for the Chunk to be loaded before returning World based values.
     * The AdvPump hacks this to false to ensure everything runs properly even when far Chunks are not actively loaded.
     * But anything else should not cause worfin' Chunks, uhh I mean orphan Chunks.
     */
    public boolean ignoreUnloadedChunks = true;
    /**
     * This Variable checks if this TileEntity is dead, because Minecraft is too stupid to have proper TileEntity unloading.
     */
    public boolean isDead = false;

    private void clearNullMarkersFromTileEntityBuffer() {
        for (int i = 0; i < mBufferedTileEntities.length; i++)
            if (mBufferedTileEntities[i] == this) mBufferedTileEntities[i] = null;
    }

    /**
     * Called automatically when the Coordinates of this TileEntity have been changed
     */
    protected final void clearTileEntityBuffer() {
        Arrays.fill(mBufferedTileEntities, null);
    }

    @Override
    public final World getWorld() {
        return worldObj;
    }

    @Override
    public final int getXCoord() {
        return xCoord;
    }

    @Override
    public final short getYCoord() {
        return (short) yCoord;
    }

    @Override
    public final int getZCoord() {
        return zCoord;
    }

    @Override
    public final int getOffsetX(int aSide, int aMultiplier) {
        return xCoord + ForgeDirection.getOrientation(aSide).offsetX * aMultiplier;
    }

    @Override
    public final short getOffsetY(int aSide, int aMultiplier) {
        return (short) (yCoord + ForgeDirection.getOrientation(aSide).offsetY * aMultiplier);
    }

    @Override
    public final int getOffsetZ(int aSide, int aMultiplier) {
        return zCoord + ForgeDirection.getOrientation(aSide).offsetZ * aMultiplier;
    }

    @Override
    public final boolean isServerSide() {
        return !worldObj.isRemote;
    }

    @Override
    public final boolean isClientSide() {
        return worldObj.isRemote;
    }

    @Override
    public final boolean openGUI(EntityPlayer aPlayer) {
        return openGUI(aPlayer, 0);
    }


    @Override
    public final int getRandomNumber(int aRange) {
        return worldObj.rand.nextInt(aRange);
    }

    @Override
    public final BiomeGenBase getBiome(int aX, int aZ) {
        return worldObj.getBiomeGenForCoords(aX, aZ);
    }

    @Override
    public final BiomeGenBase getBiome() {
        return getBiome(xCoord, zCoord);
    }

    @Override
    public final Block getBlockOffset(int aX, int aY, int aZ) {
        return getBlock(xCoord + aX, yCoord + aY, zCoord + aZ);
    }

    @Override
    public final Block getBlockAtSide(byte aSide) {
        return getBlockAtSideAndDistance(aSide, 1);
    }

    @Override
    public final Block getBlockAtSideAndDistance(byte aSide, int aDistance) {
        return getBlock(getOffsetX(aSide, aDistance), getOffsetY(aSide, aDistance), getOffsetZ(aSide, aDistance));
    }

    @Override
    public final byte getMetaIDOffset(int aX, int aY, int aZ) {
        return getMetaID(xCoord + aX, yCoord + aY, zCoord + aZ);
    }

    @Override
    public final byte getMetaIDAtSide(byte aSide) {
        return getMetaIDAtSideAndDistance(aSide, 1);
    }

    @Override
    public final byte getMetaIDAtSideAndDistance(byte aSide, int aDistance) {
        return getMetaID(getOffsetX(aSide, aDistance), getOffsetY(aSide, aDistance), getOffsetZ(aSide, aDistance));
    }

    @Override
    public final byte getLightLevelOffset(int aX, int aY, int aZ) {
        return getLightLevel(xCoord + aX, yCoord + aY, zCoord + aZ);
    }

    @Override
    public final byte getLightLevelAtSide(byte aSide) {
        return getLightLevelAtSideAndDistance(aSide, 1);
    }

    @Override
    public final byte getLightLevelAtSideAndDistance(byte aSide, int aDistance) {
        return getLightLevel(getOffsetX(aSide, aDistance), getOffsetY(aSide, aDistance), getOffsetZ(aSide, aDistance));
    }

    @Override
    public final boolean getOpacityOffset(int aX, int aY, int aZ) {
        return getOpacity(xCoord + aX, yCoord + aY, zCoord + aZ);
    }

    @Override
    public final boolean getOpacityAtSide(byte aSide) {
        return getOpacityAtSideAndDistance(aSide, 1);
    }

    @Override
    public final boolean getOpacityAtSideAndDistance(byte aSide, int aDistance) {
        return getOpacity(getOffsetX(aSide, aDistance), getOffsetY(aSide, aDistance), getOffsetZ(aSide, aDistance));
    }

    @Override
    public final boolean getSkyOffset(int aX, int aY, int aZ) {
        return getSky(xCoord + aX, yCoord + aY, zCoord + aZ);
    }

    @Override
    public final boolean getSkyAtSide(byte aSide) {
        return getSkyAtSideAndDistance(aSide, 1);
    }

    @Override
    public final boolean getSkyAtSideAndDistance(byte aSide, int aDistance) {
        return getSky(getOffsetX(aSide, aDistance), getOffsetY(aSide, aDistance), getOffsetZ(aSide, aDistance));
    }

    @Override
    public final boolean getAirOffset(int aX, int aY, int aZ) {
        return getAir(xCoord + aX, yCoord + aY, zCoord + aZ);
    }

    @Override
    public final boolean getAirAtSide(byte aSide) {
        return getAirAtSideAndDistance(aSide, 1);
    }

    @Override
    public final boolean getAirAtSideAndDistance(byte aSide, int aDistance) {
        return getAir(getOffsetX(aSide, aDistance), getOffsetY(aSide, aDistance), getOffsetZ(aSide, aDistance));
    }

    @Override
    public final TileEntity getTileEntityOffset(int aX, int aY, int aZ) {
        return getTileEntity(xCoord + aX, yCoord + aY, zCoord + aZ);
    }

    @Override
    public final TileEntity getTileEntityAtSideAndDistance(byte aSide, int aDistance) {
        if (aDistance == 1) return getTileEntityAtSide(aSide);
        return getTileEntity(getOffsetX(aSide, aDistance), getOffsetY(aSide, aDistance), getOffsetZ(aSide, aDistance));
    }

    @Override
    public final IInventory getIInventory(int aX, int aY, int aZ) {
        TileEntity tTileEntity = getTileEntity(aX, aY, aZ);
        if (tTileEntity instanceof IInventory) return (IInventory) tTileEntity;
        return null;
    }

    @Override
    public final IInventory getIInventoryOffset(int aX, int aY, int aZ) {
        TileEntity tTileEntity = getTileEntityOffset(aX, aY, aZ);
        if (tTileEntity instanceof IInventory) return (IInventory) tTileEntity;
        return null;
    }

    @Override
    public final IInventory getIInventoryAtSide(byte aSide) {
        TileEntity tTileEntity = getTileEntityAtSide(aSide);
        if (tTileEntity instanceof IInventory) return (IInventory) tTileEntity;
        return null;
    }

    @Override
    public final IInventory getIInventoryAtSideAndDistance(byte aSide, int aDistance) {
        TileEntity tTileEntity = getTileEntityAtSideAndDistance(aSide, aDistance);
        if (tTileEntity instanceof IInventory) return (IInventory) tTileEntity;
        return null;
    }

    @Override
    public final IFluidHandler getITankContainer(int aX, int aY, int aZ) {
        TileEntity tTileEntity = getTileEntity(aX, aY, aZ);
        if (tTileEntity instanceof IFluidHandler) return (IFluidHandler) tTileEntity;
        return null;
    }

    @Override
    public final IFluidHandler getITankContainerOffset(int aX, int aY, int aZ) {
        TileEntity tTileEntity = getTileEntityOffset(aX, aY, aZ);
        if (tTileEntity instanceof IFluidHandler) return (IFluidHandler) tTileEntity;
        return null;
    }

    @Override
    public final IFluidHandler getITankContainerAtSide(byte aSide) {
        TileEntity tTileEntity = getTileEntityAtSide(aSide);
        if (tTileEntity instanceof IFluidHandler) return (IFluidHandler) tTileEntity;
        return null;
    }

    @Override
    public final IFluidHandler getITankContainerAtSideAndDistance(byte aSide, int aDistance) {
        TileEntity tTileEntity = getTileEntityAtSideAndDistance(aSide, aDistance);
        if (tTileEntity instanceof IFluidHandler) return (IFluidHandler) tTileEntity;
        return null;
    }

    @Override
    public final Block getBlock(int aX, int aY, int aZ) {
        if (ignoreUnloadedChunks && crossedChunkBorder(aX, aZ) && !worldObj.blockExists(aX, aY, aZ)) return Blocks.air;
        return worldObj.getBlock(aX, aY, aZ);
    }

    @Override
    public final byte getMetaID(int aX, int aY, int aZ) {
        if (ignoreUnloadedChunks && crossedChunkBorder(aX, aZ) && !worldObj.blockExists(aX, aY, aZ)) return 0;
        return (byte) worldObj.getBlockMetadata(aX, aY, aZ);
    }

    @Override
    public final byte getLightLevel(int aX, int aY, int aZ) {
        if (ignoreUnloadedChunks && crossedChunkBorder(aX, aZ) && !worldObj.blockExists(aX, aY, aZ)) return 0;
        return (byte) (worldObj.getLightBrightness(aX, aY, aZ) * 15);
    }

    @Override
    public final boolean getSky(int aX, int aY, int aZ) {
        if (ignoreUnloadedChunks && crossedChunkBorder(aX, aZ) && !worldObj.blockExists(aX, aY, aZ)) return true;
        return worldObj.canBlockSeeTheSky(aX, aY, aZ);
    }

    @Override
    public final boolean getOpacity(int aX, int aY, int aZ) {
        if (ignoreUnloadedChunks && crossedChunkBorder(aX, aZ) && !worldObj.blockExists(aX, aY, aZ)) return false;
        Block block = getBlock(aX, aY, aZ);
        return block.isOpaqueCube();
    }

    @Override
    public final boolean getAir(int aX, int aY, int aZ) {
        if (ignoreUnloadedChunks && crossedChunkBorder(aX, aZ) && !worldObj.blockExists(aX, aY, aZ)) return true;
        return getBlock(aX, aY, aZ).isAir(worldObj, aX, aY, aZ);
    }

    @Override
    public final TileEntity getTileEntity(int aX, int aY, int aZ) {
        if (ignoreUnloadedChunks && crossedChunkBorder(aX, aZ) && !worldObj.blockExists(aX, aY, aZ)) return null;
        return worldObj.getTileEntity(aX, aY, aZ);
    }

    @Override
    public final TileEntity getTileEntityAtSide(int aSide) {
        if (aSide < 0 || aSide >= 6 || mBufferedTileEntities[aSide] == this) return null;
        int tX = getOffsetX(aSide, 1), tY = getOffsetY(aSide, 1), tZ = getOffsetZ(aSide, 1);
        if (crossedChunkBorder(tX, tZ)) {
            mBufferedTileEntities[aSide] = null;
            if (ignoreUnloadedChunks && !worldObj.blockExists(tX, tY, tZ)) return null;
        }
        if (mBufferedTileEntities[aSide] == null) {
            mBufferedTileEntities[aSide] = worldObj.getTileEntity(tX, tY, tZ);
            if (mBufferedTileEntities[aSide] == null) {
                mBufferedTileEntities[aSide] = this;
                return null;
            }
            return mBufferedTileEntities[aSide];
        }
        if (mBufferedTileEntities[aSide].isInvalid()) {
            mBufferedTileEntities[aSide] = null;
            return getTileEntityAtSide(aSide);
        }
        if (mBufferedTileEntities[aSide].xCoord == tX && mBufferedTileEntities[aSide].yCoord == tY && mBufferedTileEntities[aSide].zCoord == tZ) {
            return mBufferedTileEntities[aSide];
        }
        return null;
    }

    @Override
    public void writeToNBT(NBTTagCompound aNBT) {
        super.writeToNBT(aNBT);
        //isDead = true;
    }

    @Override
    public boolean isDead() {
        return isDead || isInvalidTileEntity();
    }

    @Override
    public void validate() {
        clearNullMarkersFromTileEntityBuffer();
        super.validate();
    }

    @Override
    public void invalidate() {
        clearNullMarkersFromTileEntityBuffer();
        super.invalidate();
    }

    @Override
    public void onChunkUnload() {
        clearNullMarkersFromTileEntityBuffer();
        super.onChunkUnload();
        isDead = true;
    }

    @Override
    public void updateEntity() {
        // Well if the TileEntity gets ticked it is alive.
        isDead = false;
    }

    //wtf? for what reason it was final...
    public void onAdjacentBlockChange(int aX, int aY, int aZ) {
        clearNullMarkersFromTileEntityBuffer();
    }

    public void updateNeighbours(int mStrongRedstone, int oStrongRedstone) {
        Block thisBlock = getBlockOffset(0, 0, 0);
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            int x1 = xCoord + dir.offsetX, y1 = yCoord + dir.offsetY, z1 = zCoord + dir.offsetZ;

            if (worldObj.blockExists(x1, y1, z1)) {
                worldObj.notifyBlockOfNeighborChange(x1, y1, z1, thisBlock);

                //update if it was / is strong powered.
                if (((((mStrongRedstone | oStrongRedstone) >>> dir.ordinal()) & 1) != 0 ) && getBlock(x1, y1, z1).isNormalCube()) {
                    int skipUpdateSide = dir.getOpposite().ordinal(); //Don't update this block. Still updates diagonal blocks twice if conditions meet.

                    for (ForgeDirection dir2 : ForgeDirection.VALID_DIRECTIONS) {
                        int x2 = x1 + dir2.offsetX, y2 = y1 + dir2.offsetY, z2 = z1 + dir2.offsetZ;
                        if (dir2.ordinal() != skipUpdateSide && worldObj.blockExists(x2, y2, z2))
                            worldObj.notifyBlockOfNeighborChange(x2, y2, z2, thisBlock);
                    }
                }
            }
        }
    }

    @Override
    public final void sendBlockEvent(byte aID, byte aValue) {
        Network.NW.sendPacketToAllPlayersInRange(worldObj, new GT_Packet_Block_Event(xCoord, (short) yCoord, zCoord, aID, aValue), xCoord, zCoord);
    }

    private boolean crossedChunkBorder(int aX, int aZ) {
        return aX >> 4 != xCoord >> 4 || aZ >> 4 != zCoord >> 4;
    }

    public final void setToFire() {
        worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.fire);
    }
    
    @Override 
    public void markDirty() {/* Do not do the super Function */} 


    public void receiveStream(ByteArrayDataInput stream) {

    }
}