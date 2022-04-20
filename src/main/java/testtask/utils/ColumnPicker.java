package testtask.utils;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

public class ColumnPicker {
    private int columnNumber;
    private final String CONFIG_PATH = "config.yaml";
    private final String CONFIG_KEY = "columnNumber";
    private final int DEFAULT_COLUMN = 2;

    public ColumnPicker(String[] args) {
        setColumnNumber(args);
    }

    private void setColumnNumber(String[] args) {
        if(args.length == 0 || args == null)
            columnFromConfig();
        else {
            if(!args[0].matches("[0-9]")){
                System.out.println("Column must be a number. Setting column number to 2.");
                columnNumber = DEFAULT_COLUMN;
            }
            else
                columnNumber = Integer.parseInt(args[0]);
        }
    }

    private void columnFromConfig() {
        Yaml yml = new Yaml();
        try { // Read config
            File config = new File(System.getProperty("user.dir") + CONFIG_PATH);
            if(!config.exists()) {
                columnNumber = DEFAULT_COLUMN;
                return;
            }

            InputStream inputStream = this.getClass()
                    .getClassLoader()
                    .getResourceAsStream(config.toString());
            Map<String, Object> obj = yml.load(inputStream);

            if(!obj.containsKey(CONFIG_KEY)) {
                columnNumber = DEFAULT_COLUMN;
                return;
            }

            columnNumber = (int)obj.get(CONFIG_KEY); // Set column number from config
            inputStream.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public int getColumnNumber() {
        return columnNumber;
    }
}
