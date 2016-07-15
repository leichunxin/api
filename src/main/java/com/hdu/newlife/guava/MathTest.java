package com.hdu.newlife.guava;

import java.math.BigInteger;
import java.math.RoundingMode;

import com.google.common.math.DoubleMath;
import com.google.common.math.IntMath;
import com.google.common.math.LongMath;

public class MathTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int logFloor = LongMath.log2(10, RoundingMode.FLOOR);
		System.out.println("logFloor=" + logFloor);
		int mustNotOverflow = IntMath.checkedMultiply(3, 4);
		System.out.println("mustNotOverflow=" + mustNotOverflow);
		BigInteger nearestInteger = DoubleMath.roundToBigInteger(10.08, RoundingMode.HALF_EVEN);
		System.out.println("nearestInteger=" + nearestInteger);

		// 最大公约数 gcd
		// 取模 mod
		// 取幂 pow
		// 是否2的幂 isPowerOfTwo
		// 阶乘* factorial
		// 二项式系数* binomial

		MathTest tester = new MathTest();
		tester.testIntMath();
	}

	private void testIntMath() {
		try {
			System.out.println(IntMath.checkedAdd(Integer.MAX_VALUE, Integer.MAX_VALUE));
		} catch (ArithmeticException e) {
			System.out.println("Error: " + e.getMessage());
		}

		System.out.println(IntMath.divide(100, 5, RoundingMode.UNNECESSARY));
		System.out.println("Log2(2): " + IntMath.log2(2, RoundingMode.HALF_EVEN));
		System.out.println("Log10(10): " + IntMath.log10(10, RoundingMode.HALF_EVEN));
		System.out.println("sqrt(100): " + IntMath.sqrt(IntMath.pow(10, 2), RoundingMode.HALF_EVEN));
		System.out.println("gcd(100,50): " + IntMath.gcd(100, 50));
		System.out.println("modulus(100,50): " + IntMath.mod(100, 50));
		System.out.println("factorial(5): " + IntMath.factorial(5));
	}

}
