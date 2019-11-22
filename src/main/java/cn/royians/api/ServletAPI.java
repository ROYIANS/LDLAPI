package cn.royians.api;

import cn.royians.utils.HttpClientUtil;
import cn.royians.utils.XMLUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.springframework.web.bind.annotation.*;

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
