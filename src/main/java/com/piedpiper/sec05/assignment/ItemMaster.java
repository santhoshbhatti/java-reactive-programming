package com.piedpiper.sec05.assignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemMaster {
	
	private static ItemMaster itemMaster = null;
	
	private final Map<String,Item> itemDB;
	
	private ItemMaster() {
		itemDB = new HashMap<>();
	}
	
	public static ItemMaster getItemMaster() {
		if(itemMaster == null) {
			itemMaster = new ItemMaster();
			for(int k = 0;k < 100; k++) {
				Item item = new Item();
				itemMaster.itemDB.put(item.getItemId(), item);
			}
			
		}
		
		return itemMaster;
	}

	public Map<String, Item> getItemDB() {
		return itemDB;
	}
	
	public List<String> getValidItemSSN(){
		return new ArrayList<>(itemDB.keySet());
	}
	
	

}
