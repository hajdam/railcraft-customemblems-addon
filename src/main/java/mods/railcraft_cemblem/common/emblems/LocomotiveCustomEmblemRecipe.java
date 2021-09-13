package mods.railcraft_cemblem.common.emblems;

import java.util.Iterator;
import mods.railcraft.common.carts.ItemLocomotive;
import mods.railcraft.common.emblems.EmblemToolsServer;
import mods.railcraft.common.util.inventory.InvTools;
import mods.railcraft.common.util.inventory.wrappers.IInvSlot;
import mods.railcraft.common.util.inventory.wrappers.InventoryIterator;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class LocomotiveCustomEmblemRecipe implements IRecipe {
   private final ItemStack locomotive;

   public LocomotiveCustomEmblemRecipe(ItemStack locomotive) {
      this.locomotive = locomotive;
   }

   private boolean isEmblem(ItemStack stack) {
      return CustomItemEmblem.item != null && stack.getItem() == CustomItemEmblem.item;
   }

   private boolean isLocomotive(ItemStack loco) {
      return InvTools.isItemEqualIgnoreNBT(this.locomotive, loco);
   }

   public boolean matches(InventoryCrafting craftingGrid, World var2) {
      int numLocomotive = 0;
      int numEmblem = 0;

      for(int slot = 0; slot < craftingGrid.getSizeInventory(); ++slot) {
         ItemStack stack = craftingGrid.getStackInSlot(slot);
         if (stack != null) {
            if (this.isLocomotive(stack)) {
               ++numLocomotive;
            } else {
               if (!this.isEmblem(stack)) {
                  return false;
               }

               ++numEmblem;
            }
         }
      }

      return numLocomotive == 1 && numEmblem == 1;
   }

   public ItemStack getCraftingResult(InventoryCrafting craftingGrid) {
      ItemStack loco = null;
      ItemStack emblem = null;
      Iterator var4 = InventoryIterator.getIterable(craftingGrid).iterator();

      while(var4.hasNext()) {
         IInvSlot slot = (IInvSlot)var4.next();
         ItemStack stack = slot.getStackInSlot();
         if (stack != null) {
            if (this.isLocomotive(stack)) {
               loco = stack;
            } else {
               if (!this.isEmblem(stack)) {
                  return null;
               }

               emblem = stack;
            }
         }
      }

      if (loco != null && emblem != null) {
         ItemStack result = loco.copy();
         ItemLocomotive.setEmblem(result, EmblemToolsServer.getEmblemIdentifier(emblem));
         return result;
      } else {
         return null;
      }
   }

   public int getRecipeSize() {
      return 2;
   }

   public ItemStack getRecipeOutput() {
      return this.locomotive;
   }
}
