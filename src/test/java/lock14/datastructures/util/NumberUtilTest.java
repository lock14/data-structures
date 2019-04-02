package lock14.datastructures.util;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;

public class NumberUtilTest {

    @Test
    public void zeroTest() {
        assertEquals(Byte.valueOf((byte)0), NumberUtil.zero(Byte.class));
        assertEquals(Short.valueOf((short)0), NumberUtil.zero(Short.class));
        assertEquals(Integer.valueOf(0), NumberUtil.zero(Integer.class));
        assertEquals(Long.valueOf(0L), NumberUtil.zero(Long.class));
        assertEquals(Float.valueOf((float)0.0), NumberUtil.zero(Float.class));
        assertEquals(Double.valueOf(0.0), NumberUtil.zero(Double.class));
        assertEquals(BigInteger.ZERO, NumberUtil.zero(BigInteger.class));
        assertEquals(BigDecimal.ZERO, NumberUtil.zero(BigDecimal.class));
    }
}
