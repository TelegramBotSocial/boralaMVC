package main;

public interface Subject {
	
	public void registerObserver(Observer observer);
	
	public void notifyObservers(long chatId);

}