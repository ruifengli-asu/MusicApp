package cst420.assign2.server;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

class TreadForDownload extends Thread {
	   private String title;
	   private Socket sock = null;
	   public TreadForDownload(Socket sock, String title)  {
		  this.sock = sock;
	      this.title = title;
	   }
	   public void run (){
	      try{
	    	 String fileName = new String(System.getProperty("user.dir") + "/DataServer/" + title + ".wav");
	    	 Thread.sleep(1000); //wait 1000 milliseconds before starting the copy.
	         System.out.println("Opening the file: " + title + ".wav");
	         //String fileName = aFile.getName();
	         //String extension = fileName.substring(fileName.length()-4,
	         //                                      fileName.length());
	         DataOutputStream fos = new DataOutputStream(sock.getOutputStream());
	         //System.out.println("Writing the file: tmp" + );
	         FileInputStream fis = new FileInputStream(fileName);
	         byte[] buff = new byte[4096];
	         while(true) {
	            int n = fis.read(buff);
	            if( n < 0 ) break;
	            fos.write(buff,0,n);
	         }
	         fis.close();
	         fos.close();
	         System.out.println("Downloading Completed ");
	      }catch (Exception e){
	         e.printStackTrace();
	      }
	   }
	}
