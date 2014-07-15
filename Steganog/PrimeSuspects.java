import java.awt.Color;

import edu.neumont.ui.Picture;

public class PrimeSuspects
{
	/**
	 * Takes a clean image and changes the prime-indexed pixels to secretly carry the message
	 */
	public Picture embedIntoImage(Picture cleanImage, String message)
	{
		int pixelHeight = cleanImage.height();
		int pixelWidth = cleanImage.width();
		
		String messageToEncode = message.toUpperCase();
		PrimeIterator primeIterator = new PrimeIterator((pixelHeight*pixelWidth)-1);
		int messageIndex = 0;
		
		while(primeIterator.hasNext() && messageIndex<messageToEncode.length())
		{
			char charToAdd = messageToEncode.charAt(messageIndex);
			String codedBinary = characterToCodedBinary(charToAdd);
			
			int index = primeIterator.next();
			Coordinate co = coordinateFromIndex(index,pixelWidth,pixelHeight);
			Color originalColor = cleanImage.get(co.x, co.y);
			
			Color newColor = addLowerBytesToColor(originalColor, codedBinary);
			cleanImage.set(co.x, co.y, newColor);
			messageIndex++;
		}
		return cleanImage;
	}
	
	/**
	 * Retrieves the embedded secret from the secret-carrying image
	 */
	public String retrieveFromImage(Picture imageWithSecretMessage)
	{
		int pixelHeight = imageWithSecretMessage.height();
		int pixelWidth = imageWithSecretMessage.width();
		
		PrimeIterator primeIterator = new PrimeIterator((pixelHeight*pixelWidth)-1);
		String message = "";
		while(primeIterator.hasNext())
		{
			int index = primeIterator.next();
			Coordinate co = coordinateFromIndex(index,pixelWidth,pixelHeight);
			Color c = imageWithSecretMessage.get(co.x, co.y);
			char ch = characterFromCodedBinary(retrieveLowerBytesFromColor(c));
			message = message + ch;
		}
		return message;
	}
	
	/**
	 * Creates an (x,y) coordinate from a given pixel index
	 * This is calculated based on the pixel width and height of the image
	 * Assumes 0 index is the upper left corner, and that the indexing is row major
	 */
	private Coordinate coordinateFromIndex(int index, int width, int height)
	{
		int x = index%width;
		int y = (int)index/width;
		Coordinate c = new Coordinate(x,y);
		return c;
	}
	
	/**
	 * Retrieves the last two bytes of the r, g, and b value of a color
	 * Combines and returns these bytes in a single string
	 */
	private String retrieveLowerBytesFromColor(Color c)
	{
		int rdec = c.getRed();
		int gdec = c.getGreen();
		int bdec = c.getBlue();
		String rbin = fullBinaryString(rdec, 8);
		String gbin = fullBinaryString(gdec, 8);
		String bbin = fullBinaryString(bdec, 8);
		
		String fullBin = rbin.substring(rbin.length()-2)+gbin.substring(gbin.length()-2)+bbin.substring(bbin.length()-2);
		return fullBin;
	}
	
	/**
	 * Retrieves the last two bytes of the r, g, and b value of a color
	 * Replaces these 6 bytes with those passed in
	 * Returns the modified color
	 */
	private Color addLowerBytesToColor(Color original, String lowerBytes)
	{
		int rdec = original.getRed();
		int gdec = original.getGreen();
		int bdec = original.getBlue();
		
		String rbin = (fullBinaryString(rdec, 8).substring(6))+lowerBytes.substring(0, 2);
		String gbin = (fullBinaryString(gdec, 8).substring(6))+lowerBytes.substring(2, 4);
		String bbin = (fullBinaryString(bdec, 8).substring(6))+lowerBytes.substring(4, 6);
		
		int newR=Integer.parseInt(rbin, 2);
		int newG=Integer.parseInt(gbin, 2);
		int newB=Integer.parseInt(bbin, 2);
		Color alteredColor = new Color(newR,newG,newB);
		return alteredColor;
	}
	
	/**
	 * Converts the passed in binary to decimal, modifies according to the encoding technique, then returns the corresponding character
	 * Current encoding technique involves the using the character's ascii value - 32
	 */
	private char characterFromCodedBinary(String bin)
	{
		int decimal = Integer.parseInt(bin, 2)+32;
		char c = (char)decimal;
		return c;
	}
	
	/**
	 * Modifies the character's ascii value according to the encoding technique, then returns that value in binary form
	 * Current encoding technique involves the using the character's ascii value - 32
	 */
	private String characterToCodedBinary(char c)
	{
		int decimal = (int)c;
		decimal -= 32;
		String bin = fullBinaryString(decimal,6);
		return bin;
	}
	
	/**
	 * Translates the passed in integer to a binary string
	 * Fills the remaining leftmost space with 0's so that the string is of the passed in length
	 */
	private String fullBinaryString(int dec, int binaryLength)
	{
		String bin = Integer.toBinaryString(dec);//bit shifting
		String fullBin = bin;
		for(int i = 0; i < binaryLength-bin.length(); i++)
		{
			fullBin = "0" + fullBin;
		}
		return fullBin;
	}
	
	/**
	 * Stores an int x and int y
	 * Used to conveniently pass and return (x,y) coordinates between methods
	 */
	private class Coordinate
	{
		public Coordinate(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		public int x;
		public int y;
	}
}
