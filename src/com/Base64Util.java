package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Util {

	public static String fileTobase64(File file) throws IOException {
		InputStream in = null;
		byte[] data = null;
		try {
			in = new FileInputStream(file);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}

	public static boolean base64ToFile(String coder, File file) {
		String encoder = coder.substring(coder.indexOf(",") + 1, coder.length());
		if (encoder == null || encoder == "") {
			return false;
		} else {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				byte[] data = decoder.decodeBuffer(encoder);
				for (int i = 0; i < data.length; ++i) {
					if (data[i] < 0) {
						data[i] += 256;
					}
				}
				OutputStream out = new FileOutputStream(file);
				out.write(data);
				out.flush();
				out.close();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static void main(String[] args) throws IOException {

//		String filePath = "C:\\001.jpg";
//		File file = new File(filePath);
//		String imageCode = fileTobase64(file);
//		//System.out.println(imageCode);
//
//		String filePath2 = "C:\\Users\\admin\\002.jpg";
//		File file2 = new File(filePath2);
//		boolean result = base64ToFile(imageCode, file2);
//		System.out.println(result);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssms");
		String str = sdf.format(date);
		System.out.println(str);
	}

}
