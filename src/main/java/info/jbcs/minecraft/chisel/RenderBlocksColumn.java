package info.jbcs.minecraft.chisel;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.fybertech.chiselretro.Icon;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

@SideOnly(Side.CLIENT)
public class RenderBlocksColumn extends RenderBlocks 
{
    TextureSubmap submap;
    Icon iconTop;
	Icon sides[]=new Icon[6];
	
	RenderBlocksColumn(){
		super();
	}

	boolean connected(IBlockAccess world, int x,int y,int z,int id,int meta){
		return world.getBlockId(x,y,z)==id && world.getBlockMetadata(x,y,z)==meta;
		
	}
	
    @Override
	public boolean renderStandardBlock(Block block, int x, int y, int z) {
		int metadata=blockAccess.getBlockMetadata(x, y, z);
		int id=block.blockID;
		
		boolean yp=connected(blockAccess,x,y+1,z,id,metadata);
		boolean yn=connected(blockAccess,x,y-1,z,id,metadata);
		
		if(yp || yn){
			sides[0]=iconTop;
			sides[1]=iconTop;
			
			if(yp && yn)
				sides[2]=submap.icons[2];
			else if(yp)
				sides[2]=submap.icons[3];
			else
				sides[2]=submap.icons[1];
			
			sides[3]=sides[4]=sides[5]=sides[2];
		} else{
			boolean xp=connected(blockAccess,x+1,y,z,id,metadata);
			boolean xn=connected(blockAccess,x-1,y,z,id,metadata);
			
			if(xp && (connected(blockAccess,x+1,y+1,z,id,metadata) || connected(blockAccess,x+1,y-1,z,id,metadata)))
				xp=false;
			if(xn && (connected(blockAccess,x-1,y+1,z,id,metadata) || connected(blockAccess,x-1,y-1,z,id,metadata)))
				xn=false;
			
			if(xp || xn){
	        	uvRotateEast = 2;
	            uvRotateWest = 1;
	            uvRotateTop = 1;
	            uvRotateBottom = 1;
	            
				sides[4]=iconTop;
				sides[5]=iconTop;
				
				if(xp && xn)
					sides[0]=submap.icons[2];
				else if(xp)
					sides[0]=submap.icons[3];
				else
					sides[0]=submap.icons[1];
				
				sides[1]=sides[2]=sides[3]=sides[0];
			} else{
				boolean zp=connected(blockAccess,x,y,z+1,id,metadata);
				boolean zn=connected(blockAccess,x,y,z-1,id,metadata);
				
				if(zp && (connected(blockAccess,x,y+1,z+1,id,metadata) || connected(blockAccess,x,y-1,z+1,id,metadata)))
					zp=false;
				if(zp && (connected(blockAccess,x+1,y,z+1,id,metadata) || connected(blockAccess,x-1,y,z+1,id,metadata)))
					zp=false;
				if(zn && (connected(blockAccess,x,y+1,z-1,id,metadata) || connected(blockAccess,x,y-1,z-1,id,metadata)))
					zn=false;
				if(zn && (connected(blockAccess,x+1,y,z-1,id,metadata) || connected(blockAccess,x-1,y,z-1,id,metadata)))
					zn=false;
				
				if(zp || zn){
		        	uvRotateSouth = 1;
		            uvRotateNorth = 2;
		            
					sides[2]=iconTop;
					sides[3]=iconTop;
					
					if(zp && zn)
						sides[0]=submap.icons[2];
					else if(zp)
						sides[0]=submap.icons[1];
					else
						sides[0]=submap.icons[3];
					
					sides[1]=sides[4]=sides[5]=sides[0];
				} else{
					sides[0]=sides[1]=iconTop;
					sides[2]=sides[3]=sides[4]=sides[5]=submap.icons[0];
				}
			}
		}
		

        boolean flag = super.renderStandardBlock(block, x, y, z);
        
        uvRotateSouth = 0;
        uvRotateEast = 0;
        uvRotateWest = 0;
        uvRotateNorth = 0;
        uvRotateTop = 0;
        uvRotateBottom = 0;

		return flag;
    }
    

	@Override // x negative
	public void renderNorthFace(Block block, double x, double y, double z, int icon){
        super.renderNorthFace(block,x,y,z,sides[4].getTextureNum());
    }
	
	@Override // x positive
	public void renderSouthFace(Block block, double x, double y, double z, int icon){
        super.renderSouthFace(block,x,y,z,sides[5].getTextureNum());
    }

	@Override // z negative
	public void renderEastFace(Block block, double x, double y, double z, int icon){
        super.renderEastFace(block,x,y,z,sides[2].getTextureNum());
    }
	

	@Override // x positive
	public void renderWestFace(Block block, double x, double y, double z, int icon){
        super.renderWestFace(block,x,y,z,sides[3].getTextureNum());
    }

	@Override	
	public void renderBottomFace(Block block, double x, double y, double z, int icon){
        super.renderBottomFace(block,x,y,z,sides[0].getTextureNum());
    }
	
	@Override
	public void renderTopFace(Block block, double x, double y, double z, int icon){
        super.renderTopFace(block,x,y,z,sides[1].getTextureNum());
    }
	
}
