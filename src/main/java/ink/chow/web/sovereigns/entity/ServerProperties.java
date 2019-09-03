package ink.chow.web.sovereigns.entity;

import java.io.Serializable;

/**
 * t_server_properties
 * @author 
 */
public class ServerProperties implements Serializable {
    private Integer id;

    private String propertyKey;

    private String propertyValue;

    /**
     * 配置的类别，1 blog配置
     */
    private Integer propertyType;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPropertyKey() {
        return propertyKey;
    }

    public void setPropertyKey(String propertyKey) {
        this.propertyKey = propertyKey;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public Integer getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(Integer propertyType) {
        this.propertyType = propertyType;
    }
}