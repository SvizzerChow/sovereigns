package ink.chow.web.sovereigns.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * t_blog_ignore_file
 * @author 
 */
public class BlogIgnoreFile implements Serializable {
    private Integer id;

    private String fileName;

    private String md5;

    private Date dateCreate;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }
}