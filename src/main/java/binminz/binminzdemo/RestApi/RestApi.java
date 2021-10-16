package binminz.binminzdemo.RestApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

//@RestController
public class RestApi {

    public void xmlTojson (String str){
        try {
            String xml = str;
            JSONObject job = XML.toJSONObject(xml);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);

            Object json = mapper.readValue(job.toString(), Object.class);
            String output = mapper.writeValueAsString(json);
            System.out.println("output>>>>>>" + output);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @GetMapping("/GetcultureData")
    public String callAPI(){

        HashMap<String, Object> result = new HashMap<String,Object>();

        String jsonInString = "";

        try {

            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setConnectTimeout(5000); //타임아웃 설정 5초
            factory.setReadTimeout(5000);//타임아웃 설정 5초
            RestTemplate restTemplate = new RestTemplate(factory);

            HttpHeaders header = new HttpHeaders();
            HttpEntity<?> entity = new HttpEntity<>(header);

//            String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
//            UriComponents uri = UriComponentsBuilder.fromHttpUrl(url+"?"+"key=430156241533f1d058c603178cc3ca0e&targetDt=20120101").build();

            String url = "http://www.culture.go.kr/openapi/rest/publicperformancedisplays/period?from=20210914&to=20210914&cPage=1&rows=10&serviceKey=zcE6gitk5tYGlmPdgRqKbu5UPMYdJ7XrgoPfvJ8C87D7RCFt7w4yAVh0%2FYjA2JHyxewW6N4%2Bq0PjoLoOoCW6kw%3D%3D";

            UriComponents uri = UriComponentsBuilder.fromHttpUrl(url).build();

            System.out.println("111111>>>>>>>>>>>>>>>>>>>>");

            //이 한줄의 코드로 API를 호출해 MAP타입으로 전달 받는다.
            ResponseEntity<Map> resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Map.class);

            System.out.println("222222>>>>>>>>>>>>>>>>>>>>");

            //            result.put("statusCode", resultMap.getStatusCodeValue()); //http status code를 확인
//            result.put("header", resultMap.getHeaders()); //헤더 정보 확인
//            result.put("body", resultMap.getBody()); //실제 데이터 정보 확인

            //데이터를 제대로 전달 받았는지 확인 string형태로 파싱해줌
//            ObjectMapper mapper = new ObjectMapper();
//            jsonInString = mapper.writeValueAsString(resultMap.getBody());

            //xml to json
            String xmlstr = resultMap.getBody().toString();
            xmlTojson(xmlstr);

            System.out.println("result>>>>>>>>>>>>" + jsonInString);


        } catch (HttpClientErrorException | HttpServerErrorException e) {
            result.put("statusCode", e.getRawStatusCode());
            result.put("body"  , e.getStatusText());
            System.out.println("http exception error!!");
            System.out.println(e.toString());

        } catch (Exception e) {
            result.put("statusCode", "999");
            result.put("body"  , "excpetion 오류!!");
            System.out.println(e.toString());
        }

        return jsonInString;

    }


}
