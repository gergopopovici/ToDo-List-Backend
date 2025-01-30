package edu.bbte.idde.pgim2289.spring.repository.jdbc;

import edu.bbte.idde.pgim2289.spring.exceptions.DatabaseException;
import edu.bbte.idde.pgim2289.spring.exceptions.EntityNotFoundException;
import edu.bbte.idde.pgim2289.spring.model.BaseEntity;
import edu.bbte.idde.pgim2289.spring.repository.Dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class JdbcDao<T extends BaseEntity> implements Dao<T> {
    protected DataSource dataSource;


    private static final Logger logger = LoggerFactory.getLogger(JdbcDao.class);

    @Autowired
    protected JdbcDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected abstract T mapRow(ResultSet resultSet) throws SQLException;

    protected abstract void prepareInsert(PreparedStatement statement, T entity) throws SQLException;

    protected abstract void prepareUpdate(PreparedStatement statement, T entity) throws SQLException;

    protected abstract String getTableName();

    protected abstract List<String> getColumnNames();

    @Override
    public Collection<T> findAll() {
        logger.info("Finding all entities");
        Collection<T> entities = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + "LIMIT 5";
        try (Connection conn = dataSource.getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                entities.add(mapRow(resultSet));
            }
        } catch (SQLException ex) {
            logger.error("Error while executing SQL query", ex);
            throw new DatabaseException("Error while finding all entities", ex);
        }
        logger.info("Returning all entities");
        return entities;
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        logger.info("Deleting entity with ID " + id);
        String sql = "DELETE FROM " + getTableName() + " WHERE Id = ?";
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
    public void create(T entity) {
        logger.info("Creating entity");
        String columns = String.join(", ", getColumnNames());
        String placeholders = String.join(", ", Collections.nCopies(getColumnNames().size(), "?"));
        String sql = "INSERT INTO " + getTableName() + " (" + columns + ") VALUES (" + placeholders + ")";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            prepareInsert(statement, entity);
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long generatedId = generatedKeys.getLong(1);
                    entity.setId(generatedId);
                    logger.info("Generated ID: " + generatedId);
                } else {
                    logger.warn("No ID generated for the created entity.");
                    throw new DatabaseException("Entity creation succeeded, but no ID was generated.", null);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error while creating entity", ex);
            throw new DatabaseException("Error while creating entity", ex);
        }
        logger.info("Entity created successfully");
    }


    @Override
    public void update(T entity) throws EntityNotFoundException {
        logger.info("Updating entity with ID " + entity.getId());
        List<String> columns = getColumnNames();
        String setClause = String.join(" = ?, ", columns) + " = ?";
        String sql = "UPDATE " + getTableName() + " SET " + setClause + " WHERE Id = ?";
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
        String sql = "SELECT * FROM " + getTableName() + " WHERE Id = ?";
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
            throw new DatabaseException("Error while finding by ID", ex);
        }
    }
}
