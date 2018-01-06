package com.taotao.upload;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

public class TestUpLoadFile {

	
	@Test
	public void testUpLoad() throws Exception{
		//导入jar包
		//添加tracker客户端的引用
		//加载配置文件
		ClientGlobal.init("F:/eclipsework/taotao-manager-web/src/main/resources/resource/client.conf");
		//创建一个TrackerClient对象
		TrackerClient trackerClient = new TrackerClient();
		//创建一个StorageServer引用
		TrackerServer trackerServer = trackerClient.getConnection();		
		//使用TrackerClient和StorageServer创建StorageClient对象
		StorageServer storageServer = null;
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		//使用StorageClient上传图片
		String[] strings = storageClient.upload_file("C:/Users/Administrator/Desktop/QQ图片20170616173014.jpg", "jpg", null);
		for(String s:strings){
			System.out.println(s);
		}
		//返回的String数组String[0]表示组名,string[1]表示路径,访问时使用【域名+string[0]+string[1]就可以访问到图片】
		
	}
	
}
