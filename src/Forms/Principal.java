package forms;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utils.Config;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import java.awt.Toolkit;

public class Principal extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JComboBox<String> cmbOpciones;
	private final JLabel lblPrompt;
	private final JButton okButton;

	// Modo inicial de operación
	private Config.Operaciones tipoOperacion = Config.Operaciones.CONFIG_CONVERSOR;

	public Principal(String titulo, String propmpt, String[] opciones) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/images/icon.png")));
		setTitle(titulo);
		setBounds(100, 100, 410, 135);
		// Layout del dialogo
		getContentPane().setLayout(new BorderLayout());
		// Panel del conenido
		{
			JPanel panelContenido = new JPanel();
			panelContenido.setBorder(new EmptyBorder(5, 5, 5, 5));
			panelContenido.setLayout(new GridLayout(2, 1));
			getContentPane().add(panelContenido, BorderLayout.CENTER);
			{
				lblPrompt = new JLabel(propmpt);
				panelContenido.add(lblPrompt);
			}
			{
				cmbOpciones = new JComboBox<>(opciones);
				cmbOpciones.setMaximumRowCount(10);
				cmbOpciones.addActionListener(this);
				cmbOpciones.setActionCommand("Cambio");
				panelContenido.add(cmbOpciones);
			}
		}
		// Panel de los botones
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Aceptar");
				okButton.addActionListener(this);
				okButton.setActionCommand("Aceptar");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Salir");
				cancelButton.addActionListener(this);
				cancelButton.setActionCommand("Cancelar");
				buttonPane.add(cancelButton);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Con cancelar termina el programa
		if (e.getActionCommand() == "Cancelar") {
			this.dispose();
		}
		// Con aceptar procesamos
		if (e.getActionCommand() == "Aceptar") {
			// Con el primer aceptar,configuramos el dialogo principal con las unidades de
			// conversión seleccionadas.
			if (tipoOperacion == Config.Operaciones.CONFIG_CONVERSOR) {
				configurarTipoConversor(cmbOpciones.getSelectedIndex());
			} else {
				// Con el segundo hacemos la conversión.
				this.setVisible(false);
				switch (tipoOperacion) {
				case MODO_CONV_MONEDA:
					procesarConversion(cmbOpciones.getSelectedIndex(), new utils.Monedas());
					break;
				case MODO_CONV_TEMP:
					procesarConversion(cmbOpciones.getSelectedIndex(), new utils.Temperaturas());
					break;
				case MODO_CONV_PRESION:
					procesarConversion(cmbOpciones.getSelectedIndex(), new utils.Presiones());
					break;
				default:
					break;
				}
			}
		}
	}

	private void configurarTipoConversor(int index) {
		switch (index) {
		case 0:
			actualizarInterface("Conversión de monedas", "Elija la moneda a la que desea convertir su dinero:",
					new utils.Monedas(), Config.Operaciones.MODO_CONV_MONEDA);
			break;
		case 1:
			actualizarInterface("Conversión de temperaturas", "Elija entre que unidades desea convertir:",
					new utils.Temperaturas(), Config.Operaciones.MODO_CONV_TEMP);
			break;
		case 2:
			actualizarInterface("Conversión de presiones", "Elija entre que unidades deseas convertir:",
					new utils.Presiones(), Config.Operaciones.MODO_CONV_PRESION);
			break;
		default:
			break;
		}
	}

	private void procesarConversion(int index, utils.Monedas obj) {
		// Utils.Monedas obj = new Utils.Monedas();
		String origen = obj.getUnidadOrigen(index);
		String destino = obj.getUnidadDestino(index);

		// Pedimos al usuario que ingrese el valor
		String strRawInput = JOptionPane.showInputDialog(this, "Ingrese la cantidad de " + origen + " a convertir.",
				"0");

		// Si el usuario pulsa cancelar salimos
		if (strRawInput == null) {
			this.setVisible(true);
			return;
		}

		// Corregimos el separador decimal
		char sep = new DecimalFormatSymbols(Locale.getDefault(Locale.Category.FORMAT)).getDecimalSeparator();
		String strInput = strRawInput.replace('.', sep);

		// Validamos
		if (obj.validarEntrada(strInput)) {
			try {
				double dblInput = DecimalFormat.getNumberInstance().parse(strInput).doubleValue();
				mostrarResultado(strInput + " " + origen + " equivalen a "
						+ String.format("%.2f", obj.convertirUnidades(index, dblInput)) + " " + destino + ".");
			} catch (ParseException e) {
				mostrarError();
			}
		} else {
			mostrarError();
		}
	}

	private void mostrarResultado(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje, "Resultado", JOptionPane.INFORMATION_MESSAGE);
		finalizar();
	}

	private void mostrarError() {
		JOptionPane.showMessageDialog(this, "El valor ingresado es inválido", "Error", JOptionPane.ERROR_MESSAGE);
		finalizar();
	}

	private void finalizar() {
		int resultado = JOptionPane.showConfirmDialog(this, "Desea continuar", "Elija una opción",
				JOptionPane.YES_NO_CANCEL_OPTION);
		if (resultado == 0) {
			actualizarInterface("Menú Principal", "Seleccione una opción de conversión:", null,
					Config.Operaciones.CONFIG_CONVERSOR);
		} else {
			this.dispose();
		}
	}

	private void actualizarInterface(String titulo, String prompt, utils.Monedas obj, Config.Operaciones operacion) {
		this.setVisible(true);
		setTitle(titulo);
		lblPrompt.setText(prompt);
		tipoOperacion = operacion;
		if (obj == null) {
			cmbOpciones.setModel(new DefaultComboBoxModel<String>(utils.Config.opcionesConversion));
		} else {
			cmbOpciones.setModel(new DefaultComboBoxModel<String>(obj.getOpcionesConversion()));
		}
	}
}
