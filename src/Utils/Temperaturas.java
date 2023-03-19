package Utils;

public class Temperaturas extends Monedas {

	public Temperaturas() {
		opciones = new String[] { "Celsius a Fahrenheit", "Celsius a Kelvin", "Fahrenheit a Celsius",
				"Kelvin de Celsius" };
		factores = null;
	}

	public double convertirUnidades(int index, double cantidadOrigen) {
		double resultado = 0;
		switch (index) {
		case 0:
			resultado = (cantidadOrigen * (9 / 5)) + 32;
			break;
		case 1:
			resultado = cantidadOrigen + 273.15;
			break;
		case 2:
			resultado = (cantidadOrigen - 32) * (5 / 9);
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
