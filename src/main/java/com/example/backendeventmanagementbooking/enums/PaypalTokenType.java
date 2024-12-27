package com.example.backendeventmanagementbooking.enums;

/**
 * The Tokenized Payment Source representing a Request to Vault a Token.
 */
public enum PaypalTokenType {
    /**The setup token, which is a temporary reference to payment source. */
    SETUP_TOKEN,

    /**The PayPal billing agreement ID. References an approved recurring payment for goods or services. */
    BILLING_AGREEMENT;
}
