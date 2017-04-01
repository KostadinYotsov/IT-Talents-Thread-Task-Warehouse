package warehouse;

import java.util.HashMap;
import java.util.Map.Entry;
import warehouse.WareHouse.FriutType;
import warehouse.WareHouse.IType;
import warehouse.WareHouse.MeatType;
import warehouse.WareHouse.ProductType;
import warehouse.WareHouse.VegetableType;

public class Shop extends Thread {
	
	private static final int FILL_QUANTITY_CONST = 5; 
	private final static int MIN_QUAN_CONST=5;
	
	private String name;
	private WareHouse wharehouse;
	private HashMap<ProductType, HashMap<IType, Integer>> products;
	
	
	public Shop(String name, WareHouse wharehouse) {
		this.name = name;
		this.wharehouse = wharehouse;
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
	
	@Override
	public void run() {
		while (true) {
			this.deliverProducts();
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
					System.out.println(type + " is not enough. Fill from wharehouse!");
					this.wharehouse.sellProducts(type);
					map.put(type, quantity+FILL_QUANTITY_CONST);
					System.out.println(this.name + " buy 5 kg. " + type + " from wharehouse.");
				}
			}
		}
	}
	
	public synchronized void sellProducts(IType type, int quantity) {
		while (insufficient(type, quantity)) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.out.println("ops");
			}
		}
		removeQuantity(type, quantity);
		notifyAll();
	}
	
	private boolean insufficient(IType type, int quantity) {
		for (HashMap<IType, Integer> map : products.values()) {
			for (Entry<IType,Integer> e : map.entrySet()) {
				IType pType=e.getKey();
				int quan=e.getValue();
				if (pType.equals(type) && quan<quantity) {
					return true;
				}
			}
		}
		return false;
	}
	
	private void removeQuantity(IType type, int quan) {
		for (HashMap<IType, Integer> map : products.values()) {
			for (Entry<IType,Integer> e : map.entrySet()) {
				int quantity=e.getValue();
				IType iType=e.getKey();
				if (iType.equals(type)) {
					map.put(type, quantity-quan);
				}
			}
		}
	}
	
	@Override
	public String toString() {
		return this.name;
	}

}
