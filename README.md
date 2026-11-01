# My Adventure Game

## Project Overview
My Adventure Game is a console-based, turn-based adventure game developed in Java for CS3354. Inspired by Slay the Spire, players choose a character, explore three increasingly difficult stages, fight enemies, and collect equipment and gold.

Phase 1 focuses on the game's core functionality and object-oriented design. Phase 2 will introduce a graphical user interface and additional features.

## Requirements
- Java JDK 21 or newer
- Visual Studio Code or another Java-compatible IDE
- No external libraries required

## Entry Point
The main class is `adventure.console.AdventureApp`.

Source file: `src/adventure/console/AdventureApp.java`

## How to Run
1. Download or clone the GitHub repository.
2. Open the project folder in VS Code.
3. Open a PowerShell terminal in the project root.
4. Create the output directory if it does not exist:

    New-Item -ItemType Directory -Force bin | Out-Null

5. Compile the Java source files:

    javac -d bin (Get-ChildItem -Path .\src -Recurse -Filter *.java).FullName

6. Start the application:

    java -cp bin adventure.console.AdventureApp

## Completed Features

### Playable Characters
- Warrior: Strength-based character using Rage, starting with 3 AP.
- Rogue: Dexterity-based character using Finesse, starting with 4 AP.
- Wizard: Intelligence-based character using Mana, starting with 3 AP.
- Each character has five predefined abilities with different AP costs, resource costs, and effects.

### Combat
- Turn-based battles with visible enemy intents.
- Abilities can deal damage, provide block, restore health, or generate resources.
- Action points determine which abilities can be used.
- Players manually end their turns.
- Enemies alternate between attacks and defensive actions.
- Victory and defeat are determined by remaining health.

### Exploration
- Three stages: The Underground, The Lost Ruins, and The Tomb.
- Stage bosses: The Golem, Twin Gargoyles, and The Undead King.
- Fixed paths with normal enemy, elite enemy, treasure, rest, and shop nodes.
- Enemy difficulty increases between stages.
- Defeating all three bosses completes the adventure.

### Inventory and Equipment
- Equipment slots: Helmet, Armor, Leggings, and Ring.
- Equipment can increase Defense and resource gain.
- Shops sell equipment and healing supplies.
- Enemies award gold, while elite encounters provide unique equipment.
- Players can equip and unequip items outside combat.

### Input Validation
- Invalid menu selections are rejected.
- Abilities cannot be used without sufficient AP or resources.
- Purchases require sufficient gold.
- Invalid inventory selections are rejected.
- Equipment cannot be managed during combat.
- Failed actions do not consume the associated resources.

## Controls
Character Selection:
- 1: Warrior
- 2: Rogue
- 3: Wizard
- 4: Quit

Exploration:
- Enter a displayed node number to visit it.
- I: Open inventory.
- Q: Quit.

Combat:
- 1-5: Use the corresponding character ability.
- 6: End turn.
- 7: Quit.

Inventory:
- Enter an item number to equip it.
- U1-U4: Unequip the corresponding equipment slot.
- B: Return to exploration.

Shop:
- 1: Purchase healing supplies.
- 2-5: Purchase equipment.
- 6: Leave shop.

## Sample Demonstration
1. Launch the application and select Warrior.
2. Choose a normal enemy node.
3. Use Light Attack to generate Rage.
4. Use Defend to reduce incoming damage.
5. End turns manually and defeat the enemy.
6. Collect gold and continue exploring.
7. Visit a shop, purchase equipment, and leave.
8. Open the inventory and equip the purchased item.
9. Continue through the stages and defeat the final boss.

## Known Limitations
- Stage paths and encounter types are predefined rather than randomly generated.
- Each battle currently contains one enemy.
- Area-of-effect abilities currently affect only that enemy.
- Enemy behavior uses a simplified repeating intent pattern.
- Character abilities have fixed values and do not yet support enchantments.
- Shops contain a limited selection of equipment.
- Combat rewards consist of gold and elite equipment rather than stat or enchantment choices.
- Progress is not saved between application launches.
- The graphical interface is reserved for Phase 2.

## Documentation
Generated Javadoc documentation is available in `docs/index.html`.

## Future Development
Phase 2 will focus on a graphical interface, including visual combat, exploration, and inventory management. Additional planned features include randomized paths, more enemy varieties, skill enchantments, and expanded equipment effects.
