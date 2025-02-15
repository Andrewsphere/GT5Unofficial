package idealindustrial.impl.tile.impl.kinetic;

import idealindustrial.api.textures.ITexture;
import idealindustrial.impl.textures.RenderedTexture;
import idealindustrial.impl.textures.TextureManager;
import idealindustrial.impl.tile.IOType;
import idealindustrial.impl.tile.impl.TileFacing1Output;
import idealindustrial.api.tile.host.HostMachineTile;
import idealindustrial.api.tile.meta.Tile;
import idealindustrial.api.tile.energy.kinetic.KUConsumer;
import idealindustrial.api.tile.energy.kinetic.KUProducer;
import idealindustrial.api.tile.energy.kinetic.KineticEnergyHandler;
import idealindustrial.impl.tile.energy.kinetic.system.KineticSystem;
import idealindustrial.impl.tile.energy.kinetic.system.KineticSystemHandler;
import idealindustrial.util.misc.II_StreamUtil;
import idealindustrial.util.misc.II_TileUtil;

public class KUGeneratorBase extends TileFacing1Output<HostMachineTile> implements KineticEnergyHandler, KUProducer {
    KineticSystem system;

    public KUGeneratorBase(HostMachineTile hostTile, String name, ITexture[] baseTextures, ITexture[] overlays) {
        super(hostTile, name, baseTextures, overlays);
    }

    public KUGeneratorBase(HostMachineTile hostTile, TileFacing1Output<?> copyFrom) {
        super(hostTile, copyFrom);
    }

    public static KUGeneratorBase testMachine() {
        return new KUGeneratorBase(II_TileUtil.makeBaseMachineTile(), "Test Kinetic Generator",
                II_StreamUtil.repeated("test/cob2", 8).map(TextureManager.INSTANCE::blockTexture).map(RenderedTexture::new).toArray(ITexture[]::new),
                II_StreamUtil.setInNullAr(idealindustrial.impl.textures.Textures.input, new ITexture[8], 3, 7)
                );
    }

    @Override
    public void onPostTick(long timer, boolean serverSide) {
        if (serverSide && system == null) {
            KineticSystemHandler.initSystem(hostTile, outputFacing);
        }
    }

    @Override
    protected IOType getOutputIOType() {
        return IOType.Kinetic;
    }


    @Override
    public boolean hasKineticEnergy() {
        return true;
    }

    @Override
    public boolean getIOatSide(int side, IOType type, boolean input) {
        return type == IOType.Kinetic && side == outputFacing && !input;
    }

    @Override
    public Tile<HostMachineTile> newMetaTile(HostMachineTile baseTile) {
        return new KUGeneratorBase(baseTile, this);
    }

    @Override
    public int getSpeed(int powerRequest) {
        return 10;
    }

    @Override
    public int getTotalPower() {
        return 20;
    }

    @Override
    public void onAddedToSystem(KineticSystem system) {

    }

    @Override
    public void onConnectionAppended() {
        if (system != null) {
            system.invalidate();
        }
    }

    @Override
    public void setSystem(KineticSystem system) {
        this.system = system;
    }

    @Override
    public KUConsumer getConsumer(int side) {
        return null;
    }

    @Override
    public KUProducer getProducer(int side) {
       if (side == outputFacing) {
           return this;
       }
       return null;
    }


    @Override
    public KineticEnergyHandler getKineticHandler() {
        return this;
    }
}
