/*
 * Author: Mark Genesis C. Topinio
 * Description: The goal of the exercise is to implement the Min-Max algorithm that would make a smart AI agent.
 * CMSC 170 - X4L
 */
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application{

    public static void main(String[] args){
        launch(args);
    }

    public void start(Stage stage) {
          // Starting the Tic-Tac-Toe Board Application
          GameBoard board = new GameBoard();
          board.setStage(stage);
    }
}