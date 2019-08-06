import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.ToggleGroup;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.io.*;

public class Selector_Controller {
	//getting variables passed from welcome_screen_controller
	private String operation;
	private myMatrix one;
	private myMatrix two;
	private myMatrix saved;

	public void setOperation(String operation) {
		this.operation=operation;
	}

	public void setSaved(myMatrix saved) {
		this.saved=saved;
	}
	//toggle group for radio buttons so only one is pressed at a time
	private ToggleGroup select_radio;
	
	//setting dimensions of the first entered matrix
	private int first_rows;

	private int first_columns;
	//will need to pass these to the next screen
	public int getFirstRows() {
		return this.first_rows;
	}
	public void setFirstRows(int rows) {
		this.first_rows=rows;
		
	}
	public int getFirstColumns() {
		return this.first_columns;
	}
	public void setFirstColumns(int columns) {
		this.first_columns=columns;
	}
	//end of setter/getter

	@FXML
	private RadioButton create_new;

	@FXML
	private RadioButton use_saved;

	@FXML
	private TextField row_box;

	@FXML
	private TextField column_box;

	@FXML
	private Button select_submit; 

	public void initialize() {
		//initializing radio buttons to be in togglegroup
		select_radio = new ToggleGroup();
		create_new.setToggleGroup(select_radio);
		use_saved.setToggleGroup(select_radio);	
	}
	
	//checks if first matrix is feasible for current operation
	private boolean checkFeasible() {
		//need a square matrix for these operations
		if (this.operation.equals("Get The Determinant") || this.operation.equals("Get The Inverse") || this.operation.equals("Get EigenValues Of A Real Matrix") || this.operation.equals("Reduce Matrix To Hessenberg Form")) {
			if (this.first_rows!=this.first_columns) {
				return false;
			} else {
				return true;
			}
		} else if (this.operation.equals("Get the Projection Of One Vector Onto Another")) {
			if (this.first_rows!=1 && this.first_columns!=1) {
				return false;
			} else {
				return true;
			}
		} else if (this.operation.equals("Get The QR Factorization")){
			if (this.first_columns>this.first_rows || this.first_columns==1) {
				return false;
			} else {
				return true;
			}
		} else if (this.operation.equals("Get The PLU Factorization")){
			if (this.first_columns==1 || this.first_rows==1) {
				return false;
			} else {
				return true;
			}

		}else {
			return true;
		}
	}	

	public void goToFirstMatrixEnter() throws IOException{
		//i will go to matrix_select if i need to enter a different first matrix
		FXMLLoader loader = new FXMLLoader(myMatrixUI.class.getResource("/fxml/Enter_Matrix_View.fxml"));
		Parent select_scene = loader.load();
		Enter_Matrix_Controller controller = loader.getController();
		//loading variables
		controller.setOne(this.one);
		controller.setFirstRows(this.first_rows);
		controller.setFirstColumns(this.first_columns);
		controller.setSaved(this.saved);
		controller.setOperation(this.operation);
		//end of passing relevant variables
		Scene new_scene = new Scene(select_scene,600,600);
		Stage stage = (Stage) create_new.getScene().getWindow();
		stage.setScene(new_scene);
		stage.show();
	}	
	public void goToSecondMatrixSelect() throws IOException{
		//will need to go to second matrix select if use saved is valid for first matrix
		//and the operation requires two matrices
		FXMLLoader loader = new FXMLLoader(myMatrixUI.class.getResource("/fxml/Second_Matrix_Selector_View.fxml"));
		Parent select_scene = loader.load();
		Second_Matrix_Selector_Controller controller = loader.getController();
		//passing relevant variables
		controller.setOne(this.one);
		controller.setSaved(this.saved);
		controller.setOperation(this.operation);
		//end of passing relevant variables
		Scene new_scene = new Scene(select_scene,600,600);
		Stage stage = (Stage) create_new.getScene().getWindow();
		stage.setScene(new_scene);
		stage.show();
	}

	public void goToScalarSelect() throws IOException{
		FXMLLoader loader= new FXMLLoader(myMatrixUI.class.getResource("/fxml/Constant_Select_View.fxml"));
		Parent select_view = loader.load();
		Constant_Select_Controller controller = loader.getController();
		//passing variables
		controller.setOne(this.one);
		controller.setSaved(this.saved);
		controller.setOperation(this.operation);
		//end of passing variables
		Scene new_scene = new Scene(select_view,600,600);
		Stage stage = (Stage) create_new.getScene().getWindow();
		stage.setScene(new_scene);
		stage.show();

	}

	public void goToRowOptions() throws IOException{
		//will only need first matrix for this
		FXMLLoader loader = new FXMLLoader(myMatrixUI.class.getResource("/fxml/Row_Options_View.fxml"));
		Parent select_scene = loader.load();
		Row_Options_Controller controller = loader.getController();
		//passing relevant variables
		controller.setOne(this.one);
		controller.setSaved(this.saved);
		controller.setOperation(this.operation);
		//end of passing relevant variables
		Scene new_scene = new Scene(select_scene,600,600);
		Stage stage = (Stage) create_new.getScene().getWindow();
		stage.setScene(new_scene);
		stage.show();


	}
	
	public void goToScalarResult() throws IOException{
		//this means that only one matrix is needed and we saved it
		FXMLLoader loader = new FXMLLoader(myMatrixUI.class.getResource("/fxml/Scalar_Result_View.fxml"));
		Parent select_scene = loader.load();
		Scalar_Result_Controller controller = loader.getController();
		//passing relevant variables
		controller.setOne(this.one);
		controller.setSaved(this.saved);
		controller.setOperation(this.operation);
		//end of passing relevant variables
		Scene new_scene = new Scene(select_scene,600,600);
		Stage stage = (Stage) create_new.getScene().getWindow();
		stage.setScene(new_scene);
		stage.show();

	}

	public void goToQRResult() throws IOException{
		FXMLLoader loader = new FXMLLoader(myMatrixUI.class.getResource("/fxml/QR_Result_View.fxml"));
		Parent select_scene = loader.load();
		QR_Result_Controller controller = loader.getController();
		//passing variables
		this.saved=this.one;
		controller.setSaved(this.saved);
		//end of passing variables
		Scene new_scene = new Scene(select_scene,600,600);
		Stage stage = (Stage) create_new.getScene().getWindow();
		stage.setScene(new_scene);
		stage.show();

	}

	public void goToPLUResult() throws IOException{
		FXMLLoader loader = new FXMLLoader(myMatrixUI.class.getResource("/fxml/PLU_Result_View.fxml"));
		Parent select_scene = loader.load();
		PLU_Result_Controller controller = loader.getController();
		//passing variables
		this.saved=this.one;
		controller.setSaved(this.saved);
		//end of passing variables
		Scene new_scene = new Scene(select_scene,600,600);
		Stage stage = (Stage) create_new.getScene().getWindow();
		stage.setScene(new_scene);
		stage.show();

	}

	public void goToAugmentedEnter() throws IOException{
		FXMLLoader loader = new FXMLLoader(myMatrixUI.class.getResource("/fxml/Enter_Augmented_Matrix_View.fxml"));
		Parent select_scene = loader.load();
		Enter_Augmented_Matrix_Controller controller = loader.getController();
		//passing variables
		controller.setOne(this.one);
		controller.setSaved(this.saved);
		controller.setOperation(this.operation);
		//end of passing variables
		Scene new_scene = new Scene(select_scene,600,600);
		Stage stage = (Stage) create_new.getScene().getWindow();
		stage.setScene(new_scene);
		stage.show();

	}

	public void goToMatrixResult() throws IOException{
		FXMLLoader loader = new FXMLLoader(myMatrixUI.class.getResource("/fxml/Matrix_Result_View.fxml"));
		Parent select_scene =loader.load();
		Matrix_Result_Controller controller = loader.getController();
		//passing variables
		controller.setSaved(this.saved);
		controller.setOperation(this.operation);
		//end of passing variables
		Scene new_scene = new Scene(select_scene,600,600);
		Stage stage = (Stage) create_new.getScene().getWindow();
		stage.setScene(new_scene);
		stage.show();

	}
	@FXML
	public void submit_matrix(ActionEvent action) throws IOException{
		if (create_new.isSelected()) {
			Alert num_error = new Alert(Alert.AlertType.ERROR,"Please check that both textfields are integers!");
			Alert zero_error = new Alert(Alert.AlertType.ERROR, "A size field is zero!");
			
			Alert negative_error = new Alert(Alert.AlertType.ERROR, "Cannot have negative dimensions!");

			try {
				
				first_rows=Integer.parseInt(row_box.getText().replaceAll(" ",""));
				first_columns=Integer.parseInt(column_box.getText().replaceAll(" ","")); //may throw number format exce
				//need to check if user entered 0 or not
				if (first_rows==0 || first_columns==0) {
					zero_error.show();
				}else if (first_rows<0 || first_columns<0) {
					negative_error.show();
				} else {
					//need to detect errors here based on operation user has chosen
					if (!this.checkFeasible()) {
						//NEED TO ALERT THEN RETURN 
						Alert not_feasible=new Alert(Alert.AlertType.ERROR,"Matrix Dimensions are Not Feasible For This Operation!");
						not_feasible.show();
						return;
					}
					//i.e operations that require square matrices

					this.goToFirstMatrixEnter();



					/*
					FXMLLoader matrix_select = new FXMLLoader(myMatrixUI.class.getResource("Enter_Matrix_View.fxml"));
					Parent matrix_select_view = matrix_select.load();
					Enter_Matrix_Controller controller =matrix_select.getController();
					controller.setOne(this.one);
					controller.setSaved(this.saved);
					controller.setOperation(this.operation);
					controller.setFirstRows(this.first_rows);
			//		System.out.println(this.first_rows);
					controller.setFirstColumns(this.first_columns);
					Scene matrix_enter=new Scene(matrix_select_view,600,600);
					Stage stage =(Stage) create_new.getScene().getWindow();
					stage.setScene(matrix_enter);
					stage.show();
					*/
					


				}
			} catch (Exception e){
				//need to display popup here to say please make sure you checked that 
				//both textfields are integers;
				//exception if string is not an integer
				num_error.show();

			}  
			
			
		} else if (use_saved.isSelected()){
			//need to check if saved matrix is square for certain operations
			Alert no_saved = new Alert(Alert.AlertType.ERROR,"No saved matrix from last operation!");
			//here i just need to select the matrix as a saved matrix
			if (this.saved==null) {
				no_saved.show();
			} else {
			//NEED TO CHECK IF SAVED IS FEASIBLE
			this.first_rows=saved.getRows();
			this.first_columns=saved.getColumns();
			if (!this.checkFeasible()) {
				//alert then return;
				Alert not_feasible=new Alert(Alert.AlertType.ERROR,"Matrix Dimensions are Not Feasible For This Operation!");
				not_feasible.show();
				return;
				
			}
			this.one=this.saved.getCopy();
			//IF THEY USED SAVED THEN I NEED TO GO STRAIGHT TO RESULT OR 2nd PROMPT BASICALLY
			//DEPENDS ON THE OPERATION !!!!!
			//if op requires two matrices, now I go to second matrix select
			//if op needs scalar input, i got to scalar screen
			//otherwise i go straight to matrix result

			if (this.operation.equals("Add Matrices") || this.operation.equals("Subtract Matrices") || this.operation.equals("Multiply Matrices") || this.operation.equals("Get the Projection Of One Vector Onto Another")) {
				this.goToSecondMatrixSelect();
			} else if (this.operation.equals("Solve A Real System")) {
				this.goToAugmentedEnter();
			} else if (this.operation.equals("Swap Rows Of A Matrix") || this.operation.equals("Swap Columns Of A Matrix") || this.operation.equals("Add Rows Of A Matrix")) {
				this.goToRowOptions();
			
			} else if (this.operation.equals("Scale A Matrix By A Constant") || this.operation.equals("Calculate The Matrix Exponential")) {

				this.goToScalarSelect();	
			} else if (this.operation.equals("Get Reduced Row Echelon Form") || this.operation.equals("Get Row Echelon Form") || this.operation.equals("Get The Inverse") || this.operation.equals("Reduce Matrix To Hessenberg Form")) {
				if (this.operation.equals("Get Reduced Row Echelon Form")) {
					this.saved = saved.reducedRowEchelon();
					this.goToMatrixResult();

				} else if (this.operation.equals("Get Row Echelon Form")) {
					this.saved = saved.rowEchelon();
					this.goToMatrixResult();
				} else if (this.operation.equals("Get The Inverse")) {
					//need to add functionality in matrix result to say that 
					//inverse doesnt exist because det is 0
					//when inverse doesnt exit saved is null
					this.saved=saved.getInverse();
					this.goToMatrixResult();
				} else {
					//the operation must be reducing to hessenberg form
					this.saved = saved.hessenberg();
					this.goToMatrixResult();

				}

			} else if (this.operation.equals("Get The QR Factorization")) {
				this.goToQRResult();
			} else if (this.operation.equals("Get The PLU Factorization")) {
				this.goToPLUResult();
			} else {
				//we are left with determinant and eigenvalues
				this.goToScalarResult();
			}
/*
			FXMLLoader matrix_select = new FXMLLoader(myMatrixUI.class.getResource("Enter_Matrix_View.fxml"));
			Parent matrix_select_view = matrix_select.load();
			Enter_Matrix_Controller controller =matrix_select.getController();
			controller.setOne(this.one);
			controller.setTwo(this.two);
			controller.setSaved(this.saved);
			controller.setOperation(this.operation);
			Scene matrix_enter=new Scene(matrix_select_view,600,600);
			Stage stage =(Stage) create_new.getScene().getWindow();
			stage.setScene(matrix_enter);
			stage.show();
			*/
				
			
			}
		} else {
			//need to display popup here that says please make sure you have selected an option
			Alert choice = new Alert(Alert.AlertType.ERROR, "Please select an option!");
			choice.show();

		}

	}





}
