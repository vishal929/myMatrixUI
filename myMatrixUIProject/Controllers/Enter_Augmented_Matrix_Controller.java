import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import java.io.*;





public class Enter_Augmented_Matrix_Controller{

	//variables and setter/getter passed from past constructors
	private myMatrix one;
	private myMatrix two;
	private myMatrix saved;
	private String operation;
	private int first_rows;
	private int first_columns;
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
	public void setFirstRows(int rows) {
		this.first_rows=rows;
	}
	public void setFirstColumns(int columns) {
		this.first_columns=columns;
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
			this.first_columns=this.one.getColumns();
			this.first_rows=this.one.getRows();
			//setting toggle group so only one radio button can be selected at a time
		ToggleGroup radio = new ToggleGroup();
		enter_values.setToggleGroup(radio);
		use_identity.setToggleGroup(radio);
		manual_entry.setToggleGroup(radio);
			//enter_values.setText(""+first_rows);
			//setting dimensions of augmented matrix
		this.second_rows=this.first_rows;
		this.second_columns=1;
			//setting top text to be matrix dimensions so user doesnt forget
		top_text.setText("Please Choose an Option To Enter Your Second "+this.first_rows+"x"+1+" Augmented Matrix!");
		//NEED TO INITIALIZE GRIDPANE TO HAVE ROWS AND COLUMNS
		int colcount=0;
		for (int i=0;i<=this.first_rows;i++) {
			
			for (int j=0;j<=1;j++) {
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

	//since the result is an array list of answer strings
	//we need to go to constant result screen to display these
	private void goToConstantResultScreen() throws IOException{
		//need to go to constant RESULT VIEW AFTER CALCULATING SOLUTION
		FXMLLoader loader = new FXMLLoader(myMatrixUI.class.getResource("/fxml/Scalar_Result_View.fxml"));
		Parent select_scene = loader.load();
		Scalar_Result_Controller controller = loader.getController();
		//passing needed variables
		//result is a set of strings so I am just setting the saved matrix equal to the matrix without the augmented part
		controller.setOne(this.one);
		controller.setTwo(this.two);
		controller.setSaved(this.one.getCopy());
		controller.setOperation(this.operation);

		Scene next_scene = new Scene(select_scene,600,600);
		Stage stage = (Stage) top_text.getScene().getWindow();
		stage.setScene(next_scene);
		stage.show();
	}


	@FXML
	public void enter_matrixTwo(ActionEvent action) throws IOException {
		//empty TextField has empty string text
		if (manual_entry.isSelected()) {
			//NEED TO PUT ALERT AND RETURN IF USER DOESNT ENTER A DOUBLE IN FIELD
			//initializing matrix
			this.two= new myMatrix(this.second_rows,this.second_columns);
			for (int i=1;i<=this.second_rows;i++) {
				for (int j=1;j<=this.second_columns;j++) {
					try { TextField toEnter = (TextField) this.getNode(matrix,i,j);
						if (toEnter.getText().replaceAll(" ","").equals("") || toEnter==null) {
							this.two.setEntry(i,j,0);
						} else {
							this.two.setEntry(i,j,Double.parseDouble(toEnter.getText().replaceAll(" ","")));	

						}

					} catch (Exception e) {
						Alert not_double = new Alert(Alert.AlertType.ERROR,"Please enter only numbers!");
						not_double.show();
						return;
					}
									}
			}

					//need to continue to next view here

					this.goToConstantResultScreen();
						
		} else if (enter_values.isSelected()) {
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
				this.two.addEntry(true_entries);
				this.two.print(3);
				//need to go to next view here
				//NEED TO CALCULATE SAVED HERE
				this.goToConstantResultScreen();
					
			} catch (Exception e) {
					//NEED TO PUT ALERT HERE TO SAY TO CHECK THAT ALL ENTRIES ARE DOUBLES!
					Alert not_number = new Alert(Alert.AlertType.ERROR,"Please Check That All Entries Are Numbers!");
					not_number.show();
					return;

			}

		} else if (use_identity.isSelected()) {
			if (this.second_rows==this.second_columns) {
				this.two=myMatrix.identity(this.second_rows);
				//need to go to next view here
				//NEED TO CALCULATE SAVED HERE
				this.goToConstantResultScreen();
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
