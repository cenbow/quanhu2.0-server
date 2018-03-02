package com.rongzhong.component.pay.chinapay;

import chinapay.Des;
import chinapay.DesKey;
import chinapay.PrivateKey;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/3/2 11:33
 * Created by lifan
 */
public class ChinapayKey extends PrivateKey {

    public boolean buildKey(String merID, int keyUsage, String keyFile) {
        byte[] iv = new byte[8];
        DesKey ks = new DesKey("SCUBEPGW".getBytes(), false);
        Des des = new Des(ks);
        byte[] KeyBuf = new byte[712];

        InputStream is;
        BufferedReader br;
        try {
            ClassPathResource classPathResource = new ClassPathResource(keyFile);
            is = classPathResource.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
        } catch (Exception e) {
            return false;
        }

        String tmpString;
        String MerPG_flag;
        label272:
        {
            boolean var17;
            try {
                boolean flag1;
                try {
                    tmpString = br.readLine();
                    if (tmpString.compareTo("[SecureLink]") == 0 || tmpString.compareTo("[NetPayClient]") == 0) {
                        tmpString = br.readLine();
                        int m_Pos = tmpString.indexOf("=");
                        MerPG_flag = tmpString.substring(0, m_Pos);
                        tmpString = tmpString.substring(m_Pos + 1, tmpString.length());
                        if (tmpString.compareTo(merID) != 0) {
                            flag1 = false;
                            var17 = flag1;
                            return var17;
                        }

                        if (keyUsage == 0) {
                            tmpString = br.readLine();
                            if (MerPG_flag.compareTo("PGID") != 0) {
                                tmpString = tmpString.substring(88, tmpString.length());
                            } else {
                                tmpString = tmpString.substring(56, tmpString.length());
                            }
                        } else {
                            tmpString = br.readLine();
                            tmpString = br.readLine();
                            if (MerPG_flag.compareTo("PGID") != 0) {
                                tmpString = tmpString.substring(88, tmpString.length());
                            } else {
                                tmpString = tmpString.substring(56, tmpString.length());
                            }
                        }
                        break label272;
                    }

                    boolean flag = false;
                    var17 = flag;
                } catch (Exception var29) {
                    flag1 = false;
                    var17 = flag1;
                    return var17;
                }
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }

                    if (is != null) {
                        is.close();
                    }
                } catch (IOException var28) {
                    return false;
                }

            }

            return var17;
        }

        BigInteger Convert = new BigInteger(tmpString, 16);
        KeyBuf = Convert.toByteArray();
        if (KeyBuf[0] == 0) {
            int i;
            if (MerPG_flag.compareTo("PGID") != 0) {
                for (i = 0; i < 712; ++i) {
                    KeyBuf[i] = KeyBuf[i + 1];
                }
            } else {
                for (i = 0; i < 264; ++i) {
                    KeyBuf[i] = KeyBuf[i + 1];
                }
            }
        }

        System.arraycopy(KeyBuf, 0, this.Modulus, 0, 128);
        if (MerPG_flag.compareTo("MERID") == 0) {
            this.memset_new(iv, (byte) 0, iv.length);
            des.cbc_encrypt(KeyBuf, 384, 64, this.Prime[0], 0, iv, false);
            this.memset_new(iv, (byte) 0, iv.length);
            des.cbc_encrypt(KeyBuf, 448, 64, this.Prime[1], 0, iv, false);
            this.memset_new(iv, (byte) 0, iv.length);
            des.cbc_encrypt(KeyBuf, 512, 64, this.PrimeExponent[0], 0, iv, false);
            this.memset_new(iv, (byte) 0, iv.length);
            des.cbc_encrypt(KeyBuf, 576, 64, this.PrimeExponent[1], 0, iv, false);
            this.memset_new(iv, (byte) 0, iv.length);
            des.cbc_encrypt(KeyBuf, 640, 64, this.Coefficient, 0, iv, false);
        } else if (MerPG_flag.compareTo("PGID") != 0) {
            return false;
        }

        return true;
    }

    private void memset_new(byte[] out, byte c, int len) {
        for (int i = 0; i < len; ++i) {
            out[i] = c;
        }

    }
}
