package mods.railcraft_cemblem.common.gui;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import org.apache.logging.log4j.Level;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import mods.railcraft_cemblem.client.gui.FactoryGui;
import mods.railcraft_cemblem.common.gui.containers.FactoryContainer;
import mods.railcraft.common.util.misc.Game;
import mods.railcraft_cemblem.common.core.Railcraft_Cemblem;

public class GuiHandler implements IGuiHandler {

    public static void openGui(EnumGui gui, EntityPlayer player, World world, int x, int y, int z) {
        if (Game.isHost(world)) {
            if (gui.hasContainer()) {
                player.openGui(Railcraft_Cemblem.getMod(), gui.ordinal(), world, x, y, z);
            }
        } else if (!gui.hasContainer()) {
            TileEntity tile = world.getTileEntity(x, y, z);
            FMLClientHandler.instance().displayGuiScreen(player, FactoryGui.build(gui, player.inventory, tile, world, x, y, z));
        }
    }

    public static void openGui(EnumGui gui, EntityPlayer player, World world, Entity entity) {
        if (Game.isHost(world)) {
            if (gui.hasContainer()) {
                player.openGui(Railcraft_Cemblem.getMod(), gui.ordinal(), world, entity.getEntityId(), -1, 0);
            }
        } else if (!gui.hasContainer()) {
            FMLClientHandler.instance().displayGuiScreen(player, FactoryGui.build(gui, player.inventory, entity, world, entity.getEntityId(), -1, 0));
        }
    }

    @Override
    public Object getServerGuiElement(int enumId, EntityPlayer player, World world, int x, int y, int z) {
        if (y < 0) {
            Entity entity = world.getEntityByID(x);
            if (entity == null) {
                Game.log(Level.WARN, "[Server] Entity not found when opening GUI: {0}", x);
                return null;
            }
            return FactoryContainer.build(EnumGui.fromOrdinal(enumId), player.inventory, entity, world, x, y, z);
        }
        TileEntity tile = world.getTileEntity(x, y, z);
        return FactoryContainer.build(EnumGui.fromOrdinal(enumId), player.inventory, tile, world, x, y, z);
    }

    @Override
    public Object getClientGuiElement(int enumId, EntityPlayer player, World world, int x, int y, int z) {
        if (y < 0) {
            Entity entity = world.getEntityByID(x);
            if (entity == null) {
                Game.log(Level.WARN, "[Client] Entity not found when opening GUI: {0}", x);
                return null;
            }
            return FactoryGui.build(EnumGui.fromOrdinal(enumId), player.inventory, entity, world, x, y, z);
        }
        TileEntity tile = world.getTileEntity(x, y, z);
        return FactoryGui.build(EnumGui.fromOrdinal(enumId), player.inventory, tile, world, x, y, z);
    }
}
