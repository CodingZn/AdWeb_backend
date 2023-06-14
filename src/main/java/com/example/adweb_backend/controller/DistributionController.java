package com.example.adweb_backend.controller;

import com.example.adweb_backend.util.JWTToken;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("")
public class DistributionController {

    public static final int WIDTH = 40;

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

    @RequestMapping(value = "/distribution/normalXY", method = RequestMethod.GET)
    public Object getNormalXYDistribution(@RequestHeader(name = "Authorization") String token,
                                  @RequestParam(name = "miu") double miu,
                                  @RequestParam(name = "sigma") double sigma,
                                  @RequestParam(name = "times") int times
                                  ){
        int tid = JWTToken.verify(token).getClaims().get("id").asInt();

        if (times > 10000)
            times = 10000;
        return generateXYNormal(miu, sigma, times);
    }


}
