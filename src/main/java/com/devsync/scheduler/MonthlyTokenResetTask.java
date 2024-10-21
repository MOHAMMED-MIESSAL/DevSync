package com.devsync.scheduler;

import com.devsync.service.UserService;

import java.util.TimerTask;

public class MonthlyTokenResetTask extends TimerTask {

    private final UserService userService;

    public MonthlyTokenResetTask(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run() {
        userService.resetMonthlyTokens();
    }
}
