package ink.chow.web.sovereigns.ctrl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ink.chow.web.sovereigns.common.UrlConstant;

/**
 * Description: 文件上传下载
 *
 * @author ZhouS
 * @date 2018/11/1 12:34
 */
@RequestMapping(UrlConstant.FILE_CONTROLLER)
@RestController
public class FileController extends BaseController{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public Logger getLogger() {
        return logger;
    }

    @ResponseBody
    public void download(String path){



    }


}
