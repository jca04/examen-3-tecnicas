package vistas;

import datechooser.beans.DateChooserCombo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

public class TemperaturasVista extends JFrame {

    private DateChooserCombo dccFechaEspecifica;
    private JButton btnCalcularExtremos;
    private JButton btnGraficar;
    private JLabel lblMasCalurosa;
    private JLabel lblMenosCalurosa;
    private JPanel pnlGrafica;
    private DateChooserCombo dccFechaInicio;
    private DateChooserCombo dccFechaFin;

    public TemperaturasVista() {
        setTitle("Análisis de Temperaturas");
        setSize(800, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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

        pnlOpciones.add(new JLabel("Desde:"));
        dccFechaInicio = new DateChooserCombo();
        pnlOpciones.add(dccFechaInicio);

        pnlOpciones.add(new JLabel("Hasta:"));
        dccFechaFin = new DateChooserCombo();
        pnlOpciones.add(dccFechaFin);

        pnlOpciones.add(new JLabel("Fecha Específica:"));
        dccFechaEspecifica = new DateChooserCombo();
        pnlOpciones.add(dccFechaEspecifica);

        lblMasCalurosa = new JLabel("Más calurosa: -");
        lblMenosCalurosa = new JLabel("Menos calurosa: -");

        pnlOpciones.add(lblMasCalurosa);
        pnlOpciones.add(lblMenosCalurosa);

        pnlGrafica = new JPanel(new BorderLayout());
        pnlGrafica.setBorder(BorderFactory.createTitledBorder("área de gráfica"));

        JPanel pnlPrincipal = new JPanel(new BorderLayout());
        pnlPrincipal.add(pnlOpciones, BorderLayout.NORTH);
        pnlPrincipal.add(pnlGrafica, BorderLayout.CENTER);

        getContentPane().add(tb, BorderLayout.NORTH);
        getContentPane().add(pnlPrincipal, BorderLayout.CENTER);
    }

    public LocalDate getFechaSeleccionada() {
        return dccFechaEspecifica.getSelectedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public LocalDate getFechaInicio() {
        return aLocalDate(dccFechaInicio);
    }

    public LocalDate getFechaFin() {
        return aLocalDate(dccFechaFin);
    }

    public void setCalcularExtremosClick(ActionListener evento) {
        btnCalcularExtremos.addActionListener(evento);
    }

    public void setGraficarClick(ActionListener evento) {
        btnGraficar.addActionListener(evento);
    }

    public void mostrarExtremos(String masCalurosa, String menosCalurosa) {
        lblMasCalurosa.setText("Más calurosa: " + masCalurosa);
        lblMenosCalurosa.setText("Menos calurosa: " + menosCalurosa);
    }

    public void mostrarGrafica(JFreeChart grafica) {
        pnlGrafica.removeAll();
        pnlGrafica.add(new ChartPanel(grafica), BorderLayout.CENTER);
        pnlGrafica.revalidate();
        pnlGrafica.repaint();
    }

    private LocalDate aLocalDate(DateChooserCombo dcc) {
        return dcc.getSelectedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}