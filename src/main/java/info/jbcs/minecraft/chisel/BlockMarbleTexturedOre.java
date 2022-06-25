package info.jbcs.minecraft.chisel;

import info.jbcs.minecraft.utilities.BlockTexturedOre;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.fybertech.chiselretro.Icon;
import net.fybertech.chiselretro.IconRegister;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.IBlockAccess;

public class BlockMarbleTexturedOre extends BlockTexturedOre implements Carvable 
{
	CarvableHelper carverHelper;

	public BlockMarbleTexturedOre(String name,int i, Material mat, String baseIcon) 
	{
		super(name==null?i:Chisel.config.getBlock(name, i).getInt(i), mat, baseIcon);
		
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
	public void registerIcons(IconRegister register) {
		super.registerIcons(register);

		carverHelper.registerIcons("Chisel",this,register);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
	{
		Icon icon = carverHelper.getBlockTexture(world, x, y, z, side);
		return icon != null ? icon.getTextureNum() : 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockTextureFromSideAndMetadata(int side, int metadata)
	{
		Icon icon = carverHelper.getIcon(side, metadata);
		return icon != null ? icon.getTextureNum() : 0;
	}
    
}
