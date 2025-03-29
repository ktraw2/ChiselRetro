package info.jbcs.minecraft.chisel;

import com.google.common.collect.ImmutableList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.fybertech.chiselretro.IBlockTextures;
import net.fybertech.chiselretro.IRegisterIcons;
import net.fybertech.chiselretro.Icon;
import net.fybertech.chiselretro.IconRegister;
import net.fybertech.chiselretro.RetroUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.List;

public class BlockRoadLine extends BlockMarble implements IRegisterIcons, IBlockTextures
{
	private final List<Icon> halfLineIcons;
	private final List<Icon> fullLineIcons;
	
	public BlockRoadLine(String name, int i) {
		super(name == null ? i : Chisel.config.getBlock(name, i).getInt(i), Material.circuits);

		this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.00390625f, 1.0f);
		halfLineIcons = new ArrayList<Icon>();
		fullLineIcons = new ArrayList<Icon>();
//        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);

		setCreativeTab(Chisel.tabChisel);
	}

	public enum LineLength {
		HALF,
		FULL
	}

	public Icon getConnectingTexture(final LineLength lineLength, final int variant) {
		final List<Icon> listToUse;
		switch (lineLength) {
			case HALF:
				listToUse = halfLineIcons;
				break;
			case FULL:
				listToUse = fullLineIcons;
				break;
			default:
				return null;
		}

		if (variant >= listToUse.size()) {
			return null;
		}

		return listToUse.get(variant);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		return null;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType() {
		return BlockRoadLineRenderer.id;
	}

	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
		return par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) || par1World.getBlockId(par2, par3 - 1, par4) == Block.glowStone.blockID;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int par5) {
		if (world.isRemote) return;

		if (!this.canPlaceBlockAt(world, x, y, z)) {
			final int metadata = world.getBlockMetadata(x, y, z);
			this.dropBlockAsItem(world, x, y, z, metadata, 0);
			RetroUtil.setBlockToAir(world, x, y, z);
		}

		super.onNeighborBlockChange(world, x, y, z, par5);
	}

	@Override
	public int damageDropped(int i) {
		System.out.println(i);
		return i;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		halfLineIcons.addAll(ImmutableList.of(
				reg.registerIcon(getQualifiedTextureVariant("white", "side")),
				reg.registerIcon(getQualifiedTextureVariant("yellow", "side"))
		));
		fullLineIcons.addAll(ImmutableList.of(
				reg.registerIcon(getQualifiedTextureVariant("white", "long")),
				reg.registerIcon(getQualifiedTextureVariant("yellow", "long"))
		));
		super.registerIcons(reg);
	}

	private static String getQualifiedTextureVariant(final String color, final String variant) {
		return String.format("Chisel:%s", getTextureVariant(color, variant));
	}

	public static String getTextureVariant(final String color, final String variant) {
		final String textureResolution = Chisel.roadLine16xTextures ? "-16x" : "";
		return String.format("line-marking/%s-%s%s", color, variant, textureResolution);
	}
}
