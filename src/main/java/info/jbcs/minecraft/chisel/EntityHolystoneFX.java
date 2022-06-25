package info.jbcs.minecraft.chisel;

import org.lwjgl.opengl.GL11;

import info.jbcs.minecraft.utilities.General;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class EntityHolystoneFX extends EntityFX {
	float				initialScale;
	float				angleOffset;
	static final float	fadetime	= 20f;

	BlockHolystone block;
	
	public EntityHolystoneFX(World world, BlockHolystone block, double x, double y, double z) {
		super(world, x, y, z, 0, 0, 0);

		// particleScale = 1.0f + 1.0f * General.rand.nextFloat();
		// particleScale = 1.0f;
		initialScale = 1.0f + 1.0f * General.rand.nextFloat();
		angleOffset = rand.nextFloat() * 360;

		particleMaxAge = (int) (Math.random() * 10.0D) + 80;

		setPosition(x, y, z);
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;

		noClip = true;

		this.block = block;		
	}

	@Override
	public int getFXLayer() {
		return 1;
	}

	@Override
	public void renderParticle(Tessellator tessellator, float partialTick, float rotX, float rotXZ, float rotZ, float rotYZ, float rotXY) 
	{		
		
		particleScale = 0.25f + initialScale * (float) Math.sin((particleAge+angleOffset) / 180.f);

		if (particleAge < fadetime)
			particleAlpha = particleAge / fadetime;
		else if (particleAge + fadetime >= particleMaxAge)
			particleAlpha = (particleMaxAge - particleAge) / fadetime;
		else
			particleAlpha = 1.0f;

		//super.renderParticle(tessellator, partialTick, rotX, rotXZ, rotZ, rotYZ, rotXY);
		
		float var8 = block.iconStar.getMinU();
        float var9 = block.iconStar.getMaxU();
        float var10 = block.iconStar.getMinV();
        float var11 = block.iconStar.getMaxV();
        float var12 = 0.1F * this.particleScale;
        float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTick - interpPosX);
        float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTick - interpPosY);
        float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTick - interpPosZ);
        float var16 = 1.0F;
        tessellator.setColorRGBA_F(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha);
        tessellator.addVertexWithUV((double)(var13 - rotX * var12 - rotYZ * var12), (double)(var14 - rotXZ * var12), (double)(var15 - rotZ * var12 - rotXY * var12), (double)var9, (double)var11);
        tessellator.addVertexWithUV((double)(var13 - rotX * var12 + rotYZ * var12), (double)(var14 + rotXZ * var12), (double)(var15 - rotZ * var12 + rotXY * var12), (double)var9, (double)var10);
        tessellator.addVertexWithUV((double)(var13 + rotX * var12 + rotYZ * var12), (double)(var14 + rotXZ * var12), (double)(var15 + rotZ * var12 + rotXY * var12), (double)var8, (double)var10);
        tessellator.addVertexWithUV((double)(var13 + rotX * var12 - rotYZ * var12), (double)(var14 - rotXZ * var12), (double)(var15 + rotZ * var12 - rotXY * var12), (double)var8, (double)var11);

	}

	@Override
	public void onUpdate() {
		if (particleAge++ >= particleMaxAge) {
			setDead();
			return;
		}

	}
}
