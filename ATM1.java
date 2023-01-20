package package1;
import java.sql.*;
import java.util.Scanner;
      public class ATM1 {	
	    static Scanner check= new Scanner(System.in);
	    public static void main(String[] args) {
	        try{
	            Scanner x = new Scanner(System.in);
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
	            Statement statement = con.createStatement();
	             //creating customers table
	             //String table_1 ="CREATE TABLE customers(pin INT(4) PRIMARY KEY, acc_number BIGINT(10) UNIQUE, cust_name VARCHAR(30), acc_balance BIGINT(12))";
                     // statement.execute(table_1);

	            // initializing/ inserting default values in the customers table
	            //String default ="INSERT INTO customers(pin, acc_number, cust_name, acc_balance) VALUES (2468, 23467812, 'LUSWATA ANDREW', 20000000)," +
                    //"(1357, 54372832, 'KIKOMEKO BASHIR',60000000),(1593,24352636, 'MUGOYA SEAN',500000),(2023,34545366,'KISAKYE SONIA', 220000)," +
                    //"(4444, 12345678, 'BIGO SALAES', 95900000)";
                    //statement.execute(default);

	            System.out.println("\tWELCOME TO OUR ATM SERVICES\nPlease Press 1 to login to your account");
	            int response = x.nextInt();
	            x.nextLine();
	            int PIN = 0, acc_Number = 0;
	            if (response == 1) {
	                // Account number
	                System.out.print("Enter your Account number :");
	                System.out.println();
	                acc_Number = x.nextInt();
	                
	                // Personal Identification Number (PIN)
	                System.out.print("Enter your account PIN :");
	                System.out.println();
	                PIN = x.nextInt();
	            }else{
	                System.out.println("Wrong account number or PIN! ");
	            }
	            
	            String auth = "SELECT * FROM customers WHERE pin=? AND acc_number=?";
	            PreparedStatement ps = con.prepareStatement(auth);
	            ps.setInt(1, PIN);
	            ps.setInt(2, acc_Number);
	            ResultSet resultSet= ps.executeQuery();
	            
	            if (!resultSet.isBeforeFirst()){
	                System.out.println("!!!! INCORRECT ACCOUNT DETAILS.PLEASE ENTER YOUR CORRECT ACCOUNT DETAILS !!!!");
	            }else {           
	                String traced_name = null;
	                int traced_pin = 0, traced_acNo = 0, traced_accBal = 0;
	                while(resultSet.next()){
	                    traced_pin=resultSet.getInt(1);
	                    traced_acNo=resultSet.getInt(2);
	                    traced_name= resultSet.getString(3);
	                    traced_accBal=resultSet.getInt(4);

	                }
	                System.out.println("WELCOME  "+ traced_name);
	                
	                customer(traced_name,traced_pin,traced_accBal,traced_acNo);
	                
	            }

	        } catch (SQLException e) {
	            System.out.println(e);
	        } catch (ClassNotFoundException e) {
	            System.out.println(e);
	        }
	    }

	    public static void customer(String mName, int mPin, int mAcc_Bal, int acc_No) throws SQLException {
	        System.out.println("Select an option ;\n1:Deposit Money\n2: Withdraw Money\n3: View Account Balance.");
	        int response= check.nextInt();
	        check.nextLine();
	        if (response == 1) {
	            deposit(mAcc_Bal, mPin);
	        } else if (response==2) {
	            withdraw(mAcc_Bal, mPin);
	        } else if (response==3) {
	            checkBalance(mAcc_Bal);
	        }else {
	            System.out.println("INVALID RESPONSE");
	            System.exit(0);
	        }
	    }
	    private static void checkBalance(int mAcc_Bal) {
	        System.out.println("Your account balance is: Ush"+mAcc_Bal);
	    }
	    private static void withdraw(int mAcc_Bal, int pin) throws SQLException {
	        System.out.print("Withdraw cash\n Amount: ");
	        int withdraw=check.nextInt();
	        check.nextLine();
	        if (withdraw > mAcc_Bal) {
	            System.out.println("Insufficient Balance!!!!!!!!!!");
	        }else {
	            int new_Bal = mAcc_Bal - withdraw;
	            System.out.println("Ush"+ withdraw +" successfully withdrawn!");
	            update(new_Bal, pin);
	        }
	    }

	    private static void deposit(int mAcc_Bal,int pin ) throws SQLException {
	        System.out.print(" Enter the amount you want to deposit :");
	        int deposit= check.nextInt();
	        check.nextLine();
	        int new_acc_Bal = mAcc_Bal + deposit;
	        update(new_acc_Bal,pin);
	        System.out.println("Deposit transaction was successful\n Thank you for banking with us !)");
	    }
	    private static void update(int new_Bal, int pin) throws SQLException {
	        Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
	        String acc_update=" UPDATE customers SET acc_balance=? WHERE pin=?";
	        PreparedStatement p =con1.prepareStatement(acc_update);
	        p.setInt(1, new_Bal );
	        p.setInt(2, pin);
	        p.execute();
	    }
	}



