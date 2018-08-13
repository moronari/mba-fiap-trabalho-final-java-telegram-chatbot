package br.com.fiap.helpers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import br.com.fiap.entity.Cliente;

public class FileHelper {

	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public static Optional<String> findFile(String fileName) {
		try {
			Optional<Path> filePath = Files.walk(Paths.get("./"))
				.filter(f -> f.getFileName().equals(fileName))
				.findFirst();
			
			return Optional.of(filePath.get().getFileName().toString());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		return Optional.empty();
	}
	
	public static <T> void saveObject(String path, T obj) throws Exception {
		try(
		    FileOutputStream fout = new FileOutputStream(path);
		    ObjectOutputStream oos = new ObjectOutputStream(fout);
		){
		    oos.writeObject(obj);
		} catch (Exception ex) {
		    throw new Exception("Falha ao salvar objecto");
		}
	}
	
	public static Optional<Object> loadObject(String path) {
		try(
			FileInputStream fin = new FileInputStream(path);
			ObjectInputStream oos = new ObjectInputStream(fin);
		){
    		return Optional.of(oos.readObject());
		} catch (Exception ex) {
		    System.out.println(ex.getMessage());
		}
		
		return Optional.empty();
	}
	
}
