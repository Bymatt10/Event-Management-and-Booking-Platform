package com.example.backendeventmanagementbooking.service;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.backendeventmanagementbooking.domain.dto.common.PayPalOrderDto;
import com.luigivismara.shortuuid.ShortUuid;
import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.ApplicationContext;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.PurchaseUnitRequest;

@Service
public interface PaypalOrder {
        default OrderRequest buildRequestBody(PayPalOrderDto payPalOrder) {
                var orderRequest = new OrderRequest();
                orderRequest.checkoutPaymentIntent("CAPTURE");

                var applicationContext = new ApplicationContext()
                                .cancelUrl("http://localhost:8080/capture")
                                .returnUrl("http://localhost:8080/success");

                var amount = new AmountWithBreakdown()
                                .currencyCode("USD")
                                .value(String.valueOf(payPalOrder.value()));

                orderRequest.applicationContext(applicationContext);

                var purchaseUnits = new PurchaseUnitRequest();
                purchaseUnits.amountWithBreakdown(amount);
                purchaseUnits.shippingDetail()

                orderRequest.purchaseUnits(Collections
                                .singletonList(purchaseUnits));

                return orderRequest;
        }

        Order executeOrder(PayPalOrderDto palOrderDto) throws IOException;

}
