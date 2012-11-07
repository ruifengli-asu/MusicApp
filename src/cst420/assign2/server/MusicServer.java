package cst420.assign2.server;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class MusicServer {
	public static void main (String args[]) {
	    Socket sock;
	    int id=0;
	    try {
	      int portNo = 8888;
	      ServerSocket serv = new ServerSocket(portNo);
	      while (true) {
	        System.out.println("Echo server waiting for connects on port "
	                            +portNo);
	        sock = serv.accept();
	        System.out.println("Echo server connected to client: "+id);
	        ServeAClient myServerThread = new ServeAClient(sock,id++);
	        myServerThread.start();
	      }
	    } catch(Exception e) {e.printStackTrace();}
	  }
}

class ServeAClient extends Thread {
	 private Socket conn;
	  private int id; 

	  public ServeAClient (Socket sock, int id) {
	    this.conn = sock;
	    this.id = id;
	  }
	
	   public void run() {
		      try {
		         DataOutputStream outSock = new DataOutputStream(conn.getOutputStream());
		         DataInputStream inSock = new DataInputStream(conn.getInputStream());
		         byte clientInput[] = new byte[1024]; // up to 1024 bytes in a message.
		         int numr = inSock.read(clientInput,0,1024);
		         while (numr != -1) {
		            //System.out.println("read "+numr+" bytes");
		            String clientString = new String(clientInput,0,numr);
		            System.out.println("read from client: "+id+" the string: "
		                               +clientString);
		       /*		            
			    outSock.write(clientInput,0,numr);*/
		         //   numr = inSock.read(clientInput,0,1024);
		            String delims = "[$]";
		            String[] tokens = clientString.split(delims);		            
		            for (int i = 0; i < tokens.length; i++)
		                System.out.println(tokens[i]);
		            
		            if (tokens[0].equalsIgnoreCase("add")){
		            	Socket sockForUpload;
		            	//ServerSocket serv = new ServerSocket(8889);
		            	//sockForUpload = serv.accept();
		            	try {
		          	      int portNo = 8886;
		          	      ServerSocket serv = new ServerSocket(portNo);
		          	
		          	        System.out.println("Echo server waiting for uploadFile at  "
		          	                            +portNo);
		          	      sockForUpload = serv.accept();
		          	        System.out.println("Echo server connected to client for UpLoading");
		          	        String musicTitle = new String(tokens[1]);
		          	   	ThreadForUpload threadUpload = new ThreadForUpload(sockForUpload,musicTitle);
		    	        threadUpload.start();
		          	    serv.close();
		          	    } catch(Exception e) {e.printStackTrace();}
		            	
		          }else if(tokens[0].equalsIgnoreCase("play")){
		        	  Socket sockForDownload;		        	  
		        	  int portNo = 8884;
		        	  ServerSocket serv = new ServerSocket(portNo);
		        	  System.out.println("Echo server waiting for downloadFile at  "
	                            +portNo);
		        	  sockForDownload = serv.accept();
		        	  System.out.println("Echo server connected to client for DownLoading");
		        	  String musicTitle = new String();
		        	  musicTitle = tokens[1];
		        	  TreadForDownload threadDownload = new TreadForDownload(sockForDownload,musicTitle);
		        	  threadDownload.start();
		        	  
		        	  
		          }
		            
		            
		            
		            
		            
		            
		            
		            
		            
		            
		            
		             numr = inSock.read(clientInput,0,1024);
		         }
		         inSock.close();
		         outSock.close();
		         conn.close();
		      } catch (IOException e) {
		         System.out.println("Can't get I/O for the connection.");
		      }
		   }
	
}
