package com.mediapermissionlib.byfoysaltechyt;

public class DialogConfig {
    private String title;
    private String message;
    private String positiveButtonText;
    private String negativeButtonText;

    public DialogConfig(String title, String message, String positiveButtonText, String negativeButtonText) {
        this.title = title;
        this.message = message;
        this.positiveButtonText = positiveButtonText;
        this.negativeButtonText = negativeButtonText;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getPositiveButtonText() {
        return positiveButtonText;
    }

    public String getNegativeButtonText() {
        return negativeButtonText;
    }
}
