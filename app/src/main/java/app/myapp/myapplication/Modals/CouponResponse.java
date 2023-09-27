package app.myapp.myapplication.Modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CouponResponse
{
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("discount")
    @Expose
    private String discount;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

}
