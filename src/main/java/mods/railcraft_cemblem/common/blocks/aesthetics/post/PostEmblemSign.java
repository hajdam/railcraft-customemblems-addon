package mods.railcraft_cemblem.common.blocks.aesthetics.post;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class PostEmblemSign {
    
    private static IIcon texture;

    private PostEmblemSign() {
    }
    
    public static void setTexture(IIcon texture) {
        PostEmblemSign.texture = texture;
    }
    
    public static IIcon getIcon() {
        return texture;
    }

    public static ItemStack getItem() {
        return getItem(1);
    }

    public static boolean isEnabled() {
        return true;
    }

    public static ItemStack getItem(int qty) {
        if (!isEnabled())
            return null;
        return new ItemStack(BlockPostEmblemSign.block, qty, 0);
    }

    public static String getTag() {
        return "tile.railcraft_cemblem.post.emblem_sign";
    }
}
