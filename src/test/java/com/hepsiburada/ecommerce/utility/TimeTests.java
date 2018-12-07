package com.hepsiburada.ecommerce.utility;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TimeTests {


    @Test(expected = InvalidTimeException.class)
    public void increase_whenTimeIsNegativeThenShouldThrowInvalidTimeException() throws InvalidTimeException {
        Time.increase(-5);
    }

    @Test(expected = InvalidTimeException.class)
    public void increase_whenTimeSumIsBigggerThan24ThenShouldThrowInvalidTimeException() throws InvalidTimeException {
        Time.increase(26);
    }

    @Test
    public void increase_whenIncreaseAmountIsValidThenShouldIncreaseTime() throws InvalidTimeException {
        Time.increase(4L);
        Assert.assertEquals(Time.getTime(), 4L);
    }
}
