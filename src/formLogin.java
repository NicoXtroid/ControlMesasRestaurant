
import java.awt.Toolkit;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;



public class formLogin extends javax.swing.JFrame {

    private Statement sentencia;
    private Connection conexion; 
    private String msj;
   
    private String nomBD="bussandwicheria";
    private String usuario="root";
    private String password="informatica";
    
    /*
    private String nomBD="nicolas.huerta";
    private String usuario="nicolas.huerta";
    private String password="inf123pass"; 
      */
    /*
    private String nomBD="bussandwicheria";
    private String usuario="bussandwicheria";
    private String password="bussandwicheria"; */
    /*ADVERTENCIA: se puede caer si es que no pusimos localhost al crear la base*/
    
    
    public formLogin() {
        initComponents();
        setIcon();
        conectar();
        
        this.setLocationRelativeTo(null);
        
        mnVentas.setEnabled(false);
        mnProductos.setEnabled(false);
        mnPersonas.setEnabled(false);
        mnInformes.setEnabled(false);
        mnSesion.setEnabled(false);
    }

    
    
    public void conectar(){
        try{
         msj="ENTRAMOS AL CONECTAR EN EL LOGIN";
        //JOptionPane.showMessageDialog(null,msj,"ENTRAMOS AL CONECTAR EN EL LOGIN",JOptionPane.INFORMATION_MESSAGE);
        Class.forName("com.mysql.jdbc.Driver"); 
        String url="jdbc:mysql://localhost:3306/"+this.nomBD;
        //String url="jdbc:mysql://tupuedes.ml/"+this.nomBD;  //esto nope
        //String url="jdbc:mysql://192.168.238:3306/"+this.nomBD;
        
         msj="antes de aplicar sent de conex";
        //JOptionPane.showMessageDialog(null,msj,"ENTRAMOS AL CONECTAR EN EL LOGIN",JOptionPane.INFORMATION_MESSAGE);
        this.conexion=(Connection)DriverManager.getConnection(url,this.usuario,this.password);
        this.sentencia=(Statement)this.conexion.createStatement();
       
           msj="despues de aplicar sent de conex";
        //JOptionPane.showMessageDialog(null,msj,"ENTRAMOS AL CONECTAR EN EL LOGIN",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e){
                msj="error al conectar";
        JOptionPane.showMessageDialog(null,msj,"ADVERTENCIA!",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    

public static String Encriptar(String texto) {
 
        String secretKey = "qualityinfosolutions"; //llave para encriptar datos
        String base64EncryptedString = "";
 
        try {
 
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
 
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);
 
            byte[] plainTextBytes = texto.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
//            byte[] base64Bytes = Base64.encodeBase64(buf);
//            base64EncryptedString = new String(base64Bytes);
 
        } catch (Exception ex) {
        }
        return base64EncryptedString;
}
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        pnlFonfo = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtPass = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        fondo = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnVentas = new javax.swing.JMenu();
        mitemMenu = new javax.swing.JMenuItem();
        mitemTurno = new javax.swing.JMenuItem();
        mitemPCredito = new javax.swing.JMenuItem();
        mitemMesas = new javax.swing.JMenuItem();
        mnProductos = new javax.swing.JMenu();
        mitemCompra = new javax.swing.JMenuItem();
        mitemInsumos = new javax.swing.JMenuItem();
        mitemProductos = new javax.swing.JMenuItem();
        mitemCategorias = new javax.swing.JMenuItem();
        mitemStock = new javax.swing.JMenuItem();
        mnPersonas = new javax.swing.JMenu();
        mitemProveedores = new javax.swing.JMenuItem();
        mitemClientes = new javax.swing.JMenuItem();
        mitemUsuarios = new javax.swing.JMenuItem();
        mnInformes = new javax.swing.JMenu();
        mitemIGanancia = new javax.swing.JMenuItem();
        mitemIStock = new javax.swing.JMenuItem();
        mnSesion = new javax.swing.JMenu();
        mitemSesion = new javax.swing.JMenuItem();
        mitemAcerca = new javax.swing.JMenuItem();
        mitemSalir = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("BUSandwicheria \"El Privilegio del Sabor\"");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(415, 330));
        setResizable(false);
        setSize(new java.awt.Dimension(400, 300));
        getContentPane().setLayout(null);

        btnAceptar.setText("Aceptar");
        btnAceptar.setMaximumSize(new java.awt.Dimension(80, 30));
        btnAceptar.setPreferredSize(new java.awt.Dimension(80, 30));
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });
        btnAceptar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                btnAceptarKeyTyped(evt);
            }
        });
        getContentPane().add(btnAceptar);
        btnAceptar.setBounds(99, 165, 80, 30);

        btnCancelar.setText("Cancelar");
        btnCancelar.setMaximumSize(new java.awt.Dimension(80, 30));
        btnCancelar.setMinimumSize(new java.awt.Dimension(75, 30));
        btnCancelar.setPreferredSize(new java.awt.Dimension(80, 30));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancelar);
        btnCancelar.setBounds(219, 165, 80, 30);

        jLabel3.setText("Password:");

        jLabel1.setText("Usuario:");

        javax.swing.GroupLayout pnlFonfoLayout = new javax.swing.GroupLayout(pnlFonfo);
        pnlFonfo.setLayout(pnlFonfoLayout);
        pnlFonfoLayout.setHorizontalGroup(
            pnlFonfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFonfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFonfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlFonfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        pnlFonfoLayout.setVerticalGroup(
            pnlFonfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFonfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFonfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(pnlFonfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );

        getContentPane().add(pnlFonfo);
        pnlFonfo.setBounds(90, 60, 220, 90);

        fondo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/fondov1.png"))); // NOI18N
        getContentPane().add(fondo);
        fondo.setBounds(0, 0, 410, 280);

        jMenuBar1.setName(""); // NOI18N

        mnVentas.setText("Ventas");

        mitemMenu.setText("Menú");
        mitemMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitemMenuActionPerformed(evt);
            }
        });
        mnVentas.add(mitemMenu);

        mitemTurno.setText("Arqueo turno");
        mitemTurno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitemTurnoActionPerformed(evt);
            }
        });
        mnVentas.add(mitemTurno);

        mitemPCredito.setText("Pagar Deuda");
        mitemPCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitemPCreditoActionPerformed(evt);
            }
        });
        mnVentas.add(mitemPCredito);

        mitemMesas.setText("Mesas");
        mitemMesas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitemMesasActionPerformed(evt);
            }
        });
        mnVentas.add(mitemMesas);

        jMenuBar1.add(mnVentas);

        mnProductos.setText("Productos");

        mitemCompra.setText("Compra Insumos");
        mitemCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitemCompraActionPerformed(evt);
            }
        });
        mnProductos.add(mitemCompra);

        mitemInsumos.setText("Insumos");
        mitemInsumos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitemInsumosActionPerformed(evt);
            }
        });
        mnProductos.add(mitemInsumos);

        mitemProductos.setText("Productos");
        mitemProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitemProductosActionPerformed(evt);
            }
        });
        mnProductos.add(mitemProductos);

        mitemCategorias.setText("Categorias");
        mitemCategorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitemCategoriasActionPerformed(evt);
            }
        });
        mnProductos.add(mitemCategorias);

        mitemStock.setText("Control Stock");
        mitemStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitemStockActionPerformed(evt);
            }
        });
        mnProductos.add(mitemStock);

        jMenuBar1.add(mnProductos);

        mnPersonas.setText("Personas");

        mitemProveedores.setText("Proveedores");
        mitemProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitemProveedoresActionPerformed(evt);
            }
        });
        mnPersonas.add(mitemProveedores);

        mitemClientes.setText("Clientes frecuentes");
        mitemClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitemClientesActionPerformed(evt);
            }
        });
        mnPersonas.add(mitemClientes);

        mitemUsuarios.setText("Usuarios");
        mitemUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitemUsuariosActionPerformed(evt);
            }
        });
        mnPersonas.add(mitemUsuarios);

        jMenuBar1.add(mnPersonas);

        mnInformes.setText("Informes");

        mitemIGanancia.setText("Informe de Ganancias");
        mitemIGanancia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitemIGananciaActionPerformed(evt);
            }
        });
        mnInformes.add(mitemIGanancia);

        mitemIStock.setText("Informe de Merma");
        mitemIStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitemIStockActionPerformed(evt);
            }
        });
        mnInformes.add(mitemIStock);

        jMenuBar1.add(mnInformes);

        mnSesion.setText("Sesion");

        mitemSesion.setText("Cerrar Sesion");
        mitemSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitemSesionActionPerformed(evt);
            }
        });
        mnSesion.add(mitemSesion);

        mitemAcerca.setText("Acerca de");
        mitemAcerca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitemAcercaActionPerformed(evt);
            }
        });
        mnSesion.add(mitemAcerca);

        mitemSalir.setText("Salir");
        mitemSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitemSalirActionPerformed(evt);
            }
        });
        mnSesion.add(mitemSalir);

        jMenuBar1.add(mnSesion);

        setJMenuBar(jMenuBar1);

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mitemCategoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemCategoriasActionPerformed
        formCategoria cat = new formCategoria();
        //this.dispose();
        //this.setVisible(false);
        cat.setVisible(true);
    }//GEN-LAST:event_mitemCategoriasActionPerformed

    private void mitemPCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemPCreditoActionPerformed
        formPagoDeuda pdeuda = new formPagoDeuda();
        //this.dispose();
        //this.setVisible(false);
        pdeuda.setVisible(true);
    }//GEN-LAST:event_mitemPCreditoActionPerformed

    private void mitemMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemMenuActionPerformed
        formMenu men = new formMenu();
        //this.dispose();
        //this.setVisible(false);
        men.setVisible(true);
    }//GEN-LAST:event_mitemMenuActionPerformed

    private void mitemProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemProveedoresActionPerformed
        formProveedores prov = new formProveedores(txtId.getText(),txtPass.getText());
        //this.dispose();
        //this.setVisible(false);
        prov.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_mitemProveedoresActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        byte i=0;
        String idUser = txtId.getText();   
        String idPass = txtPass.getText();
        String query="";
        ResultSet existeUS;
        conectar();
        //ResultSet existe2;        
        try{
            sentencia=(Statement)conexion.createStatement();
            query="SELECT * FROM usuarios WHERE idUsuario= '" + idUser + "' and password='" + idPass + "' ";
            existeUS=sentencia.executeQuery(query);            
            if(existeUS.next() == true){
                i=1;
                System.out.println("Acceso Concedido");
            } 
            else{
                    i=0;
                 }           
        }catch(SQLException aux){
            //System.out.println("HOLA");
         }     
     if (i==1){
            try {
                JOptionPane.showMessageDialog(null,"Usuario correcto");
                mnVentas.setEnabled(true);
                mnSesion.setEnabled(true);
                
                ResultSet resuNiv;
                sentencia=(Statement)conexion.createStatement();
                query="SELECT nivel FROM usuarios WHERE idUsuario= '" + idUser + "' and password='" + idPass + "' ";
                resuNiv=sentencia.executeQuery(query);
                
                int nive=0;
                if(resuNiv.next()){
                    nive = resuNiv.getInt("nivel");
                }
                
                if (nive == 1){
                    mnProductos.setEnabled(true);
                    mnPersonas.setEnabled(true);
                    mnInformes.setEnabled(true);                        
                
                }
                
                
                pnlFonfo.setVisible(false);
                btnAceptar.setVisible(false);
                btnCancelar.setVisible(false);
                
                
                //fProductos prod = new fProductos(txtId.getText(),txtPass.getText());
                //this.dispose();
                
                //prod.setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(formLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(null,"Usuario incorrecto");
        }

       
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void mitemTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemTurnoActionPerformed
        formATurno arqueo = new formATurno();
        //this.dispose();
        //this.setVisible(false);
        arqueo.setVisible(true);
    }//GEN-LAST:event_mitemTurnoActionPerformed

    private void mitemMesasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemMesasActionPerformed
        formMesa mesa = new formMesa();
        //this.dispose();
        //this.setVisible(false);
        mesa.setVisible(true);
    }//GEN-LAST:event_mitemMesasActionPerformed

    private void mitemCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemCompraActionPerformed
        formRCompra compra = new formRCompra();
        //this.dispose();
        //this.setVisible(false);
        compra.setVisible(true);
    }//GEN-LAST:event_mitemCompraActionPerformed

    private void mitemInsumosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemInsumosActionPerformed
        formInsumos insumo = new formInsumos(txtId.getText(),txtPass.getText());
        //this.dispose();
        //this.setVisible(false);
        insumo.setVisible(true);
    }//GEN-LAST:event_mitemInsumosActionPerformed

    private void mitemProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemProductosActionPerformed
        formProductos prod = new formProductos(txtId.getText());
        //this.dispose();
        //this.setVisible(false);
        prod.setVisible(true);
    }//GEN-LAST:event_mitemProductosActionPerformed

    private void mitemStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemStockActionPerformed
        formPerdida perdida = new formPerdida();
        //this.dispose();
        //this.setVisible(false);
        perdida.setVisible(true);
    }//GEN-LAST:event_mitemStockActionPerformed

    private void mitemClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemClientesActionPerformed
        formClientes cliente = new formClientes();
        //this.dispose();
        //this.setVisible(false);
        cliente.setVisible(true);
    }//GEN-LAST:event_mitemClientesActionPerformed

    private void mitemUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemUsuariosActionPerformed
        formUsuarios usua = new formUsuarios();
        //this.dispose();
        //this.setVisible(false);
        usua.setVisible(true);
    }//GEN-LAST:event_mitemUsuariosActionPerformed

    private void mitemIStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemIStockActionPerformed
        formInMerma rMerma = new formInMerma();
        //this.dispose();
        //this.setVisible(false);
        rMerma.setVisible(true);
    }//GEN-LAST:event_mitemIStockActionPerformed

    private void mitemIGananciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemIGananciaActionPerformed
        formInRangoVenta rVenta = new formInRangoVenta();
        //this.dispose();
        //this.setVisible(false);
        rVenta.setVisible(true);
    }//GEN-LAST:event_mitemIGananciaActionPerformed

    private void mitemAcercaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemAcercaActionPerformed
        formAcerca acerca = new formAcerca();
        //this.dispose();
        //this.setVisible(false);
        acerca.setVisible(true);
    }//GEN-LAST:event_mitemAcercaActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        txtId.setText("");
        txtPass.setText("");
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void mitemSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemSesionActionPerformed
        mnVentas.setEnabled(false);
        mnProductos.setEnabled(false);
        mnPersonas.setEnabled(false);
        mnInformes.setEnabled(false);
        mnSesion.setEnabled(false);
        txtId.setText("");
        txtPass.setText("");
        
        pnlFonfo.setVisible(true);
        btnAceptar.setVisible(true);
        btnCancelar.setVisible(true);
    }//GEN-LAST:event_mitemSesionActionPerformed

    private void mitemSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_mitemSalirActionPerformed

    private void btnAceptarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAceptarKeyTyped
       
    }//GEN-LAST:event_btnAceptarKeyTyped

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
            java.util.logging.Logger.getLogger(formLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel fondo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JMenuItem mitemAcerca;
    private javax.swing.JMenuItem mitemCategorias;
    private javax.swing.JMenuItem mitemClientes;
    private javax.swing.JMenuItem mitemCompra;
    private javax.swing.JMenuItem mitemIGanancia;
    private javax.swing.JMenuItem mitemIStock;
    private javax.swing.JMenuItem mitemInsumos;
    private javax.swing.JMenuItem mitemMenu;
    private javax.swing.JMenuItem mitemMesas;
    private javax.swing.JMenuItem mitemPCredito;
    private javax.swing.JMenuItem mitemProductos;
    private javax.swing.JMenuItem mitemProveedores;
    private javax.swing.JMenuItem mitemSalir;
    private javax.swing.JMenuItem mitemSesion;
    private javax.swing.JMenuItem mitemStock;
    private javax.swing.JMenuItem mitemTurno;
    private javax.swing.JMenuItem mitemUsuarios;
    private javax.swing.JMenu mnInformes;
    private javax.swing.JMenu mnPersonas;
    private javax.swing.JMenu mnProductos;
    private javax.swing.JMenu mnSesion;
    private javax.swing.JMenu mnVentas;
    private javax.swing.JPanel pnlFonfo;
    private javax.swing.JTextField txtId;
    private javax.swing.JPasswordField txtPass;
    // End of variables declaration//GEN-END:variables

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagen/icono.png")));
    }
}
