package com.netease.boot.dal;


import org.apache.commons.validator.routines.EmailValidator;

/**
 * EMail Address Value Object
 * Created by jiangwenkang on 2016/9/13.
 */
public class EMailAddress implements ValueObject<EMailAddress> {


    private final static EmailValidator VALIDATOR = EmailValidator.getInstance(false);
    private static final long serialVersionUID = -2435666042313486061L;

    private String email;

    public EMailAddress(String emailAddress) {
        if (isValid(emailAddress)) {
            this.email = emailAddress;
        } else {
            throw new IllegalArgumentException("emailAddress:" + emailAddress + " is illegal");
        }
    }

    public static boolean isValid(String emailAddress) {
        return VALIDATOR.isValid(emailAddress);
    }

    public String email() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EMailAddress)) return false;

        EMailAddress that = (EMailAddress) o;

        return isSameWith(that);

    }

    @Override
    public int hashCode() {
        return email != null ? email.hashCode() : 0;
    }

    @Override
    public boolean isSameWith(EMailAddress other) {
        return other != null && this.email.equals(other.email());
    }
}
