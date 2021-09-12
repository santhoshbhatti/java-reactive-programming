package com.piedpiper.sec03;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

public class Sec02TutorsAssignmentSolution {
	

	public Flux<String> lineReader(Path path) {
		return Flux.generate(getReader(path), getProducer(), close());
	}
	private Consumer<BufferedReader> close() {
		return (reader) -> {
			try {
				System.out.println("closed");
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	}
	private BiFunction<BufferedReader,SynchronousSink<String>,BufferedReader> getProducer() {
		
		return (reader,sink) ->{
			String line = null; 
			try {
			line = reader.readLine();
			if(line != null) {
				sink.next(line);
			}else {
				sink.complete();
			}
			}catch(IOException e) {
				sink.error(e);
			}
			return reader;
		};
	}
	public Callable<BufferedReader> getReader(Path path){
		
		return () -> Files.newBufferedReader(path);
	}
	
	public static void main(String[] args) {
		Sec02TutorsAssignmentSolution s = new Sec02TutorsAssignmentSolution();
		
		String filePath = "/Users/santhoshbhatti/redis-benchmark/java-reactive-programming/file1.txt";
		s.lineReader(Paths.get(filePath))
		.log()
		.subscribe(Util.onNext(),Util.onError(),Util.onComplete());
	}
}