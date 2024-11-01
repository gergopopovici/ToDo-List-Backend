package edu.bbte.idde.pgim2289.repository.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.pgim2289.exceptions.EntityNotFoundException;
import edu.bbte.idde.pgim2289.exceptions.InvalidInputException;
import edu.bbte.idde.pgim2289.model.BaseEntity;
import edu.bbte.idde.pgim2289.repository.Dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public abstract class JdbcDao<T extends BaseEntity> implements Dao<T>{
    private static HikariDataSource dataSource;
    private static final Logger logger = (Logger) LoggerFactory.getLogger(JdbcDao.class);

    public JdbcDao() {
        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/toDo");
        dataSource.setUsername("root");
        dataSource.setPassword("admin");
        dataSource.setMaximumPoolSize(10);
    }


    public static HikariDataSource getDataSource() {
        return dataSource;
    }

    protected abstract T mapRow(ResultSet resultSet) throws SQLException;
    protected abstract void prepareInsert(PreparedStatement statement, T entity) throws SQLException;
    protected abstract void prepareUpdate(PreparedStatement statement, T entity) throws SQLException;
    @Override
    public Collection<T> findAll() {
        Collection <T> toDos = new ArrayList<>();
        String sql = "SELECT * FROM ToDo";
        try (Connection conn = dataSource.getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)){
            while (resultSet.next()) {
                toDos.add(mapRow(resultSet));
        }
        } catch (SQLException ex) {
            logger.error("Error while executing SQL query", ex);
        }
        return toDos;
    }

    @Override
    public void delete(Long ID) throws EntityNotFoundException {
        String sql = "DELETE FROM ToDo WHERE Id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setLong(1, ID);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new EntityNotFoundException("Entity with ID " + ID + " not found");
            }
        } catch (SQLException ex) {
            logger.error("Error while deleting entity with ID " + ID, ex);
        }
    }
    @Override
    public void create(T entity) throws InvalidInputException {
        String sql = "INSERT INTO ToDo (Title, Priority, DueDate, Description) VALUES (?,?,?,?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)){
            prepareInsert(statement, entity);
            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error while creating entity", ex);
            throw new InvalidInputException("Error while creating entity");
        }
    }

    @Override
    public void update(T entity) throws EntityNotFoundException {
        String sql = "UPDATE ToDo SET Title = ?, Priority = ?, DueDate = ?, Description = ? WHERE Id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)){
             prepareUpdate(statement, entity);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new EntityNotFoundException("Entity with ID " + entity.getId() + " not found");
            }
        } catch (SQLException ex) {
            logger.error("Error while updating entity with ID " + entity.getId(), ex);
        }
    }

    @Override
    public T findById(Long id) throws EntityNotFoundException {
        String sql = "SELECT * FROM ToDo WHERE Id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()) {
                    return mapRow(resultSet);
                } else {
                    throw new EntityNotFoundException("Entity with ID " + id + " not found");
                }
            }
        } catch (SQLException ex) {
            logger.error("Error while finding entity with ID " + id, ex);
            throw new EntityNotFoundException("Error while finding entity with ID " + id);
        }
    }

}
