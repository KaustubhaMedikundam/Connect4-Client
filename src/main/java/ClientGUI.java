import javafx.application.Application;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ClientGUI extends Application {

	public int[][] boardNum = new int[6][7];
//	static Server serverConnection;
    static Client clientConnection;
	ListView<String> listItems, listItems2;
	static ArrayList<String> actions = new ArrayList<>();

	CFourInfo game = new CFourInfo();
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

		TextField port = new TextField();
		port.setPromptText("Enter a port number between 4000 and 8000");

		Button start = new Button();
		start.setText("Start");
		start.setOnAction(e->startClient(primaryStage));



		VBox vbox = new VBox(port, start);
		vbox.setAlignment(Pos.CENTER);

		BorderPane root = new BorderPane();
		root.setCenter(vbox);
	     Scene scene = new Scene(root, 700,700);
			primaryStage.setScene(scene);
			primaryStage.show();
	}

	public void startClient(Stage primaryStage){
		clientConnection = new Client(data->{
			Platform.runLater(()->{acceptMessage((CFourInfo) data, primaryStage);
			});
		});
		clientConnection.start();
//		if(actions.size()==1){
//			System.out.println("works");
//		}
	}


	public void acceptMessage(CFourInfo mes, Stage primaryStage){
		if(!mes.hasTwoPlayers){
			hasOnePlayer(primaryStage);
		}
		else {
			game = mes;
			game.rowMove = mes.rowMove;
			if(!mes.p1Win && !mes.p2Win && !mes.tie){
				if(mes.turn){
					if(mes.whoseTurn){
						if(mes.rowMove!=10){
							System.out.println("enabled");
							boardNum[mes.rowMove][mes.columnMove] = 2;
						}
						makeBoard(primaryStage, 1);
					}
					else {
						if(mes.rowMove!=10){
							System.out.println("wrong");
							boardNum[mes.rowMove][mes.columnMove] = 1;
						}
						makeBoard(primaryStage, 2);
					}
					//if it's the player turn
				}
				else {
					//if it isn't player turn
					if(mes.whoseTurn){
						disabledBoard(primaryStage);
					}
					else {
						disabledBoard(primaryStage);
					}
				}
			}
		}
	}


	public Boolean checkWin(CFourInfo game){
		if(checkHor()){
			return true;
		}
		if(checkVert()){
			return true;
		}
		if(checkDiagonal()){
			return true;
		}
		return false;
	}

	public Boolean checkVert(){
		int pl = 0;
		int num = 0;
		for(int j = 0; j < 7 ; j++){
			for(int i = 0 ; i < 6 ; i++){
				if(pl == boardNum[i][j] && pl!=0 ){
					num++;
				}
				else if(num<4) {
					num = 0;
				}
				pl = boardNum[i][j];
				System.out.println(pl);
			}
		}
		if(num >= 4){
			return true;
		}
		return false;
	}

	public Boolean checkDiagonal(){
		return false;
	}

	public Boolean checkHor(){
		int pl = 0;
		int num = 0;
		for(int i = 0; i < 6 ; i++){
			for (int j = 0; j < 7; j++){
				if(pl == boardNum[i][j] && pl!=0 ){
					num++;
				}
				else if(num<4) {
					num = 0;
				}
				pl = boardNum[i][j];
			}
		}
		if(num >= 4){
			return true;
		}
		return false;
	}
	public void hasOnePlayer(Stage primaryStage){
		Text info = new Text();
		info.setText("Waiting For Second Player...");
		BorderPane bp = new BorderPane();
		bp.setCenter(info);
		Scene scene = new Scene(bp, 700,700);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public void makeBoard(Stage primaryStage, int player){
		GridPane gridBoard = new GridPane();
		gridBoard.setMinSize(650, 650);
		for(int i = 0; i < 7; i ++){
			for(int j = 0 ; j < 6; j++){
				GameButton button = new GameButton(boardNum[j][i]);
				button.setMinHeight(75);
				button.setMinWidth(75);
				if(boardNum[j][i]==1){
					button.setStyle("-fx-background-color: POWDERBLUE");
				}
				else if(boardNum[j][i]==2){
					button.setStyle("-fx-background-color: PLUM");
				}
				int finalI = i;
				int finalJ = j;
				button.setOnAction(e->updateBoard(primaryStage, finalJ, finalI ,player));
				gridBoard.add(button, i, j);
			}
		}
		BorderPane bp = new BorderPane();
		bp.setRight(gridBoard);
		Scene scene = new Scene(bp, 700,700);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public void updateBoard(Stage primaryStage, int row, int column, int player){
		if(checkBoard(row,column)){
			boardNum[row][column] = player;
			game.rowMove = row;
			game.columnMove = column;
			game.turn = !game.turn;
//			System.out.println(game.turn + "" +game.whoseTurn );
			if(checkWin(game)&&player==1){
				game.p1Win = true;
			}
			if(checkWin(game)&&player==2){
				game.p2Win = true;
			}
			clientConnection.send(game);
		}
		else{
			Alert fail= new Alert(Alert.AlertType.INFORMATION);
			fail.setHeaderText("failure");
			fail.setContentText("Move is not valid...try again");
			fail.showAndWait();
			makeBoard(primaryStage, player);
		}
	}

	public void disabledBoard(Stage primaryStage){
		GridPane gridBoard = new GridPane();
		gridBoard.setMinSize(650, 650);
		for(int i = 0; i < 7; i ++){
			for(int j = 0 ; j < 6; j++){
				GameButton button = new GameButton(boardNum[j][i]);
				button.setMinHeight(75);
				button.setMinWidth(75);
				button.setDisable(true);
				if(boardNum[j][i]==1){
					button.setStyle("-fx-background-color: POWDERBLUE");
				}
				else if(boardNum[j][i]==2){
					button.setStyle("-fx-background-color: PLUM");
				}
				gridBoard.add(button, i, j);
			}
		}
		BorderPane bp = new BorderPane();
		bp.setRight(gridBoard);
		Scene scene = new Scene(bp, 700,700);
		primaryStage.setScene(scene);
		primaryStage.show();
	}


	public Boolean checkBoard(int row , int col){
		//returns if valid move or not
		if( row==5 || boardNum[row+1][col]!=0){
			return true;
		}
		else {
			return false;
		}
	}
}
