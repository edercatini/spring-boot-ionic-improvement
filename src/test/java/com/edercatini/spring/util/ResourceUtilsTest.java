package com.edercatini.spring.util;

import com.edercatini.spring.ComponentTest;
import com.edercatini.spring.model.Category;
import org.junit.Test;

import java.net.URI;

import static com.edercatini.spring.dataBuilder.domain.CategoryDataBuilder.anObject;
import static com.edercatini.spring.util.ResourceUtils.createUri;
import static com.edercatini.spring.util.ResourceUtils.setResponse;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class ResourceUtilsTest extends ComponentTest {

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