package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Monedas {
	
	protected String[] opciones;
	protected double[] factores;
	
	public Monedas() {
		this.opciones = new String[]{"Pesos a Dólares", "Pesos a Euros", "Pesos a Libras", "Pesos a Yenes",
				"Pesos a Wones", "Dólares a Pesos", "Euros a Pesos", "Libras a Pesos", "Yenes a Pesos", "Wones a Pesos" };
		this.factores = new double[] {200, 217.39, 243.9, 1.5151, 0.153 };
	}
	
	public String[] getOpcionesConversion() {	
		return opciones;
	}

	public double convertirUnidades(int index, double cantidadOrigen) {
		double resultado = 0;
		if (index < factores.length) {
			resultado = cantidadOrigen / factores[index];
		} else {
			resultado = cantidadOrigen * factores[index-factores.length];
		}
		return resultado;
	}

	public boolean validarEntrada(String input) {
		if (input == null || input.isEmpty()) {
			return false;
		}
		
		final String regExp = "[0-9]+([,.][0-9]{1,2})?";
		final Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}

	public String getUnidadOrigen(int index) {
		String[] origenes = getOpcionesConversion()[index].split(" ");
		return origenes[0];
	}

	public String getUnidadDestino(int index) {
		String[] destinos = getOpcionesConversion()[index].split(" ");
		return destinos[2];
	}
}