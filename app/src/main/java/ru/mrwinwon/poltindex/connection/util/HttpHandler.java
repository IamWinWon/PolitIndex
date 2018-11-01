package ru.mrwinwon.poltindex.connection.util;

import android.annotation.SuppressLint;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

import ru.mrwinwon.poltindex.util.Const;
import ru.mrwinwon.poltindex.util.MainUtil;

import static ru.mrwinwon.poltindex.util.Const.LINE_FEED;

public class HttpHandler {
    private static final String LOG_TAG = HttpHandler.class.getSimpleName();
    private static String boundary;
    private static OutputStream outputStream;
    private static PrintWriter writer;

    public static String makeServiceCallAuth(String preUrl, String locale) {
        // creates a unique boundary based on time stamp
        boundary = "===" + System.currentTimeMillis() + "===";

        String response = null;
        try {
            URL url = new URL(preUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoOutput(true); // indicates POST method
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);
            conn.setRequestProperty("connection", "close");
            conn.setRequestProperty("Encoding",
                    locale); // Это и есть таинственные "хеддеры"
            conn.setRequestMethod("POST");
            conn.setReadTimeout(240000);

            int status = conn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                String line;
                StringBuffer responce = new StringBuffer();

                while ((line = reader.readLine()) != null) {
                    responce.append(line);
                }

                response = responce.toString();

                reader.close();
                conn.disconnect();
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String inputLine;
                StringBuffer responce = new StringBuffer();

                while ((inputLine = in.readLine()) != null)
                    responce.append(inputLine);

                in.close();

                response = responce.toString();
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(LOG_TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    public static String makePostNumber(String preUrl, String locale, String number, int id_user, String token) {
        // creates a unique boundary based on time stamp
        boundary = "===" + System.currentTimeMillis() + "===";

        String response = null;
        try {
            URL url = new URL(preUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoOutput(true); // indicates POST method
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);
            conn.setRequestProperty("connection", "close");
            conn.setRequestProperty("Encoding",
                    locale); // Это и есть таинственные "хеддеры"
            conn.setRequestMethod("POST");
            conn.setReadTimeout(240000);

            outputStream = conn.getOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(outputStream, Const.CHARSET),
                    true);
            addField("id_user", String.valueOf(id_user));
            addField("token", token);
            addField("phone", number);

            writer.close();

            int status = conn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                String line;
                StringBuffer responce = new StringBuffer();

                while ((line = reader.readLine()) != null) {
                    responce.append(line);
                }

                response = responce.toString();

                reader.close();
                conn.disconnect();
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String inputLine;
                StringBuffer responce = new StringBuffer();

                while ((inputLine = in.readLine()) != null)
                    responce.append(inputLine);

                in.close();

                response = responce.toString();
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(LOG_TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    public static String makePostSms(String preUrl, String locale, String sms, int id_user, String token) {
        // creates a unique boundary based on time stamp
        boundary = "===" + System.currentTimeMillis() + "===";

        String response = null;
        try {
            URL url = new URL(preUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoOutput(true); // indicates POST method
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);
            conn.setRequestProperty("connection", "close");
            conn.setRequestProperty("Encoding",
                    locale); // Это и есть таинственные "хеддеры"
            conn.setRequestMethod("POST");
            conn.setReadTimeout(240000);

            outputStream = conn.getOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(outputStream, Const.CHARSET),
                    true);
            addField("id_user", String.valueOf(id_user));
            addField("token", token);
            addField("sms_code", sms);
            addField("hash", MainUtil.md5(String.valueOf(id_user) + ":" + token + ":" + sms));

            writer.close();

            int status = conn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                String line;
                StringBuffer responce = new StringBuffer();

                while ((line = reader.readLine()) != null) {
                    responce.append(line);
                }

                response = responce.toString();

                reader.close();
                conn.disconnect();
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String inputLine;
                StringBuffer responce = new StringBuffer();

                while ((inputLine = in.readLine()) != null)
                    responce.append(inputLine);

                in.close();

                response = responce.toString();
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(LOG_TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    public static String makePostMainScrean(String preUrl, String locale, int id_token, int id_user, String token) {
        // creates a unique boundary based on time stamp
        boundary = "===" + System.currentTimeMillis() + "===";

        String response = null;
        try {
            URL url = new URL(preUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoOutput(true); // indicates POST method
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);
            conn.setRequestProperty("connection", "close");
            conn.setRequestProperty("Encoding",
                    locale); // Это и есть таинственные "хеддеры"
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Token",
                    token); // Это и есть таинственные "хеддеры"
            conn.setRequestProperty("Authorization", id_token + "_" +
                    MainUtil.md5(String.valueOf(id_token) + ":" + id_user + ":" + token)); // Это и есть таинственные "хеддеры"
            conn.setReadTimeout(240000);

            int status = conn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                String line;
                StringBuffer responce = new StringBuffer();

                while ((line = reader.readLine()) != null) {
                    responce.append(line);
                }

                response = responce.toString();

                reader.close()  ;
                conn.disconnect();
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String inputLine;
                StringBuffer responce = new StringBuffer();

                while ((inputLine = in.readLine()) != null)
                    responce.append(inputLine);

                in.close();

                response = responce.toString();
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(LOG_TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    public static String makeLove(String preUrl, String locale, int id_token, int id_user, String token, int idFigure, int idEvent, int isLike) {
        // creates a unique boundary based on time stamp
        boundary = "===" + System.currentTimeMillis() + "===";

        String response = null;
        try {
            URL url = new URL(preUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoOutput(true); // indicates POST method
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);
            conn.setRequestProperty("connection", "close");
            conn.setRequestProperty("Encoding",
                    locale); // Это и есть таинственные "хеддеры"
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Token",
                    token); // Это и есть таинственные "хеддеры"
            conn.setRequestProperty("Authorization", id_token + "_" +
                    MainUtil.md5(String.valueOf(id_token) + ":" + id_user + ":" + token)); // Это и есть таинственные "хеддеры"
            conn.setReadTimeout(240000);

            outputStream = conn.getOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(outputStream, Const.CHARSET),
                    true);
            addField("id_event", String.valueOf(idEvent));
            addField("id_figure", String.valueOf(idFigure));
            addField("isLike", String.valueOf(isLike));

            writer.close();

            int status = conn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                String line;
                StringBuffer responce = new StringBuffer();

                while ((line = reader.readLine()) != null) {
                    responce.append(line);
                }

                response = responce.toString();

                reader.close()  ;
                conn.disconnect();
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String inputLine;
                StringBuffer responce = new StringBuffer();

                while ((inputLine = in.readLine()) != null)
                    responce.append(inputLine);

                in.close();

                response = responce.toString();
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(LOG_TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    public static String makeComment(String preUrl, String locale, int id_token, int id_user, String token, int idEvent, int idPerson, String text) {
        // creates a unique boundary based on time stamp
        boundary = "===" + System.currentTimeMillis() + "===";

        String response = null;
        try {
            URL url = new URL(preUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoOutput(true); // indicates POST method
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);
            conn.setRequestProperty("connection", "close");
            conn.setRequestProperty("Encoding",
                    locale); // Это и есть таинственные "хеддеры"
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Token",
                    token); // Это и есть таинственные "хеддеры"
            conn.setRequestProperty("Authorization", id_token + "_" +
                    MainUtil.md5(String.valueOf(id_token) + ":" + id_user + ":" + token)); // Это и есть таинственные "хеддеры"
            conn.setReadTimeout(240000);

            outputStream = conn.getOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(outputStream, Const.CHARSET),
                    true);
            addField("id_event", String.valueOf(idEvent));
            addField("id_figure", String.valueOf(idPerson));
            addField("text", text);

            writer.close();

            int status = conn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                String line;
                StringBuffer responce = new StringBuffer();

                while ((line = reader.readLine()) != null) {
                    responce.append(line);
                }

                response = responce.toString();

                reader.close()  ;
                conn.disconnect();
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String inputLine;
                StringBuffer responce = new StringBuffer();

                while ((inputLine = in.readLine()) != null)
                    responce.append(inputLine);

                in.close();

                response = responce.toString();
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(LOG_TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    public static String makePostUserData(String preUrl, String locale, int id_token, int id_user, String token, String key, String value) {
        // creates a unique boundary based on time stamp
        boundary = "===" + System.currentTimeMillis() + "===";

        String response = null;
        try {
            URL url = new URL(preUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoOutput(true); // indicates POST method
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);
            conn.setRequestProperty("connection", "close");
            conn.setRequestProperty("Encoding",
                    locale); // Это и есть таинственные "хеддеры"
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Token",
                    token); // Это и есть таинственные "хеддеры"
            conn.setRequestProperty("Authorization", id_token + "_" +
                    MainUtil.md5(String.valueOf(id_token) + ":" + id_user + ":" + token)); // Это и есть таинственные "хеддеры"
            conn.setReadTimeout(240000);

            outputStream = conn.getOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(outputStream, Const.CHARSET),
                    true);

            addField(key, value);

            writer.close();

            int status = conn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                String line;
                StringBuffer responce = new StringBuffer();

                while ((line = reader.readLine()) != null) {
                    responce.append(line);
                }

                response = responce.toString();

                reader.close()  ;
                conn.disconnect();
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String inputLine;
                StringBuffer responce = new StringBuffer();

                while ((inputLine = in.readLine()) != null)
                    responce.append(inputLine);

                in.close();

                response = responce.toString();
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(LOG_TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    @SuppressLint("WrongConstant")
    public static String makePostUserAvatar(String preUrl, String locale, int id_token, int id_user, String token, String key, File file) {
        // creates a unique boundary based on time stamp
        boundary = "===" + System.currentTimeMillis() + "===";

        String response = null;
        try {
            URL url = new URL(preUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoOutput(true); // indicates POST method
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);
            conn.setRequestProperty("connection", "close");
            conn.setRequestProperty("Encoding",
                    locale); // Это и есть таинственные "хеддеры"
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Token",
                    token); // Это и есть таинственные "хеддеры"
            conn.setRequestProperty("Authorization", id_token + "_" +
                    MainUtil.md5(String.valueOf(id_token) + ":" + id_user + ":" + token)); // Это и есть таинственные "хеддеры"
            conn.setReadTimeout(240000);

            outputStream = conn.getOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(outputStream, Const.CHARSET),
                    true);


            addFilePart("avatar", file);
            outputStream.flush();

            writer.close();
            outputStream.close();

            int status = conn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                String line;
                StringBuffer responce = new StringBuffer();

                while ((line = reader.readLine()) != null) {
                    responce.append(line);
                }

                response = responce.toString();

                reader.close()  ;
                conn.disconnect();
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String inputLine;
                StringBuffer responce = new StringBuffer();

                while ((inputLine = in.readLine()) != null)
                    responce.append(inputLine);

                in.close();

                response = responce.toString();
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(LOG_TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    private static void addField(String name, String value) throws UnsupportedEncodingException {
        writer.append("--" + boundary).append(LINE_FEED);
        writer.append("Content-Disposition: form-data; name=\"" + name + "\"")
                .append(LINE_FEED);
        writer.append("Content-Type: text/plain; charset=" + Const.CHARSET).append(
                LINE_FEED);
        writer.append(LINE_FEED);
        writer.append(value).append(LINE_FEED);
        writer.flush();
    }


    /**
     * Adds a upload file section to the request
     *
     * @param fieldName  name attribute in <input type="file" name="..." />
     * @param uploadFile a File to be uploaded
     * @throws IOException
     */
    private static void addFilePart(String fieldName, File uploadFile)
            throws IOException {
        String fileName = uploadFile.getName();
        writer.append("--" + boundary).append(LINE_FEED);
//        writer.append(
//                "Content-Disposition: form-data; name=\"" + fieldName
//                        + "\"; filename=\"" + fileName + "\"")
//                .append(LINE_FEED);
        writer.append(
                "Content-Type: multipart/form-data"
                        + URLConnection.guessContentTypeFromName(fileName))
                .append(LINE_FEED);
        writer.append(LINE_FEED);
        writer.flush();

        FileInputStream inputStream = new FileInputStream(uploadFile);
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();
        inputStream.close();

        writer.append(LINE_FEED);
        writer.flush();
    }
}
