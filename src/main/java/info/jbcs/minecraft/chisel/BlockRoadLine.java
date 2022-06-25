package info.jbcs.minecraft.chisel;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.fybertech.chiselretro.IBlockTextures;
import net.fybertech.chiselretro.IRegisterIcons;
import net.fybertech.chiselretro.Icon;
import net.fybertech.chiselretro.IconRegister;
import net.fybertech.chiselretro.RetroUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRoadLine extends Block  implements IRegisterIcons, IBlockTextures
{
	// TODO - Fyber - added
	Icon blockIcon;
	
	Icon aloneIcon;
	Icon halfLineIcon;
	Icon fullLineIcon;
	
	public BlockRoadLine(String name, int i) {
		super(name == null ? i : Chisel.config.getBlock(name, i).getInt(i), Material.circuits);

		this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.00390625f, 1.0f);
//        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);

		setCreativeTab(Chisel.tabChisel);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		return null;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType() {
		return BlockRoadLineRenderer.id;
	}

	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
		return par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) || par1World.getBlockId(par2, par3 - 1, par4) == Block.glowStone.blockID;
	}

	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
		if (par1World.isRemote) return;

		if (! this.canPlaceBlockAt(par1World, par2, par3, par4)) {
			this.dropBlockAsItem(par1World, par2, par3, par4, 0, 0);
			RetroUtil.setBlockToAir(par1World, par2, par3, par4);
		}

		super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
	}
	
	
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		blockIcon = aloneIcon = reg.registerIcon("Chisel:line-marking/white-center");
		halfLineIcon = reg.registerIcon("Chisel:line-marking/white-side");
		fullLineIcon = reg.registerIcon("Chisel:line-marking/white-long");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int metadata) {
		return blockIcon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture_FromAtlas(IBlockAccess world, int x, int y, int z, int side){
    	return blockIcon;
    }	
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockTextureFromSideAndMetadata(int side, int metadata) {
		Icon icon = blockIcon;		
		//System.out.println("getBlockTextureFromSideAndMetadata: " + icon);
		return icon != null ? icon.getTextureNum() : 0;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
	{		
		Icon icon = blockIcon;
		//System.out.println("getBlockTexture: " + icon);
		return icon != null ? icon.getTextureNum() : 0;
	}
}
