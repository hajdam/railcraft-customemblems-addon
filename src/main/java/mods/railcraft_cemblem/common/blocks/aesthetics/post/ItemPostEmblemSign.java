package mods.railcraft_cemblem.common.blocks.aesthetics.post;

import java.util.List;
import mods.railcraft.client.emblems.Emblem;
import mods.railcraft.client.emblems.EmblemToolsClient;
import mods.railcraft.common.blocks.aesthetics.post.BlockPostMetal;
import mods.railcraft.common.util.inventory.InvTools;
import mods.railcraft.common.util.misc.EnumColor;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

public class ItemPostEmblemSign extends ItemBlock {

    public static void setEmblem(ItemStack stack, String emblemIdentifier) {
        NBTTagCompound nbt = InvTools.getItemData(stack);
        nbt.setString("emblem", emblemIdentifier);
    }

    public static String getEmblem(ItemStack stack) {
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt == null || !nbt.hasKey("emblem")) {
            return "";
        }
        return nbt.getString("emblem");
    }

    public ItemPostEmblemSign(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
        setUnlocalizedName("railcraft_cemblem.post.emblem_sign");
    }

    @Override
    public IIcon getIconFromDamage(int damage) {
        return PostEmblemSign.getIcon();
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        EnumColor color = InvTools.getItemColor(stack);
        if (color != null && BlockPostMetal.textures != null) {
            return BlockPostMetal.textures[color.ordinal()];
        }
        return super.getIcon(stack, pass);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return PostEmblemSign.getTag();
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean adv) {
        if (stack.hasTagCompound()) {
            NBTTagCompound nbt = stack.getTagCompound();
            NBTTagString emblemIdent = (NBTTagString) nbt.getTag("emblem");

            if (emblemIdent == null || EmblemToolsClient.packageManager == null) {
                return;
            }

            Emblem emblem = EmblemToolsClient.packageManager.getEmblem(emblemIdent.func_150285_a_());
            if (emblem != null) {
                info.add(EnumChatFormatting.GRAY + emblem.displayName);
            }
        }
    }
}
