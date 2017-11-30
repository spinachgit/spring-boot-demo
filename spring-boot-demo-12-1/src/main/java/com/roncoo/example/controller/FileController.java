package com.roncoo.example.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/file")
public class FileController {
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	@Value("${spring.http.multipart.location}")
	private String uploadPath;

	@RequestMapping(value = "upload")
	@ResponseBody
	public String upload(@RequestParam("roncooFile") MultipartFile file) {
		if (file.isEmpty()) {
			return "文件为空";
		}

		// 获取文件名
		String fileName = file.getOriginalFilename();
		logger.info("上传的文件名为：" + fileName);

		// 获取文件的后缀名
		// String suffixName = fileName.substring(fileName.lastIndexOf("."));
		// logger.info("上传的后缀名为：" + suffixName);
		// 解决中文问题，liunx下中文路径，图片显示问题
		// fileName = UUID.randomUUID() + suffixName;

		File dest = new File(uploadPath + "/" + fileName);

		// 检测是否存在目录
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		} else if (dest.exists()) {
			return "文件已经存在！";
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

	@RequestMapping("download")
	public ResponseEntity<?> download(String filepath) throws IOException, URISyntaxException {
		File file = new File(filepath);
		HttpHeaders headers = new HttpHeaders();
		if (!file.exists()) {
			URI location = new URI(filepath);
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.setLocation(location);
			responseHeaders.set("MyResponseHeader", "MyValue");
			return new ResponseEntity<String>("文件路径不正确！", responseHeaders, HttpStatus.NOT_FOUND);
			// return ResponseEntity.noContent().build();
			// return ResponseEntity.notFound().build();
		}
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", file.getName());
		byte[] bytes = FileUtils.readFileToByteArray(file);
		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);

	}
}
