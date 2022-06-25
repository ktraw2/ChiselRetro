package info.jbcs.minecraft.utilities;

import net.fybertech.chiselretro.Icon;
import net.fybertech.chiselretro.RetroUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;

public class Drawing {
	/*public static void drawBlock(Block block,Icon icon, RenderBlocks renderer){
		Tessellator tessellator = Tessellator.instance;

		tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, icon);
        tessellator.draw();
		tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, icon);
        tessellator.draw();
		tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, icon);
        tessellator.draw();
		tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, icon);
        tessellator.draw();
		tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, icon);
        tessellator.draw();
		tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, icon);
        tessellator.draw();
	}*/

	public static void drawBlock(Block block,int meta, RenderBlocks renderer){
		Tessellator tessellator = Tessellator.instance;

		tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderBottomFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(0, meta));//  RetroUtil.getIcon(block, 0, meta));
        tessellator.draw();
		tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        //renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, RetroUtil.getIcon(block, 1, meta));
        renderer.renderTopFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(1, meta));
        tessellator.draw();
		tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        //renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, RetroUtil.getIcon(block, 2, meta));
        //renderer.renderNorthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(2, meta));
        renderer.renderEastFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(2, meta));
        tessellator.draw();
		tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        //renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, RetroUtil.getIcon(block, 3, meta));
        //renderer.renderSouthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(3, meta));
        renderer.renderWestFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(3, meta));
        tessellator.draw();
		tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        //renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, RetroUtil.getIcon(block, 4, meta));
        //renderer.renderWestFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(4, meta));
        renderer.renderNorthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(4, meta));
        tessellator.draw();
		tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        //renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, RetroUtil.getIcon(block, 5, meta));
        //renderer.renderEastFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(5, meta));
        renderer.renderSouthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(5, meta));
        tessellator.draw();
	}

	
	
}
