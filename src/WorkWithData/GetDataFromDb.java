package WorkWithData;

public class GetDataFromDb {
    String date;
    float open;
    float low;
    float high;
    float close;

    public GetDataFromDb(String date, float open, float low, float high, float close) {
        this.date = date;
        this.open = open;
        this.low = low;
        this.high = high;
        this.close = close;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getOpen() {
        return open;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public float getLow() {
        return low;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public float getHigh() {
        return high;
    }

    public void setHigh (float high) {
        this.high= high;
    }

    public float getClose() {
        return close;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public float getCorrectData(String v) {
        switch (v) {
            case ("Open"):
                return getOpen();
            case ("Close"):
                return getClose();
            case ("Low"):
                return getLow();
            case ("High"):
                return getHigh();
            default:
                return 0;
        }
    }

}
