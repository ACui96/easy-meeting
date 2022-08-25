package com.bocft.meeting.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.bocft.meeting.common.*;
import com.bocft.meeting.common.exception.ErrorCode;
import com.bocft.meeting.common.validator.AssertUtils;
import com.bocft.meeting.model.User;
import com.bocft.meeting.service.CaptchaService;
import com.bocft.meeting.service.ConferenceService;
import com.bocft.meeting.service.LoginService;
import com.bocft.meeting.vo.LoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Api(tags="登陆")
@Controller
@RequestMapping("/api/userLogin")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private PhoneUtil phoneUtil;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private ConferenceService conferenceService;

    @PostMapping("/loginByEmail")
    @ResponseBody
    public Result loginByEmail(@RequestParam(name = "邮箱")String uMail,
                 @RequestParam(name = "密码")String uPwd){
        //MD5加密
        String pwd = MD5.encrypt(uPwd);
        User user=loginService.selectUserByEmail(uMail);
        if (user==null){
            return Result.fail(ResultCodeEnum.EMAIL_FAIL);
        }
        String userUPwd = user.getUPwd();
//        生成token
//        String token =loginService.getToken(user);
        Map<String,String> payload = new HashMap<>();
        payload.put("userId", String.valueOf(user.getUserId()));
        payload.put("uMail", user.getUMail());
        String token = JwtUtil.createJWT(payload);

        Map<String,Object> map = new HashMap<>();

        if (pwd.equals(userUPwd)){

            map.put("user",user);
            map.put("token",token);
            map.put("msg","登录成功");
            map.put("code",200);
        }
        else {
            return Result.fail("密码不正确");

        }
        return Result.ok(map);

    }

    @PostMapping("/login")
    @ResponseBody
    public Result login(@RequestParam(name = "手机号")String uPhone,
                        @RequestParam(name = "密码")String uPwd){
        //MD5加密
        String pwd = MD5.encrypt(uPwd);
        User user = loginService.selectUserByPhone(uPhone);
        if (user==null){
            return Result.fail(ResultCodeEnum.PHONE_FAIL);
        }
        if (user.getUStatus() == 0) {
            return Result.fail(ResultCodeEnum.PERMISSION);
        }
        String userUPwd = user.getUPwd();
        // 生成token
        Map<String,String> payload = new HashMap<>();
        payload.put("userId", String.valueOf(user.getUserId()));
        payload.put("uMail", user.getUMail());
        String token = JwtUtil.createJWT(payload);

        Map<String,Object> map = new HashMap<>();

        if (pwd.equals(userUPwd)){

            map.put("user",user);
            map.put("token",token);
            map.put("msg","登录成功");
            map.put("code",200);
        }
        else {
            return Result.fail("密码不正确");

        }
        return Result.ok(map);

    }


    @PostMapping("/loginUseCaptcha")
    @ResponseBody
    public Result loginUseCaptcha(HttpServletRequest request, @RequestBody LoginVo loginVo){

        // 验证码判断
        boolean flag = captchaService.validate(loginVo.getUuid(), loginVo.getCaptcha());
        if (!flag) {
            return Result.fail(ResultCodeEnum.CAPTCHA_FALSE);
        }

        // 账号、密码
        String uPwd = loginVo.getPassword();
        String uPhone = loginVo.getUPhone();



        User user = loginService.selectUserByPhone(uPhone);
        // 用户不存在
        if (user==null){
            return Result.fail(ResultCodeEnum.PHONE_FAIL);
        }
        // 用户被禁用
        if (user.getUStatus() == 0) {
            return Result.fail(ResultCodeEnum.PERMISSION);
        }

        String userUPwd = user.getUPwd();


        Map<String,Object> map = new HashMap<>();
        //MD5加密
        String pwd = MD5.encrypt(uPwd);
        if (pwd.equals(userUPwd)){
            // 生成token
            Map<String,String> payload = new HashMap<>();
            payload.put("userId", String.valueOf(user.getUserId()));
            payload.put("uMail", user.getUMail());
            String token = JwtUtil.createJWT(payload);

            map.put("user",user);
            map.put("token",token);
            map.put("msg","登录成功");
            map.put("code",200);
        }
        else {
            return Result.fail(ResultCodeEnum.PASSWORD_ERROR);
        }
        return Result.ok(map);

    }


    @GetMapping("/captcha")
    @ApiOperation(value = "验证码", produces = "application/octet-stream")
    @ApiImplicitParam(paramType = "query", dataType = "string", name = "uuid", required = true)
    public void captcha(HttpServletResponse response, String uuid) throws IOException {
        //uuid不能为空
        AssertUtils.isBlank(uuid, ErrorCode.IDENTIFIER_NOT_NULL);

        //生成验证码
        captchaService.create(response, uuid);
    }

    @ApiOperation(value = "忘记密码-手机号验证码登录")
    @PostMapping("/loginByPhone")
    @ResponseBody
    public Result addUserByPhone(@RequestParam(name = "phone")String uPhone,
                                 @RequestParam(name = "code") String code) {
        // 获取验证码真值
        if (!phoneUtil.phoneNumberIsValid(uPhone)) {
            return Result.fail(ResultCodeEnum.PARAM_ERROR.getMessage());
        }
        String mobleCode = (String) redisTemplate.opsForValue().get(uPhone);

        // 判断验证码
        if (!code.equals(mobleCode)) {
            throw new ConferenceException("验证码不正确", 400);
        }

        User user = loginService.selectUserByPhone(uPhone);


        Map<String, Object> map = new HashMap<>();
        // 生成token
        Map<String, String> payload = new HashMap<>();
        payload.put("userId", String.valueOf(user.getUserId()));
        payload.put("uMail", user.getUMail());
        String token = JwtUtil.createJWT(payload);

        //用户登录
        map.put("user", user);
        map.put("token", token);
        map.put("msg", "登录成功");
        map.put("code", 200);
        return Result.ok(map);
    }

    @ApiOperation(value = "发送登录短信验证码")
    @GetMapping("/sendMsg/{phone}")
    @ResponseBody
    public Result sendSms(@PathVariable() String phone){
        if (!phoneUtil.phoneNumberIsValid(phone)){
            return Result.fail("手机号格式不对");
        }

        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isBlank(code)) {
            return Result.fail("验证码未过期，请填写验证码");
        }
        User user = loginService.selectUserByPhone(phone);
        //判断手机是否注册
        if (user==null){
            return Result.fail("账号未注册或被禁用");
        }

        //如果从redis获取不到，生成验证码
        code = RandomUtil.getSixBitRandom();
        // 模板 ：{1}为您的验证码，请于{2}分钟内填写，如非本人操作，请忽略本短信。
        String[] paramSet = new String[]{code, "2"};
        String[] phoneNumberset = new String[]{phone};
        phoneUtil.sendMessage(phoneNumberset, paramSet, Constant.TEMPLATE_ID_CODE);
        redisTemplate.opsForValue().set(phone,code,2, TimeUnit.MINUTES);
        return Result.ok("验证码已经发送至您的手机，请注意查收");

    }
}
