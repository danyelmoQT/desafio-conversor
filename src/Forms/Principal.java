package forms;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import java.awt.Toolkit;
import utils.*;

public class Principal extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	private final JComboBox<String> cmbOpciones;
	private final JLabel lblPrompt;
	private final JButton updateButton;
	// Modo inicial de operación
	private Config.Operaciones tipoOperacion = Config.Operaciones.CONFIG_CONVERSOR;
	
	//Iniciamos el conversor de monedas
	ConversorMonedas cMonedas= new ConversorMonedas();

	public Principal(String titulo, String propmpt, String[] opciones) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/images/icon.png")));
		setTitle(titulo);
		setBounds(0, 0, 410, 150);
		setLocationRelativeTo(null);
		// Layout del dialogo
		getContentPane().setLayout(new BorderLayout());
		// Panel del conenido
		{
			JPanel panelContenido = new JPanel();
			panelContenido.setBorder(new EmptyBorder(5, 5, 5, 5));
			panelContenido.setLayout(new GridLayout(2, 1, 0, 5));
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
			JPanel panelBotones = new JPanel();
			panelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(panelBotones, BorderLayout.SOUTH);
			{
				updateButton = new JButton("Actualizar cotizaciones");
				updateButton.addActionListener(this);
				updateButton.setActionCommand("Actualizar");
				updateButton.setVisible(false);
				panelBotones.add(updateButton);
			}
			{
				JButton okButton = new JButton("Aceptar");
				okButton.addActionListener(this);
				okButton.setActionCommand("Aceptar");
				panelBotones.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Salir");
				cancelButton.addActionListener(this);
				cancelButton.setActionCommand("Cancelar");
				panelBotones.add(cancelButton);
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
		else if (e.getActionCommand() == "Aceptar") {
			// Con el primer aceptar,configuramos el dialogo principal con las unidades de
			// conversión seleccionadas.
			if (tipoOperacion == Config.Operaciones.CONFIG_CONVERSOR) {
				configurarTipoConversor(cmbOpciones.getSelectedIndex());
			} else {
				// Con el segundo hacemos la conversión.
				this.setVisible(false);
				switch (tipoOperacion) {
				case MODO_CONV_MONEDA:
					procesarConversion(cmbOpciones.getSelectedIndex(), cMonedas);
					break;
				case MODO_CONV_TEMP:
					procesarConversion(cmbOpciones.getSelectedIndex(), new ConversorTemperaturas());
					break;
				case MODO_CONV_PRESION:
					procesarConversion(cmbOpciones.getSelectedIndex(), new ConversorPresiones());
					break;
				default:
					break;
				}
			}
		}
		else if ((e.getActionCommand() == "Actualizar")) {
			if(cMonedas.actualizarCotizacionesOnline()) {
				updateButton.setText("Cotizaciones actualizadas");
			}else {
				updateButton.setText("Error al actualizar");
			}
			updateButton.setEnabled(false);
		}
	}

	private void configurarTipoConversor(int index) {
		switch (index) {
		case 0:
			actualizarInterface("Conversión de monedas", "Elija la moneda a la que desea convertir su dinero:",
					cMonedas, Config.Operaciones.MODO_CONV_MONEDA, true);
			break;
		case 1:
			actualizarInterface("Conversión de temperaturas", "Elija entre que unidades desea convertir:",
					new ConversorTemperaturas(), Config.Operaciones.MODO_CONV_TEMP, true);
			break;
		case 2:
			actualizarInterface("Conversión de presiones", "Elija entre que unidades deseas convertir:",
					new ConversorPresiones(), Config.Operaciones.MODO_CONV_PRESION, true);
			break;
		default:
			break;
		}
	}

	private void procesarConversion(int index, ConversorGenerico obj) {
		String origen = obj.getUnidadOrigen(index);
		String destino = obj.getUnidadDestino(index);
		// Pedimos al usuario que ingrese el valor
		String rawInput = JOptionPane.showInputDialog(this, "Ingrese la cantidad de " + origen + " a convertir.", "0");
		// Si el usuario pulsa cancelar salimos
		if (rawInput == null) {
			this.setVisible(true);
			return;
		}
		// Corregimos el separador decimal
		String strInput = obj.corregirSeparadorDecimal(rawInput);
		// Validamos y calculamos.
		if (obj.validarEntrada(strInput)) {
			String resultado = String.format("%.2f", obj.convertirUnidades(index, strInput));
			mostrarResultado(strInput + " " + origen + " equivalen a " + resultado + " " + destino + ".");
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
		int resultado = JOptionPane.showConfirmDialog(this, "Desea continuar con otra conversión", "Elija una opción",
				JOptionPane.YES_NO_OPTION);
		if (resultado == 0) {
			actualizarInterface("Menú Principal", "Seleccione una opción de conversión:", null,
					Config.Operaciones.CONFIG_CONVERSOR, false);
		} else {
			JOptionPane.showMessageDialog(this, "¡Gracias por utilizar el programa!");
			this.dispose();
		}
	}

	private void actualizarInterface(String titulo, String prompt, ConversorGenerico obj,
			Config.Operaciones operacion, boolean mostrarActualizarMonedas) {
		this.setVisible(true);
		setTitle(titulo);
		lblPrompt.setText(prompt);
		tipoOperacion = operacion;
		updateButton.setVisible(mostrarActualizarMonedas);
		if (obj == null) {
			cmbOpciones.setModel(new DefaultComboBoxModel<String>(Config.opcionesConversion));
		} else {
			cmbOpciones.setModel(new DefaultComboBoxModel<String>(obj.getOpcionesConversion()));
		}
	}
}