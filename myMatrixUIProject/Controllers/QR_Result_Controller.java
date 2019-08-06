import javafx.fxml.FXML;
import java.io.*;
import javafx.scene.text.Text;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;


public class QR_Result_Controller {
	//need to display both matrices here in the grid panes
	private myMatrix saved;
	//setter to pass saved from last screen
	public void setSaved(myMatrix saved) {
		this.saved=saved;
	}
	private ArrayList<myMatrix> QR;

	//recall that QR means Q is orthogonal and R is upper triangular
	@FXML
	private TextField orthogonal_field;

	@FXML
	private TextField upper_field;

	@FXML
	private Button op_select;

	@FXML
	private GridPane orthogonal_matrix;

	@FXML
	private GridPane upper_matrix;

	//helper function to put entries into copy paste format for the user
	private static String copyFormat(myMatrix matrix) {

		String entries="";
		for (int i=1;i<=matrix.getRows();i++) {
			for (int j=1;j<=matrix.getColumns();j++) {
				if (i==matrix.getRows() && j==matrix.getColumns()) {
					entries=entries+""+matrix.getEntry(i,j);
				} else {
					entries=entries+""+matrix.getEntry(i,j)+", ";
				}
			}
		}
		return entries;

	}
	


	public void initialize() {
		//need to make TextFields uneditable so user can copy paste, but not mess up the result
		Platform.runLater(() ->{
			//creating array list of myMatrix objects representing Q and R
			QR = saved.houseHolderQR();
			//need to fill gridpane with corresponding matrix values
			myMatrix Q = QR.get(0);
			myMatrix R = QR.get(1);

			for (int i=0;i<=Q.getRows();i++) {
				for (int j=0;j<=Q.getColumns();j++) {
					//need to set textfield value then set it to uneditable and then add it to the grid
					//need to also set indices so user can see
					if (i==0) {
						Text entry = new Text(""+j);
						orthogonal_matrix.add(entry,j,i);
					} else if (j==0) {
						Text entry = new Text(""+i);
						orthogonal_matrix.add(entry,j,i);
					}else {
						TextField entry = new TextField(""+Q.getEntry(i,j));
						entry.setEditable(false);
						orthogonal_matrix.add(entry,j,i);
					}
	
				}
			}

			//need to also fill R matrix
			for (int i=0;i<=R.getRows();i++) {
				for (int j=0;j<=R.getColumns();j++) {
					if (i==0) {
						Text entry = new Text(""+j);
						upper_matrix.add(entry,j,i);
					}else if (j==0) {
						Text entry = new Text(""+i);
						upper_matrix.add(entry,j,i);
					} else {
						TextField entry = new TextField(""+R.getEntry(i,j));
						entry.setEditable(false);
						upper_matrix.add(entry,j,i);
					}
				}
			}
			//also need to paste entries to textfield in copy-paste format (i.e 1,2,3,4,-6,0.5, ...)
			//set disable is to ensure that user doesnt change the content of the textfield
			orthogonal_field.setText(copyFormat(Q));
			orthogonal_field.setEditable(false);

			upper_field.setText(copyFormat(R));
			upper_field.setEditable(false);

					
		});

	}


	@FXML
	public void goBack(ActionEvent action) throws IOException{
		FXMLLoader loader = new FXMLLoader(myMatrixUI.class.getResource("/fxml/Welcome_View.fxml"));
		Parent select_scene = loader.load();
		//passing saved to controller
		Welcome_Controller controller = loader.getController();
		controller.setSaved(this.saved);
		//end of passing saved
		Scene next_view = new Scene(select_scene,600,600);
		Stage stage = (Stage) op_select.getScene().getWindow();
		stage.setScene(next_view);
		stage.show();		
	}



}
