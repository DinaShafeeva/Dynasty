package ru.itis.dynasty.dto;

public class UserStatDTO {
    private String name;
    private int victories;
    private int defeats;

    private UserStatDTO(String name, int victories, int defeats) {
        this.name = name;
        this.victories = victories;
        this.defeats = defeats;
    }

    public String getName() {
        return name;
    }

    public int getVictories() {
        return victories;
    }

    public int getDefeats() {
        return defeats;
    }

    public static class Builder {
        private String name;
        private int victories;
        private int defeats;

        public Builder() {
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setVictories(int victories) {
            this.victories = victories;
            return this;
        }

        public Builder setDefeats(int defeats) {
            this.defeats = defeats;
            return this;
        }

        public UserStatDTO build() {
            return  new UserStatDTO(this.name, this.victories, this.defeats);
        }

    }
}
