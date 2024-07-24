
import java.awt.Color;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import javax.swing.JOptionPane;


public class formMenu extends javax.swing.JFrame {

    //public int numpe;
    private Statement sentencia; /*puedo ocupar sentencias de control sql*/
    private Connection conexion; /*objeto que permite conectar mi base datos con programa*/
    private String nomBD="bussandwicheria";
    private String usuario="root";
    private String password="informatica"; 
    private String msj; 
    
    
    public formMenu() {
        initComponents();
        conectar();
        this.setLocationRelativeTo(null);
        setIcon();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        txtFecha.setText(dtf.format(localDate));
        //GetNumeroPedido();
        GetTurno();
        this.getContentPane().setBackground(Color.orange);
    }
        

    public formMenu(String text, String text0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
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
    
    public void insertar(String sql){
        try{
            sentencia.executeUpdate(sql);
            msj="Datos guardados";
            //JOptionPane.showMessageDialog(null,msj,"INFORMACION",JOptionPane.INFORMATION_MESSAGE);                      
           // lblEstado.setText(msj);
        }
        catch(SQLException e){
            System.out.print(e.getMessage());
            msj="no ingreso";         
        }
    }
    
    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagen/icono.png")));
    }
    
    /*
    public int GetNumeroPedido(int codimesa, boolean ocup) {
        int cod_ped=0;        
        if (ocup == false){
            cod_ped = GetPedidoNuevo();
            System.out.println("PEDIDO NUEVO");
        }else{            
            // obtiene pedido "activo"
            try {
                sentencia = (Statement) conexion.createStatement();
                ResultSet lista = sentencia.executeQuery("SELECT MAX(numero_pedido) FROM detalle_mesa where fecha_venta = '" + txtFecha.getText() + "' and  numero_mesa = " + codimesa);
                int cont = 0;
                while (lista.next()) {
                    cod_ped = (lista.getInt("numero_pedido"));
                }
            }catch (SQLException e) {
             }
        }
        
        return cod_ped;
    }
    */
        
    
    
    public int GetPedidoNuevo(){
        //JOptionPane.showMessageDialog(null,"ETRO pedido nuevo","INFORMACION",JOptionPane.INFORMATION_MESSAGE); 
        //System.out.println("NUM PEDIDO NUEVO");
        String query="";
        ResultSet existe; 
        int num=0;
        int numped = 0;
        try {            
            sentencia = (Statement) conexion.createStatement();
            query = "SELECT * FROM pedido where fecha_venta = '" + txtFecha.getText() + "'";            
            existe = sentencia.executeQuery(query);
            if (existe.next() == false) {
                numped = 1;                
            } else {
                try {
                    //JOptionPane.showMessageDialog(null,"Entro sl try de el while de pedido nuevo","INFORMACION",JOptionPane.INFORMATION_MESSAGE);
                    sentencia = (Statement) conexion.createStatement();
                    ResultSet lista = sentencia.executeQuery("SELECT * FROM pedido where fecha_venta = '" + txtFecha.getText() + "'") ;
                                          
                    //ResultSet lista = sentencia.executeQuery("SELECT * FROM pedido where fecha_venta = '2017/12/02'");
                    
                    //JOptionPane.showMessageDialog(null,"SELECT * FROM pedido where fecha_venta = '2017/12/12'","INFORMACION",JOptionPane.INFORMATION_MESSAGE);
                    
                    int cont = 0;
                    while (lista.next()) {
                        //JOptionPane.showMessageDialog(null,"ETRO WHILE","INFORMACION",JOptionPane.INFORMATION_MESSAGE); 
                        num = (lista.getInt("numero_pedido"));
                        numped = num + 1;
                        //JOptionPane.showMessageDialog(null,numped,"INFORMACION",JOptionPane.INFORMATION_MESSAGE); 
                    }
                }catch (SQLException e) {                    
                }                
                //numped = num + 1;
            }
        } catch (SQLException e) {
            msj = "No existe persona, no se puede modificar";
        }
        return numped;
    }
    
    
        public String GetEstadoPedido(int pedido){
        String state = "";
        String query = "";        
        ResultSet existe;
        String valFecha = txtFecha.getText();
        
        try {
            //JOptionPane.showMessageDialog(null,"try 1","GetEstadoPedido",JOptionPane.INFORMATION_MESSAGE);            
            sentencia = (Statement) conexion.createStatement();
            query = "SELECT * from pedido where numero_pedido = " + pedido + " and fecha_venta = '" + valFecha + "'";
            existe = sentencia.executeQuery(query);            
            
            //JOptionPane.showMessageDialog(null,valFecha,"GetEstadoPedido",JOptionPane.INFORMATION_MESSAGE);
            if (existe.next() == false){
                state = "N";                
            } else {
                try {
                    //JOptionPane.showMessageDialog(null,"try 2","GetEstadoPedido",JOptionPane.INFORMATION_MESSAGE);
                    sentencia = (Statement) conexion.createStatement();
                    ResultSet lista = sentencia.executeQuery("SELECT estado_pedido FROM pedido where numero_pedido = " + pedido + " and fecha_venta = '" + valFecha + "'");
                    
                    while (lista.next()) {
                        state = (lista.getString("estado_pedido"));
                    }
                } catch (SQLException e) {
                    state = "N"; 
                }

            }       
            
            
            /*
            if (existe.next() == false) {
                state = "N";                
            } else {
                try {                    
                    sentencia = (Statement) conexion.createStatement();
                    ResultSet lista = sentencia.executeQuery("SELECT estado_pedido FROM pedido where numero_pedido = " + pedido);
                    
                    while (lista.next()) {
                        state = (lista.getString("estado_pedido"));
                    }
                } catch (SQLException e) {
                }

            } */
        } catch (SQLException e) {
            state = "N";
        }
        
        return state;
    }
    
    
    public int ConsultarEstadoMesa(int codmesa){
        //JOptionPane.showMessageDialog(null,"ETRO CONsultar estado mesa","INFORMACION",JOptionPane.INFORMATION_MESSAGE); 
        boolean ocupada = false;
        int cod_ped = 0;
        String estado = "";        
        try{
            //JOptionPane.showMessageDialog(null,"try estado mesa","INFORMACION",JOptionPane.INFORMATION_MESSAGE); 
            sentencia = (Statement) conexion.createStatement();
            ResultSet lista = sentencia.executeQuery("SELECT estado FROM mesas where numero_mesa = " + codmesa);
            while (lista.next()) {
                estado = (lista.getString("estado"));
                
                if (estado.equals("L")){
                    ocupada = false;                
                }else{
                    ocupada = true;
                }                
            }
        }catch (SQLException e) {
        
        }
        
        if (ocupada == false){
            //JOptionPane.showMessageDialog(null,"if ocuada false","INFORMACION",JOptionPane.INFORMATION_MESSAGE); 
            cod_ped = GetPedidoNuevo();
        }else{            
            // obtiene pedido "activo"
            try {
                //JOptionPane.showMessageDialog(null,"try ocupada true","INFORMACION",JOptionPane.INFORMATION_MESSAGE); 
                sentencia = (Statement) conexion.createStatement();
                ResultSet lista = sentencia.executeQuery("SELECT numero_pedido FROM detalle_mesa where fecha_venta = '" + txtFecha.getText() + "' and  numero_mesa = " + codmesa);
                int cont = 0;
                while (lista.next()) {
                    cod_ped = (lista.getInt("numero_pedido"));
                    //cod_ped = cod_ped + 1;
                }
            }catch (SQLException e) {
             }
        }        
        return cod_ped;  
        
        //return ocupada;
    }
    
    
    public int consultar_estado_llevar(int v_idllevar){
        int estado = 0;        
        try{
            //JOptionPane.showMessageDialog(null,"try estado mesa","INFORMACION",JOptionPane.INFORMATION_MESSAGE); 
            sentencia = (Statement) conexion.createStatement();
            ResultSet lista = sentencia.executeQuery("SELECT estado_llevar FROM llevar where id_llevar = " + v_idllevar);
            while (lista.next()) {
                estado = (lista.getInt("estado_llevar"));
                              
            }
        }catch (SQLException e) {
        
        }
        
        return estado;
    }
    
    public int consultar_pedido_llevar(int v_idllevar){        
        boolean ocupada = false;
        int cod_ped = 0;
        int estado = 0;        
        try{
            //JOptionPane.showMessageDialog(null,"try estado mesa","INFORMACION",JOptionPane.INFORMATION_MESSAGE); 
            sentencia = (Statement) conexion.createStatement();
            ResultSet lista = sentencia.executeQuery("SELECT estado_llevar FROM llevar where id_llevar = " + v_idllevar);
            while (lista.next()) {
                estado = (lista.getInt("estado_llevar"));                
                if (estado ==(0)){
                    ocupada = false;                
                }else{
                    ocupada = true;
                }                
            }
        }catch (SQLException e) {
        
        }
        
        if (ocupada == false){            
            cod_ped = GetPedidoNuevo();
        }else{            
            // obtiene pedido "activo"
            try {
                //JOptionPane.showMessageDialog(null,"try ocupada true","INFORMACION",JOptionPane.INFORMATION_MESSAGE); 
                sentencia = (Statement) conexion.createStatement();
                ResultSet lista = sentencia.executeQuery("SELECT numero_ped FROM llevar where id_llevar = " + v_idllevar);
                int cont = 0;
                while (lista.next()) {
                    cod_ped = (lista.getInt("numero_ped"));
                    //cod_ped = cod_ped + 1;
                }
            }catch (SQLException e) {
            }
        }   
        
        return cod_ped;              
    }
    
    public String GetTurno(){
        Calendar calendario = Calendar.getInstance();        
        String watashinoturn = "";
        int hora, minutos;
        
        hora =calendario.get(Calendar.HOUR_OF_DAY);
        minutos = calendario.get(Calendar.MINUTE);
        
        if (hora < 17){
            watashinoturn = "D";
        }else{
            watashinoturn = "V";
        }
        return watashinoturn;
    }
    
    
    
    
    
    
    
    
    ///////////////////////// Botones //////////////
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnM2 = new javax.swing.JButton();
        btnM1 = new javax.swing.JButton();
        btnM3 = new javax.swing.JButton();
        btnM4 = new javax.swing.JButton();
        btnM5 = new javax.swing.JButton();
        btnM6 = new javax.swing.JButton();
        btnM7 = new javax.swing.JButton();
        btnM8 = new javax.swing.JButton();
        btnM9 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        btnL1 = new javax.swing.JButton();
        btnRegresar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        txtFecha = new javax.swing.JTextField();
        btnL2 = new javax.swing.JButton();
        btnL3 = new javax.swing.JButton();
        btnL4 = new javax.swing.JButton();
        btnL5 = new javax.swing.JButton();
        btnL6 = new javax.swing.JButton();
        btnL7 = new javax.swing.JButton();
        btnL8 = new javax.swing.JButton();
        btnL9 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Layout de Mesas");
        setResizable(false);

        jLabel1.setText("Mesas");

        btnM2.setText("Mesa 2");
        btnM2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnM2ActionPerformed(evt);
            }
        });

        btnM1.setText("Mesa 1");
        btnM1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnM1ActionPerformed(evt);
            }
        });

        btnM3.setText("Mesa 3");
        btnM3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnM3ActionPerformed(evt);
            }
        });

        btnM4.setText("Mesa 4");
        btnM4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnM4ActionPerformed(evt);
            }
        });

        btnM5.setText("Mesa 5");
        btnM5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnM5ActionPerformed(evt);
            }
        });

        btnM6.setText("Mesa 6");
        btnM6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnM6ActionPerformed(evt);
            }
        });

        btnM7.setText("Mesa 7");
        btnM7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnM7ActionPerformed(evt);
            }
        });

        btnM8.setText("Mesa 8");
        btnM8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnM8ActionPerformed(evt);
            }
        });

        btnM9.setText("Mesa 9");
        btnM9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnM9ActionPerformed(evt);
            }
        });

        jLabel2.setText("Pedidos");

        btnL1.setText("LLevar 1");
        btnL1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnL1ActionPerformed(evt);
            }
        });

        btnRegresar.setText("Atrás");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        jLabel3.setText("Fecha");

        jLabel4.setText("\"EL PRIVILEGIO DEL SABOR\"");

        jButton11.setText("Ingresar Merma");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        txtFecha.setEditable(false);

        btnL2.setText("LLevar 2");
        btnL2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnL2ActionPerformed(evt);
            }
        });

        btnL3.setText("LLevar 3");
        btnL3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnL3ActionPerformed(evt);
            }
        });

        btnL4.setText("LLevar 4");
        btnL4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnL4ActionPerformed(evt);
            }
        });

        btnL5.setText("LLevar 5");
        btnL5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnL5ActionPerformed(evt);
            }
        });

        btnL6.setText("LLevar 6");
        btnL6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnL6ActionPerformed(evt);
            }
        });

        btnL7.setText("LLevar 7");
        btnL7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnL7ActionPerformed(evt);
            }
        });

        btnL8.setText("LLevar 8");
        btnL8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnL8ActionPerformed(evt);
            }
        });

        btnL9.setText("LLevar 9");
        btnL9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnL9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnM7, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnM1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnM4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnM5, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnM8, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnM2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnM9, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnM6, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnM3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jSeparator2))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel3)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(257, 257, 257)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(120, 120, 120)
                                        .addComponent(jLabel4))
                                    .addComponent(jSeparator3))
                                .addGap(0, 14, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnRegresar)))
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton11)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnL1)
                        .addGap(50, 50, 50)
                        .addComponent(btnL2))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnL7)
                            .addComponent(btnL4))
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnL5)
                            .addComponent(btnL8))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnL3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnL6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnL9, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(45, 45, 45))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRegresar)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnM2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnM3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnM1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnM4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnM5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnM6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnM7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnM8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnM9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(64, 64, 64)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnL2)
                    .addComponent(btnL1)
                    .addComponent(btnL3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnL4)
                    .addComponent(btnL5)
                    .addComponent(btnL6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnL7)
                    .addComponent(btnL8)
                    .addComponent(btnL9))
                .addGap(32, 32, 32)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton11)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        //new fLogin().setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        formMerma merm = new formMerma();
        //this.dispose();
        //this.setVisible(false);
        merm.setVisible(true);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void btnM1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnM1ActionPerformed
        int num_pedido = ConsultarEstadoMesa(0);
        String turno_act = GetTurno();
        String stat = GetEstadoPedido(num_pedido);
        //JOptionPane.showMessageDialog(null,stat,"estadoo pedido",JOptionPane.INFORMATION_MESSAGE);
        //System.out.println(num_pedido);
        System.out.println(stat);
        
        formPedido pedi = new formPedido(txtFecha.getText(), 0, num_pedido, "S", stat, turno_act);
        System.out.println("menu, estado: " + stat);
        //this.dispose();
        //this.setVisible(false);
        pedi.setVisible(true);
    }//GEN-LAST:event_btnM1ActionPerformed

    private void btnM2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnM2ActionPerformed
        int num_pedido = ConsultarEstadoMesa(1);
        String turno_act = GetTurno();
        String stat = GetEstadoPedido(num_pedido);
        
        formPedido pedi = new formPedido(txtFecha.getText(), 1, num_pedido, "S", stat, turno_act);
        pedi.setVisible(true);
    }//GEN-LAST:event_btnM2ActionPerformed

    private void btnM3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnM3ActionPerformed
        int num_pedido = ConsultarEstadoMesa(2);
        String turno_act = GetTurno();
        String stat = GetEstadoPedido(num_pedido);
        
        formPedido pedi = new formPedido(txtFecha.getText(), 2, num_pedido, "S", stat, turno_act);
        pedi.setVisible(true);
    }//GEN-LAST:event_btnM3ActionPerformed

    private void btnM4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnM4ActionPerformed
        int num_pedido = ConsultarEstadoMesa(3);
        String turno_act = GetTurno();
        String stat = GetEstadoPedido(num_pedido);
        
        formPedido pedi = new formPedido(txtFecha.getText(), 3, num_pedido, "S", stat, turno_act);
        pedi.setVisible(true);
    }//GEN-LAST:event_btnM4ActionPerformed

    private void btnM5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnM5ActionPerformed
        int num_pedido = ConsultarEstadoMesa(4);
        String turno_act = GetTurno();
        String stat = GetEstadoPedido(num_pedido);
        
        formPedido pedi = new formPedido(txtFecha.getText(), 4, num_pedido, "S", stat, turno_act);
        pedi.setVisible(true);
    }//GEN-LAST:event_btnM5ActionPerformed

    private void btnM6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnM6ActionPerformed
        int num_pedido = ConsultarEstadoMesa(5);
        String turno_act = GetTurno();
        String stat = GetEstadoPedido(num_pedido);
        
        formPedido pedi = new formPedido(txtFecha.getText(), 5, num_pedido, "S", stat, turno_act);
        pedi.setVisible(true);
    }//GEN-LAST:event_btnM6ActionPerformed

    private void btnM7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnM7ActionPerformed
        int num_pedido = ConsultarEstadoMesa(6);
        String turno_act = GetTurno();
        String stat = GetEstadoPedido(num_pedido);
        
        formPedido pedi = new formPedido(txtFecha.getText(), 6, num_pedido, "S", stat, turno_act);
        pedi.setVisible(true);
    }//GEN-LAST:event_btnM7ActionPerformed

    private void btnM8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnM8ActionPerformed
        int num_pedido = ConsultarEstadoMesa(7);
        String turno_act = GetTurno();
        String stat = GetEstadoPedido(num_pedido);
        
        formPedido pedi = new formPedido(txtFecha.getText(), 7, num_pedido, "S", stat, turno_act);
        pedi.setVisible(true);
    }//GEN-LAST:event_btnM8ActionPerformed

    private void btnM9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnM9ActionPerformed
        int num_pedido = ConsultarEstadoMesa(8);
        String turno_act = GetTurno();
        String stat = GetEstadoPedido(num_pedido);
        
        formPedido pedi = new formPedido(txtFecha.getText(), 8, num_pedido, "S", stat, turno_act);
        pedi.setVisible(true);
    }//GEN-LAST:event_btnM9ActionPerformed

    private void btnL1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnL1ActionPerformed
        //int num_pedido = ConsultarEstadoMesa(0);
        int num_pedido = consultar_pedido_llevar(1);
        String turno_act = GetTurno();
        String stat = GetEstadoPedido(num_pedido);
        
        formPedido pedi = new formPedido(txtFecha.getText(), num_pedido, "L", stat, turno_act,1);
        pedi.setVisible(true);
    }//GEN-LAST:event_btnL1ActionPerformed

    private void btnL2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnL2ActionPerformed
        int num_pedido = GetPedidoNuevo();
        String turno_act = GetTurno();
        String stat = GetEstadoPedido(num_pedido);
        
        formPedido pedi = new formPedido(txtFecha.getText(), num_pedido, "L", stat, turno_act,2);
        pedi.setVisible(true);
    }//GEN-LAST:event_btnL2ActionPerformed

    private void btnL3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnL3ActionPerformed
        int num_pedido = GetPedidoNuevo();
        String turno_act = GetTurno();
        String stat = GetEstadoPedido(num_pedido);
        
        formPedido pedi = new formPedido(txtFecha.getText(), num_pedido, "L", stat, turno_act,3);
        pedi.setVisible(true);
    }//GEN-LAST:event_btnL3ActionPerformed

    private void btnL4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnL4ActionPerformed
        int num_pedido = consultar_pedido_llevar(1);
        String turno_act = GetTurno();
        String stat = GetEstadoPedido(num_pedido);
        
        formPedido pedi = new formPedido(txtFecha.getText(), num_pedido, "L", stat, turno_act,4);
        pedi.setVisible(true);
    }//GEN-LAST:event_btnL4ActionPerformed

    private void btnL5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnL5ActionPerformed
        int num_pedido = consultar_pedido_llevar(1);
        String turno_act = GetTurno();
        String stat = GetEstadoPedido(num_pedido);
        
        formPedido pedi = new formPedido(txtFecha.getText(), num_pedido, "L", stat, turno_act,5);
        pedi.setVisible(true);
    }//GEN-LAST:event_btnL5ActionPerformed

    private void btnL6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnL6ActionPerformed
        int num_pedido = consultar_pedido_llevar(1);
        String turno_act = GetTurno();
        String stat = GetEstadoPedido(num_pedido);
        
        formPedido pedi = new formPedido(txtFecha.getText(), num_pedido, "L", stat, turno_act,6);
        pedi.setVisible(true);
    }//GEN-LAST:event_btnL6ActionPerformed

    private void btnL7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnL7ActionPerformed
        int num_pedido = consultar_pedido_llevar(1);
        String turno_act = GetTurno();
        String stat = GetEstadoPedido(num_pedido);
        
        formPedido pedi = new formPedido(txtFecha.getText(), num_pedido, "L", stat, turno_act, 7);
        pedi.setVisible(true);
    }//GEN-LAST:event_btnL7ActionPerformed

    private void btnL8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnL8ActionPerformed
        int num_pedido = consultar_pedido_llevar(1);
        String turno_act = GetTurno();
        String stat = GetEstadoPedido(num_pedido);
        
        formPedido pedi = new formPedido(txtFecha.getText(), num_pedido, "L", stat, turno_act, 8);
        pedi.setVisible(true);
    }//GEN-LAST:event_btnL8ActionPerformed

    private void btnL9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnL9ActionPerformed
        int num_pedido = consultar_pedido_llevar(1);
        String turno_act = GetTurno();
        String stat = GetEstadoPedido(num_pedido);
        
        formPedido pedi = new formPedido(txtFecha.getText(), num_pedido, "L", stat, turno_act, 9);
        pedi.setVisible(true);
    }//GEN-LAST:event_btnL9ActionPerformed

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
            java.util.logging.Logger.getLogger(formMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnL1;
    private javax.swing.JButton btnL2;
    private javax.swing.JButton btnL3;
    private javax.swing.JButton btnL4;
    private javax.swing.JButton btnL5;
    private javax.swing.JButton btnL6;
    private javax.swing.JButton btnL7;
    private javax.swing.JButton btnL8;
    private javax.swing.JButton btnL9;
    private javax.swing.JButton btnM1;
    private javax.swing.JButton btnM2;
    private javax.swing.JButton btnM3;
    private javax.swing.JButton btnM4;
    private javax.swing.JButton btnM5;
    private javax.swing.JButton btnM6;
    private javax.swing.JButton btnM7;
    private javax.swing.JButton btnM8;
    private javax.swing.JButton btnM9;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JButton jButton11;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTextField txtFecha;
    // End of variables declaration//GEN-END:variables
}
