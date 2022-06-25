package info.jbcs.minecraft.chisel;

import info.jbcs.minecraft.utilities.General;

import java.util.List;

import net.fybertech.chiselretro.Icon;
import net.fybertech.chiselretro.RetroUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringTranslate;

public class ItemCarvable extends ItemBlock {
	int blockId;

	public ItemCarvable(int id) {
		super(id);
		setMaxDamage(0);
		setHasSubtypes(true);
		blockId=id+256;
	}

	@Override
	public int getMetadata(int i) {
		return i;
	}

	
	
	
    //@Override
	public Icon getIconFromDamage_FromAtlas(int damage) {
        return RetroUtil.getIcon(Block.blocksList[blockId], 2, damage);
    }
	
	
	@Override
	public int getIconFromDamage(int damage) {
		Icon icon = RetroUtil.getIcon(Block.blocksList[blockId], 2, damage);
		return icon != null ? icon.getTextureNum() : 0;
	}
	
	

    @Override
	public void addInformation(ItemStack stack, EntityPlayer player, List lines, boolean advancedTooltips) {
    	if(! Chisel.blockDescriptions) return;
    	
    	Item item=General.getItem(stack);
    	if(item==null) return;
    	
    	Block block=General.getBlock(item.itemID);
    	if(! (block instanceof Carvable)) return;
    	
    	Carvable carvable=(Carvable) block;
    	CarvableVariation var=carvable.getVariation(stack.getItemDamage());
    	if(var==null) return;
    	
    	lines.add(var.description);
    }

}
