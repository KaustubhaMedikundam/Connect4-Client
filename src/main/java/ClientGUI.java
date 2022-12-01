import com.sun.source.doctree.EndElementTree;
import javafx.application.Application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;

public class ClientGUI extends Application {

	public static int[][] boardNum = new int[6][7];
//	static Server serverConnection;
    static Client clientConnection;
	ListView<String> listItems, listItems2;
	static ArrayList<String> actions = new ArrayList<>();

	public static int[] rowWins = new int[4];
	public static int[] colWins = new int[4];
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
//		start.setOnAction(new NewGameEvent(primaryStage));
		start.setOnAction(e->startClient(primaryStage, Integer.parseInt(port.getText())));



		VBox vbox = new VBox(port, start);
		vbox.setAlignment(Pos.CENTER);

		BorderPane root = new BorderPane();
		root.setCenter(vbox);
	     Scene scene = new Scene(root, 700,700);
			primaryStage.setScene(scene);
			primaryStage.show();

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				Platform.exit();
				System.exit(0);
			}
		});
	}

	public void startClient(Stage primaryStage, int port){
		clientConnection = new Client(data->{
			Platform.runLater(()->{
				acceptMessage((CFourInfo) data, primaryStage);
				System.out.println("accepted message: " + ((CFourInfo) data).hasTwoPlayers);
			});
		}, port);
//	`
		clientConnection.start();
	}


	public void acceptMessage(CFourInfo mes, Stage primaryStage){
		game = mes;
//		System.out.println(mes.hasTwoPlayers);
		if(!mes.hasTwoPlayers){
//			System.out.println("someone has left the game.");
			game.rowMove=10;
			for(int i = 0; i < 6; i ++){
				for(int j = 0; j < 7; j++){
					boardNum[i][j] = 0;
				}
			}
			hasOnePlayer(primaryStage);
		}
		else {
//			game = mes;
//			System.out.println("ran the true statement");
			game.rowMove = mes.rowMove;
			if(!mes.p1Win && !mes.p2Win && !mes.tie){
				if(mes.turn){
					if(mes.whoseTurn){
						if(mes.rowMove!=10){
							boardNum[mes.rowMove][mes.columnMove] = 2;
						}
						makeBoard(primaryStage, 1);
					}
					else {
						if(mes.rowMove!=10){
							boardNum[mes.rowMove][mes.columnMove] = 1;
						}
						makeBoard(primaryStage, 2);
					}
					//if it's the player turn
				}
				else {
					//if it isn't player turn
					if(mes.whoseTurn){
						disabledBoard(primaryStage, 1);
					}
					else {
						disabledBoard(primaryStage, 2);
					}
				}
			}
			else{
				preWinScreen(primaryStage, mes);
			}
		}
//		System.out.println("executed");
	}

	public void preWinScreen(Stage primaryStage, CFourInfo mes){
		GridPane gridBoard = new GridPane();
		gridBoard.setMinSize(650, 650);
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 6; j++) {
				GameButton button = new GameButton(boardNum[j][i]);
				button.setMinHeight(75);
				button.setMinWidth(75);
				button.setDisable(true);
				if (boardNum[j][i] == 1) {
					button.setStyle("-fx-background-color: POWDERBLUE");
				} else if (boardNum[j][i] == 2) {
					button.setStyle("-fx-background-color: PLUM");
				}
				else if (boardNum[j][i] == 3) {
					button.setStyle("-fx-background-color: LIGHTGREEN");
				}
				gridBoard.add(button, i, j);
			}
		}
		Scene scene = new Scene(gridBoard, 700,700);
		primaryStage.setScene(scene);
		primaryStage.show();
//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			throw new RuntimeException(e);
//		}
//		winScreen(primaryStage, mes);

	}

	public Boolean checkWin(CFourInfo game, int[][] boardNum){
		if(checkHor(boardNum)){
			for(int i = 0; i < 4; i++){
				boardNum[rowWins[i]][colWins[i]] = 3;
			}
			return true;
		}
		if(checkVert(boardNum)){
			for(int i = 0; i < 4; i++){
				boardNum[rowWins[i]][colWins[i]] = 3;
			}
			return true;
		}
		if(checkDiagonal(boardNum)){
			for(int i = 0; i < 4; i++){
				boardNum[rowWins[i]][colWins[i]] = 3;
			}
			return true;
		}
		return false;
	}

	static public Boolean checkVert(int[][] boardNum){
		int pl = boardNum[0][0];
//		rowWins[0] = 0;
//		colWins[0] = 0;
		int c = 0;
		int num = 0;
		for(int j = 0; j < 7 ; j++){
			for(int i = 0 ; i < 6 ; i++){
				if(pl == boardNum[i][j] && pl!=0 ){
					num++;
					rowWins[c] = i;
					colWins[c] = j;
				}
				else if(num<4) {
					for(int a = 0; a < rowWins.length; a++){
//						rowWins[a] = 0;
//						colWins[a] = 0;
					}
					c = 0;
					num = 1;
				}
				pl = boardNum[i][j];
				if(num >= 4){
					return true;
				}
			}
			num = 0;
		}

		return false;
	}

	public static Boolean checkDiagonal(int[][] boardNum){
		if(checkNW(boardNum)){
			return true;
		}
		if(checkNE(boardNum)){
			return true;
		}
		return false;
	}

	public static Boolean checkNW(int[][] boardNum) {
		int pl = 0;
		int num = 1;
		int a = 0;
		int b = 0;
		rowWins[0] = 0;
		colWins[0] = 0;
		int c = 0;
		for (int j = 0; j < 7; j++) {
			for (int i = 0; i < 6; i++) {
				pl = boardNum[i][j];
				a = i;
				b = j;
				while(a>0&&b>0&&pl!=0){
					a = a-1;
					b = b-1;
					if(pl == boardNum[a][b]){
						rowWins[c] = a;
						colWins[c] = b;
						num++;
					}
					else if(num<4){
						for(int k = 0; k < rowWins.length; k++){
//							rowWins[k] = 10;
//							colWins[k] = 10;
						}
						c = 0;
						num = 1;
						break;
					}
				}
				if(num >= 4){
					return true;
				}
			}
			num = 1;
		}
		return false;
	}

	public static Boolean checkNE(int[][] boardNum){
		int pl = 0;
		int num = 1;
		int a = 0;
		int b = 0;
		int c = 0;
		for (int j = 0; j < 7; j++) {
			for (int i = 0; i < 6; i++) {
				pl = boardNum[i][j];
				a = i;
				b = j;
				while(a>0&&b<6&&pl!=0){
					a = a-1;
					b = b+1;
					if(pl == boardNum[a][b]){
						num++;
					}
					else if(num<4){
						for(int k = 0; k < rowWins.length; k++){

						}
						c = 0;
						num = 0;
						break;
					}
				}
				if(num >= 4){
					return true;
				}
			}
			num = 1;
		}
		return false;
	}

	public static Boolean checkHor(int[][] boardNum){
		int pl = boardNum[0][0];//which player were checking for
		int num = 0;//count of how many in a row
		int c = 0;
		for(int i = 0; i < 6 ; i++){
			for (int j = 0; j < 7; j++){
				if(pl == boardNum[i][j] && pl!=0 ){
					rowWins[c] = i;
					colWins[c] = j;
					System.out.println("2D array " + boardNum[i][j]);
					num++;
					c++;
				}
				else if(num<4) {
					System.out.println("middle");
					c = 1;
					num = 1;
					rowWins[0] = i;
					colWins[0] = j;
				}
				pl = boardNum[i][j];

				if(num == 4){
					for(int k = 0; k < num; k++){
						System.out.println(k +" holds" + rowWins[k] + "col: "+ colWins[k] );
					}
					System.out.println("whore returns true");
					return true;
				}
			}
			c = 0;
			num = 0;
		}
		return false;
	}

	public void hasOnePlayer(Stage primaryStage){
//		System.out.println("called the has one player screen.");
		Text info = new Text();
		info.setText("Waiting For Second Player...");
		BorderPane bp = new BorderPane();
		bp.setCenter(info);
		Scene scene = new Scene(bp, 700,700);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public void makeBoard(Stage primaryStage, int player){
		if(game.hasTwoPlayers) {
			GridPane gridBoard = new GridPane();
			gridBoard.setMinSize(650, 650);
			for (int i = 0; i < 7; i++) {
				for (int j = 0; j < 6; j++) {
					GameButton button = new GameButton(boardNum[j][i]);
					button.setMinHeight(75);
					button.setMinWidth(75);
					if (boardNum[j][i] == 1) {
						button.setStyle("-fx-background-color: POWDERBLUE");
					} else if (boardNum[j][i] == 2) {
						button.setStyle("-fx-background-color: PLUM");
					}
					int finalI = i;
					int finalJ = j;
					button.setOnAction(e -> updateBoard(primaryStage, finalJ, finalI, player));
					gridBoard.add(button, i, j);
				}
			}
//		Text oldMove = new Text();
//		oldMove.setText("player " + player +" chose " + game.rowMove+", "+game.columnMove);
			BorderPane bp = new BorderPane();
			bp.setRight(gridBoard);
//		bp.setBottom(oldMove);
			Scene scene = new Scene(bp, 700, 700);
			primaryStage.setScene(scene);
			primaryStage.show();
		}
	}

	public Boolean checkTie(int[][] boardNum){
		int count = 0;
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 7; j++){
				if(boardNum[i][j]!=0){
					count++;
				}
			}
		}
		if(count == 42){
			return true;
		}
		return false;
	}


	public void updateBoard(Stage primaryStage, int row, int column, int player){
			if (checkBoard(row, column)) {
				boardNum[row][column] = player;
				game.rowMove = row;
				game.columnMove = column;
				game.turn = !game.turn;
//			System.out.println(game.turn + "" +game.whoseTurn );
				if (checkWin(game, boardNum) && player == 1) {
					game.p1Win = true;
				}
				if (checkWin(game, boardNum) && player == 2) {
					game.p2Win = true;
				}
				if (!game.p1Win && !game.p2Win && checkTie(boardNum)) {
					game.tie = true;
				}
				clientConnection.send(game);
			} else {
				Alert fail = new Alert(Alert.AlertType.INFORMATION);
				fail.setHeaderText("failure");
				fail.setContentText("Move is not valid...try again");
				fail.showAndWait();
				makeBoard(primaryStage, player);
			}

	}

	public void disabledBoard(Stage primaryStage, int player){
		if(game.hasTwoPlayers) {
			GridPane gridBoard = new GridPane();
			gridBoard.setMinSize(650, 650);
			for (int i = 0; i < 7; i++) {
				for (int j = 0; j < 6; j++) {
					GameButton button = new GameButton(boardNum[j][i]);
					button.setMinHeight(75);
					button.setMinWidth(75);
					button.setDisable(true);
					if (boardNum[j][i] == 1) {
						button.setStyle("-fx-background-color: POWDERBLUE");
					} else if (boardNum[j][i] == 2) {
						button.setStyle("-fx-background-color: PLUM");
					}
					gridBoard.add(button, i, j);
				}
			}
			Text oldMove = new Text();
			int cM = game.columnMove + 1;
			int rM = game.rowMove + 1;
			oldMove.setText("player " + player + " chose " + cM + ", " + rM);
			BorderPane bp = new BorderPane();
			bp.setRight(gridBoard);
			bp.setBottom(oldMove);
			Scene scene = new Scene(bp, 700, 700);
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		else{
			hasOnePlayer(primaryStage);
		}
	}


	public Boolean checkBoard(int row , int col){
		//returns if valid move or not
		if(boardNum[row][col]!=0){
			return false;
		}
		if( row==5 || boardNum[row+1][col]!=0){
			return true;
		}
		else {
			return false;
		}
	}

	public void reStartGame(Stage primaryStage){
		game.numPlayers ++;
		if(game.numPlayers==2){
			game.p1Win=false;
			game.p2Win = false;
			game.tie = false;
			game.rowMove=10;
			for(int i = 0; i <6; i++){
				for(int j = 0; j < 7; j++){
					boardNum[i][j] = 0;
				}
			}
			if(game.turn){
				if(game.whoseTurn){
					makeBoard(primaryStage, 1);
				}
				else{
					makeBoard(primaryStage,2);
				}
			}
			else{
				if(game.whoseTurn){
					disabledBoard(primaryStage, 2);
				}
				else{
					disabledBoard(primaryStage,1);
				}
			}
		}
		else{
			hasOnePlayer(primaryStage);
		}

	}

	public void exit(Stage primaryStage)  {
		game.hasTwoPlayers = false;
		game.p1Win=false;
		game.p2Win = false;
		game.tie = false;
//		game.rowMove=10;
//		clientConnection.send(game);
		System.out.println(game.hasTwoPlayers);
		acceptMessage(game, primaryStage);
		try {
			clientConnection.socketClient.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Platform.exit();
		primaryStage.close();

	}
	public void winScreen(Stage primaryStage, CFourInfo g){
		game.numPlayers = 0;
//		TextField text = new TextField();
		Text text = new Text();
		Button btnReplay = new Button("Replay");
		Button exit = new Button("Exit Game");
		if(game.p1Win){
			text.setText("Player One Wins");
		} else if(game.p2Win){
			text.setText("Player Two Wins");
		}else if(game.tie){
			text.setText("It's a Tie");
		}
		btnReplay.setOnAction(e->reStartGame(primaryStage));
		exit.setOnAction(e-> exit(primaryStage));
		VBox vb = new VBox();
		vb.getChildren().add(text);
		vb.getChildren().add(btnReplay);
		vb.getChildren().add(exit);
		BorderPane bp = new BorderPane();
		bp.setCenter(vb);
		Scene scene = new Scene(bp, 700,700);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
