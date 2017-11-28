package main;

public class Main {

	private static Model model;
	
	public static void main(String[] args) {
		
		model = Model.getInstance();
		initializeModel(model);
		View view = new View(model);
		model.registerObserver(view);
		view.receiveUsersMessages();
		
	}
	
	public static void initializeModel(Model model){
		
		System.out.println("Servidor do bot Rodando");
		
	}

}
