package ink.chow.web.sovereigns.domain;

import org.springframework.util.CollectionUtils;

import java.util.List;


/**
 * Description:
 *
 * @author ZhouS
 * @date 2019/2/27 10:12
 */
public class ModifyFiles {
    private List<String> updateFiles;
    private List<String> deleteFiles;

    public ModifyFiles() {
    }

    public ModifyFiles(List<String> updateFiles, List<String> deleteFiles) {
        this.updateFiles = updateFiles;
        this.deleteFiles = deleteFiles;
    }

    public List<String> getUpdateFiles() {
        return updateFiles;
    }

    public void setUpdateFiles(List<String> updateFiles) {
        this.updateFiles = updateFiles;
    }

    public List<String> getDeleteFiles() {
        return deleteFiles;
    }

    public void setDeleteFiles(List<String> deleteFiles) {
        this.deleteFiles = deleteFiles;
    }

    public boolean isEmpty(){
        return CollectionUtils.isEmpty(updateFiles) &
                CollectionUtils.isEmpty(deleteFiles);
    }
    public String display(){
        return "update: "+updateFiles+"\n"+
                "delete: "+deleteFiles;
    }

}
