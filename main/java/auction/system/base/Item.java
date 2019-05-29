package auction.system.base;

public class Item {
	public final static int FLD_SRCH_SPEC__ITM_DESCR 	= 0x01;
	public final static int FLD_SRCH_SPEC__ITM_DEPT 	= 0x02;
	public final static int FLD_SRCH_SPEC__ITM_MDL 		= 0x04;
	
	public static enum Department {
		ELECTRONICS
		, CLOTHING
		, GARDEN
		, VEHICLES
		, SERVICES
		;
	}
	
	public final Department department;
	public final String description;
	public final String modelNumber;
	
	public Item(Department department, String modelNumber, String description) {
		this.department = department;
		this.modelNumber = modelNumber;
		this.description = description;
	}

	@Override
	public String toString() {
		return String.format("{%s, %s, \"%s\"}", department, modelNumber, description);
	}
}
