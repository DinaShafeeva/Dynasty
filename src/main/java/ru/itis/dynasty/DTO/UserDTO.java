package ru.itis.dynasty.DTO;

public class UserDTO {
    private String name;

    private UserDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static class Builder {
        private String name;

        private Builder() {
        }

        public Builder setName(String name) {
           this.name = name;
            return this;
        }

        public UserDTO build() {
            return  new UserDTO(this.name);
        }

    }
}
