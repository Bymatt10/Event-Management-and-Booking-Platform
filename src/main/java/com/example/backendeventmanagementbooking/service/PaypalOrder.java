package com.example.backendeventmanagementbooking.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.example.backendeventmanagementbooking.domain.dto.common.PayPalOrderDto;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;

@Service
public interface PaypalOrder {
        OrderRequest buildRequestBody(PayPalOrderDto payPalOrder);

        Order executeOrder(PayPalOrderDto palOrderDto) throws IOException;

}
