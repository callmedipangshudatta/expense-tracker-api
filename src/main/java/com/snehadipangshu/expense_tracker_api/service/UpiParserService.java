package com.snehadipangshu.expense_tracker_api.service;

import com.snehadipangshu.expense_tracker_api.dto.ExpenseDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UpiParserService {

    // Regex to capture amounts like Rs. 450, INR 450.00, or Rs 1,200
    private static final Pattern AMOUNT_PATTERN =
            Pattern.compile("(?i)(?:rs\\.?|inr|paid)\\s*([0-9]+(?:,[0-9]+)*(?:\\.[0-9]{1,2})?)");

    // Regex to isolate typical recipient indicators
    private static final Pattern MERCHANT_PATTERN =
            Pattern.compile("(?i)(?:to|at|vpa)\\s+([a-zA-Z0-9\\s&]+?)(?:\\s+(?:on|ref|via|upi|using)|$)");

    // Rule-based dictionary for immediate categorization
    private static final Map<String, String> CATEGORY_MAP = new HashMap<>();

    static {
        CATEGORY_MAP.put("swiggy", "Food");
        CATEGORY_MAP.put("zomato", "Food");
        CATEGORY_MAP.put("blinkit", "Groceries");
        CATEGORY_MAP.put("zepto", "Groceries");
        CATEGORY_MAP.put("uber", "Transport");
        CATEGORY_MAP.put("ola", "Transport");
        CATEGORY_MAP.put("amazon", "Shopping");
        CATEGORY_MAP.put("flipkart", "Shopping");
        CATEGORY_MAP.put("netflix", "Entertainment");
        CATEGORY_MAP.put("spotify", "Entertainment");
    }

    public ExpenseDto parseSms(String sms) {
        ExpenseDto dto = new ExpenseDto();
        dto.setAmount(extractAmount(sms));

        String merchant = extractMerchant(sms);
        dto.setDescription(merchant);
        dto.setCategory(categorize(merchant));

        return dto;
    }

    private BigDecimal extractAmount(String sms) {
        Matcher matcher = AMOUNT_PATTERN.matcher(sms);
        if (matcher.find()) {
            String cleanAmount = matcher.group(1).replace(",", "");
            return new BigDecimal(cleanAmount);
        }
        return BigDecimal.ZERO;
    }

    private String extractMerchant(String sms) {
        Matcher matcher = MERCHANT_PATTERN.matcher(sms);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return "Unknown Merchant";
    }

    private String categorize(String merchant) {
        String lower = merchant.toLowerCase();
        for (Map.Entry<String, String> entry : CATEGORY_MAP.entrySet()) {
            if (lower.contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        // Fallback category if unmatched (where LLM triggers)
        return "Miscellaneous";
    }
}