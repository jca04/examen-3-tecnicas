package servicios;

import modelos.RegistroTemperatura;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Map;

public class TemperaturasServicio {

    public static List<RegistroTemperatura> getDatos(String nombreArchivo) {
        var formatoFecha = DateTimeFormatter.ofPattern("d/M/yyyy");
        try (Stream<String> lineasArchivo = Files.lines(Paths.get(nombreArchivo))) {
            return lineasArchivo.skip(1)
                    .map(linea -> linea.split(","))
                    .map(textos -> new RegistroTemperatura(
                            textos[0].trim(),
                            LocalDate.parse(textos[1].trim(), formatoFecha),
                            Double.parseDouble(textos[2].trim())
                    ))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    public static RegistroTemperatura getMasCalurosaPorFecha(List<RegistroTemperatura> registros, LocalDate fecha) {
        return registros.stream()
                .filter(r -> r.getFecha().equals(fecha))
                .max(Comparator.comparingDouble(RegistroTemperatura::getTemperatura))
                .orElse(null);
    }

    public static RegistroTemperatura getMenosCalurosaPorFecha(List<RegistroTemperatura> registros, LocalDate fecha) {
        return registros.stream()
                .filter(r -> r.getFecha().equals(fecha))
                .min(Comparator.comparingDouble(RegistroTemperatura::getTemperatura))
                .orElse(null);
    }

    public static Map<String, Double> getPromedioPorCiudad(List<RegistroTemperatura> registros, LocalDate fechaInicio, LocalDate fechaFin) {
        return registros.stream()
                .filter(r -> !r.getFecha().isBefore(fechaInicio) && !r.getFecha().isAfter(fechaFin))
                .collect(Collectors.groupingBy(
                    RegistroTemperatura::getCiudad,
                    Collectors.averagingDouble(RegistroTemperatura::getTemperatura)
                ));
    }
}
