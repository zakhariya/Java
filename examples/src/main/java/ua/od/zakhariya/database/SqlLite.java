package ua.od.zakhariya.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class SqlLite {

    private Connection getConnection() throws SQLException {
        String url = "jdbc:sqlite:C:/Users/Саня Z/Desktop/logsqldb";

        return DriverManager.getConnection(url);
    }

    public void selectAll(String table){
        String sql = "SELECT * FROM " + table;
        String row = "";

        try (Connection conn = getConnection();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {

            ArrayList<String> columns = getColumnsList(table);

            String cols = "";

            for(String column : columns) {
                cols += " " + column;
            }

            System.out.println(cols);

            //System.out.println(table + " | " + getColumnCount(table) + " | " + getRowsCountAll(table));

            while (rs.next()) {
                row = "";

                for(String column : columns)
                    row += " "  + rs.getString(column);

                System.out.println(row);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public int getTablesCount(){
        int count = 0;
        try {
            Connection conn = getConnection();
            ResultSet rs = null;
            try {
                DatabaseMetaData dbMD = (DatabaseMetaData) conn.getMetaData();
                rs = dbMD.getTables(null, null, null, null);

                while(rs.next())
                    count++;
            } finally {
                rs.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return count;
    }

    public ArrayList<String> getTablesList(){
        ArrayList<String> tablesList = new ArrayList<String>();
        try {
            Connection conn = getConnection();
            ResultSet rs = null;
            try {
                DatabaseMetaData dbMD = (DatabaseMetaData) conn.getMetaData();
                rs = dbMD.getTables(null, null, null, null);

                while(rs.next()){
                    tablesList.add(rs.getString("TABLE_NAME"));
                    //tablesList.add(rs.getString(3));
                    //rs.getString("TABLE_CAT");
                    //rs.getString("TABLE_SCHEM");
                }
            } finally {
                if(rs != null)
                    rs.close();
                if(conn != null)
                    conn.close();
            }
        } catch (NullPointerException | SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return tablesList;
    }

    public int getColumnCount(String table){
        int count = 0;
        String query = "SELECT * FROM "+table+" LIMIT 1";

        try {
            Connection conn = getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ResultSetMetaData rsmd;
            try {
                ps = conn.prepareStatement(query);
                rs = ps.executeQuery();
                rsmd = rs.getMetaData();
                count = rsmd.getColumnCount();
            } finally {
                rs.close();
                ps.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return count;
    }


    public ArrayList<String> getColumnsList(String tableName){
        int cols = getColumnCount(tableName);
        ArrayList<String> columnsList = new ArrayList<String>();

        for(int i=0; i<cols; i++)
            columnsList.add(getColumnName(tableName, i+1));

        return columnsList;
    }

    public String getColumnName(String table, int columnInd) {
        String query = "SELECT * FROM " + table + " LIMIT 1;";
        String colName = "";

        try {
            Connection conn = getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                ps = conn.prepareStatement(query);
                rs = ps.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();
                colName = rsmd.getColumnName(columnInd);
            } finally {
                ps.close();
                rs.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return colName;
    }

    public int getRowsCountAll(String table){
        int count = 0;

        String query = "SELECT * FROM " + table;

        try {
            Connection conn = getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                ps = conn.prepareStatement(query);
                rs = ps.executeQuery();

                while(rs.next())
                    count++;
            } finally {
                ps.close();
                rs.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return count;
    }


    public static void main(String[] args) {
        SqlLite app = new SqlLite();
        ArrayList<String> tables = app.getTablesList();

        for (String table : tables) {
            app.selectAll(table);
        }
    }

}