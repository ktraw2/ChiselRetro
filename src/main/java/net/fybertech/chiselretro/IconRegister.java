package net.fybertech.chiselretro;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import info.jbcs.minecraft.chisel.Chisel;

public class IconRegister 
{
	public static Map<String, IconRegister> globalRegisters = new HashMap<String, IconRegister>();
	
	
	
	public List<IconInstance> icons = new ArrayList<IconInstance>();
	
	// "blocks" or "items", used to generate filename
	public String registerType;
	
	// Number of 16x16 tile slots
	public int slotCountX;
	public int slotCountY;
	
	// Size of the texture
	public int textureWidth;
	public int textureHeight;
	
	// One pixel, in UV
	public double oneUvX;
	public double oneUvY;
	
	// 16 pixels (a tile), in UV
	public double tileUvX;
	public double tileUvY;

	public int textureId;
	public boolean[][] slots;

	public String name;

	public Map<Integer, Icon> idToIcon = new HashMap<Integer, Icon>();
	
	
	
	
	public static class TextureSlot
	{
		public int x, y;
		
		public TextureSlot(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	
	
	
	public static IconRegister getRegister(String name)
	{
		return globalRegisters.get(name);
	}
	
	
	
	public IconRegister(String name, String rtype, int slotsPerRow, int slotsPerCol) 
	{		
		this.name = name;
		this.registerType = rtype;
		
		globalRegisters.put(name, this);
		
		slotCountX = slotsPerRow;
		slotCountY = slotsPerCol;
		
		textureWidth = slotCountX * 16;
		textureHeight = slotCountY * 16;
		
		oneUvX = 1.0 / textureWidth;
		oneUvY = 1.0 / textureHeight;
		
		tileUvX = oneUvX * 16.0;
		tileUvY = oneUvY * 16.0;
		
		this.slots = new boolean[slotsPerRow][];
        for (int n = 0; n < slotsPerRow; n++) {
        	this.slots[n] = new boolean[slotsPerRow];
        }
	}

	
	
	public TextureSlot findEmptyTextureSlot(int startX, int startY)
	{
		int slotCount = slots[0].length;
		
		for (int y = startY; y < slotCount; y++) {
			for (int x = startX; x < slotCount; x++) {
				if (!slots[y][x]) return new TextureSlot(x, y);
			}
		}
		
		return null;
	}
	
	
	public TextureSlot[] findTextureSlot(int width, int height)
	{
		int slotCount = slots[0].length;
		
		int slotWidth = (int)(Math.ceil(width / 16.0));
		int slotHeight = (int)(Math.ceil(height / 16.0));
		
		//System.out.println("Slot Size: " + slotWidth + " " + slotHeight);
		
		int startX = 0;
		int startY = 0;
		
		List<TextureSlot> texSlots = new ArrayList<TextureSlot>();
		
		while (true) {
			
			TextureSlot startSlot = findEmptyTextureSlot(startX, startY);
			if (startSlot == null) return null;
			
			if (slotWidth == 1 && slotHeight == 1) return new TextureSlot[] { startSlot };
			
			texSlots.clear();
			
			for (int y = startSlot.y; y < startSlot.y + slotHeight; y++) 
			{
				if (y >= slotCount) break;
				
				for (int x = startSlot.x; x < startSlot.x + slotWidth; x++) 
				{
					if (x >= slotCount) break;
					if (!slots[y][x]) texSlots.add(new TextureSlot(x, y));
				}
			}
			
			if (texSlots.size() == (slotWidth * slotHeight)) break;
			
			startX = startSlot.x + 1;
			startY = startSlot.y;
			if (startX >= slotCount) { startX = 0; startY++; }
			if (startY >= slotCount) return null;
		}
		
		//System.out.println("Slot Size: " + slotWidth + " " + slotHeight + " " + texSlots.size());
		
		return texSlots.toArray(new TextureSlot[0]);
	}
	
	
	

	public Icon registerIcon(String string) 
	{
		//System.out.println("registerIcon: " + string);
		
		String[] split = string.split(":");
		String owner = split.length > 1 ? split[0].toLowerCase() : "minecraft";
		String name = split.length > 1 ? split[1] : split[0];
		
		//System.out.println("  /assets/" + owner + "/textures/blocks/" + name + ".png");
		
		IconInstance icon = new IconInstance();
		icon.resourceLocation = string;
		icon.owner = owner;
		icon.name = name;
		
		icons.add(icon);
		
		return icon;
	}



	public void generateAtlas() 
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureId);
		
		idToIcon.clear();
		
		for (IconInstance icon : this.icons) 
		{
			String filename = "assets/" + icon.owner + "/textures/" + this.registerType + "/" + icon.name + ".png";
			InputStream stream = Chisel.class.getClassLoader().getResourceAsStream(filename);
			if (stream == null) stream = Chisel.class.getClassLoader().getResourceAsStream("/" + filename);
			
			if (stream == null) {
				System.out.println("Unable to open " + filename);
			}
			else 
			{
				BufferedImage image = null;
				try {					
					image = ImageIO.read(stream);
					stream.close();
				} catch (IOException e) {					
					e.printStackTrace();
				}					
			
				if (image == null) {
					System.out.println("Unable to read image " + filename);
				}
				else {
					//System.out.println("  Image: " + image);
					icon.width = image.getWidth();
					icon.height = image.getHeight();
					icon.texID = this.textureId;
					
					
					TextureSlot[] texSlots = this.findTextureSlot(icon.width, icon.height);
					if (texSlots != null) 
					{
						for (TextureSlot slot : texSlots) this.slots[slot.y][slot.x] = true;
						
						int[] rgbArray = new int[icon.width * icon.height];					
						image.getRGB(0,  0, icon.width, icon.height, rgbArray, 0, icon.width);
						
						int baseX = texSlots[0].x * 16;
						int baseY = texSlots[0].y * 16;
						
						icon.texNum = (texSlots[0].y * slotCountX) + texSlots[0].x;
						icon.minU = (float) (baseX * this.oneUvX);
						icon.minV = (float) (baseY * this.oneUvX);
						icon.maxU = (float) (icon.minU + (icon.width * this.oneUvX));
						icon.maxV = (float) (icon.minV + (icon.height * this.oneUvY));
						
						this.idToIcon.put(icon.texNum, icon);
						
						ByteBuffer rgbBuffer = ByteBuffer.allocateDirect(rgbArray.length * 4);
						for (int i = 0; i < rgbArray.length; i++)
				        {
				            int red = rgbArray[i] >> 24 & 255;
				            int green = rgbArray[i] >> 16 & 255;
				            int blue = rgbArray[i] >> 8 & 255;
				            int alpha = rgbArray[i] & 255;				            
				            
				            rgbBuffer.put((byte)alpha);
				            rgbBuffer.put((byte)blue);
				            rgbBuffer.put((byte)green);
				            rgbBuffer.put((byte)red);
				        }
						rgbBuffer.flip();
						
						GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, baseX, baseY, icon.width, icon.height, GL12.GL_BGRA, GL11.GL_UNSIGNED_BYTE, rgbBuffer);
					}
					else {
						System.out.println("  ERROR: Can't find available atlas slot in " + this.name);
					}
				}
			}
		}
	}
}
