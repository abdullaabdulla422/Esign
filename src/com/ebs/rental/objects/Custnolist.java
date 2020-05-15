package com.ebs.rental.objects;

import com.ebs.rental.utils.SessionData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by techunity on 21/10/16.
 */
@SuppressWarnings("ALL")
public class Custnolist {
    private static final String TAG_CUSTNUM = "custnum";
    private String custnum;

    public String getCustnum ()
    {
        return custnum;
    }

    private void setCustnum(String custnum)
    {
        this.custnum = custnum;
    }

    @SuppressWarnings("UnusedReturnValue")
    public static ArrayList<Custnolist> parseData(String response) throws Exception {

        ArrayList<Custnolist> rental = new ArrayList<>();

        Custnolist detail = new Custnolist();

        JSONArray detailarray = new JSONArray();
        detailarray = new JSONArray(response);

        for (int i = 0; i < detailarray.length(); i++) {
            detail = new Custnolist();
            JSONObject userDetails = detailarray.getJSONObject(i);

            detail.setCustnum(userDetails.getString(TAG_CUSTNUM));

            rental.add(detail);
        }
        SessionData.getInstance().setCustnolists(rental);
    return rental;
    }

}
