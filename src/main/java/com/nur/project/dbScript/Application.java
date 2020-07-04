package com.nur.project.dbScript;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.apache.ibatis.jdbc.ScriptRunner;

public class Application {
    private static Connection connection;

    private static void runScripts(String scriptsPath) throws Exception{

        ScriptRunner scriptRunner = new ScriptRunner(connection);
        scriptRunner.setStopOnError(true);
        scriptRunner.setSendFullScript(true);

        if(scriptsPath != null){
            File scripts = new File(scriptsPath);
            if(scripts.exists()){
                if(scripts.isDirectory()){
                    for (File file : scripts.listFiles()) {
                        Application.runScripts(file.getPath());
                    }
                }else{
                    scriptRunner.runScript(new FileReader(scriptsPath));
                }
            }
        }

    }


public static void main(String[] args) {
    try(InputStream inputStream = 
    Application.class.getClassLoader().getResourceAsStream("config.properties"))
    {
        if(inputStream == null){
            System.out.println("Sorry, unable to find config.properties");
            return;
        }
        Properties properties = new Properties();
        properties.load(inputStream);

        String url = properties.getProperty("url");
        connection = DriverManager.getConnection(url, properties);

        System.out.println("Connection established .....");

        for (String command : properties.getProperty("deploymentScriptCommands").split("\\|")) {
            Application.runScripts(properties.getProperty("projectPath") + properties.getProperty(command));
        }
    }catch(Exception e){
        System.out.println("Error:" +e.getMessage());
    }
}

}