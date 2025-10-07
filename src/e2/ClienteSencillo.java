package e2;

public class ClienteSencillo implements Observador {
    @Override
    public void actualizar(String simbolo, DatosAccion datos) {
        System.out.println("Cliente sencillo - " + simbolo + ": Cierre = " + datos.getCierre());
    }
}
