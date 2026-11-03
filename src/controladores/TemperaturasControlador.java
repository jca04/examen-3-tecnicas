package controladores;

import modelos.RegistroTemperatura;
import servicios.TemperaturasServicio;
import vistas.TemperaturasVista;
import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class TemperaturasControlador {

    private final TemperaturasVista vista;
    private List<RegistroTemperatura> registros;

    public TemperaturasControlador(TemperaturasVista vista) {
        this.vista = vista;
        cargarDatos();
        
        this.vista.setCalcularExtremosClick(evento -> procesarExtremos());
    }

    private void cargarDatos() {
        String rutaBase = System.getProperty("user.dir");
        String nombreArchivo = rutaBase + "/src/datos/Temperaturas.csv";
        registros = TemperaturasServicio.getDatos(nombreArchivo);
        
        if(registros.isEmpty()) {
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
                    minima.getCiudad() + " (" + minima.getTemperatura() + "°C)"
            );
        } else {
            vista.mostrarExtremos("Sin datos para esta fecha", "Sin datos para esta fecha");
        }
    }
}
