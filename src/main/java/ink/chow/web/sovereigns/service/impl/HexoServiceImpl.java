package ink.chow.web.sovereigns.service.impl;

import com.alibaba.fastjson.JSON;
import io.micrometer.core.instrument.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import ink.chow.web.sovereigns.config.blog.BlogProperties;
import ink.chow.web.sovereigns.config.blog.GitAccount;
import ink.chow.web.sovereigns.domain.ModifyFiles;
import ink.chow.web.sovereigns.entity.BlogIgnoreFile;
import ink.chow.web.sovereigns.mapper.ex.BlogIgnoreFileMapperEx;
import ink.chow.web.sovereigns.service.GitAccountService;
import ink.chow.web.sovereigns.service.IHexoService;
import ink.chow.web.sovereigns.util.CommandUtils;
import ink.chow.web.sovereigns.util.FileUtils;
import ink.chow.web.sovereigns.util.GitUtils;
import org.springframework.util.CollectionUtils;

import static ink.chow.web.sovereigns.common.UrlConstant.PROPERTIES_TYPE_GITHUB;

@Service("HexoService")
public class HexoServiceImpl implements IHexoService {
    @Autowired
    private BlogProperties blogProperties;
    @Autowired
    private GitAccountService gitAccountService;
    @Autowired
    private BlogIgnoreFileMapperEx blogIgnoreFileMapperEx;

    private String POST_PATH = "source/_posts/";
    private String deployDir = ".deploy_git";
    private Logger logger = LoggerFactory.getLogger(HexoServiceImpl.class);

    private boolean init(){
        File file = new File(blogProperties.getHexo());
        boolean isClone = false;
        GitAccount gitAccount = gitAccountService.getAccount(PROPERTIES_TYPE_GITHUB);
        if (!file.exists()){
            GitUtils.gitClone(blogProperties.getGithubPageHexoGit(), blogProperties.getHexo(),
                    gitAccount.getUsername(), gitAccount.getPassword());
            if(CommandUtils.run(blogProperties.getHexo(), "npm", "install", "hexo", "--save")){
                if(CommandUtils.run(blogProperties.getHexo(), "npm", "install", "--unsafe-perm")){
                    //npm install hexo-deployer-git --save
                    CommandUtils.run(blogProperties.getHexo(), "npm", "install", "hexo-deployer-git", "--save");
                }
            }
            FileUtils.delete(FileUtils.connectPath(blogProperties.getHexo(), deployDir));
            isClone = true;
        }else {
            GitUtils.pull(blogProperties.getHexo(), gitAccount.getUsername(), gitAccount.getPassword());
        }
        return isClone;
    }

    /**
     *
     * @param basePath 更新basePath下的所有博客
     * @param files    更新对应路径的文件的博客
     */
    @Override
    public void updateHexo(String basePath, ModifyFiles files) {
        this.init();
        if (files!=null && !files.isEmpty()){
            this.copyBlogToHexo(files);
        }else if (StringUtils.isNotEmpty(basePath)){
            this.copyBlogToHexo(basePath);
        }else {
            return;
        }
        this.pushHexoToPage();
        this.pushHexo();
    }

    @Override
    public void pushHexoToPage() {
        logger.info("hexo push to page:{}", blogProperties.getHexo());
        if (CommandUtils.run(blogProperties.getHexo(), "hexo", "clean")){
            if (CommandUtils.run(blogProperties.getHexo(), "hexo", "g")){
                CommandUtils.run(blogProperties.getHexo(), "hexo","d");
                logger.info("blog更新到hexo");
            }
        }
    }

    @Override
    public void pushHexo() {
        GitAccount gitAccount = gitAccountService.getAccount(PROPERTIES_TYPE_GITHUB);
        GitUtils.commitAllAndPush(blogProperties.getHexo(),
                gitAccount.getUsername(), gitAccount.getPassword());
    }

    @Override
    public void copyBlogToHexo(ModifyFiles files) {
        if (files==null || files.isEmpty())
            return;
        Map<String, BlogIgnoreFile> ignoreFileMap = ignoreFileMap();
        for (String file : files.getUpdateFiles()){
            File fileObj = new File(FileUtils.connectPath(blogProperties.getPath(),
                    file));
            String fileName = fileObj.getName();
            if (needCopy(ignoreFileMap, fileName)) {
                copyFile(fileObj.getAbsolutePath(), fileName);
            }
        }
        for (String file : files.getDeleteFiles()){
            File fileObj = new File(file);
            String fileName = fileObj.getName();
            this.deleteFile(fileName);
        }
    }

    @Override
    public void copyBlogToHexo(String basePath) {
        if(StringUtils.isEmpty(basePath))
            return;
        Map<String, BlogIgnoreFile> ignoreFileMap = ignoreFileMap();
        FileUtils.foreach(basePath, file ->
             !needCopy(ignoreFileMap, file.getName())
                    || copyFile(file.getAbsolutePath(), file.getName())
        );
    }

    @Override
    public void runHexoInServer() {

    }

    private boolean needCopy(Map<String, BlogIgnoreFile> ignoreFileMap, String fileName){
        if (ignoreFileMap!=null && ignoreFileMap.get(fileName)!=null){
            return false;
        }
        if (fileName.startsWith("NBlog-")){
            return false;
        }
        return true;
    }

    /**
     *
     * @param allFilePath 带路径和文件名
     * @param fileName 文件名
     * @return
     */
    private boolean copyFile(String allFilePath, String fileName){
        boolean update = false;
        try {
            FileUtils.copyFile(allFilePath, FileUtils.connectPath(
                    FileUtils.connectPath(blogProperties.getHexo(), POST_PATH), fileName));
            update = true;
        } catch (IOException e) {
            logger.error("拷贝文件异常", e);
            throw new RuntimeException(e);
        }
        return update;
    }

    private boolean deleteFile(String fileName){
        logger.info("hexo delete:{}", fileName);
        return FileUtils.delete(FileUtils.connectPath(
                FileUtils.connectPath(blogProperties.getHexo(), POST_PATH), fileName));
    }


    private Map<String, BlogIgnoreFile> ignoreFileMap(){
        List<BlogIgnoreFile> list = blogIgnoreFileMapperEx.list();
        logger.info("blog ignore files:{}", JSON.toJSONString(list));
        if (!CollectionUtils.isEmpty(list)){
            return list.stream().collect(Collectors.toMap(BlogIgnoreFile::getFileName, b->b, (k1, k2)->k1));
        }
        return null;
    }
}
