package org.example.dao.implementation;

import org.example.dao.framework.UserProfileDAO;
import org.example.entity.UserProfile;
import org.example.model.UserError;
import org.example.model.UserModel;
import org.example.utilities.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserProfileDAOImpl implements UserProfileDAO {

    @Override
    public List<UserProfile> getAllUserProfiles() {
        List<UserProfile> userProfiles = new ArrayList<>();
        String sql = "SELECT * FROM user_profile";

        try (Connection connection = DatabaseConnection.getConnection(); // Use the connection manager
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                UserProfile userProfile = new UserProfile();
                userProfile.setUserId(resultSet.getInt("USER_ID"));
                userProfile.setName(resultSet.getString("Name"));
                userProfile.setUserName(resultSet.getString("User_Name"));
                userProfile.setPassword(resultSet.getString("Password"));
                userProfile.setAddress(resultSet.getString("Address"));
                userProfile.setNationalId(resultSet.getString("National_Id"));
                userProfile.setPhone(resultSet.getString("Phone"));
                userProfile.setDepartmentId(resultSet.getInt("Department_Id"));
                userProfile.setSalary(resultSet.getDouble("Salary"));
                userProfile.setBirthDate(resultSet.getDate("birth_date").toLocalDate());

                userProfiles.add(userProfile);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userProfiles;
    }

    @Override
    public UserProfile getUserProfileById(int userId) {
        UserProfile userProfile = null;
        String sql = "SELECT * FROM user_profile WHERE USER_ID = ?";

        try (Connection connection = DatabaseConnection.getConnection(); // Use the connection manager
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                userProfile = new UserProfile();
                userProfile.setUserId(resultSet.getInt("USER_ID"));
                userProfile.setName(resultSet.getString("Name"));
                userProfile.setUserName(resultSet.getString("User_Name"));
                userProfile.setPassword(resultSet.getString("Password"));
                userProfile.setAddress(resultSet.getString("Address"));
                userProfile.setNationalId(resultSet.getString("National_Id"));
                userProfile.setPhone(resultSet.getString("Phone"));
                userProfile.setDepartmentId(resultSet.getInt("Department_Id"));
                userProfile.setSalary(resultSet.getDouble("Salary"));
                userProfile.setBirthDate(resultSet.getDate("birth_date").toLocalDate());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userProfile;
    }
    public UserProfile getUserProfileByNationalId(String nationalId) {
        UserProfile userProfile = null;
        String sql = "SELECT * FROM user_profile WHERE National_Id = ?";

        try (Connection connection = DatabaseConnection.getConnection(); // Use the connection manager
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, nationalId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                userProfile = new UserProfile();
                userProfile.setUserId(resultSet.getInt("USER_ID"));
                userProfile.setName(resultSet.getString("Name"));
                userProfile.setUserName(resultSet.getString("User_Name"));
                userProfile.setPassword(resultSet.getString("Password"));
                userProfile.setAddress(resultSet.getString("Address"));
                userProfile.setNationalId(resultSet.getString("National_Id"));
                userProfile.setPhone(resultSet.getString("Phone"));
                userProfile.setDepartmentId(resultSet.getInt("Department_Id"));
                userProfile.setSalary(resultSet.getDouble("Salary"));
                userProfile.setBirthDate(resultSet.getDate("birth_date").toLocalDate());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userProfile;
    }


    @Override
    public UserError addUserProfile(UserProfile userProfile) {
        String sql = "INSERT INTO user_profile (Name, User_Name, Password, Address, National_Id, Phone, Department_Id, Salary, birth_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection(); // Use the connection manager
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, userProfile.getName());
            preparedStatement.setString(2, userProfile.getUserName());
            preparedStatement.setString(3, userProfile.getPassword());
            preparedStatement.setString(4, userProfile.getAddress());
            preparedStatement.setString(5, userProfile.getNationalId());
            preparedStatement.setString(6, userProfile.getPhone());
            preparedStatement.setInt(7, userProfile.getDepartmentId());
            preparedStatement.setDouble(8, userProfile.getSalary());
            preparedStatement.setDate(9, Date.valueOf(userProfile.getBirthDate()));

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                userProfile.setUserId(rs.getInt(1));
            }
            return new UserError("Doneee", 0, true);

        } catch (SQLException e) {
            return new UserError("The error is" + e.getMessage(), e.getErrorCode(), false);
        }
    }

    @Override
    public UserError updateUserProfile(UserProfile userProfile) {
        String sql = "UPDATE user_profile SET Name = ?, User_Name = ?, Password = ?, Address = ?, National_Id = ?, Phone = ?, Department_Id = ?, Salary = ?, birth_date = ? WHERE USER_ID = ?";

        try (Connection connection = DatabaseConnection.getConnection(); // Use the connection manager
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, userProfile.getName());
            preparedStatement.setString(2, userProfile.getUserName());
            preparedStatement.setString(3, userProfile.getPassword());
            preparedStatement.setString(4, userProfile.getAddress());
            preparedStatement.setString(5, userProfile.getNationalId());
            preparedStatement.setString(6, userProfile.getPhone());
            preparedStatement.setInt(7, userProfile.getDepartmentId());
            preparedStatement.setDouble(8, userProfile.getSalary());
            preparedStatement.setDate(9, Date.valueOf(userProfile.getBirthDate()));
            preparedStatement.setInt(10, userProfile.getUserId());

            preparedStatement.executeUpdate();

            return new UserError("Doneee", 0, true);

        } catch (SQLException e) {
            return new UserError("The error is" + e.getMessage(), e.getErrorCode(), false);
        }
    }

    @Override
    public UserError deleteUserProfile(int userId) {
        String sql = "DELETE FROM user_profile WHERE USER_ID = ?";

        try (Connection connection = DatabaseConnection.getConnection(); // Use the connection manager
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();

            return new UserError("Doneee", 0, true);

        } catch (SQLException e) {
            return new UserError("The error is" + e.getMessage(), e.getErrorCode(), false);
        }
    }

    @Override
    public List<UserModel> getTopFiveSalaries() {
        List<UserModel> users = new ArrayList<>();
        String query = "SELECT u.USER_ID, u.Name, u.User_Name, u.Password, u.Address, u.National_Id, " +
                "u.Phone, u.Department_Id, u.Salary, u.Birth_Date, d.Department_Name " +
                "FROM user_profile u " +
                "INNER JOIN department d ON u.Department_Id = d.DEPARTMENT_ID " +
                "WHERE u.Department_Id IN (1, 2) AND " +
                "    (SELECT COUNT(*) " +
                "     FROM user_profile u2 " +
                "     WHERE u2.Department_Id = u.Department_Id AND u2.Salary > u.Salary) < 5 " +
                "ORDER BY u.Department_Id, u.Salary DESC";

        try (Connection connection = DatabaseConnection.getConnection(); // Use the connection manager
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                UserModel userProfile = new UserModel();
                userProfile.setUserId(resultSet.getInt("USER_ID"));
                userProfile.setName(resultSet.getString("Name"));
                userProfile.setUserName(resultSet.getString("User_Name"));
                userProfile.setPassword(resultSet.getString("Password"));
                userProfile.setAddress(resultSet.getString("Address"));
                userProfile.setNationalId(resultSet.getString("National_Id"));
                userProfile.setPhone(resultSet.getString("Phone"));
                userProfile.setDepartmentId(resultSet.getInt("Department_Id"));
                userProfile.setSalary(resultSet.getDouble("Salary"));
                userProfile.setBirthDate(resultSet.getDate("Birth_Date").toLocalDate());
                userProfile.setDepartmentName(resultSet.getString("Department_Name"));

                // Calculate age
                int birthYear = userProfile.getBirthDate().getYear();
                userProfile.setAge(2024 - birthYear);

                users.add(userProfile);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}
