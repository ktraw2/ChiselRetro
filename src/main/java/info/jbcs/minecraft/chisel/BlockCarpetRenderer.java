package info.jbcs.minecraft.chisel;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BlockCarpetRenderer extends BlockAdvancedMarbleRenderer {
	
	BlockCarpetRenderer(){
		super();
		
		Chisel.RenderCarpetId = RenderingRegistry.getNextAvailableRenderId();

		rendererCTM=new RenderBlocksCTMCarpet();
	}

	@Override
	public int getRenderId() {
		return Chisel.RenderCarpetId;
	}
}
