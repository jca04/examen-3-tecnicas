package vistas;

import datechooser.beans.DateChooserCombo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;

public class TemperaturasVista extends JFrame {

    private DateChooserCombo dccFechaEspecifica;
    private JButton btnCalcularExtremos;
    private JButton btnGraficar; // grafica de milo
    private JLabel lblMasCalurosa;
    private JLabel lblMenosCalurosa;
    private JPanel pnlGrafica;

    public TemperaturasVista() {
        setTitle("Análisis de Temperaturas");
        setSize(800, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // toolbvar
        JToolBar tb = new JToolBar();

        btnCalcularExtremos = new JButton();
        btnCalcularExtremos.setIcon(new ImageIcon(getClass().getResource("/iconos/Datos.png")));
        btnCalcularExtremos.setToolTipText("Calcular Ciudad Más y Menos Calurosa");
        tb.add(btnCalcularExtremos);

        btnGraficar = new JButton();
        btnGraficar.setIcon(new ImageIcon(getClass().getResource("/iconos/Grafica.png")));
        btnGraficar.setToolTipText("Graficar Promedios por Rango de Fechas");
        tb.add(btnGraficar);

        JPanel pnlOpciones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        pnlOpciones.add(new JLabel("Fecha Específica:"));
        dccFechaEspecifica = new DateChooserCombo();
        pnlOpciones.add(dccFechaEspecifica);
        
        lblMasCalurosa = new JLabel("Más calurosa: -");
        lblMenosCalurosa = new JLabel("Menos calurosa: -");
        
        pnlOpciones.add(lblMasCalurosa);
        pnlOpciones.add(lblMenosCalurosa);

        // panel medio/central
        pnlGrafica = new JPanel();
        pnlGrafica.setBorder(BorderFactory.createTitledBorder("área de gráfica milo"));

        //layour principal
        JPanel pnlPrincipal = new JPanel(new BorderLayout());
        pnlPrincipal.add(pnlOpciones, BorderLayout.NORTH);
        pnlPrincipal.add(pnlGrafica, BorderLayout.CENTER);

        getContentPane().add(tb, BorderLayout.NORTH);
        getContentPane().add(pnlPrincipal, BorderLayout.CENTER);
    }

    //getters
    public LocalDate getFechaSeleccionada() {
        return dccFechaEspecifica.getSelectedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    // boton angel
    public void setCalcularExtremosClick(ActionListener evento) {
        btnCalcularExtremos.addActionListener(evento);
    }

    // boton milo
    public void setGraficarClick(ActionListener evento) {
        btnGraficar.addActionListener(evento);
    }

    //actualiza resultados
    public void mostrarExtremos(String masCalurosa, String menosCalurosa) {
        lblMasCalurosa.setText("Más calurosa: " + masCalurosa);
        lblMenosCalurosa.setText("Menos calurosa: " + menosCalurosa);
    }
}