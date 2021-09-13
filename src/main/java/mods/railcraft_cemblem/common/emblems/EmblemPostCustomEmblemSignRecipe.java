package mods.railcraft_cemblem.common.emblems;

import mods.railcraft.common.blocks.aesthetics.post.BlockPostMetal;
import mods.railcraft.common.blocks.aesthetics.post.EnumPost;
import mods.railcraft.common.blocks.aesthetics.post.ItemPost;
import mods.railcraft.common.emblems.EmblemToolsServer;
import mods.railcraft.common.util.inventory.InvTools;
import mods.railcraft.common.util.misc.EnumColor;
import mods.railcraft_cemblem.common.blocks.aesthetics.post.PostEmblemSign;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class EmblemPostCustomEmblemSignRecipe implements IRecipe {

    private ItemStack postTemplate;
    private ItemStack emblemPostTemplate;
    private ItemStack glassPaneTemplate;

    public ItemStack getEmblemPost() {
        if (this.emblemPostTemplate == null) {
            this.emblemPostTemplate = PostEmblemSign.getItem();
        }

        return this.emblemPostTemplate;
    }

    public ItemStack getPost() {
        if (this.postTemplate == null) {
            this.postTemplate = EnumPost.METAL_UNPAINTED.getItem();
        }

        return this.postTemplate;
    }

    public ItemStack getGlassPane() {
        if (this.glassPaneTemplate == null) {
            this.glassPaneTemplate = new ItemStack(Blocks.glass_pane);
        }

        return this.glassPaneTemplate;
    }

    private boolean isEmblem(ItemStack stack) {
        return CustomItemEmblem.item != null && stack.getItem() == CustomItemEmblem.item;
    }

    private boolean isPost(ItemStack stack) {
        return InvTools.isStackEqualToBlock(stack, BlockPostMetal.post) ? true : InvTools.isItemEqual(stack, this.getPost(), true, false);
    }

    private boolean isGlassPane(ItemStack stack) {
        return InvTools.isItemEqual(stack, this.getGlassPane(), false, false);
    }

    public boolean matches(InventoryCrafting craftingGrid, World var2) {
        int numPost = 0;
        int numPanes = 0;
        int numEmblem = 0;

        for (int slot = 0; slot < craftingGrid.getSizeInventory(); ++slot) {
            ItemStack stack = craftingGrid.getStackInSlot(slot);
            if (stack != null) {
                if (this.isGlassPane(stack)) {
                    numPanes++;
                } else if (this.isPost(stack)) {
                    numPost++;
                } else {
                    if (!this.isEmblem(stack)) {
                        return false;
                    }

                    numEmblem++;
                }
            }
        }

        return numPost == 1 && numEmblem == 1 && numPanes == 1;
    }

    public ItemStack getCraftingResult(InventoryCrafting craftingGrid) {
        ItemStack post = null;
        ItemStack emblem = null;

        for (int slot = 0; slot < craftingGrid.getSizeInventory(); ++slot) {
            ItemStack stack = craftingGrid.getStackInSlot(slot);
            if (stack != null) {
                if (this.isPost(stack)) {
                    post = stack;
                } else if (this.isGlassPane(stack)) {
                    continue;
                } else {
                    if (!this.isEmblem(stack)) {
                        return null;
                    }

                    emblem = stack;
                }
            }
        }

        if (post == null) {
            return null;
        } else {
            ItemStack result = PostEmblemSign.getItem();
            ItemPost.setEmblem(result, EmblemToolsServer.getEmblemIdentifier(emblem));
            if (InvTools.isStackEqualToBlock(post, BlockPostMetal.post)) {
                InvTools.setItemColor(result, EnumColor.fromId(post.getItemDamage()));
            }

            return result;
        }
    }

    public int getRecipeSize() {
        return 3;
    }

    public ItemStack getRecipeOutput() {
        return this.getEmblemPost();
    }
}
