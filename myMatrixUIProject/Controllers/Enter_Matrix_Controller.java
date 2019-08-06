import javafx.fxml.FXML;
import java.io.*;
import javafx.fxml.FXMLLoader;
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
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;





public class Enter_Matrix_Controller{
	//ON THIS CONTROLLER I WILL NEED TO FIGURE OUT NEXT SCREEN BASED ON
	//WHAT OPERATION WAS SELECTED TWO SCREENS AGO IN WELCOME_VIEW

	//variables and setter/getter passed from past constructors
	private myMatrix one;
	private myMatrix two;
	private myMatrix saved;
	private String operation;
	private int first_rows;
	private int first_columns;

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
	public void setFirstRows(int rows) {
		this.first_rows=rows;
	}
	public void setFirstColumns(int columns) {
		this.first_columns=columns;
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
		top_text.setText("Please Choose an Option To Enter Your First "+this.first_rows+"x"+this.first_columns+" Matrix!");
		//NEED TO INITIALIZE GRIDPANE TO HAVE ROWS AND COLUMNS
		int colcount=0;
		for (int i=0;i<=this.first_rows;i++) {
			
			for (int j=0;j<=this.first_columns;j++) {
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

/*	//helper function for getEntries
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


	//function for determining next view based on operation
	//i.e if user selected matrix addition, then we move on to select 2nd matrix view
	//i.e if user selected swap rows, we need to move on to row select screen etc.
	private void selectNextScreen() throws IOException{
		//based on this.operation
		if (this.operation.equals("Add Matrices") || this.operation.equals("Subtract Matrices") || this.operation.equals("Get the Projection Of One Vector Onto Another") || this.operation.equals("Multiply Matrices")) {
			//need to go to second matrix select
			FXMLLoader loader = new FXMLLoader (myMatrixUI.class.getResource("/fxml/Second_Matrix_Selector_View.fxml"));
			Parent select_view = loader.load();
			//passing variables to controller
			Second_Matrix_Selector_Controller controller = loader.getController();
			controller.setOne(this.one);
			controller.setSaved(this.saved);
			controller.setOperation(this.operation);
			//end of passing variables
			Scene next_view = new Scene(select_view,600,600);
			Stage stage= (Stage) submit_entered.getScene().getWindow();
			stage.setScene(next_view);
			stage.show();
		}else if (this.operation.equals("Solve A Real System")){
		       	//need to go to enter augmented matrix screen
			FXMLLoader loader = new FXMLLoader(myMatrixUI.class.getResource("/fxml/Enter_Augmented_Matrix_View.fxml"));
			Parent select_view = loader.load();
			Enter_Augmented_Matrix_Controller controller = loader.getController();
			//passing variables to next controller
			controller.setOne(this.one);
			controller.setOperation(this.operation);	
			controller.setSaved(this.saved);
			//end of passing variables
			Scene next_view = new Scene(select_view,600,600);
			Stage stage = (Stage) top_text.getScene().getWindow();
			stage.setScene(next_view);
			stage.show();

		}else if (this.operation.equals("Scale A Matrix By A Constant") || this.operation.equals("Calculate The Matrix Exponential")) {
			//need to go to constant select
			FXMLLoader loader= new FXMLLoader(myMatrixUI.class.getResource("/fxml/Constant_Select_View.fxml"));
			Parent select_view = loader.load();
			Constant_Select_Controller controller = loader.getController();
			controller.setOne(this.one);
			controller.setOperation(this.operation);
			controller.setSaved(this.saved);
			//end of passing variables
			Scene next_view = new Scene(select_view,600,600);
			Stage stage = (Stage) top_text.getScene().getWindow();
			stage.setScene(next_view);
			stage.show();
		} else if (this.operation.equals("Swap Rows Of A Matrix") || this.operation.equals("Swap Columns Of A Matrix") || this.operation.equals("Add Rows Of A Matrix")) {
			//need to go to row/column select screen
			FXMLLoader loader = new FXMLLoader(myMatrixUI.class.getResource("/fxml/Row_Options_View.fxml"));
			Parent select_scene = loader.load();
			Row_Options_Controller controller=loader.getController();
			controller.setOne(this.one);
			controller.setSaved(this.saved);
			controller.setOperation(this.operation);
			Scene next_scene = new Scene(select_scene,600,600);
			Stage stage = (Stage) top_text.getScene().getWindow();
			stage.setScene(next_scene);
			stage.show();
		} else if (this.operation.equals("Get The QR Factorization")) {
			//need to go to QR Factorization screen
			FXMLLoader loader = new FXMLLoader(myMatrixUI.class.getResource("/fxml/QR_Result_View.fxml"));
			Parent select_scene = loader.load();
			QR_Result_Controller controller = loader.getController();
			this.saved = this.one;
			controller.setSaved(this.saved);
			Scene next_scene = new Scene(select_scene,600,600);
			Stage stage = (Stage) top_text.getScene().getWindow();
			stage.setScene(next_scene);
			stage.show();

		} else if (this.operation.equals("Get The PLU Factorization")) {
			//need to go to PLU Factorization screen
			//PLU SCREEN IS 600x800 for size reasons (fitting 3 matrices)
			FXMLLoader loader = new FXMLLoader(myMatrixUI.class.getResource("/fxml/PLU_Result_View.fxml"));
			Parent select_scene = loader.load();
			PLU_Result_Controller controller = loader.getController();
			this.saved=this.one;
			controller.setSaved(this.saved);
			Scene next_scene = new Scene(select_scene,600,800);
			Stage stage = (Stage) top_text.getScene().getWindow();
			stage.setScene(next_scene);
			stage.show();
		} else if (this.operation.equals("Get The Determinant")|| this.operation.equals("Get Eigenvalues Of A Real Matrix")) {
			//need to go to constant/arrayList result screen
			FXMLLoader loader = new FXMLLoader(myMatrixUI.class.getResource("/fxml/Scalar_Result_View.fxml"));
			Parent select_scene = loader.load();
			Scalar_Result_Controller controller = loader.getController();
			this.saved = this.one;
			controller.setSaved(this.saved);
			controller.setOperation(this.operation);
			Scene next_scene = new Scene(select_scene,600,600);
			Stage stage = (Stage) top_text.getScene().getWindow();
			stage.setScene(next_scene);
			stage.show();
		} else {
			//need to go to matrix result screen
			//need to process matrix to show here
			this.process();
			FXMLLoader loader = new FXMLLoader(myMatrixUI.class.getResource("/fxml/Matrix_Result_View.fxml"));
			Parent select_scene = loader.load();
			Matrix_Result_Controller controller = loader.getController();
			controller.setSaved(this.saved);
			controller.setOperation(this.operation);
			Scene next_view = new Scene(select_scene,600,600);
			Stage stage = (Stage) top_text.getScene().getWindow();
			stage.setScene(next_view);
			stage.show();


		}
		
	}

	//IF WE NEED Matrix Result NEXT WE NEED TO PERFORM CALCULATION HERE
	//DETERMINATION OF RESULT SCREEN IS BASED ON THE OPERATION
	private void process() {
		if (this.operation.equals("Get Reduced Row Echelon Form")) {
			this.saved = this.one.reducedRowEchelon();
		} else if (this.operation.equals("Get Row Echelon Form")) {
			this.saved = this.one.rowEchelon();
		} else if (this.operation.equals("Get The Inverse")) {
			this.saved = this.one.getInverse();
		} else {
			//operation must be reduce to hessenberg form
			this.saved = this.one.hessenberg();
		}
	}


	@FXML
	public void enter_matrixOne(ActionEvent action) throws IOException{
		//empty TextField has empty string text
		if (manual_entry.isSelected()) {
			//NEED TO PUT ALERT AND RETURN IF USER DOESNT ENTER A DOUBLE IN FIELD
			//initializing matrix
			this.one= new myMatrix(this.first_rows,this.first_columns);
			for (int i=1;i<=this.first_rows;i++) {
				for (int j=1;j<=this.first_columns;j++) {
					try {   TextField entry = (TextField) this.getNode(matrix,i,j);
						if (entry.getText().replaceAll(" ","").equals("") || entry==null) {
							this.one.setEntry(i,j,0);
						} else {
							this.one.setEntry(i,j,Double.parseDouble(((TextField)this.getNode(matrix,i,j)).getText().replaceAll(" ","")));	

						}

					} catch (Exception e) {
						Alert not_double = new Alert(Alert.AlertType.ERROR,"Please enter only numbers!");
						not_double.show();
						return;
					}
					//need to continue to next view here
					//NEED TO CHECK IF WE NEED TO GO DIRECTLY TO RESULT SCREEN 
					//IF SO I CAN JUST SAVE IT TO SAVED
					//THEN DISPLAY SAVED IN NEXT SCREEN

									}
			}
						
			this.selectNextScreen();
		} else if (enter_values.isSelected()) {
			//need to make method here for user inputting values
			ArrayList<String> entries = getEntries(enter_line.getText());
			//checking if number of entries is equal to rowsxcolumns
			if (entries.size()!=(this.first_rows*this.first_columns)) {
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
				this.one=new myMatrix(this.first_rows,this.first_columns);
				double[] true_entries = new double[this.first_rows*this.first_columns];
				for (int j=0;j<entries.size();j++) {
					true_entries[j]=Double.parseDouble(entries.get(j).replaceAll(" ",""));

				}
				this.one.addEntry(true_entries);
				//need to go to next view here
				this.selectNextScreen();
					
			} catch (NumberFormatException e) {
					//NEED TO PUT ALERT HERE TO SAY TO CHECK THAT ALL ENTRIES ARE DOUBLES!
					Alert not_number = new Alert(Alert.AlertType.ERROR,"Please Check That All Entries Are Numbers!");
					not_number.show();
					return;

			}

		} else if (use_identity.isSelected()) {
			if (this.first_rows==this.first_columns) {
				this.one=myMatrix.identity(this.first_rows);
				//need to go to next view here
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
