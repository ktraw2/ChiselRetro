package info.jbcs.minecraft.chisel;

import net.fybertech.chiselretro.IItemTexture;
import net.fybertech.chiselretro.IRegisterIcons;
import net.fybertech.chiselretro.Icon;
import net.fybertech.chiselretro.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCloudInABottle extends Item implements IRegisterIcons, IItemTexture 
{
	String textureName;
	Icon icon;
	
	
	public ItemCloudInABottle(int par1) {
		super(par1);
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if (!par3EntityPlayer.capabilities.isCreativeMode) {
			--par1ItemStack.stackSize;
		}

		par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!par2World.isRemote) {
			par2World.spawnEntityInWorld(new EntityCloudInABottle(par2World,par3EntityPlayer));
		}

		return par1ItemStack;
	}
	
	
	@Override
	public Icon getIcon()
	{
		return icon;		
	}
	
	
	@Override
	public int getIconFromDamage(int par1) {
		return icon.getTextureNum();
	}
	
	@Override
	public void setTextureName(String name) {
		this.textureName = name;		
	}

	@Override
	public void registerIcons(IconRegister register) {
		icon = register.registerIcon(textureName);		
	}
}
