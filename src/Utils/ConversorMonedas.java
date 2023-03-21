package utils;

public class ConversorMonedas extends ConversorGenerico {

	public ConversorMonedas() {
		this.opciones = new String[] { "Pesos a Dólares", "Pesos a Euros", "Pesos a Libras", "Pesos a Yenes",
				"Pesos a Wones", "Dólares a Pesos", "Euros a Pesos", "Libras a Pesos", "Yenes a Pesos",
				"Wones a Pesos" };
		this.factores = new double[] { 200, 217.39, 243.9, 1.5151, 0.153 };
	}

	public double convertirUnidades(int index, String cantidadOrigen) {
		double resultado = 0;
		double origen = Double.parseDouble(cantidadOrigen);
		if (index < factores.length) {
			resultado = origen / factores[index];
		} else {
			resultado = origen * factores[index - factores.length];
		}
		return resultado;
	}

	public boolean actualizarCotizacionesOnline() {
		double[] factoresOnline = new double[5];
		String[] monedas = { "USD", "EUR", "GBP", "JPY", "KRW" };

		for (int i = 0; i < 5; i++) {
			try {
				factoresOnline[i] = Double.parseDouble(utils.CotizacionesOnline.Api(monedas[i], "ARS"));
			} catch (Exception e) {
				System.out.println("Fallo al recuperar cotizaciones. Se utilizan valores por defecto.");
				return false;
			}
		}
		this.factores = factoresOnline;
		return true;
	}

}