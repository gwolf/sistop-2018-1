import java.util.concurrent.Semaphore;

class MonitorMem extends Thread {

	private Semaphore s;

	MonitorMem(Semaphore s){
		this.s = s;
	}

	public void run(){
		MonitorGral.addMem("MEMORIA");
		MonitorGral.addMem(Interfaz.getLinea());
		MonitorGral.addMem("hola 1");
		MonitorGral.addMem("hola 2");
		MonitorGral.addMem("hola 3");
		MonitorGral.addMem("hola hola hola hola");
		try{
			sleep(1000);
		}catch(Exception e){
			e.printStackTrace();
		}
		MonitorGral.addMem("hola 5");
		MonitorGral.addMem("hola 6");
		MonitorGral.addMem("hola 7");
		MonitorGral.addMem("hola 8");
		s.release();
	}
}