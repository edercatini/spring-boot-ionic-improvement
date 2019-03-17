package com.edercatini.spring.enums;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.edercatini.spring.enums.PaymentStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentStatusTest {

    private static final Long PENDING_ID = 1L;
    private static final Long PAID_ID = 2L;
    private static final Long CANCELED_ID = 3L;

    @Test
    public void mustReturnPendingStatus() {
        assertThat(PENDING.getId(), is(equalTo(toEnum(PENDING_ID).getId())));
        assertThat(PENDING.getDescription(), is(equalTo(toEnum(PENDING_ID).getDescription())));
    }

    @Test
    public void mustReturnPaidStatus() {
        assertThat(PAID.getId(), is(equalTo(toEnum(PAID_ID).getId())));
        assertThat(PAID.getDescription(), is(equalTo(toEnum(PAID_ID).getDescription())));
    }

    @Test
    public void mustReturnCanceledStatus() {
        assertThat(CANCELED.getId(), is(equalTo(toEnum(CANCELED_ID).getId())));
        assertThat(CANCELED.getDescription(), is(equalTo(toEnum(CANCELED_ID).getDescription())));
    }
}