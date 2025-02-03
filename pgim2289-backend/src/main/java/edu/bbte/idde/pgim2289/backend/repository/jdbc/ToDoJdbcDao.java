package edu.bbte.idde.pgim2289.backend.repository.jdbc;

import edu.bbte.idde.pgim2289.backend.exceptions.DatabaseException;
import edu.bbte.idde.pgim2289.backend.exceptions.InvalidInputException;
import edu.bbte.idde.pgim2289.backend.model.ToDo;
import edu.bbte.idde.pgim2289.backend.repository.ToDoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ToDoJdbcDao extends JdbcDao<ToDo> implements ToDoDao {

    private static final Logger logger = LoggerFactory.getLogger(ToDoJdbcDao.class);
    private static final String tableName = "ToDo";

    @Override
    public Collection<ToDo> findByPriority(Integer priority) {
        logger.info("Finding all entities with priority " + priority);
        Collection<ToDo> todos = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName + " WHERE Priority = ?";
        try (Connection conn = DataSourceFactory.getDataSource().getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, priority);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                todos.add(mapRow(resultSet));
            }
        } catch (SQLException | IOException ex) {
            logger.error("Error while executing SQL query", ex);
            throw new DatabaseException("Error while finding all entities with priority " + priority, ex);
        }
        logger.info("Returning all entities with priority " + priority);
        return todos;
    }

    @Override
    protected ToDo mapRow(ResultSet resultSet) throws SQLException {
        ToDo todo = new ToDo();
        todo.setId(resultSet.getLong("Id"));
        todo.setPriority(resultSet.getInt("Priority"));
        todo.setDescription(resultSet.getString("Description"));
        todo.setDate(resultSet.getDate("DueDate"));
        todo.setTitle(resultSet.getString("Title"));
        todo.setLastUpdatedAt(resultSet.getTimestamp("lastUpdatedAt"));
        return todo;
    }

    @Override
    protected void prepareInsert(PreparedStatement statement, ToDo entity) throws SQLException, InvalidInputException {
        // validateToDoInput(entity);
        statement.setString(1, entity.getTitle());
        statement.setInt(2, entity.getPriority());
        statement.setDate(3, new Date(entity.getDate().getTime()));
        statement.setString(4, entity.getDescription());
        statement.setTimestamp(5, entity.getLastUpdatedAt());
    }

    @Override
    protected void prepareUpdate(PreparedStatement statement, ToDo entity) throws SQLException {
        // validateToDoInput(entity);
        statement.setString(1, entity.getTitle());
        statement.setInt(2, entity.getPriority());
        statement.setDate(3, new Date(entity.getDate().getTime()));
        statement.setString(4, entity.getDescription());
        statement.setLong(5, entity.getId());
        statement.setTimestamp(5, entity.getLastUpdatedAt());
    }

    @Override
    protected String getTableName() {
        return tableName;
    }

    @Override
    protected List<String> getColumnNames() {
        return List.of("Title", "Priority", "DueDate", "Description", "lastUpdatedAt");
    }
}
