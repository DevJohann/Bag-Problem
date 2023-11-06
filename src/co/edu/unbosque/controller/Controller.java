package co.edu.unbosque.controller;

import co.edu.unbosque.model.Mochila;

public class Controller {
	public Controller() {
		Mochila bag = new Mochila(5);
		try {
			bag.solve();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bag.showSolution();
	}
}
