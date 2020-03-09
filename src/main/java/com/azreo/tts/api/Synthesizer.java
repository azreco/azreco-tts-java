/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.azreo.tts.api;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author AzReco
 */
public class Synthesizer {
    private String userId = null;
    private String token = null;
    private String lang = null;
    private static final String API_URL_FILE = "http://api.azreco.az/synthesize";
    private static final String API_URL_TEXT = "http://api.azreco.az/synthesize/text";
    private static final String API_URL_VOICES = "http://api.azreco.az/voices";
    
    public Synthesizer(String userId, String token, String lang) {
        this.userId = userId;
        this.token = token;
        this.lang = lang;
    }
    
    public byte[] synthesize(String textFile, String ttsId) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        byte[] result = null;
        try {
            HttpPost httpPost = new HttpPost(API_URL_FILE);
            FileBody binary = new FileBody(new File(textFile));
            StringBody idContent = new StringBody(userId, ContentType.TEXT_PLAIN);
            StringBody tokenContent = new StringBody(token, ContentType.TEXT_PLAIN);
            StringBody langContent = new StringBody(lang, ContentType.TEXT_PLAIN);
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create()
                    .addPart("api_id", idContent)
                    .addPart("api_token", tokenContent)
                    .addPart("lang", langContent)
                    .addPart("file", binary);
            if(!(ttsId == null || ttsId.isEmpty())) {
                multipartEntityBuilder.addPart("tts_id", new StringBody(ttsId, ContentType.TEXT_PLAIN));
            }
            HttpEntity reqEntity = multipartEntityBuilder.build();
            httpPost.setEntity(reqEntity);
            CloseableHttpResponse response = null;
            try {
                response = (CloseableHttpResponse) httpClient.execute(httpPost);
                if(response.getStatusLine().getStatusCode() != 200) {
                    if(response.getStatusLine().getStatusCode() == HttpStatus.SC_BAD_REQUEST) {
                        HttpEntity resEntity = response.getEntity();
                        String error = EntityUtils.toString(resEntity);
                        System.err.println("Text-to-speech failed: " + error);
                    } else {
                        System.err.println("Text-to-speech error: " + response.getStatusLine().getReasonPhrase());
                    }
                    return null;
                }
                
                HttpEntity resEntity = response.getEntity();

                try {
                    result = EntityUtils.toByteArray(resEntity);
                    EntityUtils.consume(resEntity);
                } catch (IOException | ParseException ex) {
                    System.err.println("Parsing response failed: " + ex.getMessage());
                }
            } catch (IOException ex) {
                System.err.println("Making text-to-speech request failed: " + ex.getMessage());
                return null;
            }
            finally {
                if(response != null) {
                    try {
                        response.close();
                    } catch (IOException ex) {
                    }
                }
            }
        }
        finally {
            try {
                httpClient.close();
            } catch (IOException ex) {
            }
        }
        return result;
    }
    
    public byte[] synthesizeText(String text, String ttsId) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        byte[] result = null;
        try {
            HttpPost httpPost = new HttpPost(API_URL_TEXT);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            List<NameValuePair> reqEntityParams = new ArrayList<NameValuePair>();
            reqEntityParams.add(new BasicNameValuePair("api_id", userId));
            reqEntityParams.add(new BasicNameValuePair("api_token", token));
            reqEntityParams.add(new BasicNameValuePair("lang", lang));
            reqEntityParams.add(new BasicNameValuePair("text", text));
            if(!(ttsId == null || ttsId.isEmpty())) {
                reqEntityParams.add(new BasicNameValuePair("tts_id", ttsId));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(reqEntityParams, "utf8"));
            CloseableHttpResponse response = null;
            try {
                response = (CloseableHttpResponse) httpClient.execute(httpPost);
                if(response.getStatusLine().getStatusCode() != 200) {
                    if(response.getStatusLine().getStatusCode() == HttpStatus.SC_BAD_REQUEST) {
                        HttpEntity resEntity = response.getEntity();
                        String error = EntityUtils.toString(resEntity);
                        System.err.println("Text-to-speech failed: " + error);
                    } else {
                        System.err.println("Text-to-speech error: " + response.getStatusLine().getReasonPhrase());
                    }
                    return null;
                }
                
                HttpEntity resEntity = response.getEntity();

                try {
                    result = EntityUtils.toByteArray(resEntity);
                    EntityUtils.consume(resEntity);
                } catch (IOException | ParseException ex) {
                    System.err.println("Parsing response failed: " + ex.getMessage());
                }
            } catch (IOException ex) {
                System.err.println("Making text-to-speech request failed: " + ex.getMessage());
                return null;
            }
            finally {
                if(response != null) {
                    try {
                        response.close();
                    } catch (IOException ex) {
                    }
                }
            }
        }
        catch (UnsupportedEncodingException ex) {
            System.err.println("Making text-to-speech request failed: " + ex.getMessage());
        }        
        finally {
            try {
                httpClient.close();
            } catch (IOException ex) {
            }
        }
        return result;
    }
    
    public String getVoices() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = null;
        try {
            HttpGet httpGet = new HttpGet(API_URL_VOICES + "?api_id=" + userId + "&api_token=" + token);
            CloseableHttpResponse response = null;
            try {
                response = (CloseableHttpResponse) httpClient.execute(httpGet);
                if(response.getStatusLine().getStatusCode() != 200) {
                    if(response.getStatusLine().getStatusCode() == HttpStatus.SC_BAD_REQUEST) {
                        HttpEntity resEntity = response.getEntity();
                        String error = EntityUtils.toString(resEntity);
                        System.err.println("Getting voices failed: " + error);
                    } else {
                        System.err.println("Getting voices failed: " + response.getStatusLine().getReasonPhrase());
                    }
                    return null;
                }
                
                HttpEntity resEntity = response.getEntity();

                try {
                    result = EntityUtils.toString(resEntity);
                    EntityUtils.consume(resEntity);
                } catch (IOException | ParseException ex) {
                    System.err.println("Parsing response failed: " + ex.getMessage());
                }
            } catch (IOException ex) {
                System.err.println("Making request to get voices failed: " + ex.getMessage());
                return null;
            }
            finally {
                if(response != null) {
                    try {
                        response.close();
                    } catch (IOException ex) {
                    }
                }
            }
        }
        catch (Exception ex) {
            System.err.println("Making request to get voices failed: " + ex.getMessage());
        }        
        finally {
            try {
                httpClient.close();
            } catch (IOException ex) {
            }
        }
        return result;
    }
}
