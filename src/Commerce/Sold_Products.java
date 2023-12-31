/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Commerce;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Admin
 */
public class Sold_Products extends javax.swing.JFrame {
    
    /**
     * Creates new form Sold_Products
     */

    String[] id = new String[1000];
    StringBuilder results = new StringBuilder();
    String[] number = new String[1000];
    int res = 0, ress = 0;
    
    
    public Sold_Products() {
        initComponents();
        init();
        print();        
    }
    
    private void totalSoldProduct(String ID){
     String connect = "jdbc:sqlserver://localhost:1433;databaseName=Commerce;user=sa;password=sa;encrypt=false;trustServerCertificate=false;";
       //ress ++;
       try(Connection con = DriverManager.getConnection(connect);Statement stmt = con.createStatement();){
            String code = "Select SUM(c.Quantity_in_Queue)" +
            "from Cart c, OrderI o, Product p, Supplier s" + 
            " where c.Cart_ID = o.Customer_ID AND p.ID = c.Product_ID AND s.Supplier_ID = " + Main.USERS_ID +" AND p.ID =  '"+ ID +"'"; 
            
            ResultSet rs = stmt.executeQuery(code); 
            
            
            ResultSetMetaData metaData = rs.getMetaData();
            
            int numberOfColumns = metaData.getColumnCount();           
          
            while(rs.next()){
            
            for(int i = 1; i <= numberOfColumns; i++)
                    results.append(rs.getObject(i)).append("\t");              
            }

            
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

        jLabel1 = new javax.swing.JLabel();
        print = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        text = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("SOLD_PRODUCT");

        print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printActionPerformed(evt);
            }
        });

        text.setColumns(20);
        text.setRows(5);
        jScrollPane1.setViewportView(text);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(print, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(357, 357, 357)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 731, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(136, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(print, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(141, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_printActionPerformed
    private void print(){
           String connect = "jdbc:sqlserver://localhost:1433;databaseName=Commerce;user=sa;password=sa;encrypt=false;trustServerCertificate=false;";
       
       try(Connection con = DriverManager.getConnection(connect);Statement stmt = con.createStatement();){
            String code = "Select DISTINCT p.ID, p.Name " +
            "from Cart c, OrderI o, Product p, Supplier s  " +
            "where c.Cart_ID = o.Customer_ID AND p.ID = c.Product_ID AND s.Supplier_ID = '" + Main.USERS_ID  +"';";
            System.out.println(code);
            ResultSet rs = stmt.executeQuery(code);                         
            ResultSetMetaData metaData = rs.getMetaData();           
            int numberOfColumns = metaData.getColumnCount();
            for(int i = 1; i <= numberOfColumns; i++)
                results.append(metaData.getColumnName(i)).append("\t\t");
            
            results.append("Quantity");
            results.append("\n");
            while(rs.next()){
            for(int i = 1; i <= numberOfColumns; i++)
                    results.append(rs.getObject(i)).append("\t\t");
            
            for(int i = 1; i <= numberOfColumns; i++){
               
                    id[i] = rs.getObject(i).toString();
                    totalSoldProduct(id[i]);
            }                
            results.append("\n"); 
            }           
            text.setText(results.toString());
            
       }catch(SQLException e){
           text.setText(e.getMessage());
       }
    }
    private void init(){
        print.setText(Main.USERNAME_STATEMENT);
    }
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
            java.util.logging.Logger.getLogger(Sold_Products.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sold_Products.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sold_Products.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sold_Products.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sold_Products().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField print;
    private javax.swing.JTextArea text;
    // End of variables declaration//GEN-END:variables
}
