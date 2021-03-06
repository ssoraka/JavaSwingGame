package model;


import model.items.Armor;
import model.items.Helm;
import model.items.Weapon;
import model.war.*;
import org.sqlite.JDBC;
import app.ApplicationProperties;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.sql.*;
import java.util.Set;

import static model.war.Warrior.*;

public class DAO {

    private static boolean loggerOn = ApplicationProperties.propertyEqual("db_logs", "true");
    private static String LOGIN = "login";
    private static String PASSWORD = "password";
    private static String ID = "id";

    private static String SELECT_QUERY_BY_LOGIN_AND_PASSWORD = "SELECT * FROM players WHERE login=? AND password=?";
    private static String SELECT_QUERY_BY_LOGIN = "SELECT * FROM players WHERE login=?";
    private static String SELECT_QUERY_ALL = "SELECT * FROM players";
    private static String INSERT_QUERY_START = "INSERT INTO 'players' ";
    private static String UPDATE_QUERY_START = "UPDATE players SET ";
    private static String DROP_TABLE_QUERY = "DROP TABLE if exists 'players';";
    private static String CREATE_TABLE_QUERY =
            "CREATE TABLE if not exists 'players' (" +
                    "'" + ID + "' INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "'" + LOGIN + "' text, " +
                    "'" + PASSWORD + "' text, " +
                    "'" + TYPE + "' text, " +
                    "'" + LEVEL + "' INT, " +
                    "'" + EXP + "' INT, " +
                    "'" + HP + "' INT, " +
                    "'" + ATTACK + "' INT, " +
                    "'" + DEFENSE + "' INT, " +
                    "'" + HIT_POINTS + "' INT, " +
                    "'" + WEAPON + "' text, " +
                    "'" + ARMOR + "' text, " +
                    "'" + HELM + "' text);";

    private static Connection connection;
    private StringBuilder request;

    public synchronized void createConnection() throws SQLException {
        if (connection == null) {
            DriverManager.registerDriver(new JDBC());
            connection = DriverManager.getConnection("jdbc:sqlite:" + ApplicationProperties.getProperties("db_path"));
            if (loggerOn) {
                System.out.println("Base connected!");
            }
        }
    }

    public DAO() throws SQLException {
        createConnection();
        if (ApplicationProperties.propertyEqual("profile", "test")) {
            dropTable();
            createDB();
            initDB();
            readDB();
        }
    }

    public void closeDB() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (loggerOn) {
            System.out.println("Connections closed, base disconnected");
        }
    }

    public void dropTable() {
        try ( PreparedStatement statement = connection.prepareStatement(DROP_TABLE_QUERY) ) {
            statement.execute() ;
            if (loggerOn) {
                System.out.println("deleted the old table");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createDB() {
        try ( PreparedStatement statement = connection.prepareStatement(CREATE_TABLE_QUERY) ) {
            statement.execute();
            if (loggerOn) {
                System.out.println("Created a new table");
            }
        } catch (SQLException e) {
            if (loggerOn) {
                System.out.println("Failed to create table");
            }
            e.printStackTrace();
        }
    }

    public void initDB() {
        Warrior player = WarriorFabric.createPlayer("Capybara", Clazz.CAPYBARA);
        player.setExperience(0);
        player.setLevel(5);
        createPlayer(player.getName(), "_" + player.getName() + "_", player);

        player = WarriorFabric.createPlayer("Name2", Clazz.ALPACA);
        createPlayer(player.getName(), "_" + player.getName() + "_", player);

        player = WarriorFabric.createPlayer("Name3", Clazz.HONEY_BADGER);
        createPlayer(player.getName(), "_" + player.getName() + "_", player);

        if (loggerOn) {
            System.out.println("Init table");
        }
    }

    public void readDB() {
        if (!loggerOn) {
            return;
        }

        try ( PreparedStatement statement = connection.prepareStatement(SELECT_QUERY_ALL) ) {
            ResultSet resSet = statement.executeQuery();
            while (resSet.next()) {
                int id = resSet.getInt(ID);
                String login = resSet.getString(LOGIN);
                String password = resSet.getString(PASSWORD);
                int exp = resSet.getInt(EXP);
                int hp = resSet.getInt(HP);
                int attack = resSet.getInt(ATTACK);
                int def = resSet.getInt(DEFENSE);
                int helmet = resSet.getInt(HIT_POINTS);
                int level = resSet.getInt(LEVEL);
                String type = resSet.getString(TYPE);
                String weapon = resSet.getString(WEAPON);
                String armor = resSet.getString(ARMOR);
                String helm = resSet.getString(HELM);
                System.out.printf("ID = %d, login = %s, pas = %s, exp = %d, hp = %d, attack = %d, def = %d, helmet = %d, level = %d, type = %s, %s, %s, %s",
                        id, login, password, exp, hp, attack, def, helmet, level, type, weapon, armor, helm);
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("select");
    }

    private void insertRequest(String field, String value) {
        request = new StringBuilder(INSERT_QUERY_START)
                .append("( '")
                .append(field)
                .append("' ) VALUES ( '")
                .append(value)
                .append("' );");
    }

    private void insertInRequest(String field, String value) {
        int posField = request.indexOf(")") - 1;
        int posValue = request.length() - 3;
        request.insert(posValue, value);
        request.insert(posField, field);
    }

    private void addArg(String field, String value) {
        insertInRequest(", '", ", '");
        insertInRequest(field, value);
        insertInRequest("'", "'");
    }

    private void addArg(String field, int value) {
        insertInRequest(", '", ", ");
        insertInRequest(field, String.valueOf(value));
        insertInRequest("'", "");
    }

    public boolean isLoginAlreadyExist(String login) {
        try ( PreparedStatement statement = connection.prepareStatement(SELECT_QUERY_BY_LOGIN) ) {
            statement.setString(1, login);
            ResultSet resSet = statement.executeQuery();
            return resSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean isLoginAndPasswordAlreadyExist(String login, String password) {
        try ( PreparedStatement statement = connection.prepareStatement(SELECT_QUERY_BY_LOGIN_AND_PASSWORD) ) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resSet = statement.executeQuery();
            return resSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void createPlayer(String login, String password, Warrior warrior) {
        insertRequest(LOGIN, login);
        addArg(PASSWORD, password);
        addArg(TYPE, warrior.getClazz().name());
        addArg(LEVEL, warrior.getLevel());
        addArg(EXP, warrior.getExperience());
        addArg(HP, warrior.getHitPoints());
        addArg(ATTACK, warrior.getAttack());
        addArg(DEFENSE, warrior.getDefense());
        addArg(HIT_POINTS, warrior.getHitPoints());

        addArg(WEAPON, warrior.getWeapon().name());
        addArg(ARMOR, warrior.getArmor().name());
        addArg(HELM, warrior.getHelm().name());

        try ( PreparedStatement statement = connection.prepareStatement(request.toString()) ) {
            statement.execute();
            readDB();
        } catch (Exception e) {
            System.err.println("Not registered in the database");
            e.printStackTrace();
        }
    }

    public void updatePlayer(Warrior warrior) {
        request = new StringBuilder(UPDATE_QUERY_START)
                .append("'").append(EXP).append("'=")
                .append(warrior.getExperience())
                .append(",'").append(HP).append("'=")
                .append(warrior.getHp())
                .append(",'").append(HIT_POINTS).append("'=")
                .append(warrior.getHitPoints())
                .append(",'").append(ATTACK).append("'=")
                .append(warrior.getAttack())
                .append(",'").append(DEFENSE).append("'=")
                .append(warrior.getDefense())

                .append(",'").append(HELM).append("'='")
                .append(warrior.getHelm().name())
                .append("','").append(WEAPON).append("'='")
                .append(warrior.getWeapon().name())
                .append("','").append(ARMOR).append("'='")
                .append(warrior.getArmor().name())

                .append("','").append(LEVEL).append("'=")
                .append(warrior.getLevel())
                .append(" WHERE ").append(LOGIN).append("='")
                .append(warrior.getName())
                .append("';");
        try ( PreparedStatement statement = connection.prepareStatement(request.toString()) ) {
            statement.execute();
            readDB();
        } catch (Exception e) {
            System.err.println("The record in the database has not been updated");
            e.printStackTrace();
        }
    }

    public Warrior readPlayer(String login, String password) {
        Warrior player = null;
        try ( PreparedStatement statement = connection.prepareStatement(SELECT_QUERY_BY_LOGIN_AND_PASSWORD) ) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resSet = statement.executeQuery();

            player = WarriorFabric.createPlayer(login, Clazz.valueOf(resSet.getString(TYPE)));
            player.setLevel(resSet.getInt(LEVEL));
            player.setExperience(resSet.getInt(EXP));
            player.setAttack(resSet.getInt(ATTACK));
            player.setHitPoints(resSet.getInt(HIT_POINTS));
            player.setDefense(resSet.getInt(DEFENSE));

            player.setWeapon(Weapon.valueOf(resSet.getString(WEAPON)));
            player.setHelm(Helm.valueOf(resSet.getString(HELM)));
            player.setArmor(Armor.valueOf(resSet.getString(ARMOR)));

            player.setHp(resSet.getInt(HP));
            player.heel();

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<Warrior>> violations = validator.validate(player);
            if (!violations.isEmpty()) {
                StringBuilder log = new StringBuilder();
                for (ConstraintViolation<Warrior> errors : violations) {
                    log.append(errors.getMessage()).append("\n");
                }
                throw new DAOException(log.toString());
            }
        } catch (IllegalArgumentException e) {
            throw new DAOException("Weapon, armor or helmet specified incorrectly");
        } catch (SQLException e) {
            throw new DAOException("There is no such login and password in the database");
        }
        return player;
    }
}
