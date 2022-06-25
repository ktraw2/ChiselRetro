package info.jbcs.minecraft.autoutils;

import info.jbcs.minecraft.utilities.packets.PacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.Player;

@Mod(modid="Autoutils", name="Autoutils", version="1.0.1")
@NetworkMod(clientSideRequired=true, serverSideRequired=true, channels={ PacketHandler.channel }, packetHandler = Autoutils.class)
public class Autoutils implements IPacketHandler {
	static Configuration config;

	@Instance("Autoutils")
	public static Autoutils instance;

	@SidedProxy(clientSide = "info.jbcs.minecraft.autoutils.ProxyClient", serverSide = "info.jbcs.minecraft.autoutils.Proxy")
	public static Proxy proxy;

	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		proxy.preInit();
	}
	
	@Init
	public void init(FMLInitializationEvent event) {
		proxy.init();

		PacketHandler.register(this);
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent event) {
	}

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        PacketHandler.onPacketData(manager,packet,(EntityPlayer)player);
	}
}



