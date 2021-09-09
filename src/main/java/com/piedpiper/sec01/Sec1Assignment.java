package com.piedpiper.sec01;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Supplier;

import com.piedpiper.util.Util;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;


public class Sec1Assignment {
	public static final Path PATH = Paths.get("src/main/resources");
	public static void main(String[] args) {
		
		readFile("sometxt.txt").subscribeOn(Schedulers.boundedElastic())
		.subscribe(Util.onNext(),Util.onError(),Util.onComplete());
		
		Util.sleepSeconds(2);
		
		var rmono = writeToFile("/Users/santhoshbhatti/mytext.txt", "Hello in runnable mono");
		rmono.subscribeOn(Schedulers.boundedElastic()).
		subscribe(Util.onNext(),Util.onError(),Util.onComplete());
		Util.sleepSeconds(2);
		
		deleteFile("/Users/santhoshbhatti/mytext.txt").subscribe(Util.onNext(),Util.onError(),Util.onComplete());
		
	}
	
	public static Mono<String> readFile(String fileName) {
		return Mono.fromSupplier(fileRead());
	}

	private static Supplier<? extends String> fileRead() {
		return () -> {
			String fileContents=null;
			try {
				fileContents = Files.readString(PATH.resolve("sometxt.txt"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return fileContents;
		};
	}
	
	public static Mono<Void> writeToFile(String fileName,String txt) {
		Mono<Void> fwm = Mono.fromRunnable(writeToFileRunnable(fileName,txt));
		return fwm;
	}
	
	private static Runnable writeToFileRunnable(String fileName,String txt) {
		return () ->{
			try {
				Files.writeString(PATH.resolve(fileName), txt);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException("error writing to file",e);
			}
		};
	}
	private static Mono<Void> deleteFile(String fileName){
		return Mono.fromRunnable(deleteFileRunnable(fileName));
	}
	private static Runnable deleteFileRunnable(String fileName) {
		return () ->{
			try {
				Files.delete(PATH.resolve(fileName));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException("error deleting file",e);
			}
		};
	}

}
