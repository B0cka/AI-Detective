package com.вувуеуdetective.game.ai;

import com.badlogic.gdx.Net;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

/**
 * Simple HTTP client for Ollama API
 * Handles communication between game and local Ollama instance
 */
public class OllamaClient {
    
    private static final String OLLAMA_URL = "http://localhost:11434/api/generate";
    private static final String MODEL_NAME = "qwen2.5:0.5b";
    
    private Json json;
    
    public OllamaClient() {
        json = new Json();
    }
    
    /**
     * Send a prompt to Ollama and get AI response
     * This is synchronous - will block until response received
     */
    public String ask(String prompt) {
        try {
            return sendRequest(prompt);
        } catch (Exception e) {
            System.err.println("Ollama request failed: " + e.getMessage());
            return getFallbackResponse();
        }
    }
    
    /**
     * Send HTTP request to Ollama API
     */
    private String sendRequest(String prompt) throws Exception {
        // Build request body
        OllamaRequest request = new OllamaRequest();
        request.model = MODEL_NAME;
        request.prompt = prompt;
        request.stream = false; // We want complete response, not streaming
        
        String requestBody = json.toJson(request);
        
        // Create HTTP request
        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        httpRequest.setUrl(OLLAMA_URL);
        httpRequest.setHeader("Content-Type", "application/json");
        httpRequest.setContent(requestBody);
        
        // This is a simplified synchronous approach
        // In real game, you might want to use async callbacks
        return sendSynchronousRequest(httpRequest);
    }
    
    /**
     * Simplified synchronous HTTP request
     * Note: This blocks the game thread! In production, use async.
     */
    private String sendSynchronousRequest(Net.HttpRequest httpRequest) throws Exception {
        // For now, we'll use a simple approach
        // TODO: Implement proper async HTTP handling
        
        // Placeholder - we'll implement this properly next
        // For now, return a test response
        return "Hello, detective! I'm ready to help with your investigation.";
    }
    
    /**
     * Fallback response when Ollama is not available
     */
    private String getFallbackResponse() {
        String[] fallbacks = {
            "I'm sorry, I cannot talk right now...",
            "My thoughts are elsewhere at the moment.",
            "Perhaps we can speak later.",
            "I'm not feeling very talkative today."
        };
        return fallbacks[(int)(Math.random() * fallbacks.length)];
    }
    
    /**
     * Build contextual prompt for NPC dialogue
     */
    public String askNPC(String npcName, String npcRole, String playerQuestion) {
        String contextPrompt = String.format(
            "You are %s, a %s in a medieval village. " +
            "A detective is investigating something in the village. " +
            "Respond to them in character. Keep it brief (1-2 sentences). " +
            "Detective asks: %s",
            npcName, npcRole, playerQuestion
        );
        
        return ask(contextPrompt);
    }
    
    /**
     * Simple greeting from NPC
     */
    public String getNPCGreeting(String npcName, String npcRole) {
        String greetingPrompt = String.format(
            "You are %s, a %s in a medieval village. " +
            "Greet a detective who just approached you. " +
            "Be brief and in character (1 sentence).",
            npcName, npcRole
        );
        
        return ask(greetingPrompt);
    }
    
    /**
     * Check if Ollama is running and accessible
     */
    public boolean isOllamaAvailable() {
        try {
            // Try a simple request to check connectivity
            // This would be a ping-like request to Ollama
            return true; // Placeholder
        } catch (Exception e) {
            return false;
        }
    }
    
    // Inner classes for JSON serialization
    public static class OllamaRequest {
        public String model;
        public String prompt;
        public boolean stream;
    }
    
    public static class OllamaResponse {
        public String response;
        public boolean done;
    }
}
