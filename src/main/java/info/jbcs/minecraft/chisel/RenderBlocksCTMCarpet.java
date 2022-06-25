package info.jbcs.minecraft.chisel;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.fybertech.chiselretro.Icon;
import net.minecraft.block.Block;

@SideOnly(Side.CLIENT)
public class RenderBlocksCTMCarpet extends RenderBlocksCTM 
{
	RenderBlocksCTMCarpet(){
		super();		
	}
	
	@Override
	void resetVertices(){
		super.resetVertices();
		
		for(int i=0;i<Y.length;i++){
			Y[i]/=16;
		}
	}
	

	@Override // x negative
	public void renderNorthFace(Block block, double x, double y, double z, int icon){
		rendererOld.renderNorthFace(block,0,0,0,submapSmall.icon.getTextureNum());
    }
	
	@Override // x positive
	public void renderSouthFace(Block block, double x, double y, double z, int icon){
		rendererOld.renderSouthFace(block,0,0,0,submapSmall.icon.getTextureNum());
    }

	@Override // z negative
	public void renderEastFace(Block block, double x, double y, double z, int icon){
		rendererOld.renderEastFace(block,0,0,0,submapSmall.icon.getTextureNum());
    }
	
	@Override // z positive
	public void renderWestFace(Block block, double x, double y, double z, int icon){
		rendererOld.renderWestFace(block,0,0,0,submapSmall.icon.getTextureNum());
    }

}
