package com.jzhx.fast;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import com.jzhx.common.utils.FastDFSClient;

public class FastDfsTest {
	@Test
	public void testUpload() throws Exception{
		//创建 一个配置文件 内容就是tracker服务器地址
		//使用全局对象来加载配置文件
		ClientGlobal.init("D:/学习软件/java/template-mars2/e3-manager-web/src/main/resources/conf/client.conf");
		//创建一个TrackerClient对象
		TrackerClient trackerClient = new TrackerClient();
		//通过TrackerClient来获得一个TrackerServer对象
		TrackerServer trackerServer = trackerClient.getConnection();
		//创建一个StorageServer的引用 可以是null
		StorageServer storageServer = null; 
		//创建一个StorageClient，参数需要TrackerServer和StorageServer
		StorageClient storageClient = new StorageClient(trackerServer,storageServer);
		//使用StorageClient来上传文件
		String[] strings = storageClient.upload_file("D:/picture/1.jpg", "jpg", null);
		for (String string : strings) {
			System.out.println(string);
		}
	}
	@Test
	public void testFastDfsClient() throws Exception{
		FastDFSClient fastDFSClient = new FastDFSClient("D:/学习软件/java/template-mars2/e3-manager-web/src/main/resources/conf/client.conf");
		String string = fastDFSClient.uploadFile("D:/picture/2.jpg");
		System.out.println(string);
	}

}
