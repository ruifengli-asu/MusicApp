package cst420.assign2.client;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;


class PlayWavThread extends Thread {
	   private String aTitle;
	   private MusicThread parent;
	   public PlayWavThread(String aTitle, MusicThread parent) {
	      this.parent = parent;
	      this.aTitle = aTitle;
	   }

	   public void run (){
	      int BUFFER_SIZE = 4096;
	      AudioInputStream audioStream;
	      AudioFormat audioFormat;
	      SourceDataLine sourceLine;
	      try{
	         System.out.println("Playing the wav file: " +aTitle);
	         audioStream = AudioSystem.getAudioInputStream(new File(aTitle+".wav"));
	         audioFormat = audioStream.getFormat();
	         DataLine.Info i = new DataLine.Info(SourceDataLine.class, audioFormat);
	         sourceLine = (SourceDataLine) AudioSystem.getLine(i);
	         sourceLine.open(audioFormat);
	         sourceLine.start();
	         int nBytesRead = 0;
	         byte[] abData = new byte[BUFFER_SIZE];
	         while(nBytesRead != -1){
	            try{
	               if(parent.sezToStop()){
	                  System.out.println("Interrupted playing: "+aTitle);
	                  break;
	               }
	               nBytesRead = audioStream.read(abData, 0, abData.length);
	               if (nBytesRead >= 0) {
	                  @SuppressWarnings("unused")
	                     int nBytesWritten = sourceLine.write(abData,0,nBytesRead);
	               }
	            } catch (Exception e){
	               e.printStackTrace();
	            }
	         }
	         sourceLine.drain();
	         sourceLine.close();
	         audioStream.close();
	      }catch (Exception e){
	         e.printStackTrace();
	      }
	   }
	}
