package net.fybertech.chiselretro;

public class IconInstance implements Icon
{
	public int width;
	public int height;
	
	public float minU;
	public float maxU;
	public float minV;
	public float maxV;
	
	public String owner;
	public String name;
	public int texID;
	public int texNum;
	public String resourceLocation;
	
	
	@Override
	public int getIconWidth() {			
		return width;
	}

	@Override
	public int getIconHeight() {			
		return height;
	}

	@Override
	public float getMinU() {			
		return minU;
	}

	@Override
	public float getMaxU() {			
		return maxU;
	}

	@Override
	public float getMinV() {			
		return minV;
	}

	@Override
	public float getMaxV() {			
		return maxV;
	}

	@Override
	public float getInterpolatedU(double u) {
		return (float) (minU+(maxU-minU)*u/16.0);
	}

	@Override
	public float getInterpolatedV(double v) {			
		return (float) (minV+(maxV-minV)*v/16.0);
	}

	@Override
	public String getIconName() {			
		return name;
	}

	@Override
	public int getTextureNum() {
		return texNum;
	}
	
}
