import java.util.Arrays;
import java.util.ArrayList;
public class myMatrix {

	private double[][] matrix;

					//constructor initializes the matrix
	public myMatrix(int numberofrows, int numberofcolumns) {
		this.matrix=new double[numberofrows][numberofcolumns];

		//column have the same number of entries as the number of rows


	}

					//overloading constructor for square matrix
	public myMatrix(int square) {
		this.matrix= new double[square][square];
	}
		//checks if the matrix is a valid matrix
	public boolean isMatrix() {
		if (this==null || (this.getRows()==0 && this.getColumns()==0)) {
			return false;
		} else {
			return true;
		}
	}

	public int getRows() {
		//error case----empty matrix
			if (this.matrix.length!=0 && this.matrix[0].length!=0) {
		return this.matrix.length;
			} else {
				System.out.println("The matrix is empty!");
				return -1;
			}
	}

	public int getColumns() {
		if (this.matrix.length!=0 && this.matrix[0].length!=0) {
			return this.matrix[0].length;
		} else {
			System.out.println("The matrix is empty!");
			return -1;
		}
	}

	//method for checking if given matrix is valid for performing operations on
	public boolean isValid() {
		if (this.getRows()==0 || this.getColumns()==0 || this==null) {
			return false;
		} else {
			return true;
		}
	}

	//method for determining if matrix is square or not (I know this is easy to code, but this method makes it easier for readers to read my code (hopefully!))
	public boolean isSquare() {
		if (this.getRows()==this.getColumns()) {
			return true;
		} else {
			return false;
		}
	}

		//adds entries from left to right to our matrix
	public void addEntry(double ...ds ) {
		// error case--- dimensions do not match matrix dimensions
		if (ds.length!=this.matrix.length*this.matrix[0].length) {
			System.out.println("The Matrix has "+this.matrix.length+" rows and "+ this.matrix[0].length+ " columns.");
			System.out.println("Please call the function again with a valid number of entries.");
		}
		else {
			//iterates through each index in the matrix from left to right and adds the input
			// we need a count for the parameter
			int count=0;
			for (int i=0;i<this.matrix.length;i++) {
				for (int j=0;j<this.matrix[0].length;j++) {

				this.matrix[i][j]=ds[count];
				count++;
				}
			}
		}
	}


	//if a user has row arrays defined already, they can add them to an empty matrix with this method
	public void fillRows(double[]... ds){
		if (ds[0].length!=this.getColumns() || ds.length!=this.getRows()) {
			System.out.println("Dimension mismatch! Please check your rows and the matrix and try again!");
		} else {
			double[] entries = new double[ds.length*ds[0].length];
			//adding entries into one big array to use addEntry
			int count=0;
			for (int i=0;i<ds.length;i++) {
				for (int j=0;j<ds[0].length;j++) {
					entries[count]=ds[i][j];
					count++;
				}
			}
			this.addEntry(entries);
		}
	}

	//if a user has column arrays defined already,they can add them to an empty matrix with this method
	public void fillColumns(double []... ds) {
		if (ds.length!=this.getColumns() || ds[0].length!=this.getRows()) {
			System.out.println("Dimension mismatch! Please check your rows and the matrix and try again!");
		} else {
			double [] entries = new double[ds.length*ds[0].length];
			//adding entries into one big array to use addEntry
			int count=0;
			for (int i=0;i<ds[0].length;i++) {
				for (int j=0;j<ds.length;j++) {
					entries[count]=ds[j][i];
					count++;
				}
			}
			this.addEntry(entries);
		}
	}


		//gets the desired entry in the matrix
	public double getEntry(int row, int column) {
		//error case ---dimension mismatch
		if (row<=this.matrix.length && column<=this.matrix[0].length) {
		return this.matrix[row-1][column-1];
	} else {
			System.out.println("Dimension mismatch. Please call the function again with valid parameters.");
			return -123;
		}


	}

	public void setEntry(int row,int column, double entry) {
		this.matrix[row-1][column-1]=entry;
	}
		// gets the desired row in the matrix
	public double[] getRow(int row) {
		return this.matrix[row-1];
	}

		//gets the column as an array
	public double[] getColumn(int column) {
		//error case---- desired column doesnt exist
		double[] thiscolumn=new double[this.matrix.length];
		if (column <= this.matrix[0].length && column >0) {

		for (int i=0;i<this.matrix.length;i++) {
			//first column is 0, last column is length-1, etc
			thiscolumn[i]=this.matrix[i][column-1];
		}
		return thiscolumn;
		} else {
			System.out.println("Please call the function again with a valid column number.");
			return null;
		}
	}
		//returns a copy of the matrix the method is called on
	public myMatrix getCopy() {
		myMatrix copy = new myMatrix(this.getRows(),this.getColumns());
		double [] entries = new double[this.getRows()*this.getColumns()];
		int count=0;
		for (int i=1;i<=this.getRows();i++) {
			for (int j=1;j<=this.getColumns();j++) {
				entries[count]=this.getEntry(i,j);
				count++;
			}
		}
		copy.addEntry(entries);
		return copy;
	}

		//adds the first array parameter to the second and replaces the second
		//multiplier is the first parameter for the first array
	public void addRow(double multiplier, int row1, int row2) {
		//we need to multiply each entry in a duplicate of row1 by multiplier and add to row 2

		//rowtoreplace references the row2 row in the matrix object
		double [] rowtoreplace= this.getRow(row2);

		//replacing row desired by sum of first row multiplied by some multiplier

			for (int i=0;i<this.matrix[0].length;i++) {
				// we use i-1 for column in getEntry because that is how the method is written above
				rowtoreplace[i]=(this.getEntry(row1, i+1)*multiplier)+rowtoreplace[i];
			}



	}

	public void multiplyRow(double multiplier, int row1) {
		double [] rowtochange = this.getRow(row1);
		for (int i=0;i<this.getColumns();i++) {
			rowtochange[i]=rowtochange[i]*multiplier;
		}
	}

		//since we now have row addition, we should also add a method that swaps rows

	public void swapRow(int row1,int row2) {
		double [] placeholder = this.getRow(row1);
		this.matrix[row1-1]=this.getRow(row2);
		this.matrix[row2-1]=placeholder;
	}

	public void swapColumn(int col1, int col2) {
		for (int i=1;i<=this.getRows();i++) {
			double temp = this.getEntry(i,col1);
			this.setEntry(i,col1,this.getEntry(i,col2));
			this.setEntry(i,col2,temp);
		}
	}

	public String toString() {
		if (this.matrix.length==0 || this.matrix[0].length==0) {
			System.out.println("The matrix is empty!!. Please add values!");
			return null;
		} else {
			String stringvalue ="";
			for (int i=0;i<this.matrix.length;i++) {
				for (int j=0;j<this.matrix[0].length;j++) {
						if (j!=this.matrix[0].length-1) {
							stringvalue+=this.matrix[i][j]+", ";
						} else {
							stringvalue+=this.matrix[i][j];
						}
				}
				//line break between matrix rows
				stringvalue+="\n";
			}
			return stringvalue;
		}
	}


	public void print(int decimals) {
		String decimal = "%."+decimals+"f";
		//for formatting decimal place	
		if (this.matrix.length==0 || this.matrix[0].length==0) {
			System.out.println("The matrix is empty!!. Please add values!");
		} else {
			for (int i =1;i<=this.getRows();i++) {
				for (int j=1;j<=this.getColumns();j++) {
					if (j==this.getColumns()) {
						System.out.print(String.format(decimal,this.getEntry(i,j)));
					} else {
						System.out.print(String.format(decimal,this.getEntry(i,j))+", ");
					}
				}
				//line break
				System.out.println();
			}
			//line break in the end in case user is printing several matrices
			System.out.println();
		} 
	}


	//overloading print method
	//if user specifies no decimal place limit, then full toString matrix is printed
	public void print() {
		System.out.println(this);
	}


	public myMatrix addMatrix( myMatrix othermatrix ) {
		if (othermatrix.getRows()==this.getRows() && othermatrix.getColumns()==this.getColumns()) {
			double[] result = new double[othermatrix.getRows()*othermatrix.getColumns()];
				//adding sum to the result
			int count=0;
			for (int i=1;i<=othermatrix.getRows();i++) {
				for (int j=1;j<=othermatrix.getColumns();j++) {
					result[count]=othermatrix.getEntry(i, j)+this.getEntry(i, j);
					count++;
				}
			}
			myMatrix results = new myMatrix(othermatrix.getRows(), othermatrix.getColumns());
			results.addEntry(result);
			return results;
		} else {

			return null;
		}
	}

		//returns the result of traditional matrix multiplication;
	public myMatrix multiplyMatrix(myMatrix othermatrix) {
		if (this.getColumns()==othermatrix.getRows()) {
			//resulting matrix has as many rows as the first and as many columns as the second
			myMatrix result = new myMatrix(this.getRows(), othermatrix.getColumns());
			double[] entries = new double[this.getRows()*othermatrix.getColumns()];
			double colSum=0;
			int count=0;

			for (int i=1;i<=this.getRows();i++) {
				for (int z=1;z<=othermatrix.getColumns();z++) {
					for (int j=1;j<=this.getColumns();j++) {
						//using fact that column of first is the row of the second
						colSum+=(this.getEntry(i,j)*othermatrix.getEntry(j,z));

					}
					entries[count]=colSum;
					colSum=0;
					count++;
				}
			}

			result.addEntry(entries);
			return result;
		} else {
			System.out.println("This matrix does not have the same number of columns as the rows of the other matrix! (Dimension mismatch)");
			return null;
		}
	}

		//method scales the whole matrix by some scaling factor;
	public void scaleMatrix(double scale) {
		if (this.matrix.length==0 || this.matrix[0].length==0 || this==null) {
			System.out.println("The matrix is empty!");
		} else {
			for (int i=0;i<this.getRows();i++) {
				for (int j=0;j<this.getColumns();j++) {
					this.matrix[i][j]=scale*this.matrix[i][j];
				}
			}
		}

	}
		//method that returns a matrix of 1st entry - 2nd entry;
	public myMatrix subtractMatrix(myMatrix otherMatrix) {
		if (otherMatrix.getRows()==this.getRows() && otherMatrix.getColumns()==this.getColumns()) {
			otherMatrix.scaleMatrix(-1);
			myMatrix result =this.addMatrix(otherMatrix);

			return result;


		} else {
			System.out.println("Dimensions do not match!");
			return null;
		}
	}
		//method to return the transpose of a matrix
	public myMatrix getTranspose() {
		myMatrix transpose = new myMatrix(this.getColumns(),this.getRows());
		int count=0;
		double[] transposed = new double[this.getColumns()*this.getRows()];
		for (int j=1;j<=this.getColumns();j++) {
			for (int i=1;i<=this.getRows();i++) {
				transposed[count]=this.getEntry(i,j);
				count++;
			}
		}
		transpose.addEntry(transposed);
		return transpose;
	}


	public myMatrix matrixExponential(double exponential) {
		if (this.matrix.length==0 || this.matrix[0].length==0 || this==null) {
			System.out.println("The matrix is empty!");
			return null;
		} else {
			double [] result = new double[this.getColumns()*this.getRows()];
			int count=0;
			for (int i=1;i<=this.getRows();i++) {
				for (int j=1;j<=this.getColumns();j++) {

					result[count]=Math.pow(this.getEntry(i,j), exponential);
					count++;
				}
			}
				myMatrix finalresult=new myMatrix(this.getRows(),this.getColumns());
				finalresult.addEntry(result);
				return finalresult;
			}
		}

		//helper function
	public boolean restOfColumnZero(int rowstart, int column) {
		boolean result=true;
		for (int j=rowstart;j<=this.getRows();j++) {
			if (this.getEntry(j, column)!=0) {
				result=false;
				break;
			}
		}
		return result;
	}




	//now we have all the methods we need , so we can make complex matrix operations

	//helper function for rowEchelon
	//moves zero rows to bottom in a specific column
	public void moveZerosToBottom(int row,int column) {
		//once we hit a zero that we put towards the bottom, we can break
		int breakrow=-1;
		for (int i=row;i<=this.getRows();i++) {
			if (this.getEntry(i,column)==0) {
				if (i==breakrow) {
					break;
				}
				for (int j=this.getRows();j>i;j--) {
					if (this.getEntry(j,column)!=0) {
						//swap zero entry row with nonzero entry row closest to the bottom
						this.swapRow(i,j);
						breakrow=j;
						break;
					}
				}
			}
		}
	}

	//tries to account for rounding error
	public void checkZeros() {
		for (int i=1;i<=this.getRows();i++) {
			for (int j=1;j<=this.getColumns();j++) {
				if (Math.abs(this.getEntry(i,j))<=0.000001) {
					this.setEntry(i,j,0);
				}
			}
		}
	}

	//function to return rowEchelon form
	//KEEP IN MIND THAT THERE ARE SEVERAL ROW ECHELON FORMS FOR A SINGLE MATRIX (UNLIKE REDUCED ROW ECHELON FORM)
	public myMatrix rowEchelon(){
		//special cases
		if (this==null || this.getRows()==0 || this.getColumns()==0) {
			System.out.println("Please check your matrix and try again!");
			return null;
		}

		if (this.getRows()==1) {
			return this;
		}

		if (this.getColumns()==1) {
			myMatrix copy = this.getCopy();
			copy.moveZerosToBottom(1,1);
			return copy;
		}
		//creating copy of given matrix to perform operations on
		myMatrix copy=this.getCopy();
		//keeping track of possible pivots
		int pivots =1;
		for (int j=1;j<=copy.getColumns();j++) {
			//System.out.println(copy);
			//first move zeros to bottom
			copy.moveZerosToBottom(pivots,j);
			
			int counter=0;

			for (int i=pivots;i<=copy.getRows();i++) {
				//need to iterate through row and find number nonzero
				if (copy.getEntry(i,j)!=0 ) {
					counter++;
					//making leading entry 1

					copy.multiplyRow(1/copy.getEntry(i,j),i);

				}
			}
				//operation based on number of nonzero entries in row
				if (counter==1) {
					pivots++;
				} else if (counter>1) {
					//need to not count the pivot row in this case
					counter--;
					for (int i=pivots+1;i<=pivots+counter;i++) {
						copy.addRow(-1, pivots,i);
						//accounting for rounding error (assuming the user input valid first small entries to start)
						copy.checkZeros();



					}
					pivots++;

				}


		}
		return copy;
	}

		//there is only ONE reduced row echelon form for a matrix
		public myMatrix reducedRowEchelon() {


			//need to use the fact that the first nonzero entry in the row is a pivot
			myMatrix copy = this.rowEchelon();
			for (int i=1;i<=copy.getRows();i++) {
				int pivot=0;
				int column=0;
				for (int j=1;j<=copy.getColumns();j++) {
					if (copy.getEntry(i,j)!=0 && pivot==0) {
						//recording row of pivot
						pivot = i;
						column=j;
						break;
					}
				}
					//subtracting rows above the pivot if they have nonzero entries in that column
					if (pivot!=1 && pivot!=0) {
						for (int z =1;z<pivot;z++) {
							if (copy.getEntry(z,column)!=0) {
								//pivot entry should be equal to 1
								copy.addRow(-copy.getEntry(z,column),pivot,z);
								copy.checkZeros();

							}
						}

					}
				}


			return copy;
		}


		//method that solves the associated augemented matrix
		//parameter is the augmented part of the matrix
		//this matrix must return some string represenation
		//this is because things like free variables must be represented
	public ArrayList<String> solveSystem(myMatrix augmented) {

		if (this==null || augmented==null || this.getRows()!=augmented.getRows()) {
			System.out.println("Please check that the matrix is correct and try again.");
			return null;
		} else {

			//copying columns of variable coefficients
			myMatrix result = new myMatrix(augmented.getRows(),1);
			double [] entries = new double[augmented.getRows()];


				for (int j=1;j<=augmented.getRows();j++) {
					entries[j-1]=augmented.getEntry(j,1);
				}
			result.addEntry(entries);


			int currentpivots=0;
			ArrayList<Integer> zerorow = new ArrayList<Integer>();




			for (int i=1;i<=this.getColumns();i++) {

				for (int j=currentpivots+1;j<=this.getRows();j++) {

					if (this.getEntry(j, i)==0 && !this.restOfColumnZero(j, i)) {

						zerorow.add(j);
						for (int c=j;j<=this.getRows();c++) {
							if (this.getEntry(c, i)!=0) {
								result.multiplyRow(1/this.getEntry(c,i),c);
								result.swapRow(j,c);
								this.multiplyRow(1/this.getEntry(c, i), c);
								this.swapRow(j, c);

								break;
							}
						}

					} else if (this.getEntry(j, i)==0) {
						break;
					} else {
						result.multiplyRow(1/this.getEntry(j,i),j);
						this.multiplyRow(1/this.getEntry(j, i), j);
						if (currentpivots!=i) {
							currentpivots++;
						}
					}


				}
					//this part subtracts the pivot row from the nonzero rows
					//that are below itself
					//we do not count the end because then we will transform
					//a zero row into a nonzero one
				for (int j=currentpivots;j<=this.getRows();j++) {
					if (this.getEntry(j, i)!=0 && j!=currentpivots) {
					result.addRow(-1,currentpivots,j);
					this.addRow(-1, currentpivots, j);
					}
				}
				zerorow.clear();
			}



			//we are checking if each entry in the row is not zero
			//if so, then we divide that row by itself and move on to the next row
			for (int i=1;i<=this.getRows();i++) {
				for (int j=1;j<=this.getColumns();j++) {
					if (this.getEntry(i, j)!=0) {
						result.multiplyRow(1/this.getEntry(i,j),i);
						this.multiplyRow(1/this.getEntry(i, j), i);
						break;
					}
				}
			}



			//finally converting to row echelon

			//we do not have to check the bottom row because if we get to that row
			//without detecting any pivots, then the other rows must be zero rows
				for (int i=1;i<=this.getRows();i++) {
					//first for loop to iterate across row to find pivot
					for (int j=1;j<=this.getColumns();j++) {
						if (this.getEntry(i, j)!=0) {


							for (int z = i-1;z>=1;z--) {
								if (this.getEntry(z, j)!=0) {
									result.addRow(-this.getEntry(z,j),i,z);
									this.addRow(-this.getEntry(z, j), i, z);

								}
							}

							break;
						}
					}



				}
					//checking if row is zero and augemented entry is nonzero
					//in that case, the system is inconsistent
				for (int i=1;i<=this.getRows();i++) {
					double rowSum=0;
					for (int j=1;j<=this.getColumns();j++) {
						rowSum+=this.getEntry(i,j);
					}
					if (rowSum==0 && result.getEntry(i,1)!=0) {
						System.out.println("The system is inconsistent!");
						return null;
					}
				}

					//putting the answer vectors in an array list

					//creating possible possible variables
				ArrayList<String> variables = new ArrayList<String>();
				for (int i=1;i<=this.getColumns();i++) {
					variables.add("x"+i);
				}
					//creating the answers to add to the ArrayList
				ArrayList<String> answers = new ArrayList<String>();
				for (int i=0;i<this.getRows();i++) {
					String answer="";
					int oneCount=0;
					for (int j=0;j<this.getColumns();j++) {
						if (this.getEntry(i+1,j+1)!=0 && oneCount==0) {
							answer=""+variables.get(j)+":"+result.getEntry(i+1,1);
							oneCount++;
						} else if (this.getEntry(i+1,j+1)!=0) {
							if (this.getEntry(i+1,j+1)>0) {
								if (this.getEntry(i+1,j+1)==1) {
									answer+="-"+variables.get(j);
								} else {
								answer+="-"+this.getEntry(i+1,j+1)+variables.get(j);
								}
							} else {
								if (this.getEntry(i+1,j+1)==-1) {
									answer+="+"+variables.get(j);
								} else {
								answer+=""+(-this.getEntry(i+1,j+1))+variables.get(j);
								}
							}
						}
					}
					if (!answers.equals("")) {
					answers.add(answer);
					}
				}
				return answers;
			}
		}
	//SO FOR SMALL MATRICES MY PLU DETERMINANT IS BAD BUT IT IS GOOD FOR VERY LARGE MATRICES
	//SO I HAVE COMBINED BOTH PROGRAMS HERE
	public double getDeterminant() {
		if (this.getRows()<=6) {
			return this.getRecursiveDeterminant();
		} else {
			return this.getPLUDeterminant();
		}
	}
	//I NEEDED TO WRITE THIS METHOD BECAUSE MY RECURSIVE DEFINITION WAS TOOOOOO SLOW!!!!	
	//using the fact that upper triangular matrices have determinant that is a product of the diagonal
	//I will use my PLU method for this instead of the recursive definition
	//if A=PLU then A=det(P)det(L)det(U)
	public double getPLUDeterminant() {
		
		if (this.getRows()<=2 || this.getColumns()<=2) {
			return this.getRecursiveDeterminant();
		}
		if (this.getRows()!=this.getColumns()) {
			System.out.println("Please check that the matrix is square and try again!");
			return 1234567890;
		}
		ArrayList<myMatrix> PLU = this.PLU();
		//det of permutation matrix is (-1)^S where S is number of swaps
		double Pdet = Math.pow(-1,PLU.get(3).getEntry(1,1));
		double Ldet=1;
		double Udet=1;
		for (int i=1;i<=PLU.get(1).getRows();i++) {
					Udet=Udet*PLU.get(2).getEntry(i,i);
		}
		return (Pdet*Ldet*Udet);
	}

		//method that returns the determinant of the given matrix recursively
	public double getRecursiveDeterminant() {

		//check to see if matrix isnt square && not null
		if (this==null || this.getRows()!=this.getColumns()) {
			System.out.println("Please enter a valid matrix");
			return -1;
		} else if (this.getRows()==1) {
				//returns the single entry in the matrix
				//special case
				return this.getEntry(1,1);
		} else if (this.getRows()==2 && this.getColumns()==2) {
			//base definition of the determinant
			return (this.getEntry(1,1)*this.getEntry(2,2))-(this.getEntry(1,2)*this.getEntry(2,1));
		} else {
				double det=0;

				myMatrix toDo;
			//need a recursive definition now for bigger matrices
			for (int i=1;i<=this.getColumns();i++) {
				//if the entry is zero, the term added to the
				//determinant sum is just zero, so it doesnt matter
				if (this.getEntry(1,i)!=0) {

					toDo = new myMatrix(this.getRows()-1,this.getColumns()-1);

					double [] entries = new double[(this.getRows()-1)*(this.getColumns()-1)];

						//adding values to new matrix
					int count=0;
						for (int j=2;j<=this.getRows();j++) {
							for (int z=1;z<=this.getColumns();z++) {
								if(z!=i) {
								entries[count]=this.getEntry(j,z);
								count++;
								}
							}
						}
						toDo.addEntry(entries);

						if (i%2!=0) {
							det+=this.getEntry(1,i)*toDo.getRecursiveDeterminant();
						} else {
							det-=this.getEntry(1,i)*toDo.getRecursiveDeterminant();
					}

				}
			}
				return det;

		}
	}
		//method that returns the matrix of minors with checkerboard negative pattern
	public myMatrix getCoFactorMatrix() {

		myMatrix cofactors=new myMatrix(this.getRows(),this.getColumns());
		double[] entries = new double[this.getRows()*this.getColumns()];
		int count=0;
		//i need to find the minors of each entry
		//then i need to add it to this matrix
		//and then I need to make according entries positive and negative
		for (int i=1;i<=this.getRows();i++) {
			for (int j=1;j<=this.getColumns();j++) {
				//i need to form the minor matrix here and call my
				//getDeterminant() function on it
				myMatrix minor = new myMatrix(this.getRows()-1,this.getColumns()-1);
				double[] placer = new double[(this.getRows()-1)*(this.getColumns()-1)];
				int index=0;
				for (int c=1;c<=this.getRows();c++) {
					for (int d=1;d<=this.getColumns();d++) {
						if (c!=i && d!=j) {
							placer[index]=this.getEntry(c,d);
							index++;
						}
					}
				}
				minor.addEntry(placer);

				entries[count]=minor.getDeterminant();
				count++;

			}
		}
		cofactors.addEntry(entries);
		//now i have to add negatives in a "checkerboard" fashion
		for (int i=1;i<=cofactors.getRows();i++) {
			for (int j=1;j<=cofactors.getColumns();j++) {
				if ((i+j)%2!=0) {
					cofactors.matrix[i-1][j-1]=-cofactors.matrix[i-1][j-1];
				}
			}
		}
		return cofactors;

	}
		//method that returns the adjoint matrix-cofactor matrix with elements "reflected" over the diagonal
	public myMatrix getAdjointMatrix() {
		myMatrix adjoint = this.getCoFactorMatrix();
		//need to swap entries over the diagonal to form adjoint
		for (int i=1;i<=this.getRows();i++) {
			for (int j=1;j<=this.getColumns();j++) {
				if (j>i) {
					double temp =adjoint.matrix[i-1][j-1];
					adjoint.matrix[i-1][j-1]=adjoint.matrix[j-1][i-1];
					adjoint.matrix[j-1][i-1]=temp;
				}
			}
		}
		return adjoint;
	}


	public myMatrix getInverse() {
		//if determinant is 0, the inverse is not defined
		//otherwise, it is the adjoint matrix scaled by 1/determinant
		if (this.getDeterminant()==0 || this.getColumns()!=this.getRows()) {
			System.out.println("The given matrix does not have an inverse!");
			return null;
		} else {
				myMatrix inverse = this.getAdjointMatrix();
				//scaling adjoint by 1/determinant
				inverse.scaleMatrix(1.0/this.getDeterminant());

				return inverse;
		}

	}


		//returns the projection of this vector onto the other vector
	public myMatrix getProjection(myMatrix otherVector) {
		//assuming the user actually inputs vectors
		//need to transform given arguments for matrix multiplication
		//I am using the fact that a dot product between vectors is just matrix multiplication of a row vector and column vector
		myMatrix firstVector;
		myMatrix secondVector;
		//TRANSFORMING FIRST VECTOR INTO A ROW VECTOR
		if (this.getRows()>1) {

			firstVector = this.getTranspose();
			} else {
				//making a copy of "this" so that we dont alter "this"
			firstVector = this.getCopy();
		}

		//TRANSFORMING SECOND VECTOR INTO A COLUMN VECTOR

		if (otherVector.getColumns()>1) {
			secondVector=otherVector.getTranspose();
		} else {
			//making a copy of otherVector
			secondVector=otherVector.getCopy();
		}


		if (firstVector.getColumns()!=secondVector.getRows()) {
			System.out.println("Please enter valid vectors!");
			return null;
		} else {
			//general formula is ((v dot s)/(s dot s))*s (scalar times a vector)
			//s dot s is just every entry multiplied by itself and added together
			//in our case s is already a column vector
			double doubleDot=0;
				for (int i=1;i<=secondVector.getRows();i++) {
					double toAdd=secondVector.getEntry(i,1);
					doubleDot+=(toAdd*toAdd);
				}
			//my multiplication function will result in 1x1 matrix, so i need to "extract" that entry
			myMatrix topPart = firstVector.multiplyMatrix(secondVector);
			double top = topPart.getEntry(1,1);
			//need to scale the column vector according to the equation
			secondVector.scaleMatrix(top/doubleDot);
			return secondVector;
		}
	}

	//method that normalizes the columns of a matrix (or vectors)
	public void normalize() {
		//need to iterate through column, then divide each entry by the square root of the sum of each entry squared (definition of norm)
		for (int j=1;j<=this.getColumns();j++) {
			double colSum=0;
			for (int i=1;i<=this.getRows();i++) {
				colSum+=Math.pow(this.getEntry(i,j),2);
			}
			//now colSum is the norm of the vector squared so we need to take the square root to get norm
			colSum=Math.pow(colSum,0.5);
			//now we are dividing each entry
			for (int i=1;i<=this.getRows();i++) {
				if (Math.abs(colSum)>1e-6) {
				this.setEntry(i,j,this.getEntry(i,j)/colSum);
				}
			}
		}
	}
	//method returns an orthogonal basis based on gram schmidt algorithm of the columns of the matrix
	public myMatrix gramSchmidt() {
		//user enters matrix where the columns are vectors
		//<u1,u2,u3,...> is the entered matrix
		//we first say v1=u1
		//then v2=u2-u2projv1
		//v3= u3-u3projv2-u3projv1 and so on
		if (this.getDeterminant()==0) {
			System.out.println("The determinant is zero! You need a matrix with linearly independent columns!");
			return null;
		}
		myMatrix result;
		ArrayList<myMatrix> orthogonal = new ArrayList<myMatrix>();
		for (int i=1;i<=this.getColumns();i++) {
			result = new myMatrix(this.getRows(),1);
			result.addEntry(this.getColumn(i));
				if (i==1) {
					//making a copy of vector

					orthogonal.add(result);


				} else {
						//need to access every vector in orthogonal so far
						myMatrix vector = result;
					for (int j=0;j<orthogonal.size();j++) {
						vector=vector.subtractMatrix(result.getProjection(orthogonal.get(j)));

					}

					for (int j=0;j<orthogonal.size();j++) {
						System.out.println(vector.getProjection(orthogonal.get(j)));

					}


					orthogonal.add(vector);


				}
		}
		myMatrix realResult = new myMatrix(this.getRows(),this.getColumns());
		//adding vectors as columns in the new matrix
		double [] realEntries = new double[this.getRows()*this.getColumns()];
		int count=0;
			for (int i=1;i<=this.getRows();i++) {
				for (int j=0;j<orthogonal.size();j++) {
					realEntries [count]=orthogonal.get(j).getEntry(i,1);
					count++;
				}
			}
		realResult.addEntry(realEntries);

		return realResult;
	} 
	//orthogonal basis formation using modified gram schmidt algorithm
	//ONLY WORKS FOR LINEARLY INDEPENDENT COLUMNS (det!=0)
/*	public myMatrix gramSchmidt() {

		ArrayList<myMatrix> ortho = new ArrayList<myMatrix>();
		for (int i=1;i<=this.getColumns();i++) {
			//creating first column vector
			myMatrix vector = new myMatrix(this.getRows(),1);
			double[] entries = new double[this.getRows()];
			for (int j=1;j<=this.getRows();j++) {
				entries[j-1]=this.getEntry(j,i);
			}
			vector.addEntry(entries);

			if (i==1) {
				ortho.add(vector);
			} else {
				//using gram schmidt formula
				myMatrix toAdd = vector;

				toAdd=toAdd.subtractMatrix(vector.getProjection(ortho.get(ortho.size()-1)));

				for (int z =0;z<ortho.size();z++) {
					System.out.println(toAdd.getProjection(ortho.get(z)));
				}
				ortho.add(toAdd);
			}
		}
			//adding column vectors in new matrix
			myMatrix orthogonal = new myMatrix(this.getRows(),this.getColumns());
			double[][] columns = new double[this.getColumns()][this.getRows()];
			for (int i=0;i<ortho.size();i++) {
				columns[i]=ortho.get(i).getColumn(1);
			}
			orthogonal.fillColumns(columns);
			return orthogonal;
	} */


		//this method returns an array list with QR decomposition in that order as elements of the list
		//my method only works on square matrices
	public ArrayList<myMatrix> QR() {
		if (this.getColumns()!=this.getRows() || this==null) {
			System.out.println("Please check that the matrix is square and try again!");
			return null;
		}
		myMatrix Q = this.gramSchmidt();
		Q.normalize();
		//making the upper triangular matrix
		myMatrix R = new myMatrix(this.getRows(),this.getColumns());
		double[] entries = new double[this.getRows()*this.getColumns()];
		int count=0;
		for (int i=1;i<=this.getRows();i++) {
			double []	normalColumn = Q.getColumn(i);
			for (int j=1;j<=this.getColumns();j++) {
				if (i<=j) {
					double[] thisColumn = this.getColumn(j);
					//dot product
					double dot =0;
					for (int z=0;z<normalColumn.length;z++) {
						dot+=(normalColumn[z])*(thisColumn[z]);
					}
					entries[count]=dot;
					count++;
				} else {
					entries[count]=0;
					count++;
				}
			}
		}
		R.addEntry(entries);
		ArrayList<myMatrix> QR = new ArrayList<myMatrix>();
		QR.add(Q);
		QR.add(R);
		return QR;
	}
		//helper function for checking if matrix is upper triangular (zeros below diagonal)
	public boolean isUpperTriangular() {
		myMatrix copy=this.getCopy();
		copy.checkZeros();
		for (int i=1;i<=this.getRows();i++) {
			for (int j=1;j<=this.getColumns();j++) {
				if (i>j) {
					if (copy.getEntry(i,j)!=0) {
						return false;
					}
				}
			}
		}
		return true;
	}


	public boolean isBlockDiagonal() {
		myMatrix copy = this.getCopy();
		//zeroing out matrix for checking diagonal
		copy.checkZeros();
		for (int j=1;j<=copy.getColumns()-1;j++) {
			for (int i=1;i<=copy.getRows()-1;i++) {
				if (i==j && copy.restOfColumnZero(i+1,j)) {
					break;
				} else if (i==j && copy.getEntry(i+1,j)!=0) {
					if (i+1!=copy.getRows()) {
						if (copy.restOfColumnZero(i+2,j) && copy.restOfColumnZero(i+2,j+1)) {
							j++;
							break;
						} else {
							return false;
						}
					}
				}
			}
		
		}
		return true;
	}
		//checks if matrix is in blockdiagonal form (blocks or 1x1 entries along the diagonal)
		//this will help us extract complex eigenvalues from QR factorization
		//only valid for square matrices
/*	public boolean isBlockDiagonal() {
		myMatrix copy = this.getCopy();
		copy.checkZeros();
		for (int i=1;i<=copy.getRows();i++) {
			for (int j=1;j<=copy.getColumns();j++) {
				if (i==j) {
					if (i!=copy.getRows()) {
						//if its not the last entry in the diagonal
						if (copy.getEntry(i+1,j)!=0) {
							//checking for 2x2 block matrix
							if (i+1!=copy.getRows()) {
								if (! copy.restOfColumnZero(i+2,j)) {
									return false;
								}
							}
						} else {
							//checking for rest of column to be zero
							if (!copy.restOfColumnZero(i+1,j)) {
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}*/

	//method that returns the eigenvalues of the given matrix as a string array
	public ArrayList<String> eigenValues() {

	//uses QR algorithm in order to numerically retrieve eigenValues
	//keeps iterating A = RQ and then finding the QR factorization of That
	if (this.getRows()!=this.getColumns() || this==null) {
		System.out.println("Either the matrix is empty, or it is not square!");
		return null;
	}
	//copying the matrix that the function is called on
	myMatrix copy = this.hessenberg();
	//now copy is a duplicate object of THIS

	//Supports Complex and Real eigenvalues for inputted matrices that are REAL
	ArrayList<String> eigenvalues = new ArrayList<String>();
	//logic for 1x1 matrix: the eigenvalue is just the single entry
		if (copy.getRows()==1 && copy.getColumns()==1) {
			eigenvalues.add(Double.toString(copy.getEntry(1,1)));
			return eigenvalues;
		}
	//logic for 2x2 matrix: need to use quadratic formula
	if (copy.getRows()==2 && copy.getColumns()==2) {
		double trace = -(copy.getEntry(1,1)+copy.getEntry(2,2));
		double det = copy.getDeterminant();
		//need to see if discriminant is 0, <0, or >0
		if (Math.pow(trace,2)-4*det>0) {
			//we have two distinct real roots
			double eigen_one= (-trace+Math.pow(Math.pow(trace,2)-4*det,0.5))/(2);
			double eigen_two=(-trace-Math.pow(Math.pow(trace,2)-4*det,0.5))/(2);
			eigenvalues.add(Double.toString(eigen_one));
			eigenvalues.add(Double.toString(eigen_two));
			return eigenvalues;
		} else if (Math.pow(trace,2)-4*det<0) {
			//we have two complex roots
			//i need a string for i
			ArrayList<String> complex = new ArrayList<String>();
			String eigen_one=""+(-trace/2)+"+"+(Math.pow(4*det-Math.pow(trace,2),0.5)/2)+"i";
			String eigen_two=""+(-trace/2)+"-"+(Math.pow(4*det-Math.pow(trace,2),0.5)/2)+"i";
			complex.add(eigen_one);
			complex.add(eigen_two);
			return complex;

		} else {
			//we have a double root
			double eigen = -trace/2;
			eigenvalues.add(Double.toString(eigen));
			eigenvalues.add(Double.toString(eigen));
			return eigenvalues;
		}
	}


	int itcount=0;

		while (!copy.isUpperTriangular() && !copy.isBlockDiagonal()) {
			//getting the QR factorization

			ArrayList<myMatrix> factorization= copy.QR();
			copy = factorization.get(1).multiplyMatrix(factorization.get(0));
			itcount++;
				copy.checkZeros();
//				if (itcount==500) {
//					System.out.println("Too many iterations!");
//					break;
//				}

		}
		System.out.println(itcount);
		ArrayList<String> eigs = new ArrayList<String>();
		for (int i=1;i<=copy.getRows();i++) {
			for (int j=1;j<=copy.getColumns();j++) {
				if (i==j) {
					if (i!=copy.getRows()) {
						if (copy.getEntry(i+1,j)!=0) {
							//we have a block
							myMatrix block = new myMatrix(2);
							double[] complex = new double[4];
							complex[0]=copy.getEntry(i,j);
							complex[1]=copy.getEntry(i,j+1);
							complex[2]=copy.getEntry(i+1,j);
							complex[3]=copy.getEntry(i+1,j+1);
							block.addEntry(complex);
							//recursive definition for 2x2 blocks for which we know the eigenvalues!
							ArrayList<String> temp=block.eigenValues();
							for (String value: temp) {
								eigs.add(value);
							}
							i++;
							j++;
							//after the for loop increments, we will be at the second furthest diagonal position
						} else {
							//we have a 1x1 matrix
							eigs.add(Double.toString(copy.getEntry(i,j)));
						}
					} else {
						//if the last entry in the diagonal is not part of a block, then we add the value
						if (copy.getEntry(i,j-1)==0) {
							eigs.add(Double.toString(copy.getEntry(i,j)));
						}
					}
				}
			}
		}
		return eigs;



	}



	//method to create identity matrix of a specific size
	
	public static myMatrix identity(int size) {
		myMatrix identity= new myMatrix(size);
		for (int i=1;i<=identity.getRows();i++) {
			for (int j=1;j<=identity.getColumns();j++) {
				if (i==j) {
					identity.setEntry(i,j,1);
				} else {
					identity.setEntry(i,j,0);
				}
			}
		}
		return identity;
	}



	//method to return the norm of a vector
	public double norm() {
		if (this.getColumns()!=1 && this.getRows()!=1) {
			System.out.println("The given input is not a vector!");
			return 1234567;
		} else {
			double norm=0;
			for (int j=1;j<=this.getColumns();j++) {
				for (int i=1;i<=this.getRows();i++){
					norm+=Math.pow(this.getEntry(i,j),2);
				}
			}
			norm=Math.pow(norm,0.5);
			return norm;
		}
	}
	
	//helper method for houseHolderQR()
	public boolean isZero() {
		myMatrix copy = this.getCopy();
		copy.checkZeros();
		for (int i=1;i<=copy.getRows();i++) {
			for (int j=1;j<=copy.getColumns();j++) {
				if (copy.getEntry(i,j)!=0){
					return false;
				}
			}
		}
		return true;
	}
		//method to determine an orthogonal basis via Householder Reflections
		//this is much more numerically stable than gram schmidt because we use decimal approximations and not exact values	
		//returns an ArrayList with QR factorization of a matrix
		//NOTE I AM ALLOWING USERS TO ENTER LINEARLY DEPENDENT MATRICES THAT SATISFY M=N FOR THE PURPOSE OF THE EIGENVALUE QR ALGORITHM
	public ArrayList<myMatrix> houseHolderQR() {
		//if user enters matrix with linearly dependent columns and N>M, we cannot proceed! 
		if (this.getColumns()>this.getRows()) {
			System.out.println("You have more columns than rows! Please adjust the matrix and try again!");
			return null;
		}
		//if user enters one column matrix, then that matrix is an orthogonal basis by itself
		if (this.getColumns()==1) {
			System.out.println("The current vector is vacuously an orthogonal basis!");
			return null;
		}	
		//if user enters an Upper Triangular Matrix, then the QR decomposition is trivial
		if (this.isUpperTriangular()) {
			//need to return identity here!
			myMatrix Q = identity(this.getRows());
			ArrayList<myMatrix> QR = new ArrayList<myMatrix>();
			QR.add(Q);
			QR.add(this.getCopy());
			return QR;
		}
				//COMPLETE LOGIC 
		//making copy of THIS
		myMatrix toConsider = this.getCopy();
		//setting iterative Q to keep multiplying my Householders To 
		myMatrix Q = identity(toConsider.getRows());
		//need to stop iteration when toConsider is not Upper Triangular
			
			for (int j=1;j<=toConsider.getColumns()-1;j++) {
				//my z vector starts as the first column of toCompare
				myMatrix z=new myMatrix(toConsider.getRows()-(j-1),1);	
				//z' is a unit vector of same size with first entry being 1
				//IF THE ENTRIES BELOW DIAGONAL ARE ALREADY ZERO THEN CONTINUE;
				if (toConsider.restOfColumnZero(j+1,j)) {
					continue;
				}
				int count=1;
				myMatrix zPrime = new myMatrix(toConsider.getRows()-(j-1),1);
				for (int i=j;i<=toConsider.getRows();i++) {
					z.setEntry(count,1,toConsider.getEntry(i,j));
					if (i==j) {
						zPrime.setEntry(count,1,1);
					} else {
						zPrime.setEntry(count,1,0);
					}
					count++;
				}
				//now i scale zPrime by norm of z
				zPrime.scaleMatrix(z.norm());
				//now i create u vector 
				myMatrix u = z.subtractMatrix(zPrime);
				//now P=I-2(uu^T)/(u^Tu)
				//getting dot product:
				double dot = 0;
				for (int i=1;i<=u.getRows();i++) {
					dot+=Math.pow(u.getEntry(i,1),2);
				}
				//getting the subtraction matrix
				myMatrix toSubtract = u.multiplyMatrix(u.getTranspose());
				//scaling the subtraction matrix by 2/dotproduct
				toSubtract.scaleMatrix(2/dot);
				//making identity matrix the same size as subtraction matrix
				myMatrix identity = identity(toSubtract.getRows());
				//getting the householder reflection matrix
				myMatrix P = identity.subtractMatrix(toSubtract);
				//making P same size as toConsider if not already same size
				myMatrix toChange=identity(toConsider.getRows());
				if (P.getColumns()!=toConsider.getRows()) {
					for (int i=1;i<=toChange.getRows();i++) {
						for (int d=1;d<=toChange.getColumns();d++){
							if (i>(toChange.getRows()-P.getRows()) && d>(toChange.getColumns()-P.getColumns())){
						toChange.setEntry(i,d,P.getEntry(i-(toChange.getRows()-P.getRows()),d-(toChange.getColumns()-P.getColumns())));
							}
						}
					}

					toConsider=toChange.multiplyMatrix(toConsider);
					Q=toChange.multiplyMatrix(Q);
				} else {
					toConsider=P.multiplyMatrix(toConsider);
					Q=P.multiplyMatrix(Q);
				}
				if (toConsider.isUpperTriangular()) {
					break;
				}	
			}
		ArrayList<myMatrix> QR = new ArrayList<myMatrix>();
		QR.add(Q.getTranspose());
		QR.add(toConsider);
		return QR;
		
	
	

	}

	//helper function that checks if two matrices have the same contents
	//This is just for LU decomposition and integer or well defined matrices because this method cannot take into account floating point decimal errors
	public boolean isEqual(myMatrix other) {
		if (this.getRows()!=other.getRows()||this.getColumns()!=other.getColumns()||this==null||other==null) {
			System.out.println("Either a matrix is empty or their dimensions do not match!");
			return false;
		} else {
			for (int i=1;i<=this.getRows();i++) {
				for (int j=1;j<=this.getColumns();j++) {
					if (this.getEntry(i,j)!=other.getEntry(i,j)){
						return false;
					}
				}
			}
			return true;
		}
	}

		//helper function to determine if matrix is lower triangular or not
	public boolean isLowerTriangular() {
		for (int i=1;i<=this.getRows();i++) {
			for (int j=1;j<=this.getColumns();j++) {
				if (i<j && this.getEntry(i,j)!=0) {
					return false;
				}
			}
		}
		return true;
	}
	//method to return PLU factorization of a matrix
	//If no row changes are needed, then PLU is equivalent to LU where P is the identity matrix
	//If no row changes are needed, then P is just an identity matrix
	//Otherwise, P encodes the row changes that are incurred by converting L into lower triangular form
	//If THIS is rectangular rather than square, then U is in row echelon form instead of upper triangular form
	//STORES NUMBER OF ROW CHANGES IN index 3 FOR PURPOSES OF DETERMINANT CALCULATION (swaps) (this is more efficient than recursion for very large matrices)
	public ArrayList<myMatrix> PLU() {
		//Copy pasted my RowEchelon Code
		//special cases
		if (this==null || this.getRows()==0 || this.getColumns()==0) {
			System.out.println("Please check your matrix and try again!");
			return null;
		}

		if (this.getRows()==1) {
			System.out.println("This is a row vector!");
			return null;
		}

		if (this.getColumns()==1) {
			System.out.println("This is a column vector!");
			return null;
		}
		//creating copy of given matrix to perform operations on
		myMatrix copy=this.getCopy();
		myMatrix PL = identity(this.getRows());
		copy.checkZeros();
	//	myMatrix P = identity(this.getRows());
	//	myMatrix L =identity(this.getRows());
		//keeping track of number of row swaps, will be important for determinant calculation
		int swaps=0;
		//keeping track of possible pivots
		int pivots =1;
		for (int j=1;j<=copy.getColumns();j++) {
			//first move zeros to bottom
			//NEED TO COPY MY WHOLE MOVE ZEROS TO BOTTOM FOR THE PURPOSE OF LU DECOMPOSITION
	//		copy.moveZerosToBottom(j);
	//		IF WE DO NEED TO CHANGE ROWS, THEN I NEED TO CREATE AN ELEMENTARY MATRIX TO STORE THE OPERATION 
					int breakrow=-123;
		for (int i=pivots;i<=copy.getRows();i++) {
			if (copy.getEntry(i,j)==0) {
				if (i==breakrow) {
					break;
				}
				for (int z=copy.getRows();z>i;z--) {
					if (copy.getEntry(z,j)!=0) {
						//swap zero entryrow with nonzero entry row closest to the bottom
						copy.swapRow(i,z);
						swaps++;
				//		P.swapRow(i,z);
						myMatrix elementary = identity(this.getRows());
						elementary.swapRow(i,z);
						//LOOK AT THE COMMENT BELOW WITH SIMILAR LINE
						PL=PL.multiplyMatrix(elementary);
						breakrow=z;
						break;
					}
				}
			}
		}

			int counter=0;

			for (int i=pivots;i<=copy.getRows();i++) {
				//need to iterate through row and find number nonzero
				if (copy.getEntry(i,j)!=0 ) {
					counter++;
					//making leading entry 1
					//need to scrap the below line for the purposes of LU decomposition
//					copy.multiplyRow(1/copy.getEntry(i,j),i);

				}
			}
				//operation based on number of nonzero entries in row
				if (counter==1) {
					pivots++;
				} else if (counter>1) {
					//need to not count the pivot row in this case
					counter--;
					for (int i=pivots+1;i<=pivots+counter;i++) {
						//JUST LIKE ROW INTERCHANGES I NEED TO RECORD ANY OPERATION IN AN ELEMENTARY MATRIX HERE
						myMatrix elem = identity(this.getRows());
						//removed negative to get inverse straight away
						elem.addRow(copy.getEntry(i,j)/copy.getEntry(pivots,j), pivots,i);
					//	L.setEntry(i,pivots,copy.getEntry(i,j)/copy.getEntry(pivots,j));
						copy.addRow(-copy.getEntry(i,j)/copy.getEntry(pivots,j), pivots,i);
						//accounting for rounding error (assuming the user input valid first small entries to start)
						//IF WE HAVE E_nE_(n-1)...E(1)A=U where EA=U then E^(-1)=PL where E^(-1)U=A
						//SO this step is just keeping a running record of E^(-1) or PL
						PL=PL.multiplyMatrix(elem);
						copy.checkZeros();



					}
					pivots++;

				}


		}
		//NEED TO CREATE P MATRIX HERE (P is changed only if row interchanges were needed. Otherwise, its just an identity matrix!) 
		
		myMatrix P =identity(this.getRows());
		if (!PL.isLowerTriangular()) {
			//we need to swap rows so that PL is lower triangular and then swap rows in P to reflect that
			for (int i=1;i<=PL.getRows();i++) {
				for (int j=1;j<=PL.getColumns();j++) {
					if (j>i && PL.getEntry(i,j)!=0) {
						for (int z=i;z<=PL.getRows();z++) {
							if (PL.getEntry(z,j)==0) {
								PL.swapRow(i,z);
								P.swapRow(i,z);
								break;
							}
						}
					}
				}
			}
		}
		
		ArrayList<myMatrix> PLU = new ArrayList<myMatrix>();
		//THE INVERSE OF A PERMUTATION MATRIX IS ITS TRANSPOSE
		PLU.add(P.getTranspose());
//		System.out.println(P.getTranspose());
//		System.out.println(PL);
//		System.out.println(copy);
//		PLU.add(P);
//		PLU.add(L);
		PLU.add(PL);
		PLU.add(copy);
		//adding 1x1 matrix that stores the number of swaps 
		myMatrix swap = new myMatrix(1);
		swap.setEntry(1,1,swaps);
		PLU.add(swap);
		return PLU;

	}


	//method to use householder transformation to transform the given matrix into a similar hessenberg matrix
	//basically kind of copy paste of my householder method
	public myMatrix hessenberg() {
	//LOGIC IS TO PERFORM HOUSEHOLDER REFLECTIONS ON THE COLUMNS BELOW THE SUBDIAGONAL AND STOP AT N-2 TO GET HESSENBERG FORM
		if (this.getRows()!=this.getColumns()) {
			System.out.println("Please check that the matrix is square and try again!");
			return null;
		}
	//H=Q^TAQ for every Q(householder reflection) obtained (SO I SHOULD KEEP MULTIPLYING IN MY CODE)
	//making copy of THIS
		myMatrix toConsider = this.getCopy();
		//setting iterative Q to keep multiplying my Householders To 
		myMatrix Q = identity(toConsider.getRows());
		//need to stop iteration when toConsider is not Upper Triangular
			//need to stop at n-2 for hessenberg form	
			for (int j=1;j<=toConsider.getColumns()-2;j++) {
				if (toConsider.restOfColumnZero(j+2,j)) {
					continue;
				}
				//my z vector starts as the first column of toCompare
				myMatrix z=new myMatrix(toConsider.getRows()-(j),1);	
				//z' is a unit vector of same size with first entry being 1
				int count=1;
				myMatrix zPrime = new myMatrix(toConsider.getRows()-(j),1);
				//need to consider the row under the diagonal
				for (int i=j+1;i<=toConsider.getRows();i++) {
					z.setEntry(count,1,toConsider.getEntry(i,j));
					if (i==j+1) {
						zPrime.setEntry(count,1,1);
					} else {
						zPrime.setEntry(count,1,0);
					}
					count++;
				}
				if (z.isZero()) {
					continue;
				}
				//now i scale zPrime by norm of z
				zPrime.scaleMatrix(z.norm());
				//now i create u vector 
				myMatrix u = z.subtractMatrix(zPrime);
				//now P=I-2(uu^T)/(u^Tu)
				//getting dot product:
				double dot = 0;
				for (int i=1;i<=u.getRows();i++) {
					dot+=Math.pow(u.getEntry(i,1),2);
				}
				//getting the subtraction matrix
				myMatrix toSubtract = u.multiplyMatrix(u.getTranspose());
				//scaling the subtraction matrix by 2/dotproduct
				toSubtract.scaleMatrix(2/dot);
				//making identity matrix the same size as subtraction matrix
				myMatrix identity = identity(toSubtract.getRows());
				//getting the householder reflection matrix
				myMatrix P = identity.subtractMatrix(toSubtract);
				//making P same size as toConsider if not already same size
				myMatrix toChange=identity(toConsider.getRows());
				for (int i=1;i<=toChange.getRows();i++) {
					for (int d=1;d<=toChange.getColumns();d++){
						if (i>(toChange.getRows()-P.getRows()) && d>(toChange.getColumns()-P.getColumns())){
							toChange.setEntry(i,d,P.getEntry(i-(toChange.getRows()-P.getRows()),d-(toChange.getColumns()-P.getColumns())));
						}
					}
				}

					toConsider=toChange.getTranspose().multiplyMatrix(toConsider.multiplyMatrix(toChange));
			}  
			

		return toConsider; 
	}



	//basically just copy the eigenValues method but converting to Hessenberg form first
	//DO ANOTHER EIGENVALUE METHOD BUT THIS TIME WITH SHIFTS!!!!!
	//THIS METHOD PRINTS THE EIGENVALUES AND EIGENVECTORS OF THE GIVEN SQUARE MATRIX BUT ALSO
	//RETURNS THEM IN A STRING ARRAYLIST IN CASE THE USER DESIRES TO RECORD THEM  (I NEED STRING FOR COMPLEX)
public ArrayList<String> betterEigenValues() {
	if (this.getRows()!=this.getColumns() || this==null) {
		System.out.println("Either the matrix is empty, or it is not square!");
		return null;
	}
	//getting the hessenberg form of THIS to start
	myMatrix copy = this.hessenberg();

	//Supports Complex and Real eigenvalues for inputted matrices that are REAL
	ArrayList<String> eigenvalues = new ArrayList<String>();
	//logic for 1x1 matrix: the eigenvalue is just the single entry
		if (copy.getRows()==1 && copy.getColumns()==1) {
			eigenvalues.add(Double.toString(copy.getEntry(1,1)));
			return eigenvalues;
		}
	//logic for 2x2 matrix: need to use quadratic formula
	if (copy.getRows()==2 && copy.getColumns()==2) {
		double trace = -(copy.getEntry(1,1)+copy.getEntry(2,2));
		double det = copy.getDeterminant();
		//need to see if discriminant is 0, <0, or >0
		if (Math.pow(trace,2)-4*det>0) {
			//we have two distinct real roots
			double eigen_one= (-trace+Math.pow(Math.pow(trace,2)-4*det,0.5))/(2);
			double eigen_two=(-trace-Math.pow(Math.pow(trace,2)-4*det,0.5))/(2);
			eigenvalues.add(Double.toString(eigen_one));
			eigenvalues.add(Double.toString(eigen_two));
			return eigenvalues;
		} else if (Math.pow(trace,2)-4*det<0) {
			//we have two complex roots
			//i need a string for i
			ArrayList<String> complex = new ArrayList<String>();
			String eigen_one=""+(-trace/2)+"+"+(Math.pow(4*det-Math.pow(trace,2),0.5)/2)+"i";
			String eigen_two=""+(-trace/2)+"-"+(Math.pow(4*det-Math.pow(trace,2),0.5)/2)+"i";
			complex.add(eigen_one);
			complex.add(eigen_two);
			return complex;

		} else {
			//we have a double root
			double eigen = -trace/2;
			eigenvalues.add(Double.toString(eigen));
			eigenvalues.add(Double.toString(eigen));
			return eigenvalues;
		}
	}


	int itcount=0;
	ArrayList<myMatrix> factorization=null;
	myMatrix Q =identity(copy.getRows());
		while (!copy.isUpperTriangular() && !copy.isBlockDiagonal()) {
			//getting the QR factorization
			//NEED TO COMPUTE WILKINSON SHIFT
			//USE MY WILKINSON SHIFT METHOD TO FIND
			//something here giving me NOT A NUMBER
			//I think that it is houseHolderQR
		 	factorization= copy.houseHolderQR();
			//iteration for reducing A to schur form
			copy = factorization.get(1).multiplyMatrix(factorization.get(0));
			//this is for getting eigenvalues
			Q=Q.multiplyMatrix(factorization.get(0));
			itcount++;
			copy.checkZeros();
	//			if (itcount==500) {
	//				System.out.println("Too many iterations!");
	//				break;
	//			}

		}
		System.out.println(itcount);
		ArrayList<String> eigs = new ArrayList<String>();
		eigs.add("Eigenvalues:");
		for (int i=1;i<=copy.getRows();i++) {
			for (int j=1;j<=copy.getColumns();j++) {
				if (i==j) {
					if (i!=copy.getRows()) {
						if (copy.getEntry(i+1,j)!=0) {
							//we have a block
							myMatrix block = new myMatrix(2);
							double[] complex = new double[4];
							complex[0]=copy.getEntry(i,j);
							complex[1]=copy.getEntry(i,j+1);
							complex[2]=copy.getEntry(i+1,j);
							complex[3]=copy.getEntry(i+1,j+1);
							block.addEntry(complex);
							//recursive definition for 2x2 blocks for which we know the eigenvalues!
							ArrayList<String> temp=block.eigenValues();
							for (String value: temp) {
								eigs.add(value);
							}
							i++;
							j++;
							//after the for loop increments, we will be at the second furthest diagonal position
						} else {
							//we have a 1x1 matrix
							eigs.add(Double.toString(copy.getEntry(i,j)));
						}
					} else {
						//if the last entry in the diagonal is not part of a block, then we add the value
						if (copy.getEntry(i,j-1)==0) {
							eigs.add(Double.toString(copy.getEntry(i,j)));
						}
					}
				}
			}
		}
		eigs.add("Eigenvectors:");
		//prints the eigenvalues so far in nice format
		for (String eigenvalue:eigs) {
			System.out.println(eigenvalue);
		}

		//storing eigenvectors in eigs in case user needs it 
		for (int i=1;i<=Q.getRows();i++) {
			String eigenvector="";
			for (int j=1;j<=Q.getColumns();j++){
				if (j!=Q.getColumns()){
					eigenvector+=""+Q.getEntry(i,j)+",";
				} else {
					eigenvector+=""+Q.getEntry(i,j);
				}
				

			} 
			eigs.add(eigenvector);
		}
		//need to add string representation of eigenvectors to eigs
		//printing eigenvectors from Q matrix 
		Q.print(4);
		return eigs;

	}



	//HELPER METHODS FOR DEFLATION:
	//helper method encases square matrix in an identity matrix of selected size
	public myMatrix makeIdentity(int n) {
		if (n<this.getRows()) {
			System.out.println("Please check that the matrix is square and check the desired identity size and try again!");
			return null;
		} else if (n==this.getRows()) {
			return this.getCopy();
		} else {
			myMatrix encased = new myMatrix(n);
			int missing = n-this.getRows();
			for (int i=1;i<=n;i++) {
				for (int j=1;j<=n;j++) {
					if (i>missing && j>missing) {
						encased.setEntry(i,j,this.getEntry(i-missing,j-missing));
					} else if (i==j) {
						encased.setEntry(i,j,1);
					} else {
						encased.setEntry(i,j,0);
					}
				}
			}	
			return encased;
		
		}
}

	//reduces size of matrix by specified row and column, basically opposite of makeIdentity
	public myMatrix deflate(int n) {
		myMatrix deflated = new myMatrix(this.getRows()-n);
		for (int i=1;i<=this.getRows()-n;i++) {
			for (int j=1;j<=this.getRows()-n;j++) {
				deflated.setEntry(i,j,this.getEntry(i,j));
			}
		}
		return deflated;
	}


	//helper method for calculating wilkinson shift of a square matrix
	public double wilkinson() {
		double sigma=(0.5)*(this.getEntry(this.getRows()-1,this.getRows()-1)-this.getEntry(this.getRows(),this.getRows()));
		double sign=0;
		if (sigma>=0) {
			sign=1;
		} else {
			sign=-1;
		}
		double shift = this.getEntry(this.getRows(),this.getRows())-((sign*Math.pow(this.getEntry(this.getRows(),this.getRows()-1),2))/(Math.abs(sigma)+Math.pow(Math.pow(sigma,2)+Math.pow(this.getEntry(this.getRows()-1,this.getRows()-1),2),0.5)));
		return shift;
	}
	//MAYBE TRY WILKINSON SHIFT?
	//METHOD FOR RETURNING EIGENVECTORS AND EIGENVALUES BUT WITH DEFLATION AND rayleigh shift for faster convergence 
	//rayleigh shift is just u=A(n,n) (last entry on the diagonal)
	//SHOULD SEE NOTABLE SPEED IN CONVERGENCE TO SHUR FORM
	//LOGIC: IF n,n-1 entry is 0, then the n,n entry is eigenvalue ----> deflate
	//IF n-1,n-2 entry is 0, then the trailing 2x2 matrix has the eigenvalues needed----->deflate(2)
	public ArrayList<String> deflateEigenValues() {
	if (this.getRows()!=this.getColumns() || this==null) {
		System.out.println("Either the matrix is empty, or it is not square!");
		return null;
	}
		//Supports Complex and Real eigenvalues for inputted matrices that are REAL
	ArrayList<String> eigenvalues = new ArrayList<String>();
	eigenvalues.add("Eigenvalues:");
	myMatrix copy=this.getCopy();
	//logic for 1x1 matrix: the eigenvalue is just the single entry
		if (copy.getRows()==1 && copy.getColumns()==1) {
			eigenvalues.add(Double.toString(copy.getEntry(1,1)));
			return eigenvalues;
		}
	//logic for 2x2 matrix: need to use quadratic formula
	if (copy.getRows()==2 && copy.getColumns()==2) {
		double trace = -(copy.getEntry(1,1)+copy.getEntry(2,2));
		double det = copy.getDeterminant();
		//need to see if discriminant is 0, <0, or >0
		if (Math.pow(trace,2)-4*det>0) {
			//we have two distinct real roots
			double eigen_one= (-trace+Math.pow(Math.pow(trace,2)-4*det,0.5))/(2);
			double eigen_two=(-trace-Math.pow(Math.pow(trace,2)-4*det,0.5))/(2);
			eigenvalues.add(Double.toString(eigen_one));
			eigenvalues.add(Double.toString(eigen_two));
			return eigenvalues;
		} else if (Math.pow(trace,2)-4*det<0) {
			//we have two complex roots
			//i need a string for i
			String eigen_one=""+(-trace/2)+"+"+(Math.pow(4*det-Math.pow(trace,2),0.5)/2)+"i";
			String eigen_two=""+(-trace/2)+"-"+(Math.pow(4*det-Math.pow(trace,2),0.5)/2)+"i";
			eigenvalues.add(eigen_one);
			eigenvalues.add(eigen_two);
			return eigenvalues;

		} else {
			//we have a double root
			double eigen = -trace/2;
			eigenvalues.add(Double.toString(eigen));
			eigenvalues.add(Double.toString(eigen));
			return eigenvalues;
		}
	}

	//getting the hessenberg form of THIS to start iterations if needed
	copy = this.hessenberg();


	int itcount=0;
	ArrayList<myMatrix> factorization=null;
//	myMatrix Q =identity(copy.getRows());
		while (!copy.isUpperTriangular() && !copy.isBlockDiagonal()) {
			//getting the QR factorization
			//reducing matrix via deflation
			//SOMETHING IS WRONG WITH MY SHIFT! STEPS WITHOUT THE SHIFT WORK BUT SHIFT DOESNT GIVE RIGHT EIGENVALUES
			//USING RAYLEIGH SHIFT
						//Shift step
//			double shift=copy.wilkinson();
//			double shift = copy.getEntry(copy.getRows(),copy.getRows());
//			myMatrix toShift = identity(copy.getRows());
//			toShift.scaleMatrix(shift);
//			copy=copy.subtractMatrix(toShift);
			//using the shift
		 	factorization= copy.houseHolderQR();
			//iteration for reducing A to schur form
			copy=factorization.get(1).multiplyMatrix(factorization.get(0));
//			copy.addMatrix(toShift);
			//this is for getting eigenvalues
		//	Q=Q.multiplyMatrix(factorization.get(0).makeIdentity(this.getRows()));
			itcount++;
			copy.checkZeros();
			copy.print(3);
			//need to check for deflation conditions
			//LOOK AT LOGIC ABOVE: if n,n-1 entry is zero, then we have 1x1 trailing
			//if n-1,n-2 entry is zero, then we have 2x2 trailing
			if (copy.getEntry(copy.getRows(),copy.getRows()-1)==0) {
				//we have 1x1 trailing matrix (eigenvalue is clear on the diagonal)
				eigenvalues.add(Double.toString(copy.getEntry(copy.getRows(),copy.getRows())));
				//now deflate matrix
				copy=copy.deflate(1);
			} else if (copy.getEntry(copy.getRows()-1,copy.getRows()-2)==0) {
				myMatrix trail = new myMatrix(2);
				//adding block to seperate calculation
				trail.setEntry(1,1,copy.getEntry(copy.getRows()-1,copy.getRows()-1));
				trail.setEntry(1,2,copy.getEntry(copy.getRows()-1,copy.getRows()));
				trail.setEntry(2,1,copy.getEntry(copy.getRows(),copy.getRows()-1));
				trail.setEntry(2,2,copy.getEntry(copy.getRows(),copy.getRows()));
				ArrayList<String> trailEigs = trail.deflateEigenValues();
				//we are getting every entry after the first one because the first is "Eigenvalues:"
				eigenvalues.add(trailEigs.get(1));
				eigenvalues.add(trailEigs.get(2));
				//now we have to deflate the copy matrix 
				copy=copy.deflate(2);
			}
	//			if (itcount==500) {
	//				System.out.println("Too many iterations!");
	//				break;
	//			}
				if (copy.getRows()==1) {
					eigenvalues.add(Double.toString(copy.getEntry(1,1)));
					System.out.println(itcount);
					for (String eigen:eigenvalues) {
						System.out.println(eigen);
					}
					return eigenvalues;
				}
				if (copy.getRows()==2) {
				//we now have a square matrix, so we can just compute the eigenvalues and return
					ArrayList<String> eigs = copy.deflateEigenValues();
				//we are adding every entry but the first because the first index in the list is "Eigenvalues:"	
					eigenvalues.add(eigs.get(1));
					eigenvalues.add(eigs.get(2));
					System.out.println(itcount);
					for (String eig:eigenvalues) {
						System.out.println(eig);
					}
					return eigenvalues;
			}

		}
	
		System.out.println(itcount);
//		ArrayList<String> eigs = new ArrayList<String>();
//		eigs.add("Eigenvalues:");
		for (int i=1;i<=copy.getRows();i++) {
			for (int j=1;j<=copy.getColumns();j++) {
				if (i==j) {
					if (i!=copy.getRows()) {
						if (copy.getEntry(i+1,j)!=0) {
							//we have a block
							myMatrix block = new myMatrix(2);
					//		double[] complex = new double[4];
					//		complex[0]=copy.getEntry(i,j);
							block.setEntry(1,1,copy.getEntry(i,j));
					//		complex[1]=copy.getEntry(i,j+1);
							block.setEntry(1,2,copy.getEntry(i,j+1));
					//		complex[2]=copy.getEntry(i+1,j);
							block.setEntry(2,1,copy.getEntry(i+1,j));
					//		complex[3]=copy.getEntry(i+1,j+1);
							block.setEntry(2,2,copy.getEntry(i+1,j+1));
					//		block.addEntry(complex);
							//recursive definition for 2x2 blocks for which we know the eigenvalues!
							ArrayList<String> temp=block.deflateEigenValues();
					//		for (String value: temp) {
					//			eigenvalues.add(value);
					//		}
							//not adding zero index because that is "Eigenvalues:" label!
							eigenvalues.add(temp.get(1));
							eigenvalues.add(temp.get(2));
							i++;
							j++;
							//after the for loop increments, we will be at the second furthest diagonal position
						} else {
							//we have a 1x1 matrix
							eigenvalues.add(Double.toString(copy.getEntry(i,j)));
						}
					} else {
						//if the last entry in the diagonal is not part of a block, then we add the value
						if (copy.getEntry(i,j-1)==0) {
							eigenvalues.add(Double.toString(copy.getEntry(i,j)));
						}
					}
				}
			}
		}
//		eigs.add("Eigenvectors:");
		//prints the eigenvalues so far in nice format
		for (String eigenvalue:eigenvalues) {
			System.out.println(eigenvalue);
		}

		//storing eigenvectors in eigs in case user needs it 
//		for (int i=1;i<=Q.getRows();i++) {
//			String eigenvector="";
//			for (int j=1;j<=Q.getColumns();j++){
//				if (j!=Q.getColumns()){
//					eigenvector+=""+Q.getEntry(i,j)+",";
//				} else {
//					eigenvector+=""+Q.getEntry(i,j);
//				}
				

//			} 
//			eigs.add(eigenvector);
//		}
		//need to add string representation of eigenvectors to eigs
		//printing eigenvectors from Q matrix 
//		Q.print(4);
		return eigenvalues;

	
	}

}	


