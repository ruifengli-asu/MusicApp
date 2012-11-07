package cst420.assign2.server;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadForUpload extends Thread{
private Socket conn;
private String musicTitle;


public ThreadForUpload (Socket sock, String musicTitle) {
  this.conn = sock;
  this.musicTitle = musicTitle;
}
	/**
	 * @param args
	 */
public void run (){
    try{
    // Thread.sleep(1000); //wait 1000 milliseconds before starting the copy.
     //  System.out.println("Opening the file: "+aFile.getName());
     //  String fileName = aFile.getName();
     //  String extension = fileName.substring(fileName.length()-4,
     //                                        fileName.length());
       File dir = new File (System.getProperty("user.dir") + "/DataServer/");
       if(!dir.exists()){
    	   dir.mkdir();
    	   System.out.println("DataServer path has been Created: " 
    			   + System.getProperty("user.dir") + "/DataServer/");
       }
       String fileName = new String(System.getProperty("user.dir") + "/DataServer/" + musicTitle + ".wav");
       FileOutputStream fos = new FileOutputStream(fileName);
       DataInputStream fis = new DataInputStream(conn.getInputStream());
       byte uploadInput[] = new byte[4069]; // up to 1024 bytes in a message.
       
      
      // byte[] buf = new byte[4096];
       while(true) {
    	   int numr = fis.read(uploadInput);
    	   System.out.println("system has read the file.");
          if( numr < 0 ) break;
          fos.write(uploadInput,0,numr);
       }
      
       fos.close(); 
       fis.close();

       System.out.println("Completed the file copy.");
    }catch (Exception e){
       e.printStackTrace();
    }
 }


}



