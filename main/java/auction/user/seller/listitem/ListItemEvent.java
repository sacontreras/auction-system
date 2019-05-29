package auction.user.seller.listitem;

import java.util.Date;

import auction.system.base.Item;
import auction.system.base.ItemEvent;
import auction.user.creditor.Creditor;
import auction.user.seller.Seller;

public class ListItemEvent extends ItemEvent { 
//	public final static int FLD_SRCH_SPEC__ITM_DESCR 	= 0x01;
//	public final static int FLD_SRCH_SPEC__ITM_DEPT 	= 0x02;
//	public final static int FLD_SRCH_SPEC__ITM_MDL 		= 0x04;
//	public final static int FLD_SRCH_SPEC__SELLER_ID 	= 0x08;
	public final static int FLD_SRCH_SPEC__LSTNG_TYPE 	= 0x10;
	public final static int FLD_SRCH_SPEC__LSTNG_PRICE 	= 0x20;
	public final static int FLD_SRCH_SPEC__LSTNG_DUR 	= 0x40;
	
	public static enum ListingType {
		BUY_NOW
		, BID
		;
	}
	
	public final Seller seller;
	public final ListingType listingType;
	public final double price;
	public final Creditor creditor;
	public final long duration;
	
	public ListItemEvent(Seller seller, Item item, ListingType listingType, double price, Creditor creditor, long duration, Date timestamp, String itemListingId) {
		super(item, timestamp, itemListingId);
		this.listingType = listingType;
		this.seller = seller;
		this.price = price;
		this.creditor = creditor;
		this.duration = duration;
	}
	
	public long getTimeRemaining() {	//in milliseconds
		return (timestamp.getTime() + duration*1000) - new Date(System.currentTimeMillis()).getTime();
	}
	
	public ListItemEvent clone() {
		return new ListItemEvent(seller, item, listingType, price, creditor, duration, timestamp, eventId);
	}
	
	@Override
	public String toString() {
		return String.format("{%s, %s, %s, %s, %s, %f, %d}", item, timestamp, eventId, listingType, seller.id, price, duration);
	}
}
