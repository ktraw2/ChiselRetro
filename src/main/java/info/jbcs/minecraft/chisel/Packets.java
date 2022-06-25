package info.jbcs.minecraft.chisel;

import info.jbcs.minecraft.utilities.General;
import info.jbcs.minecraft.utilities.packets.PacketData;
import info.jbcs.minecraft.utilities.packets.PacketHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import cpw.mods.fml.common.FMLCommonHandler;

public class Packets {

	static PacketHandler chiseled = new PacketHandler("A block has been chiseled") {
		@Override
		public void onData(DataInputStream stream, EntityPlayer player) throws IOException {
			final int x=stream.readInt();
			final int y=stream.readInt();
			final int z=stream.readInt();
			
			switch(FMLCommonHandler.instance().getEffectiveSide()){
			case SERVER:
				ServerConfigurationManager mgr = MinecraftServer.getServer().getConfigurationManager();
				for (int j = 0; j < mgr.playerEntityList.size(); ++j) {
					EntityPlayerMP p = (EntityPlayerMP) mgr.playerEntityList.get(j);

					if (p.dimension != player.dimension) continue;
					if (p==player) continue;
					if (! General.isInRange(30.0f, p.posX, p.posY, p.posZ, x, y, z)) continue;

					sendToPlayer(p,new PacketData(){
						@Override
						public void data(DataOutputStream stream) throws IOException {
							stream.writeInt(x);
							stream.writeInt(y);
							stream.writeInt(z);
						}
					});
				}
				
				break;
			case CLIENT:
				int blockId=player.worldObj.getBlockId(x,y,z);
				int blockMeta=player.worldObj.getBlockMetadata(x,y,z);
				GeneralChiselClient.spawnChiselEffect(x, y, z, Carving.chisel.getVariationSound(blockId, blockMeta));

				break;
			default:
				break;
			}

		}
	};

}
