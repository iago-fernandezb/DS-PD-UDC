package e1;

public class Operativo implements EstadoBarco{
    @Override
    public void realizarEjercicio(Barco barco){
        System.out.println("El barco " + barco.getNombre() + " realizara un ejercicio.\n");
        barco.setEstado(new EnEjercicio());
    }

    @Override
    public void volverBase(Flota flota, Barco barco){
        System.out.println("El barco " + barco.getNombre() + " ya esta en estado en la base.\n");
    }

    @Override
    public void danar(Barco barco) {
        System.out.println("El barco " + barco.getNombre() + " no puede ser dañado (Esta en la base).\n");
    }

    @Override
    public void reparar(Flota flota, Barco barco){
        System.out.println("El barco " + barco.getNombre() + " sera mandado a reparacion.\n");
        if(flota.fondos.disminuirFondos(barco)) {
            barco.setEstado(new EnReparacion());
        }
        else{
            System.out.println("El barco " + barco.getNombre() + " no puede reparar (No hay presupuesto).\n");
        }
    }

    @Override
    public void desmantelar(Barco barco){
        System.out.println("El barco " + barco.getNombre() + " ha sido desmantelado.\n");
        barco.setEstado(new Inactivo("Desmantelado"));
    }

    @Override
    public void hundir(Barco barco){
        System.out.println("El barco " + barco.getNombre() + " no puede ser hundido (Esta en la base).\n");
    }
}
