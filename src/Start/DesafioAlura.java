package start;

import javax.swing.JDialog;
import forms.Principal;
import utils.*;

public class DesafioAlura {	
	public static void main(String[] args) {
		try {
			Principal dialog = new Principal("Menú Principal","Seleccione una opción de conversión:",Config.opcionesConversion);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
