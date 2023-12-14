
//import Commerce.AccountSignIn;

package Commerce;

//import java.awt.Toolkit;
//import java.awt.event.WindowEvent;


//import Commerce.AccountSignIn;
//import Commerce.AccountSignUp;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author Admin
 */
public class Main {
    
    public static boolean Function_Account;
    
    public static boolean Status_Username_ON = false;
    public static String USERNAME_STATEMENT = "User:";
    public static String user;
    public static int USERS_ID;
    
    
    /*
    public void close(){
        WindowEvent closeW = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeW);
    }
    */

    public static void main(String[] args) {
        //productFrm frm = new productFrm();
        //frm.setVisible(true);
        //AccountSignIn j = new AccountSignIn();
        //j.setVisible(true);
        
        productFrm jj = new productFrm();
        jj.setVisible(true);
    }
}