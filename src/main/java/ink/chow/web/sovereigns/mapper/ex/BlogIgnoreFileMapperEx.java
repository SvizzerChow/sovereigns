package ink.chow.web.sovereigns.mapper.ex;

import org.springframework.stereotype.Repository;

import java.util.List;

import ink.chow.web.sovereigns.entity.BlogIgnoreFile;

@Repository
public interface BlogIgnoreFileMapperEx {

    List<BlogIgnoreFile> list();
}