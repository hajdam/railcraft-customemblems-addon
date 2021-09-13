package mods.railcraft_cemblem.common.modules;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.railcraft.common.blocks.aesthetics.post.BlockPost;
import mods.railcraft.common.carts.EnumCart;
import mods.railcraft.common.emblems.EmblemManager;
import mods.railcraft_cemblem.common.emblems.LocomotiveCustomEmblemRecipe;
import mods.railcraft_cemblem.common.gui.EnumGui;
import mods.railcraft.common.items.RailcraftItem;
import mods.railcraft.common.items.ItemPlate.EnumPlate;
import mods.railcraft.common.modules.RailcraftModule;
import mods.railcraft.common.plugins.forge.CraftingPlugin;
import mods.railcraft.common.util.misc.Game;
import mods.railcraft_cemblem.client.gui.GuiCustomEngravingBench;
import mods.railcraft_cemblem.common.blocks.aesthetics.post.BlockPostEmblemSign;
import mods.railcraft_cemblem.common.blocks.machine.EnumMachine;
import mods.railcraft_cemblem.common.blocks.machine.TileCustomEngravingBench;
import mods.railcraft_cemblem.common.core.Railcraft_Cemblem;
import mods.railcraft_cemblem.common.emblems.CustomItemEmblem;
import mods.railcraft_cemblem.common.emblems.EmblemPostCustomEmblemRecipe;
import mods.railcraft_cemblem.common.emblems.EmblemPostCustomEmblemSignRecipe;
import mods.railcraft_cemblem.common.gui.GuiHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;
import org.apache.logging.log4j.Level;

public class ModuleEmblem extends RailcraftModule {

    public void preInit() {
        CustomItemEmblem.registerItem();
        BlockPostEmblemSign.registerBlock();
        GameRegistry.registerTileEntity(TileCustomEngravingBench.class, "RCCustomEngravingBenchTile");

        RecipeSorter.register("railcraft_cemblem:locomotive.customemblem", LocomotiveCustomEmblemRecipe.class, Category.SHAPELESS, "after:minecraft:shapeless");
        RecipeSorter.register("railcraft_cemblem:customemblem.post.customemblem", EmblemPostCustomEmblemRecipe.class, Category.SHAPELESS, "after:minecraft:shapeless");
        RecipeSorter.register("railcraft_cemblem:customemblem.post.customemblem.sign", EmblemPostCustomEmblemSignRecipe.class, Category.SHAPELESS, "after:minecraft:shapeless");
    }

    public void initFirst() {
        NetworkRegistry.INSTANCE.registerGuiHandler(Railcraft_Cemblem.getMod(), new GuiHandler());
        EnumMachine engravingBench = EnumMachine.CUSTOM_ENGRAVING_BENCH;
        if (engravingBench.register()) {
            CraftingPlugin.addShapedRecipe(engravingBench.getItem(), new Object[]{"TPB", "PCP", "VPV", 'T', Items.diamond_pickaxe, 'B', Items.book, 'P', RailcraftItem.plate.getRecipeObject(EnumPlate.STEEL), 'V', Blocks.sticky_piston, 'C', Blocks.crafting_table});
        }
    }

    public void postInit() {
        this.addLocomotiveEmblemRecipe(EnumCart.LOCO_STEAM_SOLID);
        this.addLocomotiveEmblemRecipe(EnumCart.LOCO_STEAM_MAGIC);
        this.addLocomotiveEmblemRecipe(EnumCart.LOCO_ELECTRIC);
        if (BlockPost.block != null) {
            CraftingPlugin.addRecipe(new EmblemPostCustomEmblemRecipe());
            CraftingPlugin.addRecipe(new EmblemPostCustomEmblemSignRecipe());
        }
    }

    private void addLocomotiveEmblemRecipe(EnumCart cart) {
        ItemStack locomotive = cart.getCartItem();
        if (locomotive != null) {
            IRecipe recipe = new LocomotiveCustomEmblemRecipe(locomotive);
            CraftingPlugin.addRecipe(recipe);
        }
    }

    private void printCode(String code) {
        Game.log(Level.INFO, String.format("Emblem Code: %s - %s", code, EmblemManager.getIdentifierFromCode(code)), new Object[0]);
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGuiScreen(EnumGui gui, InventoryPlayer inv, Object obj, World world, int x, int y, int z) {
        return new GuiCustomEngravingBench(inv, (TileCustomEngravingBench) obj);
    }
}
