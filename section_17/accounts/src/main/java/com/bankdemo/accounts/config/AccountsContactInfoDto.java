package com.bankdemo.accounts.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "accounts")
@Data
public class AccountsContactInfoDto {
    private String message;
    private Map<String, String> contactDetails;
    private List<String> onCallSupport;

}
// when the configuration doesn't need to change at runtime
//public record AccountsContactInfoDto(String message, Map<String, String> contactDetails, List<String> onCallSupport) {
//
//}
