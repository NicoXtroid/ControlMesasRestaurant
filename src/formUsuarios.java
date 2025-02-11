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


public class formUsuarios extends javax.swing.JFrame {
 
    private Statement sentencia; /*puedo ocupar sentencias de control sql*/
    private Connection conexion; /*objeto que permite conectar mi base datos con programa*/
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
    
    /**
     * Creates new form formUsuarios
     */
    public formUsuarios() {
        initComponents();
        conectar();
        llenarTabla();
        this.setLocationRelativeTo(null);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        txtFecha.setText(dtf.format(localDate));
        setIcon();
        this.getContentPane().setBackground(Color.orange);
    }
    
    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagen/icono.png")));
    }
    
   
    public void conectar() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url ="jdbc:mysql://localhost:3306/"+this.nomBD;            
         
            this.conexion=(Connection)DriverManager.getConnection(url,this.usuario,this.password);
            this.sentencia=(Statement)this.conexion.createStatement();
        }
        catch(Exception e){
            msj="error al conectar";
        }        
    }
  
    public void insertar(String sql){
        try{
            sentencia.executeUpdate(sql);
         
        }
        catch(SQLException e){
            msj="no ingreso";         
        }
    }
    
    
  
    public void llenarTabla(){
        String auxNom = "";
        String auxPassword = "";
        Integer auxNivel = 0 ;
      
        try{
            sentencia=(Statement)conexion.createStatement();
            ResultSet lista=sentencia.executeQuery("SELECT * FROM usuarios");
            int cont=0;
            while(lista.next()){
                auxNom = (lista.getString("IdUsuario")); 
                auxPassword = (lista.getString("password"));
                auxNivel =   Integer.parseInt(lista.getString("nivel"));       
                
                DefaultTableModel modelo = (DefaultTableModel) tblUsuarios.getModel();
                modelo.addRow(new Object[]{auxNom , auxPassword, auxNivel});                
                cont = cont + 1; 
                
            }
        }
        catch(SQLException e){
            msj="no se pudo seleccionar";
        }      
    }
      public void limpiarTabla(){        
        try {
            DefaultTableModel modelo=(DefaultTableModel) tblUsuarios.getModel();
            int filas=tblUsuarios.getRowCount();
            for (int i = 0;filas>i; i++) {
                modelo.removeRow(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
        }
    }
    
    public void limpiarcampos(){
        txtPassword.setText("");
        txtNombre.setText("");
    }
   
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        btnRegresar = new javax.swing.JButton();
        lblFecha = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable() {
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        btnAgregar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lblID = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cbxNivel = new javax.swing.JSpinner();
        txtFecha = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registro de Usuarios");

        btnRegresar.setText("Atras");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        lblFecha.setText("Fecha:");

        jLabel2.setText("Registrar Usuario");

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Usuario", "Password", "Nivel de Acceso"
            }
        ));
        tblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsuarios);

        jLabel3.setText("Listado de Usuarios");

        jLabel4.setText("Insercion de Datos");

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        lblID.setText("Id. Usuario:");

        jLabel6.setText("Password:");

        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPasswordKeyTyped(evt);
            }
        });

        jLabel7.setText("Nivel de Acceso:");

        cbxNivel.setModel(new javax.swing.SpinnerNumberModel(1, 1, 2, 1));

        txtFecha.setEditable(false);

        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator2))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lblFecha)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnRegresar, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(159, 159, 159))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(266, 266, 266)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblID)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAgregar)
                                .addGap(20, 20, 20)
                                .addComponent(btnModificar)
                                .addGap(20, 20, 20)
                                .addComponent(btnEliminar)
                                .addGap(20, 20, 20)
                                .addComponent(btnCancelar)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRegresar)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblFecha)
                            .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(3, 3, 3)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAgregar)
                        .addComponent(btnModificar)
                        .addComponent(btnCancelar))
                    .addComponent(btnEliminar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblID)
                    .addComponent(jLabel7)
                    .addComponent(cbxNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(65, 65, 65))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
         this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        String auxNombre     =    txtNombre.getText();
        String auxPassword  =    txtPassword.getText();
     Integer auxNivelString  = (Integer) cbxNivel.getValue();
       Integer auxNivel = auxNivelString;
        
        int sw = 0;
        
        
        if (txtNombre.getText().isEmpty()     ){
            JOptionPane.showMessageDialog(null, "ERROR! Campo nombre vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        }
         if (txtPassword.getText().isEmpty()     ){
            JOptionPane.showMessageDialog(null, "ERROR! Campos password vacio", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        }
         
          
       
        if (sw == 0){
           
            String codE = auxNombre;
            String query="";
            ResultSet existe; 
            
            try{
            sentencia=(Statement)conexion.createStatement();
             query="SELECT * FROM usuarios WHERE idUsuario= '" + auxNombre + "'";
            existe=sentencia.executeQuery(query);
            if(existe.next()==false){
                
                String sql="INSERT INTO usuarios VALUES ('" + auxNombre + "','" + auxPassword + "','" + auxNivel + "')";
                insertar(sql);               
                  msj="Datos guardados";
                 JOptionPane.showMessageDialog(null,msj,"INFORMACION",JOptionPane.INFORMATION_MESSAGE);                      
          
            }else{
                msj="Ya existe el usuario";
                JOptionPane.showMessageDialog(null,msj,"ERROR",JOptionPane.INFORMATION_MESSAGE);  
            }
        }
        catch(SQLException e){
            msj="No existe usuario, no se puede modificar";
        }
            
        }
        
        
        
        limpiarTabla();
        llenarTabla();
        limpiarcampos();        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed

        int sw=0;
          if (txtNombre.getText().isEmpty()     ){
            JOptionPane.showMessageDialog(null, "ERROR! Campo nombre vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        }
         if (txtPassword.getText().isEmpty()     ){
            JOptionPane.showMessageDialog(null, "ERROR! Campos password vacio", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        }
                 
        
        if (sw == 0) {
        
       String auxNombre     =    txtNombre.getText();
        String auxPassword  =    txtPassword.getText();
        Integer auxNivelString  = (Integer) cbxNivel.getValue();
         Integer auxNivel = auxNivelString;
        
        String codE = auxNombre;
        String query="";
        ResultSet existe;       
        
        int cat = 0;
       
              
        
        
        
        
        
        try{
            sentencia=(Statement)conexion.createStatement();
           query="SELECT * FROM usuarios WHERE idUsuario= '" + auxNombre + "'";
            existe=sentencia.executeQuery(query);
            if(existe.next()==true){
               sentencia.executeUpdate("Delete from usuarios WHERE idUsuario ='" + auxNombre + "'");        
               String sql="INSERT INTO usuarios VALUES ('" + auxNombre + "','" + auxPassword + "','" + auxNivel + "')";
                insertar(sql);               
            msj="Modificado";
            JOptionPane.showMessageDialog(null,msj,"INFORMACION",JOptionPane.INFORMATION_MESSAGE);            
            }else{
                msj="No existe el usuario";
                JOptionPane.showMessageDialog(null,msj,"ERROR",JOptionPane.INFORMATION_MESSAGE);  
            }
        }
        catch(SQLException e){
            msj="No existe el usuario, no se puede modificar";
        }
        
        
        limpiarTabla();
        llenarTabla();
        limpiarcampos();
        
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
  int sw=0;
          if (txtNombre.getText().isEmpty()     ){
          
            JOptionPane.showMessageDialog(null, "ERROR! Campo nombre vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        }
         if (txtPassword.getText().isEmpty()     ){
     
            JOptionPane.showMessageDialog(null, "ERROR! Campos password vacio", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        }
         
        
        
        if (sw == 0) {
        
        String auxNombre=txtNombre.getText();
         String auxPassword=txtPassword.getText();
        String query="";
        ResultSet existe;  
        String codE = auxNombre;  
        try{
            sentencia=(Statement)conexion.createStatement();
           query="SELECT * FROM usuarios WHERE idUsuario= '" + auxNombre + "'";
         
            existe=sentencia.executeQuery(query);
           
            if(existe.next()==true){
              
                sentencia.executeUpdate("Delete from usuarios WHERE idUsuario ='" + auxNombre + "'");
            
            msj="ELIMINADO";
            JOptionPane.showMessageDialog(null,msj,"INFORMACION",JOptionPane.INFORMATION_MESSAGE);            
            }else{
                msj="No existe el usuario";
                JOptionPane.showMessageDialog(null,msj,"ERROR",JOptionPane.INFORMATION_MESSAGE);  
            }
        }
        catch(SQLException e){
            msj="No existe usuario, no se puede eliminar";
        }
        limpiarTabla();
        llenarTabla(); 
        limpiarcampos();
        
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void tblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuariosMouseClicked
        if (tblUsuarios.getRowCount() > 0) {
            int sel = tblUsuarios.getSelectedRow();
            
            Object id = tblUsuarios.getValueAt(sel, 0);
            String auxID = id.toString();
            
    
            Object pass = tblUsuarios.getValueAt(sel, 1);
            String auxPass = pass.toString();
         
            
            Object nvl = tblUsuarios.getValueAt(sel, 2);
            int auxNvl = Integer.parseInt(nvl.toString());
            
            txtNombre.setText(auxID);
            txtPassword.setText(auxPass);
        }    
            
    }//GEN-LAST:event_tblUsuariosMouseClicked

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
       limpiarcampos();        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
      int lar = 20;

        if (txtNombre.getText().length() >= lar) {
            evt.consume();
        }

            // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtPasswordKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordKeyTyped
      int lar = 20;

        if (txtPassword.getText().length() >= lar) {
            evt.consume();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordKeyTyped

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
            java.util.logging.Logger.getLogger(formUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formUsuarios().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JSpinner cbxNivel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblID;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPassword;
    // End of variables declaration//GEN-END:variables
}
