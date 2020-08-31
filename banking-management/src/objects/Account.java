package objects;

/*

Frequent errors:

- Input error, mismatch data, Scanner input = "john", data type verification String, problems with Object types.
    - Solution: Using method equals before '==' for verifying information.

*/

public class Account {

    // User private data
    private String firstName;
    private String lastName;
    private String password;
    private String username;
    private double balance;

    // Operation Status
    final double balanceTreshold = -100.00;
    boolean successfullOperation;

    // Account constructor for creating account, no DB
    public Account(String uN, String uP, String fN, String lN){
        this.username = uN;
        this.password = uP;
        this.firstName = fN;
        this.lastName = lN;
    }

    // Different methods for deposit, withdraw and getting balance and data from account.

    public boolean Deposit(double dV){
        try{
            if(dV > 0){
                this.balance += dV;
                successfullOperation = true;
            } else {
                successfullOperation = false;
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return successfullOperation;
    }

    public boolean Withdraw(double wV){
        try {
            if((this.balance - wV) < this.balanceTreshold){
                successfullOperation = false;
            } else {
                this.balance -= wV;
                successfullOperation = true;
            }
        } catch(Exception e) {
            System.out.println(e);
        }
        return successfullOperation;
    }

    public double getBalance(){
        return this.balance;
    }

    public String getData(int x){
        switch(x){
            case 0:
                return this.firstName;
            case 1:
                return this.lastName;
            case 2:
                return this.username;
            case 3:
                return this.password;
            default:
                return "Default";
        }
    }

    // Login method for checking the inputs of the customer if match with the data of the account. No security implementation.

    public boolean logIn(Account a,String uName, String uPass){
        try {
            if(uName.equals(a.getData(2)) && uPass.equals(a.getData(3))){
                successfullOperation = true;
            } else {
                System.out.println(a.getData(2));
                System.out.println(a.getData(3));
                System.out.println(uName);
                System.out.println(uPass);
                successfullOperation = false;
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return successfullOperation;
    }

}
