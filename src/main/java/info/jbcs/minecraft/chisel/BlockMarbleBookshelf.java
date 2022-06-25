package info.jbcs.minecraft.chisel;

import java.util.Random;

import net.fybertech.chiselretro.Icon;
import net.fybertech.chiselretro.RetroUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMarbleBookshelf extends BlockMarble 
{

	public BlockMarbleBookshelf(int i) {
		super(i);
	}


	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int metadata) {
		//if(side<2)
//			return RetroUtil.getBlockTextureFromSide_FromAtlas(Block.planks, side);
		return super.getIcon(side, metadata);
	}

    @Override
    @SideOnly(Side.CLIENT)
	public Icon getBlockTexture_FromAtlas(IBlockAccess world, int x, int y, int z, int side){
		//if(side<2)
			//return RetroUtil.getBlockTextureFromSide_FromAtlas(Block.planks, side);
    	return super.getBlockTexture_FromAtlas(world, x, y, z, side);
    }

	@Override
	public int quantityDropped(Random par1Random) {
		return 3;
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return Item.book.itemID;
	}

	// TODO - Fyber - Doesn't exist in 1.4.7
	/*@Override
	public float getEnchantPowerBonus(World world, int x, int y, int z) {
		return 1;
	}*/
}
