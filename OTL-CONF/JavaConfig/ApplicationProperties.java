package ru.otlnal.onlineloans.system.config;

import java.math.BigDecimal;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private BigDecimal interest;

    private Sms sms = new Sms();

    private PhoneNumberVerification phoneNumberVerification = new PhoneNumberVerification();

    private OfflineSystem offlineSystem = new OfflineSystem();

    public Sms getSms() {
        return sms;
    }

    public void setSms(Sms sms) {
        this.sms = sms;
    }

    public PhoneNumberVerification getPhoneNumberVerification() {
        return phoneNumberVerification;
    }

    public void setPhoneNumberVerification(
        PhoneNumberVerification phoneNumberVerification) {
        this.phoneNumberVerification = phoneNumberVerification;
    }

    public OfflineSystem getOfflineSystem() {
        return offlineSystem;
    }

    public void setOfflineSystem(OfflineSystem offlineSystem) {
        this.offlineSystem = offlineSystem;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public static class OfflineSystem {

        private String url;

        private String userName;

        private String password;

        private String serviceId;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }
    }

    public static class Sms {

        private Emulator emulator = new Emulator();

        public Emulator getEmulator() {
            return emulator;
        }

        public void setEmulator(Emulator emulator) {
            this.emulator = emulator;
        }

        public static class Emulator {

            private String url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }

    public static class PhoneNumberVerification {

        private int retryAfterSeconds = 20;

        private int validitySeconds = 60;

        public int getRetryAfterSeconds() {
            return retryAfterSeconds;
        }

        public void setRetryAfterSeconds(int retryAfterSeconds) {
            this.retryAfterSeconds = retryAfterSeconds;
        }

        public int getValiditySeconds() {
            return validitySeconds;
        }

        public void setValiditySeconds(int validitySeconds) {
            this.validitySeconds = validitySeconds;
        }
    }
}
