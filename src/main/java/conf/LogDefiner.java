package conf;

import ch.qos.logback.core.PropertyDefinerBase;

import java.io.File;

/**
 * @className: LogPathDefiner
 * @description: 日志路径
 * @author: Aim
 * @date: 2023/3/29
 **/
public class LogDefiner extends PropertyDefinerBase {

    @Override
    public String getPropertyValue() {
        String LogPath = System.getProperty("user.dir");
        String dirPath = LogPath + File.separator + "target";
        File file = new File(dirPath);
        if (file.exists()) {
            LogPath = dirPath;
        }
        LogPath = LogPath + File.separator + "logs";
        System.out.println(" - 日志存放路径: " + LogPath);
        return LogPath;
    }
}