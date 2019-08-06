

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;


public class myMatrixUI extends Application {


	@Override
	public void start(Stage stage) throws IOException {
		stage.setTitle("myMatrix");
		//setting welcome screen and my controller file to control the UI elements
		FXMLLoader loader = new FXMLLoader(myMatrixUI.class.getResource("/fxml/Welcome_View.fxml"));
		System.out.println(myMatrixUI.class.getResource("/fxml/Welcome_View.fxml"));
//		Parent welcome_layout=FXMLLoader.load(getClass().getResource("Welcome_View.fxml"));
//
		Parent welcome_layout=loader.load();		
		Scene welcome = new Scene(welcome_layout,600,600);
		stage.setScene(welcome);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}



}
