package ua.axiom.security;

public interface SecurityURIConfig {
    String[] GUEST_ENDPOINTS = {"/", "/login", "/register"};
    String[] USER_ENDPOINTS = {"/clientpage", "/apiPages/neworder", "api/orderHistory", "/api/neworder"};
    String[] DRIVER_ENDPOINTS = {"/driverpage"};
    String[] ADMIN_ENDPOINTS = {"/adminpage"};

}
