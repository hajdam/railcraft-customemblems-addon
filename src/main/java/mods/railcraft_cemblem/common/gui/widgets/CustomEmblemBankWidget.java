package mods.railcraft_cemblem.common.gui.widgets;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.inventory.*;
import java.util.*;
import java.io.*;
import mods.railcraft.client.gui.GuiContainerRailcraft;
import mods.railcraft.common.gui.containers.RailcraftContainer;
import mods.railcraft.common.gui.widgets.Widget;
import mods.railcraft.common.util.collections.RevolvingList;
import mods.railcraft_cemblem.client.emblems.CustomEmblemUtils;

public class CustomEmblemBankWidget extends Widget {

    private static final int NUM_SLOTS = 7;
    private List<CustomEmblemSlotWidget> slots;
    private RevolvingList<List<String>> emblems;
    String currentSelection;
    
    public CustomEmblemBankWidget(final int x, final int y, final String currentEmblem) {
        super(x, y, 182, 12, 126, 18);
        this.slots = new ArrayList<CustomEmblemSlotWidget>(7);
        this.emblems = (RevolvingList<List<String>>)new RevolvingList();
        this.currentSelection = "";
        for (int i = 0; i < 7; ++i) {
            this.slots.add((CustomEmblemSlotWidget)new CustomEmblemBankSlotWidget(this, i, x + 1 + i * 18, y + 1, this.u, this.v));
        }
        this.currentSelection = currentEmblem;
    }
    
    public void addToContainer(final RailcraftContainer container) {
        super.addToContainer(container);
        for (final CustomEmblemSlotWidget slot : this.slots) {
            container.addWidget((Widget)slot);
        }
    }
    
    public List<CustomEmblemSlotWidget> getSlots() {
        return this.slots;
    }
    
    public String getEmblem(final int index) {
        final List<String> view = (List<String>)this.emblems.getCurrent();
        if (view == null || index >= view.size()) {
            return "";
        }
        return view.get(index);
    }
    
    public String getSelectedEmblem() {
        return this.currentSelection;
    }
    
    public void shiftLeft() {
        this.emblems.rotateLeft();
    }
    
    public void shiftRight() {
        this.emblems.rotateRight();
    }
    
    public void draw(final GuiContainerRailcraft gui, final int guiX, final int guiY, final int mouseX, final int mouseY) {
    }
    
    public void initWidget(final ICrafting player) {
        try {
            final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            final DataOutputStream data = new DataOutputStream(bytes);
            data.writeUTF(getSelectedEmblem());
            final List<String> customEmblems = CustomEmblemUtils.getCraftableCustomEmblemIds();
            data.writeShort(customEmblems.size());
            for (final String s : customEmblems) {
                data.writeUTF(s);
            }
            this.container.sendWidgetDataToClient((Widget) this, player, bytes.toByteArray());
        } catch (IOException ex) {
        }
    }

    @SideOnly(Side.CLIENT)
    public void handleClientPacketData(final DataInputStream data) throws IOException {
        this.currentSelection = data.readUTF();
        final int count = data.readShort();
        int subIndex = 0;
        List<String> currentView;
        List<String> emblemView = currentView = new ArrayList<String>(7);
        for (int i = 0; i < count; ++i) {
            if (subIndex >= 7) {
                subIndex = 0;
                this.emblems.add(emblemView);
                emblemView = new ArrayList<String>(7);
            }
            final String identifier = data.readUTF();
            emblemView.add(identifier);
            if (this.currentSelection.equals(identifier)) {
                currentView = emblemView;
            }
            ++subIndex;
        }
        this.emblems.add(emblemView);
        this.emblems.setCurrent(currentView);
    }
}
