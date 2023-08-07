package ittimfn.sample.sqlite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ittimfn.sample.sqlite.model.Model;

public class SqliteRepository {
    
    private Connection conn;
    private Statement state;

    public SqliteRepository(String filepath) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        
        this.conn = DriverManager.getConnection("jdbc:sqlite:"+filepath);
        this.state = this.conn.createStatement();
    }

    public Model find(String id) throws SQLException {
        String sql = "select id, value from DataTable where id = ?;";
        try(PreparedStatement ps = this.conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            Model returnModel = null;
            while(rs.next()) {
                // キー検索なので最大1件。
                returnModel = new Model(rs.getString("id"), rs.getString("value"));
            }
            return returnModel;
        }
    }

    public List<Model> findAll() throws SQLException {
        String sql = "select id, value from DataTable;";
        ResultSet rs = this.state.executeQuery(sql);
        List<Model> returnList = new ArrayList<>();
        while(rs.next()) {
            returnList.add(new Model(rs.getString("id"), rs.getString("value")));
        }
        return returnList;
    }

    public String add(String value) throws SQLException {
        String sql = "insert into DataTable(id, value) values (?, ?)";

        try(PreparedStatement ps = this.conn.prepareStatement(sql)) {
            String id = UUID.randomUUID().toString();
            ps.setString(1, id);
            ps.setString(2, value);

            ps.executeUpdate();

            return id;
        }
    }

    public void remove(String id) throws SQLException {
        String sql = "delete from DataTable where id = ?;";
        try(PreparedStatement ps = this.conn.prepareStatement(sql)) {
            ps.setString(1, id);

            ps.executeUpdate();
        }
    }

    public void update(String id, String value) throws SQLException {
        String sql = "update DataTable set value = ? where id = ?;";
        try(PreparedStatement ps = this.conn.prepareStatement(sql)) {
            ps.setString(1, value);
            ps.setString(2, id);

            ps.executeUpdate();
        }
    }

    public void close() throws SQLException {
        this.state.close();
        this.conn.close();
    }
}
