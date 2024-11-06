package org.example;

import org.apache.synapse.MessageContext;
import org.apache.synapse.mediators.AbstractMediator;
import org.json.JSONArray;
import org.json.JSONObject;

public class SimpleClassMediator extends AbstractMediator {
    @Override
    public boolean mediate(MessageContext messageContext) {

        // Retrieve the JSON payload as a String
        String jsonString = (String) messageContext.getProperty("JSONPayload");

// Parse the JSON string
        JSONObject jsonObject = new JSONObject(jsonString);
        System.out.println("Parsed JSON Object: " + jsonObject);

// Extract the array from the JSON and process it
        JSONArray recAcacArray = jsonObject.getJSONObject("ProcessMemberDeductible_v3_SubscriberKey")
                .getJSONObject("pACAC_COLL")
                .getJSONArray("REC_ACAC");

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < recAcacArray.length(); i++) {
            long acacAccNo = recAcacArray.getJSONObject(i).getLong("ACAC_ACC_NO");
            result.append("<faw:REC_ACAC><faw:ACAC_ACC_NO>").append(acacAccNo).append("</faw:ACAC_ACC_NO></faw:REC_ACAC>");
        }

// Set the result as a property and print it
        messageContext.setProperty("ACAC_ACC_NO", result.toString());
        System.out.println("Formatted Result: " + result.toString());

        return true;
    }
}
