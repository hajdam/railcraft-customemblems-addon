package mods.railcraft_cemblem.client.render;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import mods.railcraft_cemblem.client.emblems.CustomEmblemItemRenderer;
import mods.railcraft.client.emblems.EmblemToolsClient;
import mods.railcraft.client.util.textures.Texture;
import mods.railcraft.common.emblems.EmblemToolsServer;
import mods.railcraft_cemblem.client.emblems.CustomEmblem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

/**
 * Custom emblems renderer.
 */
public class RenderCustomEmblemItem implements IItemRenderer {

    private static final ResourceLocation GLINT_TEXTURE = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    private static final RenderItem renderItem = new RenderItem();

    @Override
    public boolean handleRenderType(ItemStack stack, IItemRenderer.ItemRenderType type) {
        return type == IItemRenderer.ItemRenderType.ENTITY || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON || type == IItemRenderer.ItemRenderType.EQUIPPED;
    }

    @Override
    public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack stack, IItemRenderer.ItemRendererHelper helper) {
        return helper == IItemRenderer.ItemRendererHelper.ENTITY_BOBBING;
    }

    @Override
    public /* varargs */ void renderItem(IItemRenderer.ItemRenderType type, ItemStack stack, Object ... data) {
        if (type == IItemRenderer.ItemRenderType.INVENTORY) {
            this.render(IItemRenderer.ItemRenderType.INVENTORY, stack);
        } else if (type == IItemRenderer.ItemRenderType.ENTITY) {
            if (RenderManager.instance.options.fancyGraphics) {
//            if (RenderManager.field_78727_a.field_78733_k.field_74347_j) {
                this.renderAsEntity(stack, (EntityItem)data[1]);
            } else {
                this.renderAsEntityFlat(stack, (EntityItem)data[1]);
            }
        } else if (type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
            GL11.glPushMatrix();
            this.renderEquiped(stack, (EntityLivingBase)data[1]);
            GL11.glPopMatrix();
        }
    }

    private void renderEquiped(ItemStack stack, EntityLivingBase entity) {
        GL11.glPushMatrix();
        Tessellator tessellator = Tessellator.instance;
        int meta = stack.getItemSpriteNumber();
        for (int pass = 0; pass < stack.getItem().getRenderPasses(meta); ++pass) {
            String emblemIdentifier;
            int color = stack.getItem().getColorFromItemStack(stack, pass);
            float c1 = (float)(color >> 16 & 255) / 255.0f;
            float c2 = (float)(color >> 8 & 255) / 255.0f;
            float c3 = (float)(color & 255) / 255.0f;
            if (RenderCustomEmblemItem.renderItem.renderWithColor) {
                GL11.glColor4f((float)c1, (float)c2, (float)c3, (float)1.0f);
            }
            if ((emblemIdentifier = EmblemToolsServer.getEmblemIdentifier((ItemStack)stack)) != null && !emblemIdentifier.equals("")) {
                Texture texture = internalGetTexture(emblemIdentifier);
                Minecraft.getMinecraft().renderEngine.bindTexture(internalGetLocation(emblemIdentifier));
                ItemRenderer.renderItemIn2D((Tessellator)tessellator, (float)1.0f, (float)0.0f, (float)0.0f, (float)1.0f, (int)texture.getImage().getWidth(), (int)texture.getImage().getHeight(), (float)0.0625f);
            } else {
                IIcon icon = stack.getItem().getIconFromDamageForRenderPass(meta, pass);
                ItemRenderer.renderItemIn2D((Tessellator) tessellator, (float) icon.getMinU(), (float) icon.getMinV(), (float) icon.getMaxU(), (float) icon.getMinV(), (int) icon.getIconWidth(), (int) icon.getIconWidth(), (float) 0.0625f);
            }
            if (!stack.hasEffect(pass)) continue;
            GL11.glDepthFunc((int)514);
            GL11.glDisable((int)2896);
            RenderManager.instance.renderEngine.bindTexture(GLINT_TEXTURE);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)768, (int)1);
            float f7 = 0.76f;
            GL11.glColor4f((float)(0.5f * f7), (float)(0.25f * f7), (float)(0.8f * f7), (float)1.0f);
            GL11.glMatrixMode((int)5890);
            GL11.glPushMatrix();
            float f8 = 0.125f;
            GL11.glScalef((float)f8, (float)f8, (float)f8);
            float f9 = (float)(Minecraft.getSystemTime() % 3000) / 3000.0f * 8.0f;
            GL11.glTranslatef((float)f9, (float)0.0f, (float)0.0f);
            GL11.glRotatef((float)-50.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            ItemRenderer.renderItemIn2D((Tessellator)tessellator, (float)0.0f, (float)0.0f, (float)1.0f, (float)1.0f, (int)256, (int)256, (float)0.0625f);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef((float)f8, (float)f8, (float)f8);
            f9 = (float)(Minecraft.getSystemTime() % 4873) / 4873.0f * 8.0f;
            GL11.glTranslatef((float)(- f9), (float)0.0f, (float)0.0f);
            GL11.glRotatef((float)10.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            ItemRenderer.renderItemIn2D((Tessellator)tessellator, (float)0.0f, (float)0.0f, (float)1.0f, (float)1.0f, (int)256, (int)256, (float)0.0625f);
            GL11.glPopMatrix();
            GL11.glMatrixMode((int)5888);
            GL11.glDisable((int)3042);
            GL11.glEnable((int)2896);
            GL11.glDepthFunc((int)515);
        }
        GL11.glPopMatrix();
    }

    private void renderAsEntity(ItemStack stack, EntityItem entity) {
        GL11.glPushMatrix();
        int iterations = 1;
        if (stack.stackSize > 1) {
            iterations = 2;
        }
        if (stack.stackSize > 15) {
            iterations = 3;
        }
        if (stack.stackSize > 31) {
            iterations = 4;
        }
        Random rand = new Random(187);
        float offsetZ = 0.084375f;
        GL11.glRotatef((float)((((float)entity.age + 1.0f) / 20.0f + entity.hoverStart) * 57.295776f), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glTranslatef((float)-0.5f, (float)-0.25f, (float)(- offsetZ * (float)iterations / 2.0f));
        for (int count = 0; count < iterations; ++count) {
            if (count > 0) {
                float offsetX = (rand.nextFloat() * 2.0f - 1.0f) * 0.3f / 0.5f;
                float offsetY = (rand.nextFloat() * 2.0f - 1.0f) * 0.3f / 0.5f;
                float z = (rand.nextFloat() * 2.0f - 1.0f) * 0.3f / 0.5f;
                GL11.glTranslatef((float)offsetX, (float)offsetY, (float)offsetZ);
            } else {
                GL11.glTranslatef((float)0.0f, (float)0.0f, (float)offsetZ);
            }
            EmblemToolsClient.renderer.renderIn3D(stack, false);
        }
        GL11.glPopMatrix();
    }

    private void renderAsEntityFlat(ItemStack stack, EntityItem entity) {
        GL11.glPushMatrix();
        int iterations = 1;
        if (stack.stackSize > 1) {
            iterations = 2;
        }
        if (stack.stackSize > 15) {
            iterations = 3;
        }
        if (stack.stackSize > 31) {
            iterations = 4;
        }
        Random rand = new Random(187);
        for (int ii = 0; ii < iterations; ++ii) {
            GL11.glPushMatrix();
            GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            if (!RenderItem.renderInFrame) {
                GL11.glRotatef((float)(180.0f - RenderManager.instance.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
            }
            if (ii > 0) {
                float var12 = (rand.nextFloat() * 2.0f - 1.0f) * 0.3f;
                float var13 = (rand.nextFloat() * 2.0f - 1.0f) * 0.3f;
                float var14 = (rand.nextFloat() * 2.0f - 1.0f) * 0.3f;
                GL11.glTranslatef((float)var12, (float)var13, (float)var14);
            }
            GL11.glTranslatef((float)0.5f, (float)0.8f, (float)0.0f);
            GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glScalef((float)0.0625f, (float)0.0625f, (float)1.0f);
            this.render(IItemRenderer.ItemRenderType.ENTITY, stack);
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
    }

    private void render(IItemRenderer.ItemRenderType type, ItemStack stack) {
        GL11.glPushMatrix();
        GL11.glDisable((int)2896);
        String emblemIdentifier = EmblemToolsServer.getEmblemIdentifier((ItemStack)stack);
        if (emblemIdentifier != null && !emblemIdentifier.equals("")) {
            this.renderTextureObject(0, 0, internalGetLocation(emblemIdentifier), 16, 16);
        } else {
            IIcon icon = stack.getItem().getIconFromDamageForRenderPass(0, 0);
            renderItem.renderIcon(0, 0, icon, 16, 16);
        }
        GL11.glEnable((int)2896);
        GL11.glPopMatrix();
    }

    public void renderTextureObject(int x, int y, ResourceLocation texture, int width, int height) {
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + height), (double)RenderCustomEmblemItem.renderItem.zLevel, 0.0, 1.0);
        tessellator.addVertexWithUV((double)(x + width), (double)(y + height), (double)RenderCustomEmblemItem.renderItem.zLevel, 1.0, 1.0);
        tessellator.addVertexWithUV((double)(x + width), (double)(y + 0), (double)RenderCustomEmblemItem.renderItem.zLevel, 1.0, 0.0);
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)RenderCustomEmblemItem.renderItem.zLevel, 0.0, 0.0);
        tessellator.draw();
    }
    
    public ResourceLocation internalGetLocation(String identifier) {
        Texture texture = internalGetTexture(identifier);
        if (texture instanceof CustomEmblem.CustomTexture) {
            return ((CustomEmblem.CustomTexture) texture).getLocation();
        } else {
            try {
                Method locationMethod = texture.getClass().getMethod("getLocation");
                return (ResourceLocation) locationMethod.invoke(texture);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(CustomEmblemItemRenderer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(CustomEmblemItemRenderer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(CustomEmblemItemRenderer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(CustomEmblemItemRenderer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(CustomEmblemItemRenderer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return null;
    }

    public Texture internalGetTexture(String identifier) {
        return EmblemToolsClient.packageManager.getEmblemTexture(identifier);
    }
}