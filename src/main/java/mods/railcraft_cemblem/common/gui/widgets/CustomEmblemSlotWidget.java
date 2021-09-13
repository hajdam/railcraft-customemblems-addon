package mods.railcraft_cemblem.common.gui.widgets;

import mods.railcraft.client.gui.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.item.*;
import mods.railcraft.common.gui.tooltips.*;
import mods.railcraft.client.emblems.*;
import mods.railcraft.common.gui.widgets.Widget;
import net.minecraft.util.ResourceLocation;

public class CustomEmblemSlotWidget extends Widget {

    public String emblemIdentifier;
    private final ToolTip toolTip;

    public CustomEmblemSlotWidget(final int x, final int y, final int u, final int v) {
        super(x, y, u, v, 16, 16);
        this.toolTip = new ToolTip(750);
    }

    @SideOnly(Side.CLIENT)
    public void draw(final GuiContainerRailcraft gui, final int guiX, final int guiY, final int mouseX, final int mouseY) {
        final String emblem = this.getEmblem();
        if (emblem != null && !emblem.equals("")) {
            final ResourceLocation emblemTextureLocation = EmblemToolsClient.packageManager.getEmblemTextureLocation(emblem);
            gui.bindTexture(emblemTextureLocation);
            gui.drawTexture(guiX + this.x, guiY + this.y, 16, 16, 0.0f, 0.0f, 1.0f, 1.0f);
            gui.bindTexture(gui.texture);
        }
        if (this.isMouseOver(mouseX, mouseY)) {
            gui.func_73733_a(guiX + this.x, guiY + this.y, guiX + this.x + 16, guiY + this.y + 16, -2130706433, -2130706433);
        }
    }

    protected String getEmblem() {
        return this.emblemIdentifier;
    }

    public ToolTip getToolTip() {
        this.toolTip.clear();
        final String emblemIdent = this.getEmblem();
        if (emblemIdent == null || emblemIdent.equals("")) {
            return null;
        }
        final Emblem emblem = EmblemToolsClient.packageManager.getEmblem(emblemIdent);
        if (emblem == null) {
            return null;
        }
        this.toolTip.add(new ToolTipLine(emblem.displayName, EnumRarity.values()[emblem.rarity].rarityColor));
        return this.toolTip;
    }
}
