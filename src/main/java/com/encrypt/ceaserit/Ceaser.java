package com.encrypt.ceaserit;



public class Ceaser {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		System.out.print(decrypt(5,  (encrypt(5, "enter text here"))));



	}
	

	public static String decrypt(int key, String input )throws Exception  {
		byte[] ct = input.getBytes();
		ct = CryptoTools.clean(ct);

		byte[] decrypt_array = new byte[ct.length];
		
		for (int i = 0; i < ct.length; i++)
		{
			int tmp = (ct[i] - 'A' - key) % 26;
			if (tmp < 0) tmp+= 26;
			decrypt_array[i] = (byte) (tmp + 'A');
		}

		
		return new String(decrypt_array);
	}

	
	public static String encrypt(int key, String input) throws Exception {
		
		byte[] pt = input.getBytes();
		
		pt = CryptoTools.clean(pt);

		byte[] ct = new byte[pt.length];
		for (int i = 0; i < pt.length; i++)
		{
			ct[i] = (byte) ((pt[i] - 'A' + key) % 26 + 'A');
		}

		return new String(ct);

	}
	
	
	

}
