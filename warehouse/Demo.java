package warehouse;

public class Demo {

	public static void main(String[] args) {
		
		WareHouse wharehouse=new WareHouse();
		Supplier supplier=new Supplier("Supplier Pesho", wharehouse);
		supplier.start();
		
		Shop shop1=new Shop("Billa", wharehouse);
		shop1.start();
		Client client1=new Client("Gosho", shop1);
		client1.start();
		Client client2=new Client("Tosho", shop1);
		client2.start();
		Client client3=new Client("Misho", shop1);
		client3.start();
		
		Shop shop2=new Shop("Fantastiko", wharehouse);
		shop2.start();
		Client client4=new Client("Kosio", shop2);
		client4.start();
		Client client5=new Client("Vanio", shop2);
		client5.start();
		Client client6=new Client("Kiro", shop2);
		client6.start();

	}

}
