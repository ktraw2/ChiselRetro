package info.jbcs.minecraft.chisel;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.fybertech.chiselretro.Icon;

public class TextureVirtual implements Icon {
	int ox,oy;
	float u0,u1,v0,v1;
	String name;
	Icon icon;
	int texNum;
	
	int offsetX, offsetY;
	
	TextureVirtual(Icon parent, int w, int h, int x, int y){
		icon=parent;
		
		u0=icon.getInterpolatedU(16.0*(x+0)/w);
		u1=icon.getInterpolatedU(16.0*(x+1)/w);
		v0=icon.getInterpolatedV(16.0*(y+0)/h);
		v1=icon.getInterpolatedV(16.0*(y+1)/h);
		
		name=icon.getIconName()+"|"+x+"."+y;
		//System.out.println(u0 + " " + u1 + " " + v0 + " " + v1);
		ox=icon.getIconWidth();
		oy=icon.getIconHeight();
		
		this.offsetX = x;
		this.offsetY = y;
		
		this.texNum = icon.getTextureNum();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getMinU() {
		return u0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getMaxU() {
		return u1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getInterpolatedU(double d0) {		
		return (float) (u0+(u1-u0)*d0/16.0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getMinV() {		
		return v0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getMaxV() {
		return v1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getInterpolatedV(double d0) {
		return (float) (v0+(v1-v0)*d0/16.0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getIconName() {
		return name;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getIconWidth() {
		return ox;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getIconHeight() {
		return oy;
	}

	@Override
	public int getTextureNum() {
		// TODO - Fyber - maybe need to fix this
		//System.out.println("GETTING VIRTUAL TEXNUM " + offsetX + " " + offsetY);
		return texNum + offsetX + (offsetY * 16);
	}

}
