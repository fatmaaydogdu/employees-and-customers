import cambodia.raven.DateChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


class Projects{
    int projectId;
    String projectName;

    public Projects(int id, String name){
        projectId = id;
        projectName = name;
    }
}

class Customers{
    int customerId;
    String customerName;

    public Customers(int id, String name){
        customerId = id;
        customerName = name;
    }
}

public class ProjectSellingFrame extends DatabaseConnector{

    private JComboBox cmb_Companies;
    private JComboBox cmb_Projects;
    private JTextField txt_SellLength;
    private JTextField txt_Price;
    private JRadioButton radio_Gun;
    private JRadioButton radio_Ay;
    private JRadioButton radio_Yil;
    private JPanel pnl_SellProject;
    private JButton btn_Sat;
    private DateChooser dateChooser;
    public MainFrame mainFrame;
    public JFrame projectSellingFrame;
    private ButtonGroup radioGrup;
    private ArrayList<Customers> currentCustomers;
    private ArrayList<Projects> currentProjects;


    public ProjectSellingFrame(){
        radioGrup = new ButtonGroup();

        radioGrup.add(radio_Gun);
        radio_Gun.setActionCommand("Gün");

        radioGrup.add(radio_Ay);
        radio_Ay.setActionCommand("Ay");

        radioGrup.add(radio_Yil);
        radio_Yil.setActionCommand("Yıl");

        projectSellingFrame = new JFrame("Müşteri Ekleme");
        projectSellingFrame.setContentPane(pnl_SellProject);
        projectSellingFrame.pack();
        projectSellingFrame.setVisible(false);


        btn_Sat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(CheckFields()){
                    SellProject();
                }else{
                    JOptionPane.showMessageDialog(null,"Lütfen tüm alanları doldurunuz.");
                    ResetFields();
                }
            }
        });
    }


    private void SellProject(){
        Customers selectedCustomer = currentCustomers.get(cmb_Companies.getSelectedIndex());
        Projects selectedProject = currentProjects.get(cmb_Projects.getSelectedIndex());
        String period = radioGrup.getSelection().getActionCommand();
        String sellLength = txt_SellLength.getText();
        String price = txt_Price.getText();
        String date = dateChooser.getSelectedDate();

        String sqlCode = "INSERT INTO db_Company.Tables.tbl_Project_Sales (Customer_Id,Project_Id,Sell_Date,Technical_Support_Time,Time_Period,Price) VALUES ('" +selectedCustomer.customerId + "','"+ selectedProject.projectId +"','"+ date +"','"+ sellLength + "','"+ period + "','" + price +"')";
        ProjectSellingFrame.super.ExecuteMyCodeUpdate(sqlCode);


    }

    private void ResetFields(){
        txt_SellLength.setText("");
        txt_Price.setText("");
    }

    private boolean CheckFields(){
        boolean priceField = txt_Price.getText().equals("");
        boolean sellLengthField = txt_SellLength.getText().equals("");
        boolean radioField = radioGrup.getSelection().getActionCommand().equals("");

        if(priceField || sellLengthField || radioField){
            return false;
        }else
            return true;

    }

    public void FillTheComboboxes(){
        try {
            FillTheComboboxesDirty();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void  FillTheComboboxesDirty() throws SQLException {
        String customerSql = "SELECT Customer_Id, Customer_Name FROM db_Company.Tables.tbl_Customer";
        String projectsSql = "SELECT Project_Id, Project_Name FROM db_Company.Tables.tbl_Project";

        ResultSet rsCustomers = ProjectSellingFrame.super.ExecuteMyCodeQuery(customerSql);
        ResultSet rsProjects = ProjectSellingFrame.super.ExecuteMyCodeQuery(projectsSql);

        currentCustomers = new ArrayList<Customers>();
        currentProjects = new ArrayList<Projects>();

        while (rsCustomers.next()){
            Customers customers = new Customers(rsCustomers.getInt("Customer_Id"),rsCustomers.getString("Customer_Name"));
            cmb_Companies.addItem(customers.customerName);
            currentCustomers.add(customers);
        }

        while (rsProjects.next()){
            Projects projects = new Projects(rsProjects.getInt("Project_Id"),rsProjects.getString("Project_Name"));
            cmb_Projects.addItem(projects.projectName);
            currentProjects.add(projects);
        }
        ProjectSellingFrame.super.Disconnect(rsProjects.getStatement().getConnection(),rsProjects.getStatement(),rsProjects);
        ProjectSellingFrame.super.Disconnect(rsCustomers.getStatement().getConnection(),rsCustomers.getStatement(),rsCustomers);
    }
}
