package es.uji.EI1017.Programacion_Avanzada.Excepciones;

public class LikedItemNotFoundException extends RuntimeException {
    public LikedItemNotFoundException(String item) {
        super("El elemento '" + item + "' no se encuentra en la lista.");
    }
}
