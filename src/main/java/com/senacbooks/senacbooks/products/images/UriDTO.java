package com.senacbooks.senacbooks.products.images;

import java.io.Serializable;

public class UriDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uri;
    private Boolean prinicpal;

    public UriDTO() {
    }

    public UriDTO(String uri, Boolean prinicpal) {
        this.uri = uri;
        this.prinicpal = prinicpal;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Boolean getPrinicpal() {
        return prinicpal;
    }

    public void setPrinicpal(Boolean prinicpal) {
        this.prinicpal = prinicpal;
    }
}
