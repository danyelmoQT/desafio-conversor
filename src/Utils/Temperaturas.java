package Utils;

public class Temperaturas extends Monedas {

	public Temperaturas() {
		opciones = new String[] { "Celsius a Fahrenheit", "Celsius a Kelvin", "Fahrenheit a Celsius",
				"Kelvin de Celsius" };
		factores = null;
	}

	public double convertirUnidades(int index, double cantidadOrigen) {
		double resultado;
		switch (index) {
		case 0:
			resultado = (cantidadOrigen * ((double) 9 / 5)) + 32;
			break;
		case 1:
			resultado = cantidadOrigen + 273.15;
			break;
		case 2:
			resultado = (cantidadOrigen - 32) * ((double) 5 / 9);
			break;
		case 3:
			resultado = cantidadOrigen - 273.15;
			break;
		default:
			resultado = 0;
			break;

		}
		return resultado;
	}
}
