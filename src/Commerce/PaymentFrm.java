/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Commerce;


import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.util.*;
/**
 *
 * @author DELL
 */
public class PaymentFrm extends javax.swing.JFrame {

    /**
     * Creates new form PaymentFrm
     */
    public PaymentFrm() {
        initComponents();
        connect();
//       
        
        
        SetPaymentInfo();
////    ShowMessage("he");
////        
    }

//    
    ResultSet rs;
    Connection con;
    Statement stmt;
    
    int sum;
    List<Integer> productList;
    List<Integer> buyQuantityList;
    List<Integer> priceList;
    List<Integer> supplierList;
    int balance;
    String userName;
    StringBuilder productDisplayList;
    String productName;
    boolean productIsAvailable;
    
    
    /**
     * Creates new form productFrm
     */
    public void close(){
        WindowEvent closeW = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeW);
    }
 
    private void connect(){
    String dbURL = "jdbc:sqlserver://localhost:1433;databaseName=Commerce;user=sa;password=sa;encrypt=false;trustServerCertificate=false";
        try {con = DriverManager.getConnection(dbURL); stmt = con.createStatement();

        } catch (SQLException e) {            
        }
    }
  
    private void SetPaymentInfo(){
        this.balance = 0;
        this.userName = "";
        this.productDisplayList = new StringBuilder();
        this.productIsAvailable = true;
        this.productName = ""; 
        this.priceList = new ArrayList<Integer>();
        this.productList = new ArrayList<Integer>();
        this.supplierList = new ArrayList<Integer>();
        this.buyQuantityList = new ArrayList<Integer>();
        this.sum = 0;
        
        //" + Main.USERS_ID + "
        String getPriceCode = "select Name, Product.ID, Price, Quantity_in_Queue, Remaining_Quantity, Supplier_Customer_ID  from Product, Cart where (Cart_ID = " + Main.USERS_ID + " and Cart.Product_ID = Product.ID);";
        String getUserInfoCode = "select User_Name, Wallet from Account where (ID = " + Main.USERS_ID + ");";
        String getProductCode = "select Name, Quantity_in_Queue from Product, Cart where (Cart_ID = " + Main.USERS_ID + " and Cart.Product_ID = Product.ID);";
        //String getSupplierBalance = "select Wallet from Account where (ID = " + this.supplierList[i] + ")";
        
        try{

            rs = stmt.executeQuery(getPriceCode);
            
            while(rs.next()){
                this.sum += rs.getInt("Price") * rs.getInt("Quantity_in_Queue");
                this.priceList.add(rs.getInt("Price") * rs.getInt("Quantity_in_Queue"));
                this.supplierList.add(rs.getInt("Supplier_Customer_ID"));
                this.productList.add(rs.getInt("ID"));
                this.buyQuantityList.add(rs.getInt("Quantity_in_Queue"));
                                
                if (rs.getInt("Quantity_in_Queue") > rs.getInt("Remaining_Quantity")){
                    this.productName = rs.getString("Name");
                    this.productIsAvailable = false;
                }

            }
            
            
            rs = stmt.executeQuery(getUserInfoCode);

            while(rs.next()){
                this.userName = rs.getString("User_Name");
                this.balance = rs.getInt("Wallet");
            }
            
            rs = stmt.executeQuery(getProductCode);
            ResultSetMetaData metaData = rs.getMetaData();
            int numberOfColumns = metaData.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= numberOfColumns; i++) {
                    this.productDisplayList.append(String.valueOf(rs.getObject(i))).append("\t");
                }
                this.productDisplayList.append("\n");
            }
//            
//            rs = stmt.executeQuery(getStockQuantityCode);
//
//            while(rs.next()){
//                if (rs.getInt("Quantity_in_Queue") > rs.getInt("Remaining_Quantity")){
//                    this.productName = rs.getString("Name");
//                    this.productIsAvailable = false;
//                    break;
//                }
//            }
            
        }catch (SQLException ex) {
            
        }
        
        PriceDisplay.setText(String.valueOf(this.sum));
        balanceDisplay.setText(String.valueOf(this.balance));
        userNameDisplay.setText(this.userName);
        productDisplay.setText(this.productDisplayList.toString());     
        
    }
    
    
    private int countOrder(){
        
        int res = 0;
        String code = "SELECT * FROM OrderI;" ;
        //System.out.println(code);

        String connect = "jdbc:sqlserver://localhost:1433;databaseName=Commerce;user=sa;password=sa;encrypt=false;trustServerCertificate=false;";

        try(Connection con = DriverManager.getConnection(connect);Statement stmt = con.createStatement();){
            ResultSet rs = stmt.executeQuery(code);
                while(rs.next()){
                res++;
            }
                rs.close();
        }catch(SQLException e){
        }finally{         
        }

        return res;
    }
    public void addOrder(){
       int tem = 1 + countOrder();
       String tmp = tem + "";
       
        String code = "INSERT INTO OrderI (Order_ID,Customer_ID, total_bill)"
                + " VALUES( " + tmp + "," + Main.USERS_ID +"," + this.sum + ");";
        


        String connect = "jdbc:sqlserver://localhost:1433;databaseName=Commerce;user=sa;password=sa;encrypt=false;trustServerCertificate=false;";

       try{
           Connection con = DriverManager.getConnection(connect);
           PreparedStatement stmt = con.prepareStatement(code);
           stmt.executeUpdate();

        }catch(SQLException e){
        } 
    
    }
    
    private int countPayment(){
        
        int res = 0;
        String code = "SELECT * FROM Payment;" ;
        //System.out.println(code);

        String connect = "jdbc:sqlserver://localhost:1433;databaseName=Commerce;user=sa;password=sa;encrypt=false;trustServerCertificate=false;";

        try(Connection con = DriverManager.getConnection(connect);Statement stmt = con.createStatement();){
            ResultSet rs = stmt.executeQuery(code);
                while(rs.next()){
                res++;
            }
                rs.close();
        }catch(SQLException e){
        }finally{         
        }

        return res;
    }
    public void addPayment(){
       int tem = 1 + countPayment();
       String tmp = tem + "";
       
        String code = "INSERT INTO Payment (Payment_ID,Order_ID, Customer_ID, Fee)"
                + " VALUES( " + tmp + "," + countOrder() + "," + Main.USERS_ID +  "," + this.sum + ");";
        


        String connect = "jdbc:sqlserver://localhost:1433;databaseName=Commerce;user=sa;password=sa;encrypt=false;trustServerCertificate=false;";

       try{
           Connection con = DriverManager.getConnection(connect);
           PreparedStatement stmt = con.prepareStatement(code);
           stmt.executeUpdate();

        }catch(SQLException e){
        } 
    
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        paymentFrmLabel = new javax.swing.JLabel();
        buyBtn = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        userNameDisplay = new javax.swing.JTextArea();
        jScrollPane6 = new javax.swing.JScrollPane();
        balanceDisplay = new javax.swing.JTextArea();
        jScrollPane7 = new javax.swing.JScrollPane();
        productDisplay = new javax.swing.JTextArea();
        priceDisplay = new javax.swing.JScrollPane();
        PriceDisplay = new javax.swing.JTextArea();
        depositeFrmBtn = new javax.swing.JButton();
        backBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        ratingBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setPreferredSize(new java.awt.Dimension(1060, 800));

        paymentFrmLabel.setText("Payment");

        buyBtn.setText("Buy");
        buyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buyBtnActionPerformed(evt);
            }
        });

        userNameDisplay.setEditable(false);
        userNameDisplay.setColumns(20);
        userNameDisplay.setRows(5);
        userNameDisplay.setText(" ");
        jScrollPane5.setViewportView(userNameDisplay);

        balanceDisplay.setEditable(false);
        balanceDisplay.setColumns(20);
        balanceDisplay.setRows(5);
        balanceDisplay.setText(" ");
        jScrollPane6.setViewportView(balanceDisplay);

        productDisplay.setEditable(false);
        productDisplay.setColumns(20);
        productDisplay.setRows(5);
        productDisplay.setText(" ");
        productDisplay.setToolTipText("");
        productDisplay.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane7.setViewportView(productDisplay);

        PriceDisplay.setEditable(false);
        PriceDisplay.setColumns(20);
        PriceDisplay.setRows(5);
        PriceDisplay.setText(" \n");
        priceDisplay.setViewportView(PriceDisplay);

        depositeFrmBtn.setText("Deposit");
        depositeFrmBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                depositeFrmBtnActionPerformed(evt);
            }
        });

        backBtn.setText("Back to main");
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });

        jLabel1.setText("User Name");

        jLabel2.setText("Balance");

        jLabel3.setText("Product");

        jLabel4.setText("Total Price");

        ratingBtn.setText("Rating (optional)");
        ratingBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ratingBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(205, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(ratingBtn)
                                .addGap(18, 18, 18)
                                .addComponent(backBtn))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(101, 101, 101)
                                        .addComponent(paymentFrmLabel)))
                                .addGap(0, 252, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(depositeFrmBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buyBtn))
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(priceDisplay, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(paymentFrmLabel)
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)))
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(priceDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buyBtn)
                    .addComponent(depositeFrmBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backBtn)
                    .addComponent(ratingBtn))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buyBtnActionPerformed
        // TODO add your handling code here:
//        System.out.println(Main.USERS_ID == 0);
//        System.out.println(this.productList.length() == 0);
//        System.out.println(this.productIsAvailable == false);
//        System.out.println(this.balance < this.sum);
        //" + (this.balance - this.sum) + "
        String updateCustomerBalance = "update Account set Wallet = " + (this.balance - this.sum) + " where (ID = " + Main.USERS_ID + ");";
         

        if(Main.USERS_ID == 0){
            JOptionPane.showMessageDialog(rootPane, "You haven't sign in");
        }else if(this.productDisplayList.length() == 0){
            JOptionPane.showMessageDialog(rootPane, "You haven't choose product");
        }else if(this.productIsAvailable == false){
            JOptionPane.showMessageDialog(rootPane, "Insufficient quantity of " + this.productName);
        }else if(this.balance < this.sum){
            JOptionPane.showMessageDialog(rootPane, "You don't have enough money");
        }else{
            try{
                stmt = con.createStatement();
                ResultSet rs;
                stmt.executeUpdate(updateCustomerBalance);
                int supplierBalance;
                int productStockQuantity;
                
                
                for(int i = 0; i < this.priceList.size(); i++){
                    String getSupplierBalance = "select Wallet from Account where (ID = " + this.supplierList.get(i) + ")";
                    String getProductStockQuantity = "select Remaining_Quantity from product where (ID = " + this.productList.get(i) + ");";          
                    supplierBalance = 0;
                    productStockQuantity = 0;
                    
                    stmt = con.createStatement();
                    rs = stmt.executeQuery(getSupplierBalance);                   
                   
                    while(rs.next()){
                        supplierBalance = rs.getInt("Wallet");
                    }
                    
                    rs = stmt.executeQuery(getProductStockQuantity);                   
                   
                    while(rs.next()){
                        productStockQuantity = rs.getInt("Remaining_Quantity");
                    }
                    
                    //" + (supplierBalance + this.priceList.get(i)) + "
                    String updateSupplierBalance = "update Account set Wallet = " + (supplierBalance + this.priceList.get(i)) + " where (ID = " + this.supplierList.get(i) + ");";
                    String updateProductStock = "update Product set Remaining_Quantity = " + (productStockQuantity - this.buyQuantityList.get(i)) + " where (ID = " + this.productList.get(i) + ");";
                    
                    Statement stmt = con.createStatement();
                    
                    stmt.executeUpdate(updateSupplierBalance);
                    stmt.executeUpdate(updateProductStock);

                    
                    
                }
            }catch(SQLException e){
            } 
            SetPaymentInfo();
            JOptionPane.showMessageDialog(rootPane, "Payment succesful");
            addOrder();
            addPayment();
            
        }
        
    }//GEN-LAST:event_buyBtnActionPerformed

    private void depositeFrmBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_depositeFrmBtnActionPerformed
        // TODO add your handling code here:
        
        close();
        DepositFrm depositeForm = new DepositFrm();
        depositeForm.setVisible(true);
    }//GEN-LAST:event_depositeFrmBtnActionPerformed

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
        close();
        productFrm j = new productFrm();
        j.setVisible(true);
            
    }//GEN-LAST:event_backBtnActionPerformed

    private void ratingBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ratingBtnActionPerformed
        // TODO add your handling code here:
        close();
        RatingFrm ratingForm = new RatingFrm();
        ratingForm.setVisible(true);
    }//GEN-LAST:event_ratingBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PaymentFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PaymentFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PaymentFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PaymentFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PaymentFrm().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea PriceDisplay;
    private javax.swing.JButton backBtn;
    private javax.swing.JTextArea balanceDisplay;
    private javax.swing.JButton buyBtn;
    private javax.swing.JButton depositeFrmBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JLabel paymentFrmLabel;
    private javax.swing.JScrollPane priceDisplay;
    private javax.swing.JTextArea productDisplay;
    private javax.swing.JButton ratingBtn;
    private javax.swing.JTextArea userNameDisplay;
    // End of variables declaration//GEN-END:variables
}


/*String code = "SELECT ID FROM Account WHERE User_Name = '" + userTMP +"' AND PassWord = '" + tmp + "';";

                String connect = "jdbc:sqlserver://localhost:1433;databaseName=Commerce;user=sa;password=sa;encrypt=true;trustServerCertificate=true;";
                try(Connection con = DriverManager.getConnection(connect);Statement stmt = con.createStatement();){
                    ResultSet rs = stmt.executeQuery(code);
                    
                    while(rs.next())
                    {
                        Main.USERS_ID = rs.getInt("ID");

                    }
                    
                    

                }catch(SQLException e){
                }*/

