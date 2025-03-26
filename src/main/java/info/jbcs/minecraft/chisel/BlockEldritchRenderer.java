package info.jbcs.minecraft.chisel;

import info.jbcs.minecraft.utilities.Drawing;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BlockEldritchRenderer implements ISimpleBlockRenderingHandler {
	public BlockEldritchRenderer() {
		Chisel.RenderEldritchId = RenderingRegistry.getNextAvailableRenderId();
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		Drawing.drawBlock(block, metadata, renderer);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

	final RenderBlocksEldritch renderer = new RenderBlocksEldritch();

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks rendererOld) {
		if (rendererOld.overrideBlockTexture >= 0) {
			return rendererOld.renderStandardBlock(block, x, y, z);
		}

		renderer.blockAccess=world;
		renderer.renderMaxX=1.0;
		renderer.renderMaxY=1.0;
		renderer.renderMaxZ=1.0;
		renderer.renderStandardBlock(block,x,y,z);

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return Chisel.RenderEldritchId;
	}
}
