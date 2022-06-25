package info.jbcs.minecraft.chisel;

import java.util.ArrayList;
import java.util.List;

import net.fybertech.chiselretro.Icon;


public class TextureSubmap {
	int width,height;
	Icon icon;
	Icon icons[];

	
	public static List<TextureSubmap> textureSubmaps = new ArrayList<TextureSubmap>();
		
	
	public TextureSubmap(Icon i,int w,int h) {
		icon=i;
		width=w;
		height=h;
		icons=new Icon[width*height];
		
		textureSubmaps.add(this);
	}

	
	public void TexturesStitched(){
		for(int x=0;x<width;x++){
			for(int y=0;y<height;y++){
				icons[y*width+x]=new TextureVirtual(icon,width,height,x,y);
			}
		}
	}

}
