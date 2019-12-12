package me.action.addflutter.utils;

import java.math.BigDecimal;

/**
 * LoanComputer
 * Created by zxx on 2019-12-10 17:30
 */
public class LoanComputer {

    /**
     * 等额本息贷款计算器
     * @param amountWan     贷款总金额，单位：万元
     * @param monthNum      还款期数，单位：月
     * @param yearRate      年化利率，单位：%
     * @return
     */
    public double loanMethod1(float amountWan, int monthNum, float yearRate) {
        // 计算过程中保留6位小数
        int decimalCount = 8;
        // 月利率
        double monthRate = correctDouble(yearRate / 12f, decimalCount);
        // 每月还款金额
        double xAmount;
        double baseRate = 1 + monthRate / 100f;
        double baseAmount = amountWan * 10000;
        double numerator = baseAmount * correctDouble(Math.pow(baseRate, monthNum), decimalCount);
        double rateSum = 0;
        for (int i = monthNum - 1; i >= 0; i--) {
            rateSum += Math.pow(baseRate, i);
        }
        rateSum = correctDouble(rateSum, decimalCount);
        xAmount = numerator / rateSum;
        // 结果保留两位小数
        xAmount = correctDouble(xAmount, 2);
        System.out.println("等额本息每月还款额= " + xAmount + "元");
        return xAmount;
    }

    private double correctDouble(double number, int decimalCount) {
        double multiplier = Math.pow(10, decimalCount);
        double result = (double) Math.round(number * multiplier) / multiplier;
        System.out.println(number + "=>" + result);
        return result;
    }

    private double correctDoubleBigDecimal(double number, int decimalCount) {
        BigDecimal b = new BigDecimal(number);
        double result = b.setScale(decimalCount, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println(number + "==>>" + result);
        return result;
    }
}
