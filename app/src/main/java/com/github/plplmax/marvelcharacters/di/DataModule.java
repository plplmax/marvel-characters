package com.github.plplmax.marvelcharacters.di;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.github.plplmax.marvelcharacters.R;
import com.github.plplmax.marvelcharacters.data.core.EntityMapper;
import com.github.plplmax.marvelcharacters.data.core.ErrorToDomainMapper;
import com.github.plplmax.marvelcharacters.data.core.ResponseMapper;
import com.github.plplmax.marvelcharacters.data.local.AppDatabase;
import com.github.plplmax.marvelcharacters.data.local.CharacterDao;
import com.github.plplmax.marvelcharacters.data.local.CharacterEntity;
import com.github.plplmax.marvelcharacters.data.local.CharacterEntityMapper;
import com.github.plplmax.marvelcharacters.data.local.CharactersLocalDataSource;
import com.github.plplmax.marvelcharacters.data.remote.CharacterResponseMapper;
import com.github.plplmax.marvelcharacters.data.remote.CharactersRemoteDataSource;
import com.github.plplmax.marvelcharacters.data.remote.CharactersService;
import com.github.plplmax.marvelcharacters.data.remote.responses.CharacterDataWrapperResponse;
import com.github.plplmax.marvelcharacters.data.repository.CharactersRepositoryImpl;
import com.github.plplmax.marvelcharacters.domain.core.AppError;
import com.github.plplmax.marvelcharacters.domain.core.Mapper;
import com.github.plplmax.marvelcharacters.domain.core.Md5Provider;
import com.github.plplmax.marvelcharacters.domain.models.Character;
import com.github.plplmax.marvelcharacters.domain.repository.CharactersRepository;
import com.github.plplmax.marvelcharacters.ui.core.ResourceProvider;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class DataModule {
    private static final String BASE_URL = "https://gateway.marvel.com/v1/public/";
    private static final String API_KEY = "apikey";
    private static final String TIMESTAMP_KEY = "ts";
    private static final String HASH_KEY = "hash";
    private static final String DATABASE_NAME = "database";
    private static final byte TIMEOUT_IN_SECONDS = 30;

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    @Provides
    OkHttpClient provideOkHttpClient(Interceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    CharactersService provideCharactersService(@NonNull Retrofit retrofit) {
        return retrofit.create(CharactersService.class);
    }

    @Provides
    CharactersRemoteDataSource provideRemoteDataSource(CharactersService service) {
        return new CharactersRemoteDataSource.Base(service);
    }

    @Provides
    CharactersLocalDataSource provideLocalDataSource(CharacterDao dao) {
        return new CharactersLocalDataSource.Base(dao);
    }

    @Provides
    @Singleton
    CharacterDao provideCharacterDao(AppDatabase database) {
        return database.characterDao();
    }

    @Provides
    @Singleton
    AppDatabase provideRoomDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                .build();
    }

    @Provides
    @Singleton
    CharactersRepository provideCharactersRepository(
            CharactersRemoteDataSource remoteDataSource,
            CharactersLocalDataSource localDataSource,
            ResponseMapper<CharacterDataWrapperResponse, List<Character>> responseMapper,
            EntityMapper<List<CharacterEntity>, List<Character>> characterEntityMapper,
            Mapper<Throwable, AppError> errorToDomainMapper) {
        return new CharactersRepositoryImpl(
                remoteDataSource,
                localDataSource,
                responseMapper,
                characterEntityMapper,
                errorToDomainMapper);
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
    @Singleton
    ResponseMapper<CharacterDataWrapperResponse, List<Character>> provideCharacterResponseMapper() {
        return new CharacterResponseMapper();
    }

    @Provides
    @Singleton
    EntityMapper<List<CharacterEntity>, List<Character>> provideCharacterEntityMapper() {
        return new CharacterEntityMapper();
    }

    @Provides
    @Singleton
    Mapper<Throwable, AppError> provideErrorToDomainMapper() {
        return new ErrorToDomainMapper();
    }
}
