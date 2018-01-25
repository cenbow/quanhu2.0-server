package com.yryz.quanhu.support.illegalwords.common;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Test{
//    public static void main(String[] args) throws FileNotFoundException{
//        Scanner scanner = new Scanner(new FileInputStream("E:\\a.txt")) ;
//        while(scanner.hasNext()){
//            String world = scanner.next() ;
//            System.out.println(world);
//        }
//         
//    }
//    
	
	 
	public void zhuan(String text) {
		 
	
		try {
		 
			BufferedReader	reader = new BufferedReader(new FileReader("test.txt"));
	
		PrintStream writer = new PrintStream(new FileOutputStream("test_new.txt"));
		String buf;
		while ((buf=reader.readLine()) != null) {
		    if (buf.isEmpty()) {continue;}
		    if (buf.matches("[/]+.*")) {
		        buf = buf.replaceAll("[/]+(.*)", "$1"); //去掉前面的/
		    }
		    buf = buf.replaceAll("\\s+(.*)", "$1"); //去掉前面的空格
		    writer.println(buf);
		}
		reader.close();
		writer.flush();
		writer.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
	 
	 
	}
	
	
	  public static void main(String[] args) throws FileNotFoundException{
		  
		  try {
				 
				BufferedReader	reader = new BufferedReader(new FileReader("C:\\abc\\test.txt"));
		
			PrintStream writer = new PrintStream(new FileOutputStream("C:\\abc\\test_new.txt"));
			String buf;
			String dest = "";
			while ((buf=reader.readLine()) != null) {
			    if (buf.isEmpty()) {continue;}
//			    if (buf.matches("[/]+.*")) {
//			        buf = buf.replaceAll("[/]+(.*)", "$1"); //去掉前面的/
//			    }
//			    buf = buf.replaceAll("\\s+(.*)", "$1"); //去掉前面的空格
//			    buf = buf.replaceAll("\\n",""); //去掉前面的空格
			    
			    Pattern p = Pattern.compile("\\s*|\\t|\\r|\\n");
				Matcher m = p.matcher(buf);
				dest = m.replaceAll("");
			    writer.println(dest);
			}
			//str.replace("\\r\\n", "");
			reader.close();
			writer.flush();
			writer.close();
			} catch (Exception e) {

				e.printStackTrace();
			}
		 
		  
		  
		  
	  }
	
}