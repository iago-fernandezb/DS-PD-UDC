package e2;

import java.util.ArrayList;
import java.util.List;

public class Mercado {
    private static Mercado instancia;
    private List<Observador> observadores = new ArrayList<>();

    private Mercado() {}

    public static Mercado getInstancia() {
        if (instancia == null) {
            instancia = new Mercado();
        }
        return instancia;
    }

    public void agregarObservador(Observador obs) {
        observadores.add(obs);
    }

    public void eliminarObservador(Observador obs) {
        observadores.remove(obs);
    }

    public void notificarObservadores(Accion accion) {
        for (Observador obs : observadores) {
            obs.actualizar(accion.getSimbolo(), accion.getDatos());
        }
    }
}
