package edu.bbte.idde.pgim2289.spring.repository.jdbc;

import edu.bbte.idde.pgim2289.spring.exceptions.DatabaseException;
import edu.bbte.idde.pgim2289.spring.exceptions.InvalidInputException;
import edu.bbte.idde.pgim2289.spring.model.ToDo;
import edu.bbte.idde.pgim2289.spring.repository.ToDoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
@Profile("jdbc")
public class ToDoJdbcDao extends JdbcDao<ToDo> implements ToDoDao {

    private static final Logger logger = LoggerFactory.getLogger(ToDoJdbcDao.class);
    private static final String tableName = "todospring";

    public ToDoJdbcDao(DataSource dataSource) {
        super(dataSource);
        this.dataSource = dataSource;
    }

    @Override
    public Collection<ToDo> findByPriority(Integer priority) {
        logger.info("Finding all entities with priority " + priority);
        Collection<ToDo> todos = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName + " WHERE Priority = ?";
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, priority);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                todos.add(mapRow(resultSet));
            }
        } catch (SQLException ex) {
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
        todo.setDate(resultSet.getDate("Duedate"));
        todo.setTitle(resultSet.getString("Title"));
        return todo;
    }

    @Override
    protected void prepareInsert(PreparedStatement statement, ToDo entity) throws SQLException, InvalidInputException {
        // validateToDoInput(entity);
        statement.setString(1, entity.getTitle());
        statement.setInt(2, entity.getPriority());
        statement.setDate(3, new java.sql.Date(entity.getDate().getTime()));
        statement.setString(4, entity.getDescription());
    }

    @Override
    protected void prepareUpdate(PreparedStatement statement, ToDo entity) throws SQLException {
        // validateToDoInput(entity);
        statement.setString(1, entity.getTitle());
        statement.setInt(2, entity.getPriority());
        statement.setDate(3, new java.sql.Date(entity.getDate().getTime()));
        statement.setString(4, entity.getDescription());
        statement.setLong(5, entity.getId());
    }

    @Override
    protected String getTableName() {
        return tableName;
    }

    @Override
    protected List<String> getColumnNames() {
        return List.of("Title", "Priority", "Duedate", "Description");
    }
}
