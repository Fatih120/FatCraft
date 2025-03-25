package com.mof.fatcraft.client.renderer;

import com.mof.fatcraft.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
public class RenderGearSprite extends TextureAtlasSprite {

    private static final int GEAR_ROTATION_STEPS = 64;
    private static final int ORIGINAL_GEAR_TEXTURE_SIZE = 32;
    private static final int MIDDLE_GEAR_TEXTURE_SIZE = 16;
    private static final int STANDARD_IMAGE_SIZE = 32;

    private BufferedImage[] rotatedTextures;

    public RenderGearSprite(String spriteName) {
        super(spriteName);
    }

    @Override
    public boolean load(IResourceManager manager, ResourceLocation location) {
        try {
            BufferedImage gearImage = ImageIO.read(manager.getResource(new ResourceLocation(Main.MODID, ":gear.png")).getInputStream());
            BufferedImage gearMiddleImage = ImageIO.read(manager.getResource(new ResourceLocation(Main.MODID, ":gearmiddle.png")).getInputStream());

            rotatedTextures = new BufferedImage[GEAR_ROTATION_STEPS];
            for (int i = 0; i < GEAR_ROTATION_STEPS; i++) {
                rotatedTextures[i] = generateGearTextureForRotation(gearImage, gearMiddleImage, i);
            }

            // Use the first rotated texture as the base texture
            BufferedImage baseTexture = rotatedTextures[0];
            this.width = baseTexture.getWidth();
            this.height = baseTexture.getHeight();
            int[][] imageData = new int[Minecraft.getMinecraft().gameSettings.mipmapLevels + 1][];
            imageData[0] = new int[width * height];
            baseTexture.getRGB(0, 0, width, height, imageData[0], 0, width);

            this.clearFramesTextureData();
            this.framesTextureData.add(imageData);

            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
    }

    private BufferedImage generateGearTextureForRotation(BufferedImage gearImage, BufferedImage gearMiddleImage, int rotationStep) {
        BufferedImage rotatedImage = new BufferedImage(STANDARD_IMAGE_SIZE, STANDARD_IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);
        float sinRotationAngle = (float) Math.sin((rotationStep / (float) GEAR_ROTATION_STEPS) * Math.PI * 2.0);
        float cosRotationAngle = (float) Math.cos((rotationStep / (float) GEAR_ROTATION_STEPS) * Math.PI * 2.0);

        for (int rotatedImageX = 0; rotatedImageX < STANDARD_IMAGE_SIZE; ++rotatedImageX) {
            for (int rotatedImageY = 0; rotatedImageY < STANDARD_IMAGE_SIZE; ++rotatedImageY) {
                float gearImageX = ((rotatedImageX / (STANDARD_IMAGE_SIZE - 1.0F)) - 0.5F) * (ORIGINAL_GEAR_TEXTURE_SIZE - 1.0F);
                float gearImageY = ((rotatedImageY / (STANDARD_IMAGE_SIZE - 1.0F)) - 0.5F) * (ORIGINAL_GEAR_TEXTURE_SIZE - 1.0F);
                float rotatedOffsetGearImageX = (cosRotationAngle * gearImageX) - (sinRotationAngle * gearImageY);
                float rotatedOffsetGearImageY = (cosRotationAngle * gearImageY) + (sinRotationAngle * gearImageX);
                int rotatedGearImageX = (int) (rotatedOffsetGearImageX + (ORIGINAL_GEAR_TEXTURE_SIZE / 2));
                int rotatedGearImageY = (int) (rotatedOffsetGearImageY + (ORIGINAL_GEAR_TEXTURE_SIZE / 2));

                int ARGB;
                if ((rotatedGearImageX >= 0) && (rotatedGearImageY >= 0) && (rotatedGearImageX < ORIGINAL_GEAR_TEXTURE_SIZE) && (rotatedGearImageY < ORIGINAL_GEAR_TEXTURE_SIZE)) {
                    int gearDiv = STANDARD_IMAGE_SIZE / MIDDLE_GEAR_TEXTURE_SIZE;
                    int gearMiddleARGB = gearMiddleImage.getRGB(rotatedImageX / gearDiv, rotatedImageY / gearDiv);
                    ARGB = (gearMiddleARGB >>> 24) > 128 ? gearMiddleARGB : gearImage.getRGB(rotatedGearImageX, rotatedGearImageY);
                } else {
                    ARGB = 0;
                }

                rotatedImage.setRGB(rotatedImageX, rotatedImageY, ARGB);
            }
        }

        return rotatedImage;
    }

    public BufferedImage getRotatedTexture(int frame) {
        if (rotatedTextures == null || rotatedTextures.length == 0) {
            throw new IllegalStateException("Rotated textures not initialized!");
        }
        return rotatedTextures[frame % rotatedTextures.length];
    }

}
