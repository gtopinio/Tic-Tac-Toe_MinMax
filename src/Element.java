import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Element {
    private String type;
	protected Image img;
	protected ImageView imgView;
	protected GameBoard gameStage;
	protected int row, col;

    // The images are stored as constant values
    public final static int IMAGE_SIZE = 60;
    public final static Image SPACE_IMAGE = new Image("images/space.png",GameBoard.CELL_WIDTH,GameBoard.CELL_WIDTH,false,false);
    public final static Image X_IMAGE = new Image("images/X.png",GameBoard.CELL_WIDTH,GameBoard.CELL_WIDTH,false,false);
    public final static Image O_IMAGE = new Image("images/O.png",GameBoard.CELL_WIDTH,GameBoard.CELL_WIDTH,false,false);

    public final static String SPACE_TYPE = "-1";
    public final static String X_TYPE = "X";
    public final static String O_TYPE = "O";
    public final static int X = 1;
    public final static int O = 0;


    public Element(String type, GameBoard gameStage) { // Note: an ELEMENT has an imgView (to set up an Image)
		this.type = type;
		this.gameStage = gameStage;

		// load image depending on the type
		switch(this.type) { // This determines which picture to use, depending on the element to be added on the layout
            case Element.SPACE_TYPE: this.img = Element.SPACE_IMAGE; break;
			case Element.X_TYPE: this.img = Element.X_IMAGE; break;
            case Element.O_TYPE: this.img = Element.O_IMAGE; break;

		}

		this.setImageView();
		this.setMouseHandler();
	}

    protected void loadImage(String filename,int width, int height){
		try{
			this.img = new Image(filename,width,height,false,false);
		} catch(Exception e){}
	}

	// getters

	String getType(){
		return this.type;
	}

	Image getImage(){
		return this.img;
	}

	int getRow() {
		return this.row;
	}

	int getCol() {
		return this.col;
	}

	protected ImageView getImageView(){
		return this.imgView;
	}

	// setters
	void setType(String type){
		this.type = type;
	}

    void initRowCol(int i, int j) {
		this.row = i;
		this.col = j;
	}

	// method to change image of an Element
	void changeImage(Element element, Image image) {
		this.imgView.setImage(image);

	}

	void setImg(Image img){
		this.img = img;
	}

	// method to adjust the properties of an image
    private void setImageView() {
		// initialize the image view of this element
		this.imgView = new ImageView();
		this.imgView.setImage(this.img);
		this.imgView.setLayoutX(0); // JavaFX method of setLayout for Image. Sets the X/Y positioning of the node
		this.imgView.setLayoutY(0);
		this.imgView.setPreserveRatio(true);

        this.imgView.setFitWidth(GameBoard.CELL_WIDTH);
        this.imgView.setFitHeight(GameBoard.CELL_HEIGHT);
	}

	// method to handle tile movements on the 3x3 grid
    private void setMouseHandler(){
		Element element = this;
		this.imgView.setOnMouseClicked(new EventHandler<MouseEvent>(){ //Notice that the ImageView element is the one that interacts
			public void handle(MouseEvent e) {
				// Getting the clicked-tile coordinates
				int currentRow = element.getRow();
				int currentCol = element.getCol();

                // Can only click if board is now clickable
                if(element.gameStage.getCanClickBoard()){
                
                // Can only change tile if tile is empty
                if(element.getType() == SPACE_TYPE){

                
                boolean stopper = true;	// to stop the for loops once there has been already an update of values

					// Updating the gameBoard (3x3 int array) while also updating the pieceCells (Element array) to update images
					for(int i=0; i<GameBoard.MAP_NUM_ROWS && stopper; i++){
						for(int j=0; j<GameBoard.MAP_NUM_COLS; j++){
							if(i == currentRow && j == currentCol){
								// Updating the gameBoard
                                if(gameStage.getPlayerMode() == Element.X){
                                    gameStage.setGameBoardValue(i, j, 1);
                                } else if(gameStage.getPlayerMode() == Element.O){
                                    gameStage.setGameBoardValue(i, j, 0);
                                }
								
								// Updating the pieceCells
                                if(gameStage.getPlayerMode() == Element.X){
                                    element.setImg(X_IMAGE);
                                    element.changeImage(element, X_IMAGE);
                                    element.setType(X_TYPE);
                                } else if (gameStage.getPlayerMode() == Element.O){
                                    element.setImg(O_IMAGE);
                                    element.changeImage(element, O_IMAGE);
                                    element.setType(O_TYPE);
                                }
								
								stopper = false;
								break;
                            }
                        }
                    }
                    System.out.println("===== GAMEBOARD =====");
                    element.gameStage.printGameBoard();

                    // Check if there's a win afte user played a move
                    if(!element.gameStage.hasMovesLeft(element.gameStage.getGameBoard()) && element.gameStage.checkWin(element.gameStage.getGameBoard())==Element.X_TYPE){
                        System.out.println("Player X Won");
                        element.gameStage.toggleClickBoard();
                        element.gameStage.showResult(X_TYPE);
                    } else if (!element.gameStage.hasMovesLeft(element.gameStage.getGameBoard()) && element.gameStage.checkWin(element.gameStage.getGameBoard())==Element.O_TYPE){
                        System.out.println("Player O Won");
                        element.gameStage.toggleClickBoard();
                        element.gameStage.showResult(O_TYPE);
                    } else if (!element.gameStage.hasMovesLeft(element.gameStage.getGameBoard()) && element.gameStage.checkWin(element.gameStage.getGameBoard())=="draw"){
                        System.out.println("It's a draw");
                        element.gameStage.toggleClickBoard();
                        element.gameStage.showResult("draw");
                    }
                    // Check if there's a possible move
                    if(element.gameStage.hasMovesLeft(element.gameStage.getGameBoard())){
                        // Switches turn to AI if there's a next possible move
                        
                        // This should hold the next best move for the AI
                        Move bestMove = element.gameStage.nextBestMove(element.gameStage.getGameBoard());
                        
                        // Updating the gameBoard
                                if(gameStage.getPlayerMode() == Element.X){
                                    gameStage.setGameBoardValue(bestMove.row, bestMove.col, 0);
                                } else if(gameStage.getPlayerMode() == Element.O){
                                    gameStage.setGameBoardValue(bestMove.row, bestMove.col, 1);
                                }
                                
                                // Updating the pieceCells
                                if(gameStage.getPlayerMode() == Element.X){
                                    // Updating the empty tile image from pieceCells
                                    for(Element tile: element.gameStage.getPieceCells()){
                                        if(tile.getRow() == bestMove.row && tile.getCol() == bestMove.col){
                                            tile.setImg(Element.O_IMAGE);
                                            tile.changeImage(tile, Element.O_IMAGE);
                                            tile.setType(Element.O_TYPE);
                                            break;
                                        }
                                    }
                                } else if (gameStage.getPlayerMode() == Element.O){
                                    // Updating the empty tile image from pieceCells
                                    for(Element tile: element.gameStage.getPieceCells()){
                                        if(tile.getRow() == bestMove.row && tile.getCol() == bestMove.col){
                                            tile.setImg(Element.X_IMAGE);
                                            tile.changeImage(tile, Element.X_IMAGE);
                                            tile.setType(Element.X_TYPE);
                                            break;
                                        }
                                    }
                                }
                                System.out.println("===== GAMEBOARD =====");
                                element.gameStage.printGameBoard();
                                // Check if there's a win after AI's move
                                if(element.gameStage.checkWin(element.gameStage.getGameBoard())==Element.X_TYPE){
                                    System.out.println("Player X Won");
                                    element.gameStage.toggleClickBoard();
                                    element.gameStage.showResult(X_TYPE);
                                } else if (element.gameStage.checkWin(element.gameStage.getGameBoard())==Element.O_TYPE){
                                    System.out.println("Player O Won");
                                    element.gameStage.toggleClickBoard();
                                    element.gameStage.showResult(O_TYPE);
                                } else if (!element.gameStage.hasMovesLeft(element.gameStage.getGameBoard()) && element.gameStage.checkWin(element.gameStage.getGameBoard())=="draw"){
                                    System.out.println("It's a draw");
                                    element.gameStage.toggleClickBoard();
                                    element.gameStage.showResult("draw");
                                }
                    }
                    
                }
                } // end of is clickable condition
					
			}	//end of handle()
		});
	}
	
}