package info.jbcs.minecraft.chisel;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.fybertech.chiselretro.IBlockTextures;
import net.fybertech.chiselretro.IRegisterIcons;
import net.fybertech.chiselretro.Icon;
import net.fybertech.chiselretro.IconRegister;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.IBlockAccess;

public class BlockMarbleWall extends BlockWall  implements IRegisterIcons, IBlockTextures {
	CarvableHelper carverHelper;

	public BlockMarbleWall(int id, Block block) {
		super(id, block);
		
		carverHelper = new CarvableHelper();

		setCreativeTab(Chisel.tabChisel);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int metadata) {
		return carverHelper.getIcon(side, metadata);
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) {
		carverHelper.registerIcons("Chisel",this,register);
	}

    @Override
	public void getSubBlocks(int blockId, CreativeTabs tabs, List list){
		carverHelper.registerSubBlocks(this,tabs,list);
    }
    
    
    
    @Override
    @SideOnly(Side.CLIENT)
	public Icon getBlockTexture_FromAtlas(IBlockAccess world, int x, int y, int z, int side){
    	return carverHelper.getBlockTexture(world, x, y, z, side);
    }
}
