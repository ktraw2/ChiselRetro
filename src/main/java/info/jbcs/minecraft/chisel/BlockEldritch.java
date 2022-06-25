package info.jbcs.minecraft.chisel;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockEldritch extends BlockMarble {

	public BlockEldritch(String name, int id) {
		super(name,id);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType() {
		return Chisel.RenderEldritchId;
	}

}
