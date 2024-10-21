package com.devsync.scheduler;

import com.devsync.service.UserService;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Scheduler {

    public static void main(String[] args) {
        Timer timer = new Timer();

        // Tâche quotidienne pour les jetons
        TimerTask dailyTokenResetTask = new TokenResetTask();
        scheduleDailyTask(timer, dailyTokenResetTask);

        // Tâche mensuelle pour les jetons
        TimerTask monthlyTokenResetTask = new MonthlyTokenResetTask(new UserService());
        scheduleMonthlyTask(timer, monthlyTokenResetTask);
    }

    private static void scheduleDailyTask(Timer timer, TimerTask task) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date firstRunTime = calendar.getTime();
        if (firstRunTime.before(new Date())) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            firstRunTime = calendar.getTime();
        }

        long period = 24 * 60 * 60 * 1000; // 24 heures en millisecondes
        timer.scheduleAtFixedRate(task, firstRunTime, period);
    }

    private static void scheduleMonthlyTask(Timer timer, TimerTask task) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1); // Premier jour du mois
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date firstRunTime = calendar.getTime();
        if (firstRunTime.before(new Date())) {
            calendar.add(Calendar.MONTH, 1); // Si déjà passé, ajoute un mois
            firstRunTime = calendar.getTime();
        }

        long period = 30L * 24 * 60 * 60 * 1000; // 30 jours en millisecondes
        timer.scheduleAtFixedRate(task, firstRunTime, period);
    }
}
