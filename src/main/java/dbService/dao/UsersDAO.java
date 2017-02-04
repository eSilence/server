package dbService.dao;

import dbService.dataSets.UsersDataSet;
import dbService.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsersDAO {

    private final Executor executor;

    public UsersDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    public UsersDataSet get(long id) throws SQLException {
        return executor.execQuery("select * from users where id=" + id, result -> {
            result.next();
            return new UsersDataSet(result.getLong(1), result.getString(2), result.getString(3));
        });
    }

    public long getUserId(String login) throws SQLException {
        return executor.execQuery("select * from users where login ='" + login + "'", result -> {
            result.next();
            return result.getLong(1);
        });
    }

    public ArrayList<UsersDataSet> getUserAll() throws SQLException {
        ArrayList<UsersDataSet> sets = new ArrayList<>();
        return executor.execQuery("select * from users", result -> {
            while (result.next()) {
                sets.add(new UsersDataSet(result.getLong(1), result.getString(2), result.getString(3)));
            }
            return sets;
        });
    }

    public void insertUser(String login, String pass) throws SQLException {
        executor.execUpdate("insert into users (login, password) values ('" + login+ "'"+ "," +"'"+ pass+"')");
    }

    public void createTable() throws SQLException {
        executor.execUpdate("create table if not exists users (id bigint auto_increment, login varchar(256), password varchar(256), primary key (id));");
    }

    public void dropTable() throws SQLException {
        executor.execUpdate("drop table users");
    }
}