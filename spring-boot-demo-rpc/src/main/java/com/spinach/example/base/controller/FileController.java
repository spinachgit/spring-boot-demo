package com.spinach.example.base.controller;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spinach.example.util.tools.DateUtil;

@Controller
@RequestMapping(value = "/file")
public class FileController {
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	// 文件上传路径
	@Value("${file.path.upload}")
	private String uploadPath;

	@RequestMapping(value = "upload")
	@ResponseBody
	public String upload(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return "文件为空";
		}

		// 获取文件名
		String fileName = file.getOriginalFilename();
		logger.info("上传的文件名为：" + fileName);

		// 获取文件的后缀名
		String suffixName = fileName.substring(fileName.lastIndexOf("."));
		logger.info("上传的后缀名为：" + suffixName);

		// 解决中文问题，liunx下中文路径，图片显示问题
		//fileName = UUID.randomUUID() + suffixName;
		fileName = DateUtil.getCurDate("YYYYMM")+fileName+suffixName;
		
		File dest = new File(uploadPath + fileName);
		// 检测是否存在目录
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}
		try {
			file.transferTo(dest);
			return "上传成功";
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "上传失败";
	}

}
