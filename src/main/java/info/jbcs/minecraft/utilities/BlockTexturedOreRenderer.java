package info.jbcs.minecraft.utilities;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class BlockTexturedOreRenderer implements ISimpleBlockRenderingHandler 
{
	float bot=-0.001f,top=1.0f-bot;
	static int id;

	public BlockTexturedOreRenderer() {
		id = RenderingRegistry.getNextAvailableRenderId();
	}

	@Override
	public void renderInventoryBlock(Block blck, int meta, int modelID, RenderBlocks renderer) {
		if (blck == null || !(blck instanceof BlockTexturedOre))
			return;
		
		BlockTexturedOre block=(BlockTexturedOre) blck;
		
		if(block.icon!=null){
			renderer.overrideBlockTexture = block.icon.getTextureNum();
			renderer.renderBlockAsItem(Block.stone,meta,1.0f);
			renderer.overrideBlockTexture=-1;
		} else if(block.base!=null){
			renderer.renderBlockAsItem(block.base,meta,1.0f);
		}
		
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		renderer.setRenderBounds(bot, bot, bot, top, top, top);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		Drawing.drawBlock(block, meta, renderer);
		GL11.glDisable(3042);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block blck, int modelId, RenderBlocks renderer) {
		if (blck == null || !(blck instanceof BlockTexturedOre))
			return false;
		BlockTexturedOre block=(BlockTexturedOre) blck;
		
		if(block.currentPass==0){
			if(block.icon!=null) {
				// Normally we'd use the block's inner icon, but we're cheating.  The item version uses
				// a static lava texture for 1.4.7 compatibility reasons, but when in the world we'll 
				// render with the animated lava texture instead.  This would be a problem if anything 
				// else ever uses BlockTexturedOre.
				renderer.overrideBlockTexture=Block.lavaMoving.blockIndexInTexture; //block.icon.getTextureNum();
				renderer.renderStandardBlock(block, x, y, z);
				renderer.overrideBlockTexture=-1;
			} else if(block.base!=null){
				renderer.renderBlockByRenderType(block.base, x, y, z);
			}
		} else{
			renderer.setRenderBounds(bot, bot, bot, top, top, top);
			renderer.renderStandardBlock(block, x, y, z);
		}

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return id;
	}
	
}
