import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerUpdatingFrame extends DatabaseConnector{
    public JTextField txt_Name;
    private JPanel pnl_Update;
    public JTextField txt_Phone;
    public JTextField txt_Address;
    private JButton btn_Update;
    public JFrame customerUpdatingFrame;
    public MainFrame mainFrame;
    public int currentSqlId;

    public CustomerUpdatingFrame() {
        customerUpdatingFrame = new JFrame("Müşteri Güncelleme");
        customerUpdatingFrame.setContentPane(pnl_Update);
        customerUpdatingFrame.pack();
        customerUpdatingFrame.setVisible(false);


        btn_Update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(CheckFields()){
                    CustomerUpdatingFrame.super.ExecuteMyCodeUpdate(CreateUpdateCode());
                    JOptionPane.showMessageDialog(null,"Güncelleme başarıyla tamamlandı.","İşlem başarılı",JOptionPane.INFORMATION_MESSAGE);
                    ResetFields();
                    customerUpdatingFrame.setVisible(false);
                    mainFrame.RefreshTable();
                }else{
                    JOptionPane.showMessageDialog(null,"Lütfen tüm boşlukları doldurunuz!","Hata",JOptionPane.ERROR_MESSAGE);
                    ResetFields();
                }
            }
        });

        customerUpdatingFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        customerUpdatingFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                ResetFields();
                e.getWindow().dispose();
            }
        });
    }

    private String CreateUpdateCode(){
        String[] infos = {txt_Name.getText(),txt_Address.getText(),txt_Phone.getText()};
        String code = "UPDATE db_Company.Tables.tbl_Customer SET Customer_Name = '" + infos[0] + "', Address = '" + infos[1] + "', Phone = '" + infos[2] + "' WHERE Customer_Id = '"+ currentSqlId + "'";
        return code;
    }

    private boolean CheckFields(){
        boolean nameField = !txt_Name.getText().equals("");
        boolean addressField = !txt_Address.getText().equals("");
        boolean phoneField = !txt_Phone.getText().equals("");

        if(!nameField || !addressField || !phoneField){
            return false;
        }
        else
            return true;
    }

    private void ResetFields(){
        txt_Address.setText("");
        txt_Name.setText("");
        txt_Phone.setText("");
    }
}
