package info.jbcs.minecraft.chisel;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.fybertech.chiselretro.IBlockTextures;
import net.fybertech.chiselretro.Icon;
import net.fybertech.chiselretro.IconRegister;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;

@SideOnly(Side.CLIENT)
public class RenderBlocksCompat extends RenderBlocksCTM
{
	
	void side2(int a,int b,int c,int d,Icon icon,boolean flip)
	{ 
    	if (icon == null) return;
		
		double u0=icon.getMaxU();
    	double u1=icon.getMinU();
    	double v0=icon.getMaxV();
    	double v1=icon.getMinV();
    	
    	U[a]=flip?u1:u1;
    	U[b]=flip?u0:u1;
    	U[c]=flip?u0:u0;
    	U[d]=flip?u1:u0;
  	
    	V[a]=flip?v1:v1;
    	V[b]=flip?v1:v0;
    	V[c]=flip?v0:v0;
    	V[d]=flip?v0:v1;
     	
    	vert(a); vert(b); vert(c); vert(d);    	
    }
	
	
	
	boolean connected(IBlockAccess world, int x,int y,int z,int id,int meta){
		return world.getBlockId(x,y,z)==id && world.getBlockMetadata(x,y,z)==meta;
		
	}
	
	
	Icon[] icons = new Icon[6];
	public Icon iconTop;
	
	
	@Override
	public boolean renderStandardBlock(Block block, int x, int y, int z) {
		int metadata=blockAccess.getBlockMetadata(x, y, z);
		int id=block.blockID;
		
		//		System.out.println("RENDER " + block);
	
		//boolean xn=connected(blockAccess,x-1,y,z,id,metadata);
		//boolean xp=connected(blockAccess,x+1,y,z,id,metadata);
		//boolean zn=connected(blockAccess,x,y,z-1,id,metadata);
		//boolean zp=connected(blockAccess,x,y,z+1,id,metadata);
		
		//for (int n = 0; n < 6; n++) icons[n] = this.submap.icons[0];
		/*icons[0] = iconTop;
		icons[1] = iconTop;
		
		if (xn && xp) { icons[2] = icons[3] = this.submap.icons[1]; }
		else if (xn) { icons[3] = this.submap.icons[3]; icons[2] = this.submap.icons[2]; }
		else if (xp) { icons[3] = this.submap.icons[2]; icons[2] = this.submap.icons[3]; }
		
		if (zn && zp) { icons[4] = icons[5] = this.submap.icons[1]; }
		else if (zn) { icons[4] = this.submap.icons[3]; icons[5] = this.submap.icons[2]; }
		else if (zp) { icons[4] = this.submap.icons[2]; icons[5] = this.submap.icons[3]; }*/
		
		if (block instanceof IBlockTextures) {
			for (int n = 0; n < 6; n++) icons[n] = ((IBlockTextures)block).getBlockTexture_FromAtlas(blockAccess,  x,  y,  z,  n);
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
	
	
	
	

	@Override  // x negative
	public void renderNorthFace(Block block, double x, double y, double z, int iconNum)
	{
		int tex[]=CTM.getSubmapIndices(blockAccess,bx,by,bz,4);
		//for (int n : tex) System.out.print(n + " ");
		//System.out.println("");
		
		//IconRegister register = IconRegister.getRegister(block.getTextureFile());
		//if (register == null) return;
		//Icon icon = register.idToIcon.get(iconNum);
        
		setupSides(1, 0, 4, 5, 14, 19, 17, 23, 9);		
		//side2(1,14,9,23,icon,false);		
		//side2(23,9,17,5,icon,false);		
		//side2(9,19,4,17,icon,false);	
		//side2(14,0,19,9,icon,false);
		//icon = this.submap.icons[0];
		side2(1,0,4,5,icons[4],false);
    }
	
	@Override // x positive
	public void renderSouthFace(Block block, double x, double y, double z, int icon){	
		int tex[]=CTM.getSubmapIndices(blockAccess,bx,by,bz,5);
		
        //if (compatCTM) compatIcon = this.submap.icon;
        
		setupSides(3, 2, 6, 7, 15, 25, 16, 21, 11);
		//side(11,21,3,15,tex[3],false);
		//side(16,7,21,11,tex[2],false);
		//side(25,11,15,2,tex[1],false);
		//side(6,16,11,25,tex[0],false);
		
		side2(6, 7, 3, 2, icons[5], false);
    }

	@Override // z negative
	public void renderEastFace(Block block, double x, double y, double z, int icon){
		int tex[]=CTM.getSubmapIndices(blockAccess,bx,by,bz,2);       
        
        //if (compatCTM) compatIcon = this.submap.icon;
        
        setupSides(2, 3, 0, 1, 15, 18, 14, 22, 8);  
        //System.out.println("NORTH " + block + " " + x + " " + y + " " + z);
        //side(2,15,8,22,tex[0],false);        
        //side(15,3,18,8,tex[2],false);        
        //side(8,18,0,14,tex[3],false);        
        //side(22,8,14,1,tex[1],false);
        
        side2(2, 3, 0, 1, icons[2], false);
    }
	

	@Override // z positive
	public void renderWestFace(Block block, double x, double y, double z, int icon){		
        int tex[]=CTM.getSubmapIndices(blockAccess,bx,by,bz,3);
        
        //if (compatCTM) compatIcon = this.submap.icon;
        
        setupSides(4, 7, 6, 5, 20, 16, 24, 17, 10);
		//side(17,4,20,10,tex[2],false);
		//side(5,17,10,24,tex[0],false);
		//side(24,10,16,6,tex[1],false);
		//side(10,20,7,16,tex[3],false);
        
        side2(5, 4, 7, 6, icons[3], false);
    }

	@Override
	public void renderBottomFace(Block block, double x, double y, double z, int icon){
        int tex[]=CTM.getSubmapIndices(blockAccess,bx,by,bz,0);
        
        //if (compatCTM) compatIcon = this.submap.icon;
        
        setupSides(0,3,7,4,18,21,20,19,13);
		//side(13,21,7,20,tex[3],true);
		//side(19,13,20,4,tex[2],true);
		//side(0,18,13,19,tex[0],true);
		//side(18,3,21,13,tex[1],true);
        
        side2(0, 3, 7, 4, icons[0], true);
    }
	
	@Override
	public void renderTopFace(Block block, double x, double y, double z, int icon){
        int tex[]=CTM.getSubmapIndices(blockAccess,bx,by,bz,1);
        
        //if (compatCTM) compatIcon = this.submap.icon;
        
        setupSides(2,1,5,6,22,23,24,25,12);
		//side(12,24,6,25,tex[3],false);
		//side(22,12,25,2,tex[1],false);
		//side(1,23,12,22,tex[0],false);
		//side(23,5,24,12,tex[2],false);
        
        side2(1, 5, 6, 2, icons[1], false);
    }
	
}
