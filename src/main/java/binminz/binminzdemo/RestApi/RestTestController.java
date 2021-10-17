package binminz.binminzdemo.RestApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
public class RestTestController {

//    @GetMapping("/apitest")
//    public String callApiWithXml(){
//
//        StringBuffer rslt = new StringBuffer();
//
//        try{
//            String urlstr = "http://www.culture.go.kr/openapi/rest/publicperformancedisplays/period"+
//                        "?from=20210914&to=20210914"+
//                        "&cPage=1"+
//                        "&rows=10"+
//                        "&serviceKey=zcE6gitk5tYGlmPdgRqKbu5UPMYdJ7XrgoPfvJ8C87D7RCFt7w4yAVh0%2FYjA2JHyxewW6N4%2Bq0PjoLoOoCW6kw%3D%3D";
//
//            URL url = new URL(urlstr);
//            HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
//            urlcon.setRequestMethod("GET");
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(urlcon.getInputStream(),"UTF-8"));
//
//            String returnLine;
//            rslt.append("<xmp>");
//
//            int i=0;
//            while((returnLine = br.readLine()) != null) {
//                rslt.append(returnLine + "\n");
//            }
//            urlcon.disconnect();
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return rslt+"</xmp>";
//    }

    @GetMapping("/apitest2")
    public String callApiWithJson() {
        StringBuffer result = new StringBuffer();
        String jsonPrintString = null;
        try {
            String apiUrl = "http://www.culture.go.kr/openapi/rest/publicperformancedisplays/period"+
            "?from=20210914&to=20210914"+
            "&cPage=1"+
            "&rows=10"+
            "&serviceKey=zcE6gitk5tYGlmPdgRqKbu5UPMYdJ7XrgoPfvJ8C87D7RCFt7w4yAVh0%2FYjA2JHyxewW6N4%2Bq0PjoLoOoCW6kw%3D%3D";


            URL url = new URL(apiUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
            String returnLine;
            while((returnLine = bufferedReader.readLine()) != null) {
                result.append(returnLine);
            }

            JSONObject jsonObject = XML.toJSONObject(result.toString());
            jsonPrintString = jsonObject.toString();
            return jsonPrintString;
            //파싱해서 TITLE만 가져와보기

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonPrintString;
    }
}
