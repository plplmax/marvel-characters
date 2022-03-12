package com.github.plplmax.marvelcharacters.ui.core;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import android.content.Context;

import com.github.plplmax.marvelcharacters.R;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ResourceProviderTest {
    private static final String FAKE_STRING = "MRV";
    private static final int STRING_ID = R.string.app_name;

    @Mock
    private Context context;

    @Test
    public void getString_stringId_returnsString() {
        when(context.getString(STRING_ID)).thenReturn(FAKE_STRING);
        ResourceProvider provider = new ResourceProvider.Base(context);

        String result = provider.getString(STRING_ID);

        assertThat(result, is(FAKE_STRING));
    }
}