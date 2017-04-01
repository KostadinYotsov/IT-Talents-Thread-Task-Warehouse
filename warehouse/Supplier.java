package warehouse;

public class Supplier extends Thread {
	
	private String name;
	private WareHouse wharehouse;
	
	public Supplier(String name, WareHouse wharehouse) {
		this.name = name;
		this.wharehouse = wharehouse;
		this.wharehouse.addSupplier(this);
	}
	
	@Override
	public void run() {
		while (true) {
			this.wharehouse.deliverProducts();
		}
	}
	
	@Override
	public String toString() {
		return this.name;
	}

}
