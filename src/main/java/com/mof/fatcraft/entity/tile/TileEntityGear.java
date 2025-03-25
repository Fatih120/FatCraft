package com.mof.fatcraft.entity.tile;

import net.minecraft.tileentity.TileEntity;

public class TileEntityGear extends TileEntity {

    private int rotationStep = 0; // Keeps track of the current rotation frame

    public void updateEntity() {
        // Increment rotation step (looping back to 0 after max steps)
        rotationStep = (rotationStep + 1) % 64; // Assuming 64 frames of rotation
        markDirty();
    }

    public int getRotationStep() {
        return rotationStep;
    }
}
