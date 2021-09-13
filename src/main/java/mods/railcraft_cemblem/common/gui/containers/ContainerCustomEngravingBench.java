package mods.railcraft_cemblem.common.gui.containers;

import cofh.api.energy.EnergyStorage;
import java.util.Iterator;
import mods.railcraft.common.gui.containers.RailcraftContainer;
import mods.railcraft.common.gui.slots.SlotOutput;
import mods.railcraft.common.gui.slots.SlotPassThrough;
import mods.railcraft.common.gui.widgets.IndicatorWidget;
import mods.railcraft.common.gui.widgets.RFEnergyIndicator;
import mods.railcraft_cemblem.common.blocks.machine.TileCustomEngravingBench;
import mods.railcraft_cemblem.common.gui.widgets.CustomEmblemBankWidget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;

public class ContainerCustomEngravingBench extends RailcraftContainer {
   private final TileCustomEngravingBench tile;
   private final RFEnergyIndicator energyIndicator;
   public CustomEmblemBankWidget emblemBank;
   private int lastEnergy;
   private int lastProgress;

   public ContainerCustomEngravingBench(InventoryPlayer inventoryplayer, TileCustomEngravingBench tile) {
      super(tile);
      this.tile = tile;
      this.energyIndicator = new RFEnergyIndicator(tile);
      this.addWidget(new IndicatorWidget(this.energyIndicator, 157, 50, 176, 12, 6, 48));
      this.addWidget(this.emblemBank = new CustomEmblemBankWidget(25, 25, tile.currentEmblem));
      this.addSlot(new SlotPassThrough(tile, 0, 35, 66));
      this.addSlot(new SlotOutput(tile, 1, 125, 66));

      int j1;
      for(j1 = 0; j1 < 3; ++j1) {
         for(int l1 = 0; l1 < 9; ++l1) {
            this.addSlot(new Slot(inventoryplayer, l1 + j1 * 9 + 9, 8 + l1 * 18, 133 + j1 * 18));
         }
      }

      for(j1 = 0; j1 < 9; ++j1) {
         this.addSlot(new Slot(inventoryplayer, j1, 8 + j1 * 18, 191));
      }

   }

   public void sendUpdateToClient() {
      super.sendUpdateToClient();
      EnergyStorage storage = this.tile.getEnergyStorage();
      Iterator var2 = this.crafters.iterator();

      while(var2.hasNext()) {
         Object crafter = var2.next();
         ICrafting icrafting = (ICrafting)crafter;
         if (this.lastProgress != this.tile.getProgress()) {
            icrafting.sendProgressBarUpdate(this, 0, this.tile.getProgress());
         }

         if (storage != null && this.lastEnergy != storage.getEnergyStored()) {
            icrafting.sendProgressBarUpdate(this, 1, storage.getEnergyStored());
         }
      }

      this.lastProgress = this.tile.getProgress();
      if (storage != null) {
         this.lastEnergy = storage.getEnergyStored();
      }

   }

   public void addCraftingToCrafters(ICrafting icrafting) { // WAS: func_75132_a
      super.addCraftingToCrafters(icrafting);
      icrafting.sendProgressBarUpdate(this, 0, this.tile.getProgress());
      EnergyStorage storage = this.tile.getEnergyStorage();
      if (storage != null) {
         icrafting.sendProgressBarUpdate(this, 2, storage.getEnergyStored());
      }

   }

   public void updateProgressBar(int id, int data) {
      switch(id) {
      case 0:
         this.tile.setProgress(data);
         break;
      case 1:
         this.energyIndicator.updateEnergy(data);
         break;
      case 2:
         this.energyIndicator.setEnergy(data);
      }
   }

    @Override
    public boolean canInteractWith(EntityPlayer ep) {
        return super.func_75145_c(ep);
    }
}
