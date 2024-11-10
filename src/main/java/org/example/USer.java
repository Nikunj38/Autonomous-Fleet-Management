package org.example;

public class USer {
    private int id;
    private String password;
    private int amount;
    private int loan_amount;
    private String firstName;
    private String lastName;

    public USer(int id, String password, String firstName, String lastName,int amount, int loan_amount) {
        this.id = id;
        this.password = password;
        this.amount = amount;
        this.loan_amount = loan_amount;
        this.firstName = firstName;
        this.lastName = lastName;

    }

    public int getId(){
        return id;
    }
    public void setId(){
        this.id = id;
    }
    public String  getPassword(){
        return password;
    }
    public void setPassword(){
        this.password = password;
    }
    public int getAmount(){
        return amount;
    }
    public void setAmount(){
        this.amount = amount;
    }
    public int getLoan_amount(){
        return loan_amount;
    }
    public void setLoan_amount(){
        this.loan_amount = loan_amount;
    }
    public String getFirstname(){
        return firstName;
    }
    public void setFirstName(){
        this.firstName = firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public void setLastName(){
        this.lastName = lastName;
    }
    @Override
    public String toString() {
        return "User [id=" + id + ", firstName=" + firstName + ", lastName="
                + lastName + ", amount=" + amount + ", firstName=" + firstName + ", lastName=" + lastName + ", loan_amount=" + loan_amount +"]";
    }
}
