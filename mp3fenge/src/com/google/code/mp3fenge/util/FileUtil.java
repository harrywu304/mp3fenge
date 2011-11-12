/**
 * COPYRIGHT. Harry Wu 2011. ALL RIGHTS RESERVED.
 * Project: mp3fenge
 * Author: Harry Wu <harrywu304@gmail.com>
 * Created On: Jun 19, 2011 2:23:13 PM
 *
 */
package com.google.code.mp3fenge.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
/**
 * 文件有关的工具类
 * 
 * @author whx
 */
public class FileUtil {
	
	/**
	 * 根据字节内容生成新文件
	 * @param newFile
	 * @param musicDatas
	 * @return
	 */
	public static boolean generateFile(File file, List<byte[]> datas){
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(file));
			for(byte[] data:datas){
				bos.write(data);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(bos != null){
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	/**
	 * 往现有文件中追加数据
	 * @param file
	 * @param datas
	 * @return
	 */
	public static boolean appendData(File file, byte[]... datas){
		RandomAccessFile rfile = null;
		try {
			rfile = new RandomAccessFile(file,"rw");
			rfile.seek(file.length());
			for(byte[] data:datas){
				rfile.write(data);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(rfile != null){
				try {
					rfile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}		
		return false;
	}

}
