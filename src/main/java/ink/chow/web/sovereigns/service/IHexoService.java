package ink.chow.web.sovereigns.service;

import ink.chow.web.sovereigns.domain.ModifyFiles;

/**
 * Description:
 *
 * @author ZhouS
 * @date 2019/1/24 22:53
 */
public interface IHexoService {
    /**
     *
     * @param basePath 更新basePath下的所有博客
     * @param files    更新对应路径的文件的博客
     */
    void updateHexo(String basePath, ModifyFiles files);

    /**
     * 更新gitPage
     */
    void pushHexoToPage();

    /**
     * 将hexo的源文件推送
     */
    void pushHexo();

    /**
     * 将博客拷贝到hexo目录
     * @param files
     */
    void copyBlogToHexo(ModifyFiles files);

    /**
     * 将博客拷贝到hexo目录
     * @param
     */
    void copyBlogToHexo(String basePath);

    void runHexoInServer();
}
