import javafx.application.Application;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ClientGUI extends Application {
//	static Server serverConnection;
    static Client clientConnection;
	ListView<String> listItems, listItems2;
	static ArrayList<String> actions = new ArrayList<>();
	public ClientGUI() throws Exception {
	}

	public static void main(String[] args)  {
		// TODO Auto-generated method stub
//		serverConnection = new Server(data -> {
//			Platform.runLater(()->{
////				listItems.getItems().add(data.toString());
//			});
//
//		});
		launch(args);


	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("CLIENT");

//		TextField port = new TextField();
//		port.setPromptText("Enter a port number between 4000 and 8000");
//
//		Button start = new Button();
//		start.setText("Start");
//		start.setOnAction(e->startClient());
//
//
//
//		VBox vbox = new VBox(port, start);
//		vbox.setAlignment(Pos.CENTER);
		GridPane gridBoard = new GridPane();

		gridBoard.setMinSize(600, 600);
		for(int i = 0; i < 7; i ++){
			for(int j = 0 ; j < 6; j++){
				GameButton button = new GameButton(0);
				button.setMinWidth(70);
				button.setMinHeight(90);
				gridBoard.add(button, i, j);
			}
		}

		BorderPane root = new BorderPane();
		root.setRight(gridBoard);
	     Scene scene = new Scene(root, 700,700);
			primaryStage.setScene(scene);
			primaryStage.show();
	}

	public void startClient(){
		clientConnection = new Client(data->{
//			Platform.runLater(()->{listItems2.getItems().add(data.toString());
//			});
		});
		clientConnection.start();
//		if(actions.size()==1){
//			System.out.println("works");
//		}
	}

	public void makeBoard(){
		GridPane gridBoard = new GridPane();
		gridBoard.setMinSize(650, 650);
		for(int i = 0; i < 7; i ++){
			for(int j = 0 ; j < 6; j++){
				gridBoard.add(new GameButton(0), i, j);
			}
		}
	}
}
