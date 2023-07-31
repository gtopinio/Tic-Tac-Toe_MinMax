# Tic Tac Toe Game with Min-Max Algorithm

Author: Topinio, Mark Genesis C.  
Course: CMSC170 X-4L

## Description

This app is a simple implementation of the classic Tic Tac Toe game, where two players take turns to place their symbols (X or O) on a 3x3 grid. The objective of the game is to get three of the same symbols in a row, column, or diagonal.

What makes this Tic Tac Toe game unique is the implementation of the Min-Max Algorithm as the AI opponent. The Min-Max Algorithm is a decision-making algorithm that ensures the AI makes the best move possible to either win or force a draw against the human player.

## Installation

To run the Tic Tac Toe Game with Min-Max Algorithm, follow these steps:

1. Clone this repository to your local machine:

2. Install Java SDK and JDK on your computer (preferrably Java 8 Version).

3. Open the project in a text editor like Visual Studio Code.

4. Compile the Java files in the terminal: `javac *.java`

5. Run the program: `java Main`


## Usage

1. When you run the program, the Tic Tac Toe game will start with an empty 3x3 grid.

2. The human player will be prompted to make the first move. Enter the row and column number where you want to place your symbol (X).

3. The AI opponent (O) will then make its move using the Min-Max Algorithm.

4. Continue taking turns with the AI until there is a winner or the game ends in a draw.

## Min-Max Algorithm

The Min-Max Algorithm is a recursive algorithm used to determine the best move for the AI opponent in games like Tic Tac Toe. It explores all possible moves and assigns a score to each move, then chooses the move with the highest score for the AI (Max) and the lowest score for the human player (Min).

This ensures that the AI makes the best possible move to either win the game or force a draw against the human player.

Feel free to challenge the AI and see if you can beat it!

## License

This project is licensed under the MIT License. Feel free to use, modify, and distribute it according to the terms of the license.

If you encounter any issues or have suggestions for improvements, please feel free to create an issue or submit a pull request.

Happy gaming!
