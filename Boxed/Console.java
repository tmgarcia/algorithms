import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.text.NumberFormatter;

public class Console extends JPanel
{
	private GridBagConstraints grid;
	private NumberFormatter formatter;
	
	private JLabel x1Label, y1Label;
	private JComboBox x1Box, y1Box;
	private JLabel x2Label, y2Label;
	private JComboBox x2Box, y2Box;
	private JLabel playerNumLabel;
	private JFormattedTextField playerNumField;
	public JButton drawButton;
	
	private JLabel playerScoreLabel;
	private JFormattedTextField playerScoreField;
	public JButton playerScoreButton;
	
	private JTextArea messageArea;
	private JScrollPane messageScrollPane;
	
	public JButton movesLeftButton;
	public JLabel dbLabel, scLabel;
	public JButton dbDFSButton, dbBFSButton;
	public JButton scDFSButton, scBFSButton;
	public JButton dcButton, cycleButton, chainButton;
	
	private String newline = "\n";
	
	private int columns;
	private int rows;
	
	public Console(int columns, int rows)
	{
		super(new GridBagLayout());
		this.columns = columns;
		this.rows = rows;
		
		NumberFormat format = NumberFormat.getInstance();
	    formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(0);
	    formatter.setMaximum(Integer.MAX_VALUE);
	    formatter.setCommitsOnValidEdit(true);
		
		grid = new GridBagConstraints();
		grid.fill = GridBagConstraints.BOTH;
		grid.insets = new Insets(0, 10, 10, 0);
		int currentRow = 0;

		currentRow = setupXYBoxes(currentRow);
		currentRow = setupPlayerNumField(currentRow);
		currentRow = setupMessageBox(currentRow);
		currentRow = setupPlayerScoreField(currentRow);
		
		movesLeftButton = new JButton("Any Moves Left?");
		grid.gridx = 0;
		grid.gridy = currentRow;
		grid.gridwidth = 3;
		add(movesLeftButton, grid);
		grid.gridwidth = 1;
		currentRow++;
		
		dbLabel = new JLabel("Dots&Boxes");
		scLabel = new JLabel("Strings&Coins");
		
		dbDFSButton = new JButton("DFS");
		dbBFSButton = new JButton("BFS");
		scDFSButton = new JButton("DFS");
		scBFSButton = new JButton("BFS");
		
		grid.gridy = currentRow;
		grid.gridx = 0;
		add(dbLabel, grid);
		grid.gridx = 1;
		add(dbDFSButton, grid);
		grid.gridx = 2;
		add(dbBFSButton, grid);
		currentRow++;
		
		grid.gridy = currentRow;
		grid.gridx = 0;
		add(scLabel, grid);
		grid.gridx = 1;
		add(scDFSButton, grid);
		grid.gridx = 2;
		add(scBFSButton, grid);
		currentRow++;
		
		dcButton = new JButton("DoubleCrosses");
		cycleButton = new JButton("Cycles");
		chainButton = new JButton("Chains");
		grid.gridy = currentRow;
		grid.gridx = 0;
		add(dcButton,grid);
		grid.gridx = 1;
		add(cycleButton,grid);
		grid.gridx = 2;
		add(chainButton,grid);
		currentRow++;
		this.setVisible(true);
	}
	private int setupPlayerNumField(int currentRow)
	{
		playerNumLabel = new JLabel("Player: ");
		playerNumField = new JFormattedTextField(formatter);
		playerNumField.setText("0");
		
		grid.gridx = 0;
		grid.gridy = currentRow;
		add(playerNumLabel, grid);
		grid.gridx = 1;
		add(playerNumField, grid);
		grid.gridx = 2;
		add(drawButton, grid);
		currentRow++;
		return currentRow;
	}
	private int setupXYBoxes(int currentRow)
	{
		x1Label = new JLabel("x1");
		y1Label = new JLabel("y1");
		x2Label = new JLabel("x2");
		y2Label = new JLabel("y2");
		
		String[] columnValues = new String[columns];
		for(int i = 0; i < columns; i++)
		{
			columnValues[i] = ""+i;
		}
		String[] rowValues = new String[rows];
		for(int i = 0; i < rows; i++)
		{
			rowValues[i] = ""+i;
		}
		
		x1Box = new JComboBox(columnValues);
		x2Box = new JComboBox(columnValues);
		y1Box = new JComboBox(rowValues);
		y2Box = new JComboBox(rowValues);
		
		drawButton = new JButton("Draw Line");
		
		grid.gridy = currentRow;
		grid.gridx = 0;
		add(x1Label, grid);
		grid.gridx = 1;
		add(y1Label, grid);
		currentRow++;
		
		grid.gridx = 0;
		grid.gridy = currentRow;
		add(x1Box, grid);
		grid.gridx = 1;
		add(y1Box, grid);
		currentRow++;
		
		grid.gridy = currentRow;
		grid.gridx = 0;
		add(x2Label, grid);
		grid.gridx = 1;
		add(y2Label, grid);
		currentRow++;
		grid.gridx = 0;
		grid.gridy = currentRow;
		add(x2Box, grid);
		grid.gridx = 1;
		add(y2Box, grid);
		currentRow++;
		return currentRow;
	}
	private int setupMessageBox(int currentRow)
	{
		messageArea = new JTextArea(10, 10);
		messageArea.setLineWrap(true);
		messageArea.setWrapStyleWord(true);
		messageScrollPane = new JScrollPane(messageArea);
		messageScrollPane.setPreferredSize(new Dimension(50, 200));
		messageArea.setEditable(false);
		messageArea.append("Message Area" + newline);
		
		grid.gridy = currentRow;
		grid.gridx = 0;
		grid.gridwidth = 3;
		grid.gridheight = 5;
		grid.weightx = 1;
		add(messageScrollPane, grid);
		currentRow+=5;
		grid.weightx = 0;
		grid.gridwidth = 1;
		grid.gridheight = 1;
		return currentRow;
	}
	private int setupPlayerScoreField(int currentRow)
	{
		playerScoreLabel = new JLabel("Player: ");
		playerScoreField = new JFormattedTextField(formatter);
		playerScoreField.setText("0");
		playerScoreButton = new JButton("Get Score");
		
		grid.gridx = 0;
		grid.gridy = currentRow;
		add(playerScoreLabel, grid);
		grid.gridx = 1;
		add(playerScoreField, grid);
		grid.gridx = 2;
		add(playerScoreButton, grid);
		currentRow++;
		return currentRow;
	}
	public int getX1()
	{
		return Integer.parseInt((String)x1Box.getSelectedItem());
	}
	public int getY1()
	{
		return Integer.parseInt((String)y1Box.getSelectedItem());
	}
	public int getX2()
	{
		return Integer.parseInt((String)x2Box.getSelectedItem());
	}
	public int getY2()
	{
		return Integer.parseInt((String)y2Box.getSelectedItem());
	}
	public int getPlayerNum()
	{
		if(playerNumField.getText().isEmpty())
		{
			playerNumField.setText("0");
		}
		return Integer.parseInt(playerNumField.getText());
	}
	public void pushMessage(String message)
	{
		messageArea.append(message + newline);
		messageArea.setCaretPosition(messageArea.getDocument().getLength());
	}
	public int getPlayerScoreNum()
	{
		if(playerScoreField.getText().isEmpty())
		{
			playerScoreField.setText("0");
		}
		return Integer.parseInt(playerScoreField.getText());
	}
}
