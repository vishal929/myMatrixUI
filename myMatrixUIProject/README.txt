Welcome to myMatrix!


Explanation of Components:
	myMatrixUI.java ---starts the application
	Class folder---- place where class files are stored and where fxml files for 	     	
		the UI are stored (in fxml subdirectory)
	Library folder---- includes myMatrix.java library (this is the logic for all 		
		my matrix operations and the myMatrix object)	
	Controllers folder --- java controllers for each fxml view which run the UI


How To Run My UI:
	1) Make sure you have JavaFX installed and have assigned it an environment path 
		variable (for my explanation we will call it $Path_FX)
	2) Navigate to the myMatrixUIProject folder in terminal/cmd
	3) We will first compile the program
	   mac/linux:javac --module-path $Path_FX --add-modules javafx.controls,javafx.fxml Controllers/*.java Library/*.java myMatrixUI.java -d Class 
	   windows: javac --module-path %Path_FX% --add-modules javafx.controls,javafx.fxml Controllers\*.java Library\*.java myMatrixUI.java -d Class

           This will compile the java components and store the class files in the Class folder

	4)Running the program
	  mac/linux: java --module-path $Path_FX --add-modules javafx.controls,javafx.fxml -cp Class myMatrixUI
          windows: java --module-path %Path_FX% --add-modules javafx.controls,javafx.fxml -cp Class myMatrixUI

What The Program Does:
	1) This is just a very basic UI for the library I created (myMatrix.java).
	
	2) The UI performs operations and the result of each operation is stored so that the user can choose to use the saved result for the next operation.
	
	3) In addition, the UI allows the user to quickly input values that they might have on hand in a copy paste format (i.e 1,2,3,4) where values are entered from left to right into the matrix
	4) NOTE: the eigenvalues solver works well for most real matrices, but for matrices of size greater than 75x75 it takes quite some time to retrieve the eigenvalues. 
	5) The UI also gives the copy paste format for results of a matrix so the user can save that seperately if they wish

Explanation of myMatrix.java
	1)Constructor: myMatrix ex = new myMatrix(3) ----creates a square matrix 3x3
		       myMatrix ex_2 = new myMatrix(3,3) ---creates a square matrix 3x3
		       myMatrix ex_3 = new myMatrix(3,4) ---creates matrix with 3 rows and 4 columns
	               myMatrix ex_4 = identity(4) ---- creates a 4x4 identity matrix

	2)Operations: All the operations from the UI are present in myMatrix.java, but there are a few more helper methods like getting the cofactor or adjoint matrix	


Final Note: Since this is my first project if you find anything wrong or have suggestions please let me know
