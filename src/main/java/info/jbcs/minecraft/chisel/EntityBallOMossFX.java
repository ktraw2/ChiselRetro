package info.jbcs.minecraft.chisel;

import info.jbcs.minecraft.utilities.General;
import net.fybertech.chiselretro.Icon;
import net.fybertech.chiselretro.RetroUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityBallOMossFX extends EntityFX
{
	BlockSnakestoneObsidian	block;
	double					tx, ty, tz;
	double					speed;
	
	float u0,u1,v0,v1;
	Icon icon;
	Icon iconPlus=new Icon(){

		@Override
		@SideOnly(Side.CLIENT)
		public int getIconWidth() {
			return 0;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public int getIconHeight() {
			return 0;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public float getMinU() {			
			return u0;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public float getMaxU() {
			return u1;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public float getInterpolatedU(double d0) {
			return (float) (u0+d0*(u1-u0)/16.0f);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public float getMinV() {
			return v0;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public float getMaxV() {
			return v1;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public float getInterpolatedV(double d0) {
			return (float) (v0+d0*(v1-v0)/16.0f);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public String getIconName() {
			return "";
		}

		@Override
		public int getTextureNum() {
			// TODO - Fyber - Maybe need to fix this
			return 0;
		}
	
	};

	public EntityBallOMossFX(World world, double x, double y, double z) {
		super(world, x, y+0.5, z, 0, 0, 0);

		particleScale = 0.5f + 0.5f*General.rand.nextFloat();
		speed=0.4+0.4*General.rand.nextDouble();

		particleMaxAge = (int) (General.rand.nextDouble() * 10.0) + 5;
		if(General.rand.nextInt(10)==0)
			particleMaxAge+=General.rand.nextDouble()*40.0;

		motionX=(General.rand.nextDouble()-0.5)*0.7;
		motionY=(General.rand.nextDouble()*0.5)*0.7;
		motionZ=(General.rand.nextDouble()-0.5)*0.7;
		particleGravity=2.0f;
		
//		setParticleIcon(block.particles[General.rand.nextInt(block.particles.length)]);
		
		icon=RetroUtil.getIconFromDamage_FromAtlas(Chisel.itemBallOMoss, 0);
		float width=4.0f+General.rand.nextFloat()*8.0f;
		float uu=General.rand.nextFloat()*(16.0f-width);
		float vv=General.rand.nextFloat()*(16.0f-width);
		u0=icon.getInterpolatedU(uu);
		u1=icon.getInterpolatedU(uu+width);
		v0=icon.getInterpolatedV(vv);
		v1=icon.getInterpolatedV(vv+width);
		
	}

	@Override
	public int getFXLayer() {
		return 2;
	}
	

	@Override
	public void onUpdate() {
		super.onUpdate();

		double remaining=particleMaxAge-particleAge;
		
		if(remaining<5){
			particleAlpha=(float) (remaining/5.0);	
		} else{
			particleAlpha=1.0f;	
		}
	}
	
	
	@Override
	public String getTexture() 
	{
		//System.out.println(Chisel.itemBallOMoss.getTextureFile());;
		return Chisel.itemBallOMoss.getTextureFile();
	}
	
	
	@Override
	public void renderParticle(Tessellator tessellator, float partialTick, float rotX, float rotXZ, float rotZ, float rotYZ, float rotXY) 
	{
		GL11.glDepthMask(false);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
        
        float var8 = iconPlus.getMinU();
        float var9 = iconPlus.getMaxU();
        float var10 = iconPlus.getMinV();
        float var11 = iconPlus.getMaxV();
        
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

}
