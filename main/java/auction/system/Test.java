package auction.system;

import java.util.ArrayList;

import auction.system.base.Item;
import auction.system.base.util.Strings;
import auction.user.buyer.Buyer;
import auction.user.creditor.Creditor;
import auction.user.seller.Seller;
import auction.user.seller.listitem.ListItemEvent;
import auction.user.seller.listitem.ListItemEvent.ListingType;

public class Test {

	public static void main(String[] args) {
		long listingDuration = 10;	//seconds
		long pauseDuration = 1500;		//seconds
		
		Seller seller = new Seller("Steven");
		Creditor creditorSmallItems = new Creditor("SMALL ITEMS Credit, LLC");
		Creditor creditorLargeItems = new Creditor("Large Items Brokerage, INC");
		Controller.getInstance().getListedItemsPublisher().listItem(
			seller, 
			new Item(Item.Department.ELECTRONICS, "EA5200", "A used Linksys Wifi Router"), 
			ListingType.BUY_NOW, 
			10.00, 
			listingDuration,
			creditorSmallItems
		);
		Controller.getInstance().getListedItemsPublisher().listItem(
			seller, 
			new Item(Item.Department.ELECTRONICS, "SamsungGalaxyS", "An old Samsung Galaxy S smart phone"), 
			ListingType.BUY_NOW, 
			150, 
			listingDuration,
			creditorSmallItems
		);
		Controller.getInstance().getListedItemsPublisher().listItem(
			seller, 
			new Item(Item.Department.ELECTRONICS, "iPhone6S", "An old iPhone 6S smart phone"), 
			ListingType.BID, 
			400, 
			listingDuration,
			creditorSmallItems
		);
		Controller.getInstance().getListedItemsPublisher().listItem(
			seller, 
			new Item(Item.Department.VEHICLES, "ToyotaSR5", "A used Toyota SR5 truck"), 
			ListingType.BID, 
			2500, 
			listingDuration,
			creditorLargeItems
		);
		System.out.println();
		try {
			Thread.sleep(pauseDuration);
		} catch (InterruptedException e1) {}
		
		
		//create 5 buyers with randomly generated user IDs...
		ArrayList<Buyer> buyers = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			double bid = Math.random() * 100;
			Buyer buyer = new Buyer(Strings.generateRandom("abcdefghijklmnopqrstuvwxyz", 8));
			buyers.add(buyer);
		}
		
		Buyer buyer = buyers.get(0);
		Controller.getInstance().getListedItemsPublisher().findItem(
			buyer, 
			Item.FLD_SRCH_SPEC__ITM_DEPT|Item.FLD_SRCH_SPEC__ITM_DESCR, 
			"smart phone", 					//itemDescription
			Item.Department.ELECTRONICS, 	//itemDept
			null, 							//itemModel
			null, 							//sellerId
			null, 							//listingType
			null, 							//maxListingPrice
			null							//maxListingDurationRemaining
		);
		for (ItemListing listedItem: buyer.getLastSearchResults()) {
			double bid = listedItem.listItemEvent.price + (Math.random() * 100);
			Controller.getInstance().getBidPublisher().bidItem(buyer, listedItem.listItemEvent.eventId, bid);
		}
		System.out.println();
		try {
			Thread.sleep(pauseDuration);
		} catch (InterruptedException e1) {}
		
		buyer = buyers.get(1);
		Controller.getInstance().getListedItemsPublisher().findItem(
			buyer, 
			Item.FLD_SRCH_SPEC__ITM_DEPT|Item.FLD_SRCH_SPEC__ITM_MDL, 
			null, 							//itemDescription
			Item.Department.ELECTRONICS, 	//itemDept
			"iPhone", 						//itemModel
			null, 							//sellerId
			null, 							//listingType
			null, 							//maxListingPrice
			null							//maxListingDurationRemaining
		);
		for (ItemListing listedItem: buyer.getLastSearchResults()) {
			double bid = listedItem.listItemEvent.price;
			Controller.getInstance().getBidPublisher().bidItem(buyer, listedItem.listItemEvent.eventId, bid);
		}
		System.out.println();
		try {
			Thread.sleep(pauseDuration);
		} catch (InterruptedException e1) {}
		
		buyer = buyers.get(2);
		Controller.getInstance().getListedItemsPublisher().findItem(
			buyer, 
			Item.FLD_SRCH_SPEC__ITM_DEPT|ListItemEvent.FLD_SRCH_SPEC__LSTNG_TYPE|ListItemEvent.FLD_SRCH_SPEC__LSTNG_PRICE, 
			null, 						//itemDescription
			Item.Department.VEHICLES, 	//itemDept
			null, 						//itemModel
			null, 						//sellerId
			ListingType.BUY_NOW, 		//listingType
			3000.00, 					//maxListingPrice
			null						//maxListingDurationRemaining
		);
		for (ItemListing listedItem: buyer.getLastSearchResults()) {
			double bid = listedItem.listItemEvent.price + (Math.random() * 100);
			Controller.getInstance().getBidPublisher().bidItem(buyer, listedItem.listItemEvent.eventId, bid);
		}
		Controller.getInstance().getListedItemsPublisher().findItem(
			buyer, 
			Item.FLD_SRCH_SPEC__ITM_DEPT|ListItemEvent.FLD_SRCH_SPEC__LSTNG_TYPE|ListItemEvent.FLD_SRCH_SPEC__LSTNG_PRICE, 
			null, 						//itemDescription
			Item.Department.VEHICLES, 	//itemDept
			null, 						//itemModel
			null, 						//sellerId
			ListingType.BID, 			//listingType
			3000.00, 					//maxListingPrice
			null						//maxListingDurationRemaining
		);
		for (ItemListing listedItem: buyer.getLastSearchResults()) {
			double bid = listedItem.listItemEvent.price + (Math.random() * 100);
			Controller.getInstance().getBidPublisher().bidItem(buyer, listedItem.listItemEvent.eventId, bid);
		}
		System.out.println();
		try {
			Thread.sleep(pauseDuration);
		} catch (InterruptedException e1) {}
		
		buyer = buyers.get(3);
		Controller.getInstance().getListedItemsPublisher().findItem(
			buyer, 
			Item.FLD_SRCH_SPEC__ITM_DEPT|Item.FLD_SRCH_SPEC__ITM_DESCR|Item.FLD_SRCH_SPEC__ITM_MDL, 
			"wifi", 						//itemDescription
			Item.Department.ELECTRONICS, 	//itemDept
			"ea5200", 						//itemModel
			null, 							//sellerId
			null, 							//listingType
			null, 							//maxListingPrice
			null							//maxListingDurationRemaining
		);
		for (ItemListing listedItem: buyer.getLastSearchResults()) {
			double bid = listedItem.listItemEvent.price;
			Controller.getInstance().getBidPublisher().bidItem(buyer, listedItem.listItemEvent.eventId, bid);
		}
		System.out.println();
		try {
			Thread.sleep(pauseDuration);
		} catch (InterruptedException e1) {}
		
		buyer = buyers.get(4);
		Controller.getInstance().getListedItemsPublisher().findItem(
			buyer, 
			Item.FLD_SRCH_SPEC__ITM_DEPT|Item.FLD_SRCH_SPEC__ITM_DESCR|Item.FLD_SRCH_SPEC__ITM_MDL|ListItemEvent.FLD_SRCH_SPEC__LSTNG_DUR, 
			"wifi", 						//itemDescription
			Item.Department.ELECTRONICS, 	//itemDept
			"ea5200", 						//itemModel
			null, 							//sellerId
			null, 							//listingType
			null, 							//maxListingPrice
			listingDuration					//maxListingDurationRemaining
		);
		for (ItemListing listedItem: buyer.getLastSearchResults()) {
			double bid = listedItem.listItemEvent.price;
			Controller.getInstance().getBidPublisher().bidItem(buyer, listedItem.listItemEvent.eventId, bid);
		}
		System.out.println();
		try {
			Thread.sleep(pauseDuration);
		} catch (InterruptedException e1) {}
		
		try {
			Thread.sleep(listingDuration*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
}
