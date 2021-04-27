package server.exceptions;

public class FileIsNotFoundException extends RuntimeException{
    public FileIsNotFoundException() {
        super("No such file");
    }
}
