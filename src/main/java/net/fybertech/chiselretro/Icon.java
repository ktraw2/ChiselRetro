package net.fybertech.chiselretro;

public interface Icon 
{
    int getIconWidth();

    int getIconHeight();

    float getMinU();

    float getMaxU();

    float getMinV();

    float getMaxV();
    
    float getInterpolatedU(double u);

    float getInterpolatedV(double v);

    String getIconName();

	int getTextureNum();    
}
