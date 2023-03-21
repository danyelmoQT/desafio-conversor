package utils;

import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ConversorGenerico {

	protected String[] opciones;
	protected double[] factores;

	public ConversorGenerico() {
	}

	public String[] getOpcionesConversion() {
		return opciones;
	}

	public abstract double convertirUnidades(int index, String cantidad);

	public boolean validarEntrada(String input) {
		if (input == null || input.isEmpty()) {
			return false;
		}
		final String regExp = "[0-9]+([,.][0-9]{1,2})?";
		final Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}

	public String corregirSeparadorDecimal(String input) {
		String corrInput;
		char sep = new DecimalFormatSymbols(Locale.getDefault(Locale.Category.FORMAT)).getDecimalSeparator();
		if (sep == ',') {
			corrInput = input.replace('.', sep);
		} else if (sep == '.') {
			corrInput = input.replace(',', sep);
		} else {
			corrInput = input;
		}
		return corrInput;
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
