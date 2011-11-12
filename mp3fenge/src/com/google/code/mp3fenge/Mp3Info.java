/**
 * COPYRIGHT. Harry Wu 2011. ALL RIGHTS RESERVED.
 * Project: mp3fenge
 * Author: Harry Wu <harrywu304@gmail.com>
 * Created On: Jun 19, 2011 2:23:13 PM
 *
 */
package com.google.code.mp3fenge;

/**
 * mp3 meta info bean
 */
public class Mp3Info {
	/**
	 * title
	 */
	private String title;
	/**
	 * author
	 */
	private String artist;
	/**
	 * album
	 */
	private String album;
	/**
	 * track length
	 */
	private int trackLength;
	/**
	 * bit rate
	 */
	private String biteRate;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public int getTrackLength() {
		return trackLength;
	}
	public void setTrackLength(int trackLength) {
		this.trackLength = trackLength;
	}
	public String getTrackLengthAsString(){
		return (trackLength/60)+":"+(trackLength%60);
	}
	public String getBiteRate() {
		return biteRate;
	}
	public void setBiteRate(String biteRate) {
		this.biteRate = biteRate;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("title:"+title+",");
		sb.append("artist:"+artist+",");
		sb.append("album:"+album+",");
		sb.append("trackLength:"+trackLength);
		sb.append("biteRate:"+biteRate);
		return sb.toString();
	}
}
