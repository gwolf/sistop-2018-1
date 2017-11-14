import java.util.concurrent.Semaphore;

class MonitorCPU extends Thread {

	private Semaphore s;

	MonitorCPU(Semaphore s){
		this.s = s;
	}

	public void run(){

		MonitorGral.addCPU("PROCESADOR");
		MonitorGral.addCPU(Interfaz.getLinea());
		MonitorGral.addCPU("hola 1");
		MonitorGral.addCPU("hola 2");
		MonitorGral.addCPU("hola 3");
		MonitorGral.addCPU("hola 4");
		MonitorGral.addCPU("hola 5 adiooooos");
		MonitorGral.addCPU("hola 6");
		MonitorGral.addCPU("hola 7");
		MonitorGral.addCPU("hola 8");
		s.release();
	}
}