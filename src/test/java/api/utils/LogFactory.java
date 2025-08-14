package api.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogFactory {

    private static final Logger logger = LogManager.getLogger(LogFactory.class);

    public static Logger getLogger(){
        return logger;
    }

}
