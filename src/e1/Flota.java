package e1;

import java.util.ArrayList;
import java.util.List;

public class Flota {
    private final List<Barco> barcos;
    public Fondos fondos;

    public Flota(double fondosIniciales) {
        this.barcos = new ArrayList<>();
        this.fondos = new Fondos(fondosIniciales);
    }

    public void agregarBarco(Barco barco) {
        barcos.add(barco);
    }

    public void listarActivos() {
        System.out.println("Activos");
        for (Barco barco : barcos) {
            if (barco.getEstado() instanceof Operativo || barco.getEstado() instanceof EnReparacion) {
                System.out.println(barco.getNombre() + " (" + barco.getTipo() + ") | Estado: " + barco.getEstado().getClass().getSimpleName());
            }
        }
    }

    public int listarInactivos() {
        System.out.println("Inactivos");
        int count = 0;
        for (Barco barco : barcos) {
            if (barco.getEstado() instanceof Inactivo) {
                count++;
                Inactivo estado = (Inactivo) barco.getEstado();
                System.out.println(barco.getNombre() + " (" + barco.getTipo() + ") | Razón: " + estado.getRazon(barco) + " | Misiones: " + barco.getNmisiones());
            }
        }
        return count;

    }

    public int listarEnReparacion() {
        System.out.println("Barcos en reparación:");
        int count = 0; // Contador de barcos en reparación.
        for (Barco barco : barcos) {
            if (barco.getEstado() instanceof EnReparacion) {
                count++;
            }
        }
        return count;
    }

    public double getFondos() {
        return fondos.getFondos();
    }


}
