package com.example.backendeventmanagementbooking.service.Impl;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.example.backendeventmanagementbooking.domain.dto.common.PayPalOrderDto;
import com.example.backendeventmanagementbooking.service.PaypalOrder;
import com.paypal.core.PayPalHttpClient;
import com.paypal.orders.Order;
import com.paypal.orders.OrdersCreateRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaypalOrderServiceImpl implements PaypalOrder {

    private final PayPalHttpClient payPalHttpClient;

    @Override
    public Order executeOrder(PayPalOrderDto palOrderDto) throws IOException {
        var request = new OrdersCreateRequest().requestBody(this.buildRequestBody(palOrderDto));

        var result = payPalHttpClient
                .execute(request)
                .result();
                
        return result;
    }

}
