/**
 * COPYRIGHT. Harry Wu 2011. ALL RIGHTS RESERVED.
 * Project: mp3fenge
 * Author: Harry Wu <harrywu304@gmail.com>
 * Created On: Jun 19, 2011 2:23:13 PM
 *
 */
package com.google.code.mp3fenge;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.id3.ID3v1Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.mp3fenge.util.FileUtil;
import com.google.code.mp3fenge.util.StringUtil;

/**
 * MP3 Splitter
 */
public class Mp3Fenge {
	
	/**
	 * logger
	 */
	private static Logger logger = LoggerFactory.getLogger(Mp3Fenge.class);
	
	/**
	 * the play time about each mp3 frame, unit if ms
	 */
	private static double TIME_PER_FRAME = 26.122448979591838;
	/**
	 * mp3 file
	 */
	private File mp3File;
	/**
	 * mp3 info
	 */
	private Mp3Info mp3Info;
	
	public Mp3Fenge(File mp3File){
		this.mp3File = mp3File;
	}
	
	/**
	 * get target mp3 file
	 * @return mp3 file
	 */
	public File getMp3File() {
		return mp3File;
	}

	/**
	 * set target mp3 file
	 * @param mp3File which will be split
	 */
	public void setMp3File(File mp3File) {
		this.mp3File = mp3File;
	}

	/**
	 * get info about target mp3
	 * @return
	 */
	public Mp3Info getMp3Info() {
		if(mp3Info == null){
			try {
				MP3File mp3 = new MP3File(mp3File);
				ID3v1Tag v1 = mp3.getID3v1Tag();
				String encoding = v1.getEncoding();
				MP3AudioHeader header = (MP3AudioHeader) mp3.getAudioHeader();
				mp3Info = new Mp3Info();
				mp3Info.setTitle(StringUtil.convertEncode(v1.getFirst(FieldKey.TITLE),encoding));
				mp3Info.setArtist(StringUtil.convertEncode(v1.getFirst(FieldKey.ARTIST),encoding));
				mp3Info.setAlbum(StringUtil.convertEncode(v1.getFirst(FieldKey.ALBUM),encoding));
				mp3Info.setTrackLength(header.getTrackLength());
				mp3Info.setBiteRate(header.getBitRate());
			} catch (Exception e) {
				e.printStackTrace();
			} 			
		}
		return mp3Info;
	}
	
	/**
	 * get audio data by bit rate 
	 * @param beginTime start time, unit ms
	 * @param endTime end time, unit ms
	 * @return
	 */
	private byte[] getDataByBitRate(int beginTime, int endTime){
		byte[] result = null;
		RandomAccessFile rMp3File = null;
		try{
			MP3File mp3 = new MP3File(mp3File);
			MP3AudioHeader header = (MP3AudioHeader) mp3.getAudioHeader();
			if(header.isVariableBitRate()){
				logger.error("Not support VBR mp3 now!!");
				result = null;
			} else {
				long mp3StartIndex = header.getMp3StartByte();	
				int trackLengthMs = header.getTrackLength()*1000;
				long bitRate = header.getBitRateAsNumber();
				long beginIndex = bitRate*1024/8/1000*beginTime+mp3StartIndex;
				long endIndex = beginIndex + bitRate*1024/8/1000*(endTime-beginTime);
				if(endTime > trackLengthMs){
					endIndex = mp3File.length()-1;
				}
				
				logger.debug("mp3StartIndex:{},trackLengthMs:{},bitRate:{}",new Object[]{mp3StartIndex,trackLengthMs,bitRate});
				logger.debug("beginIndex:{},endIndex:{}",new Object[]{beginIndex,endIndex});
				
				rMp3File = new RandomAccessFile (mp3File,"r");
				rMp3File.seek(beginIndex);
				int size = (int)(endIndex - beginIndex);
				result = new byte[size];
				rMp3File.read(result);
			}
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			if(rMp3File!=null){
				try {
					rMp3File.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}	
	
	/**
	 * get frame length from mp3 header
	 * @param headerStr 
	 * @return frame length
	 */
	private static int getCBRFrameLength(String headerStr){
		Pattern p = Pattern.compile("frame length:(\\d+)");
		Matcher m = p.matcher(headerStr);
		if(m.find()){
			return Integer.parseInt(m.group(1));
		}
		return 0;
	}
	
	/**
	 * get mp3 audio data by time interval
	 * @param beginTime start time, unit ms
	 * @param endTime end time, unit ms
	 * @return mp3 audio data
	 */
	public byte[] getDataByTime(int beginTime, int endTime){
		return getDataByBitRate(beginTime,endTime);
	}
	
	/**
	 * generate new mp3 fragment file by time interval
	 * @param beginTime start time, unit ms
	 * @param endTime end time, unit ms
	 * @param newMp3 new mp3 file
	 */
	public void generateNewMp3ByTime(File newMp3, int beginTime, int endTime){
		byte[] frames = getDataByTime(beginTime,endTime);
		if(frames == null || frames.length < 1){
			return;
		}
		List<byte[]> mp3datas = new ArrayList<byte[]>();
		mp3datas.add(frames);
		FileUtil.generateFile(newMp3, mp3datas);
	}
	
//	public static void main(String[] args){
//		Mp3Fenge helper = new Mp3Fenge(new File("testdata/eyes_on_me.mp3"));
//		
//		//directly cut out mp3 by time interval
//		helper.generateNewMp3ByTime(new File("testdata/e1.mp3"), 307000, 315000);
//		
//		//get mp3 data by time interval
//		byte[] e2 = helper.getDataByTime(70000, 76000);
//		List<byte[]> mp3datas = new ArrayList<byte[]>();
//		mp3datas.add(e2);
//		FileUtil.generateFile(new File("testdata/e2.mp3"), mp3datas);
//	}

}
