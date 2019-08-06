import javafx.fxml.FXML;
import java.io.*;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;



public class Constant_Select_Controller {
	//loading relevant variables
	//i only need one because the constant operations only involve one matrix 
	//i will store the result matrix in saved and then display it in the matrix result screen
	//no need for getter/setter for constant variable because i will process it on button click
	private myMatrix one;
	private myMatrix saved;
	private double multiplier;
	private String operation;


	public void setOne(myMatrix one ) {
		this.one=one;
	}

	public void setSaved(myMatrix saved) {
		this.saved=saved;
	}
	public void setOperation(String operation) {
		this.operation=operation;
	}

	@FXML
	private TextField constant;

	@FXML
	private Button next_view;

	public void goToMatrixResult() throws IOException{
		FXMLLoader loader= new FXMLLoader(myMatrixUI.class.getResource("/fxml/Matrix_Result_View.fxml"));
		Parent select_scene = loader.load();
		Matrix_Result_Controller controller = loader.getController();
		//passing variables to controller
		controller.setSaved(this.saved);
		controller.setOperation(this.operation);
		//end of passing variables
		Scene view = new Scene(select_scene,600,600);
		Stage stage = (Stage) next_view.getScene().getWindow();
		stage.setScene(view);
		stage.show();
	}


	@FXML
	public void go_next_view(ActionEvent action) {
		try {
			multiplier = Double.parseDouble(constant.getText().replaceAll(" ",""));
			if (this.operation.equals("Scale A Matrix By A Constant")) {
				//then we scale matrix by constant
				this.saved=this.one.getCopy();
				this.saved.scaleMatrix(multiplier);
				this.goToMatrixResult();
			} else {
				//only other option is to calculate matrix exponential
				this.saved=one.matrixExponential(multiplier);
				this.goToMatrixResult();

			}
			//NEED TO GO TO MATRIX RESULT SCREEN HERE
			//AND PASS SAVED AS A PARAMETER
//			this.goToMatrixResult();
		} catch (Exception e) {
			//we have number format error 
			//need to alert user to check that everything is a number
			Alert num = new Alert(Alert.AlertType.ERROR, "The Constant is not a number!");
		}
	}
	

}
