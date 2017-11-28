package main;

public interface Observer {

	public void update(long chatId, String resp, String typeResult);
	
	public void update(long chatId, String resp, String[] inlinesBtn);
	
}