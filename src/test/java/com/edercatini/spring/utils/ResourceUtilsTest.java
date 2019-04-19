package com.edercatini.spring.utils;

import com.edercatini.spring.model.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

import static com.edercatini.spring.builder.domain.CategoryDataBuilder.anObject;
import static com.edercatini.spring.utils.ResourceUtils.createUri;
import static com.edercatini.spring.utils.ResourceUtils.setResponse;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResourceUtilsTest {

    private static final Long ID = 1L;
    private static final String URI_TO_COMPARE = "/category/" + ID;

    @Test
    public void mustReturnResourceURI() {
        Category category = anObject().build();
        category.setId(ID);
        URI uri = createUri(category);
        assertThat(uri.getPath(), is(equalTo(URI_TO_COMPARE)));
    }

    @Test
    public void mustReturnACustomResponse() {
        Category category = anObject().build();
        URI uri = createUri(category);
        assertThat(setResponse(category, uri).getEntity(), is(instanceOf(Category.class)));
        assertThat(setResponse(category, uri).getResourceUri(), is(equalTo(uri)));
    }
}