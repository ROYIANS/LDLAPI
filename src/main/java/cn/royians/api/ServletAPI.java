package cn.royians.api;

import cn.royians.utils.HttpClientUtil;
import cn.royians.utils.XMLUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@CrossOrigin
@RestController
public class ServletAPI {
    @RequestMapping("/")
    public String hello() {
        return "Hello World";
    }

    @ResponseBody
    @RequestMapping("/getTelegram")
    public JSONObject getTelegram(@RequestParam(required = false, defaultValue = "LittleDreamlandPlus") String name) {
        JSONObject json;
        try {
            String s = HttpClientUtil.httpGet("https://rsshub.app/telegram/channel/" + name);
            Document document = DocumentHelper.parseText(s);
            json = new JSONObject();
            XMLUtil.dom4j2Json(document.getRootElement(), json);
        } catch (Exception e) {
            json = JSON.parseObject("{'error':'500'}");
            e.printStackTrace();
        }
        return json;
    }

    @ResponseBody
    @RequestMapping("/getTwitter")
    public JSONObject getTwitter(@RequestParam(defaultValue = "AlexMtzyj", required = false) String name) {
        JSONObject json;
        try {
            String s = HttpClientUtil.httpGet("https://rsshub.app/twitter/user/" + name);
            Document document = DocumentHelper.parseText(s);
            json = new JSONObject();
            XMLUtil.dom4j2Json(document.getRootElement(), json);
        } catch (Exception e) {
            json = JSON.parseObject("{'error':'500','info':'" + e.getMessage() + "'}");
        }
        return json;
    }

    @ResponseBody
    @RequestMapping("/test")
    public String test() {
        RestTemplate restTemplate = new RestTemplate();
		String url = "https://redirector.googlevideo.com/videoplayback?api=youtubemultidownloader.com&expire=1575016202&ei=qYLgXYSiN5On1wLNu6y4CA&ip=2a03%3Ab0c0%3A3%3Ae0%3A%3Ae6%3Af001&id=o-APB0LDLLB5FfWG-0fmU8hPPQU8ejM8plu2RmOKaWUwnC&itag=137&aitags=133%2C134%2C135%2C136%2C137%2C160%2C242%2C243%2C244%2C247%2C248%2C278%2C298%2C299%2C302%2C303&source=youtube&requiressl=yes&mm=31%2C26&mn=sn-4g5e6nsr%2Csn-f5f7lnel&ms=au%2Conr&mv=m&mvi=0&pl=48&initcwndbps=225000&mime=video%2Fmp4&gir=yes&clen=410313048&dur=1500.040&lmt=1574652681689759&mt=1574994512&fvip=1&keepalive=yes&fexp=23842630&txp=5535432&sparams=expire%2Cei%2Cip%2Cid%2Caitags%2Csource%2Crequiressl%2Cmime%2Cgir%2Cclen%2Cdur%2Clmt&sig=ALgxI2wwRQIhAMsVswqx-gR1K1_a9HNuzXfCezkF-hAubXPY1opdT-OQAiAxTxb1kkTzIGmdD_TzR5Xsp9vVSNx2liElqQb6OxtVfA%3D%3D&lsparams=mm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpl%2Cinitcwndbps&lsig=AHylml4wRAIgftbecU3_SLfYQAjfICzWkCV0S4-qxbdhGvLa9ntKLIACIFlsdN9sQbk3xusAqrWaXMeldl0CG-Ht3Sri7f_bBz-M&title=%E2%9C%96%EF%B8%8FEPIC+CHINA%3A+What+is+China%3F";
		String testurl = "https://www.baidu.com";
		String google = "https://www.google.com";
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(testurl, String.class);
		HttpStatus statusCode = responseEntity.getStatusCode();
		HttpHeaders headers = responseEntity.getHeaders();
		System.out.println(statusCode.toString());
		System.out.println(headers.toString());
        return "over";
    }

    @ResponseBody
    @RequestMapping("/getTwitterHTML")
    public String getTwitterHtml(@RequestParam(defaultValue = "AlexMtzyj", required = false) String name) {
        StringBuilder html = new StringBuilder();
        JSONObject twitter = this.getTwitter(name);
        JSONArray jsonArray = twitter.getJSONObject("channel").getJSONArray("item");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            html.append("<p><span>");
            html.append(item.get("author"));
            html.append("</span><small>");
            html.append(item.get("pubDate"));
            html.append("</small><br/><span>");
            html.append(item.get("description"));
            html.append("</span></p><hr/>");
        }
        return html.toString();
    }
}
