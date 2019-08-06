import javafx.fxml.FXML;
import java.io.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.scene.text.Text;
import javafx.application.Platform;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;





public class Enter_Second_Matrix_Controller{
	//ON THIS CONTROLLER I WILL NEED TO FIGURE OUT NEXT SCREEN BASED ON
	//WHAT OPERATION WAS SELECTED TWO SCREENS AGO IN WELCOME_VIEW

	//variables and setter/getter passed from past constructors
	private myMatrix one;
	private myMatrix two;
	private myMatrix saved;
	private String operation;
	private int second_rows;
	private int second_columns;

	public void setOne(myMatrix one) {
		this.one=one;
	}
	public void setTwo(myMatrix two) {
		this.two=two;
	}
	public void setSaved(myMatrix saved) {
		this.saved=saved;
	}
	public void setOperation(String operation) {
		this.operation=operation;
	}

	public void setSecondRows(int rows) {
		this.second_rows=rows;
	}
	public void setSecondColumns(int columns) {
		this.second_columns=columns;
	} 
	

	@FXML
	private GridPane matrix;

	@FXML
	private RadioButton enter_values;

	@FXML
	private RadioButton use_identity;

	@FXML
	private RadioButton manual_entry;

	@FXML
	private TextField enter_line;

	@FXML
	private Button submit_entered;

	@FXML
	private Text top_text;


	public void initialize() {
		Platform.runLater(() -> {
			//setting toggle group so only one radio button can be selected at a time
		ToggleGroup radio = new ToggleGroup();
		enter_values.setToggleGroup(radio);
		use_identity.setToggleGroup(radio);
		manual_entry.setToggleGroup(radio);
			//enter_values.setText(""+first_rows);
			//setting top text to be matrix dimensions so user doesnt forget
		top_text.setText("Please Choose an Option To Enter Your Second "+this.second_rows+"x"+this.second_columns+" Matrix!");
		//NEED TO INITIALIZE GRIDPANE TO HAVE ROWS AND COLUMNS
		int colcount=0;
		for (int i=0;i<=this.second_rows;i++) {
			
			for (int j=0;j<=this.second_columns;j++) {
				//"matrix" is my gridpane
				//indices start from 0 for javafx
				if (i==0) {
					Text col = new Text(""+j);
					matrix.add(col,j,i);
					matrix.setHalignment(col,HPos.CENTER);
				} else if (j==0) {
					Text row= new Text(""+i);
					matrix.add(row,j,i);
					matrix.setHalignment(row,HPos.CENTER);
				} else {
					TextField to_enter = new TextField();
					matrix.add(to_enter,j,i);
					matrix.setHalignment(to_enter,HPos.CENTER);
				}
			}
		}
		
		});


	}

/*
	//helper function for getEntries
	private static boolean hasComma(String line) {
		for (int i=0;i<line.length();i++) {
			if (line.charAt(i)==',') {
				return true;
			}
		}
		return false;
	}
*/
	//function for user input in straight line with commas seperating double numbers
	private static ArrayList<String> getEntries(String line) {
		ArrayList<String> entries = new ArrayList<String>();
		int comma=-1;
		for (int i=0;i<line.length();i++) {
			if (line.charAt(i)==',') {
				entries.add(line.substring(comma+1,i));
				comma=i;

			} else if (i==line.length()-1){
				entries.add(line.substring(comma+1));
			}
		}
		return entries;
	}
	
	//function for accessing textField inputted in GridPane
	//I will have to cast Node to type TextField after returning the Node
	private Node getNode(GridPane grid, int row, int column) {
		for (int i=1;i<grid.getChildren().size();i++) {
			Node toCheck = grid.getChildren().get(i);
			if (GridPane.getColumnIndex(toCheck)==column && GridPane.getRowIndex(toCheck)==row) {
				return toCheck;
			}
		}
		return null;
	} 


	public void goToMatrixResult() throws IOException{
		FXMLLoader loader= new FXMLLoader(myMatrixUI.class.getResource("/fxml/Matrix_Result_View.fxml"));
		Parent select_scene = loader.load();
		Matrix_Result_Controller controller = loader.getController();
		//passing needed variables
		controller.setSaved(this.saved);
		controller.setOperation(this.operation);
		//end of passing
		Scene new_view = new Scene(select_scene,600,600); 
		Stage stage = (Stage) top_text.getScene().getWindow();
		stage.setScene(new_view);
	       	stage.show();	
	}


	//function for determining next view based on operation
	//i.e if user selected matrix addition, then we move on to select 2nd matrix view
	//i.e if user selected swap rows, we need to move on to row select screen etc.
	private void selectNextScreen() throws IOException{
		//based on this.operation
		//need to calculate SAVED based on operation and then 
		//move onto next screen
		//we can only come to this screen if operation is one requiring two matrices
		//i.e Add Matrices, Subtract Matrices, Get the projection, or multiply matrices
		if (this.operation.equals("Add Matrices")) {
			this.saved = this.one.addMatrix(this.two);
			//MATRIX RESULT SCREEN HERE
			this.goToMatrixResult();
		} else if (this.operation.equals("Subtract Matrices")) {
			this.saved = this.one.subtractMatrix(this.two);
			//MATRIX RESULT SCREEN HERE
			this.goToMatrixResult();
		} else if (this.operation.equals("Get the Projection Of One Vector Onto Another")) {
			this.saved = this.one.getProjection(this.two);
			//MATRIX RESULT SCREEN HERE
			this.goToMatrixResult();
		} else {
			//this operation can only be multiply matrices
			this.saved=this.one.multiplyMatrix(this.two);
			//MATRIX RESULT SCREEN HERE
			this.goToMatrixResult();
		}
	}


	@FXML
	public void enter_matrixTwo(ActionEvent action) throws IOException{
		//empty TextField has empty string text
		if (manual_entry.isSelected()) {
			//NEED TO PUT ALERT AND RETURN IF USER DOESNT ENTER A DOUBLE IN FIELD
			//initializing matrix
			this.two= new myMatrix(this.second_rows,this.second_columns);
			for (int i=1;i<=this.second_rows;i++) {
				for (int j=1;j<=this.second_columns;j++) {
					try {
						TextField entry = (TextField) this.getNode(matrix,i,j);
						if (entry.getText().replaceAll(" ","").equals("") || entry==null) {
							this.two.setEntry(i,j,0);
						} else {
							this.two.setEntry(i,j,Double.parseDouble(((TextField)this.getNode(matrix,i,j)).getText().replaceAll(" ","")));	

						}

					} catch (Exception e) {
						Alert not_double = new Alert(Alert.AlertType.ERROR,"Please enter only numbers!");
						not_double.show();
						return;
					}
				
				} 
			}			
				//need to continue to next view here
				this.selectNextScreen();
				//NEED TO FIX MYMATRIX RESULT SCREEN

	
		}else if (enter_values.isSelected()) {
			//need to make method here for user inputting values
			ArrayList<String> entries = getEntries(enter_line.getText());
			//checking if number of entries is equal to rowsxcolumns
			if (entries.size()!=(this.second_rows*this.second_columns)) {
				//NEED TO DISPLAY ALERT HERE TO CHECK ENTRIES
				Alert wrong_dimension=new Alert(Alert.AlertType.ERROR,"Please Check Your Entries!");
				wrong_dimension.show();
				return;
			}
			
			try {
				//trying to parse double from string values
				//may give numberformat exception if string
				//cannot be converted to double
				//initializing matrix
				this.two=new myMatrix(this.second_rows,this.second_columns);
				double[] true_entries = new double[this.second_rows*this.second_columns];
				for (int j=0;j<entries.size();j++) {
					true_entries[j]=Double.parseDouble(entries.get(j).replaceAll(" ",""));

				}
				//adding entries to matrix
				this.two.addEntry(true_entries);
					
			} catch (Exception e) {
					//NEED TO PUT ALERT HERE TO SAY TO CHECK THAT ALL ENTRIES ARE DOUBLES!
					Alert not_number = new Alert(Alert.AlertType.ERROR,"Please Check That All Entries Are Numbers!");
					not_number.show();
					return;

			}
			//going to new screen
			this.selectNextScreen();

		} else if (use_identity.isSelected()) {
			if (this.second_rows==this.second_columns) {
				this.two=myMatrix.identity(this.second_rows);
				//need to go to next view here
				//NEED TO CALCULATE SAVED HERE
				this.selectNextScreen();
			} else {
				//NEED TO SET ALERT TO INFORM USER THAT IDENTITY CANNOT BE SELECTED FOR NON SQUARE MATRIX INPUT
				Alert non_identity = new Alert(Alert.AlertType.ERROR, "Identity Matrices Can Only Be Square!");
				non_identity.show();
				return;
			}
		} else {
			//NEED TO SET ALERT FOR USER TO SELECT RADIO BUTTON
			Alert please_select = new Alert(Alert.AlertType.ERROR, "Please Select an Option!");
			please_select.show();
		}
	}
}
