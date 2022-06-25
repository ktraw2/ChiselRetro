package info.jbcs.minecraft.chisel;


import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockLavastone extends BlockMarbleTexturedOre 
{
	public BlockLavastone(String name, int i, Material mat, String baseIcon) 
	{
		super(name, i, mat, baseIcon);
	}

	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random random) 
	{
		if (random.nextInt(8) == 0)
			GeneralChiselClient.spawnLavastoneFX(world,this,x,y,z);
	}
	
	
	// Use a different texture in pass 0 so that we can use vanilla's lava texture
	// on the inner part of the block.
	@Override
	public String getTextureFile() {
		if (currentPass == 0) return "/terrain.png";
		else return super.getTextureFile();
	}

}
