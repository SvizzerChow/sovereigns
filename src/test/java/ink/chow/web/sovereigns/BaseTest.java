package ink.chow.web.sovereigns;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ink.chow.web.sovereigns.util.GitUtils;


/**
 * Description:
 *
 * @author ZhouS
 * @date 2019/3/4 9:37
 */
public class BaseTest {

    /*@Test
    public void test() throws IOException, GitAPIException {
        String path = "D:\\workspace\\Java\\note";
        Git git = Git.open(new File(path));
        Iterable<RevCommit> it = git.log().all().call();
        RevCommit revCommit = null;
        RevCommit revCommit2 = null;
        if (it!=null ){
            int i = 0;
            Iterator iterator = it.iterator();
            while(iterator.hasNext()){

                RevCommit t = (RevCommit) iterator.next();
                if (t !=null && t.getTree()!=null){
                    System.out.println(t.getTree().getId());

                    if (i == 0){
                        revCommit = t;
                    }
                    if (i == 1){
                        revCommit2 = t;
                    }
                    ++i;
                }else{
                    System.out.println(t);
                }
            }
        }
        List<DiffEntry> diff = git.diff()
                .setOldTree(GitUtils.getAbstractTreeIterator(revCommit2, git.getRepository()))
                .setNewTree(GitUtils.getAbstractTreeIterator(revCommit, git.getRepository()))
                .call();
        List<String> updateFileNames = new ArrayList<>();
        List<String> deleteFileNames = new ArrayList<>();
        for (DiffEntry entry : diff){       // 获取更新的文件名
            switch (entry.getChangeType()){
                case ADD:
                case COPY:
                case MODIFY:
                    updateFileNames.add(entry.getNewPath());
                    break;
                case RENAME:
                    updateFileNames.add(entry.getNewPath());
                    deleteFileNames.add(entry.getOldPath());
                    break;
                case DELETE:
                    deleteFileNames.add(entry.getOldPath());
                    break;
            }
        }
        System.out.println(updateFileNames);
        System.out.println(deleteFileNames);
    }*/

}
