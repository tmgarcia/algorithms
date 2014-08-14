import java.util.ArrayList;
import java.util.Iterator;

import edu.neumont.io.Bits;


public class HuffmanCompression
{
	public byte[] compress(HuffmanTree tree, byte[] b)
	{
		ArrayList<Byte> compressed = new ArrayList<Byte>();
		String byteSoFar = "";
		for(int i = 0; i < b.length; i++)
		{
			Bits bits = new Bits();
			tree.fromByte(b[i], bits);
			Iterator<Boolean> bitsIterator = bits.iterator();
			byteSoFar = buildCompressedBytes(bits, byteSoFar, compressed);
		}
		int padding = 8-byteSoFar.length();
		if(padding>0)
		{
			for(int i = 0; i < padding; i++)
			{
				byteSoFar += "0";
			}
			byte by = (byte)(int)Integer.valueOf(byteSoFar, 2);
			compressed.add(by);
		}
		int size = compressed.size();
		byte[] bytes = new byte[size];
		for(int i = 0; i < size; i++)
		{
			bytes[i] = compressed.get(i);
		}
		return bytes;
	}
	
	public String buildCompressedBytes(Bits bits, String currentByteSoFar, ArrayList<Byte> compressedBytes)
	{
		//System.out.println(currentBit);
		if(currentByteSoFar.length()==8)
		{
			byte b= (byte)(int)Integer.valueOf(currentByteSoFar, 2);
			compressedBytes.add(b);
			currentByteSoFar = "";
		}
		if(bits.isEmpty())
		{
			return currentByteSoFar;
		}
		int nextBit = (bits.poll())? 1: 0;
		currentByteSoFar += nextBit;
		return buildCompressedBytes(bits, currentByteSoFar, compressedBytes);
	}
	
	public byte[] decompress(HuffmanTree tree, int uncompressedLength, byte[] b)
	{
		//ArrayList<Byte> decompressed = new ArrayList<Byte>();
		byte[] decompressed = new byte[uncompressedLength];
		Bits bits = new Bits();
		String bitString = "";
		for(int i = 0; i < b.length; i++)
		{
			String asString = String.format("%8s", Integer.toBinaryString(b[i] & 0xFF)).replace(' ', '0');
			bitString += asString;
		}
		for(int i = 0; i < bitString.length(); i++)
		{
			bits.add(bitString.charAt(i)!='0');
		}
		for(int i = 0; i < uncompressedLength; i++)
		{
			decompressed[i] = tree.toByte(bits);
		}
		return decompressed;
	}
}
