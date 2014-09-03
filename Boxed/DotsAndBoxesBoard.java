import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class DotsAndBoxesBoard extends JPanel
{
	private int columns;
	private int rows;
	private int width;
	private int height;
	private int buffer;
	private int xSpacing;
	private int ySpacing;
	private double dotSize;
	private double lineSize;
	private Color dotColor;
	private Color lineColor;
	private Graph graph;
	
	public DotsAndBoxesBoard(int columns, int rows, int width, int height, int buffer, Graph graph)
	{
		this.columns = columns;
		this.rows = rows;
		this.width = width;
		this.height = height;
		this.buffer = buffer;
		this.graph = graph;
		
		dotColor = Color.yellow;
		lineColor = Color.orange;
		
		xSpacing = (width-(2*buffer))/(columns-1);
		ySpacing = (height-(2*buffer))/(rows-1);
		
		dotSize = 10;
		lineSize = 5;
		
		this.setBackground(Color.gray);
		this.setPreferredSize(new Dimension(width,height));
		this.setVisible(true);
		this.repaint();
	}
	
	private void drawScale(Graphics g)
	{
		g.setColor(Color.white);
		int xPosY = buffer/4 ;
		for(int x = 0; x < columns; x++)
		{
			int posX = (x+1)*xSpacing;
			g.drawString(""+x, posX, xPosY);
		}
		int yPosX = buffer/4;
		for(int y = 0; y < rows; y++)
		{
			int posY = (y+1)*ySpacing;
			g.drawString(""+y, yPosX, posY);
		}
	}
	
	private void drawGraph(Graphics2D g)
	{
		for(int v = 0; v < (columns*rows); v++)
		{
			for(int w = graph.first(v); w < (columns*rows); w = graph.next(v, w))
			{
				int x1 = v%columns;
				int y1 = (int)v/columns;
				int x2 = w%columns;
				int y2 = (int)w/columns;
				drawLine(g, x1, y1, x2, y2);
			}
		}
		for(int v = 0; v < (columns*rows); v++)
		{
			int x = v%columns;
			int y = (int)v/columns;
			drawDot(g, x, y);
		}
	}
	
	private void drawDot(Graphics2D g, int x, int y)
	{
		g.setColor(dotColor);
		int posX = buffer+(x*xSpacing);
		int posY = buffer+(y*ySpacing);
		g.fill(new Ellipse2D.Double(posX-(dotSize/2), posY-(dotSize/2), dotSize, dotSize));
	}
	private void drawLine(Graphics2D g, int x1, int y1, int x2, int y2)
	{
		g.setColor(lineColor);
		g.setStroke(new BasicStroke(5));
		int startX = buffer+(x1*xSpacing);
		int startY = buffer+(y1*ySpacing);
		int endX = buffer+(x2*xSpacing);
		int endY = buffer+(y2*ySpacing);
		g.drawLine(startX, startY, endX, endY);
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;		
		drawScale(g);
		drawGraph(g2);
		repaint();
	}
}
