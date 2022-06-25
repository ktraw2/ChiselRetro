package info.jbcs.minecraft.chisel;

import info.jbcs.minecraft.utilities.General;
import info.jbcs.minecraft.utilities.packets.PacketData;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import net.fybertech.chiselretro.IItemTexture;
import net.fybertech.chiselretro.IRegisterIcons;
import net.fybertech.chiselretro.Icon;
import net.fybertech.chiselretro.IconRegister;
import net.fybertech.chiselretro.RetroUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;

public class ItemChisel extends ItemTool implements IRegisterIcons, IItemTexture
{
	Random random=new Random();
	Carving carving;
	public String textureName;
	public Icon icon;
	
	
	public ItemChisel(int id,Carving c) {
		super(id,1,EnumToolMaterial.IRON,CarvableHelper.chiselBlocks.toArray(new Block[CarvableHelper.chiselBlocks.size()]));

		maxStackSize = 1;
		setMaxDamage(500);
		efficiencyOnProperMaterial=100f;
		
		carving=c;
		
        MinecraftForge.setToolClass(this,"chisel",1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		entityplayer.openGui(Chisel.instance, 0, world, 0, 0, 0);
		
		return itemstack;
	}

	
    @Override
	public void registerIcons(IconRegister register)
    {
    	icon = register.registerIcon(textureName);
    }
    
    
    @Override
    public int getIconFromDamage(int par1) {
    	return icon.getTextureNum();
    }
    
	
	@Override
	public String getTextureFile() {
		return super.getTextureFile();
	}

	
    HashMap<String,Long> chiselUseTime=new HashMap<String,Long>();
    HashMap<String,String> chiselUseLocation=new HashMap<String,String>();
	
    @Override
	public boolean onBlockStartBreak(ItemStack stack, final int x, final int y, final int z, EntityPlayer player){
		World world=player.worldObj;
		int blockId=world.getBlockId(x, y, z);
		int blockMeta=world.getBlockMetadata(x,y,z);
		
		if(! ForgeHooks.isToolEffective(stack, Block.blocksList[blockId], blockMeta))
			return false;
		
		ItemStack chiselTarget=null;
		
		if(stack.stackTagCompound!=null){
			chiselTarget=ItemStack.loadItemStackFromNBT(stack.stackTagCompound.getCompoundTag("chiselTarget"));
		}

		boolean chiselHasBlockInside=true;
		
		if(chiselTarget==null){
			Long useTime=chiselUseTime.get(player.username);
			String loc=chiselUseLocation.get(player.username);
			
			if(useTime!=null && chiselUseLocation!=null && loc.equals(x+"|"+y+"|"+z)){
				long cooldown=20;
				long time=world.getWorldInfo().getWorldTotalTime();
				
				if(time>useTime-cooldown && time<useTime+cooldown)
					return true;					
				
			}
			
			CarvingVariation[] variations=carving.getVariations(blockId, blockMeta);
			if(variations==null || variations.length<2) return true;
			
			int index=random.nextInt(variations.length-1);
			if(variations[index].blockId==blockId && variations[index].meta==blockMeta){
				index++;
				if(index>=variations.length) index=0;
			}
			CarvingVariation var=variations[index];
			chiselTarget=new ItemStack(var.blockId,1,var.damage);
			
			chiselHasBlockInside=false;
		}
		
		int targetId=chiselTarget.itemID;
		int targetMeta=chiselTarget.getItemDamage();

		boolean match=carving.isVariationOfSameClass(targetId,targetMeta,blockId,blockMeta);
		int resultId=targetId;
		
		
		/* special case: stone can be carved to cobble and bricks */
		if(!match && blockId==Block.stone.blockID && targetId==Chisel.blockCobblestone.blockID)
			match=true;
		if(!match && blockId==Block.stone.blockID && targetId==Chisel.stoneBrick.blockID)
			match=true;
		
		if(!match) return true;
		if(resultId==blockId && targetMeta == blockMeta) return true;
		
		if(! world.isRemote || chiselHasBlockInside) 
			RetroUtil.setBlock(world, x, y, z, resultId, chiselTarget.getItemDamage(), 2);
		
		switch(FMLCommonHandler.instance().getEffectiveSide()){
		case SERVER:
			chiselUseTime.put(player.username, world.getWorldInfo().getWorldTotalTime());
			chiselUseLocation.put(player.username,x+"|"+y+"|"+z);
			
			ServerConfigurationManager mgr = MinecraftServer.getServer().getConfigurationManager();
			for (int j = 0; j < mgr.playerEntityList.size(); ++j) {
				EntityPlayerMP p = (EntityPlayerMP) mgr.playerEntityList.get(j);

				if (p.dimension != player.dimension) continue;
				if (p==player && chiselHasBlockInside) continue;
				if (! General.isInRange(30.0f, p.posX, p.posY, p.posZ, x, y, z)) continue;

				Packets.chiseled.sendToPlayer(p,new PacketData(){
					@Override
					public void data(DataOutputStream stream) throws IOException {
						stream.writeInt(x);
						stream.writeInt(y);
						stream.writeInt(z);
					}
				});
			}
			break;
			
		case CLIENT:
			if(chiselHasBlockInside){
				String sound=carving.getVariationSound(resultId, chiselTarget.getItemDamage());
				GeneralChiselClient.spawnChiselEffect(x, y, z, sound);
			}
			break;
			
		default:
			break;
		} 
		
		stack.damageItem(1, player);
	    if(stack.stackSize==0){
	    	player.inventory.mainInventory[player.inventory.currentItem]=
	    		chiselHasBlockInside?
	    		chiselTarget:
	    		null;
	    }
	    
		return true;
    }

	@Override
	public void setTextureName(String name) 
	{
		this.textureName = name;		
	}
	
	@Override
	public Icon getIcon()
	{
		return icon;
	}
	
}
