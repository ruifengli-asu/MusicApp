package cst420.assign2.server;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

/**
 * This class uses a Swing JTree, JTextField, JComboBox controls to realize a
 * GUI with a split pane. The left pane contains an expandable JTree of the
 * music library. The right pane contains components that allow viewing,
 * modifying and adding music files and their descriptions.
 * 
 * @author Ruifeng Li ASUID:1205680069
 * @version October 2012
 */

public class MusicDescription {

	public String musicTitle, musicAuthor, musicAlbum;

	// Hashtable track = null;

	public MusicDescription() {
		// track=new Hashtable();
	}

	public MusicDescription(String title, String author, String album) {
		this.musicTitle = title;
		this.musicAuthor = author;
		this.musicAlbum = album;

	}

	public String getTitle() {
		return musicTitle;
	}

	public void setTitle(String title) {
		musicTitle = title;
	}

	public String getAuthor() {
		return musicAuthor;
	}

	public void setAuthor(String author) {
		musicAuthor = author;
	}

	public String getAlbum() {
		return musicAlbum;
	}

	public void setAlbum(String album) {
		musicAlbum = album;
	}

}
