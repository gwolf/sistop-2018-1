import java.util.concurrent.Semaphore;
class Persona extends Thread {

	//id == 1 si es hacker, id == 0 si es serf
	int id;
	Semaphore mutex;

	public Persona(int id, Semaphore s){
		this.id = id;
		this.mutex = s;
	}
	public void run(){
		try{
			mutex.acquire();
		}catch(Exception e){
			e.printStackTrace();
		}
		//Si ya está lleno, imprime el mensaje y vacía la balsa
		if(!Cruce.aborda(id)){
			System.out.println("Salen "+ Cruce.cuenta(1)+ " hackers y "+Cruce.cuenta(0)+" serfs");
			Cruce.imprimeBalsa();
			Cruce.iniciaBalsa();
		}
		mutex.release();
	}
}