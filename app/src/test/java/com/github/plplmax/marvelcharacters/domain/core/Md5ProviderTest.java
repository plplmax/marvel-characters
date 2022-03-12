package com.github.plplmax.marvelcharacters.domain.core;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class Md5ProviderTest {
    @Test
    public void getMd5_fourStrings_returnsFourMd5() {
        Md5Provider provider = new Md5Provider.Base();
        String[] testStrings = new String[]{
                "test",
                "",
                "privateKey140839",
                "veeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeerylongstring"};

        String[] md5Results = new String[]{
                provider.getMd5(testStrings[0]),
                provider.getMd5(testStrings[1]),
                provider.getMd5(testStrings[2]),
                provider.getMd5(testStrings[3])};

        assertThat(md5Results[0], is("098f6bcd4621d373cade4e832627b4f6"));
        assertThat(md5Results[1], is("d41d8cd98f00b204e9800998ecf8427e"));
        assertThat(md5Results[2], is("54a81fe9a26f0070e999e6fc46719ca3"));
        assertThat(md5Results[3], is("1cf89cf869cfee12b915d484ef75d0fa"));
    }
}