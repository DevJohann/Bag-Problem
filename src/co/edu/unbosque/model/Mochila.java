package co.edu.unbosque.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Mochila {

	//This array contains all the problem's objects
	private ArrayList<Item> storage;
	
	//This contains just the solution's objects
	private ArrayList<Item> bag;
	
	private double maxWeight;

	
	public Mochila(int maxW) {
		this.maxWeight = maxW;
		initItems();
	}
	
	public void solve() throws CloneNotSupportedException {
		bag = new ArrayList<Item>();
		/*
		 * We've to check this out, this sort is weird looking
		 */
		storage.sort(Comparator.comparing(Item::getRelation));
		
		//Let's go over all the storage items
		for(int i = 0; i < storage.size(); i++){
			//Lets see if the current item can be stored in the bag
			//Also, lets check if there is available stock
			
			//For optimization purposes, we store in a variable the actual item
			Item currentItem = storage.get(i);
			
			System.out.println(getTotalWeight());
			//lets loop the same item till it cannot be stored or its stock goes to zero
			/*
			 * currentItem.getWeight() + getTotalWeight()
			 * This part means that the loop will go over only if the whole item can be stored
			 * And if not?
			 * We can divide the total weight by the amount of pieces:
			 * 	If there is only one piece, it wont affect anything
			 * 	If there is more than one piece, we will enter the loop only if at least one piece of the item can be stored
			 */
			
			double weighPerPiece = currentItem.getWeight() / currentItem.getPieces(); //This is the minimum weight of an item
			
			//If the currentItem amount is greater than one, we dont want to create again a copy of the item
			//Lets create the object that will be stored in the bag
			Item storedItem = (Item) currentItem.clone(); //This clones well the object
			storedItem.setPieces(0);
			storedItem.setWeight(0);
			storedItem.setStock(0);
			
			//Now, lets add the item to the bag (We will do this once per item)
			bag.add(storedItem);
			
			while(weighPerPiece + getTotalWeight() <= maxWeight && currentItem.getStock() != 0) {
				//Then, we can store the item in the bag and there are existences of the item
				
				
				//We have two cases, or the item has no pieces, or it has
				if(currentItem.getPieces() == 1) {
					//Just one piece, so lets treat the items as an unit
					
					//Lets set it's stock to one more (It was initialized in zero)
					storedItem.setStock(storedItem.getStock() + 1);
					//Lets set the item's pieces to one (It will be always one at this point)
					storedItem.setPieces(1);
					//Lets set the weight of the item (It was initialized in zero)
					storedItem.setWeight(storedItem.getWeight() + currentItem.getWeight()); //The weight of the whole piece
					
					
					//Last but not least, lets decrease the stock amount of that item
					currentItem.setStock(currentItem.getStock() - 1);
					//Now, we can move on the next loop, to see if we can store more of this item or we move to the next item
					continue;
				}else {
					//In this case, we are working with pieces
					
					//We have two cases, when the whole item can be stored, and when we dont have enough space
					//Case: We have space to store the whole item
					if(currentItem.getWeight() + getTotalWeight() <= maxWeight ) {

						//Lets create the object that will be stored in the bag
						//Item storedItem = new Item(currentItem); //Creating a reflection of the currentItem}
//						Item storedItem = (Item) currentItem.clone(); //This clones well the object
//						storedItem.setPieces(0);
//						storedItem.setWeight(0);
//						storedItem.setStock(0);
						
						//We will not move the pieces amount, because we're taking the whole unit
						//Lets set it's stock to one more (It was initialized in zero)
						storedItem.setStock(storedItem.getStock() + 1);
						//Lets set the item's pieces to one (It will not be one at this point)
						storedItem.setPieces(currentItem.getPieces());
						//Lets set the weight of the item (It was initialized in zero)
						storedItem.setWeight(storedItem.getWeight() + currentItem.getWeight()); //The weight of the whole piece
						
						//Now, lets add the item to the bag
						//bag.add(storedItem);
						
						//Last but not least, lets decrease the stock amount of that item
						currentItem.setStock(currentItem.getStock() - 1);
						//Now, we can move on the next loop, to see if we can store more of this item or we move to the next item
						continue;
						
					}else{ //Case: We cannot store the whole item (This will be the case, cause we entered the loop because at least one piece can be stored)
						
						//We have to set the weight of the item to currentWeight plus the piece weight
						
						//First, lets create the item that will be added to the bag (With zero weight)
//						Item storedItem = (Item) currentItem.clone(); //This clones well the object
//						storedItem.setPieces(0);
//						storedItem.setWeight(0);
//						storedItem.setStock(0);
						
						//We have to save the original pieces to restore the value if there is more than one unit
						int originalPieces = currentItem.getPieces();
						
						//Lets add piece by piece till the bag is full or till the pieces are down to zero
						while(weighPerPiece + getTotalWeight() <= maxWeight && currentItem.getPieces() != 0) {
							
							storedItem.setPieces(storedItem.getPieces() + 1); //Adding one piece to the bag
							storedItem.setWeight(storedItem.getWeight() + currentItem.getWeight() + weighPerPiece); //Adding the weight of one piece
							
							currentItem.setPieces(currentItem.getPieces() - 1); //Substracting a piece from the item
							currentItem.setWeight(currentItem.getWeight() - weighPerPiece); //IDK why, but to avoid mistakes

							//Now, we added one piece to the bag, lets continue to the next piece (if it can be stored or if it exists)
						}
						//We have to check if we have used all the pieces available
						//If we used all the pieces, we have to substract one unit from the stock
						if(currentItem.getPieces() == 0) {
							currentItem.setStock(currentItem.getStock() - 1);
							//If we have more units remaining, we will need to set the pieces amount again to the original one
							if(currentItem.getStock() > 0)
								currentItem.setPieces(originalPieces); //So we can come back and add more pieces from the other unit available
								//We also have to set the Weight to the original one
								currentItem.setWeight(originalPieces * weighPerPiece);
						}
						
						//At this point, the StoredItem weights the amount of all the pieces that can be stored, so lets add it to the bag
						//bag.add(storedItem);
						
						//End of the else conditional (We cannot store the whole item)
					}
					//End of the else conditional (More than one pieces)
				}
			}
		}
	}
	
	public void initItems() {
		storage = new ArrayList<Item>();
		
		//Name, value, weight, stock, pieces
		//Weight is the total weight per unit
		//If an item is divided in pieces, the weight will be divided too
		storage.add(new Item("TV", 150, 1.0, 2, 1));
		storage.add(new Item("Blackberry", 150, 0.3, 3, 1));
		storage.add(new Item("Cake", 100, 1, 1, 8));
		storage.add(new Item("iPad", 90, 0.7, 3, 1));
		storage.add(new Item("Computer", 200, 0.5, 2, 1));
		storage.add(new Item("Coffee", 150, 0.2, 2, 1));
		storage.add(new Item("Chocolate bar", 50, 0.1, 2, 4));
	}
	
	private double getTotalWeight() {
		double total = 0;
		for(Item e : bag) {
			//Do not multiply by the number of pieces (The weight is the total per unit)
			total += e.getWeight();
		}
		return total;
	}
	public void showSolution() {
		for(Item e : bag) {
			System.out.println(e);
		}
		System.out.println("Total weight: " + getTotalWeight());
	}
}
