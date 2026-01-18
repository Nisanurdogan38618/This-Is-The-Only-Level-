# 🐘 This Is The Only Level (Java Implementation)

A puzzle-platformer game developed in **Java** using the **StdDraw** library. The core concept is unique: the level geometry never changes, but the rules of the game (physics, controls, objectives) change with every stage.

Inspired by the classic Flash game franchise, this project demonstrates object-oriented programming, game physics simulation, and state management.

## 🎮 Game Overview

You play as an **Elephant**. Your goal is to navigate from the start pipe to the exit pipe. However, simply moving right won't always work. You must adapt to the changing rules of each stage to survive.

### The Stages
Defined in `Main.java`, the game currently features 5 distinct stages:

1.  **Stage 1: Basic Controls** - Standard platformer movement. Use arrow keys to move and jump.
2.  **Stage 2: Reversed Reality** - Left is Right, Right is Left. Your controls are inverted.
3.  **Stage 3: Auto-Jump** - You cannot stop jumping! Control your horizontal movement carefully.
4.  **Stage 4: Mashing** - The door is locked. You must press the button 5 times to open it.
5.  **Stage 5: Time Trial** - Hurry! The door stays open for only 5 seconds.

## 🕹️ Controls

| Key | Action |
| :--- | :--- |
| **Arrow Keys (← →)** | Move Left / Right (Subject to stage rules) |
| **Up Arrow (↑)** | Jump (Subject to stage rules) |
| **Mouse Click** | Interact with UI buttons (Restart, Help, Reset) |
| **'A'** | Play Again (On End Screen) |
| **'Q'** | Quit Game (On End Screen) |

## 🛠️ Project Structure

The project follows a modular Object-Oriented design:

* **`Main.java`**: The entry point. Initializes the game window and defines the specific rules/properties for each stage.
* **`Game.java`**: Manages the main game loop, level transitions, and global game state (timer, death counter).
* **`Stage.java`**: A data class that holds the rules for a specific level (gravity, velocity, control mapping, clues).
* **`Map.java`**: The visual engine. Handles drawing the environment (pipes, spikes, buttons), collision detection, and physics calculations.
* **`Player.java`**: Manages the player's position, velocity, and sprite rendering (`ElephantRight.png`, `ElephantLeft.png`).

## 🚀 How to Run

### Prerequisites
* **Java Development Kit (JDK):** Version 8 or higher.
* **StdDraw Library:** You need `stdlib.jar` (Princeton Standard Library) in your project directory.

### Compilation & Execution
1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/YOUR_USERNAME/This-Is-The-Only-Level-.git](https://github.com/YOUR_USERNAME/This-Is-The-Only-Level-.git)
    cd This-Is-The-Only-Level-
    ```

2.  **Compile the code:**
    (Ensure `stdlib.jar` is in the same folder)
    * *Windows:*
        ```bash
        javac -cp ".;stdlib.jar" *.java
        ```
    * *Mac/Linux:*
        ```bash
        javac -cp ".:stdlib.jar" *.java
        ```

3.  **Run the game:**
    * *Windows:*
        ```bash
        java -cp ".;stdlib.jar" Main
        ```
    * *Mac/Linux:*
        ```bash
        java -cp ".:stdlib.jar" Main
        ```

## 📸 Assets
The game requires the following image assets in the root directory:
* `ElephantRight.png`
* `ElephantLeft.png`
* `Spikes.png`

## 🤝 Contributing
Feel free to fork this repository and add your own "Stages" by modifying the `Main.java` file!

## 📝 License
This project is for educational purposes.
