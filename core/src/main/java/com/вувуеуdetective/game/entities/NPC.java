package com.вувуеуdetective.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.вувуеуdetective.game.world.SimpleMap;

/**
 * Non-Player Character (NPC) - villagers, suspects, witnesses
 * This is a placeholder before AI integration
 */
public class NPC {
    
    // NPC position (in world coordinates)
    private float x, y;
    
    // NPC properties
    private static final float RADIUS = 10f;
    private static final float MOVE_SPEED = 50f; // Slower than player
    
    // Movement AI variables
    private float moveTimer = 0f;
    private float moveDirection = 0f; // 0=right, 1=up, 2=left, 3=down
    private boolean isMoving = false;
    
    // NPC info
    private String name;
    private Color color;
    
    // Reference to map for collision detection
    private SimpleMap map;
    
    public NPC(float startX, float startY, String name, Color color, SimpleMap map) {
        this.x = startX;
        this.y = startY;
        this.name = name;
        this.color = color;
        this.map = map;
        
        // Random initial state
        this.moveTimer = MathUtils.random(0f, 3f);
        this.moveDirection = MathUtils.random(0, 3);
    }
    
    /**
     * Update NPC - handles AI movement and behavior
     */
    public void update(float deltaTime) {
        // Simple AI: Move randomly, then stop, then move again
        moveTimer -= deltaTime;
        
        if (moveTimer <= 0f) {
            // Time to change behavior
            if (isMoving) {
                // Stop moving
                isMoving = false;
                moveTimer = MathUtils.random(1f, 3f); // Stand still for 1-3 seconds
            } else {
                // Start moving
                isMoving = true;
                moveDirection = MathUtils.random(0, 3); // Pick random direction
                moveTimer = MathUtils.random(0.5f, 2f); // Move for 0.5-2 seconds
            }
        }
        
        // Move if supposed to be moving
        if (isMoving) {
            moveInDirection(deltaTime, moveDirection);
        }
    }
    
    /**
     * Move NPC in specified direction with collision detection
     */
    private void moveInDirection(float deltaTime, float direction) {
        float newX = x;
        float newY = y;
        
        // Calculate movement based on direction
        switch ((int)direction) {
            case 0: // Right
                newX += MOVE_SPEED * deltaTime;
                break;
            case 1: // Up  
                newY += MOVE_SPEED * deltaTime;
                break;
            case 2: // Left
                newX -= MOVE_SPEED * deltaTime;
                break;
            case 3: // Down
                newY -= MOVE_SPEED * deltaTime;
                break;
        }
        
        // Check collision - only move if destination is walkable
        int tileX = map.worldToTileX(newX);
        int tileY = map.worldToTileY(newY);
        
        if (map.isWalkable(tileX, tileY)) {
            x = newX;
            y = newY;
        } else {
            // Hit a wall - stop moving and pick new direction soon
            isMoving = false;
            moveTimer = MathUtils.random(0.2f, 0.5f);
        }
    }
    
    /**
     * Render NPC as colored circle
     */
    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(color);
        shapeRenderer.circle(x, y, RADIUS);
        
        // Add a small white dot in center to show it's an NPC
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.circle(x, y, 2f);
    }
    
    /**
     * Check if player is close enough to interact with this NPC
     */
    public boolean isPlayerNearby(float playerX, float playerY, float interactionDistance) {
        float distance = (float) Math.sqrt((x - playerX) * (x - playerX) + (y - playerY) * (y - playerY));
        return distance <= interactionDistance;
    }
    
    /**
     * Get dialogue from NPC (placeholder for future AI integration)
     */
    public String getDialogue() {
        // Simple random responses for now
        String[] responses = {
            "Greetings, detective! Have you found any clues?",
            "I heard strange noises last night...",
            "The blacksmith has been acting suspicious lately.",
            "Welcome to our village, stranger.",
            "Be careful around here, detective.",
            "Have you spoken to the innkeeper yet?"
        };
        
        return responses[MathUtils.random(0, responses.length - 1)];
    }
    
    // Getters
    public float getX() { return x; }
    public float getY() { return y; }
    public String getName() { return name; }
    public float getRadius() { return RADIUS; }
    
    // Setters
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
