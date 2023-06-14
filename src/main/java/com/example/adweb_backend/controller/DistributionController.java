package com.example.adweb_backend.controller;

import com.example.adweb_backend.util.JWTToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("")
public class DistributionController {

    public static Lock lock = new ReentrantLock();
    public static Lock gLock = new ReentrantLock();
    public static boolean genOK = true;

    public static final int WIDTH = 40;

    public static short[][] last_result;
    public static long last = 0;

    @Scheduled(cron = "0 * * * * ?")
    private static void makeGenOK(){
        if(gLock.tryLock()){
            try {
                genOK = true;
            }
            catch (Exception e){
            }
            finally {
                gLock.unlock();
            }
        }
        else{
        }
    }

    public static short[][] generateXYNormal(double miu, double sigma, int times){
        Random r = new Random();
        short[][] result = new short[WIDTH][WIDTH];
        for (int i = 0; i < times; i++) {
            double x = r.nextGaussian() * sigma + miu;
            short sx = (short) x;
            double y = r.nextGaussian() * sigma + miu;
            short sy = (short) y;
            if (sx >= 0 && sx < WIDTH && sy >= 0 && sy < WIDTH){
                result[sx][sy] += 1;
            }
        }
        return result;
    }

    public static short[][] generateXYUniform(int times){
        Random r = new Random();
        short[][] result = new short[WIDTH][WIDTH];
        for (int i = 0; i < times; i++) {
            short sx = (short) r.nextInt(WIDTH);
            short sy = (short) r.nextInt(WIDTH);
            result[sx][sy] += 1;
        }
        return result;
    }

    public void concurrentDo(){
        if(lock.tryLock()){
            try {
                System.out.println(Thread.currentThread().getName()+": i get the lock!, sleep 1 sec");
                Thread.sleep(2000);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            finally {
                System.out.println(Thread.currentThread().getName()+": i release lock!");
                lock.unlock();
            }

        }
        else{
            System.out.println(Thread.currentThread().getName()+": it has been locked");
        }
    }


    @RequestMapping(value = "/distribution/normalXY", method = RequestMethod.GET)
    public Object getNormalXYDistribution(
//            @RequestHeader(name = "Authorization") String token,
                                  @RequestParam(name = "miu") double miu,
                                  @RequestParam(name = "sigma") double sigma,
                                  @RequestParam(name = "times") int times
                                  ) {
        if (gLock.tryLock()) {
            try {
                System.out.println(Thread.currentThread().getName() + ": get the lock!");
                if (genOK) {
                    genOK = false;
//                    int tid = JWTToken.verify(token).getClaims().get("id").asInt();

                    if (times > 10000)
                        times = 10000;
                    last_result = generateXYNormal(miu, sigma, times);
                    last+=1;
                    return last_result;
                } else {
                    System.out.println(Thread.currentThread().getName() + ": but gen not ok...");
                    return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println(Thread.currentThread().getName() + ": release the lock!");
                gLock.unlock();
            }
        } else {
            System.out.println(Thread.currentThread().getName() + ": can't get the lock...");
            return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
        }

        return null;
    }


    @RequestMapping(value = "/distribution/uniformXY", method = RequestMethod.GET)
    public Object getUniformXYDistribution(
//            @RequestHeader(name = "Authorization") String token,
                                          @RequestParam(name = "times") int times
    ){
        if (gLock.tryLock()) {
            try {
                System.out.println(Thread.currentThread().getName() + ": get the lock!");
                if (genOK) {
                    genOK = false;

//                    int tid = JWTToken.verify(token).getClaims().get("id").asInt();

                    if (times > 10000)
                        times = 10000;
                    last_result = generateXYUniform(times);
                    last+=1;
                    return last_result;

                } else {
                    System.out.println(Thread.currentThread().getName() + ": but gen not ok...");
                    return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println(Thread.currentThread().getName() + ": release the lock!");
                gLock.unlock();
            }
        } else {
            System.out.println(Thread.currentThread().getName() + ": can't get the lock...");
            return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
        }

        return null;
    }

    @RequestMapping(value = "/distribution/check", method = RequestMethod.GET)
    public Object checkLastResult(
            @RequestParam(name = "now") int now){
//        System.out.println(last);
//        System.out.println(now);
        if (now == last){
            return new ResponseEntity<Object>(null, HttpStatus.NOT_MODIFIED);
        }
        else{
            Map<String, Object> map = new HashMap<>();
            map.put("now", last);
            map.put("data", last_result);
            return new ResponseEntity<Object>(map, HttpStatus.OK);
        }

    }


}
