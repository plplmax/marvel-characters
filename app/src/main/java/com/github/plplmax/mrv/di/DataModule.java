package com.github.plplmax.mrv.di;

import androidx.annotation.NonNull;

import com.github.plplmax.mrv.R;
import com.github.plplmax.mrv.data.cloud.CharactersService;
import com.github.plplmax.mrv.data.cloud.source.CharactersCloudDataSource;
import com.github.plplmax.mrv.data.cloud.source.CharactersCloudDataSourceImpl;
import com.github.plplmax.mrv.data.mappers.CharacterDataWrapperResponseMapper;
import com.github.plplmax.mrv.data.repository.CharactersRepositoryImpl;
import com.github.plplmax.mrv.domain.core.Md5Provider;
import com.github.plplmax.mrv.domain.repository.CharactersRepository;
import com.github.plplmax.mrv.ui.core.ResourceProvider;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class DataModule {
    private static final String BASE_URL = "https://gateway.marvel.com/v1/public/";
    private static final String API_KEY = "apikey";
    private static final String TIMESTAMP_KEY = "ts";
    private static final String HASH_KEY = "hash";
    private static final byte TIMEOUT_IN_SECONDS = 120;

    @Provides
    Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    OkHttpClient provideOkHttpClient(Interceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    CharactersService provideCharactersService(@NonNull Retrofit retrofit) {
        return retrofit.create(CharactersService.class);
    }

    @Provides
    CharactersCloudDataSource provideCloudDataSource(CharactersService service) {
        return new CharactersCloudDataSourceImpl(service);
    }

    @Provides
    CharactersRepository provideCharactersRepository(CharactersCloudDataSource cloudDataSource,
                                                     CharacterDataWrapperResponseMapper mapper) {
        return new CharactersRepositoryImpl(cloudDataSource, mapper);
    }

    @Provides
    Interceptor provideInterceptor(ResourceProvider provider) {
        return chain -> {
            Request request = chain.request();
            long timeStamp = System.currentTimeMillis();
            String publicKey = provider.getString(R.string.public_marvel_api_key);
            String privateKey = provider.getString(R.string.private_marvel_api_key);
            String md5 = Md5Provider.getMd5(timeStamp + privateKey + publicKey);
            HttpUrl url = request.url()
                    .newBuilder()
                    .addQueryParameter(API_KEY, publicKey)
                    .addQueryParameter(TIMESTAMP_KEY, timeStamp + "")
                    .addQueryParameter(HASH_KEY, md5)
                    .build();
            request = request.newBuilder().url(url).build();
            return chain.proceed(request);
        };
    }

    @Provides
    CharacterDataWrapperResponseMapper provideCharacterDataWrapperResponseMapper() {
        return new CharacterDataWrapperResponseMapper();
    }
}
