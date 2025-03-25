package com.mof.fatcraft.client.renderer;

import com.mof.fatcraft.entity.tile.TileEntityGear;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

import java.awt.image.BufferedImage;

public class RenderTEGear extends TileEntitySpecialRenderer {
    private RenderGearSprite gearSprite;

    public RenderTEGear(RenderGearSprite sprite) {
        this.gearSprite = sprite;
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks) {
        if (!(te instanceof TileEntityGear)) return;

        TileEntityGear gear = (TileEntityGear) te;
        int frame = gear.getRotationStep();

        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);

        // Bind the texture
        this.bindTexture(TextureMap.locationBlocksTexture);

        // Get the rotated texture for the current frame
        BufferedImage rotatedTexture = gearSprite.getRotatedTexture(frame);

        // Render the gear as a flat quad on the block face
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();

        // Render on the block face (e.g., north face)
        tessellator.addVertexWithUV(0, 0, 0.005, 0, 1);
        tessellator.addVertexWithUV(1, 0, 0.005, 1, 1);
        tessellator.addVertexWithUV(1, 1, 0.005, 1, 0);
        tessellator.addVertexWithUV(0, 1, 0.005, 0, 0);

        tessellator.draw();

        GL11.glPopMatrix();
    }
}

