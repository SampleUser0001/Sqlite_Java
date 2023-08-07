package ittimfn.sample.sqlite.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ittimfn.sample.sqlite.model.Model;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SqliteRepositoryTest {

    private SqliteRepository repo;

    private static final Path ORIGINAL_DB_FILEPATH = Paths.get(System.getProperty("user.dir"), "src", "test", "resources", "db", "01_select.db");
    private Path testDBFilepath;

    @BeforeEach
    public void setup() throws IOException, ClassNotFoundException, SQLException {
        this.testDBFilepath = Paths.get(System.getProperty("java.io.tmpdir"), UUID.randomUUID().toString());
        Files.copy(ORIGINAL_DB_FILEPATH, this.testDBFilepath);
        this.repo = new SqliteRepository(this.testDBFilepath.toString());
    }
    
    @Test
    public void find() throws SQLException {
        Model actual = this.repo.find("02");
        Model expect = new Model("02", "value02");
        assertThat(actual, is(equalTo(expect)));
    }

    @Test
    public void notfind() throws SQLException {
        Model actual = this.repo.find("99");
        assertThat(actual, is(nullValue()));
    }

    @Test
    public void findAll() throws SQLException {
        List<Model> actual = this.repo.findAll();
        List<Model> expect = new ArrayList<Model>();
        expect.add(new Model("01","value01"));
        expect.add(new Model("02","value02"));
        expect.add(new Model("03","value03"));

        assertThat(actual.size(), is(equalTo(expect.size())));
        for(int index = 0 ; index < expect.size() ; index++) {
            assertThat(actual.get(index), is(equalTo(expect.get(index))));
        }
    }

    @Test
    public void update() throws SQLException {
        String id = "02";
        String value = "hogehoge";
        this.repo.update(id, value);

        Model actual = this.repo.find(id);
        assertThat(actual, is(equalTo(new Model(id, value))));
    }

    @Test
    public void remove() throws SQLException {
        this.repo.remove("02");

        List<Model> actual = this.repo.findAll();
        List<Model> expect = new ArrayList<Model>();
        expect.add(new Model("01", "value01"));
        expect.add(new Model("03", "value03"));
        
        assertThat(actual.size(), is(equalTo(expect.size())));
        for(int index = 0 ; index < expect.size() ; index++) {
            assertThat(actual.get(index), is(equalTo(expect.get(index))));
        }
    }

    @Test
    public void remove_notFound() throws SQLException {
        this.repo.remove("99");
        assertThat(this.repo.findAll().size(), is(3));
    }

    @Test
    public void add() throws SQLException {
        String id = this.repo.add("hoge");

        List<Model> actual = this.repo.findAll();
        List<Model> expect = new ArrayList<Model>();
        expect.add(new Model("01", "value01"));
        expect.add(new Model("02", "value02"));
        expect.add(new Model("03", "value03"));
        expect.add(new Model(id, "hoge"));
        for(int index = 0 ; index < expect.size() ; index++) {
            assertThat(actual.get(index), is(equalTo(expect.get(index))));
        }

    }


    @AfterEach
    public void teardown() throws SQLException, IOException {
        this.repo.close();
        Files.delete(this.testDBFilepath);
    }
}
