package controladores;

import modelos.RegistroTemperatura;
import servicios.TemperaturasServicio;
import vistas.TemperaturasVista;
import javax.swing.*;
import java.time.LocalDate;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import java.util.Map;

public class TemperaturasControlador {

    private final TemperaturasVista vista;
    private List<RegistroTemperatura> registros;

    public TemperaturasControlador(TemperaturasVista vista) {
        cargarDatos();
        this.vista = vista;
        this.vista.setCalcularExtremosClick(evento -> procesarExtremos());
        this.vista.setGraficarClick(evento -> procesarGrafica());
    }

    private void cargarDatos() {
        String rutaBase = System.getProperty("user.dir");
        String nombreArchivo = rutaBase + "/src/datos/Temperaturas.csv";
        registros = TemperaturasServicio.getDatos(nombreArchivo);

        if (registros.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Error: No se encontraron datos en el CSV.");
        }
    }

    private void procesarExtremos() {
        LocalDate fecha = vista.getFechaSeleccionada();

        RegistroTemperatura maxima = TemperaturasServicio.getMasCalurosaPorFecha(registros, fecha);
        RegistroTemperatura minima = TemperaturasServicio.getMenosCalurosaPorFecha(registros, fecha);

        if (maxima != null && minima != null) {
            vista.mostrarExtremos(
                    maxima.getCiudad() + " (" + maxima.getTemperatura() + "°C)",
                    minima.getCiudad() + " (" + minima.getTemperatura() + "°C)");
        } else {
            vista.mostrarExtremos("Sin datos para esta fecha", "Sin datos para esta fecha");
        }
    }

    private void procesarGrafica() {
        LocalDate fechaInicio = vista.getFechaInicio();
        LocalDate fechaFin = vista.getFechaFin();

        if (fechaInicio.isAfter(fechaFin)) {
            JOptionPane.showMessageDialog(vista, "La fecha inicial no puede ser posterior a la final.");
            return;
        }

        Map<String, Double> promedios = TemperaturasServicio.getPromedioPorCiudad(registros, fechaInicio, fechaFin);

        if (promedios.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "No hay datos en ese rango de fechas.");
            return;
        }

        DefaultCategoryDataset datos = new DefaultCategoryDataset();
        promedios.forEach((ciudad, promedio) -> datos.addValue(promedio, "Temperatura", ciudad));

        JFreeChart grafica = ChartFactory.createBarChart(
                "Temperatura promedio por ciudad",
                "Ciudad",
                "Temperatura (°C)",
                datos,
                PlotOrientation.VERTICAL,
                false, true, false);

        vista.mostrarGrafica(grafica);
    }
}
