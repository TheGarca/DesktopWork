package com.theparrot.Model.DAO;

import com.theparrot.Configurations.ConfigurationsMySQL;
import com.theparrot.Model.Interfaces.ImplementUser;
import com.theparrot.DataBase.DataBaseGeneric;
import com.theparrot.Model.Course;
import com.theparrot.Model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAO extends DataBaseGeneric implements ImplementUser {

    public ArrayList<User> list;
    public static User user = new User();

    public UserDAO() {
        super(new ConfigurationsMySQL(), "usuario");
    }

    /**
     *
     * @param user
     */
    @Override
    public void insert(User user) {
        System.out.print("usuário - USER DAO: " + user.toString());
        Map<Object, Object> mapObj = new HashMap<>();
        mapObj.put("name", user.getName());
        mapObj.put("company", user.getCompany());
        mapObj.put("email", user.getEmail());
        mapObj.put("foneNumber", user.getFoneNumber());
        mapObj.put("password", user.getPassword());
        this.genericInsert(mapObj);
    }

    /**
     *
     * @param user
     */
    @Override
    public void update(User user) {
        Map<Object, Object> mapObj = new HashMap<>();
        Map<Object, Object> mapConditions = new HashMap<>();
        mapObj.put("name", user.getName());
        mapObj.put("company", user.getCompany());
        mapObj.put("email", user.getEmail());
        mapObj.put("foneNumber", user.getFoneNumber());
        mapObj.put("password", user.getPassword());
        mapConditions.put("id", user.getId());
        this.genericUpdate(mapObj, mapConditions);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public User getOneUser(int id) {
        ResultSet rs = this.getOne(id);
        User user = new User();
        try {
            user.setId(rs.getInt(1));
            user.setName(rs.getString("name"));
            user.setCompany(rs.getString("company"));
            user.setEmail(rs.getString("email"));
            user.setFoneNumber(rs.getString("foneNumber"));
            user.setPassword(rs.getString("password"));

            return user;
        } catch (SQLException ex) {
            System.out.println("Erro ao retornar um curso pelo id: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public User getUserByName(String name) {
        this.list = new ArrayList<>();
        try {
            ResultSet rs = this.getLike("name", name);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setName(rs.getString("name"));
                user.setCompany(rs.getString("company"));
                user.setEmail(rs.getString("email"));
                user.setFoneNumber(rs.getString("foneNumber"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        } catch (SQLException ex) {
            System.out.println("Houve um erro ao obter um curso: " + ex.getMessage());
        }
        return null;
    }
    
    @Override
    public void login(String name){
        user = getUserByName(name);
    }
}
