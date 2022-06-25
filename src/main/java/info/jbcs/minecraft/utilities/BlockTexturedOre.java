package info.jbcs.minecraft.utilities;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import info.jbcs.minecraft.chisel.CarvableHelper;
import net.fybertech.chiselretro.IBlockTextures;
import net.fybertech.chiselretro.IRegisterIcons;
import net.fybertech.chiselretro.Icon;
import net.fybertech.chiselretro.IconRegister;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

public class BlockTexturedOre extends Block implements IRegisterIcons, IBlockTextures
{
	public int currentPass;
	Block	base;
	Icon	icon;
	String	iconFile;

	public BlockTexturedOre(int id, Material mat, Block base) {
		super(id, mat);

		this.base = base;
	}

	public BlockTexturedOre(int id, Material mat, String iconFile) {
		super(id, mat);

		this.iconFile=iconFile;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType() {
		return BlockTexturedOreRenderer.id;
	}

	@Override
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public boolean canRenderInPass(int pass) {
		currentPass = pass;

		return pass == 1 || pass == 0;
	}
	
	
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) {
		if(iconFile!=null)
			icon=register.registerIcon(iconFile);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int metadata) {
		return icon;
	}	
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture_FromAtlas(IBlockAccess world, int x, int y, int z, int side){
    	return icon;
    }

}
