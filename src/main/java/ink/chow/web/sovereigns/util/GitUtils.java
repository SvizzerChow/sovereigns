package ink.chow.web.sovereigns.util;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import ink.chow.web.sovereigns.domain.ModifyFiles;
import org.springframework.util.StringUtils;

/**
 * Description: git
 *
 * @author ZhouS
 * @date 2019/1/24 10:56
 */
public class GitUtils {
    private static Logger logger = LoggerFactory.getLogger(GitUtils.class);

    public static void gitClone(String gitPath, String dir, String username, String password){
        logger.info("clone:{} -> {}", gitPath, dir);
        CredentialsProvider cp = null;
        if (!StringUtils.isEmpty(username)){
            cp = new UsernamePasswordCredentialsProvider( username, password );
        }
        Git git = null;
        try{
            CloneCommand cloneCommand = Git.cloneRepository()
                    //.setCredentialsProvider(cp)
                    .setURI(gitPath)
                    .setDirectory(new File(dir))
                    .setProgressMonitor(new ProgressMonitor() {
                        int now = 0;
                        int total = 0;
                        int totalTask=0;
                        String title="";
                        int taskNow=0;
                        @Override
                        public void start(int totalTasks) {
                            this.totalTask = totalTasks;
                            logger.info("clone start totalTasks:{}", totalTasks);
                        }

                        @Override
                        public void beginTask(String title, int totalWork) {
                            this.title = title;
                            this.taskNow++;
                            this.total = totalWork;
                            this.now = 0;
                            logger.info("clone beginTask title:{}, totalWork:{}", title, totalWork);
                        }

                        @Override
                        public void update(int completed) {
                            now += completed;
                            logger.info("clone {} task :{}/{}, all task:{}/{}",
                                    title, now, total, taskNow, totalTask);
                        }

                        @Override
                        public void endTask() {
                            logger.info("task end");
                        }

                        @Override
                        public boolean isCancelled() {
                            return false;
                        }
                    });
            if (cp !=null){
                cloneCommand.setCredentialsProvider(cp);
            }
            git = cloneCommand.call();
        } catch (GitAPIException e) {
            logger.error("git clone error", e);
            throw new RuntimeException(e);
        } finally {
            if (git !=null){
                git.close();
            }
        }
    }

    public static void commitAllAndPush(String dir, String username, String password){
        logger.info("commit and push:{}", dir);
        CredentialsProvider cp = null;
        if (!StringUtils.isEmpty(username)){
            cp = new UsernamePasswordCredentialsProvider( username, password );
        }
        try(Git git = Git.open(new File(dir))) {
            DirCache dirCache = git.add().addFilepattern(".").call();
            RevCommit revCommit = git.commit().setAll(true).setMessage("auto push"+new Date()).call();
            if (revCommit!=null){
                PushCommand pushCommand = git.push();
                if (cp !=null){
                    pushCommand.setCredentialsProvider(cp);
                }
                pushCommand.call();
            }
        } catch (CanceledException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException | GitAPIException e) {
            logger.info("git push error", e);
            throw new RuntimeException(e);
        }
    }

    public static void pull(String dir, String username, String password){
        logger.info("pull:{}", dir);
        CredentialsProvider cp = null;
        if (!StringUtils.isEmpty(username)){
            cp = new UsernamePasswordCredentialsProvider( username, password );
        }
        try(Git git = Git.open(new File(dir))) {
            PullCommand command = git.pull();
            if (cp != null)
                command.setCredentialsProvider(cp);
            PullResult p = command.call();
            logger.info("pull: {}", p.toString());
        } catch (CanceledException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException | GitAPIException e) {
            logger.info("git pull error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 拉取最新代码，并返回更新了哪些文件
     * 返回none则全面更新，返回空集合则不用更新
     * @param dir
     * @param username
     * @param password
     * @return
     */
    public static ModifyFiles pullReturnChange(String dir, String username, String password){
        logger.info("pull and return change:{}", dir);
        CredentialsProvider cp = null;
        if (!StringUtils.isEmpty(username)){
            cp = new UsernamePasswordCredentialsProvider( username, password );
        }
        try(Git git = Git.open(new File(dir))) {
            RevCommit revCommit = showHead(git);
            PullCommand command = git.pull();
            if (cp != null)
                command.setCredentialsProvider(cp);
            PullResult p = command.call();
            logger.info("pull:{}", p.toString());
            if (revCommit == null){         // 之前没有提交记录
                return null;
            }
            RevCommit newRevCommit = showHead(git);
            if (newRevCommit==null || revCommit.equals(newRevCommit)) {
                return new ModifyFiles(); // 拉取后没有变化
            }
            List<DiffEntry> diff = git.diff()
                    .setOldTree(getAbstractTreeIterator(revCommit, git.getRepository()))
                    .setNewTree(getAbstractTreeIterator(newRevCommit, git.getRepository()))
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
                        logger.info("delete file:{} -> {}",
                                entry.getOldPath(), entry.getNewPath());
                        deleteFileNames.add(entry.getOldPath());
                        break;
                }
            }

            return new ModifyFiles(updateFileNames, deleteFileNames);
        } catch (CanceledException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException | GitAPIException e) {
            logger.info("git pull error", e);
            throw new RuntimeException(e);
        }
    }
    /**
     * 获取最后的提交记录
     * @param git
     * @return
     * @throws GitAPIException
     */
    public static RevCommit showHead(Git git) throws GitAPIException{
        Iterable<RevCommit> it = git.log().setMaxCount(10).call();
        if (it!=null  ){
            Iterator<RevCommit> iterator = it.iterator();
            if (iterator.hasNext()){
                return iterator.next();
            }
        }
        return null;
    }

    public static AbstractTreeIterator getAbstractTreeIterator(RevCommit commit, Repository repository ){
        RevWalk revWalk=new RevWalk(repository);
        CanonicalTreeParser treeParser=null;
        try {
            RevTree revTree=revWalk.parseTree(commit.getTree().getId());
            logger.info("commit id:{}", commit.getTree().getId());
            treeParser=new CanonicalTreeParser();
            treeParser.reset(repository.newObjectReader(),revTree.getId());
            revWalk.dispose();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return treeParser;
    }
}
