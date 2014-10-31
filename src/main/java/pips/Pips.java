package pips;

import java.math.BigDecimal;
import java.util.Arrays;

/*
  To Do:
  Fractions
  String 123 -> 12.30
  Create BF,PIP. Handle (0, -2) (bf(0) - pip(-2))
  Also String "-0.012" -> (0, -12) ?
*/

public class Pips {
    public final BigDecimal zero;
    public final BigDecimal epsilon;
    public final BigDecimal increment;
    public final BigDecimal oneBigFigure;
    public final BigDecimal onePip;

    private final int peExp;
    private final int psExp;
    private final int bfeExp;
    private final int pipFractions;

    public Pips(int increment, int bigFigureEnd, int pipsStart, int pipsEnd, int pipFractions) {
        this.bfeExp = -adjust(bigFigureEnd);
        this.psExp = -adjust(pipsStart);
        this.peExp = -adjust(pipsEnd);
	this.pipFractions = pipFractions;

	this.zero = BigDecimal.ZERO.setScale(-peExp);
        this.epsilon = pow(peExp);
	this.increment = this.epsilon.multiply(bd(increment));
        this.oneBigFigure = scalePips(pow(bfeExp));
	this.onePip = scalePips(pow(peExp + pipFractions));
    }

    public BigDecimal getPips(BigDecimal n) {
        return n.abs().remainder(oneBigFigure);
    }

    public BigDecimal getBigFigure(BigDecimal n) {
        return (-1 == n.signum()) ? n.add(getPips(n)) : n.subtract(getPips(n));
    }

    public BigDecimal normalize(BigDecimal n) {
        return n.setScale(-peExp);
    }

    public BigDecimal up(BigDecimal n) {
	return n.add(this.increment);
    }

    public BigDecimal up(BigDecimal n, int count) {
	return n.add(increment.multiply(bd(count)));
    }

    public BigDecimal down(BigDecimal n) {
	return n.subtract(this.increment);
    }

    public BigDecimal down(BigDecimal n, int count) {
	return n.subtract(increment.multiply(bd(count)));
    }

    public boolean isValid(BigDecimal n) {
	return 0 == zero.compareTo(n.abs().remainder(increment));
    }

    public BigDecimal expandPips(String s) {
	int delta = psExp - peExp + 1;// - s.length();
	String p = paddingString(s, delta, '0', false);
	System.out.println("XP " + delta + ", [" + p + "]");
	return new BigDecimal(p).multiply(epsilon);
    }

    private BigDecimal scalePips(BigDecimal n) {
        return peExp < 0 ? n.setScale(-peExp) : n;
    }

    private static int adjust(int n) {
        return n > 0 ? n : n+1;
    }

    private static BigDecimal pow(int pos) {
        return BigDecimal.ONE.movePointRight(pos);
    }

    private static BigDecimal bd(int n) {
	return new BigDecimal(n);
    }

    public static String paddingString(String s, int n, char c, boolean paddingLeft) {
	if (s == null) {
	    return s;
	}
	int add = n - s.length(); // may overflow int size... should not be a problem in real life
	if(add <= 0){
	    return s;
	}
	StringBuffer str = new StringBuffer(s);
	char[] ch = new char[add];
	Arrays.fill(ch, c);
	if(paddingLeft){
	    str.insert(0, ch);
	} else {
	    str.append(ch);
	}
	return str.toString();
    }
    

}
