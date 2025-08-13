package com.вувуеуdetective.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.вувуеуdetective.game.ai.OllamaClient;
import com.вувуеуdetective.game.entities.NPC;
import com.вувуеуdetective.game.ui.DialogueSystem;
import com.вувуеуdetective.game.world.SimpleMap;

/** Medieval Detective Game - Main class */
public class MainGame extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    private SimpleMap map;
    
    // Simple player (red circle)
    private float playerX = 100;
    private float playerY = 100;
    private float playerSpeed = 150f; // pixels per second
    
    // NPCs list
    private Array<NPC> npcs;
    
    // AI and dialogue systems
    private OllamaClient ollamaClient;
    private DialogueSystem dialogueSystem;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        map = new SimpleMap();
        
        // Set window title
        Gdx.graphics.setTitle("Medieval Detective - Alpha");
        
        // Start player in guaranteed walkable area (tile 5,7 should be empty)
        playerX = 5 * SimpleMap.TILE_SIZE + SimpleMap.TILE_SIZE/2;
        playerY = 7 * SimpleMap.TILE_SIZE + SimpleMap.TILE_SIZE/2;
        
        // Initialize AI and dialogue systems
        ollamaClient = new OllamaClient();
        dialogueSystem = new DialogueSystem();
        
        // Create NPCs
        createNPCs();
        
        System.out.println("Game initialized! Click on NPCs to talk to them.");
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        
        // Handle input (player movement)
        handleInput(deltaTime);
        
        // Update NPCs
        updateNPCs(deltaTime);
        
        // Clear screen with dark background (medieval atmosphere)
        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);
        
        // Start rendering shapes
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        
        // Draw the map
        map.render(shapeRenderer);
        
        // Draw NPCs
        renderNPCs();
        
        // Draw player (red circle) - draw last so it's on top
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(playerX, playerY, 12f);
        
        // Draw dialogue box (if active)
        dialogueSystem.render(shapeRenderer);
        
        // End rendering
        shapeRenderer.end();
    }
    
    private void handleInput(float deltaTime) {
        // Handle E key for NPC interaction
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            handleInteraction();
        }
        
        // Handle dialogue closing with ESC key
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (dialogueSystem.isActive()) {
                dialogueSystem.closeDialogue();
                System.out.println("Dialogue closed.");
            }
        }
        
        // Don't move player if dialogue is active
        if (dialogueSystem.isActive()) {
            return; // Skip movement when talking
        }
        
        // WASD or Arrow keys movement
        float newX = playerX;
        float newY = playerY;
        
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            newX -= playerSpeed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            newX += playerSpeed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            newY += playerSpeed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            newY -= playerSpeed * deltaTime;
        }
        
        // Enable collision detection - check if new position is walkable
        int tileX = map.worldToTileX(newX);
        int tileY = map.worldToTileY(newY);
        
        // Only move if the destination tile is walkable
        if (map.isWalkable(tileX, tileY)) {
            playerX = newX;
            playerY = newY;
        }
    }
    
    /**
     * Handle E key interaction - check if player is near NPC
     */
    private void handleInteraction() {
        // If dialogue is active, close it
        if (dialogueSystem.isActive()) {
            dialogueSystem.closeDialogue();
            System.out.println("Dialogue closed.");
            return;
        }
        
        // Check if player is near any NPC
        for (NPC npc : npcs) {
            if (isPlayerNearNPC(npc)) {
                startDialogueWithNPC(npc);
                return; // Found nearby NPC, stop checking
            }
        }
        
        // No NPC nearby
        System.out.println("No one nearby to talk to. Get closer to an NPC and press E.");
    }
    
    /**
     * Check if player is close enough to interact with NPC
     */
    private boolean isPlayerNearNPC(NPC npc) {
        // Calculate distance from player to NPC
        float dx = playerX - npc.getX();
        float dy = playerY - npc.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        
        // Can interact if within interaction distance
        float interactionDistance = 50f; // 50 pixels
        return distance <= interactionDistance;
    }
    
    /**
     * Start AI dialogue with clicked NPC
     */
    private void startDialogueWithNPC(NPC npc) {
        System.out.println("Starting dialogue with " + npc.getName());
        
        // Get NPC role based on name for AI context
        String npcRole = getNPCRole(npc.getName());
        
        // Generate greeting from AI
        String aiResponse = ollamaClient.getNPCGreeting(npc.getName(), npcRole);
        
        // Show dialogue
        dialogueSystem.showDialogue(npc.getName(), aiResponse);
    }
    
    /**
     * Get NPC role for AI context
     */
    private String getNPCRole(String npcName) {
        switch (npcName.toLowerCase()) {
            case "blacksmith": return "blacksmith";
            case "innkeeper": return "innkeeper";
            case "merchant": return "merchant";
            default: return "villager";
        }
    }

    /**
     * Create NPCs and place them on the map
     */
    private void createNPCs() {
        npcs = new Array<>();
        
        // Create villagers in different locations
        // Each NPC: position, name, color
        
        // Blacksmith (blue) - in walkable area near left buildings
        NPC blacksmith = new NPC(
            6 * SimpleMap.TILE_SIZE + SimpleMap.TILE_SIZE/2,  // X position (tile 6)
            6 * SimpleMap.TILE_SIZE + SimpleMap.TILE_SIZE/2,  // Y position (tile 6)
            "Blacksmith",
            Color.BLUE,
            map
        );
        npcs.add(blacksmith);
        
        // Innkeeper (green) - in center walkable area
        NPC innkeeper = new NPC(
            12 * SimpleMap.TILE_SIZE + SimpleMap.TILE_SIZE/2, // X position (tile 12)
            6 * SimpleMap.TILE_SIZE + SimpleMap.TILE_SIZE/2,  // Y position (tile 6) 
            "Innkeeper", 
            Color.GREEN,
            map
        );
        npcs.add(innkeeper);
        
        // Merchant (yellow) - in right walkable area
        NPC merchant = new NPC(
            17 * SimpleMap.TILE_SIZE + SimpleMap.TILE_SIZE/2, // X position (tile 17)
            8 * SimpleMap.TILE_SIZE + SimpleMap.TILE_SIZE/2,  // Y position (tile 8)
            "Merchant",
            Color.YELLOW,
            map
        );
        npcs.add(merchant);
        
        System.out.println("Created " + npcs.size + " NPCs");
    }
    
    /**
     * Update all NPCs - handle their AI and movement
     */
    private void updateNPCs(float deltaTime) {
        for (NPC npc : npcs) {
            npc.update(deltaTime);
        }
    }
    
    /**
     * Render all NPCs
     */
    private void renderNPCs() {
        for (NPC npc : npcs) {
            npc.render(shapeRenderer);
        }
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
