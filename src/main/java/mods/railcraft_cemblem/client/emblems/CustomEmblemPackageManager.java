package mods.railcraft_cemblem.client.emblems;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import mods.railcraft.client.emblems.IEmblemPackageManager;
import mods.railcraft.client.emblems.EmblemPackageManager;
import mods.railcraft.client.emblems.EmblemToolsClient;
import mods.railcraft.client.emblems.Emblem;
import mods.railcraft.client.util.textures.Texture;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

/**
 * Package manager for custom emblems.
 */
public class CustomEmblemPackageManager implements IEmblemPackageManager {

    private Map<String, CustomEmblem> customEmblems = null;
    private final Set<String> loadedTextures = new HashSet<String>();

    private final EmblemPackageManager parentManager;
    public static CustomEmblemPackageManager instance;

    public CustomEmblemPackageManager(EmblemPackageManager parentManager) {
        this.parentManager = parentManager;
    }

    public static void register() {
        instance = new CustomEmblemPackageManager((EmblemPackageManager) EmblemToolsClient.packageManager);
        EmblemToolsClient.packageManager = instance;
    }

    @Override
    public Texture getEmblemTexture(String ident) {
        if (CustomEmblemUtils.isCustom(ident)) {
            String customCode = CustomEmblemUtils.getCustomCode(ident);
            CustomEmblem customEmblem = getCustomEmblems().get(customCode);
            if (customEmblem != null) {
                if (!loadedTextures.contains(customCode)) {
                    Minecraft.getMinecraft().renderEngine.loadTexture(customEmblem.getTextureLocation(), customEmblem.getTexture());
                    loadedTextures.add(customCode);
                }

                return customEmblem.getTexture();
            }
        }

        return parentManager.getEmblemTexture(ident);
    }

    @Nullable
    @Override
    public ResourceLocation getEmblemTextureLocation(String ident) {
        if (CustomEmblemUtils.isCustom(ident)) {
            String customCode = CustomEmblemUtils.getCustomCode(ident);
            CustomEmblem customEmblem = getCustomEmblems().get(customCode);
            if (customEmblem != null) {
                if (!loadedTextures.contains(customCode)) {
                    Minecraft.getMinecraft().renderEngine.loadTexture(customEmblem.getTextureLocation(), customEmblem.getTexture());
                    loadedTextures.add(customCode);
                }
                return customEmblem.getTextureLocation();
            }
        }

        return parentManager.getEmblemTextureLocation(ident);
    }

    @Override
    public Emblem getEmblem(String ident) {
        if (CustomEmblemUtils.isCustom(ident)) {
            String customCode = CustomEmblemUtils.getCustomCode(ident);
            CustomEmblem custom = getCustomEmblems().get(customCode);
            return custom;
        }

        return parentManager.getEmblem(ident);
    }

    private Map<String, CustomEmblem> getCustomEmblems() {
        if (customEmblems == null) {
            customEmblems = new HashMap<String, CustomEmblem>();
            CustomEmblemUtils.registerCustomEmblems(customEmblems);
        }

        return customEmblems;
    }
}
