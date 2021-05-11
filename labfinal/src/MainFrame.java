import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MainFrame extends DatabaseConnector{
    private JButton btn_AddCustomer;
    private JButton btn_SellProject;
    private JButton btn_UpdateCustomer;
    private JButton btn_RemoveCustomer;
    public JTable tbl_MainTable;
    private JPanel pnl_MainPanel;
    private CustomerAddingFrame musteriEkran;
    private CustomerUpdatingFrame musteriGuncellemeEkran;
    private ProjectSellingFrame projeSatEkran;


    public MainFrame() {
        musteriEkran = new CustomerAddingFrame();
        musteriEkran.mainFrame = this;

        musteriGuncellemeEkran = new CustomerUpdatingFrame();
        musteriGuncellemeEkran.mainFrame = this;

        projeSatEkran = new ProjectSellingFrame();
        projeSatEkran.mainFrame = this;


        RefreshTable();

        //Müşteri ekleme
        btn_AddCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame musteriEklemeFrame = musteriEkran.customerAddingFrame;
                musteriEklemeFrame.setVisible(true);
            }
        });
        //Müşteri güncelleme
        btn_UpdateCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tbl_MainTable.getSelectedRow() != -1){
                    JFrame musteriGuncellemeFrame = musteriGuncellemeEkran.customerUpdatingFrame;
                    musteriGuncellemeFrame.setVisible(true);
                    FillTheFieldsOfUpdatingFrame();
                }else{
                    JOptionPane.showMessageDialog(null,"Lütfen bir müşteri seçiniz.","Hata",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        //Müşteri silme
        btn_RemoveCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(tbl_MainTable.getSelectedRow() != -1){
                    int input = JOptionPane.showConfirmDialog(null,"Müşteri kaydını silmek istediğinizden emin misiniz?","Müşteri silme",JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE);
                    if(input == 0){
                        RemoveSelectedRow();
                        JOptionPane.showMessageDialog(null,"İşlem başarılı.","Müşteri Silme",JOptionPane.INFORMATION_MESSAGE);
                        RefreshTable();
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Lütfen bir müşteri seçiniz.","Hata",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        //Proje satma
        btn_SellProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame projeSatFrame = projeSatEkran.projectSellingFrame;
                projeSatFrame.setVisible(true);
                projeSatEkran.FillTheComboboxes();
            }
        });
    }

    //Müşteri silme metodu
    public void RemoveSelectedRow(){
        int currentSelectedRow = tbl_MainTable.getSelectedRow();
        int sqlId = (int) tbl_MainTable.getValueAt(currentSelectedRow,0);

        String sqlCode = "DELETE FROM db_Company.Tables.tbl_Customer WHERE Customer_Id = '" + sqlId + "'" ;
        MainFrame.super.ExecuteMyCodeUpdate(sqlCode);
    }

    //Müşteri güncellerken, güncelleme ekranındaki fieldları doldurur
    public void FillTheFieldsOfUpdatingFrame(){
        int currentSelectedRow = tbl_MainTable.getSelectedRow();
        int sqlId = (int) tbl_MainTable.getValueAt(currentSelectedRow,0);
        String name = (String) tbl_MainTable.getValueAt(currentSelectedRow,1);
        String address = (String) tbl_MainTable.getValueAt(currentSelectedRow,2);
        String phone = (String) tbl_MainTable.getValueAt(currentSelectedRow,3);

        musteriGuncellemeEkran.txt_Name.setText(name);
        musteriGuncellemeEkran.txt_Address.setText(address);
        musteriGuncellemeEkran.txt_Phone.setText(phone);
        musteriGuncellemeEkran.currentSqlId = sqlId;
    }

    //try catchli tablo doldurma
    public void RefreshTable(){
        try {
            FillTheTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //tabloya db customer tablosunun verilerini çekme
    public void FillTheTable() throws SQLException {
        ResultSet rs = super.ExecuteMyCodeQuery("SELECT * FROM db_Company.Tables.tbl_Customer");

        DefaultTableModel tableModel = new DefaultTableModel();
        tbl_MainTable.setModel(tableModel);

        tableModel.addColumn("ID");
        tableModel.addColumn("Müşteri Adı");
        tableModel.addColumn("Müşteri Adres");
        tableModel.addColumn("Müşteri Telefon");

        while(rs.next()){
            int id = rs.getInt("Customer_Id");
            String customerName = rs.getString("Customer_Name");
            String customerAddress = rs.getString("Address");
            String customerPhone = rs.getString("Phone");

            tableModel.addRow(new Object[] {id,customerName,customerAddress,customerPhone});

        }

        super.Disconnect(rs.getStatement().getConnection(),rs.getStatement(),rs);


    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ana Ekran");
        frame.setSize(1024,768);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(new MainFrame().pnl_MainPanel);
        frame.setVisible(true);


    }
}
