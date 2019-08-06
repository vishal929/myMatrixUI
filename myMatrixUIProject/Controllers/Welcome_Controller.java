import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.AnchorPane;
import java.io.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Welcome_Controller {
	//need to set operation so that other controllers can access it
	private String operation;


	
	public String getOperation() {
		return this.operation;
	}

	public void setOperation(String operation) {
		this.operation=operation;
	}

	//need to set matrix_1, matrix_2, and saved so other controllers can access it
	//ONCE EVERYTHING IS DONE MAKE SURE SAVED IS ACTUALLY SAVED
	private myMatrix one;
	private myMatrix two;
	private myMatrix saved;

	public myMatrix getOne() {
		return this.one;
	}
	public void setOne(myMatrix one) {
		this.one=one;
	}
	public myMatrix getTwo() {
		return this.two;
	}
	public void setTwo(myMatrix two) {
		this.two=two;
	}
	public myMatrix getSaved() {
		return this.saved;
	}
	public void setSaved(myMatrix saved){
		this.saved=saved; 
	}

	//welcome screen stuff
	@FXML	
	private ChoiceBox<String> choices;

	@FXML
	private Text welcome;

	@FXML
	private Button op_submit;



	//adding values to Choicebox
	//method after choicebox is created
	
	public void initialize() {
		String[] operations = { "Add Matrices","Subtract Matrices","Scale A Matrix By A Constant","Multiply Matrices", "Calculate The Matrix Exponential", "Add Rows Of A Matrix","Swap Rows Of A Matrix", "Swap Columns Of A Matrix", "Get Row Echelon Form", "Get Reduced Row Echelon Form", "Solve A Real System","Get The Determinant", "Get The Inverse","Get the Projection Of One Vector Onto Another",  "Get The QR Factorization", "Get The PLU Factorization", "Reduce Matrix To Hessenberg Form", "Get Eigenvalues Of A Real Matrix" }; 
		for (int i=0;i<operations.length;i++) {
       			choices.getItems().add(operations[i]);
		}
		//this line is so that the drop down starts at "Add Matrices"
		choices.setValue("Add Matrices");
		//SCREENS I NEED:
		//MATRIX ONE SELECT
		//MATRIX TWO SELECT
		//CONSTANT SELECT
		//MATRIX RESULT SCREEN
		//CONSTANT/S RESULT SCREEN
		//BACK BUTTON TO GO TO OPERATION SELECT AT THE END
		//(on any result screen)
		

	}


	@FXML
	public void select_op(ActionEvent event) throws IOException{
		//have to go to matrix enter screen as set by user
		//every one of my operations needs at least one matrix
		//so I can go to matrix select screen
		//then, depending on the operation, it will prompt for 
		//another matrix on a different screen
//		FXMLLoader matrix_select = new FXMLLoader(myMatrixUI.class.getResource("Matrix_Selector_View"));
//		Parent matrix_sel = matrix_select.load();
//		Scene select_matrix = new Scene(select_matrix,600,600);
//		stage.show();
		this.operation=choices.getValue();
		FXMLLoader matrix_select = new FXMLLoader(myMatrixUI.class.getResource("/fxml/Matrix_Selector_View.fxml"));
		Parent select_view = matrix_select.load();
		//passing variables to controller for setting
		Selector_Controller controller = matrix_select.getController();
		controller.setSaved(this.saved);
		controller.setOperation(this.operation);
		//end of passing variables to next controller
		Scene selector_view = new Scene(select_view,600,600);
		Stage stage= (Stage) op_submit.getScene().getWindow();
		stage.setScene(selector_view);
		stage.show();		
	}	

}
