package e2;

import java.util.ArrayList;
import java.util.List;

public class Accion {
    private String simbolo;
    private DatosAccion datos;

    public Accion(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public DatosAccion getDatos() {
        return datos;
    }

    public void actualizarDatos(double cierre, double maximo, double minimo, int volumen) {
        if (cierre < 0 || maximo < 0 || minimo < 0 || volumen < 0) {
            throw new IllegalArgumentException("Datos no válidos: los valores no pueden ser negativos.");
        }
        this.datos = new DatosAccion(cierre, maximo, minimo, volumen);
        Mercado.getInstancia().notificarObservadores(this);
    }

    public static DatosAccion calcularMaximosMinimos(List<Double> valores) {
        double maximo = valores.stream().max(Double::compare).orElse(Double.NaN);
        double minimo = valores.stream().min(Double::compare).orElse(Double.NaN);
        return new DatosAccion(0, maximo, minimo, 0);
    }
}
