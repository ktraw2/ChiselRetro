package info.jbcs.minecraft.chisel;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.fybertech.chiselretro.IBlockTextures;
import net.fybertech.chiselretro.IRegisterIcons;
import net.fybertech.chiselretro.Icon;
import net.fybertech.chiselretro.IconRegister;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.IBlockAccess;

public class BlockGlassCarvable extends BlockGlass implements Carvable, IRegisterIcons, IBlockTextures {
	CarvableHelper carverHelper;

	public BlockGlassCarvable(int i) {
		super(i, 0, Material.glass, false);  // TODO - FYBER - Added 0 for texture

		carverHelper = new CarvableHelper();

		setCreativeTab(Chisel.tabChisel);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return Chisel.RenderCTMId;
	}	
	
	@Override
	public int damageDropped(int i) {
		return i;
	}
	
    @Override
	public void getSubBlocks(int blockId, CreativeTabs tabs, List list){
		carverHelper.registerSubBlocks(this,tabs,list);
    }

	@Override
	public CarvableVariation getVariation(int metadata) {
		return carverHelper.getVariation(metadata);
	}
    
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int metadata) {
		return carverHelper.getIcon(side, metadata);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockTextureFromSideAndMetadata(int side, int metadata)
	{
		Icon icon = carverHelper.getIcon(side, metadata);
		return icon != null ? icon.getTextureNum() : 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) {
		carverHelper.registerIcons("Chisel",this,register);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture_FromAtlas(IBlockAccess world, int x, int y, int z, int side){
    	return carverHelper.getBlockTexture(world, x, y, z, side);
    }
}
