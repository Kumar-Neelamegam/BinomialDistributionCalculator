package org.rgbstudios.binomialdistributioncalculator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;

/**
 * Math utilities for binomial distribution probabilities
 */

public class Util {

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

    public static BigInteger nchoosek(int n, int k) {
        BigInteger result = BigInteger.ONE;
        for (int i = 0; i < k; i++) {
            result = result.multiply(BigInteger.valueOf(n - i))
                    .divide(BigInteger.valueOf(i + 1));
        }
        return result;
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


    public static BigDecimal equal(double p, int n, int x) {
        BigDecimal bin = new BigDecimal(nchoosek(n, x));
        return bin.multiply(BigDecimal.valueOf(Math.pow(p, x))).multiply(BigDecimal.valueOf(Math.pow(1 - p, n - x)));
    }


//    public static double less(double p, int n, int x) {
//        double result = 0;
//        for(int i=0; i<x; i++) {
//            result += equal(p, n, i);
//        }
//        return result;
//    }

    public static BigDecimal less(double p, int n, int x) {
        BigDecimal result = BigDecimal.ZERO;
        for (int i = 0; i < x; i++) {
            result = result.add(equal(p, n, i));
        }
        return result;
    }


//    public static double greater(double p, int n, int x) {
//        double result = 0;
//        for(int i=n; i>x; i--) {
//            result += equal(p, n, i);
//        }
//        return result;
//    }

    public static BigDecimal greater(double p, int n, int x) {
        BigDecimal result = BigDecimal.ZERO;
        for (int i = n; i > x; i--) {
            result = result.add(equal(p, n, i));
        }
        return result;
    }


    public static double mean(double p, int n, int x) {
        return n * p;
    }

    public static double variance(double p, int n, int x) {
        return n * p * (1 - p);
    }

    public static double stddev(double p, int n, int x) {
        return Math.sqrt(variance(p, n, x));
    }

//    public static String getPrettyNum(int num) {
//        return String.format("%.5e",num);
//    }
//    public static String getPrettyNum(double num) {
//        return String.format("%.5e",num);
//    }
//    public static String getPrettyNum(BigInteger num) {
//        return new DecimalFormat("#0.#####").format(num);
//    }
//    public static String getPrettyNum(BigDecimal num) {
//        return new DecimalFormat("#0.#####").format(num);
//    }

    public static <K> String getPrettyNum(K num) {
        return new DecimalFormat("#0.##########").format(num);
        //        return String.format("%.5e",num);
    }

    public static double round(double num, int digits) {
        return (Math.round(num * Math.pow(10, digits)) / Math.pow(10, digits));
    }

}
