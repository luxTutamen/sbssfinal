package ua.axiom.config;

public interface SecurityURIConfig {
    String[] GUEST_ENDPOINTS = {"/", "/login", "/register", "/test"};
    String[] USER_ENDPOINTS = {"/clientpage", "/apiPages/neworder", "api/orderHistory", "/api/neworder"};
    String[] DRIVER_ENDPOINTS = {"/driverpage"};
    String[] ADMIN_ENDPOINTS = {"/adminpage"};

}
