package cst420.assign2.client;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;



class TreadForUpload extends Thread {
	   private File aFile;
	   private Socket sock = null; 
	   public TreadForUpload(File aFile) throws IOException, IOException {
		   
	      this.aFile = aFile;
	   }
	   public void run (){
	      try{
	    	  sock = new Socket("localhost", 8886);
	         Thread.sleep(1000); //wait 1000 milliseconds before starting the copy.
	         System.out.println("Opening the file: "+aFile.getName());
	         String fileName = aFile.getName();
	         String extension = fileName.substring(fileName.length()-4,
	                                               fileName.length());
	         DataOutputStream fos = new DataOutputStream(sock.getOutputStream());
	         System.out.println("Writing the file: tmp"+extension);
	         FileInputStream fis = new FileInputStream(aFile);
	         byte[] buf = new byte[4096];
	         while(true) {
	            int n = fis.read(buf);
	            if( n < 0 ) break;
	            fos.write(buf,0,n);
	            Thread.sleep(2000); //wait 2 seconds.
	         }
	         fis.close();
	         fos.close();
	         System.out.println("Uploading Completed");
	      }catch (Exception e){
	         e.printStackTrace();
	      }
	   }
	}
