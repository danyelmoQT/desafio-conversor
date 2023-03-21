package utils;

public class ConversorTemperaturas extends ConversorGenerico {

	public ConversorTemperaturas() {
		this.opciones = new String[] { "°Celsius a °Fahrenheit", "°Celsius a °Kelvin", "°Celsius a °Rankine",
				"°Fahrenheit a °Celsius", "°Kelvin de °Celsius", "°Rankine a °Celsius" };
		this.factores = null;
	}

	public double convertirUnidades(int index, String cantidadOrigen) {
		double resultado;
		double origen = Double.parseDouble(cantidadOrigen);
		switch (index) {
		case 0:
			resultado = (origen * ((double) 9 / 5)) + 32;
			break;
		case 1:
			resultado = origen + 273.15;
			break;
		case 2:
			resultado = (origen * ((double) 9 / 5)) + 491.67;
			break;
		case 3:
			resultado = (origen - 32) * ((double) 5 / 9);
			break;

		case 4:
			resultado = origen - 273.15;
			break;
		case 5:
			resultado = (origen - 491.67) * ((double) 5 / 9);
			break;
		default:
			resultado = 0;
			break;
		}
		return resultado;
	}
}
