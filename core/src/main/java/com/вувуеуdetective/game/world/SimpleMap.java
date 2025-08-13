package com.вувуеуdetective.game.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Simple map system using colored rectangles as tiles
 * This is a placeholder before we add real graphics
 */
public class SimpleMap {

    // Map dimensions
    public static final int MAP_WIDTH = 20;
    public static final int MAP_HEIGHT = 15;
    public static final int TILE_SIZE = 32; // pixels

    // Tile types
    public static final int EMPTY = 0;      // Walkable floor
    public static final int WALL = 1;       // Wall/obstacle
    public static final int WATER = 2;      // Water (decoration)
    public static final int TREE = 3;       // Tree (decoration)

    // Simple medieval village map
    private int[][] mapData = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1},
        {1, 0, 1, 0, 1, 0, 0, 3, 3, 0, 0, 3, 0, 1, 0, 0, 1, 0, 0, 1},
        {1, 0, 1, 0, 1, 0, 0, 3, 3, 0, 0, 3, 0, 1, 0, 0, 1, 0, 0, 1},
        {1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 2, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 3, 0, 0, 0, 2, 0, 0, 2, 0, 0, 0, 0, 3, 0, 0, 0, 1},
        {1, 0, 0, 3, 0, 0, 0, 2, 0, 0, 2, 0, 0, 0, 0, 3, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    /**
     * Get tile type at specific coordinates
     */
    public int getTileType(int x, int y) {
        if (x < 0 || x >= MAP_WIDTH || y < 0 || y >= MAP_HEIGHT) {
            return WALL; // Outside bounds = wall
        }
        return mapData[y][x];
    }

    /**
     * Check if tile is walkable (player can move here)
     */
    public boolean isWalkable(int x, int y) {
        int tileType = getTileType(x, y);
        return tileType == EMPTY; // Only empty tiles are walkable
    }

    /**
     * Convert world pixel coordinates to tile coordinates
     */
    public int worldToTileX(float worldX) {
        return (int) (worldX / TILE_SIZE);
    }

    public int worldToTileY(float worldY) {
        return (int) (worldY / TILE_SIZE);
    }

    /**
     * Convert tile coordinates to world pixel coordinates
     */
    public float tileToWorldX(int tileX) {
        return tileX * TILE_SIZE;
    }

    public float tileToWorldY(int tileY) {
        return tileY * TILE_SIZE;
    }

    /**
     * Render the map using simple colored rectangles
     */
    public void render(ShapeRenderer shapeRenderer) {
        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                float worldX = tileToWorldX(x);
                float worldY = tileToWorldY(y);

                // Set color based on tile type
                switch (getTileType(x, y)) {
                    case WALL:
                        shapeRenderer.setColor(Color.DARK_GRAY); // Dark gray walls
                        break;
                    case WATER:
                        shapeRenderer.setColor(Color.BLUE); // Blue water
                        break;
                    case TREE:
                        shapeRenderer.setColor(Color.GREEN); // Green trees
                        break;
                    case EMPTY:
                    default:
                        shapeRenderer.setColor(Color.LIGHT_GRAY); // Light gray floor
                        break;
                }

                // Draw the tile
                shapeRenderer.rect(worldX, worldY, TILE_SIZE, TILE_SIZE);
            }
        }
    }

    /**
     * Get total map width in pixels
     */
    public int getMapWidthInPixels() {
        return MAP_WIDTH * TILE_SIZE;
    }

    /**
     * Get total map height in pixels
     */
    public int getMapHeightInPixels() {
        return MAP_HEIGHT * TILE_SIZE;
    }
}
