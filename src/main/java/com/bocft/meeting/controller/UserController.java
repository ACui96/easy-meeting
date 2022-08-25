package com.bocft.meeting.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bocft.meeting.common.*;
import com.bocft.meeting.model.User;
import com.bocft.meeting.service.ConferenceService;
import com.bocft.meeting.service.UserService;
import com.bocft.meeting.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

@Api(tags="登录及用户相关")
@RestController
@Slf4j
public class UserController {

    @Autowired
    private  UserService userService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private PhoneUtil phoneUtil;

    @Autowired
    private ConferenceService conferenceService;


    @ApiOperation(value = "用户查询")
    @GetMapping("admin/user/selUser/{user_id}")
    public Result selUser(@PathVariable("user_id")Integer user_id){

        User user = userService.selByIdUser(user_id);
        return Result.ok(user);

    }

    @ApiOperation(value = "根据id删除用户数据", notes = "删除用户")
    @DeleteMapping(value = "admin/user/delUser/{user_id}")
    public Result delUser(@PathVariable("user_id") Integer user_id){
        int user=userService.delUser(user_id);
        if (user==0){
            return Result.fail("删除失败");
        }
        return Result.ok("成功删除"+"ID为："+user_id+"的用户");
    }



    @ApiOperation(value = "用户注册")
    @PostMapping("/api/userLogin/register")
    public Result addUser(@RequestBody @Valid UserVo userVo,@RequestParam(name = "code") String code){
        if (!EmailUtil.emailIsTrue(userVo.getUMail())){
            return Result.fail(ResultCodeEnum.PARAM_ERROR.getMessage());
        }
        String mobleCode = redisTemplate.opsForValue().get(userVo.getUMail());
        if(!code.equals(mobleCode)) {
            throw new ConferenceException("验证码不正确",400);
        }
        //注册默认普通用户
        userVo.setUVerify(0);
        boolean addUser = userService.addUser(userVo,code);
        if (addUser) {
            return Result.ok(ResultCodeEnum.SUCCESS.getMessage());
        }
        return Result.ok(ResultCodeEnum.FAIL.getMessage());
    }

    @ApiOperation(value = "用户注册-手机号验证码注册")
    @PostMapping("/api/userLogin/addUserByPhone")
    public Result addUserByPhone(@RequestBody @Valid User user,@RequestParam(name = "code") String code){
        if (!phoneUtil.phoneNumberIsValid(user.getUPhone())){
            return Result.fail(ResultCodeEnum.PARAM_ERROR.getMessage());
        }
        String mobleCode = (String) redisTemplate.opsForValue().get(user.getUPhone());
        if(!code.equals(mobleCode)) {
            throw new ConferenceException("验证码不正确",400);
        }
        // 注册默认普通用户
        user.setUVerify(0);
        // 状态默认为 1
        user.setUStatus(1);
        // 密码加密存储
        String uPwdOrigin = user.getUPwd();
        String uPwdNew = MD5.encrypt(uPwdOrigin);
        user.setUPwd(uPwdNew);

        boolean addUser = userService.save(user);
        if (addUser) {
            return Result.ok(ResultCodeEnum.SUCCESS.getMessage());
        }
        return Result.ok(ResultCodeEnum.FAIL.getMessage());
    }

    @ApiOperation(value = "管理员添加用户")
    @PostMapping("/api/userLogin/addUser")
    public Result addUser(@RequestBody @Valid UserVo userVo){
        if (!EmailUtil.emailIsTrue(userVo.getUMail())){
            return Result.fail(ResultCodeEnum.PARAM_ERROR.getMessage());
        }
        if (conferenceService.userIsTrue(userVo.getUMail())) {
            return Result.fail("邮箱已经存在");
        }
        User user=new User();
        BeanUtils.copyProperties(userVo,user);
        boolean addUser = userService.save(user);
        if (addUser) {
            return Result.ok(ResultCodeEnum.SUCCESS.getMessage());
        }
        return Result.ok(ResultCodeEnum.FAIL.getMessage());
    }

    @ApiOperation(value = "根据id更新用户数据", notes = "更新用户")
    @PutMapping(value = "admin/user/updateUser")
    public Result updateUser(@RequestBody User user){
        if (!EmailUtil.emailIsTrue(user.getUMail())){
            return Result.fail(ResultCodeEnum.PARAM_ERROR.getMessage());
        }

        boolean  bool = userService.updateUser(user);
        if (!bool){
            return Result.fail("修改失败");
        }
        return Result.ok("修改成功");
    }

    @ApiOperation(value = "分页查找所有用户信息", notes = "用户列表")
    @GetMapping(value = "admin/user/selAllUser")

    public Result selAllUser(@RequestParam(name = "当前页") Long current,@RequestParam(name = "页面数量") Long size){
        Page page = PageUtils.CurrentPage(current,size);
        Page<User> userPage = userService.page(page);
        return Result.ok(userPage);
    }

    @ApiOperation(value = "邮箱发送验证码")
    @GetMapping("/api/userLogin/send/{email}")
    @ResponseBody
    public Result sendEmail(@PathVariable() String email){
        if (!EmailUtil.emailIsTrue(email)){
            return Result.fail("邮箱格式不对");
        }
        //判断邮箱是否存在
        if (conferenceService.userIsTrue(email)) {
            return Result.fail("邮箱已经存在");
        }
        String code = redisTemplate.opsForValue().get(email);
        if(!StringUtils.isBlank(code)) {
            return Result.fail("验证码未过期，请填写验证码");
        }
        //如果从redis获取不到，
        // 生成验证码，
        code = RandomUtil.getSixBitRandom();
        emailUtil.sendEmail(email,"验证码","欢迎注册中银金科会议室预订系统，您的验证码是："+code
                +"验证码2分钟有效，请尽快填写",mailSender);
        redisTemplate.opsForValue().set(email,code,2, TimeUnit.MINUTES);
        return Result.ok("验证码已经发送，请注意查收");
    }

    @ApiOperation(value = "发送注册短信验证码")
    @GetMapping("/api/userLogin/sendSms/{phone}")
    @ResponseBody
    public Result sendSms(@PathVariable() String phone){
        if (!phoneUtil.phoneNumberIsValid(phone)){
            return Result.fail("手机号格式不对");
        }
        //判断手机是否存在
        if (conferenceService.userPhoneAlreadyExist(phone)) {
            return Result.fail("手机号已经注册");
        }
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isBlank(code)) {
            return Result.fail("验证码未过期，请填写验证码");
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
