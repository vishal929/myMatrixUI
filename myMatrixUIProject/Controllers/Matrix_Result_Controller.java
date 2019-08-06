import javafx.fxml.FXML;
import java.io.*;
import javafx.geometry.HPos;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;




public class Matrix_Result_Controller {
	//result matrix of operation is passed as saved 
	private myMatrix saved;

	public void setSaved(myMatrix saved) {
		this.saved=saved;
	}

	//operation is passed to help with customizing layout
	private String operation;

	public void setOperation(String operation) {
		this.operation=operation;
	}

	@FXML
	private Text title;

	@FXML
	private GridPane matrix;

	@FXML
	private Button option_select;

	@FXML
	private TextField copy_entries;

/*
	public void setTitle() {
		if (this.operation.equals("Add Matrices")) {
			title.setText("Result of Adding Matrices");
		} else if (this.operation.equals("Subtract Matrices")) {
			title.setText("Result of Subtracting Matrices");
		} else if (this.operation.equals("Scale A Matrix By A Constant")) {
			title.setText("Result of scaling");
		} else if (this.operation.equals())
	}
*/

	public String copyValues() {
		String copy_values="";
		for (int i=1;i<=this.saved.getRows();i++) {
			for (int j=1;j<=this.saved.getColumns();j++) {
				if (i==this.saved.getRows() && j==this.saved.getColumns()) {
					copy_values=copy_values+""+this.saved.getEntry(i,j);
				} else {
					copy_values=copy_values+""+this.saved.getEntry(i,j)+", ";
				}
			}
		}
		return copy_values;
		
	}

	public void initialize() {
		Platform.runLater(() ->{
			//if operation is get inverse and we come up with null
			//then no inverse exists
			this.saved.print(3);
			//prints numbers with 3 decimal places for the user``
			if (this.saved==null) {
				Text no_inverse = new Text("No Inverse Exists!");
				matrix.add(no_inverse,0,0);
				copy_entries.setText("No Inverse Exists!");
				copy_entries.setEditable(false);
				return;
			}
			//need to set title based on operation selected
			for (int i=0;i<=this.saved.getRows();i++) {
				for (int j=0;j<=this.saved.getColumns();j++) {
					if (i==0) {
					Text entry = new Text(""+j);
					matrix.add(entry,j,i);
					matrix.setHalignment(entry,HPos.CENTER);
					}
					else if (j==0) {
						Text entry=new Text(""+i);
						matrix.add(entry,j,i);
						matrix.setHalignment(entry,HPos.CENTER);
					} else {
						TextField enter=new TextField();
						enter.setText(""+this.saved.getEntry(i,j));
						enter.setEditable(false);
						matrix.add(enter,j,i);
						matrix.setHalignment(enter,HPos.CENTER);

					}
				}
			}
			copy_entries.setText(this.copyValues());
			copy_entries.setEditable(false);
			
		});
	}


	@FXML
	public void goBack(ActionEvent action) throws IOException{
		FXMLLoader loader = new FXMLLoader(myMatrixUI.class.getResource("/fxml/Welcome_View.fxml"));
		Parent select_view = loader.load();
		//passing saved for next operation
		Welcome_Controller controller = loader.getController();
		controller.setSaved(this.saved);
		//end of passing saved variable
		Scene next_scene = new Scene(select_view,600,600);
		Stage stage = (Stage) option_select.getScene().getWindow();
		stage.setScene(next_scene);
		stage.show();

	} 




}
