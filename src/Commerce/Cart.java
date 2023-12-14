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

/**
 *
 * @author Admin
 */
public class Cart extends javax.swing.JFrame {

    /**
     * Creates new form Cart
     */
    public String customer_bill;
    
    private int product,ordered_number;
    private int[] arr = new int[1001];
    
    int Order_Number;
    private int checkCountNum(String table){
        
        int res = 0;
        String code = "SELECT FROM " + table +";" ;
        //System.out.println(code);

        String connect = "jdbc:sqlserver://localhost:1433;databaseName=Commerce;user=sa;password=sa;encrypt=false;trustServerCertificate=false;";

       try(Connection con = DriverManager.getConnection(connect);Statement stmt = con.createStatement();){
            ResultSet rs = stmt.executeQuery(code);
                while(rs.next()){
                res++;
            }            
        }catch(SQLException e){
        }
       //System.out.println(res);
       return res;
    }
    private void setup(){
       for(int i = 1; i <= 1000; i++) arr[i] = 0;
        
    }
    
    
    public int total_bill;
    private String c_id;
    private String c_quantity;
    private String c_queue;
    
    
    public Cart() {
        initComponents();
        setup();
        Order_Number = checkCountNum("OrderI");
        showww();
        
        
    }
    
    private void in4(String id){
        
                String code = "select p.ID, p.Remaining_Quantity, c.Quantity_in_Queue from Cart c "
                +" inner join Product p on c.Product_ID = p.ID where c.id = " +id  ;
        
        String connect = "jdbc:sqlserver://localhost:1433;databaseName=Commerce;user=sa;password=sa;encrypt=false;trustServerCertificate=false;";
               
                
        System.out.println(code);
               try(Connection con = DriverManager.getConnection(connect);Statement stmt = con.createStatement();){
            //String SQL = TextField1.getText();
            ResultSet rs = stmt.executeQuery(code); 
            
            StringBuilder results = new StringBuilder();
            ResultSetMetaData metaData = rs.getMetaData();
            
            
            int numberOfColumns = metaData.getColumnCount();
            /*
            for(int i = 1; i <= numberOfColumns; i++){
                results.append(metaData.getColumnName(i)).append("\t");
            }
            
            results.append("\n");
            */
            while(rs.next()){
            
            for(int i = 1; i <= numberOfColumns; i++){
                //results.append(rs.getObject(i)).append("\t");
                if(i == 1) c_id = rs.getObject(i).toString();
                if(i == 2) c_quantity = rs.getObject(i).toString();
                if(i == 3) c_queue = rs.getObject(i).toString();
            }    
            results.append("\n"); 
            }
            //te.setText(results.toString());
            System.out.println(c_id);
            System.out.println(c_quantity);
            
       }catch(SQLException e){
           //te.setText(e.getMessage());
       }
    }
    
    private void returnProduct(String id){
        
        in4(id);
        int temp_number = Integer.parseInt(c_quantity) + Integer.parseInt(c_queue);
        String Tem = temp_number + "";
        String code = "UPDATE Product SET Remaining_Quantity = " + Tem + " WHERE ID = " +c_id +  ";";
        
        System.out.println(code);

        String connect = "jdbc:sqlserver://localhost:1433;databaseName=Commerce;user=sa;password=sa;encrypt=false;trustServerCertificate=false;";

       try{
           Connection con = DriverManager.getConnection(connect);
           PreparedStatement stmt = con.prepareStatement(code);
           stmt.executeUpdate();

        }catch(SQLException e){
        } 

       //System.out.println(code);
    
    }
    
    private void dropID(String id){
        

        String code = "DELETE FROM Cart WHERE ID = " + id + " AND Cart_ID = '" + AccountSignIn.tempo   +"'" ;
        
        System.out.println(code);

        String connect = "jdbc:sqlserver://localhost:1433;databaseName=Commerce;user=sa;password=sa;encrypt=false;trustServerCertificate=false;";

       try{
           Connection con = DriverManager.getConnection(connect);
           PreparedStatement stmt = con.prepareStatement(code);
           stmt.executeUpdate();

        }catch(SQLException e){
        } 

       System.out.println(code);
    }

    
    
    public void showww(){
        String connect = "jdbc:sqlserver://localhost:1433;databaseName=Commerce;user=sa;password=sa;encrypt=false;trustServerCertificate=false;";
       
        String SQL = "SELECT c.ID, p.Name, p.Price, c.Quantity_in_Queue  FROM Product p, Cart c WHERE c.Cart_ID = " +AccountSignIn.tempo +" AND " 
                + "c.Product_ID = p.ID"
                ;
        System.out.println(AccountSignIn.tempo);
        System.out.println(SQL);
               
            total_bill = 0;
        
       try(Connection con = DriverManager.getConnection(connect);Statement stmt = con.createStatement();){
            //String SQL = text.getText();
            ResultSet rs = stmt.executeQuery(SQL); 
            
            StringBuilder results = new StringBuilder();
            ResultSetMetaData metaData = rs.getMetaData();
            
            int numberOfColumns = metaData.getColumnCount();
            for(int i = 1; i <= numberOfColumns; i++){
                results.append(metaData.getColumnName(i)).append("\t\t");
            }
            results.append("\n");
            
            while(rs.next()){
            
            for(int i = 1; i <= numberOfColumns; i++){
                results.append(rs.getObject(i)).append("\t\t");
                
                if(i == 1) {
                    int a = Integer.parseInt(rs.getObject(i).toString());
                    arr[a] = 1;
                }
                
                if(i == 3) product = Integer.parseInt(rs.getObject(i).toString());
                if(i == 4) ordered_number = Integer.parseInt(rs.getObject(i).toString());
            }    
            results.append("\n"); 
                total_bill += product * ordered_number;
            }
            text.setText(results.toString());
            
            customer_bill = total_bill + "";
            bill.setText(customer_bill);
            
            
       }catch(SQLException e){
           text.setText(e.getMessage());
       }
        
        
    }
    
    public void close(){
        WindowEvent closeW = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeW);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        user = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        text = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        drop = new javax.swing.JTextField();
        DROPP = new javax.swing.JButton();
        bill = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        te = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Cart");

        jTextField1.setEditable(false);
        jTextField1.setText("User:");

        user.setEditable(false);
        user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userActionPerformed(evt);
            }
        });

        jTextField3.setEditable(false);
        jTextField3.setText("Selected Products");

        text.setEditable(false);
        text.setColumns(20);
        text.setRows(5);
        jScrollPane3.setViewportView(text);

        jButton2.setText("Back To Main");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        drop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dropActionPerformed(evt);
            }
        });

        DROPP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        DROPP.setText("DROP");
        DROPP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DROPPActionPerformed(evt);
            }
        });

        bill.setEditable(false);
        bill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                billActionPerformed(evt);
            }
        });

        jTextField2.setEditable(false);
        jTextField2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField2.setText("OVERALL");

        jTextField4.setEditable(false);
        jTextField4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField4.setText("INPUT ID");

        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton3.setText("BUY");

        te.setText("jTextField5");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(291, 291, 291)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 326, Short.MAX_VALUE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addComponent(user, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 870, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(bill, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(drop, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DROPP))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(400, 400, 400)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(te, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bill, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DROPP)
                            .addComponent(drop, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(te)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userActionPerformed
        // TODO add your handling code here:
        if(Main.Status_Username_ON == true)
            user.setText(Main.user);
    }//GEN-LAST:event_userActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        close();
        productFrm j = new productFrm();
        j.setVisible(true);
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void DROPPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DROPPActionPerformed
        // TODO add your handling code here:
        drop.selectAll();
        String id_drop = drop.getText();
        int temporary = Integer.parseInt(id_drop);
        if(arr[temporary] == 1){
        returnProduct(id_drop);
        //dropID(id_drop);
        
        
        }
        else{
            JOptionPane.showMessageDialog(null, "Invalid ID!", "Message", JOptionPane.ERROR_MESSAGE);        
        return;       
        
    }//GEN-LAST:event_DROPPActionPerformed
    }
    private void billActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_billActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_billActionPerformed

    private void dropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dropActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dropActionPerformed

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
            java.util.logging.Logger.getLogger(Cart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Cart().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton DROPP;
    private javax.swing.JTextField bill;
    private javax.swing.JTextField drop;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField te;
    private javax.swing.JTextArea text;
    private javax.swing.JTextField user;
    // End of variables declaration//GEN-END:variables
}
