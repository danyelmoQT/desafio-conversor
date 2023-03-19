package Utils;

public class Presiones extends Monedas {

	public Presiones() {
		opciones = new String[]{"Bar a Atmósferas", "Bar a Kilopascales", "Bar a mmHG", "Bar a Pascales", "Bar a PSI",
				"Atmósferas a Bar", "Kilopascales a Bar", "mmHG a Bar","Pascales a Bar", "PSI a Bar"  };
		factores = new double[] {0.986923, 100, 750.1875, 100000, 14.50377 };
	}

	public double convertirUnidades(int index, double cantidadOrigen) {
		double resultado = 0;
		if (index < factores.length) {
			resultado = cantidadOrigen * factores[index];
		} else {
			resultado = cantidadOrigen / factores[index-factores.length];
		}
		return resultado;
	}
}
