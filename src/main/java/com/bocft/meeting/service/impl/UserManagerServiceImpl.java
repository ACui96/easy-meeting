package com.bocft.meeting.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bocft.meeting.common.BeanUtils;
import com.bocft.meeting.common.MD5;
import com.bocft.meeting.common.TimeComparation;
import com.bocft.meeting.mapper.ConferenceMapper;
import com.bocft.meeting.mapper.DeptMapper;
import com.bocft.meeting.mapper.UserMapper;
import com.bocft.meeting.model.Conference;
import com.bocft.meeting.model.User;
import com.bocft.meeting.service.AttendeesService;
import com.bocft.meeting.service.UserManagerService;
import com.bocft.meeting.vo.UserManagerVo;
import com.bocft.meeting.vo.UserViewVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xz
 * @date 2022/7/14 16:56
 */
@Service
public class UserManagerServiceImpl extends ServiceImpl<UserMapper, User> implements UserManagerService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    DeptMapper deptMapper;
    @Autowired
    ConferenceMapper conferenceMapper;
    @Autowired
    AttendeesService attendeesService;
    //管理员添加用户
    @Override
    public boolean MAddUser(UserManagerVo userManagerVo) {
        System.out.println(userManagerVo.getUDept());
        //加密
        userManagerVo.setUPwd(MD5.encrypt(userManagerVo.getUPwd()));
        User user=new User();
        BeanUtils.copyProperties(userManagerVo,user);
        user.setUStatus(1);
        if(userManagerVo.getUVerifyAll().equals("管理员")){
            user.setUVerify(1);
        }else user.setUVerify(0);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("u_phone",user.getUPhone());
        //1、原来没有，新增user
        if(userMapper.selectCount(wrapper)==0||userMapper.selectOne(wrapper).getUStatus()==0){
            user.setUDeptId(Integer.parseInt(userManagerVo.getUDept()));
            userMapper.insert(user);
            return true;
        }
        //2、用户已存在
        else return false;
    }
    //查询所有用户信息
    @Override
    public List<UserViewVo> findAllUser() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("u_status",1);
        wrapper.last("order by u_verify desc,u_dept_id asc");
        List<User> userList = userMapper.selectList(wrapper);
        List<UserViewVo> voList=new ArrayList<>();
        //转换实体类
        for (User user:userList) {
            UserViewVo vo=new UserViewVo();
            BeanUtils.copyProperties(user, vo);
            if(user.getUVerify()==1) vo.setUVerifyAll("管理员");
            else vo.setUVerifyAll("用户");
            QueryWrapper wrapper1 = new QueryWrapper();
            wrapper1.eq("dept_id",user.getUDeptId());
            String dept=deptMapper.selectOne(wrapper1).getDName();

            vo.setUDeptAll(dept);
            Long id=user.getUserId();
            vo.setUserId(String.valueOf(id));
            voList.add(vo);
        }
        return voList;
    }
    @Override
    //删除用户信息
    //2代表有正在预约中的会议
    //1删除成功
    //0未知错误
    public int deleteUser(String userId){
        //判断用户是否预约了会议（未开始或正在进行中）
        Long uId = Long.parseLong(userId);
        QueryWrapper queryWrapper1=new QueryWrapper();
        queryWrapper1.eq("user_id",uId);
        List<Conference> conferenceList=conferenceMapper.selectList(queryWrapper1);
        for (Conference conference:conferenceList) {
            int status = TimeComparation.comparation(conference.getCDate(),conference.getCBeginTime(),conference.getCEndTime());
            if(status==0||status==1){
                return 2;
            }
        }
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("user_id",Long.parseLong(userId));
        User user = userMapper.selectOne(queryWrapper);
        if(user!=null&&user.getUStatus()==1) {
            user.setUStatus(0);
            userMapper.updateById(user);
            attendeesService.deleteByUId(uId);
            return 1;
        }
        return 0;
    }
    @Override
    //根据id获取用户信息
    public UserViewVo SelectUser(String userId){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("user_id",Long.parseLong(userId));
        queryWrapper.eq("u_status",1);
        User user = userMapper.selectOne(queryWrapper);
        if(user==null) return null;
        UserViewVo vo=new UserViewVo();
        BeanUtils.copyProperties(user,vo);
        if(user.getUVerify()==1) vo.setUVerifyAll("管理员");
        else vo.setUVerifyAll("用户");
        QueryWrapper wrapper1 = new QueryWrapper();
        wrapper1.eq("dept_id",user.getUDeptId());
        String dept=deptMapper.selectOne(wrapper1).getDName();
        vo.setUDeptAll(dept);
        Long id=user.getUserId();
        vo.setUserId(String.valueOf(id));
        return vo;
    }
    //修改用户信息
    @Override
    public boolean updateUser(UserManagerVo userManagerVo) {
        //获取对象，转换成user
        String uId = userManagerVo.getUId();
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("user_id",Long.parseLong(uId));
        User user = userMapper.selectOne(queryWrapper);
        //判断user是否存在
        if(user==null||user.getUStatus()==0) return false;
        User user1=new User();
        BeanUtils.copyProperties(userManagerVo,user1);
        if(userManagerVo.getUVerifyAll().equals("管理员")){
            user1.setUVerify(1);
        }else user1.setUVerify(0);
        user1.setUserId(Long.parseLong(userManagerVo.getUId()));
        user1.setUPwd(user.getUPwd());
        user1.setUStatus(1);
        user1.setUDeptId(Integer.parseInt(userManagerVo.getUDept()));
        int i = userMapper.updateById(user1);
        if(i==1) return true;
        return false;
    }
    //按姓名查询
    @Override
    public List<UserViewVo> findByName(String name) {
        List<UserViewVo> userViewVoList=new ArrayList<>();
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.like("u_name",name);
        queryWrapper.last("order by u_verify desc,u_dept_id asc");
        List<User> userList=new ArrayList<>();
        userList = userMapper.selectList(queryWrapper);
        for (User user:userList) {
            if(user.getUStatus()==0) continue;
            UserViewVo vo=new UserViewVo();
            BeanUtils.copyProperties(user, vo);
            if(user.getUVerify()==1) vo.setUVerifyAll("管理员");
            else vo.setUVerifyAll("用户");
            QueryWrapper wrapper1 = new QueryWrapper();
            wrapper1.eq("dept_id",user.getUDeptId());
            String dept=deptMapper.selectOne(wrapper1).getDName();
            vo.setUDeptAll(dept);
            Long id=user.getUserId();
            vo.setUserId(String.valueOf(id));
            userViewVoList.add(vo);
        }
        return userViewVoList;

    }
    //按部门查询
    @Override
    public List<UserViewVo> findByDeptId(String tDeptId) {
        List<UserViewVo> userViewVoList=new ArrayList<>();
        int deptId=Integer.parseInt(tDeptId);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("u_dept_id",deptId);
        queryWrapper.last("order by u_verify desc,u_name asc");
        List<User> userList=userMapper.selectList(queryWrapper);
        for (User user:userList) {
            if(user.getUStatus()==0) continue;
            UserViewVo vo=new UserViewVo();
            BeanUtils.copyProperties(user, vo);
            if(user.getUVerify()==1) vo.setUVerifyAll("管理员");
            else vo.setUVerifyAll("用户");
            QueryWrapper wrapper1 = new QueryWrapper();
            wrapper1.eq("dept_id",user.getUDeptId());
            String dept=deptMapper.selectOne(wrapper1).getDName();
            vo.setUDeptAll(dept);
            Long id=user.getUserId();
            vo.setUserId(String.valueOf(id));
            userViewVoList.add(vo);
        }
        return userViewVoList;

    }
    //用户自己更新信息
    @Override
    public boolean updateByUser(UserManagerVo userManagerVo) {
            //获取对象，转换成user
            String uId = userManagerVo.getUId();
             QueryWrapper queryWrapper=new QueryWrapper();
             queryWrapper.eq("user_id",Long.parseLong(uId));
             User user = userMapper.selectOne(queryWrapper);
            //判断user是否存在
            if(user==null||user.getUStatus()==0) return false;

            User user1=new User();
            BeanUtils.copyProperties(userManagerVo,user1);
            //用户id
            user1.setUserId(Long.parseLong(userManagerVo.getUId()));
            //密码
            if(user1.getUPwd().length()!=0){
                user1.setUPwd(MD5.encrypt(user1.getUPwd()));
            }
            else user1.setUPwd(user.getUPwd());

            //部门
            if(userManagerVo.getUDept().length()==0){
                user1.setUDeptId(user.getUDeptId());
            }else user1.setUDeptId(Integer.parseInt(userManagerVo.getUDept()));
            //权限
            user1.setUStatus(1);
            user1.setUVerify(user.getUVerify());
            int i = userMapper.updateById(user1);
            if(i==1) return true;
            return false;

    }


}
