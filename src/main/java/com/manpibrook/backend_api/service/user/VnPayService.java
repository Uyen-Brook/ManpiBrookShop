package com.manpibrook.backend_api.service.user;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.manpibrook.backend_api.entity.PaymentTransaction;

@Service
public class VnPayService {

    @Value("${vnpay.tmn-code}")
    private String vnpTmnCode;

    @Value("${vnpay.hash-secret}")
    private String vnpHashSecret;

    @Value("${vnpay.pay-url}")
    private String vnpPayUrl;

    @Value("${vnpay.return-url}")
    private String vnpReturnUrl;

    /**
     * Tạo URL thanh toán VNPAY
     */
    public String createPaymentUrl(PaymentTransaction tx, String clientIp) {
        try {

            // ===== 1. Convert amount sang đơn vị VNPAY (x100) =====
            long amount = tx.getAmount()
                    .multiply(BigDecimal.valueOf(100))
                    .longValue();

            // ===== 2. Tạo thời gian =====
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

            String createDate = now.format(formatter);
            String expireDate = now.plusMinutes(15).format(formatter);

            // ===== 3. Build params =====
            Map<String, String> vnpParams = new TreeMap<>();

            vnpParams.put("vnp_Version", "2.1.0");
            vnpParams.put("vnp_Command", "pay");
            vnpParams.put("vnp_TmnCode", vnpTmnCode);
            vnpParams.put("vnp_Amount", String.valueOf(amount));
            vnpParams.put("vnp_CurrCode", "VND");

            // QUAN TRỌNG: dùng txnRef
            vnpParams.put("vnp_TxnRef", tx.getTxnRef());

            vnpParams.put("vnp_OrderInfo", tx.getContent());
            vnpParams.put("vnp_OrderType", "other");
            vnpParams.put("vnp_Locale", "vn");
            vnpParams.put("vnp_ReturnUrl", vnpReturnUrl);
            vnpParams.put("vnp_IpAddr", clientIp);
            vnpParams.put("vnp_CreateDate", createDate);
            vnpParams.put("vnp_ExpireDate", expireDate);

            // ===== 4. Build hash =====
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();

            for (Map.Entry<String, String> entry : vnpParams.entrySet()) {

                if (hashData.length() > 0) {
                    hashData.append("&");
                    query.append("&");
                }

                hashData.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue());

                query.append(URLEncoder.encode(entry.getKey(), StandardCharsets.US_ASCII))
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), StandardCharsets.US_ASCII));
            }

            String secureHash = hmacSHA512(vnpHashSecret, hashData.toString());

            query.append("&vnp_SecureHash=").append(secureHash);

            return vnpPayUrl + "?" + query;

        } catch (Exception e) {
            throw new RuntimeException("Không tạo được URL thanh toán VNPAY", e);
        }
    }

    /**
     * Verify chữ ký khi VNPAY callback
     */
    public boolean verifyReturn(Map<String, String> vnpParams) {

        String receivedHash = vnpParams.remove("vnp_SecureHash");
        vnpParams.remove("vnp_SecureHashType");

        Map<String, String> sorted = new TreeMap<>(vnpParams);

        StringBuilder hashData = new StringBuilder();

        for (Map.Entry<String, String> entry : sorted.entrySet()) {

            if (hashData.length() > 0) {
                hashData.append("&");
            }

            hashData.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue());
        }

        String calculatedHash = hmacSHA512(vnpHashSecret, hashData.toString());

        return calculatedHash.equals(receivedHash);
    }

    /**
     * Tạo txnRef cho hệ thống (unique)
     */
    public String generateTxnRef() {
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * HMAC SHA512
     */
    private static String hmacSHA512(String key, String data) {
        try {
            Mac hmac512 = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(
                    key.getBytes(StandardCharsets.UTF_8),
                    "HmacSHA512"
            );
            hmac512.init(secretKey);
            byte[] hash = hmac512.doFinal(data.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (Exception e) {
            throw new RuntimeException("Lỗi mã hóa HMAC", e);
        }
    }
}
