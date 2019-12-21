package WorkWithGraphics;

import WorkWithData.GetDataFromDb;

import java.util.ArrayList;

public class LeastSquares {

    public static float[] linLeastSquares(ArrayList<GetDataFromDb> y, String value) {
        //y = ax + b
        float[] result = new float[3];
        int n = y.size();
        float sumX = 0, sumY = 0, sumX_Quad = 0, sumXY = 0;
        float[] x = new float[y.size()];
        for (int i = 0; i < n; i++) {
            x[i] = i;
            sumX += x[i]; //сумма Х
            sumY += y.get(i).getCorrectData(value); //сумма У
            sumX_Quad += x[i] * x[i]; //сумма квадратов Х
            sumXY += x[i] * y.get(i).getCorrectData(value); //сумма Х*У
        }
        result[0] = (y.size() * sumXY - sumX * sumY) / (y.size() * sumX_Quad - sumX * sumX); //коэффициент а
        result[1] = (sumY - result[0] * sumX) / y.size(); //коэффициент b
        //y = ax + b = result[0]*x + result[1]
        float[] approx = new float[n];
        float[] ourdata = new float[n];
        for (int i = 0; i < n; i++) {
            approx[i] = result[0] * i + result[1];
            ourdata[i] = y.get(i).getCorrectData(value);
        }
        result[2] = percent_Mistake(ourdata, approx);
        return result;
    }

    public static float[] quadLeastSquares(ArrayList<GetDataFromDb> y, String value) {
        //y = a*x*x + b*x + c
        float[] result = new float[4];
        int n = y.size();
        float sum_y = 0;
        float sum_xy = 0;
        float sum_x2y = 0;
        float sum_x = 0;
        float sum_x_2 = 0;
        float sum_x_3 = 0;
        float sum_x_4 = 0;
        for (int i = 0; i < n; i++) {
            sum_y += y.get(i).getCorrectData(value);
            sum_xy += y.get(i).getCorrectData(value) * i;
            sum_x2y += y.get(i).getCorrectData(value) * i * i;
            sum_x += i;
            sum_x_2 += i * i;
            sum_x_3 += i * i * i;
            sum_x_4 += i * i * i * i;
        }
        float det = n * sum_x_2 * sum_x_4 + sum_x_3 * sum_x * sum_x_2 + sum_x_3 * sum_x * sum_x_2
                - sum_x_2 * sum_x_2 * sum_x_2 - sum_x_3 * sum_x_3 * n - sum_x_4 * sum_x * sum_x;
        float det1 = n * sum_x_2 * sum_x2y + sum_y * sum_x * sum_x_3 + sum_xy * sum_x * sum_x_2
                - sum_y * sum_x_2 * sum_x_2 - sum_x_3 * n * sum_xy - sum_x2y * sum_x * sum_x;
        float det2 = n * sum_xy * sum_x_4 + sum_x2y * sum_x * sum_x_2 + sum_x_3 * sum_y * sum_x_2
                - sum_x_2 * sum_x_2 * sum_xy - sum_x_3 * sum_x2y * n - sum_x_4 * sum_y * sum_x;
        float det3 = sum_y * sum_x_2 * sum_x_4 + sum_x_3 * sum_xy * sum_x_2 + sum_x_3 * sum_x * sum_x2y
                - sum_x_2 * sum_x_2 * sum_x2y - sum_x_3 * sum_x_3 * sum_y - sum_x_4 * sum_x * sum_xy;
        result[0] = det1 / det;
        result[1] = det2 / det;
        result[2] = det3 / det;
        float[] approx = new float[n];
        float[] ourdata = new float[n];
        for (int i = 0; i < n; i++) {
            approx[i] = result[0] * i * i + result[1] * i + result[2];
            ourdata[i] = y.get(i).getCorrectData(value);
        }
        result[3] = percent_Mistake(ourdata, approx);

        return result;
    }

    public static float[] expLeastSquares(ArrayList<GetDataFromDb> y, String value) {
        //F(x) = a * e^(b*x) = ln(a) + b*x
        float[] result = new float[3];
        int n = y.size();
        float sumLNy = 0, sumX_Quad = 0, sumLNyx = 0, sumX = 0;
        for (int i = 0; i < n; i++) {
            sumLNy += Math.log(y.get(i).getCorrectData(value)); //сумма ln(y)
            sumLNyx += Math.log(y.get(i).getCorrectData(value)) * i; //сумма ln(y)*x
            sumX_Quad += i * i; //сумма квадратов x
            sumX += i; //сумма x
        }
        result[0] = (sumLNy * sumX_Quad - sumLNyx * sumX) / (n * sumX_Quad - sumX * sumX); //коэффициент ln(a)
        result[1] = (n * sumLNyx - sumLNy * sumX) / (n * sumX_Quad - sumX * sumX); //коэффициент b
        //F(x) = a * e^(b*x) = ln(a) + b*x = result[0] + result[1]*x

        float[] approx = new float[n];
        float[] ourdata = new float[n];
        for (int i = 0; i < n; i++) {
            approx[i] = (float) (Math.exp(result[0]) * Math.exp(result[1] * i));
            ourdata[i] = y.get(i).getCorrectData(value);
        }
        result[2] = percent_Mistake(ourdata, approx);

        return result;
    }

    public static float[] hypLeastSquares(ArrayList<GetDataFromDb> y, String value) {
        // y = a + b/x
        float[] result = new float[3];
        int n = y.size();
        float sumYnaX = 0, sum1naX = 0, sumY = 0, sum1naX_Quad = 0;
        for (int i = 0; i < n; i++) {
            if (i != 0) {
                sum1naX += 1 / (i); //сумма 1/x
                sum1naX_Quad += 1 / ((i) * (i)); //сумма 1/x*x
                sumYnaX += y.get(i).getCorrectData(value) / (i); //сумма y/x
            }
            sumY += y.get(i).getCorrectData(value); //сумма y
        }
        result[1] = (n * sumYnaX - sum1naX * sumY) / (n * sum1naX_Quad - sum1naX * sum1naX); //коэффициент b
        result[0] = sumY / n - result[1] * sum1naX / n; //коэффициент a
        // y = a + b/x = result[0] + result[1]/x
        float[] approx = new float[n];
        float[] ourdata = new float[n];
        approx[0] = 1;
        ourdata[0] = 1;
        for (int i = 1; i < n; i++) {
            approx[i] = result[0] + result[1] / i;
            ourdata[i] = y.get(i).getCorrectData(value);
        }
        result[2] = percent_Mistake(ourdata, approx);

        return result;
    }

    public static float percent_Mistake(float[] data, float[] approx_Data) {
        float summa = 0;
        for (int i = 0; i < data.length; i++) {
            summa += Math.abs((data[i] - approx_Data[i]) / data[i]);
        }
        return summa / data.length * 100;
    }
}
