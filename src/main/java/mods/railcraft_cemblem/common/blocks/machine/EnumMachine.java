package mods.railcraft_cemblem.common.blocks.machine;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mods.railcraft.client.util.textures.TextureAtlasSheet;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import mods.railcraft.common.blocks.machine.BlockMachine;
import mods.railcraft.common.blocks.machine.IEnumMachine;
import mods.railcraft.common.blocks.machine.ItemMachine;
import mods.railcraft.common.blocks.machine.TileMachineBase;
import mods.railcraft.common.gui.tooltips.ToolTip;
import mods.railcraft.common.plugins.forge.LocalizationPlugin;
import mods.railcraft.common.modules.ModuleManager;
import mods.railcraft.common.modules.ModuleManager.Module;
import mods.railcraft.common.plugins.forge.RailcraftRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;

public enum EnumMachine implements IEnumMachine {

    CUSTOM_ENGRAVING_BENCH(Module.EMBLEM, "custom.engraving.bench", TileCustomEngravingBench.class, 4, 1, 0, 1, 3, 3, 3, 3, 2);

    private final Module module;
    private final String tag;
    private final Class<? extends TileMachineBase> tile;
    private IIcon[] texture = new IIcon[12];
    private final int[] textureInfo;
    private static final List<EnumMachine> creativeList = new ArrayList<EnumMachine>();
    private static final EnumMachine[] VALUES = values();
    private ToolTip tip;

    private Block machineBlock;

    static {
        creativeList.add(CUSTOM_ENGRAVING_BENCH);
    }

    private EnumMachine(Module module, String tag, Class<? extends TileMachineBase> tile, int... textureInfo) {
        this.module = module;
        this.tile = tile;
        this.tag = tag;
        this.textureInfo = textureInfo;
    }

    public boolean register() {
        int[] lightOpacity = new int[16];
        Arrays.fill(lightOpacity, 255);
        machineBlock = new BlockMachine(0, new MachineProxy(), true, lightOpacity).setBlockName("railcraft_cemblem.machine");
        RailcraftRegistry.register(machineBlock, ItemMachine.class);
        machineBlock.setHarvestLevel("pickaxe", 2, 0);
        return true;
    }

    @Override
    public boolean isDepreciated() {
        return module == null;
    }

    public void setTexture(IIcon[] tex) {
        this.texture = tex;
    }

    public IIcon[] getTexture() {
        return texture;
    }

    @Override
    public IIcon getTexture(int index) {
        if (index < 0 || index >= texture.length) {
            index = 0;
        }
        return texture[index];
    }

    @SideOnly(Side.CLIENT)
    public static void registerIcons(IIconRegister iconRegister) {
        for (EnumMachine machine : VALUES) {
            if (machine.isDepreciated()) {
                continue;
            }
            if (machine.textureInfo.length == 0) {
                continue;
            }
            machine.texture = new IIcon[machine.textureInfo.length - 2];
            int columns = machine.textureInfo[0];
            int rows = machine.textureInfo[1];
            IIcon[] icons = TextureAtlasSheet.unstitchIcons(iconRegister, "railcraft_cemblem:" + machine.tag, columns, rows);
            for (int i = 0; i < machine.texture.length; i++) {
                machine.texture[i] = icons[machine.textureInfo[i + 2]];
            }
        }
    }

    public static EnumMachine fromId(int id) {
        if (id < 0 || id >= VALUES.length) {
            id = 0;
        }
        return VALUES[id];
    }

    public static List<EnumMachine> getCreativeList() {
        return creativeList;
    }

    @Override
    public String getTag() {
        return "tile.railcraft_cemblem.machine." + tag;
    }

    @Override
    public Class getTileClass() {
        return tile;
    }

    public TileMachineBase getTileEntity() {
        try {
            return tile.newInstance();
        } catch (Exception ex) {
        }
        return null;
    }

    @Override
    public ItemStack getItem() {
        return getItem(1);
    }

    @Override
    public ItemStack getItem(int qty) {
        Block block = getBlock();
        if (block == null) {
            return null;
        }
        return new ItemStack(block, qty, ordinal());
    }

    public Module getModule() {
        return module;
    }

    @Override
    public Block getBlock() {
        return machineBlock;
    }

    @Override
    public boolean isAvaliable() {
        return ModuleManager.isModuleLoaded(getModule()) && getBlock() != null; // && RailcraftConfig.isSubBlockEnabled(getTag());
    }

    public ToolTip getToolTip(ItemStack stack, EntityPlayer player, boolean adv) {
        if (tip != null) {
            return tip;
        }
        String tipTag = getTag() + ".tip";
        if (LocalizationPlugin.hasTag(tipTag)) {
            tip = ToolTip.buildToolTip(tipTag);
        }
        return tip;
    }

}
