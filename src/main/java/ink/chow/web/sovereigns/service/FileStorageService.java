package ink.chow.web.sovereigns.service;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Description:
 *
 * @author ZhouS
 * @date 2019/3/5 23:10
 */
public interface FileStorageService {

    String saveFile(InputStream inputStream);

    OutputStream readFile(String fileName);
}
