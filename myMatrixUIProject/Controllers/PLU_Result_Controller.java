import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.application.Platform;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.*;



public class PLU_Result_Controller {

	//saved matrix passed from last controller
	private myMatrix saved;

	public void setSaved(myMatrix saved) {
		this.saved=saved;
	}


	@FXML
	private GridPane permutation_matrix;

	@FXML 
	private GridPane lower_matrix;

	@FXML
	private GridPane upper_matrix;

	@FXML
	private TextField permutation_field;

	@FXML
	private TextField lower_field;

	@FXML
	private TextField upper_field;

	@FXML
	private Button return_button;


	//function to get copyPaste format of matrix
	private static String copyFormat(myMatrix matrix){
		String entries="";
		for (int i=1;i<=matrix.getRows();i++) {
			for (int j=1;j<=matrix.getColumns();j++) {
				if (j==matrix.getColumns() && i==matrix.getRows()) {
					entries=entries+""+matrix.getEntry(i,j);
				} else {
					entries=entries+""+matrix.getEntry(i,j)+", ";
				}
			}
		}
		return entries;
	}

	//function to make gridpane have mymatrix entries as desired
	private void addToGrid(GridPane grid, myMatrix one) {
		for (int i=0;i<=one.getRows();i++) {
			for (int j=0;j<=one.getColumns();j++) {
				if (i==0) {
					//column numbers for readability
					Text entry = new Text(""+j);
					grid.add(entry,j,i);

				}else if (j==0) {
					//row numbers for readability
					Text entry = new Text(""+i);
					grid.add(entry,j,i);
				} else {
					//actual entries of matrix
					TextField entry = new TextField(""+one.getEntry(i,j));
					entry.setEditable(false);
					grid.add(entry,j,i);
				}
			}
		}
	}

	public void initialize() {
		Platform.runLater(() ->{
			ArrayList<myMatrix> PLU = this.saved.PLU();
			myMatrix P = PLU.get(0);
			myMatrix L = PLU.get(1);
			myMatrix U = PLU.get(2);

			//displaying each matrix in each scrollpane GridPane

			this.addToGrid(permutation_matrix,P);
			this.addToGrid(lower_matrix,L);
			this.addToGrid(upper_matrix,U);

			//adding copy paste format to textfields
			permutation_field.setText(copyFormat(P));
			permutation_field.setEditable(false);
			lower_field.setText(copyFormat(L));
			lower_field.setEditable(false);
			upper_field.setText(copyFormat(U));
			upper_field.setEditable(false);

						
			


		});	
	}

	@FXML
	public void goBack(ActionEvent action) throws IOException{
		FXMLLoader loader = new FXMLLoader(myMatrixUI.class.getResource("/fxml/Welcome_View.fxml"));
		Parent select_scene = loader.load();
		Welcome_Controller controller = loader.getController();
		//passing saved to controller
		controller.setSaved(this.saved);
		Scene next_scene = new Scene(select_scene,600,600);
		Stage stage=(Stage) return_button.getScene().getWindow();
	        stage.setScene(next_scene);
		stage.show();	
	}

}
