package com.encrypt.ceaserit;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.util.Random;

/**
 * Utility methods useful for cryptanalysis
 * @author prof. H. Roumani
 * @version F15
 */
public class CryptoTools
{
	public static final double[] ENGLISH = 
{8.12,1.49,2.71,4.32,12.02,2.3,2.03,5.92,7.31,0.1,0.69,3.98,2.61,6.95,7.68,1.82,0.11,6.02,6.28,9.1,2.88,1.11,2.09,0.17,2.11,0.07};
	
	/** Empty to prevent instantiation */
	private CryptoTools()
	{
	}

	/**
	 * Clean away all the non-letters from an ANSI byte array and return a new
	 * byte array with letters converted to upper-case.
	 **/
	public static byte[] clean(byte[] in)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		for (int i = 0; i < in.length; i++)
		{
			char c = (char) (in[i] & ~32);
			if (c >= 'A' && c <= 'Z')
				bos.write(c);
		}
		return bos.toByteArray();
	}

	/**
	 * Given a string a hex digits, convert it to an array of bytes.
	 **/
	public static byte[] hexToBytes(String string)
	{
		assert (string != null);
		if (string.length() % 2 != 0)
			string += "0";
		int half = string.length() / 2;
		byte[] buffer = new byte[half];
		for (int i = 0; i < half; i++)
		{
			String pair = string.substring(2 * i, 2 * i + 2);
			buffer[i] = (byte) Integer.parseInt(pair, 16);
		}
		return buffer;
	}
	/**
	 * Given an array of bytes, convert it to a string of hex digits.
	 */
	public static String bytesToHex(byte[] data)
	{
		assert (data != null);
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < data.length; i++)
		{
			int tmp = data[i] & 0xFF;
			if (tmp < 16)
				buffer.append("0");
			buffer.append(Integer.toHexString(tmp));
		}
		return buffer.toString().toUpperCase();
	}

	/**
	 * Given an array of bytes, convert it to a string of bits.
	 */
	public static String bytesToBin(byte[] data)
	{
		final int BITS_PER_BYTE = 8;
		StringBuffer result = new StringBuffer();
		for (byte b : data)
		{
			String tmp = Integer.toBinaryString(b & 0xFF);
			while (tmp.length() < BITS_PER_BYTE) tmp = "0" + tmp;
			result.append(tmp);
			
			// String.format("%8s", tmp).replace(' ', '0');
			
		}
		return result.toString();
	}
		
	/**
	 * Read the content of a given file and return it as an array of bytes.
	 */
	public static byte[] fileToBytes(String filename) throws Exception
	{
		FileInputStream fis = new FileInputStream(filename);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte b;
		do
		{
			b = (byte) fis.read();
			if (b != -1) bos.write(b);
		} while (b != -1);
		fis.close();
		return bos.toByteArray();
	}

	/**
	 * Write the bytes in a given byte array to a file of a given name.
	 */
	public static void bytesToFile(byte[] data, String filename) throws Exception
	{
		FileOutputStream fos = new FileOutputStream(filename);
		fos.write(data);
		fos.close();
	}

	/**
	 * Compute the md5 hash of the given array as a hex string
	 */
	public static String getMD5(byte[] ar) throws Exception
	{
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] digest = md.digest(ar);
		return bytesToHex(digest);
	}

	/**
	 * Compute the frequencies (as counts) of letters in a byte array made up of caps.
	 **/
	public static int[] getFrequencies(byte[] ar)
	{
		int[] freq = new int[26];
		for (int i = 0; i < ar.length; i++)
		{
			freq[ar[i] - 'A']++;
		}
		return freq;
	}
	public static boolean checkFrequencyVector(byte[] ar)
	{
		int[] freq=getFrequencies(ar);
		double[] check= new double[freq.length];
		int matchCount=0;
		for (int i=0; i<check.length; ++i)
		{
		    check[i] = (double) freq[i];
		    check[i]= (check[i]/ar.length)*100;
		    if(Math.floor(check[i])==Math.floor(ENGLISH[i]))
				matchCount++;
			System.out.println((Math.floor(check[i])+" check ENG "+ Math.floor(ENGLISH[i])));
		}
		
		return matchCount>10;
	}
	
	/**
	 * Compute the Index of Coincidence of the given array
	 **/
	public static double getIC(byte[] ar)
	{
		
		Random rng = new Random();
		byte[] pt = CryptoTools.clean(ar);
		int draws = ar.length;
		int success = 0;
		for (int i = 0; i < draws; i++)
		{
			int pos1 = rng.nextInt(pt.length);
			int pos2 = rng.nextInt(pt.length);
			if (pos1!=pos2 && pt[pos1] == pt[pos2]) success++;
		}
	return	(success / (double) draws); 
	
	}
	public static double getIC2(byte[] ar)
	{
		int[] frq = CryptoTools.getFrequencies(ar);
		int sum = 0;
		for (int i = 0; i < frq.length; i++)
		{
			sum += frq[i]*(frq[i]-1);
		}
		double denominator = ar.length * (ar.length - 1);
		return (sum / denominator);
	}
	public static String getXor(String x, String y) throws Exception
	{
		if(x.length()!=y.length())throw new Exception("Lenght not matching!");
		
		byte[] xb=x.getBytes();
		byte[] yb=y.getBytes();
		byte[] result = new byte[xb.length];
		for(int i=0;i<xb.length;i++)
		{
		result[i] =(byte)	(xb[i] ^ yb[i]);
		}
		return new String(result);
	}
	public static double dotProduct(byte [] a)
	{
		
		double sum = 0;
		int[] freq= getFrequencies(a);
		double[] vector = new double[freq.length];
		for (int i=0; i<freq.length; ++i)
		{
		    vector[i]= ((double)freq[i]/a.length)*100;
		 
		}
		for(int i = 0; i < vector.length; i++)
		{	
			sum += vector[i] * ENGLISH[i];
	
		}
		return sum;
	}
	public static byte[] xor(byte[] x, byte[] y) throws Exception
	{
		if (x.length != y.length) throw new Exception("Lengths Mismatch!");
		
		byte[] result = new byte[x.length];
		
		for (int i = 0; i < x.length; i++)
		{
			result[i] = (byte) (x[i] ^ y[i]);
		}
		return result; 
	}
}