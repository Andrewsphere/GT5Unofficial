package idealindustrial.util.json;

import com.google.gson.*;
import cpw.mods.fml.common.registry.GameRegistry;
import idealindustrial.impl.autogen.material.II_Material;
import idealindustrial.impl.autogen.material.II_Materials;
import idealindustrial.impl.autogen.material.Prefixes;
import idealindustrial.impl.oredict.OreDict;
import idealindustrial.impl.oredict.OreInfo;
import idealindustrial.impl.item.stack.CheckType;
import idealindustrial.impl.item.stack.II_StackSignature;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.lang.reflect.Type;

public class JsonStackSignatureSerializer implements JsonSerializer<II_StackSignature>, JsonDeserializer<II_StackSignature> {

    @Override
    public II_StackSignature deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        int amount = object.get("amount").getAsInt();
        if (object.has("material")) {
            II_Material material = II_Materials.materialForName(object.get("material").getAsString());
            Prefixes prefix = prefixForName(object.get("prefix").getAsString());
            return new II_StackSignature(OreDict.get(prefix, material), amount);
        }
        if (object.has("ore")) {
            return new II_StackSignature(OreDict.get(object.get("ore").getAsString()), amount);
        }
        NBTTagCompound nbtTag = null;
        if (object.has("nbt")) {
            nbtTag = context.deserialize(object.get("nbt"), NBTTagCompound.class);
        }
        String mod = object.get("mod").getAsString();
        String name = object.get("name").getAsString();
        int meta = object.get("damage").getAsInt();
        Item item = GameRegistry.findItem(mod, name);
        if (nbtTag == null) {
            return new II_StackSignature(item, meta, amount);
        }
        else  {
            ItemStack is = new ItemStack(item, meta);
            is.stackSize = amount;
            is.setTagCompound(nbtTag);
            return new II_StackSignature(is, CheckType.DIRECT);
        }
    }




    private Prefixes prefixForName(String name) {
        return Prefixes.valueOf(name);
    }

    @Override
    public JsonElement serialize(II_StackSignature src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        switch (src.getType()) {
            case DIRECT:
                object.addProperty("mod", src.getUniqueID().modId);
                object.addProperty("name", src.getUniqueID().name);
                object.addProperty("damage", src.getDamageValue());
                if (src.getTagCompound() != null) {
                    object.add("nbt", context.serialize(src.getTagCompound().getNBTTagCompoundCopy()));
                }
                break;
            case DAMAGE:
                object.addProperty("mod", src.getUniqueID().modId);
                object.addProperty("name", src.getUniqueID().name);
                object.addProperty("damage", src.getDamageValue());
                break;
            case OREDICT:
                OreInfo info = src.getOreInfo();
                object.addProperty("ore", info.getName());
                break;
        }
        object.addProperty("amount", src.amount);
        return object;
    }
}
