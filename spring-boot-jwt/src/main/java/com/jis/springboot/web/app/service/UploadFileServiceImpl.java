package com.jis.springboot.web.app.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServiceImpl implements IUploadFileService {

	private static final String UPLOADS_FOLDER = "uploads";

	@Override
	public Resource load(String filename) throws MalformedURLException {
		Path pathFoto = getPath(filename);
		Resource foto = new UrlResource(pathFoto.toUri());

		if (!foto.exists() || !foto.isReadable())
			throw new RuntimeException("No se puede cargar la imagen");

		return foto;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		String uniqueFileName = UUID.randomUUID().toString().concat("-").concat(file.getOriginalFilename());
		Path rootAbsolutePath = getPath(uniqueFileName);
		Files.copy(file.getInputStream(), rootAbsolutePath);
		return uniqueFileName;
	}

	@Override
	public boolean delete(String filename) {
		Path rootPath = getPath(filename);
		File fotoFile = rootPath.toFile();
		if (fotoFile.exists() && fotoFile.canRead()) {
			return fotoFile.delete();
		}
		return false;
	}

	public Path getPath(String filename) {
		return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
	}

	@Override
	public void borrarTodos() {
		FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile());
		
	}

	@Override
	public void init() throws IOException {

		Files.createDirectory(Paths.get(UPLOADS_FOLDER));
	}

}
