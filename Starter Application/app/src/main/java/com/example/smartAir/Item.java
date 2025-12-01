package com.example.smartAir;

public class Item {

    private String name;
    private String password;
    private String dateofbirth;
    private String optionalnote;

    public Item() {}

    public Item(String Name, String password, String dateOfBirth, String optionalnote) {
        this.name = Name;
        this.password = password;
        this.dateofbirth = dateOfBirth;
        this.optionalnote = optionalnote;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getDateOfBirth() { return dateofbirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateofbirth = dateOfBirth; }
    public String getOptionalnote() { return optionalnote; }
    public void setOptionalnote(String optionalnote) { this.optionalnote = optionalnote; }
}
