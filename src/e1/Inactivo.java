package e1;

public class Inactivo implements EstadoBarco{
    private final String razon;

    public Inactivo(String razon) {
        this.razon = razon;
    }

    @Override
    public void realizarEjercicio(Barco barco) {
        System.out.println("El barco " + barco.getNombre() + " no puede realizar ejercicios porque está inactivo (" + razon + ").");
    }

    @Override
    public void volverBase(Flota flota, Barco barco) {
        System.out.println("El barco " + barco.getNombre() + " no puede volver a base porque está inactivo (" + razon + ").");
    }

    @Override
    public void danar(Barco barco) {
        System.out.println("El barco " + barco.getNombre() + " no puede ser dañado (" + razon + ").");
    }

    @Override
    public void reparar(Flota flota, Barco barco) {
        System.out.println("El barco " + barco.getNombre() + " no puede ser reparado porque está inactivo (" + razon + ").");
    }

    @Override
    public void desmantelar(Barco barco) {
        System.out.println("El barco " + barco.getNombre() + " ya está desmantelado o fuera de servicio (" + razon + ").");
    }

    @Override
    public void hundir(Barco barco) {
        System.out.println("El barco " + barco.getNombre() + " ya está inactivo (" + razon + "). No se puede hundir de nuevo.");
    }

    public String getRazon(Barco barco) {
        return this.razon;
    }
}
