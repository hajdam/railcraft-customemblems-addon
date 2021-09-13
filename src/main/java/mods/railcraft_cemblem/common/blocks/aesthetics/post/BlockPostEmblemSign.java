package mods.railcraft_cemblem.common.blocks.aesthetics.post;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.railcraft.api.core.IPostConnection;
import mods.railcraft.common.core.Railcraft;
import mods.railcraft.common.core.RailcraftConfig;
import mods.railcraft.common.plugins.forge.HarvestPlugin;
import mods.railcraft.common.plugins.forge.RailcraftRegistry;
import mods.railcraft.common.util.inventory.InvTools;
import mods.railcraft.common.util.misc.EnumColor;
import mods.railcraft.common.util.misc.Game;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Random;
import mods.railcraft.common.blocks.aesthetics.post.BlockPostBase;
import mods.railcraft.common.blocks.aesthetics.post.BlockPostMetal;
import mods.railcraft.common.blocks.aesthetics.post.ItemPost;
import mods.railcraft.common.blocks.aesthetics.post.TilePostEmblem;

public class BlockPostEmblemSign extends BlockPostBase implements IPostConnection {

    public static BlockPostEmblemSign block;

    protected BlockPostEmblemSign(int renderType) {
        super(renderType);
        setBlockName("railcraft_cemblem.post_emblem_sign");
    }

    public static void registerBlock() {
        if (block != null)
            return;

        if (RailcraftConfig.isBlockEnabled("post")) {
            block = new BlockPostEmblemSign(Railcraft.getProxy().getRenderId());

            GameRegistry.registerTileEntity(TilePostEmblemSign.class, "RCPostEmblemSignTile");

            RailcraftRegistry.register(block, ItemPostEmblemSign.class);

            RailcraftRegistry.register(PostEmblemSign.getItem(1));

            HarvestPlugin.setHarvestLevel(block, "pickaxe", 2);

//            ForestryPlugin.addBackpackItem("builder", block);
        }
    }
    
    public ItemStack getItem(int qty) {
        return PostEmblemSign.getItem(qty);
    }


    @Override
    public boolean isPlatform(int meta) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        PostEmblemSign.setTexture(iconRegister.registerIcon("railcraft_cemblem:post_emblem_sign"));
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return PostEmblemSign.getIcon();
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TilePostEmblemSign) {
            TilePostEmblemSign post = (TilePostEmblemSign) tile;
            EnumColor color = post.getColor();
            if (color != null && BlockPostMetal.textures != null)
                return BlockPostMetal.textures[color.ordinal()];
        }

        return super.getIcon(world, x, y, z, side);
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    @Override
    public int quantityDropped(int meta, int fortune, Random random) {
        return 1;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TilePostEmblemSign) {
            TilePostEmblemSign post = (TilePostEmblemSign) tile;
            ArrayList<ItemStack> drops = super.getDrops(world, x, y, z, metadata, fortune);
            InvTools.setItemColor(drops.get(0), post.getColor());
            ItemPost.setEmblem(drops.get(0), post.getEmblem());
            return drops;
        }

        return super.getDrops(world, x, y, z, metadata, fortune);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l) {
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
        player.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
        player.addExhaustion(0.025F);
        if (Game.isHost(world) && !player.capabilities.isCreativeMode)
            dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
        return world.setBlockToAir(x, y, z);
    }

    @Override
    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        return side == ForgeDirection.DOWN || side == ForgeDirection.UP;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TilePostEmblemSign();
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return 0;
    }

    @Override
    public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return 0;
    }

    @Override
    public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return false;
    }

    @Override
    public boolean recolourBlock(World world, int x, int y, int z, ForgeDirection side, int colour) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TilePostEmblemSign) {
            TilePostEmblemSign tileEmblem = (TilePostEmblemSign) tile;
            tileEmblem.setColor(EnumColor.fromId(15 - colour));
            return true;
        }
        return false;
    }

    @Override
    public ConnectStyle connectsToPost(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TilePostEmblemSign) {
            TilePostEmblemSign tileEmblem = (TilePostEmblemSign) tile;
            if (tileEmblem.getFacing() == side)
                return ConnectStyle.NONE;
        }
        if (tile instanceof TilePostEmblem) {
            TilePostEmblem tileEmblem = (TilePostEmblem) tile;
            if (tileEmblem.getFacing() == side)
                return ConnectStyle.NONE;
        }
        return ConnectStyle.TWO_THIN;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        super.onBlockPlacedBy(world, x, y, z, entity, stack);
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TilePostEmblemSign) {
            TilePostEmblemSign post = (TilePostEmblemSign) tile;
            post.onBlockPlacedBy(entity, stack);
        }
    }
}
