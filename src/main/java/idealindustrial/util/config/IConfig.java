package idealindustrial.util.config;

import net.minecraftforge.common.config.Configuration;

public interface IConfig {
    /**
     * loads everything, gets invoked via reflection from {@link gregtech.loaders.preload.GT_LoaderConfig}
     * @param configuration is cfg instance that is created from file with name {@link #getName()}
     */
    void load(Configuration configuration);

    /**
     * @return name of configuration file
     */
    String getName();



}
