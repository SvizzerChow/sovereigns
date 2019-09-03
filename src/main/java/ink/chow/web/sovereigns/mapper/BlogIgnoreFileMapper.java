package ink.chow.web.sovereigns.mapper;

import ink.chow.web.sovereigns.entity.BlogIgnoreFile;

public interface BlogIgnoreFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BlogIgnoreFile record);

    int insertSelective(BlogIgnoreFile record);

    BlogIgnoreFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BlogIgnoreFile record);

    int updateByPrimaryKey(BlogIgnoreFile record);
}