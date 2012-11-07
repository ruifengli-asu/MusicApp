package cst420.assign2.client;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.Socket;



public class ThreadForDownload extends Thread{
private	ThreadForDownload threadDownload = null;	
private Socket conn;



public ThreadForDownload () {
 
}
	/**
	 * @param args
	 */
public void run (){
    try{
    	 conn = new Socket("localhost", 8884);
     Thread.sleep(1000); //wait 1000 milliseconds before starting the copy.
     //  System.out.println("Opening the file: "+aFile.getName());
     //  String fileName = aFile.getName();
     //  String extension = fileName.substring(fileName.length()-4,
     //                                        fileName.length());
     File dir = new File (System.getProperty("user.dir") + "/DataClient/");
     if(!dir.exists()){
  	   dir.mkdir();
  	   System.out.println("DataClient path has been Created: " 
  			   + System.getProperty("user.dir") + "/DataClient/");
     }
     String fileName = new String(System.getProperty("user.dir") + "/DataClient/" + "temp.wav");
    
       FileOutputStream fos = new FileOutputStream(fileName);
       DataInputStream fis = new DataInputStream(conn.getInputStream());
       byte downloadInput[] = new byte[4069]; // up to 1024 bytes in a message.
       
      
      // byte[] buf = new byte[4096];
       while(true) {
    	   int numr = fis.read(downloadInput);
    	   System.out.println("system has read the file.");
    	   if( numr < 0 ) break;
           fos.write(downloadInput,0,numr);
       }
       
       fos.close(); 
       fis.close();
       
       System.out.println("Completed the file copy.");
    }catch (Exception e){
       e.printStackTrace();
    }
 }


}



