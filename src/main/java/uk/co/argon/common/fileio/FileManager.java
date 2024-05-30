package uk.co.argon.common.fileio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileManager {

	private static FileManager instance;
	
	private FileManager() {}
	
	public static FileManager getInstance() {
		if(instance == null) {
			synchronized (FileManager.class) {
				if(instance == null)
					instance = new FileManager();
			}
		}
		return instance;
	}
	
	public InputStream fileToInputStream(String filepath, String filename) throws FileNotFoundException {
		return (InputStream) new FileInputStream(new File(filepath + filename));
	}
	
	public String fileToString(String filepath, String filename) throws IOException {
		return Files.readString(Paths.get(filepath + filename), StandardCharsets.UTF_8);
	}
	
	public List<String> fileToList(String filepath, String filename) throws IOException {
		return (ArrayList<String>) Files.lines(Paths.get(filepath + filename)).collect(Collectors.toList());
	}
	
	public void writeToFile(String data, String filepath, String filename, boolean isNewFile) throws IOException {
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(filepath + filename, isNewFile))) {
			bw.write(data);
		}
	}
}
