package com.gigachatmvc.chatbot;

public enum BotMessages {
    HelloMessage("Здраствуйте, я ваш личный помошник. Уже занимаюсь решением вашей проблемы."),
    TransferMessage("Вас понял. Зову менеджера вам на помощь."),
    UnRecognizedMessage("Извините, я не смог понять ваше сообщение. Попробуйте описать свою проблему иначе.");

    public final String message;

    BotMessages(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
