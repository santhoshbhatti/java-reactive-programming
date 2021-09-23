package com.piedpiper.sec05.assignment.vinsguru;


import com.piedpiper.util.Util;

public class ItemPoProcessor {
	
	public static void main(String[] args) {
		ItemOrderService orderService = new ItemOrderService();
		ItemInventoryService invService = new ItemInventoryService();
		ItemsRevenueService revService = new ItemsRevenueService();
		
		orderService.orderStream().subscribe(invService.subscribeOrderStream());
		orderService.orderStream().subscribe(revService.subscribeOrderStream());
		
		invService.inventoryStream().subscribe(Util.getDefaultSubscriber("inventory"));
		revService.revenueStream().subscribe(Util.getDefaultSubscriber("revenue"));
		
		Util.sleepSeconds(60);
	}

}
