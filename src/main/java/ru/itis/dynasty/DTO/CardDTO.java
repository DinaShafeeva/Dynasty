package ru.itis.dynasty.DTO;

public class CardDTO {
    private String name;
    private int power;
    private int protection;

    private CardDTO(String name, int power, int protection) {
        this.name = name;
        this.power = power;
        this.protection = protection;
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    public int getProtection() {
        return protection;
    }


    public static class Builder {
        private String name;
        private int power;
        private int protection;

        private Builder() {
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }
        public Builder setPower(int power) {
            this.power = power;
            return this;
        }
        public Builder setProtection(int protection) {
            this.protection = protection;
            return this;
        }
        public CardDTO build() {
            return  new CardDTO(this.name, this.power, this.protection);
        }

    }
}
