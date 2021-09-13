package mods.railcraft_cemblem.client.gui;

import org.apache.logging.log4j.Level;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.InventoryPlayer;
import mods.railcraft.common.blocks.machine.TileMultiBlock;
import mods.railcraft_cemblem.common.gui.EnumGui;
import mods.railcraft.common.util.misc.Game;
import mods.railcraft_cemblem.common.blocks.machine.TileCustomEngravingBench;
import net.minecraft.world.World;

public class FactoryGui {

    private FactoryGui() {
    }

    public static GuiScreen build(EnumGui gui, InventoryPlayer inv, Object obj, World world, int x, int y, int z) {
        if (obj instanceof TileMultiBlock && !((TileMultiBlock) obj).isStructureValid()) {
            return null;
        }

        try {
            switch (gui) {
                case CUSTOM_ENGRAVING_BENCH:
                    return new GuiCustomEngravingBench(inv, (TileCustomEngravingBench) obj);
            }
        } catch (ClassCastException ex) {
            Game.log(Level.WARN, "Error when attempting to build gui {0}: {1}", gui, ex);
        }
        return null;
    }
}
