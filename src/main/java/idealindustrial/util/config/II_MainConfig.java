package idealindustrial.util.config;

import idealindustrial.api.reflection.Config;
import idealindustrial.impl.reflection.config.ReflectionConfig;

public class II_MainConfig extends ReflectionConfig {

    @Config(configComment = "How much time multies sleep before struct check after server starting")
    public static int multiMachineStartup = 50;
}
