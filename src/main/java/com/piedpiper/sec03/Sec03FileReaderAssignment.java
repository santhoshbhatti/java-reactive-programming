package com.piedpiper.sec03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Sec03FileReaderAssignment {
	
	public static void main(String[] args) {
		try(FileReader reader = new FileReader("/Users/santhoshbhatti/redis-benchmark/java-reactive-programming/file1.txt");
				BufferedReader br = new BufferedReader(reader);){
			
			Flux.generate(() -> br, (freader,sink) -> {
				try {
					String line = freader.readLine();
					if(line != null) {
						sink.next(line);
					}else {
						sink.complete();
					}
				} catch (IOException e) {
					sink.error(e);
				}
				
				return freader;
			}).log().subscribe(Util.getDefaultSubscriber());
			
		}catch(Exception e) {
			
		}
	}

}
