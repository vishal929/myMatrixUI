import javafx.scene.control.Button;
import javafx.application.Platform;
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

public class Second_Matrix_Selector_Controller {
	//getting variables passed from welcome_screen_controller
	private String operation;
	private myMatrix one;
	private myMatrix two;
	private myMatrix saved;

	public void setOperation(String operation) {
		this.operation=operation;
	}

	public void setOne(myMatrix one) {
		this.one=one;
	}
	public void setTwo(myMatrix two) {
		this.two=two;
	}
	public void setSaved(myMatrix saved) {
		this.saved=saved;
	}
	
	//setting dimensions of the first entered matrix
	private int first_rows;

	private int first_columns;
		
	
	//FOR SECOND MATRIX
	
	private int second_rows;
	private int second_columns;


	public void setSecondRows(int rows) {
		this.second_rows=rows;
	}


	public void setSecondColumns(int columns) {
		this.second_columns=columns;
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
		Platform.runLater(() ->{
		this.first_rows = this.one.getRows();
		this.first_columns=this.one.getColumns();
		//need to restrict user to make operations defined
		this.restrictSize();
		//initializing radio buttons to be in togglegroup
		ToggleGroup select_radio = new ToggleGroup();
		create_new.setToggleGroup(select_radio);
		use_saved.setToggleGroup(select_radio);	
		});
	}

	public void restrictSize() {
		if (this.operation.equals("Add Matrices") || this.operation.equals("Subtract Matrices") || this.operation.equals("Get the Projection Of One Vector Onto Another")) {
			row_box.setText("" + this.first_rows);
			row_box.setEditable(false);

			column_box.setText("" + this.first_columns);
			column_box.setEditable(false);	
		} else {
			//operation must be multiply matrices
			row_box.setText("" +this.first_columns);
			row_box.setEditable(false);
		}
	}

	public boolean secondFeasible() {
		//checks if dimensions are feasible for second matrix
		//based on operation
		if (this.operation.equals("Add Matrices") || this.operation.equals("Subtract Matrices")) {
			//need to check if dimensions are equal
			if (this.first_rows!=this.second_rows || this.first_columns!=this.second_columns) {
				return false;
			} else {
				return true;
			}
		} else if (this.operation.equals("Get the Projection Of One Vector Onto Another")) {
			//need to check if non-1 length is equal to non-1 length of first 
			if (this.second_rows==1 && this.second_columns ==1) {
				if (this.first_rows!=1 || this.first_columns!=1) {
					return false;
				} else {
					return true;
				}
			} else if (this.second_rows==1) {
			
				if (this.second_columns==this.first_rows || this.second_columns ==this.first_columns) {
					return true;
				} else {
					return false;
				}
				
			} else if (this.second_columns==1) {
				if (this.second_rows==this.first_rows || this.second_rows==this.first_columns) {
					return true;
				} else {
					return false;
				}
			} else {
				//then both entries are not 1 meaning they are both greater than one
				//in this case, we do not have a vector so we return false
				return false; 
			}
		} else {
			//only other possibility is "Multiply Matrices"
			//condition is that matrix needs same number of rows as columns of the first
			if (this.second_rows!=this.first_columns) {
				return false;
			} else {
				return true;
			}
		}
	}

	//function to process saved matrix if used
	public void process() {
		if (this.operation.equals("Add Matrices")) {
			this.saved = this.one.addMatrix(this.two);
		} else if (this.operation.equals("Subtract Matrices")) {
			this.saved = this.one.subtractMatrix(this.two);
		} else if (this.operation.equals("Get the Projection Of One Vector Onto Another")) {
			this.saved = this.one.getProjection(this.two);
		} else {
			//final operation must be multiply matrices
			this.saved = this.one.multiplyMatrix(this.two);
		}
	}
	
		@FXML
	public void submit_matrix(ActionEvent action) throws IOException{
		if (create_new.isSelected()) {
			Alert num_error = new Alert(Alert.AlertType.ERROR,"Please check that both textfields are integers!");
			Alert zero_error = new Alert(Alert.AlertType.ERROR, "A size field is zero!");

			Alert negative_error = new Alert(Alert.AlertType.ERROR, "Cannot Enter Negative Dimensions!");

			try {
				second_rows=Integer.parseInt(row_box.getText().replaceAll(" ",""));
				second_columns=Integer.parseInt(column_box.getText().replaceAll(" ","")); //may throw number format exce
				//need to check if user entered 0 or not
				if (second_rows==0 || second_columns==0) {
					zero_error.show();
					return;
				} else if (second_rows<0 || second_columns<0){
					negative_error.show();
					return;
				}
			       	else {
					//need to detect errors here based on operation user has chosen
					if (!this.secondFeasible()) {
						//NEED TO ALERT THEN RETURN 
						Alert not_feasible=new Alert(Alert.AlertType.ERROR,"Matrix Dimensions are Not Feasible For This Operation!");
						not_feasible.show();
						return;
					}
					//i.e operations that require square matrices
					FXMLLoader matrix_select = new FXMLLoader(myMatrixUI.class.getResource("/fxml/Enter_Second_Matrix_View.fxml"));
					Parent matrix_select_view = matrix_select.load();
					Enter_Second_Matrix_Controller controller =matrix_select.getController();
					controller.setOne(this.one);
					controller.setSaved(this.saved);
					controller.setOperation(this.operation);
					controller.setSecondRows(this.second_rows);
					controller.setSecondColumns(this.second_columns);
			//		System.out.println(this.first_rows);
					Scene matrix_enter=new Scene(matrix_select_view,600,600);
					Stage stage =(Stage) create_new.getScene().getWindow();
					stage.setScene(matrix_enter);
					stage.show();
					


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
				return;
			} else {
			//NEED TO CHECK IF SAVED IS FEASIBLE
			this.second_rows=saved.getRows();
			this.second_columns=saved.getColumns();
			if (!this.secondFeasible()) {
				//alert then return;
				Alert not_feasible=new Alert(Alert.AlertType.ERROR,"Matrix Dimensions are Not Feasible For This Operation!");
				not_feasible.show();
				return;
				
			}
			this.two=this.saved.getCopy();
			//IF THEY USED SAVED THEN I NEED TO GO STRAIGHT TO RESULT VIEW
			//need to process the operation
			this.process();
			FXMLLoader matrix_select = new FXMLLoader(myMatrixUI.class.getResource("/fxml/Matrix_Result_View.fxml"));
			Parent matrix_select_view = matrix_select.load();
			Matrix_Result_Controller controller =matrix_select.getController();
			//passing variables
			controller.setSaved(this.saved);
			controller.setOperation(this.operation);
			Scene matrix_enter=new Scene(matrix_select_view,600,600);
			Stage stage =(Stage) create_new.getScene().getWindow();
			stage.setScene(matrix_enter);
			stage.show();
			
			
			}
		} else {
			//need to display popup here that says please make sure you have selected an option
			Alert choice = new Alert(Alert.AlertType.ERROR, "Please select an option!");
			choice.show();
			return;

		}

	}





}
