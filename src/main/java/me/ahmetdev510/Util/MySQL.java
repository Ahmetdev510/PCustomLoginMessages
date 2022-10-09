package me.ahmetdev510.Util;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.UUID;

import static me.ahmetdev510.PCustomLoginMessages.*;

public class MySQL {
    public static Connection connection;

    public static void openConnection() {
        final String username=config.getString(instance.getName()+".mysql.username");
        final String password=config.getString(instance.getName()+".mysql.password");
        final String database=config.getString(instance.getName()+".mysql.database");
        final String host=config.getString(instance.getName()+".mysql.host");
        final int port=config.getInt(instance.getName()+".mysql.port");
        final String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useUnicode=true&characterEncoding=utf8";
        try {
            connection = DriverManager.getConnection(url, username, password);
            instance.getLogger().info("Connected to MySQL database!");
        } catch (SQLException e) {
            instance.getLogger().info("Could not connect to MySQL database!");
            return;
        }
    }

    public static void closeConnection() {
        try {
            if (connection!=null && !connection.isClosed()){
                connection.close();
            }
        } catch(Exception e) {
            instance.getLogger().info("Could not close MySQL connection!");
            return;
        }
    }

    public static void setPlayerAccessMessages(String uuid, String access_messages) {
        if(config.getBoolean(instance.getName()+".mysql.enabled")) {
            try {
                Statement check = connection.createStatement();
                ResultSet resultSet = check.executeQuery("SELECT * FROM `"+config.getString(instance.getName()+".mysql.database")+"`.`"+config.getString(instance.getName()+".mysql.table")+"` WHERE `uuid` = '"+uuid+"';");
                if(resultSet.next()) {
                    PreparedStatement statement = connection.prepareStatement("UPDATE " + config.getString(instance.getName() + ".mysql.table") + " SET access_messages=? WHERE uuid=?");
                    statement.setString(1, access_messages);
                    statement.setString(2, uuid.toString());
                    statement.executeUpdate();
                    statement.close();
                } else {
                    PreparedStatement statement = connection.prepareStatement("INSERT INTO " + config.getString(instance.getName() + ".mysql.table") + " (uuid, message, access_messages) VALUES (?, ?, ?)");
                    statement.setString(1, uuid.toString());
                    statement.setString(2, "default");
                    statement.setString(3, access_messages);
                    statement.executeUpdate();
                    statement.close();
                }
                check.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            File datafile = new File("plugins/"+instance.getName()+"/"+File.separator+"/players/"+uuid+".yml");
            data = YamlConfiguration.loadConfiguration(datafile);
            if(!datafile.exists()) {
                try{
                    data.save("plugins/"+instance.getName()+"/"+ File.separator+"/players/"+uuid+".yml");
                    datafile.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            data.set("UUID", uuid);
            if(data.getString("access_messages")==null) {
                data.set("message", "default");
            }
            data.set("access_messages", access_messages);
            try {
                data.save("plugins/"+instance.getName()+"/"+ File.separator+"/players/"+uuid+".yml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getPlayerAccessMessages(String uuid) {
        String access_messages = "default";
        if(config.getBoolean(instance.getName()+".mysql.enabled")) {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM `"+config.getString(instance.getName()+".mysql.database")+"`.`"+config.getString(instance.getName()+".mysql.table")+"` WHERE `uuid` = '"+uuid+"';");
                if(resultSet.next()) {
                    access_messages = resultSet.getString("access_messages");
                }
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            File datafile = new File("plugins/"+instance.getName()+"/"+File.separator+"/players/"+uuid+".yml");
            data = YamlConfiguration.loadConfiguration(datafile);
            if(!datafile.exists()) {
                try{
                    data.save("plugins/"+instance.getName()+"/"+ File.separator+"/players/"+uuid+".yml");
                    datafile.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }else{
                if(data.getString("access_messages")!=null) {
                    access_messages = data.getString("access_messages");
                }
            }
        }
        return access_messages;
    }

    public static void setPlayerMessage(String uuid, String message) {
        if(config.getBoolean(instance.getName()+".mysql.enabled")) {
            try {
                Statement check = connection.createStatement();
                ResultSet resultSet = check.executeQuery("SELECT * FROM `"+config.getString(instance.getName()+".mysql.database")+"`.`"+config.getString(instance.getName()+".mysql.table")+"` WHERE `uuid` = '"+uuid+"';");
                if(resultSet.next()) {
                    PreparedStatement statement = connection.prepareStatement("UPDATE " + config.getString(instance.getName() + ".mysql.table") + " SET message=? WHERE uuid=?");
                    statement.setString(1, message);
                    statement.setString(2, uuid);
                    statement.executeUpdate();
                    statement.close();
                } else {
                    PreparedStatement statement = connection.prepareStatement("INSERT INTO " + config.getString(instance.getName() + ".mysql.table") + " (uuid, message) VALUES (?, ?)");
                    statement.setString(1, uuid);
                    statement.setString(2, message);
                    statement.executeUpdate();
                    statement.close();
                }
                check.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            File datafile = new File("plugins/"+instance.getName()+"/"+File.separator+"/players/"+uuid+".yml");
            data = YamlConfiguration.loadConfiguration(datafile);
            if(!datafile.exists()) {
                try{
                    data.save("plugins/"+instance.getName()+"/"+ File.separator+"/players/"+uuid+".yml");
                    datafile.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            data.set("UUID", uuid);
            data.set("message", message);

            try {
                data.save("plugins/"+instance.getName()+"/"+ File.separator+"/players/"+uuid+".yml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getPlayerMessage(String uuid) {
        String message = null;
        if(config.getBoolean(instance.getName()+".mysql.enabled")) {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM `"+config.getString(instance.getName()+".mysql.database")+"`.`"+config.getString(instance.getName()+".mysql.table")+"` WHERE `uuid` = '"+uuid+"';");
                if(resultSet.next()) {
                    message = resultSet.getString("message");
                }
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            File datafile = new File("plugins/"+instance.getName()+"/"+File.separator+"/players/"+uuid+".yml");
            data = YamlConfiguration.loadConfiguration(datafile);
            if(!datafile.exists()) {
                try{
                    data.save("plugins/"+instance.getName()+"/"+ File.separator+"/players/"+uuid+".yml");
                    datafile.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }else{
                if(data.getString("access_messages")!=null) {
                    message = data.getString("message");
                }
            }
        }

        return message;
    }
    public static void deletePlayer(UUID uuid) {
        if(config.getBoolean(instance.getName()+".mysql.enabled")) {
            try {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM " + config.getString(instance.getName() + ".mysql.table") + " WHERE uuid=?");
                statement.setString(1, uuid.toString());
                statement.executeUpdate();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            File datafile = new File("plugins/"+instance.getName()+"/"+File.separator+"/players/"+uuid+".yml");
            if(datafile.exists()) {
                datafile.delete();
            }
        }
    }

}
