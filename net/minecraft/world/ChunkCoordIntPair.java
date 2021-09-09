package net.minecraft.world;

import net.minecraft.util.BlockPos;

public class ChunkCoordIntPair
{
    /** The X position of this Chunk Coordinate Pair */
    public final int chunkXPos;

    /** The Z position of this Chunk Coordinate Pair */
    public final int chunkZPos;
    private static final String __OBFID = "CL_00000133";

    public ChunkCoordIntPair(int x, int z)
    {
        this.chunkXPos = x;
        this.chunkZPos = z;
    }

    /**
     * converts a chunk coordinate pair to an integer (suitable for hashing)
     */
    public static long chunkXZ2Int(int x, int z)
    {
        return (long)x & 4294967295L | ((long)z & 4294967295L) << 32;
    }

    public int hashCode()
    {
        int var1 = 1664525 * this.chunkXPos + 1013904223;
        int var2 = 1664525 * (this.chunkZPos ^ -559038737) + 1013904223;
        return var1 ^ var2;
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
        {
            return true;
        }
        else if (!(p_equals_1_ instanceof ChunkCoordIntPair))
        {
            return false;
        }
        else
        {
            ChunkCoordIntPair var2 = (ChunkCoordIntPair)p_equals_1_;
            return this.chunkXPos == var2.chunkXPos && this.chunkZPos == var2.chunkZPos;
        }
    }

    public int getCenterXPos()
    {
        return (this.chunkXPos << 4) + 8;
    }

    public int getCenterZPosition()
    {
        return (this.chunkZPos << 4) + 8;
    }

    /**
     * Get the first world X coordinate that belongs to this Chunk
     */
    public int getXStart()
    {
        return this.chunkXPos << 4;
    }

    /**
     * Get the first world Z coordinate that belongs to this Chunk
     */
    public int getZStart()
    {
        return this.chunkZPos << 4;
    }

    /**
     * Get the last world X coordinate that belongs to this Chunk
     */
    public int getXEnd()
    {
        return (this.chunkXPos << 4) + 15;
    }

    /**
     * Get the last world Z coordinate that belongs to this Chunk
     */
    public int getZEnd()
    {
        return (this.chunkZPos << 4) + 15;
    }

    /**
     * Get the World coordinates of the Block with the given Chunk coordinates relative to this chunk
     *  
     * @param x X coordinate of the Block in this chunk (0-15)
     * @param y Y coordinate of the Block
     * @param z Z coordinate of the Block in this chunk (0-15)
     */
    public BlockPos getBlock(int x, int y, int z)
    {
        return new BlockPos((this.chunkXPos << 4) + x, y, (this.chunkZPos << 4) + z);
    }

    /**
     * Get the coordinates of the Block in the center of this chunk with the given Y coordinate
     *  
     * @param y Y coordinate
     */
    public BlockPos getCenterBlock(int y)
    {
        return new BlockPos(this.getCenterXPos(), y, this.getCenterZPosition());
    }

    public String toString()
    {
        return "[" + this.chunkXPos + ", " + this.chunkZPos + "]";
    }
}
