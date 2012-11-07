package cst420.assign2.client;


import cst420.media.*;
import javax.swing.*;

import java.io.*;
import java.net.*;

import javax.swing.tree.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.html.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

/**
 * Purpose: demonstrate use of MusicLibraryGui class for students to use as a
 * basis for solving cst420 Fall 2012 Assign1.
 * This problem provides for browsing and managing information about
 * music files. It uses a Swing JTree, JTextField, JComboBox controls to 
 * realize a GUI with a split pane. The left pane contains an expandable
 * JTree of the music library.
 * The right pane contains components that allow viewing, modifying and adding
 * music files and their descriptions.
 * @author Tim Lindquist (Tim.Linquist@asu.edu), ASU Polytechnic, Engineering
 * @version September 2012
 */
public class MusicLibraryApp extends MusicLibraryGui implements
                                                       TreeWillExpandListener,
     					               ActionListener,
					               TreeSelectionListener {

    private static final boolean debugOn = true;
    private Socket sock = null;
    private DataOutputStream os ;
    private DataInputStream  is;
    private TreadForUpload threadUpload;
    private ThreadForDownload threadDownload;
    
    
    public MusicLibraryApp(String base) {
	super(base);
	for(int i=0; i<userMenuItems.length; i++){
            for(int j=0; j<userMenuItems[i].length; j++){
                userMenuItems[i][j].addActionListener(this);
            }
	}
        tree.addTreeSelectionListener(this);
        tree.addTreeWillExpandListener(this);
	setVisible(true);
	
	

    try {

      sock = new Socket("localhost", 8888);

     // String strToSend = stdin.readLine();
     // String strReceived;
      os = new DataOutputStream( sock.getOutputStream());
      is = new DataInputStream( sock.getInputStream());
      System.out.print("Client are waiting");
  /*    int numBytesReceived;
      int bufLen = 1024;
      byte bytesReceived[] = new byte[1024];
      while (!strToSend.equalsIgnoreCase("end")){
         byte bytesToSend[] = strToSend.getBytes();
         os.write(bytesToSend,0,bytesToSend.length);
         numBytesReceived = is.read(bytesReceived,0,bufLen);
         strReceived = new String(bytesReceived,0,numBytesReceived);
         System.out.println("Received from server: "+strReceived);
         System.out.print("String to send>");
         strToSend = stdin.readLine();*/
     // }
    //  sock.close();
    } catch (Exception e) {e.printStackTrace();}
  }
	
	


    private void debug(String message) {
        if (debugOn)
            System.out.println("debug: "+message);
    }

    /**
     * create and initialize nodes in the JTree of the left pane.
     * buildInitialTree is called by MusicLibraryGui to initialize the JTree.
     * Classes that extend MusicLibraryGui should override this method to 
     * perform initialization actions specific to the extended class.
     * The default functionality is to set base as the label of root.
     * In your solution, you will probably want to initialize by deserializing
     * your library and building the tree.
     * @param root Is the root node of the tree to be initialized.
     * @param base Is the string that is the root node of the tree.
     */
    public void buildInitialTree(DefaultMutableTreeNode root, String base){
	try{
            System.out.println("buildInitialTree called by Gui constructor");
	}catch (Exception ex){
	    JOptionPane.showMessageDialog(this,"exception initial tree:"+ex);
	    ex.printStackTrace();
	}
    }

    public void initializeTree( ){
        tree.removeTreeSelectionListener(this);
        tree.removeTreeWillExpandListener(this);
	try{
            DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
            DefaultMutableTreeNode root =
                (DefaultMutableTreeNode)model.getRoot();
	    String user = System.getProperty("user.name");
            System.out.println("user name is: "+user);
	    String sourceNames[] = {user,"Alone In Iz World","HanohanoCowboy",
                      "TwinkelLittleStar","All The Greatest Hits","ComeMonday"};
	    DefaultMutableTreeNode [] nodeArray =
                                new DefaultMutableTreeNode[sourceNames.length];
	    nodeArray[0] = root;
	    root.setUserObject(user);
	    for(int i=1; i<sourceNames.length; i++){
		nodeArray[i] = new DefaultMutableTreeNode(sourceNames[i]);
		if (i==1){
                    // insert node labelled Video as the new last child of root
		    model.insertNodeInto(nodeArray[i], root,
                                         model.getChildCount(root));
                }else if (i==2){
                    // insert node labelled Musical as new last child of Video
                    model.insertNodeInto(nodeArray[2], nodeArray[1],
                                         model.getChildCount(nodeArray[1]));
                }else if (i==3){
                    //insert node labelled MammaMia as new last child of Musical
                    model.insertNodeInto(nodeArray[3], nodeArray[1],
                                         model.getChildCount(nodeArray[1]));
                }else if (i==4){
                    // insert node labelled Action as new last child of Video
                    model.insertNodeInto(nodeArray[4], root,
                                         model.getChildCount(root));
		}else if (i==5){
                    // insert node labelled TomorrowNeverDies as new last
                    // child of node labelled Action
		    model.insertNodeInto(nodeArray[5], nodeArray[4],
                                         model.getChildCount(nodeArray[4]));
		}
	    }
            // expand all the nodes in the JTree
            for(int r =0; r < tree.getRowCount(); r++){
                tree.expandRow(r);
            }
	}catch (Exception ex){
	    JOptionPane.showMessageDialog(this,"exception initial tree:"+ex);
	    ex.printStackTrace();
	}
        tree.addTreeSelectionListener(this);
        tree.addTreeWillExpandListener(this);
    }

    public void treeWillCollapse(TreeExpansionEvent tee) {
	tree.setSelectionPath(tee.getPath());
    }

    public void treeWillExpand(TreeExpansionEvent tee) {
	DefaultMutableTreeNode dmtn =
	    (DefaultMutableTreeNode)tee.getPath().getLastPathComponent();
	System.out.println("will expand node: "+dmtn.getUserObject()+
			   " whose path is: "+tee.getPath());
    }

    public void valueChanged(TreeSelectionEvent e) {
      try{
	tree.removeTreeSelectionListener(this);
	DefaultMutableTreeNode node = (DefaultMutableTreeNode)
	                                   tree.getLastSelectedPathComponent();
	String nodeLabel = (String)node.getUserObject();
        System.out.println("Selected node labelled: "+nodeLabel);
        titleJTF.setText(nodeLabel);
      }catch (Exception ex){
	  ex.printStackTrace();
      }
      tree.addTreeSelectionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
	if(e.getActionCommand().equals("Exit")) {
	    System.exit(0);
	}else if(e.getActionCommand().equals("Save")) {
	    System.out.println("Save Selected");
	}else if(e.getActionCommand().equals("Restore")) {
	    System.out.println("Restore selected, initializing tree");
            initializeTree();
	}else if(e.getActionCommand().equals("Add")) {
		String strToSend = "add$"+titleJTF.getText()+"$"+albumJTF.getText()+"$"+authorJTF.getText();
		

	         byte bytesToSend[] = strToSend.getBytes();
	         try {
				os.write(bytesToSend,0,bytesToSend.length);
				System.out.println("Client has send "+ strToSend+" to Server");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();       	        
	     }		
	    System.out.println("Add Selected");
//	    Socket sockForupload = new Socket("localhost", 8889);
	    
	    
	    JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(
                              new File(System.getProperty("user.dir")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                              "wav files", "wav");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
           System.out.println("You selected the file: " +
                              chooser.getSelectedFile().getName());
           try {
			threadUpload = new TreadForUpload(chooser.getSelectedFile());
			   threadUpload.start();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}    
        }    
	}else if(e.getActionCommand().equals("Remove")) {
	    System.out.println("Remove Selected");
            DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
            DefaultMutableTreeNode root =
                (DefaultMutableTreeNode)model.getRoot();
            clearTree(root, model);
	}else if(e.getActionCommand().equals("Play")) {
		
		
		System.out.println("Play Selected");
		String strToSend = "play$"+titleJTF.getText();
        byte bytesToSend[] = strToSend.getBytes();
        try {
			os.write(bytesToSend,0,bytesToSend.length);
			System.out.println("Client has send "+ strToSend+" to Server");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();       	        
    }	
        try {
			threadDownload = new ThreadForDownload();
			   threadDownload.start();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	    
	}
	}
    

    private void clearTree(DefaultMutableTreeNode root, DefaultTreeModel model){
        tree.removeTreeSelectionListener(this);
        tree.removeTreeWillExpandListener(this);
        try{
            DefaultMutableTreeNode next = null;
            int subs = model.getChildCount(root);
            for(int k=subs-1; k>=0; k--){
                next = (DefaultMutableTreeNode)model.getChild(root,k);
                debug("removing node labelled:"+(String)next.getUserObject());
                model.removeNodeFromParent(next);
            }
        }catch (Exception ex) {
            System.out.println("Exception while trying to clear tree:");
            ex.printStackTrace();
        }
        tree.addTreeSelectionListener(this);
        tree.addTreeWillExpandListener(this);
    }

    public static void main(String args[]) {
	try{
	    String name = "Music Library";
	    if (args.length >= 1) {
		name = args[0];
	    }
	    MusicLibraryApp ltree = new MusicLibraryApp(name);
	}catch (Exception ex){
	    ex.printStackTrace();
	}
    }
}
