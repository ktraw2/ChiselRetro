package info.jbcs.minecraft.chisel;

import info.jbcs.minecraft.utilities.Drawing;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BlockAdvancedMarbleRenderer implements ISimpleBlockRenderingHandler {
	RenderBlocksCompat rendererCompat=new RenderBlocksCompat();
	RenderBlocksCTM rendererCTM=new RenderBlocksCTM();
    RenderBlocksColumn rendererColumn=new RenderBlocksColumn();
	
	public BlockAdvancedMarbleRenderer() {
		if(Chisel.RenderCTMId==0){
			Chisel.RenderCTMId = RenderingRegistry.getNextAvailableRenderId();
		
		}
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		Drawing.drawBlock(block, metadata, renderer);	
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}
    
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks rendererOld) {
		if (rendererOld.overrideBlockTexture >= 0) {
			return rendererOld.renderStandardBlock(block, x, y, z);
		}

		int meta = world.getBlockMetadata(x, y, z);
        
    	CarvableVariation var=((Carvable) block).getVariation(meta);
    	
    	switch(var==null?0:var.kind)
    	{	    	
	    	case CarvableHelper.CTMX:    		
	            rendererCTM.blockAccess=world;
	            rendererCTM.renderMaxX=1.0;
	            rendererCTM.renderMaxY=1.0;
	            rendererCTM.renderMaxZ=1.0;
	            
	    		rendererCTM.submap=var.submap;
	    		rendererCTM.submapSmall=var.submapSmall;
	    		
	    		rendererCTM.rendererOld=rendererOld;    		
	            return rendererCTM.renderStandardBlock(block,x,y,z);
	            
	        case CarvableHelper.CTMV:
	        	rendererColumn.blockAccess=world;
	        	rendererColumn.renderMaxX=1.0;
	            rendererColumn.renderMaxY=1.0;
	            rendererColumn.renderMaxZ=1.0;
	            
	            rendererColumn.submap=var.seamsCtmVert;        	
	            rendererColumn.iconTop=var.iconTop;   
	            
	           return rendererColumn.renderStandardBlock(block,x,y,z);      
	           
	    	default:
	    		// Use the internal renderer for slabs since the compat renderer
	    		// only works for cubes currently.  This means only 16x16 textures 
	    		// will work properly.
	    		if (block instanceof BlockMarbleSlab)
	    			return rendererOld.renderStandardBlock(block,x,y,z);
	    		
	    		// Otherwise use the 1.4.7 compatibility renderer, which
	    		// allows for larger texture sizes.
	    		rendererCompat.blockAccess=world;
	    		rendererCompat.renderMaxX=1.0;
	    		rendererCompat.renderMaxY=1.0;
	    		rendererCompat.renderMaxZ=1.0;
	    		rendererCompat.rendererOld=rendererOld;
				rendererCompat.overrideBlockTexture = rendererOld.overrideBlockTexture;
	    		return rendererCompat.renderStandardBlock(block,x,y,z);
    	}
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return Chisel.RenderCTMId;
	}

}
