package Exceptions;

public class JugadorExistente extends RuntimeException
{
    public JugadorExistente(String message) {
        super(message);
    }
}
