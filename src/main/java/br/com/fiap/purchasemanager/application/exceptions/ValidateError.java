package br.com.fiap.purchasemanager.application.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidateError extends StandardError{

    private List<ValidateMessage> messages = new ArrayList<>();

    public List<ValidateMessage> getMessages() {
        return messages;
    }

    public void addMessages(String campo, String message) {
        messages.add(new ValidateMessage(campo, message));
    }
}
