package idealindustrial.util.lang.materials;

import idealindustrial.impl.autogen.material.II_Material;
import idealindustrial.impl.autogen.material.Prefixes;
import idealindustrial.impl.autogen.material.submaterial.MatterState;

public abstract class BaseLocalizer implements MaterialLocalizer {


    @Override
    public String get(II_Material material, Prefixes prefix) {
        return String.format(getFormatFor(material, prefix), getNameFor(material, prefix));
    }

    @Override
    public String get(II_Material material, MatterState liquidState) {
        return  String.format(getFormatFor(material, liquidState), getNameFor(material, liquidState));
    }

    protected abstract String getFormatFor(II_Material material, MatterState state);

    protected abstract String getNameFor(II_Material material, MatterState state);

    protected abstract String getFormatFor(II_Material material, Prefixes prefix);

    protected abstract String getNameFor(II_Material material, Prefixes prefix);
}
