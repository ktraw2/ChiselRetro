package info.jbcs.minecraft.chisel;

import info.jbcs.minecraft.utilities.General;
import net.fybertech.chiselretro.Icon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public class EntitySnakestoneObsidianFX extends EntityFX {
	BlockSnakestoneObsidian	block;
	double					tx, ty, tz;
	double					speed;

	Icon icon;
	
	public EntitySnakestoneObsidianFX(World world, BlockSnakestoneObsidian b, int x, int y, int z) {
		super(world, x + 0.5, y + 0.5, z + 0.5, 0, 0, 0);

		block = b;

		particleScale = 0.5f + 0.5f*General.rand.nextFloat();
		speed=0.04+0.04*General.rand.nextDouble();
		//speed = 0;

//		motionX = 0;
//		motionY = 0;
//		motionZ = 0;
		particleMaxAge = (int) (Math.random() * 10.0D) + 40;

		tx = x + General.rand.nextDouble();
		ty = y + General.rand.nextDouble();
		tz = z + General.rand.nextDouble();
		switch(General.rand.nextInt(6)){
		case 0: tx=x; break;
		case 1: tx=x+1; break;
		case 2: ty=y; break;
		case 3: ty=y+1; break;
		case 4: tz=z; break;
		case 5: tz=z+1; break;
		}
		
		double dx=(tx-posX)*3;
		double dy=(ty-posY)*3;
		double dz=(tz-posZ)*3;

		setPosition(posX + dx, posY + dy, posZ + dz);
		prevPosX=posX;
		prevPosY=posY;
		prevPosZ=posZ;
		
		noClip = true;
		
		// TODO - Fyber - Fix
		icon = block.particles[General.rand.nextInt(block.particles.length)];
	}

	@Override
	public int getFXLayer() {
		return 1;
	}

	@Override
	public void renderParticle(Tessellator tessellator, float partialTick, float rotX, float rotXZ, float rotZ, float rotYZ, float rotXY) {
		GL11.glDepthMask(false);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		//super.renderParticle(tessellator, partialTick, rotX, rotXZ, rotZ, rotYZ, rotXY);
		
		//GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture(block.getTextureFile()));
		
		float var8 = icon.getMinU();
        float var9 = icon.getMaxU();
        float var10 = icon.getMinV();
        float var11 = icon.getMaxV();
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
		
		double dx = tx - posX;
		double dy = ty - posY;
		double dz = tz - posZ;

		double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
		if (distance == 0) {
			setDead();
			return;
		}
		if(distance<0.4){
			particleAlpha=(float) (distance/0.4);	
		} else if(particleAge<20){
			particleAlpha=1.0f*particleAge/20;
		} else{
			particleAlpha=1.0f;	
		}

		if(distance<speed){
			speed=distance;
		}

		prevPosX=posX;
		prevPosY=posY;
		prevPosZ=posZ;
		
		double px=posX + dx / distance * speed;
		double py=posY + dy / distance * speed;
		double pz=posZ + dz / distance * speed;

		setPosition(px, py, pz);
	}
}
