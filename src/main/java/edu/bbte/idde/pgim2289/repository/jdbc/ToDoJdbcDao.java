package edu.bbte.idde.pgim2289.repository.jdbc;

import edu.bbte.idde.pgim2289.model.ToDo;
import edu.bbte.idde.pgim2289.repository.ToDoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class ToDoJdbcDao extends JdbcDao<ToDo> implements ToDoDao {

    @Override
    public Collection<ToDo> findByPriority(Integer priority) {
        Collection <ToDo> todos = new ArrayList<>();
        String sql = "SELECT * FROM ToDo WHERE Priority = ?";
        try (Connection conn = getDataSource().getConnection())
        {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1,priority);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                todos.add(mapRow(resultSet));
            }
        }catch (SQLException ex) {
            Logger logger = logger = (Logger) LoggerFactory.getLogger(ToDoJdbcDao.class);;
            logger.error("Error while executing SQL query", ex);
        }
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
        return todo;
    }

    @Override
    protected void prepareInsert(PreparedStatement statement, ToDo entity) throws SQLException {
        statement.setString(1, entity.getTitle());
        statement.setInt(2, entity.getPriority());
        statement.setDate(3, new java.sql.Date(entity.getDate().getTime()));
        statement.setString(4, entity.getDescription());
    }

    @Override
    protected void prepareUpdate(PreparedStatement statement, ToDo entity) throws SQLException {
        statement.setString(1, entity.getTitle());
        statement.setInt(2, entity.getPriority());
        statement.setDate(3, new java.sql.Date(entity.getDate().getTime()));
        statement.setString(4, entity.getDescription());
        statement.setLong(5, entity.getId());
    }
}
