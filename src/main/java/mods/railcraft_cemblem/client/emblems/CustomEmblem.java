package mods.railcraft_cemblem.client.emblems;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import mods.railcraft.client.emblems.Emblem;
import mods.railcraft.client.util.textures.Texture;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

public class CustomEmblem extends Emblem {

    private String emblemPicture;
    private CustomTexture cachedTexture;

    public CustomEmblem(String ident, String text, String display, int rarity, boolean hasEffect, String emblemPicture) {
        super(ident, text, display, rarity, hasEffect);
        this.emblemPicture = emblemPicture;
    }

    public CustomTexture getTexture() {
        if (cachedTexture == null) {
            cachedTexture = new CustomTexture(this);
        }
        return cachedTexture;
    }

    public ResourceLocation getTextureLocation() {
        return new ResourceLocation(CustomEmblemUtils.EMBLEMS_TEXTURE_FOLDER + emblemPicture);
    }

    public static class CustomTexture extends Texture {

        private final CustomEmblem emblem;
        private boolean cachedImage = false;

        public CustomTexture(CustomEmblem emblem) {
            this.emblem = emblem;
        }

        @Override
        public void loadTexture(IResourceManager p_110551_1_) {
            try {
                InputStream istream = CustomEmblemPackageManager.class.getResourceAsStream(CustomEmblemUtils.EMBLEMS_TEXTURE_LOCAL_FOLDER + emblem.emblemPicture);
                if (istream == null) {
                    return;
                }

                imageData = ImageIO.read(istream);
                istream.close();
                // func_71410_x().field_71446_o.func_110579_a(getLocation(), (ITextureObject)this);
            } catch (IOException ex) {
                Logger.getLogger(CustomEmblemPackageManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }

        public ResourceLocation getLocation() {
            checkLoaded();
            return emblem.getTextureLocation();
        }

        @Override
        public int getGlTextureId() {
            int textureIndex = super.getGlTextureId();
            if (!this.cachedImage && this.imageData != null) {
                TextureUtil.uploadTextureImage((int) textureIndex, (BufferedImage) this.imageData);
                this.cachedImage = true;
            }
            return textureIndex;
        }

        @Override
        public BufferedImage getImage() {
            checkLoaded();
            return imageData;
        }

        public void checkLoaded() {
            if (imageData == null) {
                loadTexture(null);
            }
        }
    }
}
