package com.blogappapis.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileServices {

	// method for uploading file
	String uploadImage(String path, MultipartFile file) throws IOException;

	// method for file serving to client
	InputStream getImage(String path, String fileName) throws FileNotFoundException;

}
