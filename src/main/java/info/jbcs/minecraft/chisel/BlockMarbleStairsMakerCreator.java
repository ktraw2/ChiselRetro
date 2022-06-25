package info.jbcs.minecraft.chisel;

import net.minecraft.block.Block;

public interface BlockMarbleStairsMakerCreator {
	public BlockMarbleStairs create(String name,int i, Block block,int meta, CarvableHelper helper);
}
