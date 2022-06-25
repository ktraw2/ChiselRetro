package info.jbcs.minecraft.chisel;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.fybertech.chiselretro.IBlockTextures;
import net.fybertech.chiselretro.IRegisterIcons;
import net.fybertech.chiselretro.Icon;
import net.fybertech.chiselretro.IconRegister;
import net.minecraft.block.BlockIce;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMarbleIce extends BlockIce implements Carvable, IRegisterIcons, IBlockTextures {
	CarvableHelper carverHelper;

	public BlockMarbleIce(int i) {
		super(i, 0);

		carverHelper = new CarvableHelper();

		setCreativeTab(Chisel.tabChisel);
	}	

	@Override
	public int damageDropped(int i) {
		return 0;
	}
	
	@Override
	public void getSubBlocks(int blockId, CreativeTabs tabs, List list) {
		carverHelper.registerSubBlocks(this, tabs, list);
	}

	/**
	 * Called when the player destroys a block with an item that can harvest it.
	 * (i, j, k) are the coordinates of the block and l is the block's
	 * subtype/damage.
	 */
	@Override
	public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6) {
		if (!Chisel.dropIceShards){
			super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
			return;
		}
		
        par2EntityPlayer.addStat(StatList.mineBlockStatArray[this.blockID], 1);
        par2EntityPlayer.addExhaustion(0.025F);
        
        if(par1World.isRemote)
        	return;

		if (this.canSilkHarvest(par1World, par2EntityPlayer, par3, par4, par5, par6) && EnchantmentHelper.getSilkTouchModifier(par2EntityPlayer)) {
			ItemStack itemstack = this.createStackedBlock(par6);

			if (itemstack != null) {
				this.dropBlockAsItem_do(par1World, par3, par4, par5, itemstack);
			}
		} else {
			int i1 = EnchantmentHelper.getFortuneModifier(par2EntityPlayer);
			this.dropBlockAsItem(par1World, par3, par4, par5, par6, i1);
		}
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return Chisel.dropIceShards ? Chisel.itemIceshard.itemID : 0;
	}

	@Override
	public int quantityDropped(Random par1Random) {
		return Chisel.dropIceShards ? 1 + par1Random.nextInt(5) : 0;
	}

	@Override
	public CarvableVariation getVariation(int metadata) {
		return carverHelper.getVariation(metadata);
	}
	
	
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture_FromAtlas(IBlockAccess world, int x, int y, int z, int side){
    	return carverHelper.getBlockTexture(world, x, y, z, side);
    }

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int metadata) {
		return carverHelper.getIcon(side, metadata);
	}
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockTextureFromSideAndMetadata(int side, int metadata)
	{
		Icon icon = carverHelper.getIcon(side,  metadata);
		return icon != null ? icon.getTextureNum() : 0;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) {
		carverHelper.registerIcons("Chisel", this, register);
	}

}
