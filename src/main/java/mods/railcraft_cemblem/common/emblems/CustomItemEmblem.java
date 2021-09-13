package mods.railcraft_cemblem.common.emblems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import mods.railcraft.client.emblems.Emblem;
import mods.railcraft.client.emblems.EmblemToolsClient;
import mods.railcraft.common.core.RailcraftConfig;
import mods.railcraft.common.items.ItemRailcraft;
import mods.railcraft.common.plugins.forge.RailcraftRegistry;
import mods.railcraft.common.util.inventory.InvTools;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumChatFormatting;

public class CustomItemEmblem extends ItemRailcraft {

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

    @SideOnly(value=Side.CLIENT)
    public void func_77624_a(ItemStack stack, EntityPlayer player, List info, boolean adv) {
        if (!stack.hasTagCompound()) return;
        NBTTagCompound nbt = stack.getTagCompound();
        NBTTagString emblemIdent = (NBTTagString)nbt.getTag("emblem");
        if (emblemIdent == null) {
            return;
        }
        Emblem emblem = EmblemToolsClient.packageManager.getEmblem(emblemIdent.func_150285_a_());
        if (emblem == null) return;
        info.add(EnumChatFormatting.GRAY + emblem.displayName);
    }

    public void func_94581_a(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("railcraft_cemblem:custom_emblem");
    }
}
