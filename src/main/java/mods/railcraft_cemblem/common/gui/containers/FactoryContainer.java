package mods.railcraft_cemblem.common.gui.containers;

import org.apache.logging.log4j.Level;
import net.minecraft.inventory.Container;
import net.minecraft.entity.player.InventoryPlayer;
import mods.railcraft.common.blocks.machine.TileMultiBlock;
import mods.railcraft_cemblem.common.gui.EnumGui;
import mods.railcraft.common.util.misc.Game;
import net.minecraft.world.World;
import mods.railcraft_cemblem.common.blocks.machine.TileCustomEngravingBench;

public class FactoryContainer {

    private FactoryContainer() {

    }

    @SuppressWarnings("ConstantConditions")
    public static Container build(EnumGui gui, InventoryPlayer inv, Object obj, World world, int x, int y, int z) {
        if (obj instanceof TileMultiBlock && !((TileMultiBlock) obj).isStructureValid()) {
            return null;
        }

        try {
            switch (gui) {
                case CUSTOM_ENGRAVING_BENCH:
                    return new ContainerCustomEngravingBench(inv, (TileCustomEngravingBench) obj);
                default:
                    return null;
            }
        } catch (ClassCastException ex) {
            Game.log(Level.WARN, "Error when attempting to build gui container {0}: {1}", gui, ex);
        }
        return null;
    }
}
