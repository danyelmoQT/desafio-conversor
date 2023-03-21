package utils;

public class ConversorPresiones extends ConversorGenerico {

	public ConversorPresiones() {
		this.opciones = new String[] { "Bar a Atmósferas", "Bar a Kilopascales", "Bar a mmHG", "Bar a Pascales", "Bar a PSI",
				"Atmósferas a Bar", "Kilopascales a Bar", "mmHG a Bar", "Pascales a Bar", "PSI a Bar" };
		this.factores = new double[] { 0.986923, 100, 750.1875, 100000, 14.50377 };
	}

	public double convertirUnidades(int index, String cantidadOrigen) {
		double resultado = 0;
		double origen = Double.parseDouble(cantidadOrigen);
		if (index < this.factores.length) {
			resultado = origen * this.factores[index];
		} else {
			resultado = origen / this.factores[index - this.factores.length];
		}
		return resultado;
	}
}
