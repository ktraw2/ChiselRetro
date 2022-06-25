package info.jbcs.minecraft.chisel;

import info.jbcs.minecraft.utilities.General;
import net.fybertech.chiselretro.IBlockTextures;
import net.fybertech.chiselretro.IRegisterIcons;
import net.fybertech.chiselretro.Icon;
import net.fybertech.chiselretro.IconRegister;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSpikes extends Block  implements IRegisterIcons, IBlockTextures {
	Icon	iconBase;
	Icon	iconSpike;

	public BlockSpikes(int id, Material mat) {
		super(id, Material.circuits);

		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f / 16, 1.0f);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
			return;
		
//		int damage = (int) (entity.motionY*10);
//		if(damage!=0)
//			System.out.println(damage);
		
		double dy=entity.posY-entity.prevPosY;
		if(dy!=0)
		System.out.println(dy);
		
//		System.out.println(entity.speedInAir);
		
		
		GeneralChiselClient.speedupPlayer(world, entity, Chisel.concreteVelocity);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}


    @Override
	public boolean renderAsNormalBlock() {
        return true;
    }
    
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType() {
		return BlockSpikesRenderer.id;
	}

	@Override
	public int damageDropped(int meta) {
		return 0;
	}

	
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) {
		iconBase = register.registerIcon(General.getName(this) + "base");
		iconSpike = register.registerIcon(General.getName(this) + "spike");
	}
	
	
	// TODO - Fyber Added
	CarvableHelper carverHelper = new CarvableHelper();	
	@Override
	public Icon getIcon(int side, int metadata) {
		return carverHelper.getIcon(side, metadata);
	}
	@Override
	public Icon getBlockTexture_FromAtlas(IBlockAccess world, int x, int y, int z, int side){
    	return carverHelper.getBlockTexture(world, x, y, z, side);
    }
}
