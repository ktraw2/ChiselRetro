package info.jbcs.minecraft.chisel.item;

import lombok.Getter;
import lombok.Setter;
import net.fybertech.chiselretro.IItemTexture;
import net.fybertech.chiselretro.IRegisterIcons;
import net.fybertech.chiselretro.Icon;
import net.fybertech.chiselretro.IconRegister;
import net.minecraft.item.Item;

public class BasicTexturedItem extends Item implements IRegisterIcons, IItemTexture {
    @Setter
    private String textureName;

    @Getter
    private Icon icon;

    public BasicTexturedItem(
            final int id,
            final String textureName
    ) {
        super(id);
        this.textureName = textureName;
    }

    @Override
    public int getIconFromDamage(int par1) {
        return icon.getTextureNum();
    }

    @Override
    public void registerIcons(final IconRegister register) {
        icon = register.registerIcon(textureName);
    }
}
