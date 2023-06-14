package com.example.adweb_backend;

import com.example.adweb_backend.controller.DistributionController;
import org.junit.jupiter.api.Test;

public class TestGenerateValues {
    @Test
    void testGenerateXYNormal(){
        short[][]r = DistributionController.generateXYNormal(20, 5, 10000);
        for (int i = 0; i < r.length; i++) {
            StringBuilder a = new StringBuilder();
            for (int j = 0; j < r[0].length; j++) {
                a.append(r[i][j]);
                a.append("\t");
            }
            System.out.println(a.toString());
        }
    }


    @Test
    void testUniform(){
        short[][]r = DistributionController.generateXYUniform(10000);
        for (int i = 0; i < r.length; i++) {
            StringBuilder a = new StringBuilder();
            for (int j = 0; j < r[0].length; j++) {
                a.append(r[i][j]);
                a.append("\t");
            }
            System.out.println(a.toString());
        }
    }
}
