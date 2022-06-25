package info.jbcs.minecraft.utilities;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import info.jbcs.minecraft.chisel.Chisel;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

@SideOnly(Side.CLIENT)
public abstract class Sounds 
{
	SoundLoadEvent event;

	
    @ForgeSubscribe
    public void onSound(SoundLoadEvent evt)
    {
		event = evt;

		System.out.println("Copying Chisel sounds to resource directory...");
		addSounds();
	}

    
    public abstract void addSounds();
    

    protected void addSound(String path)
    {
		// In 1.4.7 we have to copy sounds from the jar and specify a direct 
    	// path to it.
    	//
    	// Could possibly use a URL to a resource in the jar, but that breaks
    	// in the dev environment, so this works just as well for now.
    	
    	String[] split = path.split(":");
    	String owner = split[0].toLowerCase();
    	String name = split[1];
    	
    	File output = copySoundToResources("assets/" + owner + "/sound/", name);
    	if (output != null) event.manager.soundPoolSounds.addSound(path, output);
    }
    
    
    
    private File copySoundToResources(String assetPath, String filename)
	{
		File resourceDir = new File(Minecraft.getMinecraft().mcDataDir, "resources/chiselretro/");		
		if (!resourceDir.exists()) resourceDir.mkdir();
		
		InputStream inputStream = Chisel.class.getResourceAsStream(assetPath + filename);
		if (inputStream == null) inputStream = Chisel.class.getResourceAsStream("/" + assetPath + filename);
		
		File outputFile = new File(resourceDir, filename);
		FileOutputStream outputStream = null;
		
		try {
			outputStream = new FileOutputStream(outputFile);
		
			byte[] buffer = new byte[1024];
			
			while (true)
			{
				int readCount = inputStream.read(buffer);
				if (readCount <= 0) break;
				else outputStream.write(buffer, 0, readCount);
			}			
		} 
		catch (IOException e) {}
		finally {
			try {
				inputStream.close();
				outputStream.close();
			}
			catch (IOException e) {}
		}
		
		return outputFile;
	}
    	  
    
}
