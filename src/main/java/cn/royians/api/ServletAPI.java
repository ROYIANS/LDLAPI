package cn.royians.api;

import cn.royians.utils.HttpClientUtil;
import cn.royians.utils.XMLUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
}
