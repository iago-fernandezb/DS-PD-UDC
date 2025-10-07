package e1;

public interface EstadoBarco {
    void realizarEjercicio(Barco barco);
    void volverBase(Flota flota, Barco barco);
    void danar(Barco barco);
    void reparar(Flota flota, Barco barco);
    void desmantelar(Barco barco);
    void hundir(Barco barco);
}
