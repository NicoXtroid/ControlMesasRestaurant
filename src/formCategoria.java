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


/**
 *
 * @author NicoD
 */
public class formCategoria extends javax.swing.JFrame {

    private Statement sentencia; /*puedo ocupar sentencias de control sql*/
    private Connection conexion; 
    
    private String nomBD="bussandwicheria";
    private String usuario="root";
    private String password="informatica"; 
    /*
    private String nomBD="bussandwicheria";
    private String usuario="bussandwicheria";
    private String password="bussandwicheria";
    
    
    
      private String nomBD="bussandwicheria";
    private String usuario="bussandwicheria";
    private String password="bussandwicheria"; 
*/
    private String msj; 
    
   
    /** Creates new form formCategoria */
    public formCategoria() {
        initComponents();
        conectar();
        llenarTabla();
        asignarFecha();
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.orange);
        setIcon();
    }
    
    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagen/icono.png")));
    }
    public void asignarFecha(){
     this.setLocationRelativeTo(null);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        txtFecha.setText(dtf.format(localDate));
        setIcon();

    
    
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
    
    public Integer validarBtnAgregar(){
    Integer swAceptar = 0 ;
    
     
        
         Integer sw=0;
         
        
        if (txtCodigo.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo codigo vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }
        if (txtNombre.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo nombre vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }
        
       if (sw==0 ){ 
       
           /*en esta segunda validacion necesitamos que exista el rutprov por lo que debe eser 0*/   
        /*creamos variables necesarias para el prog y resto de validaciones*/
         Integer auxRutProveedor = Integer.parseInt(txtCodigo.getText());
     
         String query = "";
            ResultSet existe;
         
            if (auxRutProveedor  == 0) {
            
            JOptionPane.showMessageDialog(null, "ERROR!Rut no puede ser 0.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
             sw = 1;   
            }
            
          try {
                sentencia = (Statement) conexion.createStatement();
                query = "SELECT * FROM `categoria` WHERE cod_categoria = " + auxRutProveedor;
                
                existe = sentencia.executeQuery(query);
                
                if (existe.next() == false) {
        
                } else {
              JOptionPane.showMessageDialog(null, "ERROR!YA EXISTE categoria", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
                 sw = 1;
                }
            } catch (SQLException e) {
               
                 sw = 1;
            }
       }
        
        
  
    
    swAceptar = sw;
    return swAceptar;
    };
    
    
    public Integer validarBtnModificar(){
    Integer swAceptar = 0 ;
    
     
       
        
      
       
         Integer sw=0;
         DefaultTableModel modelo = (DefaultTableModel) tableCategoria.getModel(); 
      int largo = modelo.getRowCount();
        if   ( largo  <=0){
       
       
                JOptionPane.showMessageDialog(null, "Tabla vacia .", "¡ERROR!", JOptionPane.INFORMATION_MESSAGE);  
               sw = 1;
       };
         
         
         
         
         
         
            if (txtCodigo.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo codigo vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } 
        if (txtNombre.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo nombre vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } 
         
         
         
       
        
       if (sw==0 ){ 
       
           /*en esta segunda validacion necesitamos que exista el rutprov por lo que debe eser 0*/   
        /*creamos variables necesarias para el prog y resto de validaciones*/
         Integer auxRutProveedor = Integer.parseInt(txtCodigo.getText());
     
         String query = "";
            ResultSet existe;
         
            if (auxRutProveedor  == 0) {
            
            JOptionPane.showMessageDialog(null, "ERROR!Rut no puede ser 0.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
             sw = 1;   
            }
            
          try {
                sentencia = (Statement) conexion.createStatement();
                query = "SELECT * FROM `categoria` WHERE cod_categoria = " + auxRutProveedor;
                
                existe = sentencia.executeQuery(query);
                
                if (existe.next() == false) {
               
              JOptionPane.showMessageDialog(null, "Error no existe el proveedor .", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);  
                 sw = 1;
                } else {
             
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error ,no se puede eliminar", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
                
                 sw = 1;
            }
       }
        
        
       

   
   
    
    swAceptar = sw;
    return swAceptar;
    
    
    }
    
    
    
    
        
    public Integer validarBtnEliminar(){
    Integer swAceptar = 0 ;
      Integer sw=0;
         DefaultTableModel modelo = (DefaultTableModel) tableCategoria.getModel(); 
      int largo = modelo.getRowCount();
        if   ( largo  <=0){
       
       
                JOptionPane.showMessageDialog(null, "Tabla vacia .", "¡ERROR!", JOptionPane.INFORMATION_MESSAGE);  
               sw = 1;
       };
         
         
         
                 if (  txtCodigo.getText().isEmpty()    ){
             JOptionPane.showMessageDialog(null, "ERROR!Campo codigo vacio.", "¡ADVRTENCIA", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        }
         else{
            if (Integer.parseInt(txtCodigo.getText())  <= 0) {
                JOptionPane.showMessageDialog(null, "ERROR! Codigo invalido.", "¡ADVRTENCIA", JOptionPane.INFORMATION_MESSAGE);
                sw = 1;
            }       
        }
        
    
       if (sw==0 ){ 
       
           /*en esta segunda validacion necesitamos que exista el rutprov por lo que debe eser 0*/   
        /*creamos variables necesarias para el prog y resto de validaciones*/
         Integer auxRutProveedor = Integer.parseInt(txtCodigo.getText());
     
         String query = "";
            ResultSet existe;
         
            if (auxRutProveedor  == 0) {
            
            JOptionPane.showMessageDialog(null, "ERROR!Rut no puede ser 0.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
             sw = 1;   
            }
            
          try {
                sentencia = (Statement) conexion.createStatement();
                query = "SELECT * FROM `categoria` WHERE cod_categoria = " + auxRutProveedor;
                
                existe = sentencia.executeQuery(query);
                
                if (existe.next() == false) {
               
              JOptionPane.showMessageDialog(null, "Error no existe el categoria .", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);  
                 sw = 1;
                } else {
             
                }
            } catch (SQLException e) {
               
                 sw = 1;
            }
       }
        
    
    swAceptar = sw;
    return swAceptar;
    
    
    }
    
    
    
    
    
    
  public void llenarTabla(){
        String auxCodigo = "";
        String auxNombre = "";
        
        try{
            sentencia=(Statement)conexion.createStatement();
            ResultSet lista=sentencia.executeQuery("SELECT * FROM categoria");
            int cont=0;
            while(lista.next()){
                auxCodigo = (lista.getString("cod_categoria")); 
                auxNombre = (lista.getString("nombre_categoria"));
           
                DefaultTableModel modelo = (DefaultTableModel) tableCategoria.getModel();
                modelo.addRow(new Object[]{auxCodigo,auxNombre});                
                cont = cont + 1;           
                
               
            }
        }
        catch(SQLException e){
            msj="no se pudo seleccionar";
        }
      
    }
       
  
    public void limpiarTabla(){        
        try {
            DefaultTableModel modelo=(DefaultTableModel) tableCategoria.getModel();
            int filas=tableCategoria.getRowCount();
            for (int i = 0;filas>i; i++) {
                modelo.removeRow(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
        }
    }   
    
    
  public void insertar(String sql){
        try{
            sentencia.executeUpdate(sql);
         }
        catch(SQLException e){
          JOptionPane.showMessageDialog(null, "Error largo excesivo");
            
        }
    }

  
  
  public void limpiarcampos(){
        txtCodigo.setText("");
        txtNombre.setText("");
      
    
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();
        lblFecha = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableCategoria = new javax.swing.JTable()
        {
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnCancelar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        lblCodigo = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtFecha = new javax.swing.JTextField();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Rgistrar Categoria");
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                formKeyTyped(evt);
            }
        });

        btnRegresar.setText("Atras");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        lblFecha.setText("Fecha");

        tableCategoria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableCategoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableCategoriaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableCategoria);
        if (tableCategoria.getColumnModel().getColumnCount() > 0) {
            tableCategoria.getColumnModel().getColumn(0).setResizable(false);
            tableCategoria.getColumnModel().getColumn(1).setResizable(false);
        }

        jLabel2.setText("Regsitrar Categoria");

        jLabel3.setText("Listado de Categorias");

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

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

        lblCodigo.setText("Codigo:");

        jLabel5.setText("Insercion de Datos");

        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoKeyTyped(evt);
            }
        });

        lblNombre.setText("Nombre:");

        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });

        txtFecha.setEditable(false);
        txtFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnAgregar)
                                .addGap(20, 20, 20)
                                .addComponent(btnModificar))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addComponent(lblCodigo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblNombre)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnEliminar)
                                .addGap(20, 20, 20)
                                .addComponent(btnCancelar)))))
                .addContainerGap(25, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblFecha)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRegresar))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1)
                        .addGap(10, 10, 10))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegresar)
                    .addComponent(lblFecha)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCodigo)
                    .addComponent(lblNombre)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar)
                    .addComponent(btnCancelar))
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
       
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
 
       int sw =   validarBtnAgregar();  
       if (sw==0){
         sw=0;
        if (txtNombre.getText().isEmpty()   ||   txtCodigo.getText().isEmpty() ){
             JOptionPane.showMessageDialog(null, "ERROR! Casillas vacias.", "¡ADVRTENCIA", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        }
         else{
            if (Integer.parseInt(txtCodigo.getText())  <= 0) {
                JOptionPane.showMessageDialog(null, "ERROR! Campo invalido.", "¡ADVRTENCIA", JOptionPane.INFORMATION_MESSAGE);
                sw = 1;
            }       
        }
     
        if (txtNombre.getText().isEmpty()   ||   txtCodigo.getText().isEmpty()   ){
            JOptionPane.showMessageDialog(null, "ERROR! Casillas vacias.", "¡ADVRTENCIA", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        }
         else{
            if (Integer.parseInt(txtCodigo.getText())  <= 0 ) {
                JOptionPane.showMessageDialog(null, "ERROR! Campo invalido.", "¡ADVRTENCIA", JOptionPane.INFORMATION_MESSAGE);
                sw = 1;
            }       
        }
        
        
        
        
       
        
     
        
        
      if (sw==0)  {
        String codigo,nombre;
            codigo = txtCodigo.getText(); 
            nombre = txtNombre.getText();  
          
            
        
          String sql="INSERT INTO categoria VALUES ('" + codigo + "','" + nombre + "')";
           
            insertar(sql);   
              msj="datos guardados";
            JOptionPane.showMessageDialog(null,msj,"Mensaje",JOptionPane.INFORMATION_MESSAGE);                      
           
            limpiarTabla();
             llenarTabla();
              limpiarcampos();

    }     }
// TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
       int sw= validarBtnEliminar();
        
        if (sw==0){
    
    
    
    
        
        
        
        
         sw=0;

        
        
        if (sw == 0) {
        
        String codi=txtCodigo.getText();
        String query="";
        ResultSet existe;  
        int codE = Integer.parseInt(codi);
        
        try{
            sentencia=(Statement)conexion.createStatement();
            query="SELECT * from categoria WHERE cod_categoria =" + codE;          
            existe=sentencia.executeQuery(query);         
            if(existe.next()==true){
                
             try{   
                sentencia.executeUpdate("Delete from categoria WHERE cod_categoria =" + codE);
              JOptionPane.showMessageDialog(null, "Eliminado", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
               
             }
             catch(SQLException e){
              JOptionPane.showMessageDialog(null, "Error ,no se puede eliminar por integridad referencial", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
               
             }
             
            msj="ELIMINADO";
            }else{
               JOptionPane.showMessageDialog(null, "Error ,no se puede eliminar por integridad referencial", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
                     }
        }
        catch(SQLException e){
            msj="No existe persona, no se puede eliminar";
        }
        limpiarTabla();
        llenarTabla(); 
          limpiarcampos();
        
        }    // TODO add your handling code here:
        }
        
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
       int sw = validarBtnModificar();
      if (sw==0)  {
        sw=0;
     
        
        if (txtNombre.getText().isEmpty()   ||   txtCodigo.getText().isEmpty()   ){
             sw = 1;
        }
         else{
            if (Integer.parseInt(txtCodigo.getText())  <= 0 ) {
                JOptionPane.showMessageDialog(null, "ERROR! Campo invalido.", "¡ADVRTENCIA", JOptionPane.INFORMATION_MESSAGE);
                sw = 1;
            }       
        }
        
        
        if (sw == 0) {
        
        
        String codi=txtCodigo.getText();
        String nom = txtNombre.getText();
           
        int codE = Integer.parseInt(codi);
        String query="";
        ResultSet existe;       
        
      
       
        
              
        
        
        
        
        
        try{
            sentencia=(Statement)conexion.createStatement();
            query="SELECT * from categoria WHERE cod_categoria=" + codE;
            existe=sentencia.executeQuery(query);
            if(existe.next()==true){
             String sql = "UPDATE categoria SET nombre_categoria='" +  nom + "'WHERE cod_categoria ='" +  codE + "'";
           sentencia.executeUpdate(sql);
              
              
           JOptionPane.showMessageDialog(null, "Modificacion exitosa.", "MENSAJE", JOptionPane.INFORMATION_MESSAGE);
           
            }else{
                msj="No existe insumo";
                JOptionPane.showMessageDialog(null,msj,"INFORMACION",JOptionPane.INFORMATION_MESSAGE);  
            }
        }
        catch(SQLException e){
            msj="No existe persona, no se puede modificar";
        }
        
        
        limpiarTabla();
        llenarTabla();
        limpiarcampos();
        
        }}    // TODO add your handling code here:
    }//GEN-LAST:event_btnModificarActionPerformed

    private void tableCategoriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCategoriaMouseClicked
if(tableCategoria.getRowCount() > 0 ){
            int sel = tableCategoria.getSelectedRow();
          
            Object cod = tableCategoria.getValueAt(sel, 0);
            String auxCode = cod.toString();
             
            Object nom = tableCategoria.getValueAt(sel, 1);
            String auxNom = nom.toString();
          
            
        
            txtCodigo.setText(auxCode);
            txtNombre.setText(auxNom);
          
           
        
            
        }        // TODO add your handling code here:
    }//GEN-LAST:event_tableCategoriaMouseClicked

    private void txtCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyTyped
         int lar = 2;

        if (txtCodigo.getText().length() >= lar) {
            evt.consume();
        }

        char c = evt.getKeyChar();

        if (c < '0' || c > '9') {
            evt.consume();
        }    // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoKeyTyped

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
      int lar = 20;

        if (txtNombre.getText().length() >= lar) {
            evt.consume();
        }

       
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaActionPerformed

    private void formKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_formKeyTyped

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
            java.util.logging.Logger.getLogger(formCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formCategoria().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JTable tableCategoria;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables

}
