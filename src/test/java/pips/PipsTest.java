package pips;

import java.math.BigDecimal;
import org.junit.*;
import static org.junit.Assert.*;

public class PipsTest {

    @Test
    public void testFF_FFPPqq() {
        Pips pips = new Pips(25, 2, 3, 6, 2);

        assertEquals(bd("0.000000"), pips.zero);
        assertEquals(bd("0.000001"), pips.epsilon);
        assertEquals(bd("0.000025"), pips.increment);
        assertEquals(bd("0.010000"), pips.oneBigFigure);
        assertEquals(bd("0.000100"), pips.onePip);

        assertEquals(bd("56.781000"), pips.normalize(bd("56.781")));

	assertTrue(pips.isValid(bd("56.781200")));
	assertTrue(pips.isValid(bd("56.781225")));
	assertFalse(pips.isValid(bd("56.781230")));

	assertTrue(pips.isValid(bd("-56.781200")));
	assertTrue(pips.isValid(bd("-56.781225")));
	assertFalse(pips.isValid(bd("-56.781230")));

	BigDecimal price = bd("56.781250");

        assertEquals(bd("0.001250"), pips.getPips(price));
        assertEquals(bd("56.780000"), pips.getBigFigure(price));

	assertEquals(bd("56.781275"), pips.up(price));
	assertEquals(bd("56.781375"), pips.up(price, 5));

	assertEquals("down", bd("56.781225"), pips.down(price));
	assertEquals("down+", bd("56.781125"), pips.down(price, 5));

	assertEquals(bd("0.001000"), pips.expandPips("1"));
	assertEquals(bd("0.001200"), pips.expandPips("12"));
	assertEquals(bd("0.001230"), pips.expandPips("123"));
	assertEquals(bd("0.001235"), pips.expandPips("1235"));
    }

    @Test
    public void testFF_FFPP() {
        Pips pips = new Pips(1, 2, 3, 4, 0);

        assertEquals(bd("0.0001"), pips.epsilon);
        assertEquals(bd("0.0001"), pips.increment);
        assertEquals(bd("0.0001"), pips.onePip);
        assertEquals(bd("0.0100"), pips.oneBigFigure);

        assertEquals(bd("56.7810"), pips.normalize(bd("56.781")));

	BigDecimal price = bd("56.7821");

        assertEquals(bd("0.0021"), pips.getPips(price));
        assertEquals(bd("56.7800"), pips.getBigFigure(price));

	assertEquals(bd("56.7822"), pips.up(price));
	assertEquals(bd("56.7826"), pips.up(price, 5));

	assertEquals("down", bd("56.7820"), pips.down(price));
	assertEquals("down+", bd("56.7816"), pips.down(price, 5));

	assertEquals(bd("0.0010"), pips.expandPips("1"));
	assertEquals(bd("0.0012"), pips.expandPips("12"));
    }

    @Test
    public void testFF_FFPP_inc10() {
        Pips pips = new Pips(10, 2, 3, 4, 0);

        assertEquals(bd("0.0001"), pips.epsilon);
        assertEquals(bd("0.0010"), pips.increment);
        assertEquals(bd("0.0001"), pips.onePip);
        assertEquals(bd("0.0100"), pips.oneBigFigure);

        assertEquals(bd("56.7810"), pips.normalize(bd("56.781")));

        assertEquals(bd("0.0012"), pips.getPips(bd("56.7812")));
        assertEquals(bd("56.7800"), pips.getBigFigure(bd("56.7812")));

	BigDecimal price = bd("56.7821");

	assertEquals(bd("56.7831"), pips.up(price));
	assertEquals(bd("56.7871"), pips.up(price, 5));

	assertEquals("down", bd("56.7811"), pips.down(price));
	assertEquals("down+", bd("56.7771"), pips.down(price, 5));
    }



    @Test
    public void testFFPP() {
        Pips pips = new Pips(1, -3, -2, -1, 0);

        assertEquals(bd("1"), pips.epsilon);
        assertEquals(bd("1"), pips.increment);
        assertEquals(bd("1"), pips.onePip);
        assertEquals(bd("100"), pips.oneBigFigure);

        assertEquals(bd("56781"), pips.normalize(bd("56781")));

        assertEquals(bd("12"), pips.getPips(bd("567812")));
        assertEquals(bd("567800"), pips.getBigFigure(bd("567812")));

	assertEquals(bd("10"), pips.expandPips("1"));
	assertEquals(bd("12"), pips.expandPips("12"));


    }

    @Test
    public void testNegFF_FFPPqq() {
        Pips pips = new Pips(25, 2, 3, 6, 2);

        assertEquals(bd("-56.781000"), pips.normalize(bd("-56.781")));
        assertEquals(bd("0.001250"), pips.getPips(bd("-56.781250")));
        assertEquals(bd("-56.780000"), pips.getBigFigure(bd("-56.781250")));
    }


    private BigDecimal bd(String s) {
        return new BigDecimal(s);
    }
}
