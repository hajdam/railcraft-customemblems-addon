package mods.railcraft_cemblem.client.emblems;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nonnull;
import static mods.railcraft.common.core.RailcraftConstants.TEXTURE_FOLDER;

public class CustomEmblemUtils {

    public static final String EMBLEMS_TEXTURE_FOLDER = TEXTURE_FOLDER + "emblems/";
    public static final String EMBLEMS_TEXTURE_LOCAL_FOLDER = "/assets/railcraft_cemblem/textures/emblems/";
    public static final String CUSTOM_PREFIX = "cust_";
    public static final String PROPERTY_PREFIX = "emblem";
    public static final String HIDDEN_PREFIX = ".";
    public static final int CUSTOM_PREFIX_LENGTH = CUSTOM_PREFIX.length();

    private CustomEmblemUtils() {
    }

    public static boolean isCustom(String ident) {
        return ident != null && ident.startsWith(CUSTOM_PREFIX);
    }

    public static String getCustomCode(String ident) {
        return ident != null && ident.length() > CUSTOM_PREFIX_LENGTH ? ident.substring(CUSTOM_PREFIX_LENGTH) : "";
    }

    public static void registerCustomEmblems(Map<String, CustomEmblem> customEmblems) {
        Properties customEmblemsProperties = new Properties();
        InputStream propertiesFileStream = CustomEmblemPackageManager.class.getResourceAsStream(CustomEmblemUtils.EMBLEMS_TEXTURE_LOCAL_FOLDER + "custom.properties");
        try {
            customEmblemsProperties.load(propertiesFileStream);
            propertiesFileStream.close();
        } catch (IOException ex) {
            Logger.getLogger(CustomEmblemPackageManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        int i = 0;
        do {
            String emblemName = customEmblemsProperties.getProperty(PROPERTY_PREFIX + i + "_name");
            if (emblemName == null || emblemName.isEmpty()) {
                break;
            }
            String emblemCode = customEmblemsProperties.getProperty(PROPERTY_PREFIX + i + "_code");
            String emblemImage = customEmblemsProperties.getProperty(PROPERTY_PREFIX + i + "_image");
            customEmblems.put(emblemCode, new CustomEmblem(emblemCode, emblemName, emblemName, 0, false, emblemImage));
            i++;
        } while (i < 1000);
    }

    @Nonnull
    public static List<String> getCraftableCustomEmblemIds() {
        List<String> customEmblemIds = new ArrayList<String>();
        Properties customEmblemsProperties = new Properties();
        InputStream propertiesFileStream = CustomEmblemUtils.class.getResourceAsStream(EMBLEMS_TEXTURE_LOCAL_FOLDER + "custom.properties");
        try {
            customEmblemsProperties.load(propertiesFileStream);
            propertiesFileStream.close();
        } catch (IOException ex) {
            Logger.getLogger(CustomEmblemUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        int i = 0;
        do {
            String emblemName = customEmblemsProperties.getProperty(PROPERTY_PREFIX + i + "_name");
            if (emblemName == null || emblemName.isEmpty()) {
                break;
            }
            String emblemCode = customEmblemsProperties.getProperty(PROPERTY_PREFIX + i + "_code");
            if (!emblemCode.startsWith(HIDDEN_PREFIX)) {
                customEmblemIds.add(CUSTOM_PREFIX + emblemCode);
            }
            i++;
        } while (i < 1000);

        return customEmblemIds;
    }
}
