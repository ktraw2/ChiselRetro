package net.fybertech.chiselretro;

import info.jbcs.minecraft.chisel.ItemBallOMoss;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class RetroUtil {

	public static Icon getBlockTextureFromSide_FromAtlas(Block block, int side)
	{
		//System.out.println("getBlockTextureFromSide_FromAtlas " + block.getBlockName() + " " + side + " " + block);		
		
		if (block instanceof IBlockTextures) {
			return ((IBlockTextures)block).getIcon(side, 0);
		}
		
		// TODO
		return null;
	}

	public static Icon getIcon(Block block, int side, int meta) 
	{
		//System.out.println("getIcon " + block);
		
		if (block instanceof IBlockTextures) {
			return ((IBlockTextures)block).getIcon(side,  meta);
		}
		
		//System.out.println("getIcon failed: " + block);
		
		// TODO
		return null;
	}

	public static void setBlock(World world, int x, int y, int z, int blockID, int meta, int flags) {
		// TODO Auto-generated method stub
		System.out.println("setBlock");
		
		world.setBlockAndMetadataWithUpdate(x,  y,  z,  blockID,  meta,  true);		
	}

	public static Icon getIconFromDamage_FromAtlas(Item item, int damage) 
	{
		IconRegister register = IconRegister.getRegister(item.getTextureFile());
		if (register == null) return null;	
		
		return register.idToIcon.get(item.getIconFromDamage(damage));
	}

	public static void setBlockMetadataWithNotify(World world, int x, int y, int z, int meta, int flags) {
		// TODO Auto-generated method stub
		//System.out.println("setBlockMetadataWithNotify");
		world.setBlockMetadataWithNotify(x,  y,  z, meta);
	}

	public static void setBlockToAir(World world, int x, int y, int z) {
		// TODO Auto-generated method stub
		world.setBlock(x,  y,  z, 0);
	}

	public static void registerIcons(Block block, IconRegister register) {
		// TODO - Fyber - This likely pulls only vanilla icons
		
		//System.out.println("registerIcons " + block.getBlockName() + " " + block);
	}
	
}
