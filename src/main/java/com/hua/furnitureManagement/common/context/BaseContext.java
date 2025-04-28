package com.hua.furnitureManagement.common.context;

public class BaseContext {

    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static ThreadLocal<String> threadLocalRole = new ThreadLocal<>();
    public static ThreadLocal<Long> threadLocalAddress = new ThreadLocal<>();


    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }

    public static void removeCurrentId() {
        threadLocal.remove();
    }

    public static void setCurrentRole(String role) {
        threadLocalRole.set(role);
    }

    public static String getCurrentRole() {
        return threadLocalRole.get();
    }

    public static void removeCurrentRole() {
        threadLocalRole.remove();
    }

    public static void setCurrentAddressId(Long id) {
        threadLocalAddress.set(id);
    }

    public static Long getCurrentAddressId() {
        return threadLocalAddress.get();
    }

    public static void removeCurrentAddressId() {
        threadLocalAddress.remove();
    }
}
