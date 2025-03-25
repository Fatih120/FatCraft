package com.mof.fatcraft.client.renderer;

import com.mof.fatcraft.Main;
import com.mof.fatcraft.entity.EntityTeeterboard;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.Tessellator;

public class RenderTeeterboard extends Render {
    private static final ResourceLocation texture = new ResourceLocation("yourmodid:textures/entity/teeterboard.png");

    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        EntityTeeterboard teeterboard = (EntityTeeterboard) entity;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y + 0.5f, (float)z);
        GL11.glRotatef(entity.rotationYaw, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-teeterboard.getTilt(), 0.0F, 0.0F, 1.0F); // Invert tilt for rendering

        this.bindEntityTexture(entity);

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        // Top face
        tessellator.addVertexWithUV(-2.5F, 0.125F, -0.5F, 0, 0);
        tessellator.addVertexWithUV(2.5F, 0.125F, -0.5F, 1, 0);
        tessellator.addVertexWithUV(2.5F, 0.125F, 0.5F, 1, 0.2);
        tessellator.addVertexWithUV(-2.5F, 0.125F, 0.5F, 0, 0.2);
        // Bottom face
        tessellator.addVertexWithUV(-2.5F, -0.125F, 0.5F, 0, 0.2);
        tessellator.addVertexWithUV(2.5F, -0.125F, 0.5F, 1, 0.2);
        tessellator.addVertexWithUV(2.5F, -0.125F, -0.5F, 1, 0.4);
        tessellator.addVertexWithUV(-2.5F, -0.125F, -0.5F, 0, 0.4);
        // Side faces
        tessellator.addVertexWithUV(-2.5F, -0.125F, -0.5F, 0, 0.4);
        tessellator.addVertexWithUV(2.5F, -0.125F, -0.5F, 1, 0.4);
        tessellator.addVertexWithUV(2.5F, 0.125F, -0.5F, 1, 0.6);
        tessellator.addVertexWithUV(-2.5F, 0.125F, -0.5F, 0, 0.6);

        tessellator.addVertexWithUV(-2.5F, -0.125F, 0.5F, 0, 0.6);
        tessellator.addVertexWithUV(-2.5F, 0.125F, 0.5F, 0, 0.8);
        tessellator.addVertexWithUV(2.5F, 0.125F, 0.5F, 1, 0.8);
        tessellator.addVertexWithUV(2.5F, -0.125F, 0.5F, 1, 0.6);
        tessellator.draw();

        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
