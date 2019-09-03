package ink.chow.web.sovereigns.service;

import java.io.File;

import ink.chow.web.sovereigns.util.GitUtils;

/**
 * Description:
 *
 * @author ZhouS
 * @date 2019/1/24 10:30
 */
public interface IBlogService {
    /**
     * 更新博客到站点
     * @param updateRange 0默认更新，1拉取最新数据且更新，2不拉取新数据更新
     */
    void updateBlogToSite(int updateRange);
}
