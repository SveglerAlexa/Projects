package DataAccess;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import Connection.ConnectionFactory;

/**
 * @Author: Technical University of Cluj-Napoca, Romania Distributed Systems
 *          Research Laboratory, http://dsrl.coned.utcluj.ro/
 * @Since: Apr 03, 2017
 * @Source http://www.java-blog.com/mapping-javaobjects-database-reflection-generics
 */


/**
 *This class use reflection to dynamically map Java objects to database tables.
 * @param <T> - the type of object this DAO manages.
 */


public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }



    /**
     *
     * @return - all the column/rows from the database.
     */

    public List<T> findAll() {
        // TODO:

        List<T> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String query = "SELECT * FROM " + type.getSimpleName();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            list = createObjects(resultSet).stream()
                    .collect(Collectors.toList());

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return list;


    }

    /**
     * @param id - the id for the Client/Product that must be found.
     * @return - it returns an instance of a Client/Product or a null if the object is not found.
     * The method executes a Select query using the id field of the table.
     */

    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * This method makes the connection with the database, it creates the query for making the insertion in the database
     * it sets the values for the parameters and inserts it in the database.
     * @param t - it receives a generic object t
     * @return - it inserts it in the table/database.
     */

    public T insert(T t) {
        // TODO:

        Connection connection = null;
        PreparedStatement statement = null;

        ///obtinem campurile definite in clasa T
        Field[] fields = type.getDeclaredFields();

        ///transformam array-ul intr-o lista
        List<Field> fieldList = Arrays.asList(fields);

        ///cream un String cu numele coloanelor separate prin virgula
        String columnNames = fieldList.stream()
                .map(Field::getName)
                .collect(Collectors.joining(", "));

        ///cream un String cu ? pentru a lega valorile la query
        String placeholders = fieldList.stream()
                .map(f -> "?")
                .collect(Collectors.joining(", "));
        ///numele clasei, numele coloanelor, semndele de intrbare
        String query = String.format("INSERT INTO %s (%s) VALUES (%s)",
                type.getSimpleName(), columnNames, placeholders);

        try {
            ///deschidem conexiunea la baza de date
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);


            ///pentru fiecare camp
            for (int i = 0; i < fieldList.size(); i++) {
                ///dam acces la camp daca e private
                fieldList.get(i).setAccessible(true);
                ///obtinem valoarea obiectului
                Object value = fieldList.get(i).get(t);
                ///legam fiecare camp la pozitia corecta din PreparedStatements
                statement.setObject(i + 1, value);
            }

            statement.executeUpdate();

        } catch (SQLException | IllegalAccessException e) {
            LOGGER.warning("Error during insert: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ///inchidem resursele deschise chiar daca a aparut o eroare
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }


            return t;
    }

    /**
     * This methood receives a generic object t, makes the connection with the database, creates the specific query for update,
     * gets the id for the object that must be updated and updates it with the values given by the user.
     * @param t - it receives a generic object t that muse be updated
     * @return - the updated generic object
     */

    public T update(T t) {
        // TODO:

        Connection connection = null;
        PreparedStatement statement = null;

        Field[] fields = type.getDeclaredFields();
        List<Field> fieldList = Arrays.asList(fields);

        String setClause = fieldList.stream()
                .filter(f -> !f.getName().equalsIgnoreCase("id"))
                .map(f -> f.getName() + " = ?")
                .collect(Collectors.joining(", "));

        String query = String.format("UPDATE %s SET %s WHERE id = ?", type.getSimpleName(), setClause);

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            int paramIndex = 1;
            Object idValue = null;

            for (Field field : fieldList) {
                field.setAccessible(true);
                Object value = field.get(t);

                if (!field.getName().equalsIgnoreCase("id")) {
                    statement.setObject(paramIndex++, value);
                } else {
                    idValue = value;
                }
            }
            statement.setObject(paramIndex, idValue);

            statement.executeUpdate();

        } catch (SQLException | IllegalAccessException e) {
            LOGGER.warning("Error during update: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

            return t;
    }


    /**
     * This method receives a generic object t that must be deleted, it makes the connection with the database, it creates
     * the specific query for the deletion in the database, it finds the object that must be deleted by its id, and it deletes it.
     * @param t -  it receives a generic object t that muse be deleted
     * @return - the object that was deleted
     */

    public T delete(T t) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            Field[] fields = type.getDeclaredFields();

            Field idField = Arrays.stream(fields)
                    .filter(f -> f.getName().equalsIgnoreCase("id"))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("id field not found"));

            idField.setAccessible(true);

            String query = "DELETE FROM " + type.getSimpleName() + " WHERE id=?";
            statement = connection.prepareStatement(query);
            statement.setObject(1, idField.get(t));

            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }


        return t;
    }
}
