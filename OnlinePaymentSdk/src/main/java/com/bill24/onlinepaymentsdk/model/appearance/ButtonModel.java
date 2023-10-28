package com.bill24.onlinepaymentsdk.model.appearance;

import com.google.gson.annotations.SerializedName;

public class ButtonModel {

    public FavoriteButtonModel getFavoriteButton() {
        return favoriteButton;
    }

    public void setFavoriteButton(FavoriteButtonModel favoriteButton) {
        this.favoriteButton = favoriteButton;
    }

    public BankButtonModel getBankButton() {
        return bankButton;
    }

    public void setBankButton(BankButtonModel bankButton) {
        this.bankButton = bankButton;
    }

    public ActionButtonModel getActionButton() {
        return actionButton;
    }

    public void setActionButton(ActionButtonModel actionButton) {
        this.actionButton = actionButton;
    }

    @SerializedName("favorite_button")
    private FavoriteButtonModel favoriteButton;
    @SerializedName("bank_button")
    private BankButtonModel bankButton;
    @SerializedName("action_button")
    private ActionButtonModel actionButton;

    public RetryButtonModel getRetryButton() {
        return retryButton;
    }

    public void setRetryButton(RetryButtonModel retryButton) {
        this.retryButton = retryButton;
    }

    @SerializedName("retry_button")
    private RetryButtonModel retryButton;
}
