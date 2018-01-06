package com.taotao.controller;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.commom.utils.JsonUtils;
import com.taotao.utils.FastDFSClient;

/**
 * 图片上传管理
 * @author Administrator
 *
 */
@Controller
public class PictureController {
	
	//图片服务器的地址
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;

	@RequestMapping(value="/pic/upload")
	@ResponseBody
	public String upload(MultipartFile uploadFile){
		Map result = new HashMap<>();
		//接受上传的文件
		String originalName = uploadFile.getOriginalFilename();
		//上传文件，指定扩展名
		String extName = originalName.substring(originalName.lastIndexOf(".")+1);
		try {
			//使用封装好的FastDFSClient去上传文件
			FastDFSClient client = new FastDFSClient("classpath:resource/client.conf");
			String url = client.uploadFile(uploadFile.getBytes(), extName);
			url = IMAGE_SERVER_URL+url;
			System.out.println(url);
			result.put("error", 0);
			result.put("url", url);
		} catch (Exception e) {
			result.put("error", 1);
			result.put("message", "上传出错！");
			e.printStackTrace();
		}
		//响应URL,此处转为Json格式可以解决兼容性问题
		return JsonUtils.objectToJson(result);
	}
}
