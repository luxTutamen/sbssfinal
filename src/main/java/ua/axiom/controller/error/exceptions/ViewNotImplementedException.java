package ua.axiom.controller.error.exceptions;

public class ViewNotImplementedException extends RuntimeException {
    private String viewName;

    public ViewNotImplementedException(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public String getMessage() {
        return "View for <" + viewName + "> is not implemented" + super.getMessage();
    }
}
