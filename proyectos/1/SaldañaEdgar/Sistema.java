import java.io.File;

public class Sistema {
	public static void main(String[] args) {

		File superbloque = new File("superbloque.bin");

		if (superbloque.exists())
			System.out.println("Ya existe");
		else
			System.out.println("Aún no");
			montar();
	}

	public static void montar() {
		Superbloque superb = new Superbloque();

	}
}