package ink.chow.web.sovereigns.util;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteStreamHandler;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Description:
 *
 * @author ZhouS
 * @date 2019/1/25 10:21
 */
public class CommandUtils {
    private static Logger logger = LoggerFactory.getLogger(CommandUtils.class);

    public static boolean run(String dir, String cmd, String... args) {
        logger.info("cmd: {}, args:{}", cmd, args);
        boolean success = true;
        CommandLine commandLine = new CommandLine(cmd);
        commandLine.addArguments(args);
        DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();

        ExecuteWatchdog watchdog = new ExecuteWatchdog(10*60*1000);
        Executor executor = new DefaultExecutor();
        executor.setStreamHandler(new LogBackExecuteStreamHandler());
        if (!StringUtils.isEmpty(dir)) {
            executor.setWorkingDirectory(new File(dir));
        }
        //executor.setExitValue(0);
        executor.setWatchdog(watchdog);
        try {
            executor.execute(commandLine, resultHandler);
            resultHandler.waitFor();
        } catch (Exception e) {
            success = false;
            logger.error("命令行执行失败", e);
        }
        return success;
    }

    public static class LogBackExecuteStreamHandler implements ExecuteStreamHandler {

        @Override
        public void setProcessInputStream(OutputStream os) throws IOException {

        }

        @Override
        public void setProcessErrorStream(InputStream is) throws IOException {
            logger.info("cmd error output:{}", IOUtils.toString(is, "utf-8"));
        }

        @Override
        public void setProcessOutputStream(InputStream is) throws IOException {
            logger.info("cmd output:{}", IOUtils.toString(is, "utf-8"));
        }

        @Override
        public void start() throws IOException {
            logger.info("cmd start");
        }

        @Override
        public void stop() throws IOException {
            logger.info("cmd stop");
        }
    }
}
