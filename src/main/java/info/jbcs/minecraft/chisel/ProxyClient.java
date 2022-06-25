package info.jbcs.minecraft.chisel;

import info.jbcs.minecraft.utilities.BlockTexturedOreRenderer;
import info.jbcs.minecraft.utilities.Sounds;
import net.fybertech.chiselretro.IRegisterIcons;
import net.fybertech.chiselretro.IconRegister;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.client.TextureFXManager;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;


@SideOnly(Side.CLIENT)
public class ProxyClient extends Proxy 
{
	ItemChiselRenderer renderer = new ItemChiselRenderer();

		
	@Override
	public void preInit() {
		MinecraftForge.EVENT_BUS.register(new Sounds() {
			@Override
			public void addSounds() {
				addSound("chisel:chisel.ogg");

				addSound("chisel:chisel-wood2.ogg");
				addSound("chisel:chisel-wood3.ogg");
				addSound("chisel:chisel-wood4.ogg");
				addSound("chisel:chisel-wood5.ogg");
				addSound("chisel:chisel-wood8.ogg");
				addSound("chisel:chisel-wood9.ogg");
				addSound("chisel:chisel-wood11.ogg");

				addSound("chisel:holystone1.ogg");
				addSound("chisel:holystone2.ogg");
				addSound("chisel:holystone3.ogg");
				addSound("chisel:holystone5.ogg");
				addSound("chisel:holystone7.ogg");

				addSound("chisel:squash.ogg");
				addSound("chisel:squash2.ogg");
				
				addSound("chisel:temple-footstep1.ogg");
				addSound("chisel:temple-footstep2.ogg");
				addSound("chisel:temple-footstep3.ogg");
				addSound("chisel:temple-footstep4.ogg");
				addSound("chisel:temple-footstep5.ogg");
				
				addSound("chisel:metal1.ogg");
				addSound("chisel:metal2.ogg");
				addSound("chisel:metal3.ogg");
				addSound("chisel:metal4.ogg");
				addSound("chisel:metal5.ogg");
				addSound("chisel:metal6.ogg");
				addSound("chisel:metal7.ogg");
				addSound("chisel:metal8.ogg");
				addSound("chisel:metal9.ogg");
			}
		});
	}

	@Override
	public void init() {
		RenderingRegistry.registerBlockHandler(new BlockMarbleStairsRenderer());
		RenderingRegistry.registerBlockHandler(new BlockMarblePaneRenderer());
		RenderingRegistry.registerBlockHandler(new BlockRoadLineRenderer());
		RenderingRegistry.registerBlockHandler(new BlockSnakeStoneRenderer());
		RenderingRegistry.registerBlockHandler(new BlockNoCTMRenderer());
		RenderingRegistry.registerBlockHandler(new BlockSpikesRenderer());
		RenderingRegistry.registerBlockHandler(new BlockMarblePillarRenderer());
		RenderingRegistry.registerBlockHandler(new BlockEldritchRenderer());
		RenderingRegistry.registerBlockHandler(new BlockAdvancedMarbleRenderer());
		RenderingRegistry.registerBlockHandler(new BlockCarpetRenderer());
		
		
		RenderingRegistry.registerBlockHandler(new BlockTexturedOreRenderer());
		
		RenderingRegistry.registerEntityRenderingHandler(EntityCloudInABottle.class, new RenderProjectile(Chisel.itemCloudInABottle));
		RenderingRegistry.registerEntityRenderingHandler(EntityBallOMoss.class, new RenderProjectile(Chisel.itemBallOMoss));
		
		
		MinecraftForgeClient.registerItemRenderer(Chisel.chisel.itemID, renderer);
//		MinecraftForgeClient.registerItemRenderer(Chisel.needle.itemID, renderer);

	}
	
	
	
	
	public int createEmptyGlTexture(int width, int height)
	{
		IntBuffer singleIntBuffer = GLAllocation.createDirectIntBuffer(1);		
        GLAllocation.generateTextureNames(singleIntBuffer);
        int textureId = singleIntBuffer.get(0);
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);			        
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer)null);
        
        return textureId;
	}
	
	
	private void saveCurrentGlTextureToFile(String filename)
	{
		int width = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_WIDTH);
		int height = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_HEIGHT);
		
		IntBuffer imageData = BufferUtils.createIntBuffer(width * height);
		GL11.glGetTexImage(GL11.GL_TEXTURE_2D, 0, GL12.GL_BGRA, GL11.GL_UNSIGNED_BYTE, imageData);
		imageData.rewind();
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB); //  TYPE_4BYTE_ABGR);
		imageData.rewind();

		int[] imgArray = new int[width * height];
		imageData.get(imgArray);
		image.setRGB(0, 0, width, height, imgArray, 0, width);

		File outputfile = new File(filename);
		try {
		    ImageIO.write(image, "png", outputfile);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	
	
	
	
	
	@Override
	public void postInit()
	{
		int atlasDimension = 256;
        int slotsPerRow = atlasDimension / 16;
		
		
		RenderEngine re = Minecraft.getMinecraft().renderEngine;
		Field textureMapField = null;
		try {
			textureMapField = RenderEngine.class.getDeclaredField("textureMap");
		} catch (Exception e1) {}
		if (textureMapField == null) {
			try {
				textureMapField = RenderEngine.class.getDeclaredField("c");
			} catch (Exception e1) {} 
		}
		
		textureMapField.setAccessible(true);
		HashMap renderEngineTextureMaps = null;
		
		try {
			renderEngineTextureMaps = (HashMap)textureMapField.get(re);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		
		boolean saveAtlases = true;
		
		if (saveAtlases) {
			File atlasDir = new File("atlases/");
			if (!atlasDir.exists()) atlasDir.mkdirs();
		}
		
		
		
		Map<String, IconRegister> iconRegisters = new HashMap<String, IconRegister>();
		
		
		int registerNum = 0;
		
		for (Block block : Block.blocksList) 
		{			
			if (block instanceof IRegisterIcons) 
			{
				// Make a separate atlas for every block since we're limited to 256x256	
				// in many places.
				String name = "atlas_" + registerNum + "_" + block.getClass().getSimpleName();
				
				IconRegister iconRegister = new IconRegister(name, "blocks", slotsPerRow, slotsPerRow);				
				iconRegister.textureId = createEmptyGlTexture(atlasDimension, atlasDimension);
				
				TextureFXManager.instance().setTextureDimensions(iconRegister.textureId, atlasDimension, atlasDimension, new ArrayList());
				
				iconRegisters.put(iconRegister.name, iconRegister);
		        renderEngineTextureMaps.put(iconRegister.name, iconRegister.textureId);	
		        
		        registerNum++;
				
				
				((IRegisterIcons)block).registerIcons(iconRegister);
				
				block.setTextureFile(iconRegister.name);
			}
		}		
		
        
		System.out.println("Generating block atlas...");		
		for (IconRegister blockIconRegister : iconRegisters.values()) 
		{
			blockIconRegister.generateAtlas();			
			if (saveAtlases) saveCurrentGlTextureToFile("atlases/" + blockIconRegister.name + ".png");
		}
		System.out.println("Block atlas done");
		
		
		
		
		iconRegisters.clear();
		
		IconRegister itemIconRegister = null;
		for (Item item : Item.itemsList) 
		{
			if (item instanceof IRegisterIcons)
			{			
				if (itemIconRegister == null) 
				{
					String name = "atlas_" + registerNum + "_items";
					
					itemIconRegister = new IconRegister(name, "items", slotsPerRow, slotsPerRow);				
					itemIconRegister.textureId = createEmptyGlTexture(atlasDimension, atlasDimension);
					
					TextureFXManager.instance().setTextureDimensions(itemIconRegister.textureId, atlasDimension, atlasDimension, new ArrayList());
					
					iconRegisters.put(itemIconRegister.name, itemIconRegister);
			        renderEngineTextureMaps.put(itemIconRegister.name, itemIconRegister.textureId);	
			        
			        registerNum++;
				}
				
				((IRegisterIcons)item).registerIcons(itemIconRegister);
				
				item.setTextureFile(itemIconRegister.name);
			}
		}		
		
        
		System.out.println("Generating item atlas...");		
		for (IconRegister register : iconRegisters.values()) 
		{
			register.generateAtlas();			
			if (saveAtlases) saveCurrentGlTextureToFile("atlases/" + register.name + ".png");
		}
		System.out.println("Item atlas done");
		
		
		for (TextureSubmap submap : TextureSubmap.textureSubmaps) submap.TexturesStitched();		
		
	}
}
