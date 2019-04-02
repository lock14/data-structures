package lock14.datastructures.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public final class NumberUtil {

    // make utility class non-instantiable
    private NumberUtil() {
    }

    @SuppressWarnings("unchecked")
    public static <N extends Number & Comparable<N>> N add(N a, N b) {
        if (a instanceof Byte) {
            byte x = a.byteValue();
            byte y = b.byteValue();
            return (N) Byte.valueOf((byte) (x + y));
        } else if (a instanceof Short) {
            short x = a.shortValue();
            short y = b.shortValue();
            return (N) Short.valueOf((short) (x + y));
        } else if (a instanceof Integer) {
            int x = a.intValue();
            int y = b.intValue();
            return (N) Integer.valueOf(x + y);
        } else if (a instanceof Long) {
            long x = a.longValue();
            long y = b.longValue();
            return (N) Long.valueOf(x + y);
        } else if (a instanceof Float) {
            float x = a.floatValue();
            float y = b.floatValue();
            return (N) Float.valueOf(x + y);
        } else if (a instanceof Double) {
            double x = a.doubleValue();
            double y = b.doubleValue();
            return (N) Double.valueOf(x + y);
        } else if (a instanceof BigInteger) {
            BigInteger x = (BigInteger) a;
            BigInteger y = (BigInteger) b;
            return (N) x.add(y);
        } else if (a instanceof BigDecimal) {
            BigDecimal x = (BigDecimal) a;
            BigDecimal y = (BigDecimal) b;
            return (N) x.add(y);
        }
        throw new UnsupportedOperationException("add not supported for " + a + ", " + b);
    }

    @SuppressWarnings("unchecked")
    public static <N extends Number & Comparable<N>> N subtract(N a, N b) {
        if (a instanceof Byte) {
            byte x = a.byteValue();
            byte y = b.byteValue();
            return (N) Byte.valueOf((byte) (x - y));
        } else if (a instanceof Short) {
            short x = a.shortValue();
            short y = b.shortValue();
            return (N) Short.valueOf((short) (x - y));
        } else if (a instanceof Integer) {
            int x = a.intValue();
            int y = b.intValue();
            return (N) Integer.valueOf(x - y);
        } else if (a instanceof Long) {
            long x = a.longValue();
            long y = b.longValue();
            return (N) Long.valueOf(x - y);
        } else if (a instanceof Float) {
            float x = a.floatValue();
            float y = b.floatValue();
            return (N) Float.valueOf(x - y);
        } else if (a instanceof Double) {
            double x = a.doubleValue();
            double y = b.doubleValue();
            return (N) Double.valueOf(x - y);
        } else if (a instanceof BigInteger) {
            BigInteger x = (BigInteger) a;
            BigInteger y = (BigInteger) b;
            return (N) x.subtract(y);
        } else if (a instanceof BigDecimal) {
            BigDecimal x = (BigDecimal) a;
            BigDecimal y = (BigDecimal) b;
            return (N) x.subtract(y);
        }
        throw new UnsupportedOperationException("subtract not supported for " + a + ", " + b);
    }

    @SuppressWarnings("unchecked")
    public static <N extends Number & Comparable<N>> N multiply(N a, N b) {
        if (a instanceof Byte) {
            byte x = a.byteValue();
            byte y = b.byteValue();
            return (N) Byte.valueOf((byte) (x * y));
        } else if (a instanceof Short) {
            short x = a.shortValue();
            short y = b.shortValue();
            return (N) Short.valueOf((short) (x * y));
        } else if (a instanceof Integer) {
            int x = a.intValue();
            int y = b.intValue();
            return (N) Integer.valueOf(x * y);
        } else if (a instanceof Long) {
            long x = a.longValue();
            long y = b.longValue();
            return (N) Long.valueOf(x * y);
        } else if (a instanceof Float) {
            float x = a.floatValue();
            float y = b.floatValue();
            return (N) Float.valueOf(x * y);
        } else if (a instanceof Double) {
            double x = a.doubleValue();
            double y = b.doubleValue();
            return (N) Double.valueOf(x * y);
        } else if (a instanceof BigInteger) {
            BigInteger x = (BigInteger) a;
            BigInteger y = (BigInteger) b;
            return (N) x.multiply(y);
        } else if (a instanceof BigDecimal) {
            BigDecimal x = (BigDecimal) a;
            BigDecimal y = (BigDecimal) b;
            return (N) x.multiply(y);
        }
        throw new UnsupportedOperationException("multiply not supported for " + a + ", " + b);
    }

    @SuppressWarnings("unchecked")
    public static <N extends Number & Comparable<N>> N divide(N a, N b) {
        if (a instanceof Byte) {
            byte x = a.byteValue();
            byte y = b.byteValue();
            return (N) Byte.valueOf((byte) (x / y));
        } else if (a instanceof Short) {
            short x = a.shortValue();
            short y = b.shortValue();
            return (N) Short.valueOf((short) (x / y));
        } else if (a instanceof Integer) {
            int x = a.intValue();
            int y = b.intValue();
            return (N) Integer.valueOf(x / y);
        } else if (a instanceof Long) {
            long x = a.longValue();
            long y = b.longValue();
            return (N) Long.valueOf(x / y);
        } else if (a instanceof Float) {
            float x = a.floatValue();
            float y = b.floatValue();
            return (N) Float.valueOf(x / y);
        } else if (a instanceof Double) {
            double x = a.doubleValue();
            double y = b.doubleValue();
            return (N) Double.valueOf(x / y);
        } else if (a instanceof BigInteger) {
            BigInteger x = (BigInteger) a;
            BigInteger y = (BigInteger) b;
            return (N) x.divide(y);
        } else if (a instanceof BigDecimal) {
            BigDecimal x = (BigDecimal) a;
            BigDecimal y = (BigDecimal) b;
            return (N) x.divide(y, RoundingMode.HALF_EVEN);
        }
        throw new UnsupportedOperationException("divide not supported for " + a + ", " + b);
    }

    @SuppressWarnings("unchecked")
    public static <N extends Number & Comparable<N>> N zero(Class<N> nClass) {
        if (nClass.equals(Byte.class)) {
            return (N) Byte.valueOf((byte) 0);
        } else if (nClass.equals(Short.class)) {
            return (N) Short.valueOf((short) 0);
        } else if (nClass.equals(Integer.class)) {
            return (N) Integer.valueOf(0);
        } else if (nClass.equals(Long.class)) {
            return (N) Long.valueOf(0L);
        } else if (nClass.equals(Float.class)) {
            return (N) Float.valueOf((float) 0.0);
        } else if (nClass.equals(Double.class)) {
            return (N) Double.valueOf(0.0);
        } else if (nClass.equals(BigInteger.class)) {
            return (N) BigInteger.ZERO;
        } else if (nClass.equals(BigDecimal.class)) {
            return (N) BigDecimal.ZERO;
        }
        throw new IllegalArgumentException("zero type not supported for class " + nClass);
    }

    @SuppressWarnings("unchecked")
    public static <N extends Number & Comparable<N>> N one(Class<N> nClass) {
        if (nClass.equals(Byte.class)) {
            return (N) Byte.valueOf((byte) 1);
        } else if (nClass.equals(Short.class)) {
            return (N) Short.valueOf((short) 1);
        } else if (nClass.equals(Integer.class)) {
            return (N) Integer.valueOf(1);
        } else if (nClass.equals(Long.class)) {
            return (N) Long.valueOf(1L);
        } else if (nClass.equals(Float.class)) {
            return (N) Float.valueOf((float) 1.0);
        } else if (nClass.equals(Double.class)) {
            return (N) Double.valueOf(1.0);
        } else if (nClass.equals(BigInteger.class)) {
            return (N) BigInteger.ONE;
        } else if (nClass.equals(BigDecimal.class)) {
            return (N) BigDecimal.ONE;
        }
        throw new IllegalArgumentException("zero type not supported for class " + nClass);
    }

    @SuppressWarnings("unchecked")
    public static <N extends Number & Comparable<N>> N ten(Class<N> nClass) {
        if (nClass.equals(Byte.class)) {
            return (N) Byte.valueOf((byte) 10);
        } else if (nClass.equals(Short.class)) {
            return (N) Short.valueOf((short) 10);
        } else if (nClass.equals(Integer.class)) {
            return (N) Integer.valueOf(10);
        } else if (nClass.equals(Long.class)) {
            return (N) Long.valueOf(10L);
        } else if (nClass.equals(Float.class)) {
            return (N) Float.valueOf((float) 10.0);
        } else if (nClass.equals(Double.class)) {
            return (N) Double.valueOf(10.0);
        } else if (nClass.equals(BigInteger.class)) {
            return (N) BigInteger.TEN;
        } else if (nClass.equals(BigDecimal.class)) {
            return (N) BigDecimal.TEN;
        }
        throw new IllegalArgumentException("zero type not supported for class " + nClass);
    }
}
