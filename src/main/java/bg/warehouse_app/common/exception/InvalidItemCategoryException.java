package bg.warehouse_app.common.exception;

public class InvalidItemCategoryException extends RuntimeException {

    public InvalidItemCategoryException(String message) {
        super(message);
    }
}
