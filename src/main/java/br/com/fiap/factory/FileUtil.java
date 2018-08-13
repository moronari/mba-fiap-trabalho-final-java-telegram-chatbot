package br.com.fiap.factory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	
	public static void write(String fileName, String content)
	{
		try(
			FileWriter fw = new FileWriter(fileName);
			PrintWriter out = new PrintWriter(fw);
		){
			out.print(content);
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
		}
	}
	
	public static List<String> readLines(String fileName)
	{
		String line = null;
		List<String> lines = new ArrayList<>();
		try(
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
		){
			while ((line = br.readLine()) != null){
				lines.add(line);
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		return lines;
	}
	
	public static void remove()
	{
		
	}
	
}
