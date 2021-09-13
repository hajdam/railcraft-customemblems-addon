package mods.railcraft_cemblem.client.gui;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import mods.railcraft.client.gui.GuiTools;
import mods.railcraft.client.gui.TileGui;
import mods.railcraft.client.gui.buttons.GuiBetterButton;
import mods.railcraft.client.gui.buttons.GuiButtonSmall;
import mods.railcraft_cemblem.common.blocks.machine.TileCustomEngravingBench.GuiPacketType;
import mods.railcraft.common.gui.buttons.StandardButtonTextureSets;
import mods.railcraft.common.gui.widgets.Widget;
import mods.railcraft.common.plugins.forge.LocalizationPlugin;
import mods.railcraft.common.util.network.PacketBuilder;
import mods.railcraft_cemblem.common.blocks.machine.TileCustomEngravingBench;
import mods.railcraft_cemblem.common.gui.containers.ContainerCustomEngravingBench;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class GuiCustomEngravingBench extends TileGui {

    private final TileCustomEngravingBench tile;
    private final EntityPlayer player;

    public GuiCustomEngravingBench(InventoryPlayer inventoryplayer, TileCustomEngravingBench tile) {
        super(tile, new ContainerCustomEngravingBench(inventoryplayer, tile), "railcraft_cemblem:textures/gui/gui_engraving.png");
        this.tile = tile;
        this.player = inventoryplayer.player;
        this.ySize = 215;
    }

    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        int w = (this.width - this.xSize) / 2;
        int h = (this.height - this.ySize) / 2;
        this.buttonList.add(new GuiBetterButton(0, w + 12, h + 26, 10, StandardButtonTextureSets.LEFT_BUTTON, ""));
        this.buttonList.add(new GuiBetterButton(1, w + 154, h + 26, 10, StandardButtonTextureSets.RIGHT_BUTTON, ""));
        this.buttonList.add(new GuiButtonSmall(2, w + 61, h + 60, 54, LocalizationPlugin.translate("railcraft.gui.engrave")));
    }

    protected void actionPerformed(GuiButton button) {
        super.actionPerformed(button);
        GuiPacketType packetType = GuiPacketType.NORMAL_RETURN;
        if (button.id == 0) {
            ((ContainerCustomEngravingBench) this.container).emblemBank.shiftLeft();
        }

        if (button.id == 1) {
            ((ContainerCustomEngravingBench) this.container).emblemBank.shiftRight();
        }

        if (button.id == 2) {
            packetType = GuiPacketType.START_CRAFTING;
        }

        this.sendUpdateToTile(packetType);
    }

    public void sendUpdateToTile(GuiPacketType type) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream data = new DataOutputStream(bytes);

        try {
            data.writeByte(type.ordinal());
            data.writeUTF(((ContainerCustomEngravingBench) this.container).emblemBank.getSelectedEmblem());
        } catch (IOException var5) {
        }

        PacketBuilder.instance().sendGuiReturnPacket(this.tile, bytes.toByteArray());
    }

    public void onGuiClosed() {
        super.onGuiClosed();
        this.sendUpdateToTile(GuiPacketType.NORMAL_RETURN);
    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        GuiTools.drawCenteredString(this.fontRendererObj, this.tile.getName(), 6);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        // Can't compile with GuiContainerRailcraft.this.drawGuiContainerBackgroundLayer(f, i, j);
        drawGuiContainerBackgroundLayerGCR(f, i, j);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        if (this.tile.getProgress() > 0) {
            int progress = this.tile.getProgressScaled(23);
            this.drawTexturedModalRect(x + 76, y + 76, 176, 0, progress + 1, 12);
        }
    }

    private void drawGuiContainerBackgroundLayerGCR(float f, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        bindTexture(texture);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        int mX = mouseX - guiLeft;
        int mY = mouseY - guiTop;

        for (Widget element : container.getElements()) {
            if (element.hidden)
                continue;
            element.draw(this, x, y, mX, mY);
        }
    }
}
