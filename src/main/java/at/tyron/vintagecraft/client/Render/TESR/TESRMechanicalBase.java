package at.tyron.vintagecraft.Client.Render.TESR;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3i;

import org.lwjgl.opengl.GL11;

import at.tyron.vintagecraft.ModInfo;
import at.tyron.vintagecraft.Client.Model.ModelGearComponents;
import at.tyron.vintagecraft.Client.Model.ModelStonePot;
import at.tyron.vintagecraft.TileEntity.Mechanics.TEAngledGearBox;

public abstract class TESRMechanicalBase extends TESRBase {

	ModelGearComponents modelcagegear = new ModelGearComponents();

	
	public void renderAngledGearBox(TEAngledGearBox te, float angle, float posX, float posY, float posZ) {
		if (te.refreshModel) {
			te.refreshModel = false;
			//System.out.println("refreshed ");
			modelcagegear.initComponents();
		}
		
		EnumFacing facing = EnumFacing.NORTH;
		if (te.orientation != null) {
			facing = te.orientation;
		}
	
		// 0 = S, 1 = W, 2 = N, 3 = E
		// =>
		// 2 = S, 3 = W, 0 = N, 1 = E
		int pegGearFacing = (facing.getHorizontalIndex() + 2) % 4;
		
		float pegGearxAxis = (pegGearFacing == 3 || pegGearFacing == 1) ? 1f : 0;
		float pegGearzAxis = (pegGearFacing == 2 || pegGearFacing == 0) ? 1f : 0;
		//int pegDir = (pegGearFacing == 1 || pegGearFacing == 2) ? -1 : 1;
		
		float pegGearFacingAngle = -pegGearFacing * 90f;
		
		GL11.glPushMatrix();
		
			GL11.glTranslatef(posX + 0.5f, posY + 0.49f, posZ + 0.5f);
			GL11.glRotatef(te.getAngle(), pegGearxAxis, 0f, pegGearzAxis);
			GL11.glRotatef(pegGearFacingAngle, 0f, 1f, 0f);
			GL11.glTranslatef(-0.5f, -0.49f, -0.5f);
			renderPegGear();
			
		GL11.glPopMatrix();
		

		
		
		// Cage Gear
		//System.out.println(te.gearOrientations[1]);
		if (te.cagegearOrientation == null) return;
		
		int cageGearFacing = (te.cagegearOrientation.getHorizontalIndex() + 2) % 4;
		float cageGearxAxis = (cageGearFacing == 3 || cageGearFacing == 1) ? 1f : 0;
		float cageGearzAxis = (cageGearFacing == 2 || cageGearFacing == 0) ? 1f : 0;

		int cageDir = (te.clockwise && te.cagegearOrientation == EnumFacing.NORTH) ? -1 : 1;
		float cageGearFacingAngle = -cageGearFacing * 90f;
		
		//System.out.println(te.clockwise);
		
		
		GL11.glPushMatrix();
		
			GL11.glTranslatef(posX + 0.5f, posY + 0.49f, posZ + 0.5f);
			GL11.glRotatef(cageDir * te.getAngle() - 18, cageGearxAxis, 0f, cageGearzAxis);
			GL11.glRotatef(cageGearFacingAngle, 0f, 1f, 0f);
			GL11.glTranslatef(-0.5f, -0.49f, -0.5f);
			
			renderCageGear();
		
		GL11.glPopMatrix();

	}
	
	
	public void renderCageGear() {
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(ModInfo.ModID, "textures/blocks/planks/oak.png"));
		
		GL11.glPushMatrix();
			
			modelcagegear.renderBase();
			modelcagegear.renderPegs();
			
			GL11.glPushMatrix();			
				GL11.glTranslatef(0.5f, -0.225f, 0.005f);
				GL11.glRotatef(45, 0, 0, 0.5f);
				modelcagegear.renderBase();
				modelcagegear.renderPegs();
				
				GL11.glTranslatef(0f, 0f, 0.28f);
				modelcagegear.renderBase();
			GL11.glPopMatrix();
			
			GL11.glTranslatef(0f, 0f, 0.28f);
			modelcagegear.renderBase();
		GL11.glPopMatrix();
	}

	
	public void renderPegGear() {
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(ModInfo.ModID, "textures/blocks/planks/oak.png"));
		
		GL11.glPushMatrix();			
			modelcagegear.renderBase();
			modelcagegear.renderPegs();
		
			GL11.glPushMatrix();			
				GL11.glTranslatef(0.5f, -0.225f, 0.005f);
				GL11.glRotatef(45, 0, 0, 0.5f);
				modelcagegear.renderBase();
				modelcagegear.renderPegs();
			GL11.glPopMatrix();
			
		GL11.glPopMatrix();
	}

	
}