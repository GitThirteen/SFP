package exceptions;

public class FolderIsEmptyException extends Exception {
    public FolderIsEmptyException(String error) {
        super(error);
    }
}
