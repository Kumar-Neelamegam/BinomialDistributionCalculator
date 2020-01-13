package org.rgbstudios.binomialdistributioncalculator

import java.math.BigDecimal
import java.math.BigInteger
import java.text.DecimalFormat
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

/**
 * Math utilities for binomial distribution probabilities
 */
object Util {
    //    public static long nchoosek(int n, int k) {
//        if(n == k) {
//            return 1;
//        }
//        double result = 1;
//        for(int i=1; i<=k; i++) {
//            result *= (double) (n+1-i)/i; //replace n+1-i with n-k+i?
//        }
//        return (long) result;
//    }
    fun nchoosek(n: Int, k: Int): BigInteger {
        var result = BigInteger.ONE
        for (i in 0 until k) {
            result = result.multiply(BigInteger.valueOf(n - i.toLong()))
                    .divide(BigInteger.valueOf(i + 1.toLong()))
        }
        return result
    }

    //    public static BigInteger factorial(int n) {
//        BigInteger ret = BigInteger.ONE;
//        for(int i = n; i>1; i--) {
//            ret = ret.multiply(BigInteger.valueOf(i) );
//        }
//        return(ret);
//    }
//
//    public static BigInteger nchoosek(int n, int k) {
//        return factorial(n).divide(factorial(k) ).divide(factorial(n-k) );
//    }
//    public static double equal(double p, int n, int x) {
//        return (double) (nchoosek(n, x) * Math.pow(p, x) * Math.pow(1-p, n-x) );
//    }
    fun equal(p: Double, n: Int, x: Int): BigDecimal {
        val bin = BigDecimal(nchoosek(n, x))
        return bin.multiply(BigDecimal.valueOf(p.pow(x.toDouble()))).multiply(BigDecimal.valueOf((1 - p).pow(n - x.toDouble())))
    }

    //    public static double less(double p, int n, int x) {
//        double result = 0;
//        for(int i=0; i<x; i++) {
//            result += equal(p, n, i);
//        }
//        return result;
//    }
    fun less(p: Double, n: Int, x: Int): BigDecimal {
        var result = BigDecimal.ZERO
        for (i in 0 until x) {
            result = result.add(equal(p, n, i))
        }
        return result
    }

    //    public static double greater(double p, int n, int x) {
//        double result = 0;
//        for(int i=n; i>x; i--) {
//            result += equal(p, n, i);
//        }
//        return result;
//    }
    fun greater(p: Double, n: Int, x: Int): BigDecimal {
        var result = BigDecimal.ZERO
        for (i in n downTo x + 1) {
            result = result.add(equal(p, n, i))
        }
        return result
    }

    fun mean(p: Double, n: Int, x: Int): Double {
        return n * p
    }

    fun variance(p: Double, n: Int, x: Int): Double {
        return n * p * (1 - p)
    }

    fun stddev(p: Double, n: Int, x: Int): Double {
        return sqrt(variance(p, n, x))
    }


    fun <K> getPrettyNum(num: K): String {
        return DecimalFormat("#0.##########").format(num)
    }

    fun round(num: Double, digits: Int): Double {
        return (num * 10.0.pow(digits.toDouble())).roundToInt() / 10.0.pow(digits.toDouble())
    }
}