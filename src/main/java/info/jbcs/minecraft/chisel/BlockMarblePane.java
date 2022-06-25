package info.jbcs.minecraft.chisel;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.fybertech.chiselretro.IBlockTextures;
import net.fybertech.chiselretro.IRegisterIcons;
import net.fybertech.chiselretro.Icon;
import net.fybertech.chiselretro.IconRegister;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.IBlockAccess;

public class BlockMarblePane extends BlockPane implements Carvable, IRegisterIcons, IBlockTextures {
	CarvableHelper carverHelper;

	protected BlockMarblePane(int id, Material material, boolean drops) {
		super(id, 0, 0, material, drops);
		
		carverHelper = new CarvableHelper();

		setCreativeTab(Chisel.tabChisel);
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
	public int getRenderType() {
		return BlockMarblePaneRenderer.id;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int metadata) {
		return carverHelper.getIcon(side, metadata);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) {
		carverHelper.registerIcons("Chisel",this,register);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockTextureFromSideAndMetadata(int side, int metadata) {
		Icon icon = carverHelper.getIcon(side, metadata);		
		//System.out.println("getBlockTextureFromSideAndMetadata: " + icon);
		return icon != null ? icon.getTextureNum() : 0;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
	{		
		Icon icon = carverHelper.getBlockTexture(world, x, y, z, side);
		//System.out.println("getBlockTexture: " + icon);
		return icon != null ? icon.getTextureNum() : 0;
	}	
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture_FromAtlas(IBlockAccess world, int x, int y, int z, int side){
		//System.out.println("getBlockTexture_FromAtlas");
    	return carverHelper.getBlockTexture(world, x, y, z, side);
    }
}
