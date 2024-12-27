package com.example.backendeventmanagementbooking.service.Impl;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.backendeventmanagementbooking.domain.dto.common.PayPalOrderDto;
import com.example.backendeventmanagementbooking.enums.PaypalTokenType;
import com.example.backendeventmanagementbooking.security.SecurityTools;
import com.example.backendeventmanagementbooking.service.PaypalOrder;
import com.paypal.core.PayPalHttpClient;
import com.paypal.orders.AmountBreakdown;
import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.ApplicationContext;
import com.paypal.orders.Item;
import com.paypal.orders.Money;
import com.paypal.orders.Name;
import com.paypal.orders.Order;
import com.paypal.orders.OrderCaptureRequest;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCaptureRequest;
import com.paypal.orders.OrdersCreateRequest;
import com.paypal.orders.Payer;
import com.paypal.orders.PaymentSource;
import com.paypal.orders.PurchaseUnitRequest;
import com.paypal.orders.Token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaypalOrderServiceImpl implements PaypalOrder {

        private final PayPalHttpClient payPalHttpClient;
        private final SecurityTools securityTools;

        @Override
        public OrderRequest buildRequestBody(PayPalOrderDto dto) {
                var user = securityTools.getCurrentUser();
                var orderRequest = new OrderRequest();
                orderRequest.checkoutPaymentIntent("CAPTURE");

                var payer = new Payer();
                payer.email(user.getEmail());
                payer.name(new Name()
                                .givenName(user.getProfile().getFullName()));

                var amount = new AmountWithBreakdown()
                                .currencyCode("USD")
                                .value(String.valueOf(dto.item().unitPrice()));

                orderRequest.payer(payer);
                orderRequest.applicationContext(new ApplicationContext());

                var purchaseUnits = new PurchaseUnitRequest();

                var item = new Item();
                item.name(dto.item().name());
                item.description(dto.item().description());
                item.sku(dto.item().sku());
                item.quantity(dto.item().quantity().toString());

                var unitAmount = new Money()
                                .currencyCode(amount.currencyCode())
                                .value(String.valueOf(dto.item().unitPrice()));
                item.unitAmount(unitAmount);

                var amountBreakdown = new AmountBreakdown();
                amountBreakdown.itemTotal(unitAmount);

                amount.amountBreakdown(amountBreakdown);

                purchaseUnits.items(List.of(item));
                purchaseUnits.amountWithBreakdown(amount);
                

                orderRequest.purchaseUnits(List.of(purchaseUnits));

                return orderRequest;
        }

        @Override
        public Order executeOrder(PayPalOrderDto palOrderDto) throws IOException {
                var request = new OrdersCreateRequest().requestBody(this.buildRequestBody(palOrderDto));

                var result = payPalHttpClient
                                .execute(request)
                                .result();

                var orderCaptureRequest = new OrdersCaptureRequest(result.id());
                var orderCapture = new OrderCaptureRequest();

                var token = new Token()
                                .type(PaypalTokenType.BILLING_AGREEMENT.name())
                                .id(result.id());
                orderCapture.paymentSource(new PaymentSource()
                                .card(palOrderDto.card().toPaypalCard())
                                .token(token));

                orderCaptureRequest.requestBody(orderCapture);
                var complete = payPalHttpClient.execute(orderCaptureRequest);

                return complete.result();
        }

}
