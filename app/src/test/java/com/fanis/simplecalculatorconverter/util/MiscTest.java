package com.fanis.simplecalculatorconverter.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MiscTest {

    //TODO find about NullPointerEx
    @Test
    public void getDateTest(){
        Assert.assertEquals("Success scenario when date 1573231596 entered and got 08-11-2019 16:39:06", "08-11-2019 16:39:06",
                Miscellaneous.getDate(1573231596));
    }

    @Test
    public void roundDoublesTest() {
        Assert.assertEquals("Success scenario when rounded value is 1.09=>1.0", 1.0, Miscellaneous.roundDouble(1.09, 2), 00.1);
        Assert.assertEquals("Success scenario when rounded value is 0.0.9950=>1.0", 1.0, Miscellaneous.roundDouble(0.9950, 2), 0.01);
        Assert.assertNotEquals("Fail scenario when rounded value is 0.9949=>1.0", 1.0, Miscellaneous.roundDouble(0.9949, 2), 0.01);
        Assert.assertNotEquals("Fail scenario when rounded value is 0.1.10=>1.0", 1.0, Miscellaneous.roundDouble(1.10, 2), 0.01);

    }

}