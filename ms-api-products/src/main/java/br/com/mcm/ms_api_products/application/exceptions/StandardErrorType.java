package br.com.mcm.ms_api_products.application.exceptions;

public enum StandardErrorType {

    INCOMPREHENSIBLE_MESSAGE("/incomprehensible-message", "Incomprehensible message"),
    RESOURCE_NOT_FOUND("/resource-not-found", "Resource not found"),
    REQUIRED_PARAMETER("/required-parameter", "Required parameter"),
    INVALID_PARAMETER("/invalid-parameter", "Invalid parameter");

    private String title;
    private String uri;

    StandardErrorType(String path, String title) {
        this.uri = "https://ms-api-product" + path;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getUri() {
        return uri;
    }

}
