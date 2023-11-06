package co.edu.unbosque.model;

public class Item implements Cloneable{
	
	private String name;
	private int value;
	private double weight;
	private int stock;
	private int pieces;
	private double relation;
	
	public Item(String name, int value, double weight, int stock, int pieces) {
		this.name = name;
		this.value = value;
		this.weight = weight;
		this.stock = stock;
		this.pieces = pieces;
		relation = value/weight;
	}
	
	/*
	 * This constructor is used to copy an Item Object
	 * (Used when adding items to the bag)
	 */
	public Item(Item e) {
		e.name = name;
		e.value = value;
		this.weight = 0; //Weight have to be zero (We add the whole weight if we can, if not, we will add only piece by piece)
		this.stock = 0;
		this.pieces = 0;
		relation = value/weight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getPieces() {
		return pieces;
	}

	public void setPieces(int pieces) {
		this.pieces = pieces;
	}
	

	public double getRelation() {
		return relation;
	}

	public void setRelation(double relation) {
		this.relation = relation;
	}

	@Override
	public String toString() {
		return "Item [name=" + name + ", value=" + value + ", weight=" + weight + ", stock=" + stock + ", pieces="
				+ pieces + "]";
	}
	
	 public Object clone() throws CloneNotSupportedException 
	    { 
	        return super.clone(); 
	    } 
	
}
