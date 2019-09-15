package ink.chow.web.sovereigns.ctrl;

import ink.chow.web.sovereigns.util.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import ink.chow.web.sovereigns.common.UrlConstant;
import ink.chow.web.sovereigns.domain.dto.DtoResult;
import ink.chow.web.sovereigns.service.IBlogService;

/**
 * Description:
 *
 * @author ZhouS
 * @date 2019/1/24 13:56
 */
@RestController
@RequestMapping(UrlConstant.WEBHOOK_CONTROLLER)
public class WebHookController extends BaseController{
    private Logger logger = LoggerFactory.getLogger(WebHookController.class);
    @Autowired
    private IBlogService blogService;

    @RequestMapping(UrlConstant.METHOD_BLOG)
    public DtoResult<String> blogWebHook(@RequestParam(required = false, defaultValue = "0") int updateRange,
                                         HttpServletRequest request) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuilder builder = new StringBuilder();
        int i = -1;
        char[] c = new char[1024];
        while ((i=reader.read(c)) != -1){
            builder.append(c);
        }
        logger.info("webhook: {}",builder.toString());
        String uuid = UUID.randomUUID().toString();
        logger.info("uuid:{} {}", uuid, updateRange);
        ThreadUtils.submitByOneThread(()->{
            try {
                blogService.updateBlogToSite(updateRange);
            }catch (Exception e){
                logger.error("同步异常", e);
            }
        });
        return DtoResult.success(uuid);
    }

    @Override
    public Logger getLogger() {
        return logger;
    }
}
