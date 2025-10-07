package e2;

public class ClienteDetallado implements Observador {
    @Override
    public void actualizar(String simbolo, DatosAccion datos) {
        System.out.println("Cliente detallado - " + simbolo + ": Cierre = " + datos.getCierre() + ", Máximo = " + datos.getMaximo() + ", Mínimo = " + datos.getMinimo() + ", Volumen = " + datos.getVolumen());
    }
}

