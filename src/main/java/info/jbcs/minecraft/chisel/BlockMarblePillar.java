package info.jbcs.minecraft.chisel;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.fybertech.chiselretro.Icon;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

public class BlockMarblePillar extends BlockMarble{
	Icon sides[]=new Icon[6];
	
	public BlockMarblePillar(String name, int i, Material m) {
		super(name,i,m);
	}
	
	public BlockMarblePillar(int i, Material m) {
		super(i,m);
	}

	@Override
	public int getRenderType() {
		return BlockMarblePillarRenderer.id;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int metadata) {
		return sides[side];
	}
	
    @Override
    @SideOnly(Side.CLIENT)
	public Icon getBlockTexture_FromAtlas(IBlockAccess world, int x, int y, int z, int side){
    	return sides[side];
    }

	public Icon getCtmIcon(int index, int metadata){
		CarvableVariation var=carverHelper.variations.get(metadata);
		
		if(index>=4) return var.iconTop;
		if(var.seamsCtmVert==null) return var.icon;
    	return var.seamsCtmVert.icons[index];
    }
   
}
