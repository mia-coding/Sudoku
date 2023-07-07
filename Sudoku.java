import java.awt.Point;
import java.util.*; 

public class Sudoku {
	
	private final int colsAndRows = 9;
	public int[][] SudokuAnswer = new int[colsAndRows][colsAndRows];
	private ArrayList<Point> available = new ArrayList<Point>();
	final private int givens = 35;
	public Point[] givenPoints = new Point[givens];
	public int given = givenPoints.length;
	private int index;
	
	public Sudoku() {
		int curr_y = -1;
		int curr_x = -1;
		populateGivenPoints();
		for (int num = 1 ; num <= colsAndRows; num++) {
			populateAvailable();
			
			for (int i = 0 ; i < colsAndRows ; i++) {
				if (available.size() == 0) {
					for (int l = 0 ; l < colsAndRows ; l++) {
						for (int m = 0; m < colsAndRows; m++) {
							if(SudokuAnswer[l][m] == num) {
								SudokuAnswer[l][m] = 0;
							}
						}
					}
					num--;
					continue;
				}
				index = (int) (Math.random() * available.size());
				SudokuAnswer[available.get(index).x] [available.get(index).y] = num;
				int j = 0;
				curr_x = available.get(index).x;
				curr_y = available.get(index).y;
				while (j < available.size()) {
					if ((available.get(j).x == curr_x || available.get(j).y == curr_y) || (findSquare(available.get(j).x, available.get(j).y) == findSquare(curr_x, curr_y))) {
						available.remove(j);
					}
					else {
						j++;
					}
				}
			}
			available.removeAll(available);
		}
		for (int i = 0 ; i < colsAndRows ; i++) {
			for (int j = 0 ; j < colsAndRows ; j++) {
				System.out.print(SudokuAnswer[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public void populateGivenPoints() {
		populateAvailable();
		for (int i = 0 ; i < givenPoints.length ; i++) {
			int given = (int) (Math.random() * available.size());
			givenPoints[i] = available.get(given);
			available.remove(given);
		}
		available.removeAll(available);
	}

	public void populateAvailable() {
		for (int row = 0 ; row < colsAndRows ; row++) {
			for (int col = 0 ; col < colsAndRows ; col++) {
				if (SudokuAnswer[row][col] == 0) {
					available.add(new Point(row, col));
				}
			}
		}
	}
	public String correctPlace(char num, int row, int col) {	
		int number;
		if (num == ' ') {
			number = 0;
			return "Blank";
		} else {
			number = Character.getNumericValue(num);
		}
		if (number == SudokuAnswer[row][col]) {
			return "Correct";
		} else {
			return "Wrong";
		}
	}
	public int findSquare(int row, int col) {
		return (3 * (row/3)) + (col/3);
	}
	
	public static void main(String[] args) {
		Sudoku Sudoku = new Sudoku();
		SudokuUI UI = new SudokuUI(Sudoku);
	}
}
