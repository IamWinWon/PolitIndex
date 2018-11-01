package ru.mrwinwon.poltindex.connection.util;

public class PostPhotoResult {
    private int requestResult;
    private String responce;

    /**
     * @param requestResult - код ответа сервера
     * @param responce      - ответ сервера
     */
    public PostPhotoResult(int requestResult, String responce) {
        super();
        this.requestResult = requestResult;
        this.responce = responce;
    }

    public int getRequestResult() {
        return requestResult;
    }

    public void setRequestResult(int requestResult) {
        this.requestResult = requestResult;
    }

    public String getResponce() {
        return responce;
    }

    public void setResponce(String responce) {
        this.responce = responce;
    }
}
