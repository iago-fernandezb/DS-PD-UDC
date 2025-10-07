package e1;

public class Danado implements EstadoBarco{
    @Override
    public void realizarEjercicio(Barco barco){
        System.out.println("El barco " + barco.getNombre() + " no puede realizar un ejercicio (Esta dañado).\n");
    }

    @Override
    public void volverBase(Flota flota, Barco barco){
        System.out.println("El barco " + barco.getNombre() + " no puede volver a la base (Esta dañado).\n");
    }

    @Override
    public void danar(Barco barco) {
        System.out.println("El barco " + barco.getNombre() + " no puede ser dañado (Ya esta dañado).\n");
    }

    @Override
    public void reparar(Flota flota, Barco barco){
        if(flota.fondos.disminuirFondos(barco)) {
            System.out.println("El barco " + barco.getNombre() + " se mandara a reparar.\n");
            barco.setEstado(new EnReparacion());
        }
        else{
            System.out.println("El barco " + barco.getNombre() + " no puede reparar (No hay presupuesto).\n");
        }
    }

    @Override
    public void desmantelar(Barco barco){
        System.out.println("El barco " + barco.getNombre() + " sera desmantelado.\n");
        barco.setEstado(new Inactivo("Desmantelado"));
    }

    @Override
    public void hundir(Barco barco){
        System.out.println("El barco " + barco.getNombre() + " no se puede hundir.\n");
    }
}
