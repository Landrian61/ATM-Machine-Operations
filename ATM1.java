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
	            //String customer_table="CREATE TABLE customers(pin INT(4) PRIMARY KEY, acc_number BIGINT(8) UNIQUE, cust_name VARCHAR(24), acc_balance BIGINT(12))";
               // statement.execute(customer_table);

               // String drop="DROP TABLE customer";
	           // statement.execute(drop);

	            // initializing the customers table
	            
	            
	           //String cust_insert="INSERT INTO customers(pin, acc_number, cust_name, acc_balance) VALUES (2468, 23467812, 'LUSWATA ANDREW', 20000000)," +
                //   "(1357, 54372832, 'KIKOMEKO BASHIR',60000000),(1593,24352636, 'MUGOYA SEAN',500000),(2023,34545366,'KISAKYE SONIA', 220000)," +
                 // "(4444, 12345678, 'BIGO SALAES', 95900000)";
             //statement.execute(cust_insert);

	            System.out.println("\tWELCOME TO OUR ATM SERVICES\nPlease Press 1 to login to your account");
	            int response = x.nextInt();
	            x.nextLine();
	            
	            int PIN = 0, acc_Number = 0;
	            if (response == 1) {
	                // account number
	                System.out.print("Enter your Account number :");
	                System.out.println();
	                acc_Number = x.nextInt();
	                
	                // Personal Identification Number
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
	                
	                
	                String retrieved_name = null;
	                int retrieved_pin = 0, retrieved_acNo = 0, retrieved_accBal = 0;
	                while(resultSet.next()){
	                    retrieved_pin=resultSet.getInt(1);
	                    retrieved_acNo=resultSet.getInt(2);
	                    retrieved_name= resultSet.getString(3);
	                    retrieved_accBal=resultSet.getInt(4);

	                }
	                System.out.println("WELCOME "+retrieved_name);
	                
	                actions(retrieved_name,retrieved_pin,retrieved_accBal,retrieved_acNo);
	                
	            }

	        } catch (SQLException e) {
	            System.out.println(e);
	        } catch (ClassNotFoundException e) {
	            System.out.println(e);
	        }
	    }

	    public static void actions(String mName, int mPin, int mAcc_Bal, int acc_No) throws SQLException {
	        System.out.println("Select an option ;\n1:Deposit Money\n2: Withdraw Money\n3: View Account Balance.");
//	        Scanner scanner1=new Scanner(System.in);
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
	        int withdrawal=check.nextInt();
	        check.nextLine();
	        if (withdrawal > mAcc_Bal) {
	            System.out.println("Insufficient Balance!!!!!!!!!!");
	        }else {
	            int new_Bal=mAcc_Bal-withdrawal;
	            System.out.println("Ush"+withdrawal+" successfully withdrawn!");
	            
	            update(new_Bal, pin);

	        }
	    }

	    private static void deposit(int mAcc_Bal,int pin ) throws SQLException {
	        System.out.print(" Enter the amount you want to deposit :");
	        int deposit_amount= check.nextInt();
	        check.nextLine();
	        int new_acc_Bal=mAcc_Bal+deposit_amount;
	        update(new_acc_Bal,pin);
	        System.out.println("Deposit transaction was successful\nNew account Balance: Ush"+new_acc_Bal+"\n Thank you for banking with us !)");

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



