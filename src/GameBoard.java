import java.util.ArrayList;
import java.util.Random;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameBoard {
    // Application components
    private Scene scene;
    private Stage stage;
    private Group root;
    private Canvas canvas;
    private Text prompt;
    private Button oBtn;
    private Button xBtn;
    private Boolean canClickBoard;

    // GameBoard components
	private GridPane map;
    private int[][] gameBoard;
    private ArrayList<Element> pieceCells;
    private Boolean hasChosenMode;
    private Boolean isPlayerTurn;
    private int playerMode;
    private int aiMode;


    // GUI and GridPane size specifications
    public final static int MAX_CELLS = 9;
    public final static int MAP_NUM_ROWS = 3;
    public final static int MAP_NUM_COLS = 3;
    public final static int MAP_WIDTH = 700;
    public final static int MAP_HEIGHT = 700;
    public final static int CELL_WIDTH = 60;
    public final static int CELL_HEIGHT = 70;
    public final static int WINDOW_WIDTH = 400;
    public final static int WINDOW_HEIGHT = 500;

    // Constructor
	public GameBoard() {
		this.root = new Group();
		this.scene = new Scene(root, GameBoard.WINDOW_WIDTH,GameBoard.WINDOW_HEIGHT, Color.PURPLE);
		this.canvas = new Canvas(GameBoard.WINDOW_WIDTH,GameBoard.WINDOW_HEIGHT);
        
        this.pieceCells = new ArrayList<Element>(); // List to hold the visual tiles of the Grid Pane
		this.map = new GridPane(); // Main layout for the game
        this.gameBoard = new int[GameBoard.MAP_NUM_ROWS][GameBoard.MAP_NUM_COLS]; // initializing the state board
        this.isPlayerTurn = true;
        this.hasChosenMode = false;
        this.canClickBoard = false;

	}

    // getters 

    int[][] getGameBoard(){
        return this.gameBoard;
    }

    Boolean getCanClickBoard(){
        return this.canClickBoard;
    }
    
    Boolean getIsPlayerTurn(){
        return this.isPlayerTurn;
    }

    int getPlayerMode(){
        return this.playerMode;
    }

    int getAIMode(){
        return this.aiMode;
    }

    ArrayList<Element> getPieceCells(){
        return this.pieceCells;
    }

    // method to make board clickable
    void toggleClickBoard(){
        this.canClickBoard = !this.canClickBoard;
    }


    // Method to set or update a value to an index in the gameBoard
    void setGameBoardValue(int row, int col, int val){
        this.gameBoard[row][col] = val;
    }

    // Method to print the current state of the gameboard
    void printGameBoard(){
        for(int i=0; i<GameBoard.MAP_NUM_ROWS; i++){
            for(int j=0; j<GameBoard.MAP_NUM_COLS; j++){
                System.out.print(this.gameBoard[i][j]+" ");
            }
            System.out.println();
        }
    }

    // Method to create a Result Stage that will prompt the user who won
    void showResult(String whoWon){
        Stage resultStage = new Stage();
        ResultStage rs = new ResultStage();
        rs.setStage(resultStage, whoWon);
    }


    // Method to add the stage elements
    public void setStage(Stage stage) {
        this.stage = stage;

        this.initGameBoard();   // set all cells to -1
        this.createMap(this.gameBoard); // create the initial tiles for the visual gameboard

        // Letting the chooser choose which one to play by setting a prompt and some buttons
        Font promptFont = Font.font("Tw Cen MT",FontWeight.NORMAL,22);
        this.prompt = new Text("What do you want to play?"); this.prompt.setFill(Color.WHITE);
        this.prompt.setFont(promptFont); this.prompt.setLayoutX(70); this.prompt.setLayoutY(400);

        this.xBtn = new Button("X");
        this.xBtn.setFont(promptFont); this.xBtn.setLayoutX(110); this.xBtn.setLayoutY(420);

        this.oBtn = new Button("O");
        this.oBtn.setFont(promptFont); this.oBtn.setLayoutX(230); this.oBtn.setLayoutY(420);

        // Adding event handlers
        this.addEventHandler(xBtn); this.addEventHandler(oBtn);

        // set stage elements here
        this.root.getChildren().add(this.canvas);
        this.root.getChildren().add(this.map);
        this.root.getChildren().addAll(this.prompt, this.xBtn, this.oBtn);

        this.stage.setTitle("Tic-Tac-Toe Game");
        this.stage.setScene(this.scene);
        this.stage.show();

    }

    // Method to initialize the gameboard cell values to -1
    private void initGameBoard(){
        for(int i=0; i<GameBoard.MAP_NUM_ROWS; i++){
            for(int j=0; j<GameBoard.MAP_NUM_COLS; j++){
                this.gameBoard[i][j] = -1;
            }
        }
    }

    // Method to create 3x3 gameboard = 9 tiles. It assigns an Element in the pieceCells array based on the gameboard index.
	private void createMap(int[][] gBoard){
        for(int i=0;i<GameBoard.MAP_NUM_ROWS;i++){
			for(int j=0;j<GameBoard.MAP_NUM_COLS;j++){
                Element o = new Element(Element.SPACE_TYPE, this);
                this.instantiateNode(o, i, j);
            }
        }

        this.setGridPaneProperties();
		this.setGridPaneContents();
    }

    // Method to set the initial tile (Element) coordinates on the board and add it to the pieceCells
	private void instantiateNode(Element node, int i, int j){
		node.initRowCol(i, j);
		this.pieceCells.add(node);
	}

    // Method to set size and location of the grid pane
	private void setGridPaneProperties(){
		this.map.setPrefSize(GameBoard.MAP_WIDTH, GameBoard.MAP_HEIGHT);
		//set the map to x and y location; add border color to see the size of the gridpane/map
		this.map.setLayoutX(GameBoard.WINDOW_WIDTH*0.23);
	    this.map.setLayoutY(GameBoard.WINDOW_WIDTH*0.3);
	    this.map.setVgap(10);
	    this.map.setHgap(10);
	}

    // Method to add row and column constraints of the grid pane
	private void setGridPaneContents(){

        //loop that will set the constraints of the elements in the grid pane
        int counter=0;
        for(int row=0;row<GameBoard.MAP_NUM_ROWS;row++){
            for(int col=0;col<GameBoard.MAP_NUM_COLS;col++){
                // map each element constraints
                GridPane.setConstraints(pieceCells.get(counter).getImageView(),col,row);
                counter++;
            }
        }

      //loop to add each element to the gridpane/map
        for(Element piece: pieceCells){
            this.map.getChildren().add(piece.getImageView());
        }
    }

    // Method to remove the text prompt and buttons after player has chosen a mode
    private void removePrompt(){
        if(hasChosenMode){
            this.root.getChildren().remove(this.prompt);
            this.root.getChildren().remove(this.xBtn);
            this.root.getChildren().remove(this.oBtn);
        }
    }

    // Method to randomize the AI's first move
    private void randomFirstMoveAI(){
        Random rand = new Random();
        int min = 0;
        int max = GameBoard.MAP_NUM_ROWS-1;

        int randomRow = rand.nextInt((max - min) + 1) + min;
        int randomCol = rand.nextInt((max - min) + 1) + min;

        // Updating the gameboard
        setGameBoardValue(randomRow, randomCol, 1);

        // Updating the pieceCells

        // Updating the empty tile image from pieceCells
        for(Element tile: this.pieceCells){
            if(tile.getRow() == randomRow && tile.getCol() == randomCol){
                tile.setImg(Element.X_IMAGE);
                tile.changeImage(tile, Element.X_IMAGE);
                tile.setType(Element.X_TYPE);
                break;
            }
        }

    }

    // ======================== MinMax Utility Methods ========================

    // Method to check if there's a win or draw
    String checkWin(int board[][]){
        String check = "draw";

        // Case where X wins (row case)
        for(int i=0; i<GameBoard.MAP_NUM_ROWS; i++){
            if((board[i][0] == board[i][1]) && (board[i][1] == board[i][2]) && board[i][0] == 1){
                return Element.X_TYPE;
            }
        }

        // Case where X wins (columnn case)
        for(int i=0; i<GameBoard.MAP_NUM_COLS; i++){
            if((board[0][i] == board[1][i]) && (board[1][i] == board[2][i]) && board[0][i] == 1){
                return Element.X_TYPE;
            }
        }

        // Case where X wins (diagonal case)
        if((board[0][0] == board[1][1]) && (board[1][1] == board[2][2]) && board[0][0] == 1){
            return Element.X_TYPE;
        } else if((board[0][2] == board[1][1]) && (board[1][1] == board[2][0]) && board[0][2] == 1){
            return Element.X_TYPE;
        } 
        // Case where O wins (diagonal case)
        else if((board[0][0] == board[1][1]) && (board[1][1] == board[2][2]) && board[0][0] == 0){
            return Element.O_TYPE;
        } else if((board[0][2] == board[1][1]) && (board[1][1] == board[2][0]) && board[0][2] == 0){
            return Element.O_TYPE;
        }

        // Case where O wins (row case)
        for(int i=0; i<GameBoard.MAP_NUM_ROWS; i++){
            if((board[i][0] == board[i][1]) && (board[i][1] == board[i][2]) && board[i][0] == 0){
                return Element.O_TYPE;
            }
        }

        // Case where O wins (columnn case)
        for(int i=0; i<GameBoard.MAP_NUM_COLS; i++){
            if((board[0][i] == board[1][i]) && (board[1][i] == board[2][i]) && board[0][i] == 0){
                return Element.O_TYPE;
            }
        }
        return check;
    }


    // Method to check if there are any moves left in the gameboard
    Boolean hasMovesLeft(int gameBoard[][]){
        for (int i = 0; i < GameBoard.MAP_NUM_ROWS; i++)
            for (int j = 0; j < GameBoard.MAP_NUM_COLS; j++)
                if (gameBoard[i][j] == -1)
                    return true;
        return false;
    }

    // Method to check the current state of the game
    private int utility(int gameBoard[][]){
        // Checking for Rows if X or O wins.
        for (int row = 0; row < GameBoard.MAP_NUM_ROWS; row++){
            if (gameBoard[row][0] == gameBoard[row][1] && gameBoard[row][1] == gameBoard[row][2]){
                if (gameBoard[row][0] == aiMode)
                    return +1;
                else if (gameBoard[row][0] == playerMode)
                    return -1;
            }
        }
     
        // Checking for Columns if X or O wins.
        for (int col = 0; col < GameBoard.MAP_NUM_COLS; col++){
            if (gameBoard[0][col] == gameBoard[1][col] && gameBoard[1][col] == gameBoard[2][col]){
                if (gameBoard[0][col] == aiMode)
                    return +1;
     
                else if (gameBoard[0][col] == playerMode)
                    return -1;
            }
        }
     
        // Checking for Diagonals if X or O wins.
        if (gameBoard[0][0] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][2]){
            if (gameBoard[0][0] == aiMode)
                return +1;
            else if (gameBoard[0][0] == playerMode)
                return -1;
        }
     
        if (gameBoard[0][2] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][0]){
            if (gameBoard[0][2] == aiMode)
                return +1;
            else if (gameBoard[0][2] == playerMode)
                return -1;
        }
     
        // If no one wins return zero
        return 0;
    }

    // Main method for simulating MinMax algorithm. It returns the evaluation of a move
    private int value(int gameBoard[][], int depth, Boolean isMax){
        int score = utility(gameBoard);
    
        if (score == 1) // Returning the max node's evaluated score if it won the game
            return score;

        if (score == -1) // Returning the min node's evaluated score if it won the game
            return score;
    
        if (hasMovesLeft(gameBoard) == false) // If there are no winners and no more moves left, return zero
            return 0;
    
        if (isMax){ // max node
            Integer bestValue = Integer.MIN_VALUE;
    
            // Going through all cells
            for (int i = 0; i < GameBoard.MAP_NUM_ROWS; i++){
                for (int j = 0; j < GameBoard.MAP_NUM_COLS; j++){
                    // Check if cell is empty
                    if (gameBoard[i][j]==-1){
                        // Make the move for the ai
                        gameBoard[i][j] = aiMode;
    
                        // Call value recursively and choosing the maximum value
                        bestValue = Math.max(bestValue, value(gameBoard, depth + 1, !isMax));
    
                        // Undo the move
                        gameBoard[i][j] = -1;
                    }
                }
            }
            return bestValue;
        }
    
        else { // min node
            Integer bestValue = Integer.MAX_VALUE;
    
            // Going through all cells
            for (int i = 0; i < GameBoard.MAP_NUM_ROWS; i++){
                for (int j = 0; j < GameBoard.MAP_NUM_COLS; j++){
                    // Check if cell is empty
                    if (gameBoard[i][j] == -1){
                        // Make the move
                        gameBoard[i][j] = playerMode;
    
                        // Call value recursively and choose the minimum value
                        bestValue = Math.min(bestValue, value(gameBoard, depth + 1, !isMax));
    
                        // Undo the move
                        gameBoard[i][j] = -1;
                    }
                }
            }
            return bestValue;
        }
    }

    // This will return the best possible for the AI
    Move nextBestMove(int gameBoard[][]){
        Integer bestValue = Integer.MIN_VALUE;
        Move bestMove = new Move();
        bestMove.row = -1;
        bestMove.col = -1;
    
        // Going through all cells, assessing the value function for all empty cells. And return the cell with the optimal value.
        for (int i = 0; i < GameBoard.MAP_NUM_ROWS; i++){
            for (int j = 0; j < GameBoard.MAP_NUM_COLS; j++){
                // Check if cell is empty
                if (gameBoard[i][j] == -1){
                    // Make the move
                    gameBoard[i][j] = aiMode;
    
                    // Computing the evaluation of this move
                    int moveVal = value(gameBoard, 0, false);
    
                    // Undoing the move since we're using a global gameboard
                    gameBoard[i][j] = -1;
    
                    // If the value of the current move is
                    // more than the best value, then update
                    // bestValue
                    if (moveVal > bestValue){
                        bestMove.row = i;
                        bestMove.col = j;
                        bestValue = moveVal;
                    }
                }
            }
        }
    
        return bestMove;
    }


    // event handler for buttons
    private void addEventHandler(Button btn) {
		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent arg0) {
				switch(btn.getText()){
				case "X":
                    isPlayerTurn = true;
                    playerMode = Element.X;
                    aiMode = Element.O;
                    hasChosenMode = true;
                    removePrompt();
                    toggleClickBoard();
                    break;
                case "O":
                    isPlayerTurn = false;
                    playerMode = Element.O;
                    aiMode = Element.X;
                    hasChosenMode = true;
                    removePrompt();
                    randomFirstMoveAI();
                    System.out.println("===== GAMEBOARD =====");
                    printGameBoard();   
                    toggleClickBoard();             

                    break;
				}
			}
		});

	}
    
}

/*
 *  References:
 *      https://www.geeksforgeeks.org/minimax-algorithm-in-game-theory-set-3-tic-tac-toe-ai-finding-optimal-move/
 *      Date Updated: April 29, 2022
 *      Author: GeekforGeeks
 * 
 */
