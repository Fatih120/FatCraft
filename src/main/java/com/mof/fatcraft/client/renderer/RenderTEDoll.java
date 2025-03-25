package com.mof.fatcraft.client.renderer;

import com.mof.fatcraft.Main;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderTEDoll extends TileEntitySpecialRenderer {
    private ModelBiped model;
    private ResourceLocation texture;

    public RenderTEDoll() {
        model = new ModelBiped();
        texture = new ResourceLocation(Main.MODID, "textures/entity/doll.png");
    }


    @Override
    public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float partialTicks) {
        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.0F, (float) z + 0.5F); // Raised y position back to 0.5F
        GL11.glRotatef(180, 0, 0, 1);
        GL11.glScalef(0.9F, 0.9F, 0.9F);

        bindTexture(texture);

        // Set the sitting pose
        float f6 = 0.0625F;
        model.isRiding = true;
        model.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, f6, null);

        // Render the model
        model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, f6);

        GL11.glPopMatrix();
    }

    // standing
/*    @Override
    public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float partialTicks) {
        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glRotatef(180, 0, 0, 1);
        GL11.glScalef(1F, 1F, 1F);

        bindTexture(texture);

        model.render(null, 0, 0, 0, 0, 0, 0.0625F);

        GL11.glPopMatrix();
    }*/
}
