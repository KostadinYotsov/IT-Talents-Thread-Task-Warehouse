package warehouse;

import java.util.HashMap;
import java.util.Map.Entry;

public class WareHouse {

	public interface IType{}
	
	public enum ProductType {MEAT, FRUIT, VEGETABLE}
	
	public enum MeatType implements IType {PORK, BEEF, CHIKEN}
	
	public enum FriutType implements IType {BANANA, ORANGE, APPLE}
	
	public enum VegetableType implements IType {POTATO, EGGPLANT, CUCUMBER}
	
	
	private static final int REMOVE_QUANTITY_CONST = 5;
	private static final int FILL_QUANTITY_CONST = 25;
	private final static int MIN_QUAN_CONST=5;
	
	private HashMap<ProductType, HashMap<IType, Integer>> products;
	private Supplier supplier;
	

	public WareHouse() {
		products=new HashMap<>();
		for (int i=0; i<3; i++) {
			products.put(ProductType.values()[i], new HashMap<>());
			for (int j = 0; j <3; j++) {
				FriutType ft=FriutType.values()[j];
				MeatType mt=MeatType.values()[j];
				VegetableType vt= VegetableType.values()[j];
				products.get(ProductType.values()[i]).put(ft, 15);
				products.get(ProductType.values()[i]).put(mt, 15);
				products.get(ProductType.values()[i]).put(vt, 15);
			}
		}
	}
	
	
	public synchronized void deliverProducts () {
		while (!hasInsufficient()) {
			try {
				wait ();
			} catch (InterruptedException e) {
				System.out.println("ops");
			}
		}
		fillQuantity();
		notifyAll();
	}
	
	private boolean hasInsufficient() {
		for (HashMap<IType, Integer> map : products.values()) {
			for (Entry<IType,Integer> e : map.entrySet()) {
				int quantity=e.getValue();
				if (quantity<MIN_QUAN_CONST) {
					return true;
				}
			}
		}
		return false;
	}

	
	private void fillQuantity() {
		for (HashMap<IType, Integer> map : products.values()) {
			for (Entry<IType,Integer> e : map.entrySet()) {
				IType type=e.getKey();
				int quantity=e.getValue();
				if (quantity<MIN_QUAN_CONST) {
					map.put(type, quantity+FILL_QUANTITY_CONST);
					System.out.println(this.supplier.toString() + " deliver 25 kg. " + type + " in wharehouse.");
				}
			}
		}
	}
	
	public synchronized void sellProducts(IType type) {
		while (insufficient(type)) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.out.println("ops");
			}
		}
		removeQuantity(type);
		notifyAll();
	}
	
	
	
	private boolean insufficient(IType type) {
		for (HashMap<IType, Integer> map : products.values()) {
			for (Entry<IType,Integer> e : map.entrySet()) {
				IType pType=e.getKey();
				int quantity=e.getValue();
				if (pType.equals(type) && quantity<MIN_QUAN_CONST) {
					return true;
				}
			}
		}
		return false;
	}

	private void removeQuantity(IType type) {
		for (HashMap<IType, Integer> map : products.values()) {
			for (Entry<IType,Integer> e : map.entrySet()) {
				int quantity=e.getValue();
				IType iType=e.getKey();
				if (iType.equals(type)) {
					map.put(type, quantity-REMOVE_QUANTITY_CONST);
				}
			}
		}
	}


	public void addSupplier(Supplier supplier) {
		this.supplier=supplier;	
	}
	
}
