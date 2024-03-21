package com.poly.moneylover.models.Response;

public class InitialBalanceSingleton {
    private static InitialBalanceSingleton instance;
    private double initialBalance;

    private InitialBalanceSingleton() {
        // Khởi tạo số dư ban đầu
        initialBalance = 0.0;
    }

    public static synchronized InitialBalanceSingleton getInstance() {
        if (instance == null) {
            instance = new InitialBalanceSingleton();
        }
        return instance;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }
}
