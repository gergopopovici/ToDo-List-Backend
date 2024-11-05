package edu.bbte.idde.pgim2289.repository.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.pgim2289.exceptions.DatabaseException;
import edu.bbte.idde.pgim2289.exceptions.EntityNotFoundException;
import edu.bbte.idde.pgim2289.exceptions.InvalidInputException;
import edu.bbte.idde.pgim2289.model.BaseEntity;
import edu.bbte.idde.pgim2289.repository.Dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public abstract class JdbcDao<T extends BaseEntity> implements Dao<T> {
    private static final HikariDataSource dataSource = DataSourceFactory.getDataSource();
    private static final Logger logger = LoggerFactory.getLogger(JdbcDao.class);
    private static final String tableName = "ToDo";

    protected JdbcDao() {
    }

    protected abstract T mapRow(ResultSet resultSet) throws SQLException;

    protected abstract void prepareInsert(PreparedStatement statement, T entity) throws SQLException;

    protected abstract void prepareUpdate(PreparedStatement statement, T entity) throws SQLException;

    @Override
    public Collection<T> findAll() {
        logger.info("Finding all entities");
        Collection<T> toDos = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;
        try (Connection conn = dataSource.getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                toDos.add(mapRow(resultSet));
            }
        } catch (SQLException ex) {
            logger.error("Error while executing SQL query", ex);
            throw new DatabaseException("Error while finding all entities", ex);
        }
        logger.info("Returning all entities");
        return toDos;
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        logger.info("Deleting entity with ID " + id);
        String sql = "DELETE FROM " + tableName + " WHERE Id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new EntityNotFoundException("Entity with ID " + id + " not found");
            }
        } catch (SQLException ex) {
            logger.error("Error while deleting entity with ID " + id, ex);
            throw new DatabaseException("Error while deleting entity with ID " + id, ex);
        }
        logger.info("Entity with ID " + id + " deleted");
    }

    @Override
    public void create(T entity) throws InvalidInputException {
        logger.info("Creating entity");
        String sql = "INSERT INTO " + tableName + " (Title, Priority, DueDate, Description) VALUES (?,?,?,?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            prepareInsert(statement, entity);
            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error while creating entity", ex);
            throw new DatabaseException("Error while creating entity", ex);
        }
        logger.info("Entity created successfully");
    }

    @Override
    public void update(T entity) throws EntityNotFoundException {
        logger.info("Updating entity with ID " + entity.getId());
        String sql = "UPDATE " + tableName + " SET Title = ?, Priority = ?, DueDate = ?, Description = ? WHERE Id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            prepareUpdate(statement, entity);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new EntityNotFoundException("Entity with ID " + entity.getId() + " not found");
            }
        } catch (SQLException ex) {
            logger.error("Error while updating entity with ID " + entity.getId(), ex);
            throw new DatabaseException("Error while updating entity with ID " + entity.getId(), ex);
        }
        logger.info("Entity with ID " + entity.getId() + " updated");
    }

    @Override
    public T findById(Long id) throws EntityNotFoundException {
        logger.info("Finding entity with ID " + id);
        String sql = "SELECT * FROM " + tableName + " WHERE Id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    logger.info("Entity with ID " + id + " found");
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
