package e1;

public class EnEjercicio implements EstadoBarco{
    @Override
    public void realizarEjercicio(Barco barco){
        System.out.println("El barco " + barco.getNombre() + " ya esta realizando un ejercicio.\n");
    }

    @Override
    public void volverBase(Flota flota, Barco barco){
        System.out.println("El barco " + barco.getNombre() + " acabara con el ejercicio.\n");
        barco.incrementarMisiones();
        flota.fondos.aumentarFondos(barco);
        barco.setEstado(new Operativo());
    }

    @Override
    public void danar(Barco barco) {
        System.out.println("El barco " + barco.getNombre() + " ha sido dañado.\n");
        barco.setEstado(new Danado());
    }

    @Override
    public void reparar(Flota flota, Barco barco){
        System.out.println("El barco " + barco.getNombre() + " no puede ser mandado a reparacion (Esta en un ejercicio).\n");
    }

    @Override
    public void desmantelar(Barco barco){
        System.out.println("El barco " + barco.getNombre() + " no puede ser desmantelado (Esta en un ejercicio).\n");
    }

    @Override
    public void hundir(Barco barco){
        System.out.println("El barco " + barco.getNombre() + " ha sido hundido.\n");
        barco.setEstado(new Inactivo("Hundido"));
    }
}
