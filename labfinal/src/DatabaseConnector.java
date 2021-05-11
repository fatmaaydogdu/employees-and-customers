import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class DatabaseConnector {

    private String url = "jdbc:jtds:sqlserver://DESKTOP-K3ILO81;instance=MSSQLSERVER2";
    private Connection _connect = null;
    private Statement _statement = null;
    private ResultSet _resultSet = null;

    public Connection Connect() {

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            _connect = DriverManager.getConnection(url);
            System.out.println("Bağlantı Başarılı");
            return _connect;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Bağlantı Başarısız");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return _connect;
    }

    public Statement ExecuteMyCodeUpdate(String code){

        _connect = Connect();

        try {
            _statement = _connect.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            _statement.executeUpdate(code);
            Disconnect(_connect,_statement);
            return _statement;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Disconnect(_connect,_statement);
        return _statement;
    }


    public ResultSet ExecuteMyCodeQuery(String code){
        _connect = Connect();

        try {
            _statement = _connect.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            _resultSet = _statement.executeQuery(code);
            return _resultSet;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return _resultSet;
    }



    public void Disconnect(Connection cn){
        try {
            cn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        System.out.println("Bağlantı Başarıyla Kapatıldı");
    }
    public void Disconnect(Connection cn, Statement stm){
        try {
            cn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            stm.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        System.out.println("Bağlantı ve Statement Başarıyla Kapatıldı");
    }
    public void Disconnect(Connection cn, Statement stm, ResultSet rs){
        try {
            cn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            stm.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Bağlantı, Statement ve ResultSet Başarıyla Kapatıldı");
    }


}
