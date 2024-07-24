
import java.awt.Color;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author tuinf
 */
public class formMerma extends javax.swing.JFrame {

    private Statement sentencia;
    /*puedo ocupar sentencias de control sql*/
    private Connection conexion;
    
     /*objeto que permite conectar mi base datos con programa*/
    private String nomBD = "bussandwicheria";
    private String usuario = "root";
    private String password = "informatica";
    private String msj;

    /*
    private String nomBD="nicolas.huerta";
    private String usuario="nicolas.huerta";
    private String password="inf123pass";
     */  /*
     private String nomBD="bussandwicheria";
    private String usuario="bussandwicheria";
    private String password="bussandwicheria"; 
    */

    /**
     * Creates new form formMerma
     */
    public formMerma() {
        initComponents();
        conectar();
        setIcon();
         DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        lblFecha2.setText(dtf.format(localDate));
        this.getContentPane().setBackground(Color.orange);
        llenarTablaP();
        
        
        
        this.setLocationRelativeTo(null);
    }
    
    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagen/icono.png")));
    }
    
    public Integer validarProdNegativo(Integer paramCodProdix){
    /*En esta parte del codigo ,se realiza en el caso de que el chk del producto este activado */      
     int swMaestro = 0 ; /*valida que el prod sea positivio*/
     int sw = 0 ;
     
     
         
            
        if (sw==0){
    
        
       Integer  auxCodProd    = Integer.parseInt(txtCodProd.getText());
        String auxCodIns     = "" ; 
        Integer auxCant       = 0 ;
        
        try{
            System.out.println("llenar tabla");
            
            sentencia=(Statement)conexion.createStatement();
            ResultSet lista=sentencia.executeQuery("SELECT * FROM detalle_producto WHERE cod_producto='" + auxCodProd + "'");
            int cont=0;
          
            while(lista.next()){
            auxCodIns = lista.getString("cod_insumo"); 
            auxCant   = (Integer.parseInt(lista.getString("cantidad") ) ) * (Integer.parseInt(txtCant.getText()) )    ;
                
                
                 
    /*   JOptionPane.showMessageDialog(null,msj,"TOCO EL BOTON ACEPTAR",JOptionPane.INFORMATION_MESSAGE);*/
        int switchNoNegativo = 0 ;
    
    
    
         
         Integer auxMontoBD = 0 ;  
         Integer auxMontoForm =  auxCant ;
         Integer auxMontoNuevo = 0 ;
        String query = "";     
        
        String auxTipoAjuste = "X" ;
           String auxObs = "Nada" ;
        try{
            sentencia=(Statement)conexion.createStatement();
            
            ResultSet lista2=sentencia.executeQuery("SELECT * FROM insumos where cod_insumo ='" + auxCodIns  + "'");

             cont=0;
            while(lista2.next()){
                auxMontoBD = Integer.parseInt((lista2.getString("stock_actual")));
                
                
                        
         
               
     /*        JOptionPane.showMessageDialog(null,msj,"el aux monto BF valex" +   auxMontoBD,JOptionPane.INFORMATION_MESSAGE);            
             JOptionPane.showMessageDialog(null,msj,"el aux monto FORM VALE " +   auxMontoForm,JOptionPane.INFORMATION_MESSAGE);*/
           
           
           
             
          
                 /*si la var accion = 2 , descontara stock*/
             auxMontoNuevo = auxMontoBD - auxMontoForm;
              
           
              
             if (auxMontoNuevo < 0){
             swMaestro = 1;
              switchNoNegativo = 1;
                JOptionPane.showMessageDialog(null,"Error no se puede eliminar una cantidad que no dispone","ERROR",JOptionPane.INFORMATION_MESSAGE);  
           
             
             }
             
             
             
                
        /* JOptionPane.showMessageDialog(null,msj,"el aux monto nuevo NUEVO VALE " +   auxMontoNuevo,JOptionPane.INFORMATION_MESSAGE);*/
               

             
                cont = cont + 1; 
                
                
                /*
                Object datos[] = {auxRut,auxNom,auxApp,auxApm,auxDir,auxFono,auxMail};
                tblProveedores.addRow(datos); */
            }
        }
        catch(SQLException e){
            msj="no se pudo seleccionar";
        }
                  
        
    
          /*   JOptionPane.showMessageDialog(null,msj,"YA OBTUVIMOS ELVALOR AHORA HAREMOS EL UPDATE " +   auxMontoNuevo,JOptionPane.INFORMATION_MESSAGE);*/
               

        
        
        //IMPORTANTE : AHORA QUE YA TENEMOS EL VALOR NUEVO A AGREGAR
        //VENDRIA LA PARTE DE AGREGAR DICHO MONTO
        
            
             
              
         /*recordar siempre el param2... si es 1 es ingresoy 2 perdida*/
                
            }
        }catch(SQLException e){
            msj="no se pudo seleccionar";
        }  
       
     
      
      
        
        }
        
    
    return  swMaestro;
    }
       
        
    
    
    
    public void limpiarCampos(){
        txtCodProd.setText("");
        txtCant.setText("");
       
    
    } 
         
       
    //INSERTAR
    public void insertar(String sql){
        try{
            sentencia.executeUpdate(sql);
            msj="Datos guardados";
            JOptionPane.showMessageDialog(null,msj,"INFORMACION",JOptionPane.INFORMATION_MESSAGE);                      
           // lblEstado.setText(msj);
        }
        catch(SQLException e){
            msj="no ingreso";         
        }
    }  

    
    
    
     public Integer calcCodAjuste(){
    int cod_final = 0 ;
     try {
                sentencia = (Statement) conexion.createStatement();

                ResultSet lista = sentencia.executeQuery("SELECT * FROM ajuste_stock ");

               
                /*ESTE CONTADOR ES PARA RECUPERAR EL COD INS*/
                while (lista.next()) {
                 cod_final = Integer.parseInt((lista.getString("cod_ajuste")));   
                  cod_final = cod_final + 1 ;  
                  /* JOptionPane.showMessageDialog(null, "COD COMPRAES"+cod_final, "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);    */
                }
     }catch(SQLException e){
     
     }
     if (cod_final == 0){
      cod_final = 1;
     }
     
     
     
    return cod_final ;
    }
    
    
    
    
    
    
 public Integer validarBtnAgregar(){
    Integer swAceptar = 0 ;
    
     
        /*VALIDACION */
        
        
        
        
      
       
         Integer sw=0;
         
         
         
         
         
         
         
         
         
         
         
        if (  txtCodProd.getText().isEmpty() || txtCant.getText().isEmpty()  ){
            System.out.println("Una de las casillas vacías error ");
            JOptionPane.showMessageDialog(null, "ERROR! Campos vacios.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        }
         else{
               
         }
        
       if (sw==0 ){ 
       
           /*en esta segunda validacion necesitamos que exista el rutprov por lo que debe eser 0*/   
        /*creamos variables necesarias para el prog y resto de validaciones*/
         Integer auxRutProveedor = Integer.parseInt(txtCodProd.getText());
     
         String query = "";
            ResultSet existe;
         
            if (auxRutProveedor  == 0) {
            
            JOptionPane.showMessageDialog(null, "ERROR!Rut no puede ser 0.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
             sw = 1;   
            }
            
          try {
                sentencia = (Statement) conexion.createStatement();
                query = "SELECT * FROM `producto` WHERE cod_producto = " + auxRutProveedor;
                
                existe = sentencia.executeQuery(query);
                
                if (existe.next() == false) {
               
                JOptionPane.showMessageDialog(null, "Error NO EXISTE el producto .", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);  
                 sw = 1;
                } else {
              JOptionPane.showMessageDialog(null, "ERROR!Felicitaciones EXISTE producto.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
                
                }
            } catch (SQLException e) {
               
                 sw = 1;
            }
       }
        
        
       
   /*FIN VALIDACION*/
   
   
    
    swAceptar = sw;
    return swAceptar;
    };
       
    public void llenarTablaP() {
        String auxCod = "";
        String auxNom = "";
        String auxPre = "";

        try {
            System.out.println("llenar tabala");
            sentencia = (Statement) conexion.createStatement();
            ResultSet lista = sentencia.executeQuery("SELECT * FROM producto");
            int cont = 0;
            while (lista.next()) {
                auxCod = (lista.getString("cod_producto"));
                System.out.println(auxCod);
                auxNom = (lista.getString("nombre_producto"));
                System.out.println(auxNom);
                auxPre = (lista.getString("precio"));
                System.out.println(auxPre);

                DefaultTableModel modelo = (DefaultTableModel) tblProd.getModel();
                modelo.addRow(new Object[]{auxCod, auxNom, auxPre});
                cont = cont + 1;

            }
        } catch (SQLException e) {
            msj = "no se pudo seleccionar";
        }
    }

    //CONECTAR
    public void conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/" + this.nomBD;

            //String url="jdbc:mysql://tupuedes.ml/"+this.nomBD;  //esto nope            
            //String url="jdbc:mysql://192.168.238:3306/"+this.nomBD;            
            this.conexion = (Connection) DriverManager.getConnection(url, this.usuario, this.password);
            this.sentencia = (Statement) this.conexion.createStatement();
        } catch (Exception e) {
            msj = "error al conectar";
        }
    }

    /*variable "accion " descuenta o aumenta el stock segun corresponda*/
    public void descontarAumentarInsumo(String paramCodIns, Integer accion, Integer cantidad) {

        JOptionPane.showMessageDialog(null, msj, "TOCO EL BOTON ACEPTAR", JOptionPane.INFORMATION_MESSAGE);
        String auxCodIns = paramCodIns;
        Integer auxMontoBD = 0;
        Integer auxMontoForm = cantidad;
        Integer auxMontoNuevo = 0;
        String query = "";
        
        
        
        
        String auxTipoAjuste = "X" ;
        String auxObs = "Nada" ;

        try {
            sentencia = (Statement) conexion.createStatement();

            ResultSet lista = sentencia.executeQuery("SELECT * FROM insumos where cod_insumo ='" + auxCodIns + "'");

            int cont = 0;
            while (lista.next()) {
                auxMontoBD = Integer.parseInt((lista.getString("stock_actual")));

                JOptionPane.showMessageDialog(null, msj, "el aux monto BF valex" + auxMontoBD, JOptionPane.INFORMATION_MESSAGE);

                JOptionPane.showMessageDialog(null, msj, "el aux monto FORM VALE " + auxMontoForm, JOptionPane.INFORMATION_MESSAGE);

                if (accion == 1) {
                    /*si la var accion = 2 , descontara stock*/
                    auxMontoNuevo = auxMontoBD + auxMontoForm;
               auxTipoAjuste = "D";
              auxObs = "Donacion";
             }
             
             if (accion == 2 ){
                 /*si la var accion = 2 , descontara stock*/
             auxMontoNuevo = auxMontoBD - auxMontoForm;
              auxTipoAjuste = "M";
               auxObs = "Merma";
             }
                JOptionPane.showMessageDialog(null, msj, "el aux monto nuevo NUEVO VALE " + auxMontoNuevo, JOptionPane.INFORMATION_MESSAGE);

                cont = cont + 1;

                /*
                Object datos[] = {auxRut,auxNom,auxApp,auxApm,auxDir,auxFono,auxMail};
                tblProveedores.addRow(datos); */
            }
        } catch (SQLException e) {
            msj = "no se pudo seleccionar";
        }

        JOptionPane.showMessageDialog(null, msj, "YA OBTUVIMOS ELVALOR AHORA HAREMOS EL UPDATE " + auxMontoNuevo, JOptionPane.INFORMATION_MESSAGE);

        //IMPORTANTE : AHORA QUE YA TENEMOS EL VALOR NUEVO A AGREGAR
        //VENDRIA LA PARTE DE AGREGAR DICHO MONTO
        ResultSet existe;

        try {

            query = "SELECT * FROM insumos where cod_insumo ='" + auxCodIns + "'";
            existe = sentencia.executeQuery(query);
            if (existe.next() == true) {

                JOptionPane.showMessageDialog(null, msj, "ESTO ANTES DEL UPDATE " + auxMontoNuevo, JOptionPane.INFORMATION_MESSAGE);

                JOptionPane.showMessageDialog(null, msj, "AXU RUT VALE  " + auxCodIns, JOptionPane.INFORMATION_MESSAGE);

                String sql = "UPDATE insumos SET stock_actual='" + auxMontoNuevo + "'WHERE cod_insumo ='" + auxCodIns + "'";
                JOptionPane.showMessageDialog(null, msj, "ESTO es despues del update clave ", JOptionPane.INFORMATION_MESSAGE);

                try {
                    sentencia.executeUpdate(sql);
                    msj = "Datos guardados";
                    JOptionPane.showMessageDialog(null, msj, "DATOS ACTUALIZADOS XDXDXDXDXDXD", JOptionPane.INFORMATION_MESSAGE);
                    // lblEstado.setText(msj);
                } catch (SQLException e) {
                    msj = "no ingreso";
                    JOptionPane.showMessageDialog(null, msj, "MAAAAL", JOptionPane.INFORMATION_MESSAGE);
                }

                msj = "Modificado";
                JOptionPane.showMessageDialog(null, msj, "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
            } else {
                msj = "No existe la persona no se puede modificar";
                JOptionPane.showMessageDialog(null, msj, "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            msj = "No existe persona, no se puede modificar";
        }

        
        
         /*ahora registramos en el ajuste stock*/
         Integer auxCodAjuste  = calcCodAjuste() ;
       String auxFechaAjuste = lblFecha2.getText();
         String   sql = "INSERT INTO ajuste_stock VALUES (" + auxCodAjuste+ "," +  auxCodIns  + "," + auxMontoForm + ",'" + auxFechaAjuste + "','" +  auxTipoAjuste + "','" + auxObs + "')"; 
         insertar(sql);
         
         /*fin de ahora registramos en el ajuste stock*/
        
        limpiarTabla();

        llenarTablaP();

    }

    public void limpiarTabla() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) tblProd.getModel();
            int filas = tblProd.getRowCount();
            for (int i = 0; filas > i; i++) {
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

        jLabel1 = new javax.swing.JLabel();
        txtCodProd = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCant = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblProd = new javax.swing.JTable() {

            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }

        };
        lblFecha2 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Código:");

        txtCodProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodProdKeyTyped(evt);
            }
        });

        jLabel3.setText("Cantidad:");

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jLabel4.setText("Fecha");

        jLabel5.setText("Ingreso de Merma");

        txtCant.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantKeyTyped(evt);
            }
        });

        tblProd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "Precio"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProdMouseClicked(evt);
            }
        });
        tblProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tblProdKeyTyped(evt);
            }
        });
        jScrollPane3.setViewportView(tblProd);

        lblFecha2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                lblFecha2KeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(lblFecha2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSalir))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(txtCodProd, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCant, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnAceptar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnCancelar))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalir)
                    .addComponent(jLabel4)
                    .addComponent(lblFecha2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCodProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtCant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(btnCancelar))
                .addGap(39, 39, 39))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        //new fLogin().setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed

        int sw = validarBtnAgregar();
        
        Integer auxCodProd2 = Integer.parseInt(txtCodProd.getText());
            sw =   validarProdNegativo(auxCodProd2);
        
        
        
        if (sw==0){
    
    
    
        
        
        Integer auxCodProd = Integer.parseInt(txtCodProd.getText());
        String auxCodIns = "";
        Integer auxCant = 0;

        try {
            System.out.println("llenar tabla");

            sentencia = (Statement) conexion.createStatement();
            ResultSet lista = sentencia.executeQuery("SELECT * FROM detalle_producto WHERE cod_producto='" + auxCodProd + "'");
            int cont = 0;

            while (lista.next()) {
                JOptionPane.showMessageDialog(null, msj, "ENTRO AL WHILE DE BOTON AC PROD", JOptionPane.INFORMATION_MESSAGE);
                auxCodIns = lista.getString("cod_insumo");
                auxCant = (Integer.parseInt(lista.getString("cantidad"))) * (Integer.parseInt(txtCant.getText()));

                descontarAumentarInsumo(auxCodIns, 2, auxCant);
                /*recordar siempre el param2... si es 1 es ingresoy 2 perdida*/

            }
        } catch (SQLException e) {
            msj = "no se pudo seleccionar";
        }
        }
     limpiarCampos();   // TODO add your handling code here:
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void tblProdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProdMouseClicked

        if (tblProd.getRowCount() > 0) {
            int sel = tblProd.getSelectedRow();
            Object cod = tblProd.getValueAt(sel, 0);
            String auxCode = cod.toString();

            txtCodProd.setText(auxCode);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tblProdMouseClicked

    private void txtCodProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodProdKeyTyped
          int lar = 5;

        if (txtCodProd.getText().length() > lar) {
            evt.consume();
        }

        char c = evt.getKeyChar();

        if (c < '0' || c > '9') {
            evt.consume();
        }   // TODO add your handling code here:
    }//GEN-LAST:event_txtCodProdKeyTyped

    private void txtCantKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantKeyTyped
        int lar = 4;

        if (txtCant.getText().length() > lar) {
            evt.consume();
        }

        char c = evt.getKeyChar();

        if (c < '0' || c > '9') {
            evt.consume();
        }     // TODO add your handling code here:
    }//GEN-LAST:event_txtCantKeyTyped

    private void lblFecha2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblFecha2KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_lblFecha2KeyTyped

    private void tblProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblProdKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_tblProdKeyTyped

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
            java.util.logging.Logger.getLogger(formMerma.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formMerma.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formMerma.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formMerma.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formMerma().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField lblFecha2;
    private javax.swing.JTable tblProd;
    private javax.swing.JTextField txtCant;
    private javax.swing.JTextField txtCodProd;
    // End of variables declaration//GEN-END:variables
}
