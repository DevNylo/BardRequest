package com.devnylo.api;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;

public class BardRequest {
    public static void main(String[] args) throws IOException, InterruptedException {


        // VARIÁVEL QUE VAI ARMAZENAR A PERGUNTA.
        String pergunta = "Diga Olá!";

        // O ENCONDER QUE VAI TRANSFORMAR A PERGUNTA EM UM PADRÃO HTTPS URL.
        String codificarPergunta = URLEncoder.encode(pergunta, "UTF-8");

        // CONSTANTE DESTINADA AO COOKIE 1PSID [VOCÊ PRECISARÁ TROCAR A CHAVE DE TEMPOS EM TEMPOS.]
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



    }
}
