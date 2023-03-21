package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CotizacionesOnline extends Thread {

	public static void main(String[] args) {
		String resultado = "Error";
		try {
			resultado = CotizacionesOnline.Api("USD", "ARS");
		} catch (Exception e) {
			System.out.println("No fue posible completar la operación.");
			e.printStackTrace();
		}
		System.out.println(resultado);
	}

	public static String Api(String monedaInicial, String monedaDestino) throws Exception {
		String url = "https://api.getgeoapi.com/v2/currency/convert"
				+ "?api_key=e054e0b235fe74453932abb9e05d54ae42175a26&from=" + monedaInicial + "&to=" + monedaDestino;

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

		HttpResponse<String> respuesta = client.send(request, HttpResponse.BodyHandlers.ofString());

		String responseBody = respuesta.body();

		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(responseBody);
		JsonNode ratesNode = jsonNode.get("rates");
		JsonNode targetCurrencyNode = ratesNode.get(monedaDestino);
		String rateForAmount = targetCurrencyNode.get("rate").asText();

		return (rateForAmount);

	}
}