package guru.springframework.repositories.reactive;

import guru.springframework.domain.Category;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryReactiveRepositoryTest extends TestCase {

    private final String FOO = "Foo";
    @Autowired
    CategoryReactiveRepository repository;

    @Before
    public void setUp() throws Exception {
        repository.deleteAll().block();
    }

    @Test
    public void testCategorySave() throws Exception{
        Category category = new Category();
        category.setDescription(FOO);

        repository.save(category).block();

        Long count = repository.count().block();

        assertEquals(Long.valueOf(1L), count);
    }

    @Test
    public void testFindByDescription() {
        Category category = new Category();
        category.setDescription(FOO);

        repository.save(category).then().block();

        Category fetchedCat = repository.findByDescription(FOO).block();

        assertNotNull(fetchedCat.getId());
    }
}