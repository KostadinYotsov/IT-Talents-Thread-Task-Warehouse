package warehouse;

import java.util.Random;
import warehouse.WareHouse.FriutType;
import warehouse.WareHouse.IType;
import warehouse.WareHouse.MeatType;
import warehouse.WareHouse.ProductType;
import warehouse.WareHouse.VegetableType;

public class Client  extends Thread {
	
	private String name;
	private Shop shop;
	
	public Client(String name, Shop shop) {
		this.name = name;
		this.shop = shop;
	}
	
	@Override
	public void run() {
		while (true) {
			ProductType whichType=ProductType.values()[new Random().nextInt(ProductType.values().length)];
			IType type=null;
			switch (whichType  ) {
			case FRUIT:
				type=FriutType.values()[new Random().nextInt(FriutType.values().length)];
				break;
				
			case VEGETABLE:
				type=VegetableType.values()[new Random().nextInt(VegetableType.values().length)];
				break;
				
			case MEAT:
				type=MeatType.values()[new Random().nextInt(MeatType.values().length)];
				break;
				
			default:
				break;
			}
			
			int quantity=new Random().nextInt(3)+3;
			this.shop.sellProducts(type, quantity);
			System.out.println(this.name + " buy " + quantity + " kg. " + type + " from " + this.shop.toString());
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				System.out.println("ops");
			}
		}
	}

}
