import javafx.fxml.FXML;
import javafx.scene.Scene;
import java.io.*;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;




public class Row_Options_Controller {
	//arguments from previous controller i need to pass
	private myMatrix one;
	private myMatrix saved;
	private String operation;
	//i dont need myMatrix two because these operations do not require two matrices
	//setter
	public void setOne(myMatrix one) {
		this.one=one;
	}
	public void setSaved(myMatrix saved) {
		this.saved=saved; 
	}
	public void setOperation(String operation) {
		this.operation=operation;
	}

	private double scale;
	private int first_choice;
	private int second_choice;

	@FXML
	private Text title_text;

	@FXML
	private Text pick_first_text;

	@FXML
	private Text pick_second_text;

	@FXML
	private Text multiplier_text;

	@FXML
	private TextField first_field;

	@FXML
	private TextField second_field;

	@FXML
	private TextField multiplier_field;

	@FXML
	private Button submit_button;



	public void initialize() {
		//need to initialize text and textfields based on operation
		//i.e if I am just swapping rows or columns I do not need a multiplier 
		//if I am adding rows then I should include a multiplier to multiply first row by some value then add to second row
		Platform.runLater(() -> {
			if (this.operation.equals("Add Rows Of A Matrix")) {
				title_text.setText("Please Choose Rows And a Multiplier");
				pick_first_text.setText("First Row:");
				pick_second_text.setText("Second Row:");
				multiplier_text.setText("Enter a Multiplier:");

			} else if (this.operation.equals("Swap Rows Of A Matrix")) {
				title_text.setText("Please Choose Rows");
				pick_first_text.setText("First Row:");
				pick_second_text.setText("Second Row:");
				multiplier_text.setVisible(false);
				multiplier_field.setVisible(false);
			} else {
				//only other option is swapping columns
				title_text.setText("Please Choose Columns");
				pick_first_text.setText("First Column:");
				pick_second_text.setText("Second Column:");
				multiplier_text.setVisible(false);
				multiplier_field.setVisible(false);
			}
		});
	}

	//function to use FXMLLoader to go to matrix result view
	public void next_view() throws IOException{
		//need to go to matrix result view for every operation
		//need to pass this.saved to matrix result view 
		//(matrix result view is just a display for this.saved)
		FXMLLoader loader = new FXMLLoader(myMatrixUI.class.getResource("/fxml/Matrix_Result_View.fxml"));
		Parent select_scene = loader.load();
		Matrix_Result_Controller controller = loader.getController();
		//passing saved and operation to matrix view
		controller.setSaved(this.saved);
		controller.setOperation(this.operation);
		//end of passing variables
		Scene new_scene = new Scene(select_scene,600,600);
		Stage stage = (Stage) submit_button.getScene().getWindow();
		stage.setScene(new_scene);
		stage.show();
	}


	@FXML
	public void enter_options(ActionEvent action) throws IOException{
		//i also have to choose steps based on this.operation
		Alert num_error = new Alert(Alert.AlertType.ERROR,"Please Check Entries!");
		//error to check if entered row/column is actually in the matrix
		Alert dim_error= new Alert(Alert.AlertType.ERROR, "Please Make Sure Rows Exist!");
		if (this.operation.equals("Add Rows Of A Matrix")) {
			try {
				first_choice=Integer.parseInt(first_field.getText().replaceAll(" ",""));
				second_choice=Integer.parseInt(second_field.getText().replaceAll(" ",""));
				if (first_choice <=0 || first_choice>this.one.getRows() || second_choice <=0 || second_choice>this.one.getRows()) {
					dim_error.show();
					return;
				}
				scale = Double.parseDouble(multiplier_field.getText().replaceAll(" ",""));
				//performing operation and going to matrix result screen
				this.saved=this.one.getCopy();
				this.saved.addRow(scale,first_choice,second_choice);
				//going to matrix result screen
				this.next_view();
			}catch (Exception e) {
				//catching number format exception
				num_error.show();
			}
		}else if (this.operation.equals("Swap Rows Of A Matrix")) {
			try {
				first_choice = Integer.parseInt(first_field.getText().replaceAll(" ",""));
				second_choice=Integer.parseInt(second_field.getText().replaceAll(" ",""));
				if (first_choice <=0 || first_choice>this.one.getRows() || second_choice <=0 || second_choice>this.one.getRows()) {
					dim_error.show();
					return;
				}
				//performing operation and going to matrix result screen
				this.saved=this.one.getCopy();
				this.saved.swapRow(first_choice,second_choice);
				//going to matrix result screen
				this.next_view();
			}catch (Exception e) {
				//catching number format exception
				num_error.show();
			}
		} else {
			//last operation here must be swap columns
			try {
			first_choice = Integer.parseInt(first_field.getText().replaceAll(" ",""));
			second_choice=Integer.parseInt(second_field.getText().replaceAll(" ",""));
			if (first_choice <=0 || first_choice>this.one.getColumns() || second_choice<=0 || second_choice>this.one.getColumns()) {
				dim_error.show();
				return;
			}
			//performing operation and going to matrix result screen
			this.saved=this.one.getCopy();
			this.saved.swapRow(first_choice,second_choice);
			//performing operation and going to matrix result screen
			this.next_view();

			
			}catch (Exception e) {
				//catching number format exception
				num_error.show();
			}
		}
	}


}
