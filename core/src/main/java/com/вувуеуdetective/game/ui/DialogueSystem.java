package com.вувуеуdetective.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.вувуеуdetective.game.entities.NPC;

/**
 * Simple dialogue system for showing NPC conversations
 * Displays dialogue boxes when talking to NPCs
 */
public class DialogueSystem {
    
    // Dialogue box properties  
    private boolean isDialogueActive = false;
    private String currentSpeaker = "";
    private String currentText = "";
    
    // Simple text display (we'll improve this later)
    private static final int BOX_WIDTH = 600;
    private static final int BOX_HEIGHT = 150;
    private static final int BOX_X = 50;
    private static final int BOX_Y = 50;
    
    /**
     * Show dialogue from an NPC
     */
    public void showDialogue(String npcName, String text) {
        isDialogueActive = true;
        currentSpeaker = npcName;
        currentText = text;
        
        System.out.println("[" + npcName + "]: " + text);
    }
    
    /**
     * Close the current dialogue
     */
    public void closeDialogue() {
        isDialogueActive = false;
        currentSpeaker = "";
        currentText = "";
    }
    
    /**
     * Check if dialogue is currently showing
     */
    public boolean isActive() {
        return isDialogueActive;
    }
    
    /**
     * Render the dialogue box (simple colored rectangle for now)
     */
    public void render(ShapeRenderer shapeRenderer) {
        if (!isDialogueActive) return;
        
        // Draw dialogue box background
        shapeRenderer.setColor(new Color(0, 0, 0, 0.8f)); // Semi-transparent black
        shapeRenderer.rect(BOX_X, BOX_Y, BOX_WIDTH, BOX_HEIGHT);
        
        // Draw border
        shapeRenderer.setColor(Color.WHITE);
        // Top border
        shapeRenderer.rect(BOX_X, BOX_Y + BOX_HEIGHT - 2, BOX_WIDTH, 2);
        // Bottom border  
        shapeRenderer.rect(BOX_X, BOX_Y, BOX_WIDTH, 2);
        // Left border
        shapeRenderer.rect(BOX_X, BOX_Y, 2, BOX_HEIGHT);
        // Right border
        shapeRenderer.rect(BOX_X + BOX_WIDTH - 2, BOX_Y, 2, BOX_HEIGHT);
        
        // Note: For now we just draw a box
        // Later we'll add proper text rendering
    }
    
    /**
     * Handle input during dialogue (like closing with ESC or ENTER)
     */
    public void handleInput() {
        // We'll implement this when we add input handling
        // For now, dialogue can be closed by clicking elsewhere
    }
    
    /**
     * Get simple debug info about current dialogue
     */
    public String getDebugInfo() {
        if (!isDialogueActive) return "No dialogue";
        return currentSpeaker + ": " + 
               (currentText.length() > 30 ? 
                currentText.substring(0, 30) + "..." : 
                currentText);
    }
}
