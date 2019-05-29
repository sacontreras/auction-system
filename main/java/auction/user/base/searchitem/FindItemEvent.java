package auction.user.base.searchitem;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

import auction.system.ItemListing;
import auction.system.base.Event;
import auction.system.base.Item;
import auction.user.seller.Seller;
import auction.user.seller.listitem.ListItemEvent;

public class FindItemEvent extends Event {
//	public final static int FLD_SRCH_SPEC__ITM_DESCR 	= 0x01;
//	public final static int FLD_SRCH_SPEC__ITM_DEPT 	= 0x02;
//	public final static int FLD_SRCH_SPEC__ITM_MDL 		= 0x04;
//	public final static int FLD_SRCH_SPEC__SELLER_ID 	= 0x08;
//	public final static int FLD_SRCH_SPEC__LSTNG_TYPE 	= 0x10;
//	public final static int FLD_SRCH_SPEC__LSTNG_PRICE 	= 0x20;
//	public final static int FLD_SRCH_SPEC__LSTNG_DUR 	= 0x40;
	public static enum SearchField {
		ITEM_DESCRIPTION
		, ITEM_DEPARTMENT
		, ITEM_MODEL
		, SELLER_ID
		, LISTING_TYPE
		, LISTING_PRICE
		, LISTING_DURATION
	}

	public final int searchFieldsSpec; // use the above bit combinations to include fields in search spec
	public final String itemDescription;
	public final Item.Department itemDept;
	public final String itemModel;
	public final String sellerId;
	public final ListItemEvent.ListingType listingType;
	public final Double maxListingPrice;
	public final Long maxListingDurationRemaining;
	public final ArrayList<ItemListing> searchResults = new ArrayList<>();

	public FindItemEvent(
			int searchFieldsSpec, 
			String itemDescription, 
			Item.Department itemDept, 
			String itemModel,
			String sellerId, 
			ListItemEvent.ListingType listingType, 
			Double maxListingPrice, 
			Long maxListingDurationRemaining, 
			Date timestamp, 
			String searchItemEventId) {
		
		super(timestamp, searchItemEventId);
		this.searchFieldsSpec = searchFieldsSpec;
		this.itemDescription = itemDescription;
		this.itemDept = itemDept;
		this.itemModel = itemModel;
		this.sellerId = sellerId;
		this.listingType = listingType;
		this.maxListingPrice = maxListingPrice;
		this.maxListingDurationRemaining = maxListingDurationRemaining;
	}
	
	public boolean searchSpecContainsField(SearchField searchField) {
		switch (searchField) {
			case ITEM_DESCRIPTION: return (searchFieldsSpec & Item.FLD_SRCH_SPEC__ITM_DESCR) == Item.FLD_SRCH_SPEC__ITM_DESCR;
			case ITEM_DEPARTMENT: return (searchFieldsSpec & Item.FLD_SRCH_SPEC__ITM_DEPT) == Item.FLD_SRCH_SPEC__ITM_DEPT;
			case ITEM_MODEL: return (searchFieldsSpec & Item.FLD_SRCH_SPEC__ITM_MDL) == Item.FLD_SRCH_SPEC__ITM_MDL;
			case SELLER_ID: return (searchFieldsSpec & Seller.FLD_SRCH_SPEC__SELLER_ID) == Seller.FLD_SRCH_SPEC__SELLER_ID;
			case LISTING_TYPE: return (searchFieldsSpec & ListItemEvent.FLD_SRCH_SPEC__LSTNG_TYPE) == ListItemEvent.FLD_SRCH_SPEC__LSTNG_TYPE;
			case LISTING_PRICE: return (searchFieldsSpec & ListItemEvent.FLD_SRCH_SPEC__LSTNG_PRICE) == ListItemEvent.FLD_SRCH_SPEC__LSTNG_PRICE;
			case LISTING_DURATION: return (searchFieldsSpec & ListItemEvent.FLD_SRCH_SPEC__LSTNG_DUR) == ListItemEvent.FLD_SRCH_SPEC__LSTNG_DUR;
			default: return false;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sbfmt = new StringBuilder();
		if (searchSpecContainsField(SearchField.ITEM_DESCRIPTION)) {
			sbfmt.append(String.format("\"%s\"", itemDescription));
		}
		if (searchSpecContainsField(SearchField.ITEM_DEPARTMENT)) {
			if (sbfmt.length() > 0)
				sbfmt.append(", ");
			sbfmt.append(String.format("%s", itemDept));
		}
		if (searchSpecContainsField(SearchField.ITEM_MODEL)) {
			if (sbfmt.length() > 0)
				sbfmt.append(", ");
			sbfmt.append(String.format("\"%s\"", itemModel));
		}
		if (searchSpecContainsField(SearchField.SELLER_ID)) {
			if (sbfmt.length() > 0)
				sbfmt.append(", ");
			sbfmt.append(String.format("%s", sellerId));
		}
		if (searchSpecContainsField(SearchField.LISTING_TYPE)) {
			if (sbfmt.length() > 0)
				sbfmt.append(", ");
			sbfmt.append(String.format("%s", listingType));
		}
		if (searchSpecContainsField(SearchField.LISTING_PRICE)) {
			if (sbfmt.length() > 0)
				sbfmt.append(", ");
			sbfmt.append(String.format("%f", maxListingPrice));
		}
		if (searchSpecContainsField(SearchField.LISTING_DURATION)) {
			if (sbfmt.length() > 0)
				sbfmt.append(", ");
			sbfmt.append(String.format("%d", maxListingDurationRemaining));
		}
		return "{" + sbfmt.toString() + "}";
	}
}
