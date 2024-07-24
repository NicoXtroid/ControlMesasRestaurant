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
import javax.swing.table.*;
import java.util.logging.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class formProveedores extends javax.swing.JFrame {
    
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
 
    public formProveedores() {
        initComponents();
        conectar();
        this.setLocationRelativeTo(null);
        DefaultTableModel modelo = (DefaultTableModel) tblProveedores.getModel();
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        txtFecha.setText(dtf.format(localDate));
        this.getContentPane().setBackground(Color.orange);
        
        setIcon();
        
    }

    public formProveedores(String ID, String Pass){
        initComponents();
        conectar();
        llenarTabla();
        this.setLocationRelativeTo(null);
        
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        txtFecha.setText(dtf.format(localDate));
        this.getContentPane().setBackground(Color.orange);
        
        setIcon();
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
     
    public Integer validarBtnEliminar(){
    Integer swAceptar = 0 ;
    
         Integer sw=0;
         DefaultTableModel modelo = (DefaultTableModel) tblProveedores.getModel(); 
      int largo = modelo.getRowCount();
        if   ( largo  <=0){
       
       
                JOptionPane.showMessageDialog(null, "Tabla vacia .", "¡ERROR!", JOptionPane.INFORMATION_MESSAGE);  
               sw = 1;
       };
         
         
         
         
         
         
         
         
         
         
         
       if (txtRut.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo rut vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }
        if (txtNombre.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo nombre vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }
         if (txtApP.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo apellido paterno vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }
          if (txtApM.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo apellido materno vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }
           if (txtFono.getText().isEmpty()) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo fono vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }
          if (txtMail.getText().isEmpty()) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo deuda vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
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
                query = "SELECT * FROM `proveedores` WHERE rut_proveedor = " + auxRutProveedor;
                
                existe = sentencia.executeQuery(query);
                
                if (existe.next() == false) {
               
              JOptionPane.showMessageDialog(null, "Error no existe el proveedor .", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);  
                 sw = 1;
                } 
            } catch (SQLException e) {
               
                 sw = 1;
            }
       }
        
        
       
  
    swAceptar = sw;
    return swAceptar;
    
    
    }
    
    
    
    
    
    
    
    
    
    
    public Integer validarBtnModificar(){
    Integer swAceptar = 0 ;
     Integer sw=0;
         DefaultTableModel modelo = (DefaultTableModel) tblProveedores.getModel(); 
      int largo = modelo.getRowCount();
        if   ( largo  <=0){
       
       
                JOptionPane.showMessageDialog(null, "Tabla vacia .", "¡ERROR!", JOptionPane.INFORMATION_MESSAGE);  
               sw = 1;
       };
          
       if (txtRut.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo rut vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }
        if (txtNombre.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo nombre vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }
         if (txtApP.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo apellido paterno vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }
          if (txtApM.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo apellido materno vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }
           if (txtFono.getText().isEmpty()) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo fono vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }
          if (txtMail.getText().isEmpty()) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo deuda vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
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
                query = "SELECT * FROM `proveedores` WHERE rut_proveedor = " + auxRutProveedor;
                
                existe = sentencia.executeQuery(query);
                
                if (existe.next() == false) {
               
              JOptionPane.showMessageDialog(null, "Error no existe el proveedor .", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);  
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
    
    
    public Integer validarBtnAgregar(){
    Integer swAceptar = 0 ;
    
     
        Integer sw=0;
          if (validarMail(txtMail.getText()) == 1){
        JOptionPane.showMessageDialog(null, "ERROR! Formato mail erroneo.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        
        }
         
         
       if (txtRut.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo rut vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }
        if (txtNombre.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo nombre vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }
         if (txtApP.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo apellido paterno vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }
          if (txtApM.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo apellido materno vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }
           if (txtFono.getText().isEmpty()) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo fono vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }
          if (txtMail.getText().isEmpty()) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo deuda vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
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
                query = "SELECT * FROM `proveedores` WHERE rut_proveedor = " + auxRutProveedor;
                
                existe = sentencia.executeQuery(query);
                
                if (existe.next() == false) {
               
               
                } else {
              JOptionPane.showMessageDialog(null, "ERROR!YA EXISTE proveedor.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
                 sw = 1;
                }
            } catch (SQLException e) {
               
                 sw = 1;
            }
       }
        
        
     
    
    swAceptar = sw;
    return swAceptar;
    };
       
  public void insertar(String sql){
        try{
            sentencia.executeUpdate(sql);
            msj="datos guardados";
            JOptionPane.showMessageDialog(null,msj,"Datos guardados",JOptionPane.INFORMATION_MESSAGE);                      
        }
        catch(SQLException e){
            msj="no ingreso";         
        }
    }
    
     public void actualizar(String rut,String nombre,String paterno, String materno, String direccion, String tele, String correo){
        String upSQL="";
          try{
            upSQL=("UPDATE proveedores SET rut-proveedor='" + rut + "',nombre-proveedor='" + nombre + "',ap-paterno=" + paterno + 
                    " ");
                       /*^ alli va el nombre de la tabla a actu */           
            sentencia.executeUpdate(upSQL);
            msj="datos actualizados";
         }
        catch (SQLException e){
            msj="no actualizo";
        }
    }
     
  
    public void llenarTabla(){
        String auxRut = "";
        String auxNom = "";
        String auxApp = "";
        String auxApm = "";
        String auxDir = "";
        String auxFono = "";
        String auxMail = "";
        String query = "";     
        
        try{
            sentencia=(Statement)conexion.createStatement();
            ResultSet lista=sentencia.executeQuery("SELECT * FROM proveedores");
            int cont=0;
            while(lista.next()){
                auxRut = (lista.getString("rut_proveedor"));
               
                
                auxNom = (lista.getString("nombre_proveedor")); 
                auxApp = (lista.getString("ap_paterno"));
                auxApm = (lista.getString("ap_materno")); 
                auxDir = (lista.getString("direccion")); 
                auxFono = (lista.getString("fono")); 
                auxMail = (lista.getString("email")); 
              
                 
                
                DefaultTableModel modelo = (DefaultTableModel) tblProveedores.getModel();
                modelo.addRow(new Object[]{auxRut,auxNom,auxApp,auxApm,auxDir,auxFono,auxMail});                
                cont = cont + 1; 
                
                
              
            }
        }
        catch(SQLException e){
            msj="no se pudo seleccionar";
        }
      
    }
    
 
    public void limpiarTabla(){        
        try {
            DefaultTableModel modelo=(DefaultTableModel) tblProveedores.getModel();
            int filas=tblProveedores.getRowCount();
            for (int i = 0;filas>i; i++) {
                modelo.removeRow(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
        }
    }
    
    
    public void limpiarCampos(){
        txtRut.setText("");
        txtNombre.setText("");
        txtApP.setText("");
        txtApM.setText("");
        txtFono.setText("");
        txtMail.setText("");
        txtDirecc.setText("");
    
    } 
    
    
    
    
    ////////////////////////////////////////////////////////////////////////////
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        tblProveedores = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }

        };
        lblRut = new javax.swing.JLabel();
        txtRut = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        lblApP = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtApP = new javax.swing.JTextField();
        lblApM = new javax.swing.JLabel();
        txtApM = new javax.swing.JTextField();
        lblDirecc = new javax.swing.JLabel();
        lblFono = new javax.swing.JLabel();
        lblMail = new javax.swing.JLabel();
        txtDirecc = new javax.swing.JTextField();
        txtFono = new javax.swing.JTextField();
        txtMail = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tblProveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Rut", "Nombre", "Ap Paterno", "Ap Materno", "Direccion", "Fono", "Email"
            }
        ));
        tblProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProveedoresMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblProveedores);

        lblRut.setText("Rut: ");

        txtRut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRutActionPerformed(evt);
            }
        });
        txtRut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRutKeyTyped(evt);
            }
        });

        lblNombre.setText("Nombre:");

        lblApP.setText("Apellido Paterno:");

        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });

        txtApP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApPKeyTyped(evt);
            }
        });

        lblApM.setText("Apellido Materno:");

        txtApM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApMKeyTyped(evt);
            }
        });

        lblDirecc.setText("Direccion:");

        lblFono.setText("Fono:");

        lblMail.setText("E-mail:");

        txtDirecc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccKeyTyped(evt);
            }
        });

        txtFono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFonoKeyTyped(evt);
            }
        });

        txtMail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMailKeyTyped(evt);
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

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel1.setText("Inserción de datos");

        jLabel2.setText("Lista de proveedores");

        jSeparator2.setMaximumSize(new java.awt.Dimension(32767, 12));

        jLabel3.setText("Fecha");

        btnRegresar.setText("Atras");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        jLabel4.setText("Registro Proveedores");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblDirecc)
                                        .addGap(10, 10, 10)
                                        .addComponent(txtDirecc)
                                        .addGap(10, 10, 10)
                                        .addComponent(txtApM, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnAgregar)
                                        .addGap(44, 44, 44)
                                        .addComponent(btnModificar)
                                        .addGap(42, 42, 42)
                                        .addComponent(btnEliminar)
                                        .addGap(47, 47, 47)
                                        .addComponent(btnCancelar))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblFono)
                                        .addGap(10, 10, 10)
                                        .addComponent(txtFono, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(69, 69, 69)
                                        .addComponent(lblMail)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtMail, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblNombre, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(lblApP)
                                                .addGap(10, 10, 10)
                                                .addComponent(txtApP, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblRut)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtRut, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(72, 72, 72)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(30, 30, 30)
                                                .addComponent(lblApM))
                                            .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtNombre))))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 24, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(131, 131, 131)
                        .addComponent(btnRegresar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(btnRegresar)
                            .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel1)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblRut)
                            .addComponent(txtRut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNombre)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblApP)
                    .addComponent(txtApP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblApM)
                    .addComponent(txtApM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDirecc)
                    .addComponent(txtDirecc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFono)
                    .addComponent(lblMail)
                    .addComponent(txtFono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAgregar)
                        .addComponent(btnModificar)
                        .addComponent(btnCancelar)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        //new fLogin().setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed
 public int validarMail(String pMail) {
      Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
 
        
        String email = pMail;
 int resultadox = 0 ;
        Matcher mather = pattern.matcher(email);
 
        if (mather.find() == true) {
            System.out.println("El email ingresado es válido.");
        } else {
            System.out.println("El email ingresado es inválido.");
            resultadox = 1;
        }
        
        return resultadox;
    }
    
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        txtApM.setText("");
        txtApP.setText("");
        txtDirecc.setText("");
        txtFono.setText("");
        txtMail.setText("");
        txtNombre.setText("");
        txtRut.setText("");
         limpiarCampos();
        
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
       Integer sw = validarBtnAgregar();
    if (sw == 0) {
      
        String rut,nombre,app,apm,direc,fono,mail;
            rut = txtRut.getText(); 
            nombre = txtNombre.getText(); 
            app = txtApP.getText(); 
            apm = txtApM.getText();
            direc = txtDirecc.getText(); 
            fono =  txtFono.getText();
            mail = txtMail.getText(); 
           String sql="INSERT INTO proveedores VALUES ('" + rut + "','" + nombre + "','" + app  +  "','" + apm + "','" + direc + "','" + fono + "','" + mail + "')";
             insertar(sql);           
          
        limpiarTabla();
        llenarTabla();
        limpiarCampos();
      
    }
    }//GEN-LAST:event_btnAgregarActionPerformed

    
    
    
    
    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
  Integer sw = validarBtnEliminar();
    
      
        
        if (sw == 0) {
        
        
        
        
        
       
        
        String codi=txtRut.getText();
        String query="";
        ResultSet existe;  
       String codE = codi;
              
                
     try{
      
           
            sentencia=(Statement)conexion.createStatement();
             query = "SELECT * FROM `proveedores` WHERE rut_proveedor = '" +  codE + "'";
               
           existe=sentencia.executeQuery(query);
          
            if(existe.next()==false){
         msj="No existe insumo";
                JOptionPane.showMessageDialog(null,msj,"INFORMACION",JOptionPane.INFORMATION_MESSAGE);  
               
            }else{
                
               sentencia.executeUpdate("Delete from proveedores WHERE rut_proveedor ='" + codE + "'");
            
            msj="ELIMINADO";
            JOptionPane.showMessageDialog(null,msj,"INFORMACION",JOptionPane.INFORMATION_MESSAGE);            
           
                 }
        }
        catch(SQLException e){
            msj="No se puede eliminar el proveedor por integridad referencial";
              
                JOptionPane.showMessageDialog(null,msj,"INFORMACION",JOptionPane.INFORMATION_MESSAGE);  
           
        }
        limpiarTabla();
        llenarTabla(); 
        limpiarCampos();
        
        }    
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void tblProveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProveedoresMouseClicked
      if(tblProveedores.getRowCount() > 0 ){
            int sel = tblProveedores.getSelectedRow();
            Object cod = tblProveedores.getValueAt(sel, 0);
            String auxRut = cod.toString();
            
            Object stoA = tblProveedores.getValueAt(sel, 1);
            String auxNom = stoA.toString();
            
            Object stoC = tblProveedores.getValueAt(sel, 2);
            String auxApP = stoC.toString();
            
            Object tipoS = tblProveedores.getValueAt(sel, 3);
            String auxApM = tipoS.toString();
            
           
            Object nom = tblProveedores.getValueAt(sel, 4);
            String auxDirecc = nom.toString();
            
            Object cat = tblProveedores.getValueAt(sel, 5);
            String auxFono = cat.toString();
            
              Object mailx = tblProveedores.getValueAt(sel, 6);
            String auxMail = cat.toString();
            
            
            
            //NICO R, $%$%$y DIEGO JARA :V V:%&$· TODOS LOS DERECHOS RESERVADOS//
            txtRut.setText(auxRut);
            txtNombre.setText(auxNom);
            txtApP.setText(auxApP);
            txtApM.setText(auxApM);
            txtDirecc.setText(auxDirecc);
            txtFono.setText(auxFono);
            txtMail.setText(auxMail);
            
        }   // TODO add your handling code here:
    }//GEN-LAST:event_tblProveedoresMouseClicked

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
Integer sw = validarBtnModificar();
    
      
        
        if (sw == 0) {
        
        
        String rut,nombre,app,apm,direc,fono,mail;
            rut = txtRut.getText(); 
            nombre = txtNombre.getText();  
            app = txtApP.getText();
            apm = txtApM.getText(); 
            direc = txtDirecc.getText(); 
            fono =  txtFono.getText(); 
            mail = txtMail.getText(); 
            
      String codE =  rut;
        String query="";
        ResultSet existe;       
        
        int cat = 0;
        
              
        
        
        
        
        
        try{
            sentencia=(Statement)conexion.createStatement();
            query="SELECT * from proveedores WHERE rut_proveedor ='" + codE + "'" ;
           
            existe=sentencia.executeQuery(query);
            if(existe.next()==true){
                 String sql = "UPDATE proveedores SET nombre_proveedor='" +  nombre + "'WHERE rut_proveedor ='" +   rut + "'";
                         sentencia.executeUpdate(sql);
                   sql = "UPDATE proveedores SET ap_paterno='" +  app + "'WHERE rut_proveedor ='" +   rut + "'";
                         sentencia.executeUpdate(sql);
                   sql = "UPDATE proveedores SET ap_materno='" +  apm + "'WHERE rut_proveedor ='" +   rut + "'";
                         sentencia.executeUpdate(sql);
                    sql = "UPDATE proveedores SET direccion='" +  direc + "'WHERE rut_proveedor ='" +   rut + "'";
                         sentencia.executeUpdate(sql);            
                    sql = "UPDATE proveedores SET fono='" +  fono + "'WHERE rut_proveedor ='" +   rut + "'";
                         sentencia.executeUpdate(sql);
                   sql = "UPDATE proveedores SET  email='" +   mail + "'WHERE rut_proveedor ='" +   rut + "'";
                         sentencia.executeUpdate(sql);
                              
                
                
            
            msj="Modificado";
            JOptionPane.showMessageDialog(null,msj,"INFORMACION",JOptionPane.INFORMATION_MESSAGE);            
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
        limpiarCampos();
        
        }
    // TODO add your handling code here:
    }//GEN-LAST:event_btnModificarActionPerformed

    private void txtRutKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRutKeyTyped
      int lar = 9;

        if (txtRut.getText().length() >= lar) {
            evt.consume();
        }

        char c = evt.getKeyChar();

        if (c < '0' || c > '9') {
            evt.consume();
        }       // TODO add your handling code here:     // TODO add your handling code here:
    }//GEN-LAST:event_txtRutKeyTyped

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
     int lar = 15;

        if (txtNombre.getText().length() >= lar) {
            evt.consume();
        }    // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtApPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApPKeyTyped
  int lar = 15;

        if (txtApP.getText().length() > lar) {
            evt.consume();
        }    // TODO add your handling code here:      // TODO add your handling code here:
    }//GEN-LAST:event_txtApPKeyTyped

    private void txtApMKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApMKeyTyped
    int lar = 15;

        if (txtApM.getText().length() > lar) {
            evt.consume();
        }    // TODO add your handling code here:    // TODO add your handling code here:
    }//GEN-LAST:event_txtApMKeyTyped

    private void txtDireccKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccKeyTyped
    int lar = 30;

        if (txtDirecc.getText().length() > lar) {
            evt.consume();
        }    // TODO add your handling code here:    // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccKeyTyped

    private void txtFonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFonoKeyTyped
   int lar = 12;

        if (txtFono.getText().length() > lar) {
            evt.consume();
        }    // TODO add your handling code here:     // TODO add your handling code here:
    }//GEN-LAST:event_txtFonoKeyTyped

    private void txtMailKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMailKeyTyped
     int lar = 30;

        if (txtMail.getText().length() > lar) {
            evt.consume();
        }    // TODO add your handling code here:   // TODO add your handling code here:
    }//GEN-LAST:event_txtMailKeyTyped

    private void txtRutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRutActionPerformed
    int lar = 9;

             // TODO add your handling code here:
    }//GEN-LAST:event_txtRutActionPerformed

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
            java.util.logging.Logger.getLogger(formProveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formProveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formProveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formProveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formProveedores().setVisible(true);
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblApM;
    private javax.swing.JLabel lblApP;
    private javax.swing.JLabel lblDirecc;
    private javax.swing.JLabel lblFono;
    private javax.swing.JLabel lblMail;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblRut;
    private javax.swing.JTable tblProveedores;
    private javax.swing.JTextField txtApM;
    private javax.swing.JTextField txtApP;
    private javax.swing.JTextField txtDirecc;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtFono;
    private javax.swing.JTextField txtMail;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtRut;
    // End of variables declaration//GEN-END:variables
}
