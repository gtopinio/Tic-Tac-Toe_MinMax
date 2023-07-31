import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ResultStage {
    public static final int WINDOW_HEIGHT = 100;
	public static final int WINDOW_WIDTH = 200;
	private Scene scene;
	private Stage stage;
	private Group root;


    public ResultStage (){
        this.root = new Group();
        this.scene = new Scene(root, ResultStage.WINDOW_WIDTH,ResultStage.WINDOW_HEIGHT, Color.GREEN);
    }

    //method to add the stage elements
	void setStage(Stage stage, String result) {
		this.stage = stage;
		Font promptFont = Font.font("Tw Cen MT",FontWeight.BOLD,25);
        Text prompt = new Text("   Its a draw!");prompt.setFill(Color.WHITE);
        if(result == Element.X_TYPE){
            prompt = new Text("Player X Won!"); prompt.setFill(Color.WHITE);
        } else if(result == Element.O_TYPE){
            prompt = new Text("Player O Won!"); prompt.setFill(Color.WHITE);
        }

        prompt.setFont(promptFont);prompt.setLayoutX(30);prompt.setLayoutY(60);

        this.root.getChildren().addAll(prompt);
        this.stage.setTitle("Who Won?");
		this.stage.setScene(this.scene);

		this.stage.show();

    }
}
