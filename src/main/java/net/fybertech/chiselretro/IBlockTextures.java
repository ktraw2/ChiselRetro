package net.fybertech.chiselretro;

import net.minecraft.world.IBlockAccess;

public interface IBlockTextures 
{
	public Icon getIcon(int side, int metadata);
	
	public Icon getBlockTexture_FromAtlas(IBlockAccess world, int x, int y, int z, int side);
}
