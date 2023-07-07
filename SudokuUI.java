import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.*;

public class SudokuUI {

	private final String title = "Sudoku";
	private final int colsAndRows = 9;
	private final int frameWidth = 500;
	private final int frameLength = 500;
	private final int endFrameWidth = 100;
	private final int endFrameLength = 200;
	final private int WORD_FONT_SIZE = 20;
	final private String WORD_FONT_TYPE= "Verdana";
	private Sudoku Sudoku;
	private int counter;
	private int wrong;
	public boolean gameOver = false;
	
	private JFrame frame;
	private GridLayout grid = new GridLayout(colsAndRows + 2, colsAndRows + 2);
	private JTextField[][] textArray = new JTextField[colsAndRows][colsAndRows];
	
	public SudokuUI(Sudoku Sudoku) {
		this.Sudoku = Sudoku;
		
		frame = new JFrame(title);
		frame.setSize(frameWidth, frameLength);
		
		frame.setLayout(grid);
		frame.setResizable(true);
		
		for (int row = 0 ; row < colsAndRows ; row++) {
			if (row == 3 || row == 6) {
				for (int i = 0 ; i < colsAndRows + 2 ; i++) {
					frame.add(new JSeparator(SwingConstants.HORIZONTAL));
				}
			}
			for (int col = 0 ; col < colsAndRows ; col++) {
				textArray[row][col] = new JTextField(1);
				frame.add(textArray[row][col]);
				Font f = new Font(WORD_FONT_TYPE,Font.BOLD,WORD_FONT_SIZE);
				textArray[row][col].setBackground(Color.cyan);
				textArray[row][col].setFont(f);
				textArray[row][col].setHorizontalAlignment(JTextField.CENTER);
				JTextField tmp  = textArray[row][col];
				if (existsInGiven(new Point(row, col))) {
					tmp.setText(Integer.toString(Sudoku.SudokuAnswer[row][col]));
					tmp.setEditable(false);
				}
				int column = col;
				int rowww = row;
				//An event to confirm only one letter will be typed in the text field
				tmp.addKeyListener(new java.awt.event.KeyAdapter() {
					public void keyTyped(java.awt.event.KeyEvent evt) {
						if (!gameOver && tmp.isEditable()) {
						String ans = Sudoku.correctPlace(evt.getKeyChar() ,rowww, column);
						if (ans.equals("Correct")) {
							if (!tmp.getBackground().equals(Color.green)) {
								counter++;
							}
							tmp.setBackground(Color.GREEN);
						} else if (ans.equals("Wrong")) {
							if (tmp.getBackground().equals(Color.green)) {
								counter--;
							}
							wrong++;
							tmp.setBackground(Color.RED);
						} else {
							if (tmp.getBackground().equals(Color.green)) {
								counter--;
							}
							tmp.setBackground(Color.cyan);
						}
						if(tmp.getText().length()>=1){
							tmp.setText(Character.toString(evt.getKeyChar()));
							evt.consume();
						}
						if (counter == (colsAndRows * colsAndRows) - Sudoku.given) {
							gameOver();
						}
						} else {
							tmp.setEditable(false);
						}
					}
				});
				if (col == 2 || col == 5) {
						frame.add(new JSeparator(SwingConstants.VERTICAL));
				}
			}
		}
		frame.setVisible(true);
	}
	
	private boolean existsInGiven(Point p) {
		for (int i = 0 ; i < Sudoku.givenPoints.length ; i++) {
			if (p.equals(Sudoku.givenPoints[i])) {
				return true;
			}
		}
		return false;
	}
	
	private void gameOver() {
		gameOver = true;
		JFrame endFrame = new JFrame("YOU WON!!!");
		endFrame.setSize(endFrameLength, endFrameWidth);
		endFrame.setLocation(frame.getX() + frameWidth/2 - endFrameLength/2, frame.getY() + frameLength/2 - endFrameWidth/2);
		String mistake;
		if (wrong == 1) {
			mistake = "mistake";
		} else {
			mistake = "mistakes";
		}
		JLabel mistakes = new JLabel(("      You made " + wrong + " " + mistake + "..."));
		endFrame.add(mistakes);
		endFrame.setVisible(true);
	}
	
}
