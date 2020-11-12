package com.github.binarywang.demo.wx.mp.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.core.JsonGenerator;
import com.github.binarywang.demo.wx.mp.builder.TextBuilder;
import com.github.binarywang.demo.wx.mp.dao.THjcczzTablelistMapper;
import com.github.binarywang.demo.wx.mp.dao.UserInfoMapper;
import com.github.binarywang.demo.wx.mp.entity.THjcczzTablelist;
import com.github.binarywang.demo.wx.mp.entity.THjcczzTablelistExample;
import com.github.binarywang.demo.wx.mp.entity.UserInfo;
import com.github.binarywang.demo.wx.mp.entity.UserInfoExample;
import com.github.binarywang.demo.wx.mp.utils.JsonUtils;
import lombok.extern.log4j.Log4j2;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
@Log4j2
public class MsgHandler extends AbstractHandler {
    @Autowired
    private THjcczzTablelistMapper tHjcczzTablelistMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;


    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {
        String content = "收到信息内容：" + JsonUtils.toJson(wxMessage);
        if (!wxMessage.getMsgType().equals(XmlMsgType.EVENT)) {
            //TODO 可以选择将消息保存到本地
        }

        //当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
        try {
            if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")
                && weixinService.getKefuService().kfOnlineList()
                .getKfOnlineList().size() > 0) {
                return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE()
                    .fromUser(wxMessage.getToUser())
                    .toUser(wxMessage.getFromUser()).build();
            } else if (StringUtils.startsWithAny(wxMessage.getContent(), "测试")){
                logger.info("用户抽奖");
                content = "收到信息内容：用户序号：1";
                return new TextBuilder().build(content, wxMessage, weixinService);
            } else if (StringUtils.startsWithAny(wxMessage.getContent(), "抽奖")){
                logger.info("用户抽奖");
                String userToken=wxMessage.getFromUser();
                UserInfoExample example = new UserInfoExample();
                example.createCriteria().andUseTokenEqualTo(userToken);
                List<UserInfo> userList =  userInfoMapper.selectByExample(example);
                log.info("用户信息查询结果结果 : {}", JSONArray.toJSON(userList));
                if(userList == null || userList.size() == 0){
                    UserInfo newUserInfoRecord = new UserInfo();
                    newUserInfoRecord.setUseToken(userToken);
                    log.info("插入新用户信息 : {}", JSON.toJSON(newUserInfoRecord));
                    userInfoMapper.insert(newUserInfoRecord);
                    log.info("插入新用户信息，结果 : {}", JSON.toJSON(newUserInfoRecord));
                    content = "用户序号 : " + String.valueOf(newUserInfoRecord.getId());
                    log.info(content);
                    return new TextBuilder().build(content, wxMessage, weixinService);
                }
                content = "用户序号 : " + String.valueOf(userList.get(0).getId());
                log.info(content);
                return new TextBuilder().build(content, wxMessage, weixinService);
            } else {
                JSONObject data = new JSONObject();
                THjcczzTablelistExample example = new THjcczzTablelistExample();
                example.createCriteria().andIdBetween(1,2);
                List<THjcczzTablelist> list =  tHjcczzTablelistMapper.selectByExample(example);
                log.info("===================================");
                log.info(list);
                content = data.toString();
                return new TextBuilder().build(content, wxMessage, weixinService);
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        //TODO 组装回复消息
        content = "收到信息内容：" + JsonUtils.toJson(wxMessage);
        return new TextBuilder().build(content, wxMessage, weixinService);

    }

}
