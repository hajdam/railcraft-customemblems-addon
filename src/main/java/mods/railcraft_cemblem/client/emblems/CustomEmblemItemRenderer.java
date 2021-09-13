package mods.railcraft_cemblem.client.emblems;

import mods.railcraft.client.util.textures.Texture;
import mods.railcraft.common.emblems.EmblemToolsServer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import mods.railcraft.client.emblems.IEmblemItemRenderer;
import mods.railcraft.client.emblems.EmblemToolsClient;
import mods.railcraft.common.emblems.ItemEmblem;

public class CustomEmblemItemRenderer implements IEmblemItemRenderer {

    private static final ResourceLocation GLINT_TEXTURE;
    private static final RenderItem renderItem;
    private IEmblemItemRenderer parent;
    public static CustomEmblemItemRenderer instance;

    public CustomEmblemItemRenderer(IEmblemItemRenderer parent) {
        this.parent = parent;
    }

    public static void register() {
        instance = new CustomEmblemItemRenderer((IEmblemItemRenderer) EmblemToolsClient.renderer);
        EmblemToolsClient.renderer = instance;
    }

    @Override
    public void renderIn3D(String ident, boolean renderGlint) {
        if (CustomEmblemUtils.isCustom(ident)) {
            this.renderIn3D(ItemEmblem.getEmblem((String) ident), renderGlint);
        } else {
            parent.renderIn3D(ident, renderGlint);
        }
    }

    @Override
    public void renderIn3D(ItemStack stack, boolean renderGlint) {
        GL11.glPushMatrix();
        Tessellator tessellator = Tessellator.instance;
        int meta = stack.getItemSpriteNumber(); // func_77960_j();
        for (int pass = 0; pass < stack.getItem().getRenderPasses(meta); ++pass) {
            int color = stack.getItem().getColorFromItemStack(stack, pass);
            float c1 = (float) (color >> 16 & 255) / 255.0f;
            float c2 = (float) (color >> 8 & 255) / 255.0f;
            float c3 = (float) (color & 255) / 255.0f;
            if (CustomEmblemItemRenderer.renderItem.renderWithColor) {
                GL11.glColor4f((float) c1, (float) c2, (float) c3, (float) 1.0f);
            }
            String emblemIdentifier = EmblemToolsServer.getEmblemIdentifier((ItemStack) stack);
            Texture texture = EmblemToolsClient.packageManager.getEmblemTexture(emblemIdentifier);
            if (emblemIdentifier != null && !emblemIdentifier.equals("")) {
                Minecraft.getMinecraft().renderEngine.bindTexture(EmblemToolsClient.packageManager.getEmblemTextureLocation(emblemIdentifier));

                if (texture != null && texture.getImage() != null) {
                    ItemRenderer.renderItemIn2D((Tessellator) tessellator, (float) 0.0f, (float) 0.0f, (float) 1.0f, (float) 1.0f, (int) texture.getImage().getWidth(), (int) texture.getImage().getHeight(), (float) 0.0625f);
                }
            } else {
                IIcon icon = stack.getItem().getIconFromDamageForRenderPass(meta, pass);
                // WAS: ItemRenderer.renderItemIn2D((Tessellator) tessellator, (float) icon.func_94212_f(), (float) icon.func_94206_g(), (float) icon.func_94209_e(), (float) icon.func_94210_h(), (int) icon.func_94211_a(), (int) icon.func_94216_b(), (float) 0.0625f);
                ItemRenderer.renderItemIn2D((Tessellator) tessellator, (float) icon.getMinU(), (float) icon.getMinV(), (float) icon.getMaxU(), (float) icon.getMinV(), (int) icon.getIconWidth(), (int) icon.getIconWidth(), (float) 0.0625f);
            }
            if (!renderGlint || !stack.hasEffect(pass)) {
                continue;
            }
            GL11.glDepthFunc((int) 514);
            GL11.glDisable((int) 2896);
            RenderManager.instance.renderEngine.bindTexture(GLINT_TEXTURE); // WAS: .field_78724_e.func_110577_a(GLINT_TEXTURE);
            GL11.glEnable((int) 3042);
            GL11.glBlendFunc((int) 768, (int) 1);
            float f13 = 0.76f;
            GL11.glColor4f((float) (0.5f * f13), (float) (0.25f * f13), (float) (0.8f * f13), (float) 1.0f);
            GL11.glMatrixMode((int) 5890);
            GL11.glPushMatrix();
            float f14 = 0.125f;
            GL11.glScalef((float) f14, (float) f14, (float) f14);
            float f15 = (float) (Minecraft.getGLMaximumTextureSize() % 3000) / 3000.0f * 8.0f;
            GL11.glTranslatef((float) f15, (float) 0.0f, (float) 0.0f);
            GL11.glRotatef((float) -50.0f, (float) 0.0f, (float) 0.0f, (float) 1.0f);
            ItemRenderer.renderItemIn2D((Tessellator) tessellator, (float) 0.0f, (float) 0.0f, (float) 1.0f, (float) 1.0f, (int) 255, (int) 255, (float) 0.0625f);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef((float) f14, (float) f14, (float) f14);
            f15 = (float) (Minecraft.getGLMaximumTextureSize() % 4873) / 4873.0f * 8.0f;
            GL11.glTranslatef((float) (-f15), (float) 0.0f, (float) 0.0f);
            GL11.glRotatef((float) 10.0f, (float) 0.0f, (float) 0.0f, (float) 1.0f);
            ItemRenderer.renderItemIn2D((Tessellator) tessellator, (float) 0.0f, (float) 0.0f, (float) 1.0f, (float) 1.0f, (int) 255, (int) 255, (float) 0.0625f);
            GL11.glPopMatrix();
            GL11.glMatrixMode((int) 5888);
            GL11.glDisable((int) 3042);
            GL11.glEnable((int) 2896);
            GL11.glDepthFunc((int) 515);
        }
        GL11.glPopMatrix();
    }

    static {
        GLINT_TEXTURE = new ResourceLocation("textures/misc/enchanted_item_glint.png");
        renderItem = new RenderItem();
    }
}
