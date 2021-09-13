package mods.railcraft_cemblem.common.gui.widgets;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.railcraft.client.gui.GuiContainerRailcraft;

public class CustomEmblemBankSlotWidget extends CustomEmblemSlotWidget {

    private final CustomEmblemBankWidget bank;
    public final int index;

    CustomEmblemBankSlotWidget(CustomEmblemBankWidget bank, int index, int x, int y, int u, int v) {
        super(x, y, u, v);
        this.bank = bank;
        this.index = index;
    }

    public boolean mouseClicked(int mouseX, int mouseY, int button) {
        if (button != 0) return false;
        this.bank.currentSelection = this.getEmblem();
        return true;
    }

    @SideOnly(value=Side.CLIENT)
    public void draw(GuiContainerRailcraft gui, int guiX, int guiY, int mouseX, int mouseY) {
        super.draw(gui, guiX, guiY, mouseX, mouseY);
        if (this.bank.currentSelection.equals("")) return;
        if (!this.bank.currentSelection.equals(this.getEmblem())) return;
        gui.drawTexturedModalRect(guiX + this.x - 2, guiY + this.y - 2, this.u, this.v, this.w + 4, this.h + 4);
    }

    protected String getEmblem() {
        return this.bank.getEmblem(this.index);
    }
}
