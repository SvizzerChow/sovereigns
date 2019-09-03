package ink.chow.web.sovereigns.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

import ink.chow.web.sovereigns.common.BlogUpdateRangeEnum;
import ink.chow.web.sovereigns.config.blog.BlogProperties;
import ink.chow.web.sovereigns.config.blog.GitAccount;
import ink.chow.web.sovereigns.domain.ModifyFiles;
import ink.chow.web.sovereigns.service.GitAccountService;
import ink.chow.web.sovereigns.service.IBlogService;
import ink.chow.web.sovereigns.service.IHexoService;
import ink.chow.web.sovereigns.util.GitUtils;

import static ink.chow.web.sovereigns.common.UrlConstant.PROPERTIES_TYPE_GITHUB;

@Service("GithubPageBlogService")
public class GithubPageBlogServiceImpl implements IBlogService {
    @Autowired
    private BlogProperties blogProperties;
    @Autowired
    private GitAccountService gitAccountService;
    @Autowired
    private IHexoService hexoService;
    private Logger logger = LoggerFactory.getLogger(GithubPageBlogServiceImpl.class);

    private boolean init(){
        boolean isClone = false;
        File file = new File(blogProperties.getPath());
        if (!file.exists()){
            GitAccount gitAccount = gitAccountService.getAccount(PROPERTIES_TYPE_GITHUB);
            GitUtils.gitClone(blogProperties.getMarkdownGit(), blogProperties.getPath(),
                    gitAccount.getUsername() , gitAccount.getPassword());
            isClone = true;
        }
        return isClone;
    }


    @Override
    public void updateBlogToSite(int updateRange) {
        logger.info("开始同步");
        ModifyFiles updateFiles = null;
        // 更新到最新
        if (updateRange == BlogUpdateRangeEnum.UPDATE_LATEST.getValue() ||
            updateRange == BlogUpdateRangeEnum.UPDATE_ALL_LATEST.getValue()){
            if (!init()) {
                GitAccount gitAccount = gitAccountService.getAccount(PROPERTIES_TYPE_GITHUB);
                updateFiles = GitUtils.pullReturnChange(blogProperties.getPath()
                        , gitAccount.getUsername(), gitAccount.getPassword());
                logger.info("update files:{}", updateFiles!=null?updateFiles.display():"");
                // 空集合代表不用更新
                if (updateFiles != null && updateFiles.isEmpty() &&
                        updateRange == BlogUpdateRangeEnum.UPDATE_LATEST.getValue()) {
                    logger.info("不用更新");
                    return;
                }
            }
        }
        hexoService.updateHexo(blogProperties.getPath(), updateFiles);
        logger.info("结束同步");
    }

}
