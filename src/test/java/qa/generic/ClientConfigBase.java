package qa.generic;

import java.io.FileInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import org.slf4j.LoggerFactory;
import java.util.Properties;
import org.slf4j.Logger;

public abstract class ClientConfigBase implements ClientConfig
{
    private Logger logger;
    protected Properties properties;
    
    public ClientConfigBase() {
        this.logger = LoggerFactory.getLogger(ClientConfigBase.class);
        this.properties = new Properties();
    }
    
    protected void load(final String fileName) {
        this.logger.debug(String.format("look up for file: %s", fileName));
        final InputStream input = this.getClass().getClassLoader().getResourceAsStream(fileName);
        try {
            this.properties.load(input);
            this.logger.debug(String.format("file loaded successfully: %s", fileName));
        }
        catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void load(final File file) {
        this.logger.debug(String.format("look up for file: %s", file.getName()));
        InputStream input = null;
        try {
            input = new FileInputStream(file);
            this.properties.load(input);
            this.logger.debug(String.format("file loaded successfully: %s", file.getName()));
        }
        catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public String getProperty(final String propertyName) {
        if (null == this.properties) {
            return System.getProperty(propertyName, "");
        }
        return this.properties.getProperty(propertyName, "");
    }
}
