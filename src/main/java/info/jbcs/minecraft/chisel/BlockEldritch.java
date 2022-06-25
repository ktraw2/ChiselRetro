package info.jbcs.minecraft.chisel;


public class BlockEldritch extends BlockMarble {

	public BlockEldritch(String name, int id) {
		super(name,id);
	}
	
	@Override
	public int getRenderType() {
		return Chisel.RenderEldritchId;
	}

}
