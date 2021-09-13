package mods.railcraft_cemblem.common.emblems;

import mods.railcraft.common.core.RailcraftConfig;
import mods.railcraft.common.emblems.ItemEmblemBase;
import mods.railcraft.common.plugins.forge.RailcraftRegistry;
import mods.railcraft.common.util.inventory.InvTools;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class CustomItemEmblem extends ItemEmblemBase {

    private static final String TAG = "railcraft_cemblem.custom_emblem";
    public static CustomItemEmblem item;

    public static void registerItem() {
        if (item == null && RailcraftConfig.isItemEnabled("railcraft.emblem")) {
            item = new CustomItemEmblem();
            item.setUnlocalizedName(TAG);
            RailcraftRegistry.register((Item) item);
        }
    }

    public static ItemStack getEmblem(String identifier) {
        ItemStack stack = new ItemStack((Item) item);
        NBTTagCompound nbt = InvTools.getItemData((ItemStack) stack);
        nbt.setString("emblem", identifier);
        return stack;
    }

    public void func_94581_a(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("railcraft_cemblem:custom_emblem");
    }
}
