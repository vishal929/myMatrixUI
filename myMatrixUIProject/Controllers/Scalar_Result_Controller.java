import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.io.*;



public class Scalar_Result_Controller {
	//need to pass saved
	private myMatrix saved;

	public void setSaved(myMatrix saved){
		this.saved=saved;
	}

	//need to pass Operation
	private String operation;

	public void setOperation(String operation) {
		this.operation=operation;
	}

	//need to pass matrix one and matrix two for system solver
	private myMatrix one;
	private myMatrix two;

	public void setOne(myMatrix one) {
		this.one=one;
	}

	public void setTwo(myMatrix two) {
		this.two=two;
	}



	@FXML
	private Text title;

	@FXML
	private Text operation_text;

	@FXML
	private GridPane entries;

	@FXML
	private Button operation_select;


	public void initialize() {
		Platform.runLater(() ->{
			//need to choose what to do based on operation
			if (this.operation.equals("Get The Determinant")) {
				double det = this.saved.getDeterminant();
				//setting title and category properly
				title.setText("Determinant Result");
				operation_text.setText("Determinant:");

				TextField category = new TextField("Determinant");
				category.setEditable(false);
				TextField determ = new TextField(""+det);
				determ.setEditable(false);
				entries.add(category,0,0);
				entries.add(determ,0,1);

			} else if (this.operation.equals("Get Eigenvalues Of A Real Matrix")){
				//then operation must be get eigenvalues
				//changing title and category
				title.setText("Eigenvalue Result");
				operation_text.setText("Eigenvalues:");

				ArrayList<String> eigs = this.saved.deflateEigenValues();
				//recall that in myMatrix the first element of this array list is "Eigenvalues : "
				TextField category = new TextField("Eigenvalues");
				category.setEditable(false);
				entries.add(category,0,0);
				
				for (int i=1;i<eigs.size();i++) {
					TextField entry = new TextField(eigs.get(i));
					entry.setEditable(false);
					entries.add(entry,0,i);
				}
			} else {
				//operation here must be solve system
				title.setText("Solve System Result");
				operation_text.setText("System Result");
				
				ArrayList<String> answers = this.one.solveSystem(this.two);
				if (answers==null) {
					//then the system is inconsistent
					TextField category = new TextField("System is Inconsistent!");
					category.setEditable(false);
					entries.add(category,0,0);
					return;
				}
				TextField category = new TextField("Solutions");
				category.setEditable(false);
				entries.add(category,0,0);
				//filling gridpane with solutions
				for (int i=0;i<answers.size();i++) {
					TextField answer = new TextField(answers.get(i));
					answer.setEditable(false);
					entries.add(answer,0,i+1);
				}
			}

		});
	}

	@FXML
	public void goBack(ActionEvent action) throws IOException {
		FXMLLoader loader = new FXMLLoader(myMatrixUI.class.getResource("/fxml/Welcome_View.fxml"));
		Parent select_scene = loader.load();
		//passing saved to controller
		Welcome_Controller controller = loader.getController();
		controller.setSaved(this.saved);
		Scene next_scene = new Scene(select_scene,600,600);
		Stage stage = (Stage) entries.getScene().getWindow(); 
		stage.setScene(next_scene);
		stage.show();
	}



}
