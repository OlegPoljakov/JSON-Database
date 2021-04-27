package server.exceptions;

public class FileIsEmptyException extends RuntimeException {
    public FileIsEmptyException() {
        super("No data in a file");
    }
}
