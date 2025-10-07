package e1;

public class EnReparacion implements EstadoBarco{
    @Override
    public void realizarEjercicio(Barco barco){
        System.out.println("El barco " + barco.getNombre() + " no puede realizar un ejercicio (Esta en reparacion).\n");
    }

    @Override
    public void volverBase(Flota flota, Barco barco){
        System.out.println("El barco " + barco.getNombre() + " volvera a base.\n");
        barco.setEstado(new Operativo());
    }

    @Override
    public void danar(Barco barco) {
        System.out.println("El barco " + barco.getNombre() + " se ha dejado de reparar. (Esta en reparacion).\n");
        barco.setEstado(new Danado());
    }

    @Override
    public void reparar(Flota flota, Barco barco){
        System.out.println("El barco " + barco.getNombre() + " ya esta siendo reparado.\n");
    }

    @Override
    public void desmantelar(Barco barco){
        System.out.println("El barco " + barco.getNombre() + " no se desmantelar (Esta siendo reparado).\n");
        barco.setEstado(new Inactivo("Desmantelado"));
    }

    @Override
    public void hundir(Barco barco){
        System.out.println("El barco " + barco.getNombre() + " no puede ser hundido (Esta siendo reparado).\n");
    }
}
