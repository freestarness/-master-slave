package com.example.demo.service.impl;

import com.example.demo.annotation.TargetDataSource;
import com.example.demo.mapper.demo1.UserInfoMapper1;
import com.example.demo.mapper.demo2.UserInfoMapper2;
import com.example.demo.model.demo1.UserInfo1;
import com.example.demo.model.demo2.UserInfo2;
import com.example.demo.service.UserInfoService;
import com.example.demo.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Target;


/**
 *
 */
@Transactional
@Service
public class UserInfoServiceImpl implements UserInfoService {
	@Autowired
	private UserInfoMapper1 userInfoMaper1;
	@Autowired
	private UserInfoMapper2 userInfoMaper2;

	@Autowired
	private UserInfoService userInfoService;

	@Override
	public UserInfo1 listUseInfoFromDemo1(HttpServletRequest request) {
		String ip = getClientIp(request);
		System.out.print(111);
		return  userInfoMaper1.selectByPrimaryKey(1L);
	}

	@Override
	public UserInfo2 listUseInfoFromDemo2(HttpServletRequest request) {

		return userInfoMaper2.selectByPrimaryKey(1L);
	}

	/**
	 * 获取客户端IP
	 *
	 * @param request
	 * @return
	 */
	private String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}
}
