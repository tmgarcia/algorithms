import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import edu.neumont.ui.Picture;


public class SeamCarver
{
	private class Coordinate
	{
		int x;
		int y;
		Coordinate(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
	}
	private class Pixel
	{
		Color color;
		double energy;
		Pixel()
		{
			color = new Color(0);
			energy = -1;
		}
		Pixel(int energy, Color color)
		{
			this.energy = energy;
			this.color = color;
		}
	}
	private int width;
	private int height;
	
	List<List<Pixel>> pixels;
	
	public SeamCarver(Picture pic)
	{
		width = pic.width();
		height = pic.height();
		pixels  = new ArrayList<List<Pixel>>();
		setupPixels(pic);
	}
	private void setupPixels(Picture pic)
	{
		for(int x = 0; x < width; x++)
		{
			pixels.add(new ArrayList<Pixel>());
			for(int y = 0; y < height; y++)
			{
				Color c = pic.get(x, y);
				Pixel p = new Pixel(-1, c);
				pixels.get(x).add(p);
			}
		}
	}
	private double getEnergy(Pixel p, int x, int y)
	{
		Pixel leftPixel = pixels.get(((x-1)%width)+(x>0? 0:width)).get(y);
		Pixel rightPixel = pixels.get((x+1)%width).get(y);
		Pixel topPixel = pixels.get(x).get(((y-1)%height)+(y>0? 0:height));
		Pixel bottomPixel = pixels.get(x).get((y+1)%height);
		
		int Rx = leftPixel.color.getRed() - rightPixel.color.getRed();
		int Ry = topPixel.color.getRed() - bottomPixel.color.getRed();
		int Gx = leftPixel.color.getGreen() - rightPixel.color.getGreen();
		int Gy = topPixel.color.getGreen() - bottomPixel.color.getGreen();
		int Bx = leftPixel.color.getBlue() - rightPixel.color.getBlue();
		int By = topPixel.color.getBlue() - bottomPixel.color.getBlue();
		int x2 = (Rx*Rx) + (Gx*Gx) + (Bx*Bx);
		int y2 = (Ry*Ry) + (Gy*Gy) + (By*By);
		double Energy = y2 + x2;
		p.energy = Energy;
		
		return Energy;
	}
	private Coordinate getCoords(int index)
	{
		int x= (index%width);
		int y= (index/width);
		Coordinate c = new Coordinate(x, y);
		return c;
	}
	private int getIndex(int x, int y)
	{
		return y*width + x;
	}
	public Picture getPicture ()
	{
		Picture currentPic = new Picture(width, height);
		for(int x = 0; x<width; x++)
		{
			for(int y = 0; y < height; y++)
			{
				currentPic.set(x, y, pixels.get(x).get(y).color);
			}
		}
		return currentPic;
		
	}// get the current image
	public int width()//constant time
	{
		return width;
	}
	public int height()//constant time
	{
		return height;
	}
	public double energy(int x, int y)//constant time
	{
		if(x>=width || y>=height)
		{
			throw new java.lang.IndexOutOfBoundsException();
		}
		double e = getEnergy(pixels.get(x).get(y), x, y);
		return e;
		
	}// the energy of a pixel at (x,y)
	public int[] findHorizontalSeam()//O(WH)
	{
		return horizontalSeam();
		
	}// the sequence of indices for a horizontal seam
	private int[] horizontalSeam()
	{
		Coordinate start = getLowestEnergyFromFirstColumn();
		Pixel startPixel = pixels.get(start.x).get(start.y);
		int indices[] = new int[width];
		indices[0] = getIndex(start.x, start.y);
		Coordinate current = start;
		
		startPixel.color = Color.red;
		
		for(int i = 1; i < width; i++)
		{
			current = getLowestEnergyRightNeighbor(current);
			pixels.get(current.x).get(current.y).color = Color.red;
			indices[i] = getIndex(current.x, current.y);
		}
		return indices;
	}
	private Coordinate getLowestEnergyRightNeighbor(Coordinate pixel)
	{
		Coordinate lowestNeighbor = null;
		double lowestEnergy = Double.MAX_VALUE;
		if(pixel.x+1 < width)
		{
			lowestEnergy = getEnergy(pixels.get(pixel.x+1).get(pixel.y), pixel.x+1, pixel.y);
			
			lowestNeighbor = new Coordinate(pixel.x+1, pixel.y);
			if(pixel.y>0)
			{
				//x+1 y-1
				if(getEnergy(pixels.get(pixel.x+1).get(pixel.y-1), pixel.x+1, pixel.y-1)<lowestEnergy)
				{
					lowestEnergy = pixels.get(pixel.x+1).get(pixel.y-1).energy;
					lowestNeighbor.y = pixel.y-1;
				}
			}
			if(pixel.y+1<height)
			{
				//x+1 y+1
				if(getEnergy(pixels.get(pixel.x+1).get(pixel.y+1), pixel.x+1, pixel.y+1)<lowestEnergy)
				{
					lowestEnergy = pixels.get(pixel.x+1).get(pixel.y+1).energy;
					lowestNeighbor.y = pixel.y+1;
				}
			}
		}
		return lowestNeighbor;
	}
	private Coordinate getLowestEnergyFromFirstColumn()
	{
		int x = 0;
		int lowestY = -1;
		double lowestEnergy = Double.MAX_VALUE;
		for(int y = 0; y < height; y++)
		{
			if(getEnergy(pixels.get(x).get(y),x,y)<lowestEnergy)
			{
				lowestEnergy = pixels.get(x).get(y).energy;
				lowestY = y;
			}
		}
		Coordinate c = new Coordinate(x, lowestY);
		return c;
	}
	public int[] findVerticalSeam()//O(WH)
	{
		return verticalSeam();
	}// the sequence of indices for a vertical seam
	private int[] verticalSeam()
	{
		Coordinate start = getLowestEnergyFromFirstRow();
		Pixel startPixel = pixels.get(start.x).get(start.y);
		int indices[] = new int[height];
		indices[0] = getIndex(start.x, start.y);
		Coordinate current = start;
		
		startPixel.color = Color.red;
		
		for(int i = 1; i < height; i++)
		{
			current = getLowestEnergyLowerNeighbor(current);
			pixels.get(current.x).get(current.y).color = Color.red;
			indices[i] = getIndex(current.x, current.y);
		}
		return indices;
	}
	private Coordinate getLowestEnergyLowerNeighbor(Coordinate pixel)
	{
		Coordinate lowestNeighbor = null;
		double lowestEnergy = Double.MAX_VALUE;
		int neighborY = pixel.y+1;
		if(neighborY < height)
		{
			lowestEnergy = getEnergy(pixels.get(pixel.x).get(neighborY), pixel.x, neighborY);
			lowestNeighbor = new Coordinate(pixel.x, neighborY);
			if(pixel.x>0)
			{
				//x+1 y-1
				if(getEnergy(pixels.get(pixel.x-1).get(neighborY), pixel.x-1, neighborY)<lowestEnergy)
				{
					lowestEnergy = pixels.get(pixel.x-1).get(neighborY).energy;
					lowestNeighbor.x = pixel.x-1;
				}
			}
			if(pixel.x+1<width)
			{
				//x+1 y+1
				if(getEnergy(pixels.get(pixel.x+1).get(neighborY), pixel.x+1, neighborY)<=lowestEnergy)
				{
					lowestEnergy = pixels.get(pixel.x+1).get(neighborY).energy;
					lowestNeighbor.x = pixel.x+1;
				}
			}
		}
		return lowestNeighbor;
	}
	private Coordinate getLowestEnergyFromFirstRow()
	{
		int y = 0;
		int lowestX = -1;
		double lowestEnergy = Double.MAX_VALUE;
		for(int x = 0; x < width; x++)
		{
			if(getEnergy(pixels.get(x).get(y),x,y)<lowestEnergy)
			{
				lowestEnergy = pixels.get(x).get(y).energy;
				lowestX = x;
			}
		}
		Coordinate c = new Coordinate(lowestX, y);
		return c;
	}
	public void removeHorizontalSeam(int[] indices)//O(WH)
	{
		if((width==1 || height==1) || indices.length < width)
		{
			throw new java.lang.IllegalArgumentException();
		}
		Coordinate previous = null;
		Coordinate c = null;
		for(int i = 0; i < indices.length; i++)
		{
			if(indices[i]>= width*height)
			{
				System.out.println("INDICES["+i+"] -" + indices[i]+"- OUT OF BOUNDS");
				System.out.println("WIDTH("+width+")*HEIGHT("+height+")=" + width*height);
				throw new java.lang.IndexOutOfBoundsException();
			}
			c = getCoords(indices[i]);
			if(previous != null && (c.x-previous.x!=1 || Math.abs(previous.y-c.y)>1))
			{
				System.out.println("INDEX OFF BY MORE THAN ONE FROM PREVIOUS");
				throw new java.lang.IndexOutOfBoundsException();
			}
			shiftColumnUp(c.x, c.y);
		}
		height-=1;
	}
	private void shiftColumnUp(int columnX, int topY)
	{
		int x = columnX;
		List<Pixel> column = pixels.get(x);
		for(int y = topY+1; y < height; y++)
		{
			column.set(y-1, column.get(y));
		}
	}
	public void removeVerticalSeam(int[] indices)//O(WH)
	{
		if((width==1 || height==1) || indices.length < height)
		{
			throw new java.lang.IllegalArgumentException();
		}
		Coordinate previous = null;
		Coordinate c = null;
		for(int i = 0; i < indices.length; i++)
		{
			if(indices[i]>= width*height)
			{
				System.out.println("INDICES["+i+"] -" + indices[i]+"- OUT OF BOUNDS");
				System.out.println("WIDTH("+width+")*HEIGHT("+height+")=" + width*height);
				throw new java.lang.IndexOutOfBoundsException();
			}
			c = getCoords(indices[i]);
			if(previous != null && (c.y-previous.y!=1 || Math.abs(previous.x-c.x)>1))
			{
				System.out.println("INDEX OFF BY MORE THAN ONE FROM PREVIOUS");
				throw new java.lang.IndexOutOfBoundsException();
			}
			shiftRowLeft(c.x, c.y);
		}
		width-=1;
	}
	private void shiftRowLeft(int leftX, int rowY)
	{
		int y = rowY;
		//List<Pixel> column = pixels.get(x);
		for(int x = leftX+1; x < width; x++)
		{
			pixels.get(x-1).set(y, pixels.get(x).get(y));
		}
	}

}
