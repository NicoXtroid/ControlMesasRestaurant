/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author NicoD
 */

import java.awt.Color;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.Statement;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
//import javax.swing.table.DefaultTableModel;
import javax.swing.table.*;
import java.util.logging.*;



public class formPagoDeuda extends javax.swing.JFrame {

    private Statement sentencia; /*puedo ocupar sentencias de control sql*/
    private Connection conexion; /*objeto que permite conectar mi base datos con programa*/
     /*objeto que permite conectar mi base datos con programa*/
     private String msj;
     
    private String nomBD = "bussandwicheria";
    private String usuario = "root";
    private String password = "informatica";
   

    /*
    private String nomBD="nicolas.huerta";
    private String usuario="nicolas.huerta";
    private String password="inf123pass";
     
     private String nomBD="bussandwicheria";
    private String usuario="bussandwicheria";
    private String password="bussandwicheria";  
*/ 
    public formPagoDeuda() {
        initComponents();
         conectar();
        llenarTabla();
        asignarFecha();
        setIcon();
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.orange);
    }

     public void asignarFecha(){
     this.setLocationRelativeTo(null);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        txtFecha.setText(dtf.format(localDate));
        setIcon();

    
    
    }
    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagen/icono.png")));
    }
      
    //CONECTAR
    public void conectar() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url ="jdbc:mysql://localhost:3306/"+this.nomBD;            
            //String url="jdbc:mysql://tupuedes.ml/"+this.nomBD;  //esto nope
            
            //String url="jdbc:mysql://192.168.238:3306/"+this.nomBD;            
            
            this.conexion=(Connection)DriverManager.getConnection(url,this.usuario,this.password);
            this.sentencia=(Statement)this.conexion.createStatement();
        }
        catch(Exception e){
            msj="error al conectar";
        }        
    }
    
    
    public Integer validarBtnAgregar(){
    Integer swAceptar = 0 ;
    
     
        /*VALIDACION */
        
        
        
       
         Integer sw=0;
       
         
        if (txtRut.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo rut vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }
        if (txtMonto.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo monto vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }
        
       if (sw==0 ){ 
       
           /*en esta segunda validacion necesitamos que exista el rutprov por lo que debe eser 0*/   
        /*creamos variables necesarias para el prog y resto de validaciones*/
         Integer auxRutProveedor = Integer.parseInt(txtRut.getText());
     
         String query = "";
            ResultSet existe;
         
            if (auxRutProveedor  == 0) {
            
            JOptionPane.showMessageDialog(null, "ERROR!Rut no puede ser 0.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
             sw = 1;   
            }
            
          try {
                sentencia = (Statement) conexion.createStatement();
                query = "SELECT * FROM `cliente_frecuente` WHERE rut_cliente = " + auxRutProveedor;
                
                existe = sentencia.executeQuery(query);
                
                if (existe.next() == false) {
               
              JOptionPane.showMessageDialog(null, " Error NO EXISTE el  cliente .", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);  
                 sw = 1;
                } else {
              
                }
            } catch (SQLException e) {
               
                 sw = 1;
            }
       }
        
        
       
   /*FIN VALIDACION*/
   
   
    
    swAceptar = sw;
    return swAceptar;
    };
    
    
    
     //LLENAR TABLA
    public void llenarTabla(){
        Integer auxRut = 0;
        String auxNom = "";
        String auxAp = "";
        Integer auxMonto = 0 ;   
       
        String query = "";     
        
        try{
            sentencia=(Statement)conexion.createStatement();
            
            ResultSet lista=sentencia.executeQuery("SELECT * FROM cliente_frecuente");
          int cont=0;
            while(lista.next()){
                auxRut = Integer.parseInt((lista.getString("rut_cliente")));          
                auxNom = (lista.getString("nombre_cliente")); 
                auxAp = (lista.getString("ap_paterno")); 
                auxMonto = Integer.parseInt((lista.getString("monto_deuda")));
             
               DefaultTableModel modelo = (DefaultTableModel) tblClientes.getModel();
                modelo.addRow(new Object[]{auxRut,auxNom,auxAp,auxMonto});                
                cont = cont + 1; 
                
                
                /*
                Object datos[] = {auxRut,auxNom,auxApp,auxApm,auxDir,auxFono,auxMail};
                tblProveedores.addRow(datos); */
            }
        }
        catch(SQLException e){
            msj="no se pudo seleccionar";
        }
      
    }
    
    public void limpiarcampos(){
        txtRut.setText("");
        txtMonto.setText("");
       
    
    
    }
    
    public void limpiarTabla(){        
        try {
            DefaultTableModel modelo=(DefaultTableModel) tblClientes.getModel();
            int filas=tblClientes.getRowCount();
            for (int i = 0;filas>i; i++) {
                modelo.removeRow(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
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

        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();
        lblFecha = new javax.swing.JLabel();
        txtRut = new javax.swing.JTextField();
        txtMonto = new javax.swing.JTextField();
        lblRut = new javax.swing.JLabel();
        lblMonto = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable()
        {
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }}
            ;
            txtFecha = new javax.swing.JTextField();

            jLabel5.setText("jLabel5");

            setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

            jLabel1.setText("Pago Deuda");

            btnRegresar.setText("Atrás");
            btnRegresar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnRegresarActionPerformed(evt);
                }
            });

            lblFecha.setText("Fecha:");

            txtRut.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent evt) {
                    txtRutKeyTyped(evt);
                }
            });

            txtMonto.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent evt) {
                    txtMontoKeyTyped(evt);
                }
            });

            lblRut.setText("Rut Cliente:");

            lblMonto.setText("Monto a Pagar:");

            jLabel6.setText("Listado de Clientes");

            btnAceptar.setText("Aceptar");
            btnAceptar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnAceptarActionPerformed(evt);
                }
            });

            btnCancelar.setText("Cancelar");
            btnCancelar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnCancelarActionPerformed(evt);
                }
            });

            jLabel2.setText("Ingreso de datos");

            tblClientes.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                    "Rut", "Nombre", "Ap. Paterno", "Monto Deuda"
                }
            ) {
                boolean[] canEdit = new boolean [] {
                    true, false, false, true
                };

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }
            });
            tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblClientesMouseClicked(evt);
                }
            });
            jScrollPane2.setViewportView(tblClientes);

            txtFecha.setEditable(false);

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addComponent(lblFecha)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRegresar))
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(164, 164, 164)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator2))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(18, 18, 18)
                                        .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap(209, Short.MAX_VALUE)
                                .addComponent(btnAceptar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelar)
                                .addGap(24, 24, 24))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblRut)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtRut, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(70, 70, 70)
                                .addComponent(lblMonto)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addContainerGap(57, Short.MAX_VALUE))
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnRegresar)
                        .addComponent(lblFecha)
                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(3, 3, 3)
                    .addComponent(jLabel1)
                    .addGap(11, 11, 11)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel6)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtRut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblRut)
                        .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblMonto))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAceptar)
                        .addComponent(btnCancelar))
                    .addContainerGap())
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
       
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        if(tblClientes.getRowCount() > 0 ){
            int sel = tblClientes.getSelectedRow();
            Object cod = tblClientes.getValueAt(sel, 0);
            String auxRut = cod.toString();

           
//yolo
            Object cat = tblClientes.getValueAt(sel, 3);
            String auxMonto = cat.toString();

          txtRut.setText(auxRut);
          
          
            txtMonto.setText(auxMonto);
           
        }

    }//GEN-LAST:event_tblClientesMouseClicked

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
      int   sw = validarBtnAgregar();
      int   switchNoNegativo = 0;
      if (sw == 0){  
         String auxRut = txtRut.getText();
         Integer auxMontoBD = 0 ;  
         Integer auxMontoForm = Integer.parseInt(txtMonto.getText());
         Integer auxMontoNuevo = 0 ;
        String query = "";     
        
        try{
            sentencia=(Statement)conexion.createStatement();
            
            ResultSet lista=sentencia.executeQuery("SELECT * FROM cliente_frecuente where rut_cliente ='" + auxRut + "'");
          int cont=0;
            while(lista.next()){
                auxMontoBD = Integer.parseInt((lista.getString("monto_deuda")));
            
             auxMontoNuevo = auxMontoBD - auxMontoForm;
              
             
             
             if (auxMontoBD == 0 ){
             switchNoNegativo = 1;
                JOptionPane.showMessageDialog(null,"Error el cliente no posee dueda","ERROR",JOptionPane.INFORMATION_MESSAGE);  
           
             }
             
              if ((auxMontoNuevo < 0) && (switchNoNegativo == 0) ){
             
              switchNoNegativo = 1;
                JOptionPane.showMessageDialog(null,"Error, la deuda que desea reducir es menor al monto que esta reduciendo","ERROR",JOptionPane.INFORMATION_MESSAGE);  
           
             
             }
                
               

             
                cont = cont + 1; 
                
                
                /*
                Object datos[] = {auxRut,auxNom,auxApp,auxApm,auxDir,auxFono,auxMail};
                tblProveedores.addRow(datos); */
            }
        }
        catch(SQLException e){
            msj="no se pudo seleccionar";
        }
                  
        
        
          
        
      
       
      
        ResultSet existe;       
        
      
              
        
        
      if  (switchNoNegativo == 0){  
        
        
        try{
            sentencia=(Statement)conexion.createStatement();
            query="SELECT * from cliente_frecuente WHERE rut_cliente='" + auxRut + "'";
            existe=sentencia.executeQuery(query);
            if(existe.next()==true){
              
         
               String sql="UPDATE cliente_frecuente SET monto_deuda='" + auxMontoNuevo + "'WHERE rut_cliente ='" + auxRut + "'";
             
               try{
            sentencia.executeUpdate(sql);
            msj="Datos guardados";
        
        }
        catch(SQLException e){
            msj="no ingreso";  
             JOptionPane.showMessageDialog(null,msj,"Error al actualizar",JOptionPane.INFORMATION_MESSAGE);                      
        }
               
               
               
               
               
               
               
               
                
            
            msj="Modificado";
            JOptionPane.showMessageDialog(null,msj,"INFORMACION",JOptionPane.INFORMATION_MESSAGE);            
            }else{
                msj="No existe la persona no se puede modificar";
                JOptionPane.showMessageDialog(null,msj,"INFORMACION",JOptionPane.INFORMATION_MESSAGE);  
            }
        }
        catch(SQLException e){
            msj="No existe persona, no se puede modificar";
        }
        
        
        limpiarTabla();
        llenarTabla();
        limpiarcampos();
        
        
        
        
        
        
        
      }
      }    // TODO add your handling code here:
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void txtRutKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRutKeyTyped
     int lar = 9;

        if (txtRut.getText().length() >= lar) {
            evt.consume();
        }

        char c = evt.getKeyChar();

        if (c < '0' || c > '9') {
            evt.consume();
        }      // TODO add your handling code here:
    }//GEN-LAST:event_txtRutKeyTyped

    private void txtMontoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoKeyTyped
         int lar = 6;

        if (txtMonto.getText().length() >= lar) {
            evt.consume();
        }

        char c = evt.getKeyChar();

        if (c < '0' || c > '9') {
            evt.consume();
        }    // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoKeyTyped

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
 limpiarcampos();        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarActionPerformed

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
            java.util.logging.Logger.getLogger(formPagoDeuda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formPagoDeuda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formPagoDeuda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formPagoDeuda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formPagoDeuda().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblMonto;
    private javax.swing.JLabel lblRut;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtMonto;
    private javax.swing.JTextField txtRut;
    // End of variables declaration//GEN-END:variables
}
