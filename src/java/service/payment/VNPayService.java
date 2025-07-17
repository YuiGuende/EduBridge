package service.payment;

import config.VNPayConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.course.Course;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.payment.Payment;
import model.payment.PaymentDetail;
import model.user.User;

public class VNPayService {

    private final IPaymentService paymentService = new PaymentServiceImpl();
    private final VNPayConfig config = new VNPayConfig();

    public String createPaymentUrl(List<Course> selectedCourses, HttpServletRequest request) throws UnsupportedEncodingException {
        double totalAmount = (selectedCourses.stream()
                .mapToDouble(c -> c.getDiscountPrice() != 0 ? c.getDiscountPrice() : c.getPrice())
                .sum() * 100);
        long amount = (long) totalAmount;

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";

        String vnp_TxnRef = config.getRandomNumber(8);
        String vnp_IpAddr = config.getIpAddress(request);

        String vnp_TmnCode = config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = request.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", config.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        Payment payment = new Payment("VNPay", LocalDate.now(), totalAmount, 0, vnp_TxnRef, Payment.PaymentStatus.PENDING);
        for (Course c : selectedCourses) {
            PaymentDetail detail = new PaymentDetail(payment, c, c.getDiscountPrice() != 0 ? c.getDiscountPrice() : c.getPrice());
            payment.getPaymentDetails().add(detail);
        }
        HttpSession session = request.getSession(false);
        User u = (session != null) ? (User) session.getAttribute("user") : null;
        payment.setUser(u);
        paymentService.save(payment); // cần có PaymentDAO
        String queryUrl = query.toString();
        String vnp_SecureHash = config.hmacSHA512(config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        return config.vnp_PayUrl + "?" + queryUrl;
    }

}
