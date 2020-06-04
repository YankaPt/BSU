package bsu.jp.dao;

import bsu.jp.model.Deal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DealDAO {
    private Connection connection;

    public DealDAO() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/java", "root", "");
    }

    public String testQuery() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from deals");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getString(2)+" "+resultSet.getString(3);
    }

    public long getSumOfAll() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select sum(sumOfDeal) from deals");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getLong(1);
    }

    public long getAmountOfAll() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select count(*) as summ from deals where type='buy'");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getLong(1);
    }

    public List<Deal> getAllDeals() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from deals");
        ResultSet resultSet = statement.executeQuery();
        List<Deal> deals = new ArrayList<>();
        while (resultSet.next()) {
            Deal deal = new Deal();
            deal.setId(resultSet.getLong(1));
            deal.setType(resultSet.getString(2));
            deal.setName(resultSet.getString(3));
            deal.setDateOfDeal(resultSet.getString(4));
            deal.setSumOfDeal(resultSet.getLong(5));
            deals.add(deal);
        }
        return deals;
    }
}
