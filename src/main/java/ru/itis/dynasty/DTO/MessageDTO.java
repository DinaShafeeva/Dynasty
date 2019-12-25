package ru.itis.dynasty.DTO;

public class MessageDTO {
    private String textMessage;

    private MessageDTO(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getTextMessage() {
        return textMessage;
    }

     public static class Builder {

        private String textMessage;

        public Builder setTextMessage(String textMessage) {
            this.textMessage = textMessage;

            return this;
        }

        public MessageDTO build() {
            return new MessageDTO(this.textMessage);
        }

    }
}
