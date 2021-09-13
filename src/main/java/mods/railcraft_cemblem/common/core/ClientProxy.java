package mods.railcraft_cemblem.common.core;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import mods.railcraft.client.emblems.EmblemPackageManager;
import mods.railcraft.client.render.BlockRenderer;
import mods.railcraft.client.render.EmblemRenderHelper;
import mods.railcraft.common.emblems.ItemEmblem;
import mods.railcraft_cemblem.client.emblems.CustomEmblemItemRenderer;
import mods.railcraft_cemblem.client.emblems.CustomEmblemPackageManager;
import mods.railcraft_cemblem.client.render.RenderBlockPostSign;
import mods.railcraft_cemblem.client.render.RenderCustomEmblemItem;
import mods.railcraft_cemblem.common.blocks.aesthetics.post.TilePostEmblemSign;
import mods.railcraft_cemblem.common.emblems.CustomItemEmblem;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

    @Override
    public void initClient() {
        if (ItemEmblem.item != null) {
            EmblemPackageManager.instance.init();
            CustomEmblemPackageManager.register();

            EmblemRenderHelper.instance.init();
            CustomEmblemItemRenderer.register();
            registerBlockRenderer(RenderBlockPostSign.make());
            if (CustomItemEmblem.item != null) {
                MinecraftForgeClient.registerItemRenderer(CustomItemEmblem.item, new RenderCustomEmblemItem());
            }

            ClientRegistry.bindTileEntitySpecialRenderer(TilePostEmblemSign.class, new RenderBlockPostSign.EmblemPostTESR());
        }
    }

    private void registerBlockRenderer(BlockRenderer renderer) {
        if (renderer.getBlock() != null) {
            RenderingRegistry.registerBlockHandler(renderer);
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(renderer.getBlock()), renderer.getItemRenderer());
        }
    }
}
