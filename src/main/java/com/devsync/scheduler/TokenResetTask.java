package com.devsync.scheduler;

import java.util.TimerTask;

public class TokenResetTask extends TimerTask {

    @Override
    public void run() {
        resetDailyTokens();
    }

    private void resetDailyTokens() {
        System.out.println("Réinitialisation des jetons quotidiens...");
    }
}
