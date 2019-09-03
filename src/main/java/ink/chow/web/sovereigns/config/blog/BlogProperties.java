package ink.chow.web.sovereigns.config.blog;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author ZhouS
 * @date 2019/1/24 14:26
 */
@Component
@ConfigurationProperties(prefix = "blog")
public class BlogProperties {
    private String markdownGit;
    private String githubPageGit;
    private String githubPageHexoGit;
    private String basePath;
    private String path;
    private String hexo;


    public String getMarkdownGit() {
        return markdownGit;
    }

    public void setMarkdownGit(String markdownGit) {
        this.markdownGit = markdownGit;
    }

    public String getGithubPageGit() {
        return githubPageGit;
    }

    public void setGithubPageGit(String githubPageGit) {
        this.githubPageGit = githubPageGit;
    }

    public String getGithubPageHexoGit() {
        return githubPageHexoGit;
    }

    public void setGithubPageHexoGit(String githubPageHexoGit) {
        this.githubPageHexoGit = githubPageHexoGit;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHexo() {
        return hexo;
    }

    public void setHexo(String hexo) {
        this.hexo = hexo;
    }
}
