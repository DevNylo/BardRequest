package com.devnylo.api;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class BardRequest {

    private static final String API_KEY = "1696697682984x827451273217603300"; // KEY TESTE!
    private static final String BASE_URL = "https://api.undetectable.ai/submit";

    public static void main(String[] args) throws IOException, InterruptedException {


        // VARIÁVEL QUE VAI ARMAZENAR A PERGUNTA.
        System.out.print("Pergunte ao BARD: ");
        String pergunta = new Scanner(System.in).nextLine();
        System.out.println();

        // O ENCONDER QUE VAI TRANSFORMAR A PERGUNTA EM UM PADRÃO HTTPS URL.
        String codificarPergunta = URLEncoder.encode(pergunta, "UTF-8");

        // CONSTANTE DESTINADA AO COOKIE 1PSID [PRECISO TROCAR A CHAVE DE TEMPOS EM TEMPOS.]
        final String _1PSID = "";

        // CONSTANTE DESTINADA AO COOKIE 1PSIDTS
        final String _1PSIDTS = "";

        // CONSTANTE DESTINADA AO COOKIE 1PSIDCC
        final String _1PSIDCC = "";

        // CONSTANTE DESTINADA A API KEY
        final String APIKEY = "";

        // ALGORITMO QUE IRÁ FAZER A REQUISIÇÃO A API.
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        "https://google-bard-api1.p.rapidapi.com/ask?question="+codificarPergunta+
                        "&bard___Secure-1PSID_cookie_value="+_1PSID+
                        "&bard___Secure-1PSIDTS_cookie_value="+_1PSIDTS+
                        "&bard___Secure-1PSIDCC_cookie_value="+_1PSIDCC))

                .header("X-RapidAPI-Key", APIKEY)
                .header("X-RapidAPI-Host", "google-bard-api1.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        // RESPOSTA RECEBIDA E EXIBIDA NO CONSOLE, TRATE DA FORMA QUE ACHAR NECESSÁRIO.
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        // ANALISE DA RESPOSTA JSON
        JSONObject jsonResponse = new JSONObject(response.body());

        // EXTRÇÃO DO VALOR DA PALAVRA CHAVE "RESPONSE"
        JSONObject responseObject = jsonResponse.getJSONObject("response");

        // Imprime o valor da chave "response"
        //System.out.println(responseObject);

        // RESPOSTA TRATADA :)
        JSONArray draftsArray = responseObject.getJSONArray("drafts");
        System.out.println(draftsArray.getJSONArray(1).getString(0));


        // ---------------------- UNDETECTABLE IA AREA ---------------------

        System.out.println("// ---------------------- UNDETECTABLE IA AREA ---------------------");

        HttpClient client = HttpClient.newHttpClient();

        var headers = new java.util.HashMap<String, String>();
        headers.put("Authorization", "Bearer " + API_KEY);
        headers.put("Content-Type", "application/json");

        JSONObject requestBody = new JSONObject();
        requestBody.put("content", draftsArray.getJSONArray(1).getString(0));
        requestBody.put("readability", "High School");
        requestBody.put("purpose", "General Writing");
        requestBody.put("strength", "More Human");

        String newBody = requestBody.toString();

        List<String> headerList = new ArrayList<>();
        headers.forEach((key, value) -> headerList.add(key + ": " + value));

        String[] headerArray = headerList.toArray(new String[0]);

        HttpRequest newRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .method("POST", HttpRequest.BodyPublishers.ofString(newBody))
                .headers(headerArray)
                .build();

        HttpResponse<String> newResponse = client.send(newRequest, HttpResponse.BodyHandlers.ofString());

        if (newResponse.statusCode() != 200) {
            throw new IOException("Unexpected response status code: " + newResponse.statusCode());
        }

        System.out.println(newResponse.body());

    }
}
