package com.piedpiper.sec06.threadandscheduling;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

public class RaceConditionDemo {
	
	public static void main(String[] args) {
		var list = new ArrayList<Integer>();
		var latch = new CountDownLatch(10);
		for(int k = 0; k < 10; k++) {
			final int thNum = k;
			new Thread(() -> {
				for(int j = 0;j < 100;j++) {
					list.add((j*thNum));
				}
				latch.countDown();
			}).start();
		}
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(list.size());
		list.sort(Comparator.comparingInt(a -> a));
		var strList = list.stream().map(a -> a+"").collect(Collectors.toList());
		String listContent = String.join("\r\n", strList);
		System.out.println(listContent);
		
		
	}

}
