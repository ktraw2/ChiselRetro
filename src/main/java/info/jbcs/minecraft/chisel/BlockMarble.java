package info.jbcs.minecraft.chisel;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.fybertech.chiselretro.IBlockTextures;
import net.fybertech.chiselretro.IRegisterIcons;
import net.fybertech.chiselretro.Icon;
import net.fybertech.chiselretro.IconRegister;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.IBlockAccess;

public class BlockMarble extends Block implements Carvable, IRegisterIcons, IBlockTextures 
{
	CarvableHelper carverHelper;

	public BlockMarble(int i) {
		this(null, i, Material.rock);
	}

	public BlockMarble(String name,int i) {
		this(name, i, Material.rock);
	}

	public BlockMarble(int i, Material m) {
		this(null, i, m);
	}
	
	public BlockMarble(String name,int i, Material m) 
	{
		super(name==null?i:Chisel.config.getBlock(name, i).getInt(i), m);

		carverHelper = new CarvableHelper();

		setCreativeTab(Chisel.tabChisel);
	}
	
	@Override
	public int damageDropped(int i) {
		return i;
	}
	
    @Override
	public void getSubBlocks(int blockId, CreativeTabs tabs, List list) {
		carverHelper.registerSubBlocks(this,tabs,list);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType() {
		return Chisel.RenderCTMId;
	}

	@Override
	public CarvableVariation getVariation(int metadata) {
		return carverHelper.getVariation(metadata);
	}
	
	
	
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) 
	{
		carverHelper.registerIcons("Chisel",this,register);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int metadata) 
	{
		return carverHelper.getIcon(side, metadata);
	}

    @Override
    @SideOnly(Side.CLIENT)
	public Icon getBlockTexture_FromAtlas(IBlockAccess world, int x, int y, int z, int side)
    {
    	return carverHelper.getBlockTexture(world, x, y, z, side);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockTextureFromSideAndMetadata(int side, int metadata) 
	{
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


	
}
