package com.snehadipangshu.expense_tracker_api.dto;

import jakarta.validation.constraints.NotBlank;

public class SmsRequestDto {
    @NotBlank(message = "SMS text cannot be empty")
    private String smsText;

    public String getSmsText(){
        return smsText;
    }

    public void setSmsText(String smsText){
        this.smsText = smsText;
    }
}
