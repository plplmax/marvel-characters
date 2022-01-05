package com.github.plplmax.mrv.di;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.github.plplmax.mrv.R;
import com.github.plplmax.mrv.data.cloud.CharactersService;
import com.github.plplmax.mrv.data.cloud.source.CharactersCloudDataSource;
import com.github.plplmax.mrv.data.cloud.source.CharactersCloudDataSourceImpl;
import com.github.plplmax.mrv.data.local.AppDatabase;
import com.github.plplmax.mrv.data.local.CharacterDao;
import com.github.plplmax.mrv.data.local.source.CharactersLocalDataSource;
import com.github.plplmax.mrv.data.local.source.CharactersLocalDataSourceImpl;
import com.github.plplmax.mrv.data.mappers.CharacterDataMapper;
import com.github.plplmax.mrv.data.mappers.CharacterDataWrapperResponseMapper;
import com.github.plplmax.mrv.data.cloud.CharacterResponseMapper;
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
    private static final String DATABASE_NAME = "database";
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
    CharactersLocalDataSource provideLocalDataSource(CharacterDao dao) {
        return new CharactersLocalDataSourceImpl(dao);
    }

    @Provides
    CharacterDao provideCharacterDao(AppDatabase database) {
        return database.characterDao();
    }

    @Provides
    AppDatabase provideRoomDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                .build();
    }

    @Provides
    CharactersRepository provideCharactersRepository(CharactersCloudDataSource cloudDataSource,
                                                     CharactersLocalDataSource localDataSource,
                                                     CharacterDataWrapperResponseMapper wrapperResponseMapper,
                                                     CharacterDataMapper characterDataMapper) {
                                                     CharacterResponseMapper wrapperResponseMapper,
        return new CharactersRepositoryImpl(cloudDataSource,
                localDataSource,
                wrapperResponseMapper,
                characterDataMapper);
    }

    @Provides
    Interceptor provideInterceptor(ResourceProvider resourceProvider, Md5Provider md5Provider) {
        return chain -> {
            Request request = chain.request();
            long timeStamp = System.currentTimeMillis();
            String publicKey = resourceProvider.getString(R.string.public_marvel_api_key);
            String privateKey = resourceProvider.getString(R.string.private_marvel_api_key);
            String md5 = md5Provider.getMd5(timeStamp + privateKey + publicKey);
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
    CharacterResponseMapper provideCharacterDataWrapperResponseMapper() {
        return new CharacterResponseMapper();
    }

    @Provides
    CharacterDataMapper provideCharacterDataMapper() {
        return new CharacterDataMapper();
    }
}
