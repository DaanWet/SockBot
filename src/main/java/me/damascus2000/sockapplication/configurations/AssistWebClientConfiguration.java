package me.damascus2000.sockapplication.configurations;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@PropertySource("classpath:application.properties")
public class AssistWebClientConfiguration {

    @Value("${assist.email}")
    public String assistEmail;
    @Value("${assist.password}")
    public String assistPassword;
    @Value("${assist.validation}")
    public String assistValidation;
    @Value("${assist.viewstate}")
    public String assistViewstate;
    @Value("${assist.generator}")
    public String assistGenerator;

    public static String BASE_URL = "https://app.assistonline.eu";

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
            .baseUrl(BASE_URL)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.ALL_VALUE)
            .build();
    }

    @NotNull
    @Bean
    public MultiValueMap<String, String> createLoginFormData() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("ctl00$ContentPlaceHolder1$txtLogin", assistEmail);
        formData.add("ctl00$ContentPlaceHolder1$txtPassword", assistPassword);
        formData.add("__EVENTTARGET", "ctl00$ContentPlaceHolder1$btnSubmit");
        formData.add("__EVENTVALIDATION", assistValidation);
        formData.add("__VIEWSTATE", assistViewstate);
        formData.add("__VIEWSTATEGENERATOR", assistGenerator);
        return formData;
    }
}
