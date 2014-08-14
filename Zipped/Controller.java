import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Random;

import edu.neumont.io.Bits;


public class Controller
{
	public static void decompressImage()
	{
		int[] frequencies = {423, 116, 145, 136, 130, 165, 179, 197, 148, 125, 954, 156, 143, 145, 164, 241, 107, 149, 176, 153, 121, 164, 144, 166, 
				100, 138, 157, 140, 119, 138, 178, 289, 360, 120, 961, 195, 139, 147, 129, 192, 119, 146, 138, 184, 137, 196, 163, 331, 115, 160, 127, 
				172, 176, 181, 149, 194, 138, 154, 163, 167, 196, 174, 250, 354, 142, 169, 170, 209, 205, 179, 147, 245, 108, 179, 148, 186, 131, 160, 
				112, 219, 118, 204, 164, 154, 154, 175, 189, 239, 126, 145, 185, 179, 149, 167, 152, 244, 189, 257, 234, 208, 179, 170, 171, 178, 184, 
				189, 203, 184, 204, 208, 187, 163, 335, 326, 206, 189, 210, 204, 230, 202, 415, 240, 275, 295, 375, 308, 401, 608, 2099, 495, 374, 160, 
				130, 331, 107, 181, 117, 133, 476, 129, 137, 106, 107, 237, 184, 143, 122, 143, 1596, 205, 121, 170, 123, 124, 150, 132, 143, 133, 178, 
				308, 96, 102, 114, 176, 159, 149, 123, 199, 1156, 119, 144, 237, 131, 155, 143, 225, 92, 125, 117, 138, 135, 154, 124, 137, 121, 143, 149, 
				141, 177, 159, 247, 384, 302, 120, 95, 140, 87, 1460, 155, 199, 111, 198, 147, 182, 91, 148, 119, 233, 445, 1288, 138, 133, 122, 170, 156, 
				257, 143, 149, 180, 174, 132, 151, 193, 347, 91, 119, 135, 182, 124, 152, 109, 175, 152, 159, 166, 224, 126, 169, 145, 220, 119, 148, 133, 
				158, 144, 185, 139, 168, 244, 145, 167, 167, 262, 214, 293, 402};
		HuffmanTree tree = new HuffmanTree(frequencies, (byte)-128, (byte)127);
		tree.printTree();
		HuffmanCompression compressor = new HuffmanCompression();
		
		int originalLength = 54679;
		Path path = Paths.get("compressed.huff");

		try
		{
			byte[] bytes = Files.readAllBytes(path);
			byte[] decompressedBytes = compressor.decompress(tree, originalLength, bytes);
			
			FileOutputStream stream = new FileOutputStream("decompressed.bmp");
			try {
			    stream.write(decompressedBytes);
			} finally {
			    stream.close();
			}
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[]args)
	{
		decompressImage();
//		Random gen = new Random();
//		byte[] bytes = new byte[] { 45, 56, 67, 78, 89, 12, 23, 34, 45, 23, 45, 67, 45 };
//		for(int i = 0; i < bytes.length; i++)
//		{
//			System.out.print(bytes[i]+", ");
//		}
//		System.out.println();
//		HuffmanTree tree = new HuffmanTree(bytes);
//		tree.printNodeWithBits();
//		System.out.println();
//		//tree.printTree();
//		
//		Bits bits = new Bits();
//		
//		tree.fromByte((byte)45, bits);
//		
//		//System.out.println(tree.toByte(bits));
//		
//		HuffmanCompression compressor = new HuffmanCompression();
//		byte[] bytesCompressed = compressor.compress(tree, bytes);
//		
//		for(int i = 0; i < bytesCompressed.length; i++)
//		{
//			System.out.print(bytesCompressed[i] + ",");
//		}
//		System.out.println();
//		for(int i = 0; i < bytesCompressed.length; i++)
//		{
//			String bs =  String.format("%8s", Integer.toBinaryString(bytesCompressed[i] & 0xFF)).replace(' ', '0');
//			System.out.print(bs);
//		}
//		System.out.println();
//		
//		byte[] bytesDecompressed = compressor.decompress(tree, bytes.length, bytesCompressed);
//		for(int i = 0; i < bytesDecompressed.length; i++)
//		{
//			System.out.print(bytesDecompressed[i] + ",");
//		}
	}
}
