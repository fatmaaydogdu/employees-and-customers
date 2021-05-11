import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerAddingFrame extends DatabaseConnector{

    private JPanel pnl_CustomerAdding;
    private JTextField txt_CustomerName;
    private JTextField txt_CustomerPhone;
    private JTextField txt_CustomerAddress;
    private JButton kaydetButton;
    public JFrame customerAddingFrame;
    public MainFrame mainFrame;


    public CustomerAddingFrame(){
        customerAddingFrame = new JFrame("Müşteri Ekleme");
        customerAddingFrame.setContentPane(pnl_CustomerAdding);
        customerAddingFrame.pack();
        customerAddingFrame.setVisible(false);

        kaydetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(CheckFields()){
                    CustomerAddingFrame.super.ExecuteMyCodeUpdate(CreateInsertCode());
                    JOptionPane.showMessageDialog(null,"Kayıt başarıyla tamamlandı.","İşlem başarılı",JOptionPane.INFORMATION_MESSAGE);
                    ResetFields();
                    customerAddingFrame.setVisible(false);
                    mainFrame.RefreshTable();
                }else{
                    JOptionPane.showMessageDialog(null,"Lütfen tüm boşlukları doldurunuz!","Hata",JOptionPane.ERROR_MESSAGE);
                    ResetFields();
                }
            }
        });

        customerAddingFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        customerAddingFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                ResetFields();
                e.getWindow().dispose();
            }
        });


    }

    private String CreateInsertCode(){
        String[] infos = {txt_CustomerName.getText(),txt_CustomerAddress.getText(),txt_CustomerPhone.getText()};
        String code = "INSERT INTO db_Company.Tables.tbl_Customer (Customer_Name, Address, Phone) VALUES ('" +infos[0] +"', '"+ infos[1] + "', '" + infos[2] + "')";
        return code;
    }

    private void ResetFields(){
        txt_CustomerAddress.setText("");
        txt_CustomerName.setText("");
        txt_CustomerPhone.setText("");
    }
    private boolean CheckFields(){
        boolean nameField = !txt_CustomerName.getText().equals("");
        boolean addressField = !txt_CustomerAddress.getText().equals("");
        boolean phoneField = !txt_CustomerPhone.getText().equals("");

        if(!nameField || !addressField || !phoneField){
            return false;
        }
        else
            return true;
    }
}
