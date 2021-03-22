package com.alibaba.com.caucho.hessian.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class Hessian2InputTest {
    @Test
    public void should_read_a_byte_correctly() throws IOException {
        byte[] buf = new byte[512];
        buf[0] = 0x01;
        buf[2] = 0x03;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(buf);

        Hessian2Input hessian2Input = new Hessian2Input(inputStream);

        Assert.assertEquals(0x1, hessian2Input.read());
        Assert.assertEquals(0x0, hessian2Input.read());
        Assert.assertEquals(0x3, hessian2Input.read());
    }
}