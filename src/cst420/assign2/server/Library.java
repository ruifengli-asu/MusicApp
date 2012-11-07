package cst420.assign2.server;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


import java.io.*;
import java.util.*;
import java.beans.*;

/**
 * This class uses a Swing JTree, JTextField, JComboBox controls to realize a
 * GUI with a split pane. The left pane contains an expandable JTree of the
 * music library. The right pane contains components that allow viewing,
 * modifying and adding music files and their descriptions.
 * 
 * @author Ruifeng Li ASUID:1205680069
 * @version October 2012
 */
public class Library {
	public String Title, Author, Ablum;
	// Hashtable hash =null;
	MusicDescription music = null;
	Hashtable hash = null;

	public Library() {
		hash = new Hashtable();

	}

	// track.getTitle(titleJTF.getText());
	// MusicDescription mus= new MusicDescription();
	// public void addDescription(String Title, String Author, String Album){
	public void addDescription(MusicDescription music) {
		// String Title = titleJTF.getText();
		// if(hash.containsKey(Title)){
		/*
		 * if(hash.containsKey(music.musicTitle)){ String
		 * warning="title has already existed£¡"; System.out.println(warning); }
		 * else{
		 */
		// String Author = authorJTF.getText;
		// String Album = albumJTF.getText;
		// MusicDescription track = new MusicDescription();
		// track.setTitle(Title);
		// track.setAuthor(Author);
		// track.setAlbum(Album);
		// hash.put(Title,track);
		hash.put(music.musicTitle, music);
		// }
	}

	public MusicDescription getDescription(String Title) {
		// String Title = titleJTF.getText;
		if (hash.containsKey(Title)) { // search by title
			MusicDescription mus = (MusicDescription) hash.get(Title);
			return mus;
			// authorJTF.setText(mus.getAuthor);
			// albumJTF.setText(mus.getAlbum);
		} else {
			String warning = "title dosen't exist£¡";
			System.out.println(warning);
			return null;
		}
	}

	public void removeDescrption(String Title) {
		// String Title = titleJTF.getText;
		// MusicDescription mus=(MusicDescription)hash.get(Title);
		hash.remove(Title);
		// titleJTF.setText(null);
		// authorJTF.setText(null);
		// albumJTF.setText(null);

	}

	public String[] getTitleList() {
		int i = 0;
		String[] list = new String[hash.size() + 1];
		for (Enumeration tit = hash.keys(); tit.hasMoreElements();) {
			String str = tit.nextElement().toString();

			list[i] = str;
			i++;
			// System.out.println(list[i]);
			// Student stu=(Student)enm.nextElement();
		}

		return list;

	}

	public String[] getAlbumList() {
		int i = 0;
		String[] list = new String[hash.size()];
		for (Enumeration val = hash.elements(); val.hasMoreElements();) {
			MusicDescription mus = (MusicDescription) val.nextElement();
			String album = new String(mus.getAlbum());

			list[i] = album;
			i++;
			// Student stu=(Student)enm.nextElement();
		}

		return list;
	}

	public void saveDescription() {
		// MusicDescription Music = new MusicDescription(?????????);
		try {
			FileOutputStream xmlos = new FileOutputStream("Library.xml");
			XMLEncoder encoder = new XMLEncoder(xmlos);
			encoder.writeObject(hash);
			encoder.close();
			System.out
					.println("Done exporting a Library as xml to Library.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reStore() {
		try {
			FileInputStream inFileStream = new FileInputStream("Library.xml");
			XMLDecoder decoder = new XMLDecoder(inFileStream);
			hash = (Hashtable) decoder.readObject();
			System.out.println("Importing a Library as xml from Library.xml");

			decoder.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		// public Library(){}

	}

}