package ink.chow.web.sovereigns.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.function.Function;

/**
 * Description:
 *
 * @author ZhouS
 * @date 2019/1/24 15:11
 */
public class FileUtils {
    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);
    /**
     * 遍历目录下的文件
     * @param basePath
     * @param function
     * @return
     */
    public static boolean foreach(String basePath, Function<File, Boolean> function){
        File file = new File(basePath);
        return foreach(file, function);
    }

    public static boolean foreach(File file, Function<File, Boolean> function){
        if (file==null || !file.exists() || file.isHidden()){
            return false;
        }
        if (file.isDirectory()){
            File[] subFiles = file.listFiles();
            if (subFiles == null || subFiles.length ==0)
                return true;
            for (File subFile : subFiles){
                foreach(subFile, function);
            }
        }else if (file.isFile()){
            function.apply(file);
        }
        return true;
    }

    public static void copyFile(String source, String dest) throws IOException {
        logger.info("file copy:{} -> {}", source, dest);
        FileChannel sourceChannel = FileChannel.open(Paths.get(source), StandardOpenOption.READ);
        FileChannel destChannel = FileChannel.open(Paths.get(dest), StandardOpenOption.CREATE,
                StandardOpenOption.WRITE);
        sourceChannel.transferTo(0, sourceChannel.size(), destChannel);
    }

    public static String connectPath(String one, String two){
        if (StringUtils.isEmpty(one)){
            return two;
        }
        if (StringUtils.isEmpty(two)){
            return one;
        }
        boolean end = one.endsWith("/");
        boolean first = two.startsWith("/");
        if (!end && !first){
            return one +"/"+two;
        }
        if (!(end && first)){
            return one + two;
        }
        return one + two.substring(1, two.length());
    }

    public static boolean delete(String file){
        return delete(new File(file));
    }

    public static boolean delete(File file){
        if (file!=null && file.exists()){
            return file.delete();
        }
        return true;
    }
}
