package com.example;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class GrafanaDashboardCreator {

    private static final String GRAFANA_URL = "http://localhost:3000/api/dashboards/db";
    private static final String GRAFANA_API_KEY = "eyJrIjoiTFRSN1RBOVc3SGhjblc0bWZodXZ3MnNDcU92Um5VZUIiLKJuIjoibXktYXBpLWtleSIsImlkIjoxfQ"; 

    public static void main(String[] args) {
        try {
            createDashboard();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createDashboard() throws IOException {
        // Define the dashboard JSON configuration
        String dashboardJson = "{\n" +
                "  \"dashboard\": {\n" +
                "    \"id\": null,\n" +
                "    \"title\": \"ETH Deposit Tracker\",\n" +
                "    \"uid\": \"eth-deposit-tracker\",\n" +
                "    \"timezone\": \"browser\",\n" +
                "    \"panels\": [\n" +
                "      {\n" +
                "        \"type\": \"timeseries\",\n" +
                "        \"title\": \"Total Deposit Amount Over Time\",\n" +
                "        \"targets\": [\n" +
                "          {\n" +
                "            \"expr\": \"sum(rate(deposit_amount[5m])) by (pubkey)\",\n" +
                "            \"legendFormat\": \"Total Deposits\",\n" +
                "            \"refId\": \"A\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"gridPos\": {\n" +
                "          \"x\": 0,\n" +
                "          \"y\": 0,\n" +
                "          \"w\": 12,\n" +
                "          \"h\": 9\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"type\": \"bargauge\",\n" +
                "        \"title\": \"Number of Deposits Over Time\",\n" +
                "        \"targets\": [\n" +
                "          {\n" +
                "            \"expr\": \"count(rate(deposit_amount[5m])) by (pubkey)\",\n" +
                "            \"legendFormat\": \"Deposit Count\",\n" +
                "            \"refId\": \"B\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"gridPos\": {\n" +
                "          \"x\": 12,\n" +
                "          \"y\": 0,\n" +
                "          \"w\": 12,\n" +
                "          \"h\": 9\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"type\": \"stat\",\n" +
                "        \"title\": \"Average Deposit Amount\",\n" +
                "        \"targets\": [\n" +
                "          {\n" +
                "            \"expr\": \"avg(deposit_amount)\",\n" +
                "            \"legendFormat\": \"Avg Deposit\",\n" +
                "            \"refId\": \"C\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"gridPos\": {\n" +
                "          \"x\": 0,\n" +
                "          \"y\": 9,\n" +
                "          \"w\": 12,\n" +
                "          \"h\": 9\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"type\": \"table\",\n" +
                "        \"title\": \"Individual Deposit Details\",\n" +
                "        \"targets\": [\n" +
                "          {\n" +
                "            \"expr\": \"deposit_amount\",\n" +
                "            \"legendFormat\": \"{{blockNumber}}\",\n" +
                "            \"refId\": \"D\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"gridPos\": {\n" +
                "          \"x\": 0,\n" +
                "          \"y\": 18,\n" +
                "          \"w\": 24,\n" +
                "          \"h\": 10\n" +
                "        },\n" +
                "        \"options\": {\n" +
                "          \"showHeader\": true\n" +
                "        }\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"overwrite\": true\n" +
                "}";

        // Create HTTP client and request
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(GRAFANA_URL);

            // Set API key in headers
            httpPost.setHeader("Authorization", "Bearer " + GRAFANA_API_KEY);
            httpPost.setHeader("Content-Type", "application/json");

            // Set request body
            StringEntity entity = new StringEntity(dashboardJson);
            httpPost.setEntity(entity);

            // Execute request
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            // Print response
            if (responseEntity != null) {
                String responseString = EntityUtils.toString(responseEntity);
                System.out.println("Response: " + responseString);
            }
        }
    }
}
