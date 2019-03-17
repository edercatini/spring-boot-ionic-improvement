package com.edercatini.spring.enums;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.edercatini.spring.enums.CustomerTypes.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerTypesTest {

    private static final Long LEGAL_PERSON_ID = 1L;
    private static final Long PHYSICAL_PERSON_ID = 2L;

    @Test
    public void mustReturnLegalPerson() {
        assertThat(LEGAL_PERSON.getId(), is(equalTo(toEnum(LEGAL_PERSON_ID).getId())));
        assertThat(LEGAL_PERSON.getDescription(), is(equalTo(toEnum(LEGAL_PERSON_ID).getDescription())));
    }

    @Test
    public void mustReturnPhysicalPerson() {
        assertThat(PHYSICAL_PERSON.getId(), is(equalTo(toEnum(PHYSICAL_PERSON_ID).getId())));
        assertThat(PHYSICAL_PERSON.getDescription(), is(equalTo(toEnum(PHYSICAL_PERSON_ID).getDescription())));
    }
}