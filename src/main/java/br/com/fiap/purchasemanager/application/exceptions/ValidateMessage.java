package br.com.fiap.purchasemanager.application.exceptions;

public class ValidateMessage {

    private String campo;
    private String message;

    public ValidateMessage() {}

    public ValidateMessage(String campo, String message) {
        this.campo = campo;
        this.message = message;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
