package mods.railcraft_cemblem.common.gui;

public enum EnumGui {

    CUSTOM_ENGRAVING_BENCH(true);

    private final boolean hasContainer;

    EnumGui(boolean hasContainer) {
        this.hasContainer = hasContainer;
    }

    public boolean hasContainer() {
        return hasContainer;
    }

    public static EnumGui fromOrdinal(int i) {
        return values()[i];
    }
}
