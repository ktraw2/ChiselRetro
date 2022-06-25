package info.jbcs.minecraft.chisel;



import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.fybertech.chiselretro.Icon;
import net.fybertech.chiselretro.IconRegister;
import net.minecraft.world.World;

public class BlockSnakestoneObsidian extends BlockSnakestone {
	Icon[] particles=new Icon[8];
	
	public BlockSnakestoneObsidian(int id, String iconPrefix) {
		super(id, iconPrefix);
		
		flipTopTextures=true;

	}

	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		GeneralChiselClient.spawnSnakestoneObsidianFX(world,this,x,y,z);
	}

	
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) {
		super.registerIcons(register);
		
		for(int i=0;i<particles.length;i++){
			particles[i]=register.registerIcon(iconPrefix + "particles/"+i);
		}
	}

}
