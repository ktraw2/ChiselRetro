package info.jbcs.minecraft.chisel;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.fybertech.chiselretro.IBlockTextures;
import net.fybertech.chiselretro.IRegisterIcons;
import net.fybertech.chiselretro.Icon;
import net.fybertech.chiselretro.IconRegister;
import net.fybertech.chiselretro.RetroUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMarbleStairs extends BlockStairs implements Carvable, IRegisterIcons, IBlockTextures 
{
	CarvableHelper carverHelper;
	int blockMeta;

	public BlockMarbleStairs(String name,int i, Block block,int meta, CarvableHelper helper) {
		super(name==null?i:Chisel.config.getBlock(name, i).getInt(i), block, meta);
		
		useNeighborBrightness[blockID]=true;
		setCreativeTab(Chisel.tabChisel);
		carverHelper=helper;
		blockMeta=meta;
	}

	@Override
	public int damageDropped(int i) {
		return i&0x8;
	}
	
    @Override
	public void getSubBlocks(int blockId, CreativeTabs tabs, List list){
		list.add(new ItemStack(blockID, 1, 0));
		list.add(new ItemStack(blockID, 1, 8));
    }
    
	@Override
	public int getRenderType() {
		return BlockMarbleStairsRenderer.id;
	}

    @Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
    {
    	ItemStack par6ItemStack = par5EntityLiving.getHeldItem();
    	
    	int l = MathHelper.floor_double((par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        int i1 = par1World.getBlockMetadata(par2, par3, par4) & 4;
        int odd=par6ItemStack.getItemDamage();

        if (l == 0)
        {
            RetroUtil.setBlockMetadataWithNotify(par1World, par2, par3, par4, 2 | i1 + odd, 2);
        }

        if (l == 1)
        {
        	RetroUtil.setBlockMetadataWithNotify(par1World, par2, par3, par4, 1 | i1 + odd, 2);
        }

        if (l == 2)
        {
        	RetroUtil.setBlockMetadataWithNotify(par1World, par2, par3, par4, 3 | i1 + odd, 2);
        }

        if (l == 3)
        {
        	RetroUtil.setBlockMetadataWithNotify(par1World, par2, par3, par4, 0 | i1 + odd, 2);
        }
    }
    

    @Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hx, float hy, float hz, int damage){
       // int res=super.onBlockPlaced();
    	return side != 0 && (side == 1 || hy <= 0.5D) ? damage : damage | 4;
    }

	@Override
	public CarvableVariation getVariation(int metadata) {
		return carverHelper.getVariation(metadata);
	}
	
	
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int metadata) {
		return carverHelper.getIcon(side, blockMeta+metadata/8);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) {		
		//if(blockMeta==0)
			carverHelper.registerIcons("Chisel",this,register);		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture_FromAtlas(IBlockAccess world, int x, int y, int z, int side){
    	return carverHelper.getBlockTexture(world, x, y, z, side);
    }	
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockTextureFromSideAndMetadata(int side, int metadata) {
		Icon icon = carverHelper.getIcon(side, blockMeta + (metadata >= 8 ? 1 : 0));		
		//System.out.println("getBlockTextureFromSideAndMetadata: " + icon);
		return icon != null ? icon.getTextureNum() : 0;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
	{		
		int metadata = world.getBlockMetadata(x,  y,  z);
		Icon icon = carverHelper.getIcon(side, blockMeta + (metadata >= 8 ? 1 : 0));  //carverHelper.getBlockTexture(world, x, y, z, side);
		//System.out.println("getBlockTexture: " + icon);
		return icon != null ? icon.getTextureNum() : 0;
	}
	
	
}
