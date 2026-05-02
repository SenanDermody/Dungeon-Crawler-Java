# 🏰 Dungeon Game

A text-based dungeon exploration game where you navigate through a grid while avoiding traps and collecting power-ups.

## 🎮 Game Overview

Navigate from the starting position `(0,0)` to the end `(E)` of the dungeon without stepping on traps `(X)`. Listen carefully for clicking sounds - they might warn you about nearby dangers!

## ✨ Features

- **Customizable grid size** (5x5 to 15x15)
- **4 difficulty levels** affecting trap and power-up density
- **Power-up system** - collect shields to absorb trap hits
- **Proximity warnings** - hear clicking when near traps
- **Move counter** - track how many steps you take
- **ASCII grid visualization** with numbered rows and columns

## 🎯 Difficulty Levels

| Difficulty | Traps | Shields | Challenge Level |
|------------|-------|---------|-----------------|
| Peaceful | 1% | None | Very Easy |
| Easy | 5% | 1% | Easy |
| Medium | 15% | 3% | Moderate |
| Hard | 30% | 5% | Challenging |

*Percentages are based on total grid area (gridSize × gridSize)*

## 🕹️ How to Play

### Controls
- **W** - Move Up
- **A** - Move Left  
- **S** - Move Down
- **D** - Move Right

### Game Elements
- `@` - Your character's current position
- `E` - Exit / Goal
- `X` - Trap (game ends if stepped on without shield)
- `P` - Power-up (grants one shield)
- `.` - Empty, safe tile
- `-` - Previously visited tile

### Special Mechanics
- **Shields** automatically protect you from one trap hit
- **Nearby traps** make clicking sounds when you're adjacent to them
- **Warning messages** appear when traps are detected nearby

## 🚀 Getting Started

### Prerequisites
- Java JDK 8 or higher
- Any terminal/console that supports Java

### Running the Game

1. **Compile the game:**
```bash
javac DungeonGame.java
