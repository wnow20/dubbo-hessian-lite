package com.alibaba.com.caucho.hessian.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.alibaba.com.caucho.hessian.io.beans.GrandsonUser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class Hessian2OutputTest {

    @Test
    public void should_output_primary_data_correctly() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream(4096);

        Hessian2Output hessian2Output = new Hessian2Output(os);

        hessian2Output.writeString("key1");
        hessian2Output.writeString("key2");
        hessian2Output.writeInt(1);
        hessian2Output.writeInt(2);
        hessian2Output.flush();

        InputStream fileInputStream = new ByteArrayInputStream(os.toByteArray());
        Hessian2Input hessian2Input = new Hessian2Input(fileInputStream);

        Assert.assertEquals("key1", hessian2Input.readString());
        Assert.assertEquals("key2", hessian2Input.readString());
        Assert.assertEquals(1, hessian2Input.readInt());
        Assert.assertEquals(2, hessian2Input.readInt());
        os.close();
        hessian2Input.close();
    }

    @Test
    public void should_output_external_object_correctly() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream(4096);

        Hessian2Output hessian2Output = new Hessian2Output(os);
        GrandsonUser grandsonUser = new GrandsonUser();
        grandsonUser.setUserId(100);
        grandsonUser.setUserName("user name");
        grandsonUser.setAgeList(new ArrayList<Integer>() {{
            this.add(1);
            this.add(2);
            this.add(3);
        }});
        grandsonUser.setSexyList(new ArrayList<Boolean>() {{
            this.add(true);
            this.add(false);
        }});
        grandsonUser.setWeightList(new ArrayList<Double>() {{
            this.add(123.45);
            this.add(130.12);
            this.add(95.6);
        }});
        hessian2Output.writeObject(grandsonUser);
        hessian2Output.flush();

        InputStream inputStream =  new ByteArrayInputStream(os.toByteArray());
        Hessian2Input hessian2Input = new Hessian2Input(inputStream);
        GrandsonUser fetched = (GrandsonUser)hessian2Input.readObject(GrandsonUser.class);

        Assert.assertEquals(new Integer(100), fetched.getUserId());
        Assert.assertEquals("user name", fetched.getUserName());
        Assert.assertEquals(3, fetched.getAgeList().size());
        Assert.assertEquals(new Integer(1), fetched.getAgeList().get(0));
        Assert.assertEquals(new Integer(2), fetched.getAgeList().get(1));
        Assert.assertEquals(new Integer(3), fetched.getAgeList().get(2));
        Assert.assertEquals(true, fetched.getSexyList().get(0));
        Assert.assertEquals(false, fetched.getSexyList().get(1));
        Assert.assertEquals(new Double(123.45), fetched.getWeightList().get(0));
        Assert.assertEquals(new Double(130.12), fetched.getWeightList().get(1));
        Assert.assertEquals(new Double(95.6), fetched.getWeightList().get(2));
    }
}