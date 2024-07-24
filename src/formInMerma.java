
import java.awt.Color;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class formInMerma extends javax.swing.JFrame {

    private Statement sentencia;
    private Connection conexion;
    private String msj;

    private String nomBD = "bussandwicheria";
    private String usuario = "root";
    private String password = "informatica";

    public formInMerma() {
        initComponents();
        this.setLocationRelativeTo(null);
        setIcon();
        conectar();
        CalcularPrimero();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        txtFecha.setText(dtf.format(localDate));
        lblDesde.setVisible(false);
        lblHasta.setVisible(false);
        dcDesde.setVisible(false);
        dcHasta.setVisible(false);
        btnAceptar.setVisible(false);
        btnCancelar.setVisible(false);
        this.getContentPane().setBackground(Color.orange);
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

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagen/icono.png")));
    }
    
    
    private String CalcularPrimero(){
        String primero = "";
        
        Calendar c1 = Calendar.getInstance();
        String mes = Integer.toString(c1.get(Calendar.MONTH));
        String anno = Integer.toString(c1.get(Calendar.YEAR));        
        System.out.println("mes "+ mes + " agno " + anno);
        primero = anno + "/" + mes + "/" + "01";
        System.out.println(primero);
        return primero;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        lblFecha = new javax.swing.JLabel();
        rbtnMes = new javax.swing.JRadioButton();
        rbtnRango = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableMerma = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        txtTotal = new javax.swing.JTextField();
        lblDesde = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        lblHasta = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        dcDesde = new com.toedter.calendar.JDateChooser();
        dcHasta = new com.toedter.calendar.JDateChooser();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Informe de Mermas");

        lblFecha.setText("Fecha:");

        buttonGroup1.add(rbtnMes);
        rbtnMes.setText("Mes Actual");
        rbtnMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnMesActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtnRango);
        rbtnRango.setText("Rango de fechas");
        rbtnRango.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnRangoActionPerformed(evt);
            }
        });

        tableMerma.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo Ajuste", "Codigo Insumo", "Nombre", "Cantidad"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableMerma);
        if (tableMerma.getColumnModel().getColumnCount() > 0) {
            tableMerma.getColumnModel().getColumn(0).setResizable(false);
            tableMerma.getColumnModel().getColumn(1).setResizable(false);
            tableMerma.getColumnModel().getColumn(2).setResizable(false);
            tableMerma.getColumnModel().getColumn(3).setResizable(false);
        }

        jLabel7.setText("Seleccione fecha");

        lblDesde.setText("Desde:");

        lblTotal.setText("Total Neto:");

        lblHasta.setText("Hasta:");

        jLabel1.setText("Informe de Mermas");

        btnRegresar.setText("Atras");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        jLabel5.setText("Listado de Mermas");

        jLabel10.setText("Detalle");

        dcDesde.setDateFormatString("yyyy/MM/dd");

        dcHasta.setDateFormatString("yyyy/MM/dd");

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblFecha)
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnRegresar))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblDesde)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dcDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49)
                                .addComponent(lblHasta)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dcHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(169, 169, 169)
                                .addComponent(btnAceptar)
                                .addGap(47, 47, 47)
                                .addComponent(btnCancelar))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(jLabel5))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(lblTotal)
                                            .addGap(18, 18, 18)
                                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 517, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 15, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(26, 26, 26)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(rbtnMes)
                                .addComponent(rbtnRango)))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(28, Short.MAX_VALUE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addGap(222, 222, 222)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFecha)
                    .addComponent(btnRegresar))
                .addGap(123, 123, 123)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dcDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDesde)
                    .addComponent(lblHasta)
                    .addComponent(dcHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(btnCancelar))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                        .addGap(25, 25, 25))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTotal))))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(41, 41, 41)
                    .addComponent(jLabel1)
                    .addGap(24, 24, 24)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel7)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(rbtnMes)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(rbtnRango)
                    .addContainerGap(333, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rbtnMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnMesActionPerformed
        //new fLogin().setVisible(true);
        //this.setVisible(false);
        //this.dispose();
        lblDesde.setVisible(false);
        lblHasta.setVisible(false);
        dcDesde.setVisible(false);
        dcHasta.setVisible(false);
        btnAceptar.setVisible(false);
        btnCancelar.setVisible(false);
        vaciarListado();
        String prim = CalcularPrimero();
        String today = txtFecha.getText();
        
        llenarListado(prim,today);
    }//GEN-LAST:event_rbtnMesActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        //new fLogin().setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void rbtnRangoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnRangoActionPerformed
        lblDesde.setVisible(true);
        lblHasta.setVisible(true);
        dcDesde.setVisible(true);
        dcHasta.setVisible(true);
        btnAceptar.setVisible(true);
        btnCancelar.setVisible(true);
        vaciarListado();
    }//GEN-LAST:event_rbtnRangoActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        //String x = dcDesde.getString();
        //DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        
        String hoy = txtFecha.getText();
        String fecha1 = "";
        int sw = 0;
 
        try {
            String formato = "yyyy/MM/dd";
            //Formato
            Date date = dcDesde.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat(formato);
            fecha1 = (sdf.format(date));
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(this, "Al menos selecciona una fecha válida!", "Error!", JOptionPane.INFORMATION_MESSAGE);
        }
        
        String fecha2 = "";        
        try {
            String formato = "yyyy/MM/dd";
            //Formato
            Date date = dcHasta.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat(formato);
            fecha2 = (sdf.format(date));
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(this, "Al menos selecciona una fecha válida!", "Error!", JOptionPane.INFORMATION_MESSAGE);
        }
        
        int mayor = (fecha1.compareTo(fecha2)); 
        if (mayor>0){
            JOptionPane.showMessageDialog(null, "'Fecha inicial' supuera a 'Fecha final'", "ADVERTENCIA", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        }
        
        int hoyM = (fecha1.compareTo(hoy));
        if (hoyM>0){
            JOptionPane.showMessageDialog(null, "'Fecha inicial' supuera a Fecha actual", "ADVERTENCIA", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        }
        
        int hoyMF = (fecha2.compareTo(hoy));
        if (hoyMF>0){
            JOptionPane.showMessageDialog(null, "'Fecha final' supuera a Fecha actual", "ADVERTENCIA", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        }
        
        if (sw == 0) {
            System.out.println(fecha1 + " " + fecha2);
            llenarListado(fecha1,fecha2);
        }
        
        System.out.println(fecha1 + " " + fecha2);
        //llenarListado(fecha1,fecha2);
    }//GEN-LAST:event_btnAceptarActionPerformed

    public void llenarListado(String fechaInicial, String fechaFinal) {
        System.out.println("ntrar llenar listado");
        
        try {
            System.out.println("1 SELECT * FROM ajuste_stock WHERE fecha_ajuste BETWEEN '" + fechaInicial + "' AND '" + fechaFinal + "'");
            sentencia = (Statement)conexion.createStatement();
            //System.out.println("2 SELECT * FROM ajuste_stock WHERE fecha_ajuste BETWEEN '" + fechaInicial + "' AND '" + fechaFinal + "'");
            ResultSet lista = sentencia.executeQuery("SELECT * FROM ajuste_stock WHERE fecha_ajuste BETWEEN '" + fechaInicial + "' AND '" + fechaFinal + "'");
            //ResultSet lista = sentencia.executeQuery("SELECT * FROM ajuste_stock WHERE fecha_ajuste BETWEEN '2017/12/01' AND '2017/12/04'");
            int cont = 0;
            while (lista.next()) {
                int auxCodIns = (lista.getInt("cod_insumo")); // System.out.println(auxCod);
                int auxCodA = (lista.getInt("cod_ajuste"));
                int auxCant = (lista.getInt("cantidad_ajustada"));             

                String auxNom = "";
                
                try {
                    sentencia = (Statement) conexion.createStatement();
                    String queryz = "SELECT * from insumos";
                    ResultSet listaz = sentencia.executeQuery(queryz);
                    while (listaz.next() == true) {
                        String nomIns = (listaz.getString("nombre_insumo"));
                        //int cate = Integer.parseInt(cat);
                        int idIns = (listaz.getInt("cod_insumo"));

                        if (auxCodIns == idIns) {
                            auxNom = nomIns;
                        }
                    }
                } catch (Exception e) {

                }
                
                DefaultTableModel modelo = (DefaultTableModel) tableMerma.getModel();
                modelo.addRow(new Object[]{auxCodA, auxCodIns, auxNom, auxCant});
                cont = cont + 1;

            }
        } catch (SQLException e) {
            msj = "no se pudo seleccionar";
        }
        /*
        try {
            //System.out.println("ntrar llenar listado - try");
            //System.out.println("SELECT * FROM ajuste_stock WHERE fecha_ajuste BETWEEN '" + fechaInicial + "' AND '" + fechaFinal + "'");
            sentencia = (Statement) conexion.createStatement();
            
            String sql = "SELECT * FROM ajuste_stock WHERE fecha_ajuste BETWEEN '" + fechaInicial + "' AND '" + fechaFinal + "'";
            System.out.println(sql);
            ResultSet lista = sentencia.executeQuery(sql);
            int cont = 0;
            while (lista.next()) {
                
                int auxCodIns = (lista.getInt("cod_insumo")); // System.out.println(auxCod);
                int auxCodA = (lista.getInt("cod_ajuste"));
                int auxCant = (lista.getInt("cantidad_ajustada"));  //System.out.println(auxCat);
                
                //tipo = (lista.getString("tipo_insumo"));  //System.out.println(auxTipo);
                String auxNom = "";
                
                try {
                    sentencia = (Statement) conexion.createStatement();
                    String queryz = "SELECT * from insumos";
                    ResultSet listaz = sentencia.executeQuery(queryz);
                    while (listaz.next() == true) {
                        String nomIns = (listaz.getString("nombre_insumo"));
                        //int cate = Integer.parseInt(cat);
                        int idIns = (listaz.getInt("cod_insumo"));

                        if (auxCodIns == idIns) {
                            auxNom = nomIns;
                        }
                    }
                } catch (Exception e) {

                }
                DefaultTableModel modelo = (DefaultTableModel) tableMerma.getModel();
                modelo.addRow(new Object[]{auxCodA,auxCodIns, auxNom, auxCant, ""});
                cont = cont + 1;                
            }
        } catch (SQLException e) {
            msj = "no se pudo seleccionar";
        }*/

    }
    
    public void vaciarListado(){
        try {
            DefaultTableModel modelo=(DefaultTableModel) tableMerma.getModel();
            int filas=tableMerma.getRowCount();
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
            msj="Datos guardados";
            JOptionPane.showMessageDialog(null,msj,"INFORMACION",JOptionPane.INFORMATION_MESSAGE);                      
           // lblEstado.setText(msj);
        }
        catch(SQLException e){
            System.out.print(e.getMessage());
            msj="no ingreso";         
        }
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
            java.util.logging.Logger.getLogger(formInMerma.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formInMerma.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formInMerma.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formInMerma.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formInMerma().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.toedter.calendar.JDateChooser dcDesde;
    private com.toedter.calendar.JDateChooser dcHasta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lblDesde;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblHasta;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JRadioButton rbtnMes;
    private javax.swing.JRadioButton rbtnRango;
    private javax.swing.JTable tableMerma;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
