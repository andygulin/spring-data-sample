package spring.data.sample;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Pattern;

public class GridFsTest extends BaseTest {

    @Test
    public void store() throws IOException {
        File file = new File("C:/Windows/notepad.exe");
        InputStream is = FileUtils.openInputStream(file);
        String contentType = "";
        DBObject dbObject = new BasicDBObject();
        GridFSFile fsFile = gridFsTemplate.store(is, file.getName(),
                contentType, dbObject);
        System.out.println(fsFile);
    }

    @Test
    public void storeFile() throws IOException {
        InputStream is;
        File dir = new File("C:\\Windows");
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                try {
                    is = FileUtils.openInputStream(file);
                } catch (Exception e) {
                    continue;
                }
                GridFSFile fsFile = gridFsTemplate.store(is, file.getName());
                System.out.println(fsFile);
            }
        }
    }

    @Test
    public void find() {
        String queryFileName = ".dll";
        long queryLength = 800608L;
        Pattern pattern = Pattern.compile("^.*" + queryFileName + ".*$",
                Pattern.CASE_INSENSITIVE);
        List<GridFSDBFile> files = gridFsTemplate
                .find(new Query(Criteria.where("filename").regex(pattern)
                        .and("length").gt(queryLength)));
        for (GridFSDBFile file : files) {
            System.out.println(file);
        }
    }

    @Test
    public void findOne() {
        GridFSDBFile file = gridFsTemplate.findOne(new Query(Criteria.where(
                "filename").is("notepad.exe")));
        System.out.println(file);
    }
}
