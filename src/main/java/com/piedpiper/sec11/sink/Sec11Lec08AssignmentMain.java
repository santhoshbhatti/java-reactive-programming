package com.piedpiper.sec11.sink;

import com.piedpiper.util.SlackMember;
import com.piedpiper.util.SlackRoom;
import com.piedpiper.util.Util;

public class Sec11Lec08AssignmentMain {
	
	public static void main(String[] args) {
		SlackRoom room = new SlackRoom("reactor");
		
		SlackMember sam = new SlackMember("sam");
		SlackMember mike = new SlackMember("mike");
		
		room.joinRoom(sam);
		room.joinRoom(mike);
		
		sam.says("Hello reactor....");
		Util.sleepSeconds(4);
		mike.says("Hello!!!!");
		sam.says("just wanted to say Hi!!!!!");
		
		var bob = new SlackMember("bob");
		room.joinRoom(bob);
		
		bob.says("Hey guys just joined");
		
	}

}
