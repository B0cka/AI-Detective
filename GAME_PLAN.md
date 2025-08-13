# Medieval Detective Game - Development Plan

## 🎯 Core Game Concept
- **Genre**: Medieval detective/investigation game
- **Art Style**: Don't Starve Together (hand-drawn, dark atmosphere)
- **AI Integration**: Ollama for dynamic NPCs and story generation
- **Platform**: Desktop (Windows/Mac/Linux) via libGDX

## 🏗️ Project Structure

```
medieval-detective/
├── core/src/main/java/com/вувуеуdetective/game/
│   ├── MainGame.java                 # Main game entry point
│   ├── screens/                      # Different game screens
│   │   ├── MenuScreen.java          # Main menu
│   │   ├── GameScreen.java          # Main gameplay
│   │   └── DialogueScreen.java      # Conversations with NPCs
│   ├── entities/                     # Game objects
│   │   ├── Player.java              # Player character
│   │   ├── NPC.java                 # Non-player characters
│   │   ├── Clue.java                # Investigation clues
│   │   └── InteractiveObject.java   # Objects player can examine
│   ├── world/                        # Game world system
│   │   ├── GameMap.java             # Map management
│   │   ├── Location.java            # Different locations
│   │   └── Camera.java              # Camera controls
│   ├── ai/                          # AI integration
│   │   ├── OllamaClient.java        # Ollama API client
│   │   ├── DialogueGenerator.java   # AI-generated conversations
│   │   └── CaseGenerator.java       # AI-generated mysteries
│   ├── systems/                     # Game systems
│   │   ├── InputHandler.java        # Input processing
│   │   ├── CollisionSystem.java     # Collision detection
│   │   └── InventorySystem.java     # Item management
│   └── utils/                       # Utilities
│       ├── Constants.java           # Game constants
│       └── AssetManager.java        # Asset loading
└── assets/                          # Game assets
    ├── graphics/                    # Images and sprites
    ├── sounds/                      # Audio files
    └── data/                        # Game data files
```

## 🎮 Phase 1: Basic Gameplay (Current Focus)
1. **✅ Project Setup** - Fixed Java compatibility
2. **🔄 Basic Game Structure** - Screen management
3. **🔄 Simple Map with Shapes** - Placeholder graphics
4. **🔄 Player Movement** - Basic character control
5. **🔄 NPC Interaction** - Click to talk
6. **🔄 Simple Dialogue System** - Text display

## 🎨 Phase 2: Graphics & Polish
1. **Don't Starve Art Style** - Hand-drawn sprites
2. **Animations** - Character movement, idle states
3. **UI Design** - Medieval-themed interface
4. **Lighting Effects** - Atmospheric lighting
5. **Particle Effects** - Dust, fire, magic

## 🤖 Phase 3: AI Integration
1. **Ollama Connection** - REST API integration
2. **Dynamic NPCs** - AI-generated personalities
3. **Case Generation** - Random mysteries
4. **Smart Dialogue** - Context-aware conversations
5. **Adaptive Story** - Story changes based on player choices

## 🕵️ Phase 4: Detective Mechanics
1. **Clue System** - Find and collect evidence
2. **Investigation Journal** - Track discoveries
3. **Deduction System** - Connect clues
4. **Multiple Cases** - Various mystery types
5. **Reputation System** - Player detective skills

## 📱 Current Implementation Plan

### Step 1: Basic World (Today)
- Create simple map with colored rectangles
- Player character as colored circle
- NPCs as different colored shapes
- Basic movement with arrow keys/WASD

### Step 2: Interaction System
- Click on NPCs to interact
- Simple text dialogue boxes
- Basic inventory system

### Step 3: AI Foundation
- Connect to local Ollama instance
- Generate simple NPC responses
- Basic case generation

## 🎯 Success Metrics
- ✅ Player can move around map
- ✅ Player can interact with NPCs
- ✅ AI generates believable dialogue
- ✅ Game feels atmospheric and engaging
- ✅ Core detective mechanics work

Let's start with Phase 1 and build from there!
