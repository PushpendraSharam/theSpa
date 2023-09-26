package app.myapp.myapplication.ApiModal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginApi
{
    @SerializedName("Result")
    @Expose
    private String result;
    @SerializedName("Data")
    @Expose
    private LoginApiData data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LoginApiData getData() {
        return data;
    }

    public void setData(LoginApiData data) {
        this.data = data;
    }
}
