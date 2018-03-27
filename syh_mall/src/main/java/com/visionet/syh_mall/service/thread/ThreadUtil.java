package com.visionet.syh_mall.service.thread;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.visionet.syh_mall.entity.User;
import com.visionet.syh_mall.repository.UserRepository;

@Component
public class ThreadUtil implements Runnable {
	
	@Autowired
	private UserRepository userDao;
	
	private static List<String> list;
	
	public static void setList(List<String> list) {
		ThreadUtil.list = list;
	}
	
	
	@Override
	public void run() {
		for (String userId : list) {
			User user = userDao.findOne(userId);
			System.out.println(user);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
