package com.blogappapis.services.implimentation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blogappapis.services.FileServices;

@Service
public class FileServicesImpl implements FileServices {

	// Impl for get image from client
	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {

		// File Name
		String name = file.getOriginalFilename();

		// generate random ID for file name;
		String randomId = UUID.randomUUID().toString();
		String fileName = randomId.concat(name.substring(name.lastIndexOf(".")));

		// FullPath
		String filePath = path + File.separator + fileName;

		// folder creation in system
		File f = new File(path);

		// Condition for creating folder
		if (!f.exists()) {
			// create folder
			f.mkdir();
		}

		// Files Copy
		// file.getInoutStram() -> This is source and it get from url.
		// Paths.get(filePath) -> This is destination where file will upload.

		Files.copy(file.getInputStream(), Paths.get(filePath));

		return fileName;
	}

	// impl for back response for client
	@Override
	public InputStream getImage(String path, String fileName) throws FileNotFoundException {
		
		String fullPath = path+ File.separator +fileName;
		
		InputStream is = new FileInputStream(fullPath);
		
		return is;
	}

}
