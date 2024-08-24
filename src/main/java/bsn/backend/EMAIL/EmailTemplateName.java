package bsn.backend.EMAIL;

import lombok.Getter;

@Getter
public enum EmailTemplateName {
    ACTIVATE_ACCOUNT("account_activate");

private final String  name;
    EmailTemplateName(String name) {
        this.name=name;

    }
}
