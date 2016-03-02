package com.softserve.hotels.service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import com.softserve.hotels.dao.PaymentTokenDao;
import com.softserve.hotels.model.PaymentToken;
import com.softserve.hotels.model.Reserved;
import com.softserve.hotels.model.User;

@Service("paymentTokenService")
public class PaymentServiceImpl extends AbstractServiceImpl<PaymentToken> implements PaymentService {
    
    private static final Logger LOGGER = LogManager.getLogger();
    
    @Autowired
    private ServletContext context;
    
    @Autowired
    private PaymentTokenDao paymentTokenDao;
    
    
    
    // ###Details
    // Let's you specify details of a payment amount.
    private Details generateDetails(Float price) {
        return new Details().setSubtotal(formatPrice(price)).setTax("1");
    }
    // ###Amount
    // Let's you specify a payment amount.
    private Amount generateAmount(Float price) {
        Amount amount = new Amount();
        amount.setCurrency("USD");
        // Total must be equal to sum of shipping, tax and subtotal.
        Float total = price + 1;
        amount.setTotal(formatPrice(total));
        amount.setDetails(generateDetails(price));
        return amount;
    }
    // The Payment creation API requires a list of
    // Transaction; add the created `Transaction`
    // to a List
    private ItemList generateItems(Reserved reserved, PaymentToken entity, Float price) {
        Item item = new Item();
        String itemName = entity.getReserved().getApartment().getName() + " "
        + reserved.getDateStartReservation() + "-" + reserved.getDateEndReservation(); 
        item.setName(itemName).setQuantity("1").setCurrency("USD").setPrice(formatPrice(price));
        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();
        items.add(item);
        itemList.setItems(items);
        return itemList;
    }
    // ###Transaction
    // A transaction defines the contract of a
    // payment - what is the payment for and who
    // is fulfilling it. Transaction is created with
    // a `Payee` and `Amount` types
    private Transaction generateTransaction(Reserved reserved, PaymentToken entity, Float price) {
        Transaction transaction = new Transaction();
        transaction.setAmount(generateAmount(price));
        transaction
                .setDescription("Payment for apartment booking.");
        transaction.setItemList(generateItems(reserved, entity, price));
        return transaction;
    }
    // ###Payment
    // A Payment Resource; create one using
    // the above types and intent as 'order'
    private Payment generatePayment(Reserved reserved, PaymentToken entity,
            Float price, String guid, String confirmUrl, String cancelUrl) {
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");
        Payment payment = new Payment();
        payment.setIntent("order");
        payment.setPayer(payer);
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(generateTransaction(reserved, entity, price));
        payment.setTransactions(transactions);
        
        payment.setRedirectUrls(generateRedirectUrls(confirmUrl + guid, cancelUrl));
        return payment;
    }
    // ###Redirect URLs
    private RedirectUrls generateRedirectUrls(String confirmUrl, String cancelUrl) {
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setReturnUrl(confirmUrl);
        redirectUrls.setCancelUrl(cancelUrl);
        return redirectUrls;
    }
    @Override
    public void create(PaymentToken entity) {
        final String confirmUrl = "http://" + context.getVirtualServerName() 
        + ":8080" + context.getContextPath() + "/tenant/paymentApproved?token=";
        final String cancelUrl = "http://" + context.getVirtualServerName() 
        + ":8080" + context.getContextPath();
        Reserved reserved = entity.getReserved();
        final Float dayPrice = reserved.getApartment().getPrice();
        Float price = Days
                .daysBetween(reserved.getDateStartReservation(), reserved.getDateEndReservation()).getDays() * dayPrice;
        Payment createdPayment;
        APIContext apiContext = null;
        String accessToken = null;
        try {
            String clientID = Payment.getClientID();
            String clientSecret = Payment.getClientSecret();
            accessToken = new OAuthTokenCredential(clientID, clientSecret).getAccessToken(); 
            apiContext = new APIContext(accessToken);
            String guid = UUID.randomUUID().toString().replaceAll("-", "");
            Payment payment = generatePayment(reserved, entity, price, guid, confirmUrl, cancelUrl);
            

            // Create a payment by posting to the APIService
            // using a valid AccessToken
            // The return object contains the status;
            createdPayment = payment.create(apiContext);
            entity.setToken(guid);
            entity.setAccessToken(accessToken);
            List<Links> links = createdPayment.getLinks();
            for (Links link : links) {
                if ("approval_url".equalsIgnoreCase(link.getRel())) {
                    entity.setApprovementUrl(link.getHref());
                    
                }
                if ("execute".equalsIgnoreCase(link.getRel())) {
                    entity.setExecuteUrl(link.getHref());
                }
            }
            paymentTokenDao.create(entity);
        } catch (PayPalRESTException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public PaymentToken findByToken(String token) {
        return paymentTokenDao.findByToken(token);
    }
    
    @Override
    public List<PaymentToken> findByUser(User user) {
        return paymentTokenDao.findByUser(user);
    }
    
    private String formatPrice(Float price) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setRoundingMode(RoundingMode.CEILING);
        return decimalFormat.format(price);
    }

}